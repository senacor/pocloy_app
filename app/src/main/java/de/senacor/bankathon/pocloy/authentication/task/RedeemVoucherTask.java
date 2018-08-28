package de.senacor.bankathon.pocloy.authentication.task;

import android.os.AsyncTask;
import de.senacor.bankathon.pocloy.authentication.framework.GsonRestTemplate;

public abstract class RedeemVoucherTask extends AsyncTask<Void, Void, Void> {
    private static final String REDEEM_VOUCHER_URI = "https://desolate-depths-64341.herokuapp.com/user/redeemVoucher";
    
    private final GsonRestTemplate restTemplate;
    
    public RedeemVoucherTask(){
        this.restTemplate = new GsonRestTemplate();
    }
    
    @Override
    protected Void doInBackground(Void... voids) {
        String result = restTemplate.postForObject(REDEEM_VOUCHER_URI, unpackStickerRequest, String.class);
        return null;
    }

    @Override
    protected void onPostExecute(Void result) {
    }
    
    protected abstract void handleSuccessfulRedemption();
    
    protected abstract void handleFailedRedemption();
}
