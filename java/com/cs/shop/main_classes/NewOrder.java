package com.cs.shop.main_classes;

import android.content.Context;

import com.cs.shop.Interfaces.Filter;
import com.cs.shop.data.DataSetup;

import java.util.ArrayList;

public class NewOrder extends DataSetup {

    public static int NOT_ERROR = -1;
    public static int ERROR_TOTAL_NOT_MATCH = -2;
    public static int ERROR = -3;
    public static int UNIT_NOT_EXIST = -4;

    public static String DATE_FORMAT = "yyyy-MM-dd HH:mm";

    private int status;
    private Context context;
    private double total;
    private ManageProduct demoData;
    private ArrayList<com.cs.shop.main_classes.InvoiceLine> lines;

    public NewOrder(Context context, int status){
        super(context);
        this.context = context;
        total = 0;
        lines = new ArrayList<>();
        demoData = new ManageProduct(context);
        this.status = status;
    }

    public ArrayList<com.cs.shop.main_classes.InvoiceLine> getLines() {
        return lines;
    }

    public ArrayList<ProductDetails> getProducts() {
        return demoData.getProducts();
    }

    public ArrayList<ProductDetails> getProducts(Filter.Product filter) {
        return demoData.getProducts(filter);
    }

    public ProductDetails searchProduct(int productId){
        return demoData.searchProduct(productId);
    }

    public ProductDetails searchProduct(String productNumber){
        return demoData.searchProduct(productNumber);
    }

    public ArrayList<UnitDetails> getProductUnits(int productId) {
       return demoData.getProductUnits(productId);
    }

    public ArrayList<UnitDetails> getProductUnits(int productId, Filter.Units filter) {

        return demoData.getProductUnits(productId, filter);
    }

    public UnitDetails searchUnit(int unitId){
        return demoData.searchUnit(unitId);
    }

    public int addOrder(int orderId, int unitId, int numberOfUnits, double unitPrice){

        UnitDetails u = demoData.searchUnit(unitId);
        if (u == null) return UNIT_NOT_EXIST;
        if (status == ManageInvoice.S_WITHDRAW) {
            ProductDetails p = demoData.searchProduct(u.getProductId());
            int units = numberOfUnits * u.getConversion();
            if (p.getUnits() < units) return p.getUnits();
            editDemoData(true, p, units);
        }

        lines.add(new com.cs.shop.main_classes.InvoiceLine(orderId, 0, unitId, numberOfUnits, unitPrice));
        total += numberOfUnits*unitPrice;
        return NOT_ERROR;

    }

    public void deleteOrder(int orderId){
        for (int i=0; i<lines.size(); i++){
            if (lines.get(i).getInvoiceLineId() == orderId){

                UnitDetails u = demoData.searchUnit(lines.get(i).getUnitId());
                ProductDetails p = demoData.searchProduct(u.getProductId());
                int units = lines.get(i).getUnits()*u.getConversion();
                editDemoData(false, p, units);

                total -= lines.get(i).getUnits()*lines.get(i).getUnitPrice();
                lines.remove(i);
                return;
            }
        }
    }

    public double getTotal() {
        return total;
    }

    public int save( int otherSideId, int userId, double cash, String date, double dept){
        if (Double.compare(cash+dept, total)  != 0) return ERROR_TOTAL_NOT_MATCH;
        boolean result = super.insert(new InvoiceDetails(0, otherSideId, userId, cash, date, dept, status), lines);
        if (!result) return ERROR;

        ManageProduct mProduct = new ManageProduct(context);
        for (com.cs.shop.main_classes.InvoiceLine line : lines){
            UnitDetails unit = mProduct.searchUnit(line.getUnitId());
            ProductDetails product = mProduct.searchProduct(unit.getProductId());
            if (status == ManageInvoice.S_ADD) product.addUnits(line.getUnits()*unit.getConversion());
            else product.withdrawUnits(line.getUnits()*unit.getConversion());
            mProduct.updateProduct(product);
        }
        mProduct = null;

        if (status == ManageInvoice.S_ADD){
            ManageUser mUser = new ManageUser(context);
            UserDetails user = mUser.searchUser(userId);
            user.withdraw(cash);
            mUser.updateUser(user);
            mUser = null;

            ManageStore mStore = new ManageStore(context);
            StoreDetails store = mStore.searchStore(otherSideId);
            store.withdraw(total);
            store.deposit(cash);
            mStore.updateStore(store);
            return NOT_ERROR;
        }

        ManageUser mUser = new ManageUser(context);
        UserDetails user = mUser.searchUser(userId);
        user.deposit(cash);
        mUser.updateUser(user);
        mUser = null;

        ManageClient mClient = new ManageClient(context);
        ClientDetails client = mClient.searchClient(otherSideId);
        client.withdraw(total);
        client.deposit(cash);
        mClient.updateClient(client);
        return NOT_ERROR;
    }

    private void editDemoData(boolean s, ProductDetails p, int units){
        if (s)
        demoData.getProducts().set(demoData.getProducts().indexOf(p),
                new ProductDetails(p.getProductId(), p.getImage(), p.getName(), p.getNegotiable(), p.getNumber(), p.getUnits()-units));
        else
            demoData.getProducts().set(demoData.getProducts().indexOf(p),
                    new ProductDetails(p.getProductId(), p.getImage(), p.getName(), p.getNegotiable(), p.getNumber(), p.getUnits()+units));
    }
}
