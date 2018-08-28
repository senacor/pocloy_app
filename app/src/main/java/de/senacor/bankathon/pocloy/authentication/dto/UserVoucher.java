package de.senacor.bankathon.pocloy.authentication.dto;

public class UserVoucher {

    private final long id;
    private final int voucherTypeId;
    private final String voucherTypeName;

    public UserVoucher(long id, int voucherTypeId, String voucherTypeName) {
        this.id = id;
        this.voucherTypeId = voucherTypeId;
        this.voucherTypeName = voucherTypeName;
    }

    public long getId() {
        return id;
    }

    public int getVoucherTypeId() {
        return voucherTypeId;
    }

    public String getVoucherTypeName() {
        return voucherTypeName;
    }

}
