package com.hotrodatmon_app;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.hotrodatmon_app.DAL.QL_Loai;
import com.hotrodatmon_app.MODEL.LOAI;

import java.util.ArrayList;

public class addLoai extends AppCompatActivity {
    EditText edtMaLoai, edtTen;
    Button btnSave, btnThoat;
    QL_Loai ql_loai;
    ArrayList<LOAI> list=new ArrayList<>();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_loai);
        addControls();
        ql_loai=new QL_Loai(this);
        String newMaLoai=ql_loai.generateEmployeeCode();
        edtMaLoai.setText(newMaLoai);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String maLoai=edtMaLoai.getText().toString();
                String ten=edtTen.getText().toString();
                if (ql_loai.kiemTraMaLOAITonTai(maLoai)) {
                    Toast.makeText(getApplicationContext(), "Mã Loại đã tồn tại", Toast.LENGTH_SHORT).show();
                    edtMaLoai.requestFocus();
                }
                else {
                    boolean result = ql_loai.themLOAI(maLoai, ten);
                    Log.d("Thanh cong", "co " + result);
                    if (result == true) {
                        Toast.makeText(getApplicationContext(), "Thêm loại thành công", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(addLoai.this, PhanLoaiActivity.class);
                        list.addAll(ql_loai.loadLoai());
                        Log.d("List","list= "+list);
                        startActivity(intent);
                        finish();
                    } else {
                        Toast.makeText(getApplicationContext(), "Thêm loại không thành công", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        btnThoat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(addLoai.this);
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
