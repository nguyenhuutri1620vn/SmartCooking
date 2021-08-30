package com.example.nauanthongminh;

import java.io.Serializable;

public class Food implements Serializable {
    String id;
    String ten;
    String nguyenlieu;
    String huongdan;

    public Food(String id, String ten, String nguyenlieu, String huongdan) {
        this.id = id;
        this.ten = ten;
        this.nguyenlieu = nguyenlieu;
        this.huongdan = huongdan;
    }

    public Food(String ten, String nguyenlieu, String huongdan) {
        this.ten = ten;
        this.nguyenlieu = nguyenlieu;
        this.huongdan = huongdan;
    }

    public Food(String id,String ten) {
        this.ten = ten;
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTen() {
        return ten;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }

    public String getNguyenlieu() {
        return nguyenlieu;
    }

    public void setNguyenlieu(String nguyenlieu) {
        this.nguyenlieu = nguyenlieu;
    }

    public String getHuongdan() {
        return huongdan;
    }

    public void setHuongdan(String huongdan) {
        this.huongdan = huongdan;
    }

    @Override
    public String toString() {
        return "Food{" +
                "id=" + id +
                ", ten='" + ten + '\'' +
                ", nguyenlieu='" + nguyenlieu + '\'' +
                ", huongdan='" + huongdan + '\'' +
                '}';
    }
}
