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
    private static List<TradeOffer> tradeOffers = Arrays.asList(
            new TradeOffer(1, StickerResources.BOTTLE, 2, StickerResources.APPLE, 3),
            new TradeOffer(2, StickerResources.CAR, 1, StickerResources.CUP, 10),
            new TradeOffer(3, StickerResources.SILVERWARE, 2, StickerResources.SUN, 2),
            new TradeOffer(4, StickerResources.SILVERWARE, 5, StickerResources.GAS_STATION, 1),
            new TradeOffer(5, StickerResources.SUN, 1, StickerResources.SUNGLASSES, 2),
            new TradeOffer(6, StickerResources.HAMBURGER, 3, StickerResources.FOOD, 1),
            new TradeOffer(7, StickerResources.SUN, 1, StickerResources.SUNGLASSES, 2),
            new TradeOffer(8, StickerResources.PIZZA, 5, StickerResources.SUNGLASSES, 2),
            new TradeOffer(9, StickerResources.SILVERWARE, 10, StickerResources.CAR, 2),
            new TradeOffer(10, StickerResources.APPLE, 1, StickerResources.HAMBURGER, 1),
            new TradeOffer(11, StickerResources.BOTTLE, 1, StickerResources.APPLE, 2),
            new TradeOffer(12, StickerResources.CUP, 8, StickerResources.GAS_STATION, 5),
            new TradeOffer(13, StickerResources.GAS_STATION, 7, StickerResources.SUN, 4),
            new TradeOffer(14, StickerResources.HAMBURGER, 15, StickerResources.GAS_STATION, 2)
    );

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
