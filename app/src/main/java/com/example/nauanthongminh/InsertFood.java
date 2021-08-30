package com.example.nauanthongminh;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class InsertFood extends Activity {
    EditText edtten, edtnl, edthd;
    Button btnSave;
    SQLiteDatabase db;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_them);
        Init();
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = getIntent();
                Bundle bundle = intent.getBundleExtra("data");
                int maL=bundle.getInt("MaL");
                    if(maL==2){
                        Food f = new Food(null,edtten.getText().toString(),edtnl.getText().toString(),
                                edthd.getText().toString());
                        try{
                            db = openOrCreateDatabase(DangNhap.DATABASENAME,MODE_PRIVATE,null);
                            ContentValues values = new ContentValues();
                            values.put("ten",edtten.getText().toString());
                            values.put("nguyenlieu",edtnl.getText().toString());
                            values.put("huongdan",edthd.getText().toString());
                            bundle.putSerializable("food",f);
                            long idd = db.insert("tblxao",null,values);

                                setResult(FoodListXao.SAVECLASSX, intent);
                                finish();
                                //return id;
                        }catch (Exception e){
                            Toast.makeText(getApplication(),"Lỗi "+e,Toast.LENGTH_SHORT).show();
                        }
                    }
                if(maL==1){
                    Food f = new Food(null,edtten.getText().toString(),edtnl.getText().toString(),
                            edthd.getText().toString());
                    try{
                        db = openOrCreateDatabase(DangNhap.DATABASENAME,MODE_PRIVATE,null);
                        ContentValues values = new ContentValues();
                        values.put("ten",edtten.getText().toString());
                        values.put("nguyenlieu",edtnl.getText().toString());
                        values.put("huongdan",edthd.getText().toString());
                        bundle.putSerializable("food",f);
                        long idd = db.insert("tblman",null,values);
                        setResult(FoodList.SAVECLASS, intent);
                        finish();
                        //return id;
                    }catch (Exception e){
                        Toast.makeText(getApplication(),"Lỗi "+e,Toast.LENGTH_SHORT).show();
                    }
                } if(maL==3){
                    Food f = new Food(null,edtten.getText().toString(),edtnl.getText().toString(),
                            edthd.getText().toString());
                    try{
                        db = openOrCreateDatabase(DangNhap.DATABASENAME,MODE_PRIVATE,null);
                        ContentValues values = new ContentValues();
                        values.put("ten",edtten.getText().toString());
                        values.put("nguyenlieu",edtnl.getText().toString());
                        values.put("huongdan",edthd.getText().toString());
                        bundle.putSerializable("food",f);
                        long idd = db.insert("tblluoc",null,values);
                        setResult(FoodListLuoc.SAVECLASSL, intent);
                        finish();
                    }catch (Exception e){
                        Toast.makeText(getApplication(),"Lỗi "+e,Toast.LENGTH_SHORT).show();
                    }
                }
                if(maL==4){
                    Food f = new Food(null,edtten.getText().toString(),edtnl.getText().toString(),
                            edthd.getText().toString());
                    try{
                        db = openOrCreateDatabase(DangNhap.DATABASENAME,MODE_PRIVATE,null);
                        ContentValues values = new ContentValues();
                        values.put("ten",edtten.getText().toString());
                        values.put("nguyenlieu",edtnl.getText().toString());
                        values.put("huongdan",edthd.getText().toString());
                        bundle.putSerializable("food",f);
                        long idd = db.insert("tblcanh",null,values);

                        setResult(FoodListCanh.SAVECLASSC, intent);
                        finish();
                        //return id;
                    }catch (Exception e){
                        Toast.makeText(getApplication(),"Lỗi "+e,Toast.LENGTH_SHORT).show();
                    }
                }
                if(maL==5){
                    Food f = new Food(null,edtten.getText().toString(),edtnl.getText().toString(),
                            edthd.getText().toString());
                    try{
                        db = openOrCreateDatabase(DangNhap.DATABASENAME,MODE_PRIVATE,null);
                        ContentValues values = new ContentValues();
                        values.put("ten",edtten.getText().toString());
                        values.put("nguyenlieu",edtnl.getText().toString());
                        values.put("huongdan",edthd.getText().toString());
                        bundle.putSerializable("food",f);
                        long idd = db.insert("tblchay",null,values);
                        setResult(FoodListChay.SAVECLASSCH, intent);
                        finish();
                        //return id;
                    }catch (Exception e){
                        Toast.makeText(getApplication(),"Lỗi "+e,Toast.LENGTH_SHORT).show();
                    }
                }
                   /* Food f = new Food(id+"",edtten.getText().toString(),edtnl.getText().toString(),
                            edthd.getText().toString());
                    bundle.putSerializable("food",f);
                    intent.putExtra("data",bundle);
                    setResult(FoodList.SAVECLASS, intent);
                    Toast.makeText(getApplication(),"Thêm món ăn thành công !!",Toast.LENGTH_SHORT).show();
                    finish();*/
                }

        });
    }
    private void Init(){
        edtten = (EditText) findViewById(R.id.edtTen);
        edtnl = (EditText)findViewById(R.id.edtNL);
        edthd = (EditText)findViewById(R.id.edtHD);
        btnSave = (Button)findViewById(R.id.btnluu);
    }
//    private long saveFood(){
//        try{
//            db = openOrCreateDatabase(DangNhap.DATABASENAME,MODE_PRIVATE,null);
//            ContentValues values = new ContentValues();
//            values.put("ten",edtten.getText().toString());
//            values.put("nguyenlieu",edtnl.getText().toString());
//            values.put("huongdan",edthd.getText().toString());
//            long id = db.insert("tblman",null,values);
//            if(id!=-1){
//                return id;
//            }
//        }catch (Exception e){
//            Toast.makeText(getApplication(),"Lỗi "+e,Toast.LENGTH_SHORT).show();
//        }
//        return -1;
//    }
}
