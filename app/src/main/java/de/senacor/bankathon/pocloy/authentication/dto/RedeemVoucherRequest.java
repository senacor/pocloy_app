package de.senacor.bankathon.pocloy.authentication.dto;

public class RedeemVoucherRequest {
    private String voucherTypeId;
    private Credentials credentials;

    public RedeemVoucherRequest(){};

    public RedeemVoucherRequest(String voucherTypeId, Credentials credentials) {
        this.voucherTypeId = voucherTypeId;
        this.credentials = credentials;
    }

    public String getVoucherTypeId() {
        return voucherTypeId;
    }

    public void setVoucherTypeId(String voucherTypeId) {
        this.voucherTypeId = voucherTypeId;
    }

    public Credentials getCredentials() {
        return credentials;
    }

    public void setCredentials(Credentials credentials) {
        this.credentials = credentials;
    }
}
