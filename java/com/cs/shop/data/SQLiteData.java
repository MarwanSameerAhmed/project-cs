package com.cs.shop.data;

import static com.cs.shop.data.DataSetup.bti;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.cs.shop.main_classes.ClientDetails;

public class SQLiteData extends SQLiteOpenHelper {


    private static final int VERSION = 1;
    private static final String DATABASE_NAME = "shop_main.db";
    private static final SQLiteDatabase.CursorFactory FACTORY = null;

    private static final String TAG = "SQLiteData";

    /**
     * Structure Clients table
     */
    protected static final class Clients{
        public static final String T_CLIENTS = "clients";
        public static final String C_CLIENT_ID = "id";
        public static final String C_BALANCE = "balance";
        public static final String C_NAME = "name";
        public static final String C_PHONE_NUMBER = "phone_number";
    }

    /**
     * Structure Products table
     */
    protected static final class Products{
        public static final String T_PRODUCTS = "products";
        public static final String C_PRODUCT_ID = "id";
        public static final String C_IMAGE = "image";
        public static final String C_NAME = "name";
        public static final String C_NEGOTIABLE = "negotiable";
        public static final String C_NUMBER = "number";
        public static final String C_UNITS = "units";

    }

    /**
     * Structure InvoiceLine table
     */
    protected static final class InvoiceLine {
        public static final String T_INVOICE_LINE = "invoice_line";
        public static final String C_INVOICE_LINE_ID = "id";
        public static final String C_INVOICE_ID = "record_id";
        public static final String C_UNIT_ID = "unit_id";
        public static final String C_UNITS = "units";
        public static final String C_UNIT_PRICE = "unit_price";
    }

    /**
     * Structure Invoice table
     */
    protected static final class Invoice{
        public static final String T_INVOICE = "invoice";
        public static final String C_INVOICE_ID = "id";
        public static final String C_OTHER_SIDE_ID = "other_side_id";
        public static final String C_USER_ID = "user_id";
        public static final String C_CASH = "cash";
        public static final String C_DATE = "date";
        public static final String C_DEPT = "dept";
        public static final String C_STATUS = "status";
    }

    /**
     * Structure Stores table
     */
    protected static final class Stores{
        public static final String T_STORES = "stores";
        public static final String C_STORE_ID = "id";
        public static final String C_BALANCE = "balance";
        public static final String C_LOCATION = "location";
        public static final String C_PHONE_NUMBER = "phone_number";
        public static final String C_SELLER_NAME = "seller_name";
        public static final String C_STORE_NAME = "store_name";
    }

    /**
     * Structure Units table
     */
    protected static final class Units{
        public static final String T_UNITS = "units";
        public static final String C_UNIT_ID = "id";
        public static final String C_PRODUCT_ID = "product_id";
        public static final String C_CONVERSION = "conversion";
        public static final String C_EXPIRY_DATE = "expiry_date";
        public static final String C_NAME = "name";
        public static final String C_PRICE = "price";
        public static final String C_WARNING_EXPIRY = "warning_expiry";
    }

    /**
     * Structure User table
     */
    protected static final class Users{
        public static final String T_USERS = "users";
        public static final String C_USER_ID = "id";
        public static final String C_ACCESS = "access";
        public static final String C_BALANCE = "balance";
        public static final String C_ENABLE = "enable";
        public static final String C_NAME = "name";
        public static final String C_PASSWORD = "password";
        public static final String C_PHONE_NUMBER = "phone_number";
        public static final String C_USERNAME = "username";
    }

    protected SQLiteData(Context context) {
        super(context, DATABASE_NAME, FACTORY, VERSION);
    }
    /**
     * Create tables
     * @param db
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d(TAG, "onCreate: Clients table");
        db.execSQL("CREATE TABLE "+Clients.T_CLIENTS+"("
                +Clients.C_CLIENT_ID+" INTEGER PRIMARY KEY AUTOINCREMENT, "
                +Clients.C_BALANCE+" DOUBLE, "
                +Clients.C_NAME+" TEXT, "
                +Clients.C_PHONE_NUMBER+" TEXT"
                +")");

        Log.d(TAG, "onCreate: products table");
        db.execSQL("CREATE TABLE "+Products.T_PRODUCTS+"("
                +Products.C_PRODUCT_ID+" INTEGER PRIMARY KEY AUTOINCREMENT, "
                +Products.C_IMAGE+" TEXT, "
                +Products.C_NAME+" TEXT, "
                +Products.C_NEGOTIABLE+" INTEGER, "
                +Products.C_NUMBER+" TEXT, "
                +Products.C_UNITS+" INTEGER"
                +")");


        Log.d(TAG, "onCreate: invoice_line table");
        db.execSQL("CREATE TABLE "+ InvoiceLine.T_INVOICE_LINE +"("
                + InvoiceLine.C_INVOICE_LINE_ID +" INTEGER PRIMARY KEY AUTOINCREMENT, "
                + InvoiceLine.C_INVOICE_ID +" INTEGER, "
                + InvoiceLine.C_UNIT_ID+" INTEGER, "
                + InvoiceLine.C_UNITS+" INTEGER, "
                + InvoiceLine.C_UNIT_PRICE+" DOUBLE"
                +")");

        Log.d(TAG, "onCreate: invoice table");
        db.execSQL("CREATE TABLE "+ Invoice.T_INVOICE +"("
                + Invoice.C_INVOICE_ID +" INTEGER PRIMARY KEY AUTOINCREMENT, "
                + Invoice.C_OTHER_SIDE_ID+" INTEGER, "
                + Invoice.C_USER_ID+" INTEGER, "
                + Invoice.C_CASH+" DOUBLE, "
                + Invoice.C_DATE+" TEXT, "
                + Invoice.C_DEPT+" DOUBLE, "
                + Invoice.C_STATUS+" INTEGER"
                +")");

        Log.d(TAG, "onCreate: stores table");
        db.execSQL("CREATE TABLE "+Stores.T_STORES+"("
                +Stores.C_STORE_ID+" INTEGER PRIMARY KEY AUTOINCREMENT, "
                +Stores.C_BALANCE+" DOUBLE, "
                +Stores.C_LOCATION+" TEXT, "
                +Stores.C_PHONE_NUMBER+" TEXT, "
                +Stores.C_SELLER_NAME+" TEXT, "
                +Stores.C_STORE_NAME+" TEXT"
                +")");

        Log.d(TAG, "onCreate: units table");
        db.execSQL("CREATE TABLE "+Units.T_UNITS+"("
                +Units.C_UNIT_ID+" INTEGER PRIMARY KEY AUTOINCREMENT, "
                +Units.C_PRODUCT_ID+" INTEGER, "
                +Units.C_CONVERSION+" INTEGER, "
                +Units.C_EXPIRY_DATE+" TEXT, "
                +Units.C_NAME+" TEXT, "
                +Units.C_PRICE+" DOUBLE, "
                +Units.C_WARNING_EXPIRY+" INTEGER"
                +")");

        Log.d(TAG, "onCreate: users table");
        db.execSQL("CREATE TABLE "+Users.T_USERS+"("
                +Users.C_USER_ID+" INTEGER PRIMARY KEY AUTOINCREMENT, "
                +Users.C_ACCESS+" INTEGER, "
                +Users.C_BALANCE+" DOUBLE, "
                +Users.C_ENABLE+" INTEGER, "
                +Users.C_NAME+" TEXT, "
                +Users.C_PASSWORD+" TEXT, "
                +Users.C_PHONE_NUMBER+" TEXT, "
                +Users.C_USERNAME+" TEXT"
                +")");

        ContentValues values = new ContentValues();
        values.put(Clients.C_BALANCE, 0);
        values.put(Clients.C_NAME, "عميل");
        values.put(Clients.C_PHONE_NUMBER, "X");
        db.insert(Clients.T_CLIENTS, null, values);

        values = new ContentValues();
        values.put(Users.C_NAME, "Admin");
        values.put(Users.C_USERNAME, "admin");
        values.put(Users.C_PASSWORD, "1dmin");
        values.put(Users.C_ACCESS, "4");
        values.put(Users.C_BALANCE, 0);
        values.put(Users.C_PHONE_NUMBER, "X");
        values.put(Users.C_ENABLE, bti(true));
        db.insert(Users.T_USERS, null, values);

        values = new ContentValues();
        values.put(Stores.C_STORE_NAME, "مورد عام");
        values.put(Stores.C_SELLER_NAME, "X");
        values.put(Stores.C_BALANCE, "0");
        values.put(Stores.C_PHONE_NUMBER, "X");
        values.put(Stores.C_LOCATION, "X");
        db.insert(Stores.T_STORES, null, values);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {

    }

    protected Cursor getData(String tableName){
        Log.d(TAG, "getData: Select data from "+tableName);
        checkTableName(tableName);

        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + tableName, null);
    }


    protected long insert(ContentValues values, String tableName){
        Log.d(TAG, "insert: Insert into "+tableName);
        checkTableName(tableName);

        SQLiteDatabase db = this.getWritableDatabase();
        return db.insert(tableName, null, values);
    }

    protected boolean update(ContentValues values, int id, String tableName){
        Log.d(TAG, "update: Update data in"+tableName);
        checkTableName(tableName);

        SQLiteDatabase db = this.getWritableDatabase();
        long result = db.update(tableName, values, " id = ?", new String[]{ String.valueOf(id)});
        return result != -1;
    }

    protected boolean delete(int id, String tableName){
        Log.d(TAG, "delete: Delete data in "+tableName);
        checkTableName(tableName);

        SQLiteDatabase db = this.getWritableDatabase();
        long result = db.delete(tableName, " id = ?", new String[]{ String.valueOf(id)});
        return result != -1;
    }

    private void checkTableName(String tableName){
        Log.d(TAG, "checkTableName: "+tableName);
        boolean checkTableName = !tableName.equals(Clients.T_CLIENTS) && !tableName.equals(Products.T_PRODUCTS) && !tableName.equals(InvoiceLine.T_INVOICE_LINE)
                && !tableName.equals(Invoice.T_INVOICE) && !tableName.equals(Stores.T_STORES) && !tableName.equals(Units.T_UNITS) && !tableName.equals(Users.T_USERS);
        if (checkTableName)
            throw new IllegalArgumentException("This table is not identified");
    }
}