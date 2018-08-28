package de.senacor.bankathon.pocloy.authentication.task;

import android.os.AsyncTask;
import android.util.Log;
import de.senacor.bankathon.pocloy.authentication.dto.Credentials;
import de.senacor.bankathon.pocloy.authentication.dto.UserAssetsList;
import de.senacor.bankathon.pocloy.authentication.framework.GsonRestTemplate;

public abstract class AuthenticationTask extends AsyncTask<Void, Void, Void> {
    private final Credentials credentials;
    private final GsonRestTemplate restTemplate;
    //TODO: Specify
    private final String uri = "https://desolate-depths-64341.herokuapp.com/user/login";
    private Exception authenticationException;

    public AuthenticationTask(String email, String password) {
        this.credentials = new Credentials(email, password);
        this.restTemplate = new GsonRestTemplate();

    }

    @Override
    protected final Void doInBackground(Void... params) {
        try {
           UserAssetsList userAssetsList = restTemplate.postForObject(uri, credentials, UserAssetsList.class);
        } catch (Exception e) {
            this.authenticationException = e;
        }
        return null;
    }

    @Override
    protected final void onPostExecute(Void result) {
        if (authenticationException == null) {
            handleSuccessfulAuthentication();
        } else {
            handleFailedAuthentication();
        }
    }

    @Override
    protected final void onCancelled() {
        Log.d("AuthenticationTask", "AuthenticationTask.onCancelled");
    }

    protected abstract void handleSuccessfulAuthentication();

    protected abstract void handleFailedAuthentication();
}
