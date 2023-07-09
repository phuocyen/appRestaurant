package com.hotrodatmon_app.DAL;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import com.hotrodatmon_app.Adapter.HoaDon_adapter;
import com.hotrodatmon_app.MODEL.CTHD;
import com.hotrodatmon_app.MODEL.DoanhThu;
import com.hotrodatmon_app.MODEL.HoaDon;
import com.hotrodatmon_app.MODEL.MonAn;
import com.hotrodatmon_app.MODEL.NHANVIEN;
import com.hotrodatmon_app.Util.DatabaseHelper;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class dal_MonAn {
    private DatabaseHelper dbHelper;
    private Context mContext;
    SQLiteDatabase database;
    public dal_MonAn(Context context)
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


    public boolean kiemTraAcccout(String tenacc, String pss) {
        Cursor cursor = null;
        try {
            cursor = database.rawQuery("SELECT * FROM NHANVIEN WHERE TaiKhoan = ? AND MatKhau = ?", new String[]{tenacc, pss});
            return cursor.moveToFirst();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            // Không đóng database ở đây, để cho việc sử dụng tiếp theo
        }
    }


    public List<NHANVIEN> loadNV(String tenacc, String pss )
    {
        List<NHANVIEN>  listNV=new ArrayList<>();
        Cursor cursor = null;
        cursor = database.rawQuery("SELECT * FROM NHANVIEN WHERE TaiKhoan = ? AND MatKhau = ?", new String[]{tenacc, pss});
        cursor.moveToFirst();
        String data ="";
        while (cursor.isAfterLast() == false) {

            String id=cursor.getString(0);
            String ten=cursor.getString(1);
            String sdt=cursor.getString(2);
            String cccd=cursor.getString(3);
            String tkhoan=cursor.getString(4);
            String pass=cursor.getString(5);
            NHANVIEN nv= new NHANVIEN(id,ten,sdt,cccd,tkhoan,pass);
            listNV.add(nv);


            cursor.moveToNext();
            break;
        }
        //---------------------------------------------


        cursor.close();
        dbHelper.closeDatabase();
        return listNV;

    }

    public List<MonAn> loadmonan(String mamon)
    {
        List<MonAn> listmonan=new ArrayList<>();
        String query = "select * from MONAN m where m.MaMenu = '" + mamon + "'";
        Cursor cursor = database.rawQuery(query, null);
        cursor.moveToFirst();
        String data ="";
        while (cursor.isAfterLast() == false) {

            String id=cursor.getString(0);
            String ten=cursor.getString(1);
           // Toast.makeText(mContext, ten, Toast.LENGTH_LONG).show();
            String hinh=cursor.getString(2);
            String ghichu=cursor.getString(4);
            BigDecimal gia=new BigDecimal(cursor.getString(3));
            BigDecimal dongia=gia;
            String mota=cursor.getString(5);
            String maloai=cursor.getString(6);
            MonAn ma= new MonAn(id,ten,hinh,gia,ghichu,mota,maloai);
            listmonan.add(ma);

            data = cursor.getString(0) + " - " + cursor.getString(1);
            //Toast.makeText(mContext, data, Toast.LENGTH_LONG).show();
            cursor.moveToNext();
        }
        //---------------------------------------------


        cursor.close();
        dbHelper.closeDatabase();
        return listmonan;

    }


    public List<CTHD> loadCTHD(String mahd)
    {
        List<CTHD> listCTHD = new ArrayList<>();
        String query = "SELECT * FROM CTHD WHERE MaHD = '" + mahd + "'";
        Cursor cursor = database.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                String idmcthd = cursor.getString(0);
                String sl = cursor.getString(1);
                BigDecimal bigDecimal1 = new BigDecimal(cursor.getString(2));
                BigDecimal thanhtien = bigDecimal1;
                BigDecimal bigDecimal = new BigDecimal(cursor.getString(3));
                BigDecimal dongia = bigDecimal;
                String mamenu = cursor.getString(4);
                String mahdd = cursor.getString(5);

                CTHD cthd = new CTHD(idmcthd, sl, thanhtien, dongia, mamenu, mahd);
                listCTHD.add(cthd);

                String data = cursor.getString(0) + " - " + cursor.getString(1);
                //Toast.makeText(mContext, data, Toast.LENGTH_LONG).show();
            } while (cursor.moveToNext());
        }

        cursor.close();
        dbHelper.closeDatabase();
        return listCTHD;

    }


    public List<HoaDon> loadHoaDon(String maban)
    {
        List<HoaDon> listHD = new ArrayList<>();
        String query = "SELECT * FROM HOADON WHERE MaBan = '" + maban + "' AND GhiChu = 'Thanh toán'";
        Cursor cursor = database.rawQuery(query, null);
        cursor.moveToFirst();

        if (cursor.getCount() > 0) {
            while (!cursor.isAfterLast()) {
                String MaHD = cursor.getString(0);
                Date ngayxuat = new Date(cursor.getLong(1));
                BigDecimal tongtien;
                if(cursor.getString(2) =="null")
                {
                     tongtien = new BigDecimal(BigInteger.ZERO);
                    // Toast.makeText(mContext, "TH1", Toast.LENGTH_LONG).show();
                }
                else
                {
                    //Toast.makeText(mContext, "TH2", Toast.LENGTH_LONG).show();
                     tongtien = new BigDecimal(cursor.getString(2));
                }

                String ghichu = cursor.getString(3);
                String maNV = cursor.getString(4);
                String mabann = cursor.getString(5);

                HoaDon hoaDon = new HoaDon(MaHD, ngayxuat, tongtien, ghichu, maNV, mabann);
                listHD.add(hoaDon);

                cursor.moveToNext();
            }
        }
        cursor.close();
        dbHelper.closeDatabase();
        return listHD;

    }
    public List<MonAn> loadMonan()
    {
        List<MonAn> listMA = new ArrayList<>();

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
        Cursor cursor = database.query("MONAN", null, null, null, null, null, null);
        cursor.moveToFirst();
        String data ="";
        while (cursor.isAfterLast() == false) {

            String id=cursor.getString(0);
            String ten=cursor.getString(1);
            String hinh=cursor.getString(2);
            String ghichu=cursor.getString(4);
            BigDecimal gia=new BigDecimal(cursor.getString(3));
            BigDecimal dongia=gia;
            String mota=cursor.getString(5);
            String maloai=cursor.getString(6);
            MonAn ma= new MonAn(id,ten,hinh,gia,ghichu,mota,maloai);
            listMA.add(ma);


            data = cursor.getString(0) + " - " + cursor.getString(1);
            //Toast.makeText(mContext, data, Toast.LENGTH_LONG).show();
            cursor.moveToNext();
        }
        //---------------------------------------------

        cursor.close();
        dbHelper.closeDatabase();
        return listMA;

    }

    public List<MonAn> loadMonan_find(String sreach)
    {
        List<MonAn> listMA = new ArrayList<>();

        //Đây là phần truy vấn: Muốn them xoa sua là làm việc ở chổ này nha.
        String query = "SELECT * FROM MONAN WHERE TenMonAN LIKE '%" + sreach + "%'";
        Cursor cursor = database.rawQuery(query, null);
        cursor.moveToFirst();
        while (cursor.isAfterLast() == false) {

            String id=cursor.getString(0);
            String ten=cursor.getString(1);
            String hinh=cursor.getString(2);
            String ghichu=cursor.getString(4);
            BigDecimal gia=new BigDecimal(cursor.getString(3));
            BigDecimal dongia=gia;
            String mota=cursor.getString(5);
            String maloai=cursor.getString(6);
            MonAn ma= new MonAn(id,ten,hinh,gia,ghichu,mota,maloai);
            listMA.add(ma);

            //Toast.makeText(mContext, data, Toast.LENGTH_LONG).show();
            cursor.moveToNext();
        }

        return listMA;

    }


    public List<DoanhThu> chart() {
        List<DoanhThu> listDT = new ArrayList<>();

        // Phần truy vấn
        String query = "SELECT strftime('%m-%Y', NgayXuat) AS Thang, SUM(TongTien) AS DoanhThu\n" +
                "FROM HoaDon\n" +
                "GROUP BY strftime('%m-%Y', NgayXuat)\n" +
                "ORDER BY strftime('%Y-%m', NgayXuat) ASC";
        Cursor cursor = database.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {
                String thang = cursor.getString(0);
                String doanhThu = cursor.getString(1);

                DoanhThu doanhThuObj = new DoanhThu(thang, doanhThu);
                listDT.add(doanhThuObj);
            } while (cursor.moveToNext());
        }
        cursor.close();

        return listDT;
    }


    public List<MonAn> loadMonan_loai(String tenloai)
    {

        List<MonAn> listMA = new ArrayList<>();

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
        String query = "select * from MONAN m, LOAI l where m.MaLoai = l.MaLoai and l.TenLoai = '" + tenloai + "'";
        Cursor cursor = database.rawQuery(query, null);
        cursor.moveToFirst();
        String data ="";
        while (cursor.isAfterLast() == false) {

            String id=cursor.getString(0);
            String ten=cursor.getString(1);
            Toast.makeText(mContext, ten, Toast.LENGTH_LONG).show();
            String hinh=cursor.getString(2);
            String ghichu=cursor.getString(4);
            BigDecimal gia=new BigDecimal(cursor.getString(3));
            BigDecimal dongia=gia;
            String mota=cursor.getString(5);
            String maloai=cursor.getString(6);
            MonAn ma= new MonAn(id,ten,hinh,gia,ghichu,mota,maloai);
            listMA.add(ma);


            data = cursor.getString(0) + " - " + cursor.getString(1);
            //Toast.makeText(mContext, data, Toast.LENGTH_LONG).show();
            cursor.moveToNext();
        }
        //---------------------------------------------


        cursor.close();
        dbHelper.closeDatabase();
        return listMA;

    }
}
