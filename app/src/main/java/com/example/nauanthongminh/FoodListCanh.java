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

public class FoodListCanh extends Activity {
    ListView lstfood;
    ImageButton btnOpenInsertFood, btnBack;
    ArrayList<Food> foodListCanh = new ArrayList<Food>();
    MyAdapter adapterCanh;
    SQLiteDatabase db;
    int posselected =-1;
    public static final int OPENCLASSC = 113;
    public static final int EDITCLASSC = 114;
    public static final int SAVECLASSC = 115;
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
                Food food = foodListCanh.get(posselected);
                Bundle bundle = new Bundle();
                Intent intent = new Intent(FoodListCanh.this, FoodDetail.class);
                bundle.putSerializable("food",food);
                intent.putExtra("data",bundle);
                startActivity(intent);
            }
        });
        btnOpenInsertFood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FoodListCanh.this, InsertFood.class);
                Bundle bundle=new Bundle();
                bundle.putInt("MaL",4);
                intent.putExtra("data",bundle);
                startActivityForResult(intent, FoodListCanh.OPENCLASSC);
            }
        });
    }
    private void getFoodList(){
        try{
            db = openOrCreateDatabase(DangNhap.DATABASENAME,MODE_PRIVATE,null);
            Cursor c = db.query("tblcanh",null,null,null,null,null,null);
            c.moveToFirst();
            while (!c.isAfterLast()){
                foodListCanh.add(new Food(c.getInt(0)+"",c.getString(1),
                        c.getString(2),c.getString(3)));
                c.moveToNext();
            }
            adapterCanh = new MyAdapter(this, android.R.layout.simple_list_item_1,foodListCanh);
            adapterCanh.notifyDataSetChanged();
            lstfood.setAdapter(adapterCanh);

        }catch (Exception e ){
            Toast.makeText(getApplication(),"L???i "+e,Toast.LENGTH_SHORT).show();
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
                Food food = foodListCanh.get(posselected);
                Bundle bundle = new Bundle();
                Intent intent = new Intent(FoodListCanh.this, EditFoodCanh.class);
                bundle.putSerializable("food",food);
                intent.putExtra("data",bundle);
                startActivityForResult(intent,FoodListCanh.EDITCLASSC);
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
        aleBuilder.setTitle("X??c nh???n ????? x??a m??n ??n..!!");
        aleBuilder.setMessage("B???n c?? mu???n ch???c ch???n x??a ? ");
        aleBuilder.setCancelable(false);
        aleBuilder.setPositiveButton("?????ng ??", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                db = openOrCreateDatabase(DangNhap.DATABASENAME,MODE_PRIVATE, null);
                String id = foodListCanh.get(posselected).getId();
                if(db.delete("tblcanh","idcanh=?", new String []{id}) !=-1){
                    foodListCanh.remove(posselected);
                    adapterCanh.notifyDataSetChanged();
                    Toast.makeText(getApplication(), "X??a m??n ??n th??nh c??ng !",Toast.LENGTH_SHORT).show();
                }
            }
        });
        aleBuilder.setNegativeButton("Kh??ng ?????ng ??", new DialogInterface.OnClickListener() {
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
            case FoodListCanh.OPENCLASSC:{
                if(resultCode==FoodListCanh.SAVECLASSC){
                    Bundle bundle=data.getBundleExtra("data");
                    Food x=(Food) bundle.getSerializable("food");
                    foodListCanh.add(x);
                    adapterCanh.notifyDataSetChanged();
                }
                break;
            }
            case FoodListCanh.EDITCLASSC:
                if(resultCode==FoodListCanh.SAVECLASSC){
                    Bundle bundle = data.getBundleExtra("data");
                    Food food =(Food)bundle.getSerializable("food");
                    foodListCanh.set(posselected, food);
                    adapterCanh.notifyDataSetChanged();
                }
                break;
        }

    }
}
