package com.cs.shop.main_classes;

public class ProductDetails {


    private int productId;

    private String image;

    private String name;

    private boolean negotiable;

    private final String number;

    private int units;


    public ProductDetails(int productId, String image, String name, boolean negotiable, String number, int units) {
        this.productId = productId;
        this.image = image;
        this.name = name;
        this.negotiable = negotiable;
        this.number = number;
        this.units = units;
    }

    public int getProductId(){return productId;}

    public String getName(){return name;}

    public String getNumber(){return number;}

    public String getImage(){return image;}

    public int getUnits(){return units;}

    public boolean getNegotiable(){return negotiable;}

    public void setName(String name) {
        this.name = name;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setNegotiable(boolean negotiable) {
        this.negotiable = negotiable;
    }

    public void addUnits(int units){
        this.units += units;
    }

    public void withdrawUnits(int units){
        this.units -= units;
    }

}
