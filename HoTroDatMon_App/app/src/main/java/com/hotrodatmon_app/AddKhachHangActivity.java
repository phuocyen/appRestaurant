package com.hotrodatmon_app;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.hotrodatmon_app.DAL.dal_KhachHang;
import com.hotrodatmon_app.MODEL.BanAn;

public class AddKhachHangActivity extends AppCompatActivity {

    Button btnDatban, btnGoimon;
    TextView txtSoban,txtidban;
    EditText edtTenKH;
    EditText edtSDT;
    dal_KhachHang dal_khachHang;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_khachhang);
        addControls();
        Bundle bundle = getIntent().getExtras();
        BanAn mm = (BanAn) bundle.get("Oject_BanAn");
        txtSoban.setText(mm.getTenban());
        txtidban.setText(mm.getMaban());

        btnDatban.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dal_khachHang. themkhachhang(edtTenKH.getText().toString(),edtSDT.getText().toString(),txtidban.getText().toString());
                Intent intent = new Intent(AddKhachHangActivity.this, TrangChuActivity.class);
                intent.putExtra("fragmentIndex", 1); // Truyền thông báo hiển thị HomeFragment
                startActivity(intent);
            }
        });
    }


    void addControls ()
    {
        btnDatban= findViewById(R.id.btnDatBan);
        btnGoimon=findViewById(R.id.btnGoiMon);
        txtSoban=findViewById(R.id.txtSoban);
        edtTenKH=findViewById(R.id.edtKhachhang);
        edtSDT=findViewById(R.id.edtDienthoai);
        dal_khachHang = new dal_KhachHang(this);
        txtidban=findViewById(R.id.idBan);
        txtidban.setVisibility(View.GONE);
    }
}
