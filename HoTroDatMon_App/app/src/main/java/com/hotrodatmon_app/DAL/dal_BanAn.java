package com.hotrodatmon_app.DAL;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import com.hotrodatmon_app.MODEL.BanAn;
import com.hotrodatmon_app.MODEL.MonAn;
import com.hotrodatmon_app.Util.DatabaseHelper;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class dal_BanAn {
    private DatabaseHelper dbHelper;
    private Context mContext;
    SQLiteDatabase database;

    public dal_BanAn(Context context)
    {
        this.mContext=context;
        getCSDL();
    }


    public void huyDatban(String idban)
    {
        ContentValues valuess = new ContentValues();
        Calendar currentTime = Calendar.getInstance();
        valuess.put("MaKH", "NULL");
        valuess.put("GhiChu", "Trống");

        String whereClause = "MaBan = ?";
        String[] whereArgs = {idban};

        int rowsAffected = database.update("BANAN", valuess, whereClause, whereArgs);

        if (rowsAffected > 0) {
            Toast.makeText(mContext, "Hủy bàn thành công", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(mContext, "Hủy bàn thất bại", Toast.LENGTH_SHORT).show();
        }
    }


    public void thanhtoan(String tenban,String idhd)
    {
        ContentValues valuess = new ContentValues();
        valuess.put("GhiChu", "Trống");
        valuess.put("MaKH", "NULL");

        String whereClause = "TenBan = ?";
        String[] whereArgs = {tenban};

        int rowsAffected = database.update("BANAN", valuess, whereClause, whereArgs);


        ContentValues valuess1 = new ContentValues();
        valuess1.put("GhiChu", "Đã thanh toán");

        String whereClause1 = "MaHD = ?";
        String[] whereArgs1 = {idhd};

        int rowsAffected1 = database.update("HOADON", valuess1, whereClause1, whereArgs1);


        if (rowsAffected > 0 && rowsAffected1 > 0) {
            Toast.makeText(mContext, "Hẹn gặp lại quý khách", Toast.LENGTH_SHORT).show();
        }
    }

    public void delete_CTHD (String maCTHD)
    {
        String whereClause = "MaCTHD = ?";
        String[] whereArgs = {maCTHD};

        int rowsAffected = database.delete("CTHD", whereClause, whereArgs);

        if (rowsAffected > 0) {
            Toast.makeText(mContext, "Hủy bàn thành công", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(mContext, "Hủy bàn thất bại", Toast.LENGTH_SHORT).show();
        }
    }

    public void update_CTHD (String maCTHD, int sl, BigDecimal thanhtien)
    {
        ContentValues valuess = new ContentValues();
        valuess.put("SoLuong", sl);
        valuess.put("ThanhTien", thanhtien.toString());

        String whereClause = "MaCTHD = ?";
        String[] whereArgs = {maCTHD};

        int rowsAffected = database.update("CTHD", valuess, whereClause, whereArgs);

        if (rowsAffected > 0) {
            Toast.makeText(mContext, "Thay đổi thành công", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(mContext, "Thay đổi thất bại", Toast.LENGTH_SHORT).show();
        }
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



    public List<BanAn> loadbanan()
    {
        List<BanAn> listBanan = new ArrayList<>();

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
        Cursor cursor = database.query("BANAN", null, null, null, null, null, null);
        cursor.moveToFirst();
        String data ="";
        while (cursor.isAfterLast() == false) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
            Date ngaydat = null;
             Time time = null;
            String id=cursor.getString(0);
            String tenban=cursor.getString(1);
            //Get ngày
            String ngaydatString = cursor.getString(2);

            //Get giờ
            String timeString = cursor.getString(3); // Chuỗi thời gian lấy từ cột



            int sl=cursor.getInt(4);

            String ghichu=cursor.getString(5);
            String makh=cursor.getString(6);
            BanAn banan= new BanAn(id,tenban,ngaydatString,timeString,sl,ghichu,makh);
            listBanan.add(banan);


            //data = cursor.getString(0) + " - " + cursor.getString(1);
            //Toast.makeText(mContext, data, Toast.LENGTH_LONG).show();
            cursor.moveToNext();
        }
        //---------------------------------------------

        cursor.close();
        dbHelper.closeDatabase();
        return listBanan;

    }


    public List<BanAn> loadbanan_state(String trangthai)
    {
        List<BanAn> listBanan = new ArrayList<>();

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
        String query = "select * from BANAN b where  b.GhiChu = '" + trangthai + "'";
        Cursor cursor = database.rawQuery(query, null);
        cursor.moveToFirst();
        String data ="";
        while (cursor.isAfterLast() == false) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
            Date ngaydat = null;
            Time time = null;
            String id=cursor.getString(0);
            String tenban=cursor.getString(1);
            //Get ngày
            String ngaydatString = cursor.getString(2);

            //Get giờ
            String timeString = cursor.getString(3); // Chuỗi thời gian lấy từ cột


            int sl=cursor.getInt(4);

            String ghichu=cursor.getString(5);
            String makh=cursor.getString(6);
            BanAn banan= new BanAn(id,tenban,ngaydatString,timeString,sl,ghichu,makh);
            listBanan.add(banan);


            //data = cursor.getString(0) + " - " + cursor.getString(1);
            //Toast.makeText(mContext, data, Toast.LENGTH_LONG).show();
            cursor.moveToNext();
        }
        //---------------------------------------------

        cursor.close();
        dbHelper.closeDatabase();
        return listBanan;

    }

}
