package com.cs.shop.main_classes;


public class ClientDetails {
    private int clientId;
    private double balance;
    private String name;
    private String phoneNumber;

    public ClientDetails(int clientId, double balance, String name, String phoneNumber) {
        this.clientId = clientId;
        this.balance = balance;
        this.name = name;
        this.phoneNumber = phoneNumber;
    }


    public int getClientId() {
        return clientId;
    }

    public double getBalance() {
        return balance;
    }

    public String getName() {
        return name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void deposit(double amount){
        this.balance += amount;
    }

    public void withdraw(double amount){
        this.balance -= amount;
    }
}
