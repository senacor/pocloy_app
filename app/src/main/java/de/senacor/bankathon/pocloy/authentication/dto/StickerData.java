package de.senacor.bankathon.pocloy.authentication.dto;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import java.util.stream.Stream;

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
        return stickerResources.imageCode;
    }

    public int getStickerId() {
        return stickerResources.imageReference;
    }

    @Override
    public int describeContents() {
        return hashCode();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(amount);
        dest.writeString(stickerResources.imageCode);
    }

    private enum StickerResources {
        BOTTLE("bottle_wine", R.drawable.sticker_bottle_wine),
        CAR("car_hatchback", R.drawable.sticker_car_hatchback),
        CUP("cup", R.drawable.sticker_cup),
        FOOD("food", R.drawable.sticker_food),
        APPLE("food_apple", R.drawable.sticker_food_apple),
        GAS_STATION("gas_station", R.drawable.sticker_gas_station),
        HAMBURGER("hamburger", R.drawable.sticker_hamburger),
        PIZZA("pizza", R.drawable.sticker_pizza),
        SILVERWARE("silverware_fork_knife", R.drawable.sticker_silverware_fork_knife),
        SUNGLASSES("sunglasses", R.drawable.sticker_sunglasses),
        SUN("white_balance_sunny", R.drawable.sticker_white_balance_sunny),
        UNKNOWN("unknown", R.drawable.gift),
        OTHER(null, R.drawable.sticker_alert_circle_outline);

        private String imageCode;
        private int imageReference;

        StickerResources(String imageCode, int imageReference) {
            this.imageCode = imageCode;
            this.imageReference = imageReference;
        }

        static StickerResources forImageCode(String imageCode) {
            return Stream.of(values())
                    .filter(stickerName -> stickerName.imageCode.equals(imageCode))
                    .findFirst()
                    .orElseGet(() -> {
                        Log.e("MyCollectionFragment", "Unknown sticker " + imageCode);
                        return OTHER;
                    });
        }
    }
}
