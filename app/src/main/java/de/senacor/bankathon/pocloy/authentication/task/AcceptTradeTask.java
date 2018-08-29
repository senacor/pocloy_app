package de.senacor.bankathon.pocloy.authentication.task;

import android.os.AsyncTask;
import android.util.Log;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpStatusCodeException;

import java.util.List;

import de.senacor.bankathon.pocloy.authentication.dto.Credentials;
import de.senacor.bankathon.pocloy.authentication.dto.TradeOffer;
import de.senacor.bankathon.pocloy.authentication.framework.GsonRestTemplate;

public abstract class AcceptTradeTask extends AsyncTask<TradeOffer, Void, Boolean> {

    private static final String ACCEPT_TRADE_URI = "https://desolate-depths-64341.herokuapp.com/user/consumeExchangeOffer";

    private final GsonRestTemplate restTemplate;

    public AcceptTradeTask() {
        restTemplate = new GsonRestTemplate();
    }

    @Override
    protected Boolean doInBackground(TradeOffer... tradeOffers) {
        try {
            boolean success = false;
            for (TradeOffer tradeOffer : tradeOffers) {
                ResponseEntity<List<TradeOffer>> response = restTemplate.exchange(
                        ACCEPT_TRADE_URI,
                        HttpMethod.POST,
                        new HttpEntity<>(new Body(tradeOffer.getExchangeOfferId())),
                        new ParameterizedTypeReference<List<TradeOffer>>() {
                        });
                if (response.getStatusCode().is2xxSuccessful()) {
                    Log.d("AcceptTradeTask", "Accepted trade");
                    success = true;
                }
            }
            return success;
        } catch (Exception e) {
            if (e instanceof HttpStatusCodeException) {
                HttpStatusCodeException httpStatusCodeException = (HttpStatusCodeException) e;
                Log.e("AcceptTradeTask", "Body: " + httpStatusCodeException.getResponseBodyAsString(), httpStatusCodeException);
            } else {
                Log.e("AcceptTradeTask", "Exception thrown while accepting trade: " + e.getMessage(), e);
            }
        }
        return false;
    }

    @Override
    protected void onPostExecute(Boolean success) {
        if (Boolean.TRUE.equals(success)) {
            handleSuccessfulRetrieval();
        } else {
            handleFailedRetrieval();
        }
    }

    protected abstract void handleSuccessfulRetrieval();

    protected abstract void handleFailedRetrieval();

    private class Body {
        private final long exchangeOfferToConsumeId;
        private final Credentials credentials = GsonRestTemplate.getCredentials();

        public Body(long exchangeOfferToConsumeId) {
            this.exchangeOfferToConsumeId = exchangeOfferToConsumeId;
        }

        public long getExchangeOfferToConsumeId() {
            return exchangeOfferToConsumeId;
        }

        public Credentials getCredentials() {
            return credentials;
        }
    }
}
