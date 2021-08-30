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
import android.widget.Toast;

import androidx.annotation.Nullable;

public class EditFoodChay extends Activity {
    EditText edtten, edtnl, edthd;
    Button btnSave;
    String id;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chinhsua);
        Init();
        getData();
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                Intent intent = getIntent();
                if(saveFood()){
                    Food f = new Food(id,edtten.getText().toString(),edtnl.getText().toString(),
                            edthd.getText().toString());
                    bundle.putSerializable("food",f);
                    intent.putExtra("data",bundle);
                    setResult(FoodListChay.SAVECLASSCH, intent);
                    Toast.makeText(getApplication(),"Cập nhật thành công",Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        });
    }
    private void Init(){
        edtten = (EditText) findViewById(R.id.edtTen);
        edtnl = (EditText)findViewById(R.id.edtNL);
        edthd = (EditText)findViewById(R.id.edtHD);
        btnSave = (Button)findViewById(R.id.btncapnhat);
    }
    private void getData(){
        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra("data");
        Food food = (Food)bundle.getSerializable("food");
        id = food.getId();
        edtten.setText(food.getTen());
        edtnl.setText(food.getNguyenlieu());
        edthd.setText(food.getHuongdan());
    }
    private boolean saveFood(){
        try {
            SQLiteDatabase db = openOrCreateDatabase(DangNhap.DATABASENAME, MODE_PRIVATE, null);
            ContentValues values = new ContentValues();
            values.put("ten", edtten.getText().toString());
            values.put("nguyenlieu", edtnl.getText().toString());
            values.put("huongdan", edthd.getText().toString());
            if (db.update("tblchay", values, "idchay=?", new String[]{id}) != -1)
                return true;

        }catch (Exception e) {
            Toast.makeText(getApplication(), "Lỗi " + e, Toast.LENGTH_SHORT).show();
        }
        return false;
    }
}
