package com.cs.shop.main_classes;


import android.content.Context;

import com.cs.shop.Interfaces.Filter;
import com.cs.shop.data.DataSetup;

import java.util.ArrayList;

public class ManageProduct extends DataSetup {

    public static int NOT_ERROR = 0;
    public static int THIS_NUMBER_IS_EXIT = -1;
    public static int ERROR = -2;

    private ArrayList<ProductDetails> products;

    private ArrayList<UnitDetails> units;



    public ManageProduct(Context context) {
        super(context);
        products = super.products();
        units = super.units();
    }

    public ArrayList<ProductDetails> getProducts() {
        return products;
    }

    public ArrayList<ProductDetails> getProducts(Filter.Product filter) {
        filter.sort(this.products);
        return this.products;
    }

    public ProductDetails searchProduct(int productId){
        for (ProductDetails product: products) {
            if (product.getProductId() == productId){
                return product;
            }
        }
        return null;
    }

    public ProductDetails searchProduct(String productNumber){
        for (ProductDetails product:
                products)
            if (product.getNumber().equals(productNumber))
                return product;
        return null;
    }


    public ArrayList<UnitDetails> getProductUnits(int productId) {
        ArrayList<UnitDetails> productUnits = new ArrayList<>();
        for (UnitDetails unit:
                units)
            if (unit.getProductId() == productId)
                productUnits.add(unit);

        return productUnits;
    }

    public ArrayList<UnitDetails> getProductUnits(int productId, Filter.Units filter) {
        ArrayList<UnitDetails> productUnits = new ArrayList<>();
        for (UnitDetails unit:
                units)
            if (unit.getProductId() == productId)
                productUnits.add(unit);
        filter.sort(productUnits);
        return productUnits;
    }

    public UnitDetails searchUnit(int unitId){
        for (UnitDetails unit: units) {
            if (unit.getUnitId() == unitId){
                return unit;
            }
        }
        return null;
    }

    public int newProduct(ProductDetails product) {
        if (searchProduct(product.getNumber()) != null)
            return THIS_NUMBER_IS_EXIT;
        long result = super.insert(product);
        if (result == -1) return ERROR;
        refresh();
        return (int) result;
    }

    public  boolean newUnit(UnitDetails unit){
        boolean result = super.insert(unit);
        refresh();
        return result;
    }

    public boolean deleteProduct(ProductDetails product) {
        boolean result = super.delete(product);
        if (!result) return false;
        for (UnitDetails unit:
             getProductUnits(product.getProductId())) {
            deleteUnit(unit);
        }
        refresh();
        return true;
    }

    public boolean updateProduct(ProductDetails product) {
        boolean result = super.update(product);
        refresh();
        return result;
    }


    public boolean updateUnit(UnitDetails unit){
        boolean result = super.update(unit);
        refresh();
        return result;
    }

    public boolean deleteUnit(UnitDetails unit) {
        boolean result = super.delete(unit);
        refresh();
        return result;
    }

    public void refresh(){
        products = super.products();
        units = super.units();
    }
}