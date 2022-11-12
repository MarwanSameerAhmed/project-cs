package com.cs.shop;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.cs.shop.main_classes.ClientDetails;
import com.cs.shop.main_classes.ManageClient;
import com.cs.shop.main_classes.ManageInvoice;
import com.cs.shop.main_classes.ManageProduct;
import com.cs.shop.main_classes.ManageStore;
import com.cs.shop.main_classes.ManageUser;
import com.cs.shop.main_classes.NewOrder;
import com.cs.shop.main_classes.ProductDetails;
import com.cs.shop.main_classes.StoreDetails;
import com.cs.shop.main_classes.UnitDetails;
import com.cs.shop.main_classes.UserDetails;



public class Login extends AppCompatActivity {

    @SuppressLint("StaticFieldLeak")
    public static UserDetails user;
    private ManageUser mUser;
    private static final String TAG = "Login";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_act1);
        changeNavigationBarColor(this);
        mUser = new ManageUser(getBaseContext());
    }

    public void login(View view) {

        final EditText eUsername = findViewById(R.id.eText_username_la);
        final EditText ePassword = findViewById(R.id.eText_password_la);
        String username = eUsername.getText().toString();
        String password = ePassword.getText().toString();

        if (password.trim().equals("") || username.trim().equals("")){
            Toast.makeText(getBaseContext(), "Must be added all data", Toast.LENGTH_SHORT).show();
            return;
        }
        if (mUser.login( username, password) == ManageUser.ERROR_USERNAME_OR_PASSWORD){
            Toast.makeText(getBaseContext(), "Username or Password is wrong", Toast.LENGTH_SHORT).show();
            return;
        }
        if (mUser.login( username, password) == ManageUser.ERROR_NOT_ENABLE){
            Toast.makeText(getBaseContext(), "this user not enabled", Toast.LENGTH_SHORT).show();
            return;
        }
        user = mUser.searchUser(username);
        Intent toDashboard = new Intent(this, DashboardActivity.class);
        startActivity(toDashboard);
    }

//    private void demoData(){
//        Log.d(TAG, "demoData: Start");
//        ManageUser mUser = new ManageUser(getBaseContext());
//        ManageClient mClient = new ManageClient(getBaseContext());
//        ManageStore mStore = new ManageStore(getBaseContext());
//        ManageProduct mProduct = new ManageProduct(getBaseContext());
//
//        mUser.newUser(new UserDetails(0, 4, -10000, true, "محمد مرجاني", "123m", "771234567", "mm"));
//        mUser.newUser(new UserDetails(0, 5, 10000, false, "علي مرجاني", "123a", "771234567", "am"));
//        mUser.newUser(new UserDetails(0, 6, 0, true, "احمد مرجاني", "123h", "771234567", "hm"));
//
//        mClient.newClient(new ClientDetails(0, 0, "محمد سهيل", "771234567"));
//        mClient.newClient(new ClientDetails(0, 10000, "علي سهيل", "771234567"));
//        mClient.newClient(new ClientDetails(0, -10000, "احمد سهيل", "771234567"));
//
//        mStore.newStore(new StoreDetails(0, 0, "نقم", "771234567", "محمد قاسم", "المجهلي"));
//        mStore.newStore(new StoreDetails(0, 10000, "شعوب", "771234567", "علي قاسم", "القملي"));
//        mStore.newStore(new StoreDetails(0, -10000, "هائل", "771234567", "احمد قاسم", "قاطن"));
//
//        mProduct.newProduct(new ProductDetails(0, "", "حليب", false, "01102", 200));
//        mProduct.newProduct(new ProductDetails(0, "", "شاي", true, "01103", 5000));
//        mProduct.newProduct(new ProductDetails(0, "", "بسكوت", false, "01104", 100));
//        mProduct.newUnit(new UnitDetails(0, mProduct.searchProduct("01102").getProductId(), 1, "20-02-2023", "حبة", 250, 30));
//        mProduct.newUnit(new UnitDetails(0, mProduct.searchProduct("01102").getProductId(), 24, "20-02-2023", "كرتون", 5000, 30));
//        mProduct.newUnit(new UnitDetails(0, mProduct.searchProduct("01103").getProductId(), 1, "20-02-2023", "100g", 200, 30));
//        mProduct.newUnit(new UnitDetails(0, mProduct.searchProduct("01103").getProductId(), 5, "20-02-2023", "500g", 1000, 30));
//        mProduct.newUnit(new UnitDetails(0, mProduct.searchProduct("01104").getProductId(), 1, "20-02-2023", "حبة", 200, 30));
//
//        NewOrder newOrder = new NewOrder(getBaseContext(), ManageInvoice.S_WITHDRAW);
//        Toast.makeText(this, ""+newOrder.addOrder(1, 1, 500, 250), Toast.LENGTH_SHORT).show();
//        newOrder.addOrder(1, 2, 2, 5000);
//        newOrder.save(1, 1, newOrder.getTotal()-100, "", 100);
//
//        mProduct.refresh();
//        mClient.refresh();
//        mUser.refresh();
//        mStore.refresh();
//        Log.d(TAG, "demoData: Finish");
//    }

    public static void changeNavigationBarColor(Activity activity){
        activity.getWindow().setNavigationBarColor(activity.getResources().getColor(R.color.black));
    }
}