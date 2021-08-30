package com.example.nauanthongminh;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    CardView cv1;
    CardView cv2;
    CardView cv3;
    CardView cv4;
    CardView cv5;
    CardView thoat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        thoat = (CardView) findViewById(R.id.thoat);

        thoat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Notify.exit(MainActivity.this);
            }
        });
        cv1 = (CardView)findViewById(R.id.man);
        cv2 = (CardView)findViewById(R.id.xao);
        cv3 = (CardView)findViewById(R.id.luoc);
        cv4 = (CardView)findViewById(R.id.canh);
        cv5 = (CardView)findViewById(R.id.chay);

        cv1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent man = new Intent(MainActivity.this, FoodList.class);
                startActivity(man);
            }
        });
        cv2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent xao = new Intent(MainActivity.this, FoodListXao.class);
                startActivity(xao);
            }
        });
        cv3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent luoc = new Intent(MainActivity.this, FoodListLuoc.class);
                startActivity(luoc);
            }
        });
        cv4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent canh = new Intent(MainActivity.this, FoodListCanh.class);
                startActivity(canh);
            }
        });
        cv5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent chay = new Intent(MainActivity.this, FoodListChay.class);
                startActivity(chay);
            }
        });

    }
}