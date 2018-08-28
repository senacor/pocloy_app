package de.senacor.bankathon.pocloy.authentication.task;

import android.os.AsyncTask;
import org.springframework.web.client.RestTemplate;
import de.senacor.bankathon.pocloy.authentication.dto.Credentials;

public class AuthenticationTask extends AsyncTask<Void, Void, Void> {
    private final Credentials credentials;
    private final RestTemplate restTemplate;
    //TODO: Specify
    private final String uri = "TheUriForRegistration";

    public AuthenticationTask(String email, String password) {
        this.credentials = new Credentials(email, password);
        this.restTemplate = new RestTemplate();
        
    }

    @Override
    protected final Void doInBackground(Void... params) {
        restTemplate.postForObject(uri, credentials, Void.class);
        return null;
    }

    @Override
    protected final void onPostExecute(Void result) {
        System.out.println("AuthenticationTask.onPostExecute");
    }

    @Override
    protected final void onCancelled() {
        System.out.println("Test");
    }
}
