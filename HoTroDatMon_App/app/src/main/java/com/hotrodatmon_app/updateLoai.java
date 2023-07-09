package com.hotrodatmon_app;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.hotrodatmon_app.DAL.QL_Loai;

public class updateLoai extends AppCompatActivity {
    EditText edtMaLoai, edtTen;
    Button btnSave, btnThoat;
    QL_Loai ql_loai;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.update_loai);

        addControls();
        String maLoai = getIntent().getStringExtra("MaLoai");
        edtMaLoai.setText(maLoai);
        String ten= getIntent().getStringExtra("Ten");
        edtTen.setText(ten);

        ql_loai=new QL_Loai(this);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String maLoai = getIntent().getStringExtra("MaLoai");
                String ten = edtTen.getText().toString();
                    // cập nhật thông tin nhân viên
                    boolean result = ql_loai.updateLoai(maLoai, ten);
                    if (result) {
                        Toast.makeText(getApplicationContext(), "Cập nhật thông tin loại thành công", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(updateLoai.this, PhanLoaiActivity.class);
                        startActivity(intent);
                        finish();
                    } else {
                        Toast.makeText(getApplicationContext(), "Cập nhật thông tin loại không thành công", Toast.LENGTH_SHORT).show();
                    }
            }
        });
        btnThoat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(updateLoai.this);
                builder.setMessage("Bạn có muốn thoát không?")
                        .setCancelable(false)
                        .setPositiveButton("Có", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                finish();
                            }
                        })
                        .setNegativeButton("Không", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });
                AlertDialog alert = builder.create();
                alert.show();
            }
        });


    }
    private void addControls(){

        edtMaLoai=(EditText) findViewById(R.id.edtMaLoai);
        edtTen=(EditText) findViewById(R.id.edtTen);
        edtMaLoai.setEnabled(false);

        btnSave=(Button) findViewById(R.id.btnSave);
        btnThoat=(Button) findViewById(R.id.btnThoat);
    }
}
