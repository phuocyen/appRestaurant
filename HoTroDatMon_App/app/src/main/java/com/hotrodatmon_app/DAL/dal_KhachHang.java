package com.hotrodatmon_app.DAL;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import com.hotrodatmon_app.MODEL.BanAn;
import com.hotrodatmon_app.MODEL.KhachHang;
import com.hotrodatmon_app.Util.DatabaseHelper;

import java.io.IOException;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class dal_KhachHang {

    private DatabaseHelper dbHelper;
    private Context mContext;
    SQLiteDatabase database;
    public dal_KhachHang(Context context)
    {
        this.mContext=context;
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
    public void themkhachhang(String tenkh, String sdt,String idBan)
    {
        //TẠO KHÓA TỰ ĐỘNG KHACHHANG
        String query = "SELECT MAX(MaKH) AS MaxMaKH FROM KHACHHANG";
        Cursor cursor = database.rawQuery(query, null);
        cursor.moveToFirst();
        int number = Integer.parseInt(cursor.getString(0).substring(2));
        number++;
        String newMaKH = "KH" + String.format("%02d", number);
        cursor.close();

        //Đây là phần truy vấn: Muốn them xoa sua là làm việc ở chổ này nha.
        ContentValues values = new ContentValues();
        values.put("MaKH", newMaKH);
        values.put("TenKH", tenkh);
        values.put("SDT", sdt);

        if (database.insert("KHACHHANG", null, values)!= -1) {
            Toast.makeText(mContext, "Thêm dữ liệu thành công", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(mContext, "Thêm dữ liệu thất bại", Toast.LENGTH_SHORT).show();
            return;
        }
        // update
        ContentValues valuess = new ContentValues();

        valuess.put("MaKH", newMaKH);
        Calendar currentTime = Calendar.getInstance();
        int hour = currentTime.get(Calendar.HOUR_OF_DAY); // Lấy giờ hiện tại (24 giờ)
        int minute = currentTime.get(Calendar.MINUTE);
        String h=hour+":"+minute;
        valuess.put("GioDat",h);
        Date currentDate = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String formattedDate = formatter.format(currentDate);
        valuess.put("NgayDat",formattedDate.toString());
        valuess.put("GhiChu","Đặt");


        String whereClause = "MaBan = ?";
        String[] whereArgs = {idBan};

        int rowsAffected = database.update("BANAN", valuess, whereClause, whereArgs);

        if (rowsAffected > 0) {
            Toast.makeText(mContext, "Cập nhật dữ liệu thành công", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(mContext, "Cập nhật dữ liệu thất bại", Toast.LENGTH_SHORT).show();
        }


    }

    public void themCTHD(String mahd, String mamenu, String thanhtien, String sl, String dongia) {
        //TẠO KHÓA TỰ ĐỘNG CTHD
        String query = "SELECT MAX(MaCTHD) AS MaxMaCTHD FROM CTHD";
        Cursor cursor = database.rawQuery(query, null);
        cursor.moveToFirst();
        int number = Integer.parseInt(cursor.getString(0).substring(2));
        number++;
        String newMaCTHD = "CT" + String.format("%02d", number);
        cursor.close();
        //THÊM VÀO TRONG CTHD
        ContentValues values = new ContentValues();
        values.put("MaCTHD", newMaCTHD);
        values.put("SoLuong", sl);
        values.put("ThanhTien", thanhtien);
        values.put("DonGia", dongia);
        values.put("MaMenu", mamenu);
        values.put("MaHD", mahd);

        if (database.insert("CTHD", null, values)!= -1) {
            Toast.makeText(mContext, "Thêm món thành công", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(mContext, "Thêm món thất bại", Toast.LENGTH_SHORT).show();
            return;
        }

    }


    public void themhoadon(String tenkh, String sdt,String idBan)
    {
        //TẠO KHÓA TỰ ĐỘNG KHACHHANG
        String query = "SELECT MAX(MaKH) AS MaxMaKH FROM KHACHHANG";
        Cursor cursor = database.rawQuery(query, null);
        cursor.moveToFirst();
        int number = Integer.parseInt(cursor.getString(0).substring(2));
        number++;
        String newMaKH = "KH" + String.format("%02d", number);

        cursor.close();
        //TẠO KHÓA TỰ ĐỘNG HOADON
        String query1 = "SELECT MAX(MaHD) AS MaxMaHD FROM HOADON";
        Cursor cursor1 = database.rawQuery(query1, null);
        cursor1.moveToFirst();
        int number1 = Integer.parseInt(cursor1.getString(0).substring(2));
        number1++;
        String newMaHD = "HD" + String.format("%02d", number1);

        cursor1.close();


        //Đây là phần truy vấn: Muốn them xoa sua là làm việc ở chổ này nha.
        //Thêm khách hàng
        ContentValues values = new ContentValues();
        values.put("MaKH", newMaKH);
        values.put("TenKH", tenkh);
        values.put("SDT", sdt);

        if (database.insert("KHACHHANG", null, values)!= -1) {
            Toast.makeText(mContext, "Thêm dữ liệu thành công", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(mContext, "Thêm dữ liệu thất bại", Toast.LENGTH_SHORT).show();
            return;
        }
        //Thêm hóa đơn
        Date currentDate = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String formattedDate = formatter.format(currentDate);
        ContentValues values1 = new ContentValues();
        values1.put("MaHD", newMaHD);
        values1.put("NgayXuat", formattedDate);
        values1.put("TongTien","0");
        values1.put("GhiChu","Thanh toán");
        values1.put("MaNV","NV01");
        values1.put("MaBan",idBan);


        if (database.insert("HOADON", null, values1)!= -1) {
            Toast.makeText(mContext, "Thêm dữ liệu thành công", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(mContext, "Thêm dữ liệu thất bại", Toast.LENGTH_SHORT).show();
            return;
        }
        // update
        ContentValues valuess = new ContentValues();

        valuess.put("MaKH", newMaKH);
        Calendar currentTime = Calendar.getInstance();
        int hour = currentTime.get(Calendar.HOUR_OF_DAY); // Lấy giờ hiện tại (24 giờ)
        int minute = currentTime.get(Calendar.MINUTE);
        String h=hour+":"+minute;
        valuess.put("GioDat",h);

        String formattedDate1 = formatter.format(currentDate);
        valuess.put("NgayDat",formattedDate1.toString());

        valuess.put("GhiChu","Hoạt động");


        String whereClause = "MaBan = ?";
        String[] whereArgs = {idBan};

        int rowsAffected = database.update("BANAN", valuess, whereClause, whereArgs);

        if (rowsAffected > 0) {
            Toast.makeText(mContext, "Cập nhật dữ liệu thành công", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(mContext, "Cập nhật dữ liệu thất bại", Toast.LENGTH_SHORT).show();
        }


    }


    public void themhoadon2(String idBan) // đã được tạo KHachhang
    {

        //TẠO KHÓA TỰ ĐỘNG HOADON
        String query1 = "SELECT MAX(MaHD) AS MaxMaHD FROM HOADON";
        Cursor cursor1 = database.rawQuery(query1, null);
        cursor1.moveToFirst();
        int number1 = Integer.parseInt(cursor1.getString(0).substring(2));
        number1++;
        String newMaHD = "HD" + String.format("%02d", number1);
        cursor1.close();



        //Thêm hóa đơn
        ContentValues values1 = new ContentValues();
        values1.put("MaHD", newMaHD);

        Date currentDate = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String formattedDate = formatter.format(currentDate);
        values1.put("NgayXuat", formattedDate);
        values1.put("TongTien","0");
        values1.put("GhiChu","Thanh toán");
        values1.put("MaNV","NV01");
        values1.put("MaBan",idBan);


        if (database.insert("HOADON", null, values1)!= -1) {
            Toast.makeText(mContext, "Thêm dữ liệu thành công", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(mContext, "Thêm dữ liệu thất bại", Toast.LENGTH_SHORT).show();
            return;
        }
        // update
        ContentValues valuess = new ContentValues();
        valuess.put("GhiChu","Hoạt động");

        String whereClause = "MaBan = ?";
        String[] whereArgs = {idBan};

        int rowsAffected = database.update("BANAN", valuess, whereClause, whereArgs);

        if (rowsAffected > 0) {
            Toast.makeText(mContext, "Cập nhật dữ liệu thành công", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(mContext, "Cập nhật dữ liệu thất bại", Toast.LENGTH_SHORT).show();
        }


    }

    public List<KhachHang> loadkhachhang(String idKH)
    {
        List<KhachHang> listKH = new ArrayList<>();

        //Fix cứng
        dbHelper = new DatabaseHelper();
        try {
            dbHelper.copyDatabaseFromAssets(mContext);
        } catch (IOException e) {
            e.printStackTrace();
        }
        dbHelper.openDatabase(mContext);
        SQLiteDatabase database =dbHelper.getData();

        //Đây là phần truy vấn: Muốn them xoa sua là làm việc ở chổ này nha.
        String query = "select * from KHACHHANG k where  k.MaKH = '" + idKH + "'";
        Cursor cursor = database.rawQuery(query, null);
        cursor.moveToFirst();
        String data ="";
        while (cursor.isAfterLast() == false) {

            String id=cursor.getString(0);
            String tenKH=cursor.getString(1);
            String sdt = cursor.getString(2);


            KhachHang kh= new KhachHang(id,tenKH,sdt);
            listKH.add(kh);


            //data = cursor.getString(0) + " - " + cursor.getString(1);
            //Toast.makeText(mContext, data, Toast.LENGTH_LONG).show();
            cursor.moveToNext();
        }
        //---------------------------------------------

        cursor.close();
        dbHelper.closeDatabase();
        return listKH;

    }
}
