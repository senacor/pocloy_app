package de.senacor.bankathon.pocloy.authentication.task;

import android.os.AsyncTask;
import android.util.Log;
import com.google.gson.Gson;
import de.senacor.bankathon.pocloy.authentication.dto.Credentials;
import de.senacor.bankathon.pocloy.authentication.dto.UnpackStickerRequest;
import de.senacor.bankathon.pocloy.authentication.dto.UserAssets;
import de.senacor.bankathon.pocloy.authentication.framework.GsonRestTemplate;

public abstract class UnpackStickerTask extends AsyncTask<Void, Void, Void> {
    private final Credentials credentials;
    private final GsonRestTemplate restTemplate;
    private final UnpackStickerRequest unpackStickerRequest;
    private final String unpackStickerContentUri = "https://desolate-depths-64341.herokuapp.com/user/unpack";
    private Exception unpackStickerException;

    public UnpackStickerTask(String codeId) {
        this.credentials = GsonRestTemplate.getCredentials();
        this.restTemplate = new GsonRestTemplate();
        this.unpackStickerRequest = new UnpackStickerRequest(codeId, credentials);
    }

    @Override
    protected final Void doInBackground(Void... params) {
        try {
            String jsonList = restTemplate.postForObject(unpackStickerContentUri, unpackStickerRequest, String.class);
            Gson gson = new Gson();
            UserAssets[] userAssets = gson.fromJson(jsonList, UserAssets[].class);
            return null;
        } catch (Exception e) {
            this.unpackStickerException = e;
            Log.d("AuthenticationTask.doInBackground", e.getMessage(), e);
        }
        return null;
    }

    @Override
    protected final void onPostExecute(Void result) {
        if (unpackStickerException == null) {
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
