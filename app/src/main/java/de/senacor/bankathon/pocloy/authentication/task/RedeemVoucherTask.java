package de.senacor.bankathon.pocloy.authentication.task;

import android.os.AsyncTask;
import de.senacor.bankathon.pocloy.authentication.dto.Credentials;
import de.senacor.bankathon.pocloy.authentication.dto.RedeemVoucherRequest;
import de.senacor.bankathon.pocloy.authentication.framework.GsonRestTemplate;

public abstract class RedeemVoucherTask extends AsyncTask<Void, Void, Void> {
    private static final String REDEEM_VOUCHER_URI = "https://desolate-depths-64341.herokuapp.com/user/redeemVoucher";
    private final Credentials credentials;
    private final GsonRestTemplate restTemplate;
    private RedeemVoucherRequest redeemVoucherRequest;
    
    public RedeemVoucherTask(String voucherId){
        this.credentials = GsonRestTemplate.getCredentials();
        this.restTemplate = new GsonRestTemplate();
        redeemVoucherRequest = new RedeemVoucherRequest(voucherId, credentials);
    }
    
    @Override
    protected Void doInBackground(Void... voids) {
        String result = restTemplate.postForObject(REDEEM_VOUCHER_URI, redeemVoucherRequest, String.class);
        return null;
    }

    @Override
    protected void onPostExecute(Void result) {
    }
    
    protected abstract void handleSuccessfulRedemption();
    
    protected abstract void handleFailedRedemption();
}
