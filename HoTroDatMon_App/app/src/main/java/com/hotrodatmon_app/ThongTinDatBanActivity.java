package com.hotrodatmon_app;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.hotrodatmon_app.DAL.dal_BanAn;
import com.hotrodatmon_app.DAL.dal_KhachHang;
import com.hotrodatmon_app.MODEL.BanAn;
import com.hotrodatmon_app.MODEL.KhachHang;

import java.util.ArrayList;
import java.util.List;

public class ThongTinDatBanActivity extends AppCompatActivity {

    TextView tenban,idban,tenkh,sdt,sluong,ngaygio;
    Button btnHuyBan, btnGoiMon;
    dal_KhachHang dal_khachHang;
    dal_BanAn dal_banAn;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_thongtindatban);
        addControls();



        Bundle bundle = getIntent().getExtras();
        BanAn mm = (BanAn) bundle.get("Oject_BanAn");
        if (bundle != null) {
            //Toast.makeText(this, mm.getTenban(), Toast.LENGTH_LONG).show();
            tenban.setText(mm.getTenban());
            idban.setText(mm.getMaban());
            List<KhachHang> listKhachHang = new ArrayList<>();
            dal_khachHang = new dal_KhachHang(this);
            listKhachHang = dal_khachHang.loadkhachhang(mm.getMakh().toString());
            for (KhachHang item : listKhachHang) {
                tenkh.setText(item.getTenKH());
                sdt.setText(item.getSdt());
                sluong.setText(String.valueOf(mm.getSoluong()));
                ngaygio.setText(mm.getNgaydat() + " _" + mm.getGiodat());
            }
        }
        click_HuyMon();

    }



    void click_HuyMon()
    {
        dal_banAn = new dal_BanAn(this);
        btnHuyBan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                dal_banAn.huyDatban(idban.getText().toString());
                Intent intent = new Intent(ThongTinDatBanActivity.this, TrangChuActivity.class);
                intent.putExtra("fragmentIndex", 1); // Truyền thông báo hiển thị HomeFragment
                startActivity(intent);
            }
        });
    }

    void addControls()
    {
        tenban= findViewById(R.id.txtSoban);
        idban= findViewById(R.id.idBann);
        tenkh = findViewById(R.id.txtKhachhang);
        sdt=findViewById(R.id.txtDienthoai);
        sluong=findViewById(R.id.txtSL);
        ngaygio=findViewById(R.id.txtNgayGio);
        idban.setVisibility(View.GONE);
        btnHuyBan=findViewById(R.id.btnHuyBan);
        btnGoiMon=findViewById(R.id.btnGoiMon);


    }
}
