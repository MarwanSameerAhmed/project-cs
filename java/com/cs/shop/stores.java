package com.cs.shop;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

public class stores extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stores);
        final ImageView aliMorgin3 = findViewById(R.id.ali_morgin3);
        final Animation anim = AnimationUtils.loadAnimation(getBaseContext(), R.anim.move_to_corner);
        anim.setDuration(1000);
        aliMorgin3.setAnimation(anim);
    }
}