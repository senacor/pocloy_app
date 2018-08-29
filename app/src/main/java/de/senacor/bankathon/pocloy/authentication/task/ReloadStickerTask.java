package de.senacor.bankathon.pocloy.authentication.task;

import android.os.AsyncTask;
import android.util.Log;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.web.client.HttpStatusCodeException;
import java.util.Arrays;
import java.util.List;
import de.senacor.bankathon.pocloy.authentication.dto.Credentials;
import de.senacor.bankathon.pocloy.authentication.dto.UserAssets;
import de.senacor.bankathon.pocloy.authentication.framework.GsonRestTemplate;

public abstract class ReloadStickerTask extends AsyncTask<Void, Void, List<UserAssets>> {
    private static final String TRANSACTION_URI = "https://desolate-depths-64341.herokuapp.com/user/transactions";
    private final Credentials credentials;
    private final GsonRestTemplate restTemplate;
    private Exception reloadStickerException;
    
    public ReloadStickerTask(){
        this.restTemplate = new GsonRestTemplate();
        this.credentials = GsonRestTemplate.getCredentials();
    }

    @Override
    protected final List<UserAssets> doInBackground(Void... params) {
        try {
                Gson gson = new GsonBuilder().setDateFormat("dd-MM-yyyy HH:mm:ss").create();
                String jsonList = restTemplate.postForObject(TRANSACTION_URI, credentials, String.class);
                UserAssets[] userAssets = gson.fromJson(jsonList, UserAssets[].class);
                return Arrays.asList(userAssets);
        } catch (Exception e) {
            this.reloadStickerException = e;
            if (e instanceof HttpStatusCodeException) {
                HttpStatusCodeException httpStatusCodeException = (HttpStatusCodeException) e;
                Log.e("AuthenticationTask.doInBackground", "Body: " + httpStatusCodeException.getResponseBodyAsString(), httpStatusCodeException);
            } else {
                Log.e("AuthenticationTask.doInBackground", "Message: " + e.getMessage(), e);
            }
        }
        return null;
    }

    @Override
    protected final void onPostExecute(List<UserAssets> result) {
        if (reloadStickerException == null) {
            handleSuccessfulReload(result);
        } else {
            handleFailedReload();
        }
    }

    @Override
    protected final void onCancelled() {
        Log.d("AuthenticationTask", "AuthenticationTask.onCancelled");
    }

    protected abstract void handleSuccessfulReload(List<UserAssets> result);

    protected abstract void handleFailedReload();
}
