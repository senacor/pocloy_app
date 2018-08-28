package de.senacor.bankathon.pocloy.authentication.task;

import android.os.AsyncTask;

public class AuthenticationTask extends AsyncTask<Void, Void, Void> {
    private final String mEmail;
    private final String mPassword;

    public AuthenticationTask(String email, String password) {
        mEmail = email;
        mPassword = password;
    }

    @Override
    protected final Void doInBackground(Void... params) {
        return null;
    }

    @Override
    protected final void onPostExecute(Void result) {
    }

    @Override
    protected final void onCancelled() {
    }
}
