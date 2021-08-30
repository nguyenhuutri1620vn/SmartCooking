package com.example.nauanthongminh;

import java.io.Serializable;

public class User implements Serializable {
    String idKH;
    String taikhoan;
    String matkhau;

    public User(String idKH, String taikhoan, String matkhau) {
        this.idKH = idKH;
        this.taikhoan = taikhoan;
        this.matkhau = matkhau;
    }

    public User(String taikhoan, String matkhau) {
        this.taikhoan = taikhoan;
        this.matkhau = matkhau;
    }

    public String getIdKH() {
        return idKH;
    }

    public void setIdKH(String idKH) {
        this.idKH = idKH;
    }

    public String getTaikhoan() {
        return taikhoan;
    }

    public void setTaikhoan(String taikhoan) {
        this.taikhoan = taikhoan;
    }

    public String getMatkhau() {
        return matkhau;
    }

    public void setMatkhau(String matkhau) {
        this.matkhau = matkhau;
    }
}
