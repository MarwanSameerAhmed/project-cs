package com.cs.shop.main_classes;

public class InvoiceDetails {
    private int invoiceId;
    private int userId;
    private int otherSideId;
    private int status;
    private double dept;
    private double cash;
    private String date;

    public InvoiceDetails(int invoiceId, int otherSideId, int userId, double cash, String date, double dept, int status) {
        this.invoiceId = invoiceId;
        this.userId = userId;
        this.otherSideId = otherSideId;
        this.status = status;
        this.dept = dept;
        this.cash = cash;
        this.date = date;
    }

    public int getInvoiceId() {
        return invoiceId;
    }

    public int getUserId() {
        return userId;
    }

    public int getOtherSideId() {
        return otherSideId;
    }

    public int getStatus() {
        return status;
    }

    public double getDept() {
        return dept;
    }

    public double getCash() {
        return cash;
    }

    public String getDate() {
        return date;
    }
}
