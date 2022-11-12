package com.cs.shop.main_classes;

import android.content.Context;

import com.cs.shop.Interfaces.Filter;
import com.cs.shop.data.DataSetup;


import java.util.ArrayList;

/**
 * This class manages invoices.
 * This class recalls and delete invoices or invoice liens by class com.cs.shop.data.DataSetup.
 * Can't edit invoice or invoice line.
 * Design by Abdullah Al-Sayyad
 */
public class ManageInvoice extends DataSetup {

    /**
     * This constant is used in the status of supply
     */
    public static final int S_ADD = 1;
    /**
     * This constant is used in the status of buy
     */
    public static final int S_WITHDRAW = 2;


    private ArrayList<InvoiceDetails> invoices;
    private ArrayList<com.cs.shop.main_classes.InvoiceLine> lines;

    /**
     * When initializing the object, it calls the data.
     * @param context activity's context
     */
    public ManageInvoice(Context context) {
        super(context);
        this.invoices = super.invoices();
        this.lines = super.lines();
    }

    /**
     * This method returns all invoices
     * @return ArrayList of invoices type of com.cs.shop.main_classes.InvoiceDetails.
     */
    public ArrayList<InvoiceDetails> getInvoices() {
        return invoices;
    }

    /**
     * This method returns invoices according to the filter sent.
     * @param filter it's object type of com.cs.interfaces.Filter.Invoice.
     * @return ArrayList of invoices type of com.cs.shop.main_classes.InvoiceDetails.
     */
    public ArrayList<InvoiceDetails> getInvoices(Filter.Invoice filter) {
        filter.sort(this.invoices);
        return invoices;
    }

    /**
     * This method searches invoice by invoiceId and if not found it returns null.
     * @param invoiceId id of invoice.
     * @return if found it return object type of com.cs.shop.main_classes.InvoiceDetails else returns null.
     */
    public InvoiceDetails searchInvoice(int invoiceId) {
        for (InvoiceDetails invoice : invoices)
            if (invoice.getInvoiceId() == invoiceId)
                return invoice;
        return null;
    }

    /**
     * This method searches invoice line by invoiceLineId and if not found it returns null.
     * @param invoiceLineId id of invoice line.
     * @return if found it return object type of com.cs.shop.main_classes.InvoiceDetails else returns null.
     */
    public com.cs.shop.main_classes.InvoiceLine searchInvoiceLine(int invoiceLineId){
        for (com.cs.shop.main_classes.InvoiceLine line : lines)
            if (line.getInvoiceLineId() == invoiceLineId)
                return line;
        return null;
    }

    /**
     * This methode returns invoice lines by invoiceId.
     * @param invoiceId Id of the invoice owner of invoice liens
     * @return ArrayList of invoice lines type of com.cs.shop.main_classes.InvoiceLine.
     */
    public ArrayList<com.cs.shop.main_classes.InvoiceLine> getInvoiceLines(int invoiceId) {
        ArrayList<com.cs.shop.main_classes.InvoiceLine> lines = new ArrayList<>();
        for (com.cs.shop.main_classes.InvoiceLine line : this.lines)
            if (line.getInvoiceId() == invoiceId)
                lines.add(line);
        return lines;
    }

    /**
     * This methode returns invoice lines by invoiceId and according to the filter sent.
     * @param invoiceId Id of the invoice owner of invoice liens
     * @param filter it's object type of com.cs.interfaces.Filter.InvoiceLine.
     * @return ArrayList of invoice lines type of com.cs.shop.main_classes.InvoiceLine.
     */
    public ArrayList<com.cs.shop.main_classes.InvoiceLine> getInvoiceLines(int invoiceId, Filter.InvoiceLiens filter) {
        ArrayList<com.cs.shop.main_classes.InvoiceLine> lines = new ArrayList<>();
        for (com.cs.shop.main_classes.InvoiceLine line : this.lines)
            if (line.getInvoiceId() == invoiceId)
                lines.add(line);
        filter.sort(lines);
        return lines;
    }

    /**
     * This method for delete invoice.
     * When delete all invoice lines belonging to it will be deleted automatically.
     * @param invoice Invoice to be deleted.
     * @return true if the operation is successful else false
     */
    public boolean deleteInvoice(InvoiceDetails invoice){
        for (com.cs.shop.main_classes.InvoiceLine line : getInvoiceLines(invoice.getInvoiceId()))
            deleteInvoiceLine(line);
        boolean result = super.delete(invoice);
        refresh();
        return result;
    }

    /**
     * This method for delete invoice line.
     * @param invoiceLine Invoice line to be deleted.
     * @return true if the operation is successful else false
     */
    public boolean deleteInvoiceLine(com.cs.shop.main_classes.InvoiceLine invoiceLine){
        return super.delete(invoiceLine);
    }

    /**
     * Refresh the data.
     */
    public void refresh(){
        this.invoices = super.invoices();
        this.lines = super.lines();
    }

}
