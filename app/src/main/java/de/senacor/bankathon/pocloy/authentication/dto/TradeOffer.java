package de.senacor.bankathon.pocloy.authentication.dto;

import java.util.Objects;

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

    public TradeOffer(StickerResources offeredStickerType, int offeredStickerAmount, StickerResources requiredStickerType, int requiredStickerAmount) {
        this.offeredStickerType = offeredStickerType;
        this.offeredStickerAmount = offeredStickerAmount;
        this.requiredStickerType = requiredStickerType;
        this.requiredStickerAmount = requiredStickerAmount;
    }

    public long getExchangeOfferId() {
        return exchangeOfferId;
    }

    public void setExchangeOfferId(long exchangeOfferId) {
        this.exchangeOfferId = exchangeOfferId;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TradeOffer that = (TradeOffer) o;
        return exchangeOfferId == that.exchangeOfferId &&
                offeredStickerAmount == that.offeredStickerAmount &&
                requiredStickerAmount == that.requiredStickerAmount &&
                offeredStickerType == that.offeredStickerType &&
                requiredStickerType == that.requiredStickerType;
    }

    @Override
    public int hashCode() {
        return Objects.hash(exchangeOfferId, offeredStickerType, offeredStickerAmount, requiredStickerType, requiredStickerAmount);
    }
}
