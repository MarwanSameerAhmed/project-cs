package com.cs.shop.Interfaces;

import com.cs.shop.main_classes.ClientDetails;
import com.cs.shop.main_classes.InvoiceDetails;
import com.cs.shop.main_classes.ProductDetails;
import com.cs.shop.main_classes.StoreDetails;
import com.cs.shop.main_classes.UnitDetails;
import com.cs.shop.main_classes.UserDetails;
import com.cs.shop.main_classes.InvoiceLine;

import java.util.ArrayList;

public interface Filter {
    interface User {
        void sort(ArrayList<UserDetails> users);
    }

    interface Units {
        void sort(ArrayList<UnitDetails> units);
    }

    interface Product {
        void sort(ArrayList<ProductDetails> products);
    }

    interface Clients {
        void sort(ArrayList<ClientDetails> clients);
    }

    interface Stores {
        void sort(ArrayList<StoreDetails> stores);
    }

    interface Invoice {
        void sort(ArrayList<InvoiceDetails> invoices);
    }

    interface InvoiceLiens {
        void sort(ArrayList<InvoiceLine> lines);

    }
}
