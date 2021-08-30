package com.example.nauanthongminh;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.media.Image;
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

public class FoodList extends Activity {
    ListView lstfood;
    ImageButton btnOpenInsertFood, btnBack;
    ArrayList<Food> foodList = new ArrayList<Food>();
    MyAdapter adapterMan;
    SQLiteDatabase db;
    MyADapterDetail detailadapter;
    int posselected =-1;
    static int pos = -1;
    public static final int OPENCLASS = 113;
    public static final int EDITCLASS = 114;
    public static final int SAVECLASS = 115;
    public static final int OPENCLASSDETAIL = 112;
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
                Food food = foodList.get(posselected);
                Bundle bundle = new Bundle();
                Intent intent = new Intent(FoodList.this, FoodDetail.class);
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
                Intent intent = new Intent(FoodList.this, InsertFood.class);
                Bundle bundle=new Bundle();
                bundle.putInt("MaL",1);
                intent.putExtra("data",bundle);
                startActivityForResult(intent, FoodList.OPENCLASS);
            }
        });
    }
    private void getFoodList(){
        try{
            db = openOrCreateDatabase(DangNhap.DATABASENAME,MODE_PRIVATE,null);
            Cursor c = db.query("tblman",null,null,null,null,null,null);
            c.moveToFirst();
            while (!c.isAfterLast()){
                foodList.add(new Food(c.getInt(0)+"",c.getString(1),
                        c.getString(2),c.getString(3)));
                c.moveToNext();
            }
            adapterMan = new MyAdapter(this, android.R.layout.simple_list_item_1,foodList);
            lstfood.setAdapter(adapterMan);

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
                Food food = foodList.get(posselected);
                Bundle bundle = new Bundle();
                Intent intent = new Intent(FoodList.this, EditFood.class);
                bundle.putSerializable("food",food);
                intent.putExtra("data",bundle);
                startActivityForResult(intent,FoodList.EDITCLASS);
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
                String id = foodList.get(posselected).getId();
                if(db.delete("tblman","idman=?", new String []{id}) !=-1){
                    foodList.remove(posselected);
                    adapterMan.notifyDataSetChanged();
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
            case FoodList.OPENCLASS:{
                if(resultCode==FoodList.SAVECLASS){
                    Bundle bundle=data.getBundleExtra("data");
                    Food x=(Food) bundle.getSerializable("food");
                    foodList.add(x);
                    adapterMan.notifyDataSetChanged();
                }
                break;
            }
            case FoodList.EDITCLASS:
                if(resultCode==FoodList.SAVECLASS){
                    Bundle bundle = data.getBundleExtra("data");
                    Food food =(Food)bundle.getSerializable("food");
                    foodList.set(posselected, food);
                    adapterMan.notifyDataSetChanged();
                }
                break;
            case FoodList.OPENCLASSDETAIL:
                if(resultCode==FoodList.SAVECLASS){
                    Bundle bundle = data.getBundleExtra("data");
                    Food food =(Food)bundle.getSerializable("food");
                    foodList.set(posselected, food);
                }
                break;
            }
    }
}
