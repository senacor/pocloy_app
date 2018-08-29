package de.senacor.bankathon.pocloy.authentication.task;

import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.springframework.web.client.HttpStatusCodeException;

import java.util.Arrays;
import java.util.List;

import de.senacor.bankathon.pocloy.authentication.dto.UserAssets;
import de.senacor.bankathon.pocloy.authentication.framework.DataHolder;
import de.senacor.bankathon.pocloy.authentication.framework.GsonRestTemplate;

public abstract class UpdateCollectionTask extends AsyncTask<Void, Void, List<UserAssets>> {
    private static final String TRANSACTION_URI = "https://desolate-depths-64341.herokuapp.com/user/transactions";

    private final GsonRestTemplate restTemplate;

    public UpdateCollectionTask() {
        this.restTemplate = new GsonRestTemplate();
    }

    @Override
    protected List<UserAssets> doInBackground(Void... voids) {
        try {
            Gson gson = new GsonBuilder().setDateFormat("dd-MM-yyyy HH:mm:ss").create();
            String jsonList = restTemplate.postForObject(TRANSACTION_URI, GsonRestTemplate.getCredentials(), String.class);
            UserAssets[] userAssets = gson.fromJson(jsonList, UserAssets[].class);
            return Arrays.asList(userAssets);
        } catch (Exception e) {
            if (e instanceof HttpStatusCodeException) {
                HttpStatusCodeException httpStatusCodeException = (HttpStatusCodeException) e;
                Log.e("UpdateCollectionTask.doInBackground", "Body: " + httpStatusCodeException.getResponseBodyAsString(), httpStatusCodeException);
            } else {
                Log.e("UpdateCollectionTask.doInBackground", "Message: " + e.getMessage(), e);
            }
        }
        return null;
    }

    @Override
    protected final void onPostExecute(List<UserAssets> result) {
        if (result != null) {
            handleSuccessfulAuthentication(result);
        } else {
            handleFailedAuthentication();
        }
    }

    @Override
    protected final void onCancelled() {
        Log.d("AuthenticationTask", "AuthenticationTask.onCancelled");
    }

    protected void handleSuccessfulAuthentication(List<UserAssets> result) {
        DataHolder.setUserAssets(result);
    }

    protected abstract void handleFailedAuthentication();
}
