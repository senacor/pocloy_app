package de.senacor.bankathon.pocloy.authentication.task;

import android.os.AsyncTask;
import android.util.Log;
import de.senacor.bankathon.pocloy.authentication.dto.Credentials;
import de.senacor.bankathon.pocloy.authentication.framework.GsonRestTemplate;

public class AuthenticationTask extends AsyncTask<Void, Void, Void> {
    private final Credentials credentials;
    private final GsonRestTemplate restTemplate;
    //TODO: Specify
    private final String uri = "http://echo.jsontest.com/key/value/one/two";

    public AuthenticationTask(String email, String password) {
        this.credentials = new Credentials(email, password);
        this.restTemplate = new GsonRestTemplate();
        
    }

    @Override
    protected final Void doInBackground(Void... params) {
        try {
            String test = restTemplate.postForObject(uri, credentials, String.class);
        } catch (Exception e) {
            Log.e("AuthenticationTask", e.getMessage());
        }
        return null;
    }

    @Override
    protected final void onPostExecute(Void result) {
        Log.d("AuthenticationTask", "AuthenticationTask.onPostExecute");
    }

    @Override
    protected final void onCancelled() {
        Log.d("AuthenticationTask", "AuthenticationTask.onCancelled");
    }
}
