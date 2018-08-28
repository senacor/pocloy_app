package de.senacor.bankathon.pocloy.authentication.dto;

import java.util.List;

public class UserAssetsList {
    private List<UserAssets> userAssetsList;
    
    public UserAssetsList(){
        
    }

    public List<UserAssets> getUserAssetsList() {
        return userAssetsList;
    }

    public void setUserAssetsList(List<UserAssets> userAssetsList) {
        this.userAssetsList = userAssetsList;
    }
}
