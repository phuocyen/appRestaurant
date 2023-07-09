package com.hotrodatmon_app.MODEL;

public class DoanhThu {
    String thang;
    String data;

    public String getThang() {
        return thang;
    }

    public void setThang(String thang) {
        this.thang = thang;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public DoanhThu() {
    }

    public DoanhThu(String thang, String data) {
        this.thang = thang;
        this.data = data;
    }



}
