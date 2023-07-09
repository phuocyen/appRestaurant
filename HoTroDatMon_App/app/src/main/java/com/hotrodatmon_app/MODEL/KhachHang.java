package com.hotrodatmon_app.MODEL;

public class KhachHang {

    String idKH;
    String tenKH;
    String sdt;

    public KhachHang(String idKH, String tenKH, String sdt) {
        this.idKH = idKH;
        this.tenKH = tenKH;
        this.sdt = sdt;
    }

    public KhachHang() {
    }

    public String getIdKH() {
        return idKH;
    }

    public void setIdKH(String idKH) {
        this.idKH = idKH;
    }

    public String getTenKH() {
        return tenKH;
    }

    public void setTenKH(String tenKH) {
        this.tenKH = tenKH;
    }

    public String getSdt() {
        return sdt;
    }

    public void setSdt(String sdt) {
        this.sdt = sdt;
    }



}
