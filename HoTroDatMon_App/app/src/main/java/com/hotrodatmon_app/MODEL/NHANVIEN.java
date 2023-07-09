package com.hotrodatmon_app.MODEL;

public class NHANVIEN {
    private String MaNV;
    private String TenNV;
    private  String SDT;
    private String CCCD;
    private String TaiKhoan;
    private  String MatKhau;

    public NHANVIEN(String maNV, String tenNV, String SDT, String CCCD, String taiKhoan, String matKhau) {
        MaNV = maNV;
        TenNV = tenNV;
        this.SDT = SDT;
        this.CCCD = CCCD;
        TaiKhoan = taiKhoan;
        MatKhau = matKhau;
    }

    public NHANVIEN() {
    }

    public String getMaNV() {
        return MaNV;
    }

    public void setMaNV(String maNV) {
        MaNV = maNV;
    }

    public String getTenNV() {
        return TenNV;
    }

    public void setTenNV(String tenNV) {
        TenNV = tenNV;
    }

    public String getSDT() {
        return SDT;
    }

    public void setSDT(String SDT) {
        this.SDT = SDT;
    }

    public String getCCCD() {
        return CCCD;
    }

    public void setCCCD(String CCCD) {
        this.CCCD = CCCD;
    }

    public String getTaiKhoan() {
        return TaiKhoan;
    }

    public void setTaiKhoan(String taiKhoan) {
        TaiKhoan = taiKhoan;
    }

    public String getMatKhau() {
        return MatKhau;
    }

    public void setMatKhau(String matKhau) {
        MatKhau = matKhau;
    }
}

