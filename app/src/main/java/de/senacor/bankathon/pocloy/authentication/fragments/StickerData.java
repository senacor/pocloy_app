package de.senacor.bankathon.pocloy.authentication.fragments;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import de.senacor.bankathon.pocloy.R;

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
    private final String stickerName;
    private final int stickerId;

    public StickerData(int amount, String stickerName) {
        this.amount = amount;
        this.stickerName = stickerName;
        switch (stickerName) {
            case "bottle_wine":
                this.stickerId = R.drawable.sticker_bottle_wine;
                return;
            case "car_hatchback":
                this.stickerId = R.drawable.sticker_car_hatchback;
                return;
            case "cup":
                this.stickerId = R.drawable.sticker_cup;
                return;
            case "food":
                this.stickerId = R.drawable.sticker_food;
                return;
            case "food_apple":
                this.stickerId = R.drawable.sticker_food_apple;
                return;
            case "gas_station":
                this.stickerId = R.drawable.sticker_gas_station;
                return;
            case "hamburger":
                this.stickerId = R.drawable.sticker_hamburger;
                return;
            case "pizza":
                this.stickerId = R.drawable.sticker_pizza;
                return;
            case "silverware_fork_knife":
                this.stickerId = R.drawable.sticker_silverware_fork_knife;
                return;
            case "sunglasses":
                this.stickerId = R.drawable.sticker_sunglasses;
                return;
            case "white_balance_sunny":
                this.stickerId = R.drawable.sticker_white_balance_sunny;
                return;
            case "unknown":
                this.stickerId = R.drawable.gift;
                return;
            default:
                Log.e("MyCollectionFragment", "Unknown sticker " + stickerName);
                this.stickerId = R.drawable.sticker_alert_circle_outline;
        }
    }

    public StickerData(Parcel parcel) {
        amount = parcel.readInt();
        stickerName = parcel.readString();
        stickerId = parcel.readInt();
    }

    public int getAmount() {
        return amount;
    }

    public String getStickerName() {
        return stickerName;
    }

    public int getStickerId() {
        return stickerId;
    }

    @Override
    public int describeContents() {
        return hashCode();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(amount);
        dest.writeString(stickerName);
        dest.writeInt(stickerId);
    }
}
