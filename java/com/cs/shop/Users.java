package com.cs.shop;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

public class Users extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users);
        final ImageView aliMorgin4 = findViewById(R.id.ali_morgin4);
        final Animation anim = AnimationUtils.loadAnimation(getBaseContext(), R.anim.move_to_corner);
        anim.setDuration(1000);
        aliMorgin4.setAnimation(anim);
    }
}