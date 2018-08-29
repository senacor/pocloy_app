package de.senacor.bankathon.pocloy.authentication.dto;

public class TradeOffer {

    private long exchangeOfferId;

    private StickerResources offeredStickerType;
    private int offeredStickerAmount;

    private StickerResources requiredStickerType;
    private int requiredStickerAmount;

    public TradeOffer(long exchangeOfferId, StickerResources offeredStickerType, int offeredStickerAmount, StickerResources requiredStickerType, int requiredStickerAmount) {
        this.exchangeOfferId = exchangeOfferId;
        this.offeredStickerType = offeredStickerType;
        this.offeredStickerAmount = offeredStickerAmount;
        this.requiredStickerType = requiredStickerType;
        this.requiredStickerAmount = requiredStickerAmount;
    }

    public long getExchangeOfferId() {
        return exchangeOfferId;
    }

    public StickerResources getOfferedStickerType() {
        return offeredStickerType;
    }

    public int getOfferedStickerAmount() {
        return offeredStickerAmount;
    }

    public StickerResources getRequiredStickerType() {
        return requiredStickerType;
    }

    public int getRequiredStickerAmount() {
        return requiredStickerAmount;
    }
}
