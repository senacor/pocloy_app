package de.senacor.bankathon.pocloy.authentication.task;

import android.os.AsyncTask;
import android.util.Log;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpStatusCodeException;

import java.util.List;

import de.senacor.bankathon.pocloy.authentication.dto.UserVoucher;
import de.senacor.bankathon.pocloy.authentication.framework.GsonRestTemplate;

public abstract class LoadMyVouchersTask extends AsyncTask<Void, Void, List<UserVoucher>> {

    private static final String AVAILABLE_VOUCHERS_URI = "https://desolate-depths-64341.herokuapp.com/user/vouchers";

    private final GsonRestTemplate restTemplate;

    public LoadMyVouchersTask() {
        this.restTemplate = new GsonRestTemplate();
    }

    @Override
    protected List<UserVoucher> doInBackground(Void... voids) {
        try {
            ResponseEntity<List<UserVoucher>> response = restTemplate.exchange(
                    AVAILABLE_VOUCHERS_URI,
                    HttpMethod.POST,
                    new HttpEntity<>(GsonRestTemplate.getCredentials()),
                    new ParameterizedTypeReference<List<UserVoucher>>() {
                    });
            if (response.getStatusCode().is2xxSuccessful()) {
                Log.d("LoadAvailableVouchersTask", "Retrieved available vouchers");
                return response.getBody();
            }
        } catch (Exception e) {
            if (e instanceof HttpStatusCodeException) {
                HttpStatusCodeException httpStatusCodeException = (HttpStatusCodeException) e;
                Log.e("LoadAvailableVouchersTask", httpStatusCodeException.getResponseBodyAsString(), httpStatusCodeException);
            } else {
                Log.e("LoadAvailableVouchersTask", "Exception thrown while retrieving available vouchers: " + e.getMessage(), e);
            }
        }
        return null;
    }

    @Override
    protected void onPostExecute(List<UserVoucher> voucherRedeemingData) {
        if (voucherRedeemingData != null) {
            handleSuccessfulRetrieval(voucherRedeemingData);
        } else {
            handleFailedRetrieval();
        }
    }

    protected abstract void handleSuccessfulRetrieval(List<UserVoucher> result);

    protected abstract void handleFailedRetrieval();
}
