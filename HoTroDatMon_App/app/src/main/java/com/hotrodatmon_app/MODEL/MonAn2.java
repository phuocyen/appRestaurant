package com.hotrodatmon_app.MODEL;

public class MonAn2 {


    private String maMon;
    private String tenMon;
    private String hinhAnh;
    private Integer donGia;
    private String ghiChu;
    private String moTa;
    private String maLoai;

    public MonAn2() {
    }

    public MonAn2(String maMon, String tenMon, String hinhAnh, Integer donGia, String ghiChu, String moTa, String maLoai) {
        this.maMon = maMon;
        this.tenMon = tenMon;
        this.hinhAnh = hinhAnh;
        this.donGia = donGia;
        this.ghiChu = ghiChu;
        this.moTa = moTa;
        this.maLoai = maLoai;
    }


    public String getMaMon() {
        return maMon;
    }

    public void setMaMon(String maMon) {
        this.maMon = maMon;
    }

    public String getTenMon() {
        return tenMon;
    }

    public void setTenMon(String tenMon) {
        this.tenMon = tenMon;
    }

    public String getHinhAnh() {
        return hinhAnh;
    }

    public void setHinhAnh(String hinhAnh) {
        this.hinhAnh = hinhAnh;
    }

    public double getDonGia() {
        return donGia;
    }

    public void setDonGia(Integer donGia) {
        this.donGia = donGia;
    }

    public String getGhiChu() {
        return ghiChu;
    }

    public void setGhiChu(String ghiChu) {
        this.ghiChu = ghiChu;
    }

    public String getMoTa() {
        return moTa;
    }

    public void setMoTa(String moTa) {
        this.moTa = moTa;
    }

    public String getMaLoai() {
        return maLoai;
    }

    public void setMaLoai(String maLoai) {
        this.maLoai = maLoai;
    }
}
