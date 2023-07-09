package com.hotrodatmon_app.DAL;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.hotrodatmon_app.MODEL.LOAI;
import com.hotrodatmon_app.Util.DatabaseHelper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class QL_Loai {

    private DatabaseHelper dbHelper;
    private Context mContext;
    SQLiteDatabase database;
    public QL_Loai(Context context) {

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
            dbHelper.openDatabase(mContext);
            database = dbHelper.getData();

            // Truy vấn để lấy mã loại lớn nhất từ database
            cursor = database.rawQuery("SELECT MAX(MaLoai) FROM LOAI", null);
            if (cursor.moveToFirst()) {
                String lastCode = cursor.getString(0);
                if (lastCode != null) {
                    int codeNumber = Integer.parseInt(lastCode.substring(2)) + 1;
                    employeeCode = "LL" + String.format("%02d", codeNumber);
                }
            }
            if (employeeCode == null) {
                employeeCode = "LL01"; // Mã loai đầu tiên nếu chưa có trong database
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


    public List<LOAI> loadLoai()
    {
        List<LOAI> list=new ArrayList<>();
        //Fix cứng


        //Đây là phần truy vấn: Muốn them xoa sua là làm việc ở chổ này nha.
        Cursor cursor = database.query("LOAI", null, null, null, null, null, null);
        cursor.moveToFirst();
        list.clear(); //xoa du lieu cu de tran  h hien thi lai

        for(int i = 0; i < cursor.getCount(); i++){
            cursor.moveToPosition(i);
            String maloai = cursor.getString(0);
            String ten = cursor.getString(1);

            list.add(new LOAI(maloai, ten));
        }
        //---------------------------------------------

        cursor.close();

        return  list;
    }
    public boolean themLOAI(String maLOAI, String tenLOAI) {


        // Thêm loại mới vào cơ sở dữ liệu
        ContentValues values = new ContentValues();
        values.put("MaLoai", maLOAI);
        values.put("TenLoai", tenLOAI);

        long result = database.insert("LOAI", null, values);


        if (result == -1) {
            return false; // Thêm không thành công
        } else {
            return true; // Thêm thành công
        }
    }
    public boolean kiemTraMaLOAITonTai(String maLOAI) {

        Cursor cursor = null;
        try {
            dbHelper.openDatabase(mContext);
            database = dbHelper.getData();

            // Kiểm tra xem mã loại có tồn tại trong cơ sở dữ liệu không
            cursor = database.rawQuery("SELECT * FROM LOAI WHERE MaLoai = ?", new String[]{maLOAI});
            return cursor.moveToFirst();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            if (database != null) {

            }

        }
    }

    public boolean deleteLOAI(String maLOAI) {
        SQLiteDatabase db = null;
        Cursor cursor = null;
        try {
            dbHelper.openDatabase(mContext);
            db = dbHelper.getData();

            // Kiểm tra xem loại có tồn tại trong cơ sở dữ liệu không
            cursor = db.rawQuery("SELECT * FROM LOAI WHERE MaLoai = ?", new String[]{maLOAI});
            if (cursor.moveToFirst()) {
                int result = db.delete("LOAI", "MaLoai = ?", new String[]{maLOAI});
                return result > 0;
            } else {
                return false; // Không tìm thấy mã loại trong cơ sở dữ liệu
            }

        } finally {
            if (cursor != null) {
                cursor.close();
            }
            if (db != null) {
                db.close();
            }

        }
    }
    public boolean updateLoai(String maLOAI, String tenLOAI) {
        SQLiteDatabase db = null;
        Cursor cursor = null;
        try {
            dbHelper.openDatabase(mContext);
            db = dbHelper.getData();

            // Kiểm tra xem loai có tồn tại trong cơ sở dữ liệu không
            cursor = db.rawQuery("SELECT * FROM LOAI WHERE MaLoai = ?", new String[]{maLOAI});
            if (cursor.moveToFirst()) {
                ContentValues values = new ContentValues();
                values.put("TenLoai", tenLOAI);

                int result = db.update("LOAI", values, "MaLoai = ?", new String[]{maLOAI});
                return result > 0;
            } else {
                return false; // Không tìm thấy mã loai trong cơ sở dữ liệu
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            if (db != null) {
                db.close();
            }

        }
    }
}
