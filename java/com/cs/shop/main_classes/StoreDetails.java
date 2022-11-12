package com.cs.shop.main_classes;

public class StoreDetails {


    private int storeId;

    private double balance;

    private String location;

    private String phoneNumber;

    private String sellerName;

    private String storeName;

    public StoreDetails(int storeId, double balance, String location, String phoneNumber, String sellerName, String storeName) {
        this.storeId = storeId;
        this.balance = balance;
        this.location = location;
        this.phoneNumber = phoneNumber;
        this.sellerName = sellerName;
        this.storeName = storeName;
    }


    public int getStoreId(){return storeId;}

    public String getStoreName(){return storeName;}

    public String getSellerName(){return sellerName;}

    public double getBalance(){return balance;}

    public String getPhoneNumber(){return phoneNumber;}

    public String getLocation(){return location;}

    public void setSellerName(String sellerName) {
        this.sellerName = sellerName;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void deposit(double amount) {
        this.balance += amount;
    }

    public void withdraw(double amount) {
        this.balance -= amount;
    }

}
