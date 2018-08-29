package de.senacor.bankathon.pocloy.authentication.task;

import android.os.AsyncTask;
import android.util.Log;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpStatusCodeException;

import java.util.List;

import de.senacor.bankathon.pocloy.authentication.dto.TradeOffer;
import de.senacor.bankathon.pocloy.authentication.framework.GsonRestTemplate;

public abstract class LoadAvailableTradesTask extends AsyncTask<Void, Void, List<TradeOffer>> {

    private static final String AVAILABLE_TRADES_URI = "https://desolate-depths-64341.herokuapp.com/user/getOtherExchangeOffers";

    private final GsonRestTemplate restTemplate;

    public LoadAvailableTradesTask() {
        restTemplate = new GsonRestTemplate();
    }

    @Override
    protected List<TradeOffer> doInBackground(Void... voids) {
        try {
            ResponseEntity<List<TradeOffer>> response = restTemplate.exchange(
                    AVAILABLE_TRADES_URI,
                    HttpMethod.POST,
                    new HttpEntity<>(GsonRestTemplate.getCredentials()),
                    new ParameterizedTypeReference<List<TradeOffer>>() {
                    });
            if (response.getStatusCode().is2xxSuccessful()) {
                Log.d("LoadAvailableTradesTask", "Retrieved available trades");
                return response.getBody();
            }
        } catch (Exception e) {
            if (e instanceof HttpStatusCodeException) {
                HttpStatusCodeException httpStatusCodeException = (HttpStatusCodeException) e;
                Log.e("LoadAvailableTradesTask", "Body: " + httpStatusCodeException.getResponseBodyAsString(), httpStatusCodeException);
            } else {
                Log.e("LoadAvailableTradesTask", "Exception thrown while retrieving available trades: " + e.getMessage(), e);
            }
        }
        return null;
    }

    @Override
    protected void onPostExecute(List<TradeOffer> tradeOffers) {
        if (tradeOffers != null) {
            handleSuccessfulRetrieval(tradeOffers);
        } else {
            handleFailedRetrieval();
        }
    }

    protected abstract void handleSuccessfulRetrieval(List<TradeOffer> result);

    protected abstract void handleFailedRetrieval();
}
