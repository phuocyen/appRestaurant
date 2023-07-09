package com.hotrodatmon_app.MODEL;

import java.io.Serializable;
import java.math.BigDecimal;

public class MonAn implements Serializable {
    String idMon;
    String tenMon;
    String hinhMon;
    BigDecimal donGia;
    String ghiChu;
    String moTa;
    String maLoai;

    public MonAn(String idMon, String tenMon, String hinhMon, BigDecimal donGia, String ghiChu, String moTa, String maLoai) {
        this.idMon = idMon;
        this.tenMon = tenMon;
        this.hinhMon = hinhMon;
        this.donGia = donGia;
        this.ghiChu = ghiChu;
        this.moTa = moTa;
        this.maLoai = maLoai;
    }

    public MonAn() {

        this.tenMon = "Hgdfdfg";
        this.hinhMon = "banhxeo.jpg";
        this.donGia = BigDecimal.TEN;

        this.moTa = "fgfgfg";

    }

    public String getIdMon() {
        return idMon;
    }

    public void setIdMon(String idMon) {
        this.idMon = idMon;
    }

    public String getTenMon() {
        return tenMon;
    }

    public void setTenMon(String tenMon) {
        this.tenMon = tenMon;
    }

    public String getHinhMon() {
        return hinhMon;
    }

    public void setHinhMon(String hinhMon) {
        this.hinhMon = hinhMon;
    }

    public BigDecimal getDonGia() {
        return donGia;
    }

    public void setDonGia(BigDecimal donGia) {
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
