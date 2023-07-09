package com.hotrodatmon_app.DAL;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import com.hotrodatmon_app.MODEL.Loai2;
import com.hotrodatmon_app.MODEL.MonAn2;
import com.hotrodatmon_app.Util.DatabaseHelper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class dal_monAn2 {


    private DatabaseHelper dbHelper;
    private Context mContext;
    SQLiteDatabase database;

    public dal_monAn2(Context context)
    {
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


    public  List<MonAn2> loadMonAn()
    {
        List<MonAn2> ma = new ArrayList<>();



        //Đây là phần truy vấn: Muốn them xoa sua là làm việc ở chổ này nha.
        Cursor cursor = database.query("MONAN", null, null, null, null, null, null);
        cursor.moveToFirst();
        String data = "";
        while (cursor.isAfterLast() == false) {

            String mamonan = cursor.getString(0);
            String tenMon = cursor.getString(1);
            String hinhanh = cursor.getString(2);
            Integer Dongia = cursor.getInt(3);
            String ghiChu = cursor.getString(4);
            String Mota = cursor.getString(5);
            String maLoai = cursor.getString(6);

            ma.add(new MonAn2(mamonan, tenMon, hinhanh, Dongia, ghiChu, Mota, maLoai));

            //data = cursor.getString(0) + " - " + cursor.getString(1);
          //  Toast.makeText(mContext, data, Toast.LENGTH_LONG).show();
            cursor.moveToNext();
        }
        //---------------------------------------------


        cursor.close();

        return ma;
    }

    public boolean themMA(String maMenu, String tenMonAn, String hinhAnh, int donGia, String ghiChu, String mota,String maLoai) {


            // Thêm mon an mới vào cơ sở dữ liệu
            ContentValues values = new ContentValues();
            values.put("MaMenu", maMenu);
            values.put("TenMonAn", tenMonAn);
            values.put("HinhAnh", hinhAnh);
            values.put("DonGia", donGia);
            values.put("GhiChu", ghiChu);
            values.put("MoTa", mota);
            values.put("MaLoai", maLoai);


            long result = database.insert("MONAN", null, values);


            if (result == -1) {
                return false; // Thêm không thành công
            } else {
                return true; // Thêm thành công
            }

    }
    public boolean kiemTraMaMATonTai(String maMenu) {
        Cursor cursor = null;
        try {
             // Thay thế bằng phương thức khởi tạo/mở database của bạn

            // Kiểm tra xem mã mon an có tồn tại trong cơ sở dữ liệu không
            cursor = database.rawQuery("SELECT * FROM MONAN WHERE MaMenu = ?", new String[]{maMenu});
            return cursor.moveToFirst();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            // Không đóng database ở đây, để cho việc sử dụng tiếp theo
        }
    }
    public boolean deleteMA(String maMa) {

        Cursor cursor = null;


        try {
            dbHelper.openDatabase(mContext);
            database = dbHelper.getData();


            // Kiểm tra xem Mon an có tồn tại trong cơ sở dữ liệu không
            cursor = database.rawQuery("SELECT * FROM MONAN WHERE MaMenu = ?", new String[]{maMa});
            if (cursor.moveToFirst()) {
                int result = database.delete("MONAN", "MaMenu = ?", new String[]{maMa});
                return result > 0;
            } else {
                return false; // Không tìm thấy mã nhân viên trong cơ sở dữ liệu
            }

        } finally {
            if (cursor != null) {
                cursor.close();
            }
            if (database != null) {
                database.close();
            }
        }
    }
    public boolean updateMA(String maMenu, String tenMonAn, String hinhAnh, int donGia, String ghiChu, String mota,String maLoai) {

        Cursor cursor = null;

        try {


            // Kiểm tra xem MÓn ăn có tồn tại trong cơ sở dữ liệu không
            cursor = database.rawQuery("SELECT * FROM MONAN WHERE MaMenu = ?", new String[]{maMenu});
            if (cursor.moveToFirst()) {
                ContentValues values = new ContentValues();
                values.put("TenMonAn", tenMonAn);
                values.put("HinhAnh", hinhAnh);
                values.put("DonGia", donGia);
                values.put("GhiChu", ghiChu);
                values.put("MoTa", mota);
                values.put("MaLoai", maLoai);

                int result = database.update("MONAN", values, "MaMenu = ?", new String[]{maMenu});
                return result > 0;
            } else {
                return false; // Không tìm thấy mã nhân viên trong cơ sở dữ liệu
            }

        } finally {
            if (cursor != null) {
                cursor.close();
            }
            if (database != null) {
                database.close();
            }
        }
    }

    public ArrayList<Loai2> getAllLoai()
    {
        ArrayList<Loai2> loai2List = new ArrayList<>();

        //Fix cứng




        //Đây là phần truy vấn: Muốn them xoa sua là làm việc ở chổ này nha.
        Cursor cursor = database.query("LOAI", null, null, null, null, null, null);
        cursor.moveToFirst();
        String data = "";
        while (cursor.isAfterLast() == false) {

            String tenLoai = cursor.getString(1);
            String maLoai = cursor.getString(0);

            loai2List.add(new Loai2(maLoai,tenLoai));

            data = cursor.getString(0) + " - " + cursor.getString(1);
           // Toast.makeText(mContext, data, Toast.LENGTH_LONG).show();
            cursor.moveToNext();
        }
        //---------------------------------------------


        cursor.close();


        return loai2List;

    }


    public String getIDLoai(String tenLoai)
    {
        Cursor cursor ;



        cursor = database.rawQuery("SELECT MaLoai  FROM LOAI WHERE TenLoai= ?", new String[]{tenLoai});

        String idd="";
        // looping through all rows and adding to list
        while(cursor.moveToNext()) {

            idd = cursor.getString(0); // truy vấn ra mã loai


        }
        // returning labels
        return idd;
    }

    public String generateCode() {

        Cursor cursor = null;
        String employeeCode = null;
        try {

            // Truy vấn để lấy mã món ăn lớn nhất từ database
            cursor = database.rawQuery("SELECT MAX(MaMenu) FROM MonAn", null);
            if (cursor.moveToFirst()) {
                String lastCode = cursor.getString(0);
                if (lastCode != null) {
                    int codeNumber = Integer.parseInt(lastCode.substring(2)) + 1;
                    employeeCode = "TD" + String.format("%02d", codeNumber);
                }
            }

            if (employeeCode == null) {
                employeeCode = "TD01"; // Mã món ăn  đầu tiên nếu chưa có trong database
            }

            return employeeCode;
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            if (database != null) {
                database.close();
            }
        }
    }




}
