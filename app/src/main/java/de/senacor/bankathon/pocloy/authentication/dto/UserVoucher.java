package de.senacor.bankathon.pocloy.authentication.dto;

public class UserVoucher {

    private final long id;
    private final int voucherId;
    private final String voucherName;

    public UserVoucher(long id, int voucherId, String voucherName) {
        this.id = id;
        this.voucherId = voucherId;
        this.voucherName = voucherName;
    }

    public long getId() {
        return id;
    }

    public int getVoucherId() {
        return voucherId;
    }

    public String getVoucherName() {
        return voucherName;
    }

}
