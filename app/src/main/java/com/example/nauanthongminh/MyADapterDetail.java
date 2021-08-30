package com.example.nauanthongminh;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class MyADapterDetail extends ArrayAdapter<Food> {
    private Context c;
    ArrayList<Food> foodListDetail = new ArrayList<Food>();
    public MyADapterDetail(@NonNull Context context, int resource, ArrayList<Food> object) {
        super(context, resource, object);
        foodListDetail = object;
    }
    @Override

    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View v = convertView;
        LayoutInflater inflater = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        v = inflater.inflate(R.layout.item_detailfood,null);
        TextView txtten = (TextView)v.findViewById(R.id.txtTenMonCT);
        TextView txtnguyenlieu = (TextView)v.findViewById(R.id.txtNguyenLieuCT);
        TextView txthuongdan = (TextView)v.findViewById(R.id.txtHuongDanCT);
        if(position==0){
            txtten.setBackgroundColor(Color.WHITE);
            txtnguyenlieu.setBackgroundColor(Color.WHITE);
            txthuongdan.setBackgroundColor(Color.WHITE);
        }
        txtten.setText(foodListDetail.get(position).getTen());
        txtnguyenlieu.setText(foodListDetail.get(position).getNguyenlieu());
        txthuongdan.setText(foodListDetail.get(position).getHuongdan());
        return v;
    }
}
