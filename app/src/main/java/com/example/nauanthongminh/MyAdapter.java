package com.example.nauanthongminh;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class MyAdapter extends ArrayAdapter<Food> {
    ArrayList<Food> foodList = new ArrayList<Food>();
    public MyAdapter(@NonNull Context context, int resource, ArrayList<Food> object) {
        super(context, resource, object);
        foodList = object;
    }
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View v = convertView;
        LayoutInflater inflater = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        v = inflater.inflate(R.layout.item_food,null);
        TextView txtten = (TextView)v.findViewById(R.id.txtTenMon);
        if(position==0){
            txtten.setBackgroundColor(Color.WHITE);
        }
        txtten.setText(foodList.get(position).getTen());
        return v;
    }
}
