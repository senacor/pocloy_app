package de.senacor.bankathon.pocloy.authentication.dto;

import java.util.Map;

public class VoucherRedeemingData {
    private int id;
    private String name;
    private Map<StickerResources, Integer> price;

    public VoucherRedeemingData(int id, String name, Map<StickerResources, Integer> price) {
        this.id = id;
        this.name = name;
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Map<StickerResources, Integer> getPrice() {
        return price;
    }

    public void setPrice(Map<StickerResources, Integer> price) {
        this.price = price;
    }
}
