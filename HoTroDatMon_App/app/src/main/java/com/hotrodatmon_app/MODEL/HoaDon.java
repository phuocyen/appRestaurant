package com.hotrodatmon_app.MODEL;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class HoaDon implements Serializable {

    String MaHD;
    Date ngayxuat;
    BigDecimal tongtien;
    String ghichu;
    String maNV;
    String maban;

    public HoaDon() {
    }

    public HoaDon(String maHD, Date ngayxuat, BigDecimal tongtien, String ghichu, String maNV, String maban) {
        MaHD = maHD;
        this.ngayxuat = ngayxuat;
        this.tongtien = tongtien;
        this.ghichu = ghichu;
        this.maNV = maNV;
        this.maban = maban;
    }

    public String getMaHD() {
        return MaHD;
    }

    public void setMaHD(String maHD) {
        MaHD = maHD;
    }

    public Date getNgayxuat() {
        return ngayxuat;
    }

    public void setNgayxuat(Date ngayxuat) {
        this.ngayxuat = ngayxuat;
    }

    public BigDecimal getTongtien() {
        return tongtien;
    }

    public void setTongtien(BigDecimal tongtien) {
        this.tongtien = tongtien;
    }

    public String getGhichu() {
        return ghichu;
    }

    public void setGhichu(String ghichu) {
        this.ghichu = ghichu;
    }

    public String getMaNV() {
        return maNV;
    }

    public void setMaNV(String maNV) {
        this.maNV = maNV;
    }

    public String getMaban() {
        return maban;
    }

    public void setMaban(String maban) {
        this.maban = maban;
    }


}
