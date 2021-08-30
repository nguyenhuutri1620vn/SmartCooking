package com.example.nauanthongminh;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class DangKy extends Activity {
    Button btnDangKy;
    EditText edttk, edtmk, edtnlmk;
    TextView txtDN;
    SQLiteDatabase db;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dangky);
        Init();
        txtDN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DangKy.this, DangNhap.class);
                startActivity(intent);
            }
        });
        btnDangKy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!edtmk.getText().toString().equals(edtnlmk.getText().toString())){
                    Toast.makeText(getApplication(), "Nhập lại mật khẩu không trùng khớp", Toast.LENGTH_SHORT).show();
                }else {
                long id = saveClass();
                Bundle bundle = new Bundle();
                Intent intent = getIntent();
                if(id!=-1) {
                    User u = new User(id + "", edttk.getText().toString(), edtmk.getText().toString());
                    bundle.putSerializable("user", u);
                    intent.putExtra("data", bundle);
                    Toast.makeText(getApplication(), "Đăng ký thành công", Toast.LENGTH_SHORT).show();
                    saveClass();
                    finish();
                }
                }
            }
        });
    }
    private long saveClass(){
        try{
            db = openOrCreateDatabase(DangNhap.DATABASENAME, MODE_PRIVATE, null);
            ContentValues values = new ContentValues();
            values.put("username",edttk.getText().toString());
            values.put("password",edtmk.getText().toString());
            long id = db.insert("tblUser",null, values);
            if(id!=-1){
                return id;
            }
        }catch (Exception e){
            Toast.makeText(this,"Lỗi "+e, Toast.LENGTH_SHORT).show();
        }
        return -1;
    }
    private void Init(){
        btnDangKy = (Button)findViewById(R.id.btnDangKy);
        edttk = (EditText)findViewById(R.id.edttaikhoan);
        edtmk = (EditText)findViewById(R.id.edtmatkhau);
        edtnlmk = (EditText)findViewById(R.id.edtnhaplaimatkhau);
        txtDN = (TextView)findViewById(R.id.txtDN);
    }
}
