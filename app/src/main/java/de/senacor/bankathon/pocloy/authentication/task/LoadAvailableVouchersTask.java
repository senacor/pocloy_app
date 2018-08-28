package de.senacor.bankathon.pocloy.authentication.task;

import android.os.AsyncTask;
import android.util.Log;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import java.util.List;

import de.senacor.bankathon.pocloy.authentication.dto.VoucherRedeemingData;
import de.senacor.bankathon.pocloy.authentication.framework.GsonRestTemplate;

public abstract class LoadAvailableVouchersTask extends AsyncTask<Void, Void, List<VoucherRedeemingData>> {

    private final GsonRestTemplate restTemplate;
    private String availableVouchersUri = "https://desolate-depths-64341.herokuapp.com/merchant/vouchers";

    public LoadAvailableVouchersTask() {
        this.restTemplate = new GsonRestTemplate();
    }

    @Override
    protected List<VoucherRedeemingData> doInBackground(Void... voids) {
        try {
            ResponseEntity<List<VoucherRedeemingData>> response = restTemplate.exchange(
                    availableVouchersUri,
                    HttpMethod.GET,
                    null,
                    new ParameterizedTypeReference<List<VoucherRedeemingData>>() {
                    });
            if (response.getStatusCode().is2xxSuccessful()) {
                Log.d("LoadAvailableVouchersTask", "Retrieved available vouchers");
                return response.getBody();
            }
        } catch (Exception e) {
            Log.e("LoadAvailableVouchersTask", "Exception thrown while retrieving available vouchers", e);
        }
        return null;
    }

    @Override
    protected void onPostExecute(List<VoucherRedeemingData> voucherRedeemingData) {
        if (voucherRedeemingData != null) {
            handleSuccessfulRetrieval(voucherRedeemingData);
        } else {
            handleFailedRetrieval();
        }
    }

    protected abstract void handleSuccessfulRetrieval(List<VoucherRedeemingData> result);

    protected abstract void handleFailedRetrieval();
}
