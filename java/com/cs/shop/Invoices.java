package com.cs.shop;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.cs.shop.main_classes.ClientDetails;
import com.cs.shop.main_classes.InvoiceDetails;
import com.cs.shop.main_classes.InvoiceLine;
import com.cs.shop.main_classes.ManageClient;
import com.cs.shop.main_classes.ManageInvoice;
import com.cs.shop.main_classes.ManageProduct;
import com.cs.shop.main_classes.ManageStore;
import com.cs.shop.main_classes.ProductDetails;
import com.cs.shop.main_classes.StoreDetails;
import com.cs.shop.main_classes.UnitDetails;

import java.util.ArrayList;
import java.util.Collections;

public class Invoices extends AppCompatActivity {
    private ManageInvoice mInvoice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.invoices_act5);
        Login.changeNavigationBarColor(this);
        final ImageView aliMorgin2 = findViewById(R.id.ali_morgin2);
        final Animation anim = AnimationUtils.loadAnimation(getBaseContext(), R.anim.move_to_corner);
        anim.setDuration(1000);
        aliMorgin2.setAnimation(anim);

        final ListView lsInvoices = findViewById(R.id.ls_invoices_act5);
        mInvoice = new ManageInvoice(this);
        ListInvoiceAdapter invoiceAdapter = new ListInvoiceAdapter(mInvoice.getInvoices(invoices -> Collections.sort(invoices,
                (i1, i2) -> Integer.compare(i2.getInvoiceId(), i1.getInvoiceId()))));
        lsInvoices.setAdapter(invoiceAdapter);
    }

    class ListInvoiceAdapter extends BaseAdapter{
        ArrayList<InvoiceDetails> invoices;
        ManageProduct mProduct;
        public ListInvoiceAdapter(ArrayList<InvoiceDetails> invoices) {
            this.invoices = invoices;
        }

        @Override
        public int getCount() {
            return invoices.size();
        }

        @Override
        public Object getItem(int i) {
            return invoices.get(i);
        }

        @Override
        public long getItemId(int i) {
            return invoices.get(i).getInvoiceId();
        }

        @Override
        public View getView(int i, View convertView, ViewGroup parent) {
            @SuppressLint({"ViewHolder", "InflateParams"})
            final View invoiceView = getLayoutInflater().inflate(R.layout.item_in_order_ly7, null);
            final TextView txtInvoiceId = invoiceView.findViewById(R.id.txt_invoice_id_ly7);
            final TextView txtStatus = invoiceView.findViewById(R.id.txt_status_ly7);
            final TextView txtOtherSideName = invoiceView.findViewById(R.id.txt_other_side_name_ly7);
            final TextView txtDate = invoiceView.findViewById(R.id.txt_date_ly7);

            InvoiceDetails invoiceDetails = invoices.get(i);
            int invoiceId = invoiceDetails.getInvoiceId();
            String status, otherSideName, date;

            date = invoiceDetails.getDate();
            if (invoiceDetails.getStatus() == ManageInvoice.S_WITHDRAW){
                status = "بيع";
                ClientDetails client = new ManageClient(Invoices.this).searchClient(invoiceDetails.getOtherSideId());
                otherSideName = client.getName();
            }
            else{
                status = "توريد";
                StoreDetails store = new ManageStore(Invoices.this).searchStore(invoiceDetails.getOtherSideId());
                otherSideName = store.getStoreName();
            }

            txtInvoiceId.setText(String.valueOf(invoiceId));
            txtStatus.setText(status);
            txtOtherSideName.setText(otherSideName);
            txtDate.setText(date);

            invoiceView.setOnClickListener(v -> {
                @SuppressLint({"InflateParams", "ViewHolder"})
                final View invoiceDetailsView = getLayoutInflater().inflate(R.layout.invoice_details_ly8, null);
                final ListView lsInvoiceLines = invoiceDetailsView.findViewById(R.id.ls_invoice_lines_ly8);
                final TextView tOtherSideName = invoiceDetailsView.findViewById(R.id.txt_other_side_name_ly8);
                final TextView tDate = invoiceDetailsView.findViewById(R.id.txt_date_ly8);
                final TextView tStatus = invoiceDetailsView.findViewById(R.id.txt_status_ly8);
                final TextView tTotal = invoiceDetailsView.findViewById(R.id.txt_total_ly8);

                tStatus.setText(status);
                tTotal.setText(String.valueOf(invoiceDetails.getCash() + invoiceDetails.getDept()));
                tOtherSideName.setText(otherSideName);
                tDate.setText(date);

                mProduct = new ManageProduct(Invoices.this);
                ListLinesAdepter linesAdepter = new ListLinesAdepter(mInvoice.getInvoiceLines(invoiceId));
                lsInvoiceLines.setAdapter(linesAdepter);

                AlertDialog.Builder builder = new AlertDialog.Builder(Invoices.this, R.style.AlertThem);
                builder.setTitle(String.valueOf(invoiceId))
                        .setView(invoiceDetailsView);

                AlertDialog show = builder.create();
                show.show();

            });

            return invoiceView;
        }

        class ListLinesAdepter extends BaseAdapter{
            ArrayList<InvoiceLine> lines;

            public ListLinesAdepter(ArrayList<InvoiceLine> lines) {
                this.lines = lines;
            }

            @Override
            public int getCount() {
                return lines.size();
            }

            @Override
            public Object getItem(int i) {
                return lines.get(i);
            }

            @Override
            public long getItemId(int i) {
                return lines.get(i).getInvoiceLineId();
            }

            @Override
            public View getView(int i, View convertView, ViewGroup parent) {
                @SuppressLint({"InflateParams", "ViewHolder"})
                final View invoiceLineView = getLayoutInflater().inflate(R.layout.invoice_line_ly9, null);
                final TextView txtProductName = invoiceLineView.findViewById(R.id.txt_product_name_ly9);
                final TextView txtUnitName = invoiceLineView.findViewById(R.id.txt_unit_name_ly9);
                final TextView txtUnitPrice = invoiceLineView.findViewById(R.id.txt_unit_price_ly9);
                final TextView txtUnitsNumber = invoiceLineView.findViewById(R.id.txt_units_number_ly9);

                InvoiceLine invoiceLine = lines.get(i);
                UnitDetails unit = mProduct.searchUnit(invoiceLine.getUnitId());
                ProductDetails product = mProduct.searchProduct(unit.getProductId());

                txtProductName.setText(product.getName());
                txtUnitName.setText(unit.getName());
                txtUnitPrice.setText(String.valueOf(invoiceLine.getUnitPrice()));
                txtUnitsNumber.setText(String.valueOf(invoiceLine.getUnits()));

                return invoiceLineView;
            }
        }
    }
}