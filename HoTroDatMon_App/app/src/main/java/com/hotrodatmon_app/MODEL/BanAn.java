package com.hotrodatmon_app.MODEL;

import java.io.Serializable;
import java.sql.Time;
import java.util.Date;

public class BanAn implements Serializable {

    String maban;
    String tenban;
    String ngaydat;
    String giodat;
    int Soluong;
    String ghichu;
    String makh;

    public BanAn() {
    }

    public BanAn(String maban, String tenban, String ngaydat, String giodat, int soluong, String ghichu, String makh) {
        this.maban = maban;
        this.tenban = tenban;
        this.ngaydat = ngaydat;
        this.giodat = giodat;
        Soluong = soluong;
        this.ghichu = ghichu;
        this.makh = makh;
    }

    public String getMaban() {
        return maban;
    }

    public void setMaban(String maban) {
        this.maban = maban;
    }

    public String getTenban() {
        return tenban;
    }

    public void setTenban(String tenban) {
        this.tenban = tenban;
    }

    public String getNgaydat() {
        return ngaydat;
    }

    public void setNgaydat(String ngaydat) {
        this.ngaydat = ngaydat;
    }

    public String getGiodat() {
        return giodat;
    }

    public void setGiodat(String giodat) {
        this.giodat = giodat;
    }

    public int getSoluong() {
        return Soluong;
    }

    public void setSoluong(int soluong) {
        Soluong = soluong;
    }

    public String getGhichu() {
        return ghichu;
    }

    public void setGhichu(String ghichu) {
        this.ghichu = ghichu;
    }

    public String getMakh() {
        return makh;
    }

    public void setMakh(String makh) {
        this.makh = makh;
    }




}
