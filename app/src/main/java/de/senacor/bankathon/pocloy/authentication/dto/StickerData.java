package de.senacor.bankathon.pocloy.authentication.dto;

import android.os.Parcel;
import android.os.Parcelable;

public class StickerData implements Parcelable {

    public static final Creator<StickerData> CREATOR = new Creator<StickerData>() {
        @Override
        public StickerData createFromParcel(Parcel in) {
            return new StickerData(in);
        }

        @Override
        public StickerData[] newArray(int size) {
            return new StickerData[size];
        }
    };

    private final int amount;
    private final StickerResources stickerResources;

    public StickerData(int amount, String stickerName) {
        this.amount = amount;
        this.stickerResources = StickerResources.forImageCode(stickerName);
    }

    public StickerData(Parcel parcel) {
        this.amount = parcel.readInt();
        this.stickerResources = StickerResources.forImageCode(parcel.readString());
    }

    public int getAmount() {
        return amount;
    }

    public String getStickerName() {
        return stickerResources.getImageCode();
    }

    public int getStickerId() {
        return stickerResources.getImageReference();
    }

    @Override
    public int describeContents() {
        return hashCode();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(amount);
        dest.writeString(stickerResources.getImageCode());
    }

}
