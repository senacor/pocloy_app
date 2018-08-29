package de.senacor.bankathon.pocloy.authentication.task;

import android.os.AsyncTask;
import android.util.Log;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpStatusCodeException;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import de.senacor.bankathon.pocloy.authentication.dto.Credentials;
import de.senacor.bankathon.pocloy.authentication.dto.TradeOffer;
import de.senacor.bankathon.pocloy.authentication.framework.GsonRestTemplate;

public abstract class UpdateMyTradesTask extends AsyncTask<UpdateMyTradesTask.TradeOfferUpdate, Void, Boolean> {

    private static final String GET_MY_TRADES_URI = "https://desolate-depths-64341.herokuapp.com/user/getMyExchangeOffers";
    private static final String SET_MY_TRADES_URI = "https://desolate-depths-64341.herokuapp.com/user/setMyExchangeOffers";

    private final GsonRestTemplate restTemplate;

    public UpdateMyTradesTask() {
        restTemplate = new GsonRestTemplate();
    }

    @Override
    protected Boolean doInBackground(TradeOfferUpdate... tradeOffers) {
        try {
            ResponseEntity<List<TradeOffer>> myTrades = restTemplate.exchange(
                    GET_MY_TRADES_URI,
                    HttpMethod.POST,
                    new HttpEntity<>(GsonRestTemplate.getCredentials()),
                    new ParameterizedTypeReference<List<TradeOffer>>() {
                    });
            if (!myTrades.getStatusCode().is2xxSuccessful()) {
                return false;
            }
            Log.d("UpdateMyTradesTask", "Retrieved own trades");
            List<TradeOffer> unchangedTrades = myTrades.getBody()
                    .stream()
                    .filter(tradeOffer ->
                            Stream.of(tradeOffers)
                                    .map(TradeOfferUpdate::getTradeOffer)
                                    .map(TradeOffer::getExchangeOfferId)
                                    .noneMatch(id -> Objects.equals(id, tradeOffer.getExchangeOfferId()))
                    ).collect(Collectors.toList());
            List<TradeOffer> newTrades = Stream.of(tradeOffers)
                    .filter(tradeOffer -> tradeOffer.action == TradeOfferAction.ADD_NEW_OFFER)
                    .map(TradeOfferUpdate::getTradeOffer)
                    .collect(Collectors.toList());
            List<TradeOffer> allTradeOffers = new ArrayList<>(unchangedTrades.size() + newTrades.size());
            allTradeOffers.addAll(unchangedTrades);
            allTradeOffers.addAll(newTrades);
            ResponseEntity<Void> updateResponse = restTemplate.postForEntity(
                    SET_MY_TRADES_URI,
                    new HttpEntity<>(new TradeOfferUpdateBody(allTradeOffers)),
                    Void.class
            );
            return updateResponse.getStatusCode().is2xxSuccessful();
        } catch (Exception e) {
            if (e instanceof HttpStatusCodeException) {
                HttpStatusCodeException httpStatusCodeException = (HttpStatusCodeException) e;
                Log.e("UpdateMyTradesTask", "Body: " + httpStatusCodeException.getResponseBodyAsString(), httpStatusCodeException);
            } else {
                Log.e("UpdateMyTradesTask", "Exception thrown while updating my trades: " + e.getMessage(), e);
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

    public static class TradeOfferUpdate {
        private TradeOffer tradeOffer;
        private TradeOfferAction action;

        public TradeOfferUpdate(TradeOffer tradeOffer, TradeOfferAction action) {
            this.tradeOffer = tradeOffer;
            this.action = action;
        }

        public TradeOffer getTradeOffer() {
            return tradeOffer;
        }

        public TradeOfferAction getAction() {
            return action;
        }
    }

    public enum TradeOfferAction {
        ADD_NEW_OFFER, DELETE_OFFER
    }

    private class TradeOfferUpdateBody {
        private final Credentials credentials = GsonRestTemplate.getCredentials();
        private final List<TradeOffer> exchangeOfferDTOs;

        public TradeOfferUpdateBody(List<TradeOffer> exchangeOfferDTOs) {
            this.exchangeOfferDTOs = exchangeOfferDTOs;
        }

        public Credentials getCredentials() {
            return credentials;
        }

        public List<TradeOffer> getExchangeOfferDTOs() {
            return exchangeOfferDTOs;
        }
    }
}
