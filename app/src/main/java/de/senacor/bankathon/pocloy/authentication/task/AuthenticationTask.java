package de.senacor.bankathon.pocloy.authentication.task;

import android.os.AsyncTask;
import android.util.Log;
import org.springframework.http.ResponseEntity;
import de.senacor.bankathon.pocloy.authentication.dto.Credentials;
import de.senacor.bankathon.pocloy.authentication.dto.UserAssetsList;
import de.senacor.bankathon.pocloy.authentication.framework.GsonRestTemplate;

public abstract class AuthenticationTask extends AsyncTask<Void, Void, UserAssetsList> {
    private final Credentials credentials;
    private final GsonRestTemplate restTemplate;
    private final String loginUri = "https://desolate-depths-64341.herokuapp.com/user/login";
    private final String transactionUri = "https://desolate-depths-64341.herokuapp.com/user/transactions";
    private Exception authenticationException;

    public AuthenticationTask(String email, String password) {
        this.credentials = new Credentials(email, password);
        this.restTemplate = new GsonRestTemplate();

    }

    @Override
    protected final UserAssetsList doInBackground(Void... params) {
        try {
            ResponseEntity<Void> responseEntity = restTemplate.postForEntity(loginUri, credentials, Void.class);
            if (responseEntity.getStatusCode().is2xxSuccessful()) {
                Log.d("AuthenticationTask.doInBackground", "Login successfull");
                GsonRestTemplate.setCredentials(credentials);
                return restTemplate.postForObject(transactionUri, credentials, UserAssetsList.class);
            } else {
                Log.d("AuthenticationTask.doInBackground", "Login failed");
                this.authenticationException = new IllegalArgumentException("Was not 200");
            }
        } catch (Exception e) {
            this.authenticationException = e;
            Log.d("AuthenticationTask.doInBackground", e.getMessage(), e);
        }
        return null;
    }

    @Override
    protected final void onPostExecute(UserAssetsList result) {
        if (authenticationException == null) {
            handleSuccessfulAuthentication(result);
        } else {
            handleFailedAuthentication();
        }
    }

    @Override
    protected final void onCancelled() {
        Log.d("AuthenticationTask", "AuthenticationTask.onCancelled");
    }

    protected abstract void handleSuccessfulAuthentication(UserAssetsList result);

    protected abstract void handleFailedAuthentication();
}
