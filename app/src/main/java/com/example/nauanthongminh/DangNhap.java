package com.example.nauanthongminh;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
public class DangNhap extends Activity {
    public static final String DATABASENAME = "db.db";
    SQLiteDatabase db;
    Button btndangnhap;
    TextView txtdk;
    EditText edttk, edtmk;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dangnhap);
        Init();
        Data();
        txtdk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DangNhap.this, DangKy.class);
                startActivity(intent);
            }
        });
        btndangnhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user =edttk.getText().toString();
                String pass =edtmk.getText().toString();
                if(user.isEmpty()){
                    Toast.makeText(getApplication(),"Vui lòng nhập tên tài khoản",Toast.LENGTH_SHORT).show();
                    edttk.requestFocus();
                }
                if(pass.isEmpty()){
                    Toast.makeText(getApplication(),"Vui lòng nhập mật khẩu",Toast.LENGTH_SHORT).show();
                    edttk.requestFocus();
                }
                else if(isUser(edttk.getText().toString(), edtmk.getText().toString())){
                    Intent intent = new Intent(DangNhap.this, MainActivity.class);
                    startActivity(intent);
                }
                else{
                    Toast.makeText(getApplication(),"Sai tài khoản hoặc mật khẩu",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    private void Init(){
        btndangnhap = (Button)findViewById(R.id.btnDangNhap);
        txtdk = (TextView)findViewById(R.id.txtDK);
        edttk = (EditText) findViewById(R.id.edttaikhoan);
        edtmk = (EditText)findViewById(R.id.edtmatkhau);
    }
    public void Data(){
        db =openOrCreateDatabase(DATABASENAME, MODE_PRIVATE, null);
        String sql;
        try{
            if(!isTableExists(db,"tblUser")){
                sql = "CREATE TABLE tblUser (id_user INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,";
                sql +="username TEXT NOT NULL,";
                sql +="password TEXT NOT NULL)";
                db.execSQL(sql);
                sql = "insert into tbluser (username, password) values ('admin','admi')";;
                db.execSQL(sql);
            }
            if(!isTableExists(db,"tblman")){
                sql = "CREATE TABLE tblman (idman INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,";
                sql+= "ten TEXT NOT NULL,";
                sql+= "nguyenlieu TEXT,";
                sql+= "huongdan TEXT)";
                db.execSQL(sql);
            }
            if(!isTableExists(db,"tblcanh")){
                sql = "CREATE TABLE tblcanh (idcanh INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,";
                sql+= "ten TEXT NOT NULL,";
                sql+= "nguyenlieu TEXT,";
                sql+= "huongdan TEXT )";
                db.execSQL(sql);
            }
            if(!isTableExists(db,"tblxao")){
                sql = "CREATE TABLE tblxao (idxao INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,";
                sql+= "ten TEXT NOT NULL,";
                sql+= "nguyenlieu TEXT ,";
                sql+= "huongdan TEXT )";
                db.execSQL(sql);

            }
            if(!isTableExists(db,"tblchay")){
                sql = "CREATE TABLE tblchay (idchay INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,";
                sql+= "ten TEXT NOT NULL,";
                sql+= "nguyenlieu TEXT ,";
                sql+= "huongdan TEXT )";
                db.execSQL(sql);
            }
            if(!isTableExists(db,"tblluoc")){
                sql = "CREATE TABLE tblluoc (idluoc INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,";
                sql+= "ten TEXT NOT NULL,";
                sql+= "nguyenlieu TEXT,";
                sql+= "huongdan TEXT )";
                db.execSQL(sql);
            }
        }catch (Exception e){
            Toast.makeText(this,"Khởi tạo CSDL không thành công", Toast.LENGTH_SHORT).show();
        }
    }
    private boolean isTableExists(SQLiteDatabase db, String tableName) {
        Cursor cursor = db.rawQuery("select DISTINCT tbl_name from sqlite_master where tbl_name"+
                "= '"+tableName+"'",null);
        if(cursor!=null){
            if(cursor.getCount()>0){
                cursor.close();
                return true;
            }
            cursor.close();
        }
        return false;
    }
    private boolean isUser(String username, String password){
        try{
            db = openOrCreateDatabase(DATABASENAME, MODE_PRIVATE, null);
            Cursor c = db.rawQuery("select * from tblUser where username=? and password=?",
                    new String[] {username, password});
            c.moveToFirst();
            if(c.getCount()>0){
                return true;
            }
        } catch (Exception e) {
            Toast.makeText(this,"Lỗi "+e, Toast.LENGTH_SHORT).show();
        }
        return false;
    }
}
