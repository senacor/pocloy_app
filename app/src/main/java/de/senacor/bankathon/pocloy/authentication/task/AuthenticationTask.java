package de.senacor.bankathon.pocloy.authentication.task;

import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpStatusCodeException;

import java.util.Arrays;
import java.util.List;

import de.senacor.bankathon.pocloy.authentication.dto.Credentials;
import de.senacor.bankathon.pocloy.authentication.dto.UserAssets;
import de.senacor.bankathon.pocloy.authentication.framework.GsonRestTemplate;

public abstract class AuthenticationTask extends AsyncTask<Void, Void, List<UserAssets>> {
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
    protected final List<UserAssets> doInBackground(Void... params) {
        try {
            ResponseEntity<Void> responseEntity = restTemplate.postForEntity(loginUri, credentials, Void.class);
            if (responseEntity.getStatusCode().is2xxSuccessful()) {
                Log.d("AuthenticationTask.doInBackground", "Login successfull");
                GsonRestTemplate.setCredentials(credentials);
                Gson gson = new GsonBuilder().setDateFormat("dd-MM-yyyy HH:mm:ss").create();
                String jsonList = restTemplate.postForObject(transactionUri, credentials, String.class);
                UserAssets[] userAssets = gson.fromJson(jsonList, UserAssets[].class);
                return Arrays.asList(userAssets);
            } else {
                Log.d("AuthenticationTask.doInBackground", "Login failed");
                this.authenticationException = new IllegalArgumentException("Was not 200");
            }
        } catch (Exception e) {
            this.authenticationException = e;
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

    protected abstract void handleSuccessfulAuthentication(List<UserAssets> result);

    protected abstract void handleFailedAuthentication();
}
