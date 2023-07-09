package com.hotrodatmon_app;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.hotrodatmon_app.DAL.dal_MonAn;
import com.hotrodatmon_app.MODEL.NHANVIEN;
import com.hotrodatmon_app.MODEL.PhanQuyen;

import java.util.ArrayList;
import java.util.List;


public class DangNhapActivity extends AppCompatActivity {
    Button btnDangNhap;
    EditText Acc, Pass;
    dal_MonAn dal_monAn;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_dangnhap);
        addControls();

        dal_monAn = new dal_MonAn(this);

        Acc.setText("");
        Pass.setText("");
        btnDangNhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String acc=Acc.getText().toString();
                String pass=Pass.getText().toString();
                if(dal_monAn.kiemTraAcccout(acc,pass)!=true)
                {
                    Toast.makeText(DangNhapActivity.this, "Tài khoản không hợp lệ", Toast.LENGTH_SHORT).show();

                }
                else {

                    List<NHANVIEN> nhanvien =new ArrayList<>();
                    nhanvien=dal_monAn.loadNV(acc,pass);

                    for(NHANVIEN item: nhanvien)
                    {
                        PhanQuyen phanQuyen=new PhanQuyen(item.getMaNV(), item.getTenNV());
                        break;
                    }

                    Intent intent = new Intent(DangNhapActivity.this, MainActivity.class);

                    startActivity(intent);

                }
            }
        });



    }

    void addControls()
    {
        btnDangNhap = findViewById(R.id.btnDangNhap);
        Acc = findViewById(R.id.edtAcc);
        Pass = findViewById(R.id.edtPass);

    }
}
