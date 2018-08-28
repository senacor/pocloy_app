package de.senacor.bankathon.pocloy.authentication.dto;

import android.util.Log;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Objects;
import java.util.stream.Stream;

import de.senacor.bankathon.pocloy.R;

public enum StickerResources {
    @SerializedName("bottle_wine")
    BOTTLE("bottle_wine", R.drawable.sticker_bottle_wine),
    @SerializedName("car_hatchback")
    CAR("car_hatchback", R.drawable.sticker_car_hatchback),
    @SerializedName("cup")
    CUP("cup", R.drawable.sticker_cup),
    @SerializedName("food")
    FOOD("food", R.drawable.sticker_food),
    @SerializedName("food_apple")
    APPLE("food_apple", R.drawable.sticker_food_apple),
    @SerializedName("gas_station")
    GAS_STATION("gas_station", R.drawable.sticker_gas_station),
    @SerializedName("hamburger")
    HAMBURGER("hamburger", R.drawable.sticker_hamburger),
    @SerializedName("pizza")
    PIZZA("pizza", R.drawable.sticker_pizza),
    @SerializedName("silverware_fork_knife")
    SILVERWARE("silverware_fork_knife", R.drawable.sticker_silverware_fork_knife),
    @SerializedName("sunglasses")
    SUNGLASSES("sunglasses", R.drawable.sticker_sunglasses),
    @SerializedName("white_balance_sunny")
    SUN("white_balance_sunny", R.drawable.sticker_white_balance_sunny),
    @SerializedName("unknown")
    UNKNOWN("unknown", R.drawable.gift),
    OTHER(null, R.drawable.sticker_alert_circle_outline);

    private String imageCode;
    @Expose(serialize = false, deserialize = false)
    private int imageReference;

    StickerResources(String imageCode, int imageReference) {
        this.imageCode = imageCode;
        this.imageReference = imageReference;
    }

    public String getImageCode() {
        return imageCode;
    }

    public int getImageReference() {
        return imageReference;
    }

    public static StickerResources forImageCode(String imageCode) {
        return Stream.of(values())
                .filter(stickerName -> Objects.equals(stickerName.imageCode, imageCode))
                .findFirst()
                .orElseGet(() -> {
                    Log.e("MyCollectionFragment", "Unknown sticker " + imageCode);
                    return OTHER;
                });
    }
}
