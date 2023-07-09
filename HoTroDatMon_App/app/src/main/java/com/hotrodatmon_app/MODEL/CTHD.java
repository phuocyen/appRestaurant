package com.hotrodatmon_app.MODEL;

import java.math.BigDecimal;

public class CTHD {

    String maCTHD;
    String sl;
    BigDecimal thanhtien;
    BigDecimal dongia;
    String maMenu;
    String maHD;

    public CTHD() {
    }

    public CTHD(String maCTHD, String sl, BigDecimal thanhtien, BigDecimal dongia, String maMenu, String maHD) {
        this.maCTHD = maCTHD;
        this.sl = sl;
        this.thanhtien = thanhtien;
        this.dongia = dongia;
        this.maMenu = maMenu;
        this.maHD = maHD;
    }

    public String getMaCTHD() {
        return maCTHD;
    }

    public void setMaCTHD(String maCTHD) {
        this.maCTHD = maCTHD;
    }

    public String getSl() {
        return sl;
    }

    public void setSl(String sl) {
        this.sl = sl;
    }

    public BigDecimal getThanhtien() {
        return thanhtien;
    }

    public void setThanhtien(BigDecimal thanhtien) {
        this.thanhtien = thanhtien;
    }

    public BigDecimal getDongia() {
        return dongia;
    }

    public void setDongia(BigDecimal dongia) {
        this.dongia = dongia;
    }

    public String getMaMenu() {
        return maMenu;
    }

    public void setMaMenu(String maMenu) {
        this.maMenu = maMenu;
    }

    public String getMaHD() {
        return maHD;
    }

    public void setMaHD(String maHD) {
        this.maHD = maHD;
    }

   

}
