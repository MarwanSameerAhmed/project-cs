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
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.cs.shop.main_classes.ManageProduct;
import com.cs.shop.main_classes.ProductDetails;
import com.cs.shop.main_classes.UnitDetails;

import java.util.ArrayList;
import java.util.Collections;

public class Products extends AppCompatActivity {

    private ManageProduct mProducts;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.products_act3);
        Login.changeNavigationBarColor(this);

        final ImageView aliMorgin = findViewById(R.id.ali_morgin);
        final Animation anim = AnimationUtils.loadAnimation(getBaseContext(), R.anim.move_to_corner);
        anim.setDuration(1000);
        aliMorgin.setAnimation(anim);

        mProducts = new ManageProduct(getBaseContext());
        mProducts.getProducts(products -> Collections.sort(mProducts.getProducts(),
                (p1, p2) -> p1.getName().compareTo(p2.getName())));

        addData();
    }

    @SuppressLint("InflateParams")
    public void addNewProduct(View view) {
        final View addProductView = getLayoutInflater().inflate(R.layout.new_product_ly6, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(addProductView);
        AlertDialog show = builder.create();
        show.show();

        final Button btnAdd = addProductView.findViewById(R.id.btn_add_ly6);
        btnAdd.setOnClickListener(view1 -> {
            final EditText eTextProductName = addProductView.findViewById(R.id.etext_product_name_ly6);
            final EditText eTextProductNumber = addProductView.findViewById(R.id.etext_product_number_ly6);
            final EditText eTextUnitName = addProductView.findViewById(R.id.etext_unit_name_ly6);
            final EditText eTextUnitPrice = addProductView.findViewById(R.id.etext_units_price_ly6);
            final EditText eTextUnitsNumber = addProductView.findViewById(R.id.etext_units_number_ly6);
            final CheckBox chBoxNegotiable = addProductView.findViewById(R.id.chbox_negotiable_ly6);

            String productName = eTextProductName.getText().toString();
            String productNumber = eTextProductNumber.getText().toString();
            String unitName = eTextUnitName.getText().toString();
            int unitsPrice = 0;
            if (!eTextUnitsNumber.getText().toString().equals(""))
                unitsPrice = Integer.parseInt(eTextUnitPrice.getText().toString());
            int unitsNumber = 0;
            if (!eTextUnitsNumber.getText().toString().equals(""))
                unitsNumber = Integer.parseInt(eTextUnitsNumber.getText().toString());
            boolean negotiable = chBoxNegotiable.isChecked();

            if (productName.equals("") || productNumber.equals("") || unitName.equals("") || unitsPrice == 0) {
                Toast.makeText(Products.this, "يجب إكمال البيانات", Toast.LENGTH_SHORT).show();
                return;
            }

            int result = mProducts.newProduct(new ProductDetails(0, "", productName, negotiable, productNumber, unitsNumber));

            if (result == ManageProduct.THIS_NUMBER_IS_EXIT){
                Toast.makeText(Products.this, "رقم المنتج موجود مسبقا", Toast.LENGTH_SHORT).show();
                eTextProductNumber.requestFocus();
                return;
            }

            if (result == ManageProduct.ERROR){
                Toast.makeText(Products.this, "لم تتم الإضافة", Toast.LENGTH_SHORT).show();
                return;
            }

            mProducts.newUnit(new UnitDetails(0, result, 1, "2000-01-01 00:00", unitName, unitsPrice, 0));
            show.cancel();
            addData();
        });

       // mProducts.newProduct(new ProductDetails())
    }

    private void addData(){
        final ListView list = findViewById(R.id.product_list_act3);
        ListAdepter adepter = new ListAdepter(mProducts.getProducts());
        list.setAdapter(adepter);
    }

    class ListAdepter extends BaseAdapter{
        ArrayList<ProductDetails> products = new ArrayList<>();

        public ListAdepter(ArrayList<ProductDetails> products) {
            this.products = products;
        }

        @Override
        public int getCount() {
            return products.size();
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return products.get(i).getProductId();
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            @SuppressLint({"ViewHolder", "InflateParams"})
            final View productView = getLayoutInflater().inflate(R.layout.item_product_ly1, null);
            final TextView txtProductId = productView.findViewById(R.id.txt_id_product_ly1);
            final TextView txtProductName = productView.findViewById(R.id.txt_name_product_ly1);
            final TextView txtProductNumber = productView.findViewById(R.id.txt_number_product_ly1);
            final TextView txtProductUnits = productView.findViewById(R.id.txt_units_product_ly1);

            ProductDetails product = products.get(i);
            int productId = product.getProductId(), productUnits = product.getUnits();
            String productName = product.getName(), productNumber = product.getNumber();

            txtProductId.setText(String.valueOf(productId));
            txtProductName.setText(productName);
            txtProductNumber.setText(productNumber);
            txtProductUnits.setText(String.valueOf(productUnits));

            productView.setOnClickListener(view1 -> {
                final View productUnitsView = getLayoutInflater().inflate(R.layout.product_details_ly10, null);
                final TextView txtProductNameLy10 = productUnitsView.findViewById(R.id.txt_product_name_ly10);
                final Button btnAddNewUnit = productUnitsView.findViewById(R.id.btn_add_new_unit_ly10);
                final ListView lsUnits = productUnitsView.findViewById(R.id.ls_units_ly10);

                btnAddNewUnit.setOnClickListener(view2 -> {
                    final View newUnitView = getLayoutInflater().inflate(R.layout.new_unit_ly11, null);
                    final EditText eTextUnitName = newUnitView.findViewById(R.id.etext_unit_name_ly11);
                    final EditText eTextUnitConversion = newUnitView.findViewById(R.id.etext_unit_conversion_ly11);
                    final EditText eTextUnitPrice = newUnitView.findViewById(R.id.etext_unit_price_ly11);
                    final Button btnAdd = newUnitView.findViewById(R.id.btn_add_ly11);

                    AlertDialog.Builder builder = new AlertDialog.Builder(Products.this);
                    builder.setView(newUnitView);
                    AlertDialog show = builder.create();
                    show.show();

                    btnAdd.setOnClickListener(view3 -> {
                        if (eTextUnitName.getText().toString().trim().equals("") || eTextUnitConversion.getText().toString().equals("") ||
                                eTextUnitPrice.getText().toString().equals(""))
                            Toast.makeText(Products.this, "Must be added all data", Toast.LENGTH_SHORT).show();

                        String unitName = eTextUnitName.getText().toString().trim();
                        int unitConversion = Integer.parseInt(eTextUnitConversion.getText().toString());
                        double unitPrice = Double.parseDouble(eTextUnitPrice.getText().toString());
                        mProducts.newUnit(new UnitDetails(0, productId, unitConversion, "", unitName, unitPrice, 0));

                        ListUnitsAdapter listUnitsAdapter = new ListUnitsAdapter(mProducts.getProductUnits(productId));
                        lsUnits.setAdapter(listUnitsAdapter);
                        show.cancel();
                    });
                });

                txtProductNameLy10.setText(productName);
                ListUnitsAdapter listUnitsAdapter = new ListUnitsAdapter(mProducts.getProductUnits(productId));
                lsUnits.setAdapter(listUnitsAdapter);

                AlertDialog.Builder builder = new AlertDialog.Builder(Products.this);
                builder.setView(productUnitsView);
                AlertDialog show = builder.create();
                show.show();

            });

            return productView;
        }

        class ListUnitsAdapter extends BaseAdapter{
            ArrayList<UnitDetails> units;

            public ListUnitsAdapter(ArrayList<UnitDetails> units) {
                this.units = units;
            }

            @Override
            public int getCount() {
                return units.size();
            }

            @Override
            public Object getItem(int i) {
                return units.get(i);
            }

            @Override
            public long getItemId(int i) {
                return units.get(i).getUnitId();
            }

            @Override
            public View getView(int i, View convertView, ViewGroup parent) {
                @SuppressLint({"ViewHolder", "InflateParams"})
                final View itemUnitsView = getLayoutInflater().inflate(R.layout.item_unit_ly3, null);
                final TextView txtUnitName = itemUnitsView.findViewById(R.id.txt_unit_name_ly3);
                final TextView txtUnitConversion = itemUnitsView.findViewById(R.id.txt_unit_conv_ly3);
                final TextView txtUnitPrice = itemUnitsView.findViewById(R.id.txt_unit_price_ly3);

                UnitDetails unitDetails = units.get(i);

                txtUnitName.setText(unitDetails.getName());
                txtUnitConversion.setText(String.valueOf(unitDetails.getConversion()));
                txtUnitPrice.setText(String.valueOf(unitDetails.getPrice()));
                return itemUnitsView;
            }
        }
    }
}