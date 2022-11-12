package com.cs.shop.main_classes;


public class UnitDetails {

    private int unitId;

    private int productId;

    private int conversion;

    private String expiryDate;

    private String name;

    private double price;

    private int warningExpiry;

    public UnitDetails(int unitId, int productId, int conversion, String expiryDate, String name, double price, int warningExpiry) {
        this.unitId = unitId;
        this.productId = productId;
        this.conversion = conversion;
        this.expiryDate = expiryDate;
        this.name = name;
        this.price = price;
        this.warningExpiry = warningExpiry;
    }

    public int getUnitId(){return unitId;}

    public int getProductId() {
        return productId;
    }

    public String getName(){return name;}

    public double getPrice(){return price;}

    public int getConversion(){return conversion;}

    public String getExpiryDate(){return expiryDate;}

    public int getWarningExpiry(){return warningExpiry;}

    public void setPrice(double price){
        this.price=price;
    }

    public void setConversion(int conversion) {
        this.conversion = conversion;
    }


}
