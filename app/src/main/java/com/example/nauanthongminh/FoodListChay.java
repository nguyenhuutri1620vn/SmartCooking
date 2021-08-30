package com.example.nauanthongminh;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class FoodListChay extends Activity {
    ListView lstfood;
    ImageButton btnOpenInsertFood, btnBack;
    ArrayList<Food> foodListChay = new ArrayList<Food>();
    MyAdapter adapterChay;
    SQLiteDatabase db;
    int posselected =-1;
    public static final int OPENCLASSCH = 113;
    public static final int EDITCLASSCH = 114;
    public static final int SAVECLASSCH = 115;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mainlist);
        lstfood = (ListView)findViewById(R.id.lstFood);
        btnOpenInsertFood = (ImageButton) findViewById(R.id.btnOpenInsert);
        btnBack = (ImageButton)findViewById(R.id.btnThoat);
        getFoodList();
        registerForContextMenu(lstfood);
        lstfood.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                posselected = position;
                return false;
            }
        });
        lstfood.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                posselected = position;
                Food food = foodListChay.get(posselected);
                Bundle bundle = new Bundle();
                Intent intent = new Intent(FoodListChay.this, FoodDetail.class);
                bundle.putSerializable("food",food);
                intent.putExtra("data",bundle);
                startActivity(intent);
            }
        });
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        btnOpenInsertFood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FoodListChay.this, InsertFood.class);
                Bundle bundle=new Bundle();
                bundle.putInt("MaL",5);
                intent.putExtra("data",bundle);
                startActivityForResult(intent, FoodListChay.OPENCLASSCH);
            }
        });
    }
    private void getFoodList(){
        try{
            db = openOrCreateDatabase(DangNhap.DATABASENAME,MODE_PRIVATE,null);
            Cursor c = db.query("tblchay",null,null,null,null,null,null);
            c.moveToFirst();
            while (!c.isAfterLast()){
                foodListChay.add(new Food(c.getInt(0)+"",c.getString(1),
                        c.getString(2),c.getString(3)));
                c.moveToNext();
            }
            adapterChay = new MyAdapter(this, android.R.layout.simple_list_item_1,foodListChay);
            adapterChay.notifyDataSetChanged();
            lstfood.setAdapter(adapterChay);

        }catch (Exception e ){
            Toast.makeText(getApplication(),"Lỗi "+e,Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.mnu, menu);
    }
    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.mnuedit:
                Food food = foodListChay.get(posselected);
                Bundle bundle = new Bundle();
                Intent intent = new Intent(FoodListChay.this, EditFood.class);
                bundle.putSerializable("food",food);
                intent.putExtra("data",bundle);
                startActivityForResult(intent,FoodListChay.EDITCLASSCH);
                return true;
            case R.id.mnudelete:
                comfirmDelete();
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }
    private void comfirmDelete(){
        AlertDialog.Builder aleBuilder = new AlertDialog.Builder(this);
        aleBuilder.setTitle("Xác nhận để xóa món ăn..!!");
        aleBuilder.setMessage("Bạn có muốn chắc chắn xóa ? ");
        aleBuilder.setCancelable(false);
        aleBuilder.setPositiveButton("Đồng ý", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                db = openOrCreateDatabase(DangNhap.DATABASENAME,MODE_PRIVATE, null);
                String id = foodListChay.get(posselected).getId();
                if(db.delete("tblchay","idchay=?", new String []{id}) !=-1){
                    foodListChay.remove(posselected);
                    adapterChay.notifyDataSetChanged();
                    Toast.makeText(getApplication(), "Xóa món ăn thành công !",Toast.LENGTH_SHORT).show();
                }
            }
        });
        aleBuilder.setNegativeButton("Không đồng ý", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        AlertDialog alertDialog = aleBuilder.create();
        aleBuilder.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case FoodListChay.OPENCLASSCH:{
                if(resultCode==FoodListChay.SAVECLASSCH){
                    Bundle bundle=data.getBundleExtra("data");
                    Food x=(Food) bundle.getSerializable("food");
                    foodListChay.add(x);
                    adapterChay.notifyDataSetChanged();
                }
                break;
            }
            case FoodListChay.EDITCLASSCH:
                if(resultCode==FoodListChay.SAVECLASSCH){
                    Bundle bundle = data.getBundleExtra("data");
                    Food food =(Food)bundle.getSerializable("food");
                    foodListChay.set(posselected, food);
                    adapterChay.notifyDataSetChanged();
                }
                break;
        }

    }
}
