package com.hotrodatmon_app.MODEL;

public class PhanQuyen {

    static String IdNV;
    static String tenNV;


    public PhanQuyen(String IdNV,String tenNV) {
        this.IdNV=IdNV;
        this. tenNV=tenNV;
    }

    public static String getIdNV() {
        return IdNV;
    }

    public static void setIdNV(String idNV) {
        IdNV = idNV;
    }

    public static String getTenNV() {
        return tenNV;
    }

    public static void setTenNV(String tenNV) {
        PhanQuyen.tenNV = tenNV;
    }


}
