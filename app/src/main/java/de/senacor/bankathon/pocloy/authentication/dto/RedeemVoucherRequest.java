package de.senacor.bankathon.pocloy.authentication.dto;

public class RedeemVoucherRequest {
    private String vocherId;
    private Credentials credentials;

    public RedeemVoucherRequest(){};

    public RedeemVoucherRequest(String vocherId, Credentials credentials) {
        this.vocherId = vocherId;
        this.credentials = credentials;
    }

    public String getVocherId() {
        return vocherId;
    }

    public void setVocherId(String vocherId) {
        this.vocherId = vocherId;
    }

    public Credentials getCredentials() {
        return credentials;
    }

    public void setCredentials(Credentials credentials) {
        this.credentials = credentials;
    }
}
