package com.example.nauanthongminh;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.widget.TextView;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class FoodDetail extends Activity {
    ArrayList<Food> foodListMan = new ArrayList<Food>();
    TextView txtten, txtnguyenlieu, txthuongdan;
    SQLiteDatabase db;
    MyADapterDetail adapter;
    String id;
    int pos = 0;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.item_detailfood);
        Init();
        getData();
    }
    private void getData(){
        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra("data");
        Food food = (Food)bundle.getSerializable("food");
        id = food.getId();
        txtten.setText(food.getTen());
        txtnguyenlieu.setText(food.getNguyenlieu());
        txthuongdan.setText(food.getHuongdan());
    }
    public void Init(){
        txtten = (TextView)findViewById(R.id.txtTenMonCT);
        txtnguyenlieu = (TextView)findViewById(R.id.txtNguyenLieuCT);
        txthuongdan = (TextView)findViewById(R.id.txtHuongDanCT);
    }

}
