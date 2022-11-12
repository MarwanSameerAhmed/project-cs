package com.cs.shop;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.cs.shop.main_classes.InvoiceLine;
import com.cs.shop.main_classes.ManageInvoice;
import com.cs.shop.main_classes.ProductDetails;
import com.cs.shop.main_classes.UnitDetails;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;

public class NewOrder extends AppCompatActivity {

    private com.cs.shop.main_classes.NewOrder newOrder;
    int orderId = 0;
    int status;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_order_act4);

        Login.changeNavigationBarColor(this);

        final ImageView aliMorgin5 = findViewById(R.id.ali_morgin5);
        final Animation anim = AnimationUtils.loadAnimation(NewOrder.this, R.anim.move_to_corner);
        anim.setDuration(1000);
        aliMorgin5.setAnimation(anim);

        status = getIntent().getIntExtra("status", ManageInvoice.S_WITHDRAW);

        newOrder = new com.cs.shop.main_classes.NewOrder(NewOrder.this, status);
        //newOrder.getProducts(products -> Collections.sort(products, null));

        addToList();

    }

    public void saveOrder(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(NewOrder.this, R.style.AlertThem);
        builder.setTitle("Save Order")
                .setMessage("Total = "+newOrder.getTotal())
                .setPositiveButton("Save", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (newOrder.getTotal() == 0) return;
                        String date;
                        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                            LocalDateTime localDateTime = LocalDateTime.now();
                            date = localDateTime.format(DateTimeFormatter.ofPattern(com.cs.shop.main_classes.NewOrder.DATE_FORMAT));
                        }else{
                            @SuppressLint("SimpleDateFormat")
                            SimpleDateFormat dateFormat = new SimpleDateFormat(com.cs.shop.main_classes.NewOrder.DATE_FORMAT);
                            date = dateFormat.format(new Date());
                        }

                        int result = newOrder.save(1, Login.user.getUserId(), newOrder.getTotal(), date, 0);
                        if (result == com.cs.shop.main_classes.NewOrder.NOT_ERROR){
                            Toast.makeText(NewOrder.this, "successful", Toast.LENGTH_SHORT).show();
                            NewOrder.super.finish();
                        }
                    }
                });
        AlertDialog showDialog = builder.create();
        showDialog.show();
    }

    public void showItems(View view) {
        @SuppressLint("InflateParams")
        final View orderItemsView = getLayoutInflater().inflate(R.layout.order_items_ly4, null);
        final ListView lsOrderItems = orderItemsView.findViewById(R.id.ls_order_items_ly4);
        AlertDialog.Builder builder = new AlertDialog.Builder(NewOrder.this, R.style.AlertThem);
        builder.setTitle("Order Items")
                .setView(orderItemsView);
        AlertDialog showDialog = builder.create();
        ListOrderItems listOrderItems = new ListOrderItems(newOrder.getLines(), showDialog);
        lsOrderItems.setAdapter(listOrderItems);
        showDialog.show();
    }


    private void addToList(){
        final ListView list = findViewById(R.id.new_order_list_act4);
        final TextView txtTotal = findViewById(R.id.txt_total_act4);
        txtTotal.setText(String.valueOf(newOrder.getTotal()));
        ListProductsAdepter adepter = new ListProductsAdepter(newOrder.getProducts());
        list.setAdapter(adepter);
    }

    class ListProductsAdepter extends BaseAdapter {
        int numberOfUnitsTemp;
        @SuppressLint({"ViewHolder", "InflateParams"})
        final View productDetailsView = getLayoutInflater().inflate(R.layout.order_prroduct_ly2, null, false);
        AlertDialog detailsAlertDialog;
        ArrayList<ProductDetails> products = new ArrayList<>();
        public ListProductsAdepter(ArrayList<ProductDetails> products) {
            this.products = products;
        }

        @Override
        public int getCount() {
            return products.size();
        }

        @Override
        public Object getItem(int i) {
            return products.get(i);
        }

        @Override
        public long getItemId(int i) {
            return products.get(i).getProductId();
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            @SuppressLint({"ViewHolder", "InflateParams"})
            final View itemProductView = getLayoutInflater().inflate(R.layout.item_product_ly1, null);
            final TextView txtProductId = itemProductView.findViewById(R.id.txt_id_product_ly1);
            final TextView txtProductName = itemProductView.findViewById(R.id.txt_name_product_ly1);
            final TextView txtProductNumber = itemProductView.findViewById(R.id.txt_number_product_ly1);
            final TextView txtProductUnits = itemProductView.findViewById(R.id.txt_units_product_ly1);

            ProductDetails productDetails = products.get(i);
            numberOfUnitsTemp = productDetails.getUnits();

            txtProductId.setText(String.valueOf(productDetails.getProductId()));
            txtProductName.setText(productDetails.getName());
            txtProductNumber.setText(productDetails.getNumber());
            txtProductUnits.setText(String.valueOf(productDetails.getUnits()));



            itemProductView.setOnClickListener(view1 -> {
                final ListView lsUnitsList = productDetailsView.findViewById(R.id.ls_units_ly2);
                final TextView txtProductName1 = productDetailsView.findViewById(R.id.txt_product_name_ly2);
                final TextView txtProductNumber1 = productDetailsView.findViewById(R.id.txt_product_number_ly2);
                final TextView txtTotal = productDetailsView.findViewById(R.id.txt_total_ly2);
                final TextView txtUnitName = productDetailsView.findViewById(R.id.txt_unit_name_ly2);
                final EditText eTextUnitPrice = productDetailsView.findViewById(R.id.etext_unit_price_ly2);
                final EditText eTextUnitsNumber = productDetailsView.findViewById(R.id.etext_units_ly2);

                txtProductName1.setText(productDetails.getName());
                txtProductNumber1.setText(productDetails.getNumber());
                txtTotal.setText("total");
                txtUnitName.setText("Unit");
                if (status == ManageInvoice.S_WITHDRAW)
                    eTextUnitPrice.setEnabled(productDetails.getNegotiable());
                eTextUnitPrice.setText("");
                eTextUnitsNumber.setText("");


                ListUnitsAdepter listUnitsAdepter = new ListUnitsAdepter(newOrder.getProductUnits(products.get(i).getProductId(),
                        units -> Collections.sort(units, (o1, o2) -> Integer.compare(o2.getConversion(), o1.getConversion()))));
                lsUnitsList.setAdapter(listUnitsAdepter);

                if (productDetailsView.getParent() != null)
                    ((ViewGroup) productDetailsView.getParent()).removeAllViews();

                AlertDialog.Builder details = new AlertDialog.Builder(NewOrder.this, R.style.AlertThem);
                details.setView(productDetailsView)
                        .setTitle(productDetails.getName());
                detailsAlertDialog = details.create();
                detailsAlertDialog.show();

            });

            return itemProductView;
        }

        class ListUnitsAdepter extends BaseAdapter{
            private final ArrayList<UnitDetails> units;
            public ListUnitsAdepter(ArrayList<UnitDetails> units) {
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
            public View getView(int i, View view, ViewGroup viewGroup) {
                @SuppressLint({"ViewHolder", "InflateParams"})
                final View itemUnitsView = getLayoutInflater().inflate(R.layout.item_unit_ly3, null);
                final TextView txtUnitName = itemUnitsView.findViewById(R.id.txt_unit_name_ly3);
                final TextView txtUnitConversion = itemUnitsView.findViewById(R.id.txt_unit_conv_ly3);
              //  final TextView txtUnitsNumber = itemUnitsView.findViewById(R.id.txt_units_number_ly3);
                final TextView txtUnitPrice = itemUnitsView.findViewById(R.id.txt_unit_price_ly3);

                UnitDetails unitDetails = units.get(i);
         //       int numberOfUnits = numberOfUnitsTemp / unitDetails.getConversion();

                txtUnitName.setText(unitDetails.getName());
                txtUnitConversion.setText(String.valueOf(unitDetails.getConversion()));
           //     txtUnitsNumber.setText(String.valueOf(numberOfUnits));
                txtUnitPrice.setText(String.valueOf(unitDetails.getPrice()));

               // numberOfUnitsTemp -= numberOfUnits * unitDetails.getConversion();

                itemUnitsView.setOnClickListener(view1 -> {
                    final TextView txtTotal = productDetailsView.findViewById(R.id.txt_total_ly2);
                    final TextView txtUnitName1 = productDetailsView.findViewById(R.id.txt_unit_name_ly2);
                    final EditText eTextUnitPrice = productDetailsView.findViewById(R.id.etext_unit_price_ly2);
                    final EditText eTextUnitsNumber = productDetailsView.findViewById(R.id.etext_units_ly2);
                    final Button btnSave = productDetailsView.findViewById(R.id.btn_save_ly2);

                    txtUnitName1.setText(unitDetails.getName());
                    TextWatcher textWatcher = new TextWatcher() {
                        @Override
                        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                        }

                        @Override
                        public void onTextChanged(CharSequence s, int start, int before, int count) {
                            if (!(eTextUnitsNumber.getText().toString().trim().equals("") || eTextUnitPrice.getText().toString().trim().equals(""))){
                                txtTotal.setText(String.valueOf(
                                        Double.parseDouble(eTextUnitsNumber.getText().toString()) * Double.parseDouble(eTextUnitPrice.getText().toString())));
                                return;
                            }
                            txtTotal.setText("Total");
                        }

                        @Override
                        public void afterTextChanged(Editable s) {
                        }
                    };
                    eTextUnitPrice.setText(String.valueOf(unitDetails.getPrice()));
                    eTextUnitPrice.addTextChangedListener(textWatcher);
                    eTextUnitsNumber.addTextChangedListener(textWatcher);
                    btnSave.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (txtTotal.getText().toString().equals("Total"))
                                return;
                            int units = Integer.parseInt(eTextUnitsNumber.getText().toString());
                            int result = newOrder.addOrder(orderId, unitDetails.getUnitId(),
                                    units, Double.parseDouble(eTextUnitPrice.getText().toString()));
                            if (result != com.cs.shop.main_classes.NewOrder.NOT_ERROR) {
                                Toast.makeText(NewOrder.this,
                                        "المخزون (" + String.valueOf(result/unitDetails.getConversion()) +
                                                ") لا يكفي ل"+units+" "+unitDetails.getName(),
                                        Toast.LENGTH_SHORT).show();
                                return;
                            }
                            orderId++;
                            detailsAlertDialog.cancel();
                            addToList();
                        }
                    });


                });
                return itemUnitsView;
            }
        }

    }

    class ListOrderItems extends BaseAdapter{
        ArrayList<InvoiceLine> lines;
        AlertDialog alertDialog;

        public ListOrderItems(ArrayList<InvoiceLine> lines, AlertDialog alertDialog) {
            this.lines = lines;
            this.alertDialog = alertDialog;
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
            @SuppressLint({"ViewHolder", "InflateParams"})
            final View itemInOrderView = getLayoutInflater().inflate(R.layout.item_in_order_ly5, null);
            final TextView txtOrderId = itemInOrderView.findViewById(R.id.txt_order_id_ly5);
            final TextView txtProductName = itemInOrderView.findViewById(R.id.txt_product_name_ly5);
            final TextView txtUnitName = itemInOrderView.findViewById(R.id.txt_unit_name_ly5);
            final TextView txtNumberOfUnits = itemInOrderView.findViewById(R.id.txt_untis_number_ly5);
            final TextView txtUnitPrice = itemInOrderView.findViewById(R.id.txt_unit_price_ly5);
            final TextView txtTotal = itemInOrderView.findViewById(R.id.txt_total_ly5);

            UnitDetails unit = newOrder.searchUnit(lines.get(i).getUnitId());
            ProductDetails product = newOrder.searchProduct(unit.getProductId());

            txtOrderId.setText(String.valueOf(lines.get(i).getInvoiceLineId()));
            txtProductName.setText(product.getName());
            txtUnitName.setText(unit.getName());
            txtNumberOfUnits.setText(String.valueOf(lines.get(i).getUnits()));
            txtUnitPrice.setText(String.valueOf(lines.get(i).getUnitPrice()));
            txtTotal.setText(String.valueOf(lines.get(i).getUnits()*lines.get(i).getUnitPrice()));

            itemInOrderView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    newOrder.deleteOrder(lines.get(i).getInvoiceLineId());
                    alertDialog.cancel();
                    addToList();
                }
            });
            return itemInOrderView;
        }
    }

}