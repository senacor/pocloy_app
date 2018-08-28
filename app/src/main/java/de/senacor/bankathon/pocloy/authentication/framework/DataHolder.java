package de.senacor.bankathon.pocloy.authentication.framework;

import java.util.List;
import de.senacor.bankathon.pocloy.authentication.dto.UserAssets;

public class DataHolder {
    private static List<UserAssets> userAssets;

    public static List<UserAssets> getUserAssets() {
        return userAssets;
    }

    public static void setUserAssets(List<UserAssets> userAssets) {
        DataHolder.userAssets = userAssets;
    }
}
