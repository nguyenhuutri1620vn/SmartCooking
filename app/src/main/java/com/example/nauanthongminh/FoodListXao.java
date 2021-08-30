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

public class FoodListXao extends Activity {
    ListView lstfood;
    ImageButton btnOpenInsertFood, btnBack;
    ArrayList<Food> foodListXao = new ArrayList<Food>();
    MyAdapter adapterXao;
    SQLiteDatabase db;
    int posselected =-1;
    public static final int OPENCLASSX = 113;
    public static final int EDITCLASS = 114;
    public static final int SAVECLASSX = 115;
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
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        lstfood.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                posselected = position;
                Food food = foodListXao.get(posselected);
                Bundle bundle = new Bundle();
                Intent intent = new Intent(FoodListXao.this, FoodDetail.class);
                bundle.putSerializable("food",food);
                intent.putExtra("data",bundle);
                startActivity(intent);
            }
        });
        btnOpenInsertFood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FoodListXao.this, InsertFood.class);
                Bundle bundle=new Bundle();
                bundle.putInt("MaL",2);
                intent.putExtra("data",bundle);
                startActivityForResult(intent, FoodListXao.OPENCLASSX);
            }
        });
    }
    private void getFoodList(){
        try{
            db = openOrCreateDatabase(DangNhap.DATABASENAME,MODE_PRIVATE,null);
            Cursor c = db.query("tblxao",null,null,null,null,null,null);
            c.moveToFirst();
            while (!c.isAfterLast()){
                foodListXao.add(new Food(c.getInt(0)+"",c.getString(1),
                        c.getString(2),c.getString(3)));
                c.moveToNext();
            }
            adapterXao = new MyAdapter(this, android.R.layout.simple_list_item_1,foodListXao);
            lstfood.setAdapter(adapterXao);
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
                Food food = foodListXao.get(posselected);
                Bundle bundle = new Bundle();
                Intent intent = new Intent(FoodListXao.this, EditFoodXao.class);
                bundle.putSerializable("food",food);
                bundle.putInt("MaL",2);
                intent.putExtra("data",bundle);
                startActivityForResult(intent,FoodListXao.EDITCLASS);
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
                String id = foodListXao.get(posselected).getId();
                if(db.delete("tblxao","idxao=?", new String []{id}) !=-1){
                    foodListXao.remove(posselected);
                    adapterXao.notifyDataSetChanged();
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
            case FoodListXao.OPENCLASSX:{
                if(resultCode==FoodListXao.SAVECLASSX){
                    Bundle bundle=data.getBundleExtra("data");
                    Food x=(Food) bundle.getSerializable("food");
                    foodListXao.add(x);
                    adapterXao.notifyDataSetChanged();
                }
                break;
            }
            case FoodListXao.EDITCLASS:
                if(resultCode==FoodListXao.SAVECLASSX){
                    Bundle bundle = data.getBundleExtra("data");
                    Food food =(Food)bundle.getSerializable("food");
                    foodListXao.set(posselected, food);
                    Toast.makeText(FoodListXao.this,"Thêm thành công",Toast.LENGTH_SHORT).show();
                    adapterXao.notifyDataSetChanged();
                }
                break;
        }

    }
}
