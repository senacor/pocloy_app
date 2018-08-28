package de.senacor.bankathon.pocloy.authentication.framework;

import java.util.Collections;
import java.util.List;
import de.senacor.bankathon.pocloy.authentication.dto.UserAssets;
import de.senacor.bankathon.pocloy.authentication.dto.VoucherRedeemingData;

public class DataHolder {
    private static List<UserAssets> userAssets = Collections.emptyList();
    private static List<VoucherRedeemingData> voucherRedeemingData = Collections.emptyList();

    public static List<UserAssets> getUserAssets() {
        return userAssets;
    }

    public static void setUserAssets(List<UserAssets> userAssets) {
        DataHolder.userAssets = userAssets;
    }

    public static List<VoucherRedeemingData> getVoucherRedeemingData() {
        return voucherRedeemingData;
    }

    public static void setVoucherRedeemingData(List<VoucherRedeemingData> voucherRedeemingData) {
        DataHolder.voucherRedeemingData = voucherRedeemingData;
    }
}
