package de.senacor.bankathon.pocloy.authentication.task;

import android.os.AsyncTask;
import android.util.Log;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.web.client.HttpServerErrorException;
import de.senacor.bankathon.pocloy.authentication.dto.Credentials;
import de.senacor.bankathon.pocloy.authentication.dto.UnpackStickerRequest;
import de.senacor.bankathon.pocloy.authentication.dto.UnpackedSticker;
import de.senacor.bankathon.pocloy.authentication.framework.GsonRestTemplate;

public abstract class UnpackStickerTask extends AsyncTask<Void, Void, UnpackedSticker> {
    private final Credentials credentials;
    private final GsonRestTemplate restTemplate;
    private final UnpackStickerRequest unpackStickerRequest;
    private final String unpackStickerContentUri = "https://desolate-depths-64341.herokuapp.com/user/unpack";
    private Exception unpackStickerException;
    private String reason;

    public UnpackStickerTask(String codeId) {
        this.credentials = GsonRestTemplate.getCredentials();
        this.restTemplate = new GsonRestTemplate();
        this.unpackStickerRequest = new UnpackStickerRequest(codeId, credentials);
    }

    @Override
    protected final UnpackedSticker doInBackground(Void... params) {
        try {
            String jsonList = restTemplate.postForObject(unpackStickerContentUri, unpackStickerRequest, String.class);
            Gson gson = new GsonBuilder().setDateFormat("dd-MM-yyyy HH:mm:ss").create();
            UnpackedSticker unpackedSticker = gson.fromJson(jsonList, UnpackedSticker.class);
            return unpackedSticker;
        } catch (HttpServerErrorException e) {
            String reason = e.getResponseBodyAsString();
            this.unpackStickerException = e;
            this.reason = reason;
            Log.d("UnpackStickerTask.doInBackground", e.getMessage(), e);
        }
        return null;
    }

    @Override
    protected final void onPostExecute(UnpackedSticker result) {
        if (unpackStickerException == null) {
            handleSuccessfulUnpackOfSticker(result);
        } else {
            handleFailedUnpackOfSticker(reason);
        }
    }

    @Override
    protected final void onCancelled() {
        Log.d("AuthenticationTask", "AuthenticationTask.onCancelled");
    }

    protected abstract void handleSuccessfulUnpackOfSticker(UnpackedSticker unpackedSticker);

    protected abstract void handleFailedUnpackOfSticker(String reason);
}
