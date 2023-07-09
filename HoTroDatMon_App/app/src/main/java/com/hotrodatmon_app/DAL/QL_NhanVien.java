package com.hotrodatmon_app.DAL;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;


import com.hotrodatmon_app.MODEL.NHANVIEN;
import com.hotrodatmon_app.Util.DatabaseHelper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class QL_NhanVien {
    private DatabaseHelper dbHelper;
    private Context mContext;
    SQLiteDatabase database;

    public QL_NhanVien(Context context) {

        mContext=context;
        getCSDL();
    }

    void getCSDL()
    {
        dbHelper = new DatabaseHelper();
        try {
            dbHelper.copyDatabaseFromAssets(mContext);
        } catch (IOException e) {
            e.printStackTrace();
        }
        dbHelper.openDatabase(mContext);
        database =dbHelper.getData();
    }
    public String generateEmployeeCode() {
        Cursor cursor = null;
        String employeeCode = null;
        try {


            // Truy vấn để lấy mã nhân viên lớn nhất từ database
            cursor = database.rawQuery("SELECT MAX(MaNV) FROM NHANVIEN", null);
            if (cursor.moveToFirst()) {
                String lastCode = cursor.getString(0);
                if (lastCode != null) {
                    int codeNumber = Integer.parseInt(lastCode.substring(2)) + 1;
                    employeeCode = "NV" + String.format("%02d", codeNumber);
                }
            }

            if (employeeCode == null) {
                employeeCode = "NV01"; // Mã nhân viên đầu tiên nếu chưa có trong database
            }

            return employeeCode;
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            if (database != null) {

            }

        }
    }



    public List<NHANVIEN> loadNhanVien()
    {
        List<NHANVIEN> list=new ArrayList<>();
        //Fix cứng


        //Đây là phần truy vấn: Muốn them xoa sua là làm việc ở chổ này nha.
        Cursor cursor = database.query("NHANVIEN", null, null, null, null, null, null);
        cursor.moveToFirst();
        list.clear(); //xoa du lieu cu de tran  h hien thi lai

        for(int i = 0; i < cursor.getCount(); i++){
            cursor.moveToPosition(i);
                String manv = cursor.getString(0);
            String ten = cursor.getString(1);
            String sdt = cursor.getString(2);
            String cccd = cursor.getString(3);
            String tk = cursor.getString(4);
            String mk = cursor.getString(5);

            list.add(new NHANVIEN(manv, ten, sdt, cccd, tk,mk));
        }


        //---------------------------------------------

        cursor.close();

    return  list;
    }

    public boolean kiemTraMaNVTonTai(String maNV) {

        Cursor cursor = null;
        try {


            // Kiểm tra xem mã nhân viên có tồn tại trong cơ sở dữ liệu không
            cursor = database.rawQuery("SELECT * FROM NHANVIEN WHERE MaNV = ?", new String[]{maNV});
            return cursor.moveToFirst();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            if (database != null) {

            }

        }
    }


    public boolean deleteNV(String maNV) {

        Cursor cursor = null;
        try {


            // Kiểm tra xem nhân viên có tồn tại trong cơ sở dữ liệu không
            cursor = database.rawQuery("SELECT * FROM NHANVIEN WHERE MaNV = ?", new String[]{maNV});
            if (cursor.moveToFirst()) {
                int result = database.delete("NHANVIEN", "MaNV = ?", new String[]{maNV});
                return result > 0;
            } else {
                return false; // Không tìm thấy mã nhân viên trong cơ sở dữ liệu
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            if (database != null) {

            }

        }
    }

    public boolean updateNV(String maNV, String tenNV, String dienThoai, String cccd, String taiKhoan, String matKhau) {

        Cursor cursor = null;
        try {

            // Kiểm tra xem nhân viên có tồn tại trong cơ sở dữ liệu không
            cursor = database.rawQuery("SELECT * FROM NHANVIEN WHERE MaNV = ?", new String[]{maNV});
            if (cursor.moveToFirst()) {
                ContentValues values = new ContentValues();
                values.put("TenNV", tenNV);
                values.put("SDT", dienThoai);
                values.put("CCCD", cccd);
                values.put("TaiKhoan", taiKhoan);
                values.put("MatKhau", matKhau);

                int result = database.update("NHANVIEN", values, "MaNV = ?", new String[]{maNV});
                return result > 0;
            } else {
                return false; // Không tìm thấy mã nhân viên trong cơ sở dữ liệu
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            if (database != null) {

            }

        }
    }

    public boolean addNV(String maNV, String tenNV, String dienThoai, String cccd, String taiKhoan, String matKhau) {

        // Thêm nhân viên mới vào cơ sở dữ liệu
        ContentValues values = new ContentValues();
        values.put("MaNV", maNV);
        values.put("TenNV", tenNV);
        values.put("SDT", dienThoai);
        values.put("CCCD", cccd);
        values.put("TaiKhoan", taiKhoan);
        values.put("MatKhau", matKhau);
        long result = database.insert("NHANVIEN", null, values);

        if (result == -1) {
            return false; // Thêm không thành công
        } else {
            return true; // Thêm thành công
        }



    }

}
