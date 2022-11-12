package com.cs.shop.data;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.cs.shop.main_classes.*;
import java.util.ArrayList;

public class DataSetup extends SQLiteData{


    protected DataSetup(Context context) {
        super(context);
    }

    private ContentValues valuesOfObject(Object object){
        ContentValues values = new ContentValues();

        if (object instanceof ProductDetails){
            ProductDetails product = (ProductDetails) object;
            values.put(Products.C_IMAGE, product.getImage());
            values.put(Products.C_NAME, product.getName());
            values.put(Products.C_NEGOTIABLE, bti(product.getNegotiable()));
            values.put(Products.C_NUMBER, product.getNumber());
            values.put(Products.C_UNITS, product.getUnits());
            return values;
        }
        if (object instanceof UnitDetails){
            UnitDetails unit = (UnitDetails) object;
            values.put(Units.C_PRODUCT_ID, unit.getProductId());
            values.put(Units.C_NAME, unit.getName());
            values.put(Units.C_PRICE, unit.getPrice());
            values.put(Units.C_CONVERSION, unit.getConversion());
            values.put(Units.C_EXPIRY_DATE, unit.getExpiryDate());
            values.put(Units.C_WARNING_EXPIRY, unit.getWarningExpiry());
            return values;
        }
        if (object instanceof StoreDetails){
            StoreDetails store = (StoreDetails) object;
            values.put(Stores.C_STORE_NAME, store.getStoreName());
            values.put(Stores.C_SELLER_NAME, store.getSellerName());
            values.put(Stores.C_BALANCE, store.getBalance());
            values.put(Stores.C_PHONE_NUMBER, store.getPhoneNumber());
            values.put(Stores.C_LOCATION, store.getLocation());
            return values;
        }
        if (object instanceof InvoiceDetails){
            InvoiceDetails invoice = (InvoiceDetails) object;
            values.put(Invoice.C_OTHER_SIDE_ID, invoice.getOtherSideId());
            values.put(Invoice.C_USER_ID, invoice.getUserId());
            values.put(Invoice.C_CASH, invoice.getCash());
            values.put(Invoice.C_DATE, invoice.getDate());
            values.put(Invoice.C_DEPT, invoice.getDept());
            values.put(Invoice.C_STATUS, invoice.getStatus());
            return values;
        }
        if (object instanceof UserDetails){
            UserDetails user = (UserDetails) object;
            values.put(Users.C_NAME, user.getName());
            values.put(Users.C_USERNAME, user.getUsername());
            values.put(Users.C_PASSWORD, user.getPassword());
            values.put(Users.C_ACCESS, user.getAccess());
            values.put(Users.C_BALANCE, user.getBalance());
            values.put(Users.C_PHONE_NUMBER, user.getPhoneNumber());
            values.put(Users.C_ENABLE, bti(user.getEnabled()));
            return values;
        }
        if (object instanceof ClientDetails){
            ClientDetails client = (ClientDetails) object;
            values.put(Clients.C_BALANCE, client.getBalance());
            values.put(Clients.C_NAME, client.getName());
            values.put(Clients.C_PHONE_NUMBER, client.getPhoneNumber());
            return values;
        }
        throw new IllegalArgumentException("DataSetup : valuesOfObject the object not undefined");
    }

    /**
     * Used to add a new product.
     * Receive the Product that will be added
     * @param product Receive parameter type of main_classes.ProductDetails
     * @return Return True if added or False if not added
     */
    protected long insert(ProductDetails product){
        return super.insert(valuesOfObject(product), Products.T_PRODUCTS);
    }

    /**
     * Used to add a new unit.
     * Receive the item that will be added
     * @param unit Receive parameter type of c.c.Product.UnitItem
     * @return Return True if added or False if not added
     */
    protected boolean insert(UnitDetails unit){
        return super.insert(valuesOfObject(unit), Units.T_UNITS) != -1;
    }

    /**
     *  Used to add a new store.
     *  Receive the item that will be added
     * @param store Receive parameter type of c.c.Store.Item
     * @return Return True if added or False if not added
     */
    protected boolean insert(StoreDetails store){
        return super.insert(valuesOfObject(store), Stores.T_STORES) != -1;
    }

    protected boolean insert(InvoiceDetails invoice, ArrayList<com.cs.shop.main_classes.InvoiceLine> lines){
        ContentValues values = new ContentValues();
        long invoiceId = super.insert(valuesOfObject(invoice), Invoice.T_INVOICE);
        if (invoiceId == -1) return false;
        for (com.cs.shop.main_classes.InvoiceLine line : lines){
            values = new ContentValues();
            values.put(InvoiceLine.C_INVOICE_ID, invoiceId);
            values.put(InvoiceLine.C_UNIT_ID, line.getUnitId());
            values.put(InvoiceLine.C_UNITS, line.getUnits());
            values.put(InvoiceLine.C_UNIT_PRICE, line.getUnitPrice());
            super.insert(values, InvoiceLine.T_INVOICE_LINE);
        }
        return true;
    }

    /**
     *  Used to add a new user.
     *  Receive the user that will be added
     * @param user
     * @return
     */
    protected boolean insert(UserDetails user){
        return super.insert(valuesOfObject(user), Users.T_USERS) != -1;
    }

    protected boolean insert(ClientDetails client){
        ContentValues values = new ContentValues();
        values.put(Clients.C_BALANCE, client.getBalance());
        values.put(Clients.C_NAME, client.getName());
        values.put(Clients.C_PHONE_NUMBER, client.getPhoneNumber());
        return super.insert(values, Clients.T_CLIENTS) != -1;
    }

    /**
     * Used to edit product.
     * Receive the item that will be edited
     * @param product Receive parameter type of c.c.Product.ProductItem.
     * @return True if edited or False if not edited
     */
    protected boolean update(ProductDetails product){
        return super.update(valuesOfObject(product),product.getProductId(), Products.T_PRODUCTS);
    }

    /**
     * Used to edit unit.
     * Receive the item that will be edited
     * @param unit Receive parameter type of c.c.Product.UnitItem
     * @return True if edited or False if not edited
     */
    protected boolean update(UnitDetails unit){
        return super.update(valuesOfObject(unit), unit.getUnitId(), Units.T_UNITS);
    }

    /**
     * Used to edit store.
     * Receive the item that will be edited
     * @param store Receive parameter type of c.c.Store.Item
     * @return True if edited or False if not edited
     */
    protected boolean update(StoreDetails store){
        return super.update(valuesOfObject(store), store.getStoreId(), Stores.T_STORES);
    }

    protected boolean update(UserDetails user){
        return super.update(valuesOfObject(user), user.getUserId(), Users.T_USERS);
    }

    protected boolean update(ClientDetails client){
        return super.update(valuesOfObject(client), client.getClientId(), Clients.T_CLIENTS);
    }

    /**
     * Used to delete product.
     * Receive the item that will be deleted
     * @param product Receive parameter type of c.c.Product.ProductItem
     * @return True if deleted or False if not deleted
     */
    protected boolean delete(ProductDetails product){
        return super.delete(product.getProductId(), Products.T_PRODUCTS);
    }

    /**
     * Used to delete unit.
     * Receive the item that will be deleted
     * @param unit Receive parameter type of c.c.Product.UnitItem
     * @return True if deleted or False if not deleted
     */
    protected boolean delete(UnitDetails unit){
        return super.delete(unit.getUnitId(), Units.T_UNITS);
    }

    /**
     * Used to delete store.
     * Receive the item that will be deleted
     * @param store Receive parameter type of c.c.Store.Item
     * @return True if deleted or False if not deleted
     */
    protected boolean delete(StoreDetails store){
        return super.delete(store.getStoreId(), Stores.T_STORES);
    }

    protected  boolean delete(UserDetails user){
        return super.delete(user.getUserId(), Users.T_USERS);
    }

    protected boolean delete(InvoiceDetails invoice){
        return super.delete(invoice.getInvoiceId(), Invoice.T_INVOICE);
    }

    protected boolean delete(com.cs.shop.main_classes.InvoiceLine invoice){
        return super.delete(invoice.getInvoiceLineId(), InvoiceLine.T_INVOICE_LINE);
    }

    protected boolean delete(ClientDetails client){
        return super.delete(client.getClientId(), Clients.T_CLIENTS);
    }


    /**
     * Used to get all products.
     * @return List type of ArrayList<c.c.Product.ProductItem>
     */
    protected ArrayList<ProductDetails> products() {
        Cursor data = super.getData(Products.T_PRODUCTS);
        ArrayList<ProductDetails> products = new ArrayList<>();

        if (data.getCount() <= 0) return products;

        data.moveToFirst();
        while (!data.isAfterLast()) {
            products.add(new ProductDetails(data.getInt(0), data.getString(1), data.getString(2), DataSetup.itb(data.getInt(3)), data.getString(4), data.getInt(5)));
            data.moveToNext();
        }

        return products;
    }

    /**
     * Used to get all units.
     * @return List type of ArrayList<c.c.Product.UnitItem>
     */
    protected ArrayList<UnitDetails> units() {
        Cursor data = super.getData(Units.T_UNITS);
        ArrayList<UnitDetails> units = new ArrayList<>();

        if (data.getCount() <= 0) return units;

        data.moveToFirst();
        while (!data.isAfterLast()) {
            units.add( new UnitDetails(data.getInt(0), data.getInt(1), data.getInt(2), data.getString(3), data.getString(4), data.getDouble(5), data.getInt(6)));
            data.moveToNext();
        }

        return units;
    }

    /**
     * Used to get all stores.
     * @return List type of ArrayList<c.c.Store.Item>
     */
    protected  ArrayList<StoreDetails> stores() {
        Cursor data = super.getData(Stores.T_STORES);
        ArrayList<StoreDetails> stores = new ArrayList<>();

        if (data.getCount() <= 0) return stores;

        data.moveToFirst();
        while (!data.isAfterLast()) {
            stores.add( new StoreDetails(data.getInt(0), data.getDouble(1), data.getString(2), data.getString(3), data.getString(4), data.getString(5)));
            data.moveToNext();
        }

        return stores;
    }

    protected ArrayList<UserDetails> users() {
        Cursor data = super.getData(Users.T_USERS);
        ArrayList<UserDetails> users = new ArrayList<>();

        if (data.getCount() <= 0) return users;

        data.moveToFirst();
        while (!data.isAfterLast()) {
            users.add( new UserDetails(data.getInt(0), data.getInt(1), data.getDouble(2), DataSetup.itb(data.getInt(3)), data.getString(4), data.getString(5), data.getString(6), data.getString(7)));
            data.moveToNext();
        }

        return users;
    }

    protected ArrayList<InvoiceDetails> invoices(){
        Cursor data = super.getData(Invoice.T_INVOICE);
        ArrayList<InvoiceDetails> invoices = new ArrayList<>();

        if(data.getCount() <= 0) return invoices;

        data.moveToFirst();
        while (!data.isAfterLast()) {
            invoices.add(new InvoiceDetails(data.getInt(0), data.getInt(1), data.getInt(2), data.getDouble(3), data.getString(4), data.getDouble(5), data.getInt(6)));
            data.moveToNext();
        }

        return invoices;
    }

    public ArrayList<com.cs.shop.main_classes.InvoiceLine> lines(){
        Cursor data = super.getData(InvoiceLine.T_INVOICE_LINE);
        ArrayList<com.cs.shop.main_classes.InvoiceLine> lines = new ArrayList<>();

        if(data.getCount() <= 0) return lines;

        data.moveToFirst();
        while (!data.isAfterLast()) {
            lines.add(new com.cs.shop.main_classes.InvoiceLine(data.getInt(0), data.getInt(1), data.getInt(2), data.getInt(3), data.getDouble(4)));
            data.moveToNext();
        }

        return lines;
    }

    protected ArrayList<ClientDetails> clients(){
        Cursor data = super.getData(Clients.T_CLIENTS);
        ArrayList<ClientDetails> clients = new ArrayList<>();

        if(data.getCount() <= 0) return clients;

        data.moveToFirst();
        while (!data.isAfterLast()) {
            clients.add(new ClientDetails(data.getInt(0), data.getDouble(1), data.getString(2), data.getString(3)));
            data.moveToNext();
        }

        return clients;
    }

    public static int bti(boolean x){
        return (x)?1:0;
    }

    public static boolean itb(int x){
        return x==1;
    }
}