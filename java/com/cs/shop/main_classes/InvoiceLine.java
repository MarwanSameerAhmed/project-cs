package com.cs.shop.main_classes;

public class InvoiceLine {
    private final int invoiceLineId;
    private final int invoiceId;
    private final int unitId;
    private final int units;
    private final double unitPrice;

    public InvoiceLine(int invoiceLineId, int invoiceId, int unitId, int units, double unitPrice) {
        this.invoiceLineId = invoiceLineId;
        this.invoiceId = invoiceId;
        this.unitId = unitId;
        this.units = units;
        this.unitPrice = unitPrice;
    }

    public int getInvoiceLineId() {
        return invoiceLineId;
    }

    public int getInvoiceId() {
        return invoiceId;
    }

    public int getUnitId() {
        return unitId;
    }

    public int getUnits() {
        return units;
    }

    public double getUnitPrice() {
        return unitPrice;
    }
}
