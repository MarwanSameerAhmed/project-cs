package com.cs.shop;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.AssetManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.ContextThemeWrapper;
import android.view.View;
import android.widget.Toast;

import com.cs.shop.main_classes.ManageInvoice;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.util.concurrent.atomic.AtomicInteger;

public class DashboardActivity extends AppCompatActivity {

    int soon = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dashboard_act2);
        Login.changeNavigationBarColor(this);
        Toast.makeText(this, "مرحبا بك "+Login.user.getName(), Toast.LENGTH_SHORT).show();
    }

    public void toProductActivity(View view) {
        Intent toProduct = new Intent(this, Products.class);
        startActivity(toProduct);
    }

    public void toNewOrderActivity(View view) {
        Intent toNewOrder = new Intent(this, NewOrder.class);
        AtomicInteger status = new AtomicInteger();
        status.set(ManageInvoice.S_WITHDRAW);
        AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.AlertThem);
        builder.setMessage("طلب جديد من نوع")
                .setPositiveButton("توريد", (d,w) -> {
                    status.set(ManageInvoice.S_ADD);
                    toNewOrder.putExtra("status", status.get());
                    startActivity(toNewOrder);
                } ).setNegativeButton("بيع", (d,w) -> {
                    toNewOrder.putExtra("status", status.get());
                    startActivity(toNewOrder);
                });
        AlertDialog show = builder.create();
        if (Login.user.getUserId() == 1) show.show();
        else {
            toNewOrder.putExtra("status", status.get());
            startActivity(toNewOrder);
        }
    }

    public void toInvoices(View view) {
        Intent toInvoices = new Intent(this, Invoices.class);
        startActivity(toInvoices);
    }

    public void soon(View view) {
        switch (soon){
            case 0:
                Toast.makeText(this, "قريبا..", Toast.LENGTH_SHORT).show();
                soon++;
                break;
            case 1:
                Toast.makeText(this, "قريبا بإذن الله..", Toast.LENGTH_SHORT).show();
                soon++;
                break;
            case 2:
                Toast.makeText(this, "قلنا قريبا احنا والغاجه", Toast.LENGTH_SHORT).show();
                soon++;
                break;
            case 3:
                Toast.makeText(this, "ارحمني انا لي منعك", Toast.LENGTH_SHORT).show();
                soon++;
                break;
            default:
                Toast.makeText(this, "احنا طلاب معانا 7 مواد هذا الترم وما اقدرنا نكمله في الموعد ):", Toast.LENGTH_LONG).show();
                final MediaPlayer mPlayer = MediaPlayer.create(this, R.raw.soon);
                mPlayer.start();
                break;
        }
    }

    public void toDirective(View view) {

    }
}