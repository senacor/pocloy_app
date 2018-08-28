package de.senacor.bankathon.pocloy.authentication.dto;

import java.util.List;
import java.util.stream.Collectors;

public class UserAssetsList {
    private List<UserAssets> userAssetsList;

    public UserAssetsList() {

    }

    public List<UserAssets> getUserAssetsList() {
        return userAssetsList;
    }

    public void setUserAssetsList(List<UserAssets> userAssetsList) {
        this.userAssetsList = userAssetsList;
    }

    @Override
    public String toString() {
        if (userAssetsList != null) {
            return "UserAssetsList{" +
                    "userAssetsList=" + userAssetsList.stream().map(UserAssets::toString).collect(Collectors.joining(", ")) +
                    '}';
        } else {
            return "UserAssetsList{" +
                    "userAssetsList=null"  +
                    '}';
        }
    }
}
