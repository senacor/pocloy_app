package de.senacor.bankathon.pocloy.authentication.framework;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import de.senacor.bankathon.pocloy.authentication.dto.StickerResources;
import de.senacor.bankathon.pocloy.authentication.dto.TradeOffer;
import de.senacor.bankathon.pocloy.authentication.dto.UserAssets;
import de.senacor.bankathon.pocloy.authentication.dto.UserVoucher;
import de.senacor.bankathon.pocloy.authentication.dto.VoucherRedeemingData;

public class DataHolder {
    private static List<UserAssets> userAssets = Collections.emptyList();
    private static List<VoucherRedeemingData> voucherRedeemingData = Collections.emptyList();
    private static List<UserVoucher> userVouchers = Collections.emptyList();
    private static List<TradeOffer> tradeOffers = Collections.emptyList();

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

    public static List<UserVoucher> getUserVouchers() {
        return userVouchers;
    }

    public static void setUserVouchers(List<UserVoucher> userVouchers) {
        DataHolder.userVouchers = userVouchers;
    }

    public static void setTradeOffers(List<TradeOffer> tradeOffers) {
        DataHolder.tradeOffers = tradeOffers;
    }

    public static List<TradeOffer> getTradeOffers() {
        return tradeOffers;
    }
}
