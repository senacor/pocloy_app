package de.senacor.bankathon.pocloy.authentication.dto;

public class UnpackStickerRequest {
    private String codeId;
    private Credentials credentials;

    public UnpackStickerRequest(){}
    
    public UnpackStickerRequest(String codeId, Credentials credentials) {
        this.codeId = codeId;
        this.credentials = credentials;
    }

    public String getCodeId() {
        return codeId;
    }

    public Credentials getCredentials() {
        return credentials;
    }
}
