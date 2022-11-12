package com.cs.shop.main_classes;

import android.content.Context;

import com.cs.shop.Interfaces.Filter;
import com.cs.shop.data.DataSetup;

import java.util.ArrayList;

public class ManageStore extends DataSetup {
    private ArrayList<StoreDetails> stores;

    public ManageStore(Context context){
        super(context);
        stores = super.stores();
    }

    public ArrayList<StoreDetails> getStores(){
        return stores;
    }

    public ArrayList<StoreDetails> getStores(Filter.Stores filter){
        filter.sort(this.stores);
        return stores;
    }

    public StoreDetails searchStore(int storeId){
        for (StoreDetails store : stores) {
            if (store.getStoreId() == storeId)
                return store;
        }
        return null;
    }

    public StoreDetails searchStore(String storeName){
        for (StoreDetails store : stores) {
            if (store.getStoreName().equals(storeName))
                return store;
        }
        return null;
    }

    public boolean newStore(StoreDetails store){
       boolean result = super.insert(store);
       refresh();
       return result;
    }

    public boolean updateStore(StoreDetails store){
        boolean result = super.update(store);
        refresh();
        return result;
    }

    public boolean deleteStore(StoreDetails store){
        boolean result = super.delete(store);
        refresh();
        return result;
    }

    public void refresh(){stores = super.stores();}

}