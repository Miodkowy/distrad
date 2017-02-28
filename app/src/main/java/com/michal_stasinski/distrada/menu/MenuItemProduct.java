package com.michal_stasinski.distrada.menu;

/**
 * Created by win8 on 27.12.2016.
 */

public class MenuItemProduct {

    private Number rank;
    private String name;
    private String desc;
    private Number price;

    public Number getPrice() {
        return price;
    }

    public void setPrice(Number price) {
        this.price = price;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getNameProduct() {

        return name;
    }

    public void setNameProduct(String nameProduct) {

        this.name = nameProduct;
    }

    public Number getRank() {

        return rank;
    }

    public void setRank(Number rank) {

        this.rank = rank;
    }
}
