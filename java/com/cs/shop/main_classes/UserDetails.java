package com.cs.shop.main_classes;

public class UserDetails {


    private int userId;
    private int access;
    private double balance;
    private boolean enabled;
    private String name;
    private String password;
    private String phoneNumber;
    private String username;

    public UserDetails(int userId, int access, double balance, boolean enabled, String name, String password, String phoneNumber, String username) {
        this.userId = userId;
        this.access = access;
        this.balance = balance;
        this.enabled = enabled;
        this.name = name;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.username = username;
    }


    public int getUserId() {
        return this.userId;
    }

    public String getName() {
        return this.name;
    }

    public String getUsername() {
        return this.username;
    }

    public String getPassword() {
        return this.password;
    }

    public int getAccess() {
        return this.access;
    }

    public double getBalance() {
        return this.balance;
    }

    public String getPhoneNumber() {
        return this.phoneNumber;
    }

    public boolean getEnabled() {
        return this.enabled;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setAccess(int access) {
        this.access = access;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public void deposit(double amount) {
        this.balance += amount;
    }

    public void withdraw(double amount) {
        this.balance -= amount;
    }

}
