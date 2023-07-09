package com.hotrodatmon_app;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.hotrodatmon_app.DAL.dal_monAn2;
import com.hotrodatmon_app.MODEL.Loai2;

import java.util.ArrayList;

public class updateMonAn extends AppCompatActivity {


    EditText edtMaMenu, edtTen, edtDongia, edtghiChu, edtMota, edtHinhAnh;

    Spinner loai_spin;


    Button btnSave, btnThoat;


    dal_monAn2 dal_monAn2;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sua_monan);
        addControls();

        String maMenu = getIntent().getStringExtra("MaMenu");
        edtMaMenu.setText(maMenu);
        String ten=getIntent().getStringExtra("TenMonAn");
        edtTen.setText(ten);

        String hinhanh = getIntent().getStringExtra("HinhAnh");

        String Dongia = getIntent().getStringExtra("DonGia");
        String mota = getIntent().getStringExtra("MoTa");
        String ghiChu = getIntent().getStringExtra("GhiChu");

        edtTen.setText(ten);

        edtHinhAnh.setText(hinhanh);

        edtDongia.setText(Dongia);
        edtMota.setText(mota);
        edtghiChu.setText(ghiChu);



        dal_monAn2 =new dal_monAn2(this);

        loadSpinnerData();



        btnThoat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(updateMonAn.this);
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


        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String maMA = getIntent().getStringExtra("MaMenu");


                String ten = edtTen.getText().toString();

                String hinhanh = edtHinhAnh.getText().toString();

                Integer donGia = Integer.parseInt(edtDongia.getText().toString());
                String mota = edtMota.getText().toString();
                String ghiChu = edtghiChu.getText().toString();

                String gt = loai_spin.getSelectedItem().toString();
                String idLoai = dal_monAn2.getIDLoai(gt);

                Toast.makeText(updateMonAn.this, edtMaMenu.getText().toString(), Toast.LENGTH_SHORT).show();
                if (dal_monAn2.kiemTraMaMATonTai(edtMaMenu.getText().toString())!=true) {
                    Toast.makeText(updateMonAn.this, "", Toast.LENGTH_SHORT).show();
                    Toast.makeText(updateMonAn.this, "2222222eeeee", Toast.LENGTH_SHORT).show();
                    edtMaMenu.requestFocus();

                } else {
                    boolean result = dal_monAn2.updateMA(maMA, ten, hinhanh, donGia, mota, ghiChu, idLoai);
                    Toast.makeText(updateMonAn.this, "23333", Toast.LENGTH_SHORT).show();
                   if (result) {
                        Toast.makeText(getApplicationContext(), "Cập nhật món ăn thành công", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(updateMonAn.this, ThucDonActivity.class);
                        startActivity(intent);
                        finish();
                    } else {
                        Toast.makeText(getApplicationContext(), "Cập nhật món ăn không thành công", Toast.LENGTH_SHORT).show();
                    }
                }

            }
        });

    }

    private void addControls() {

        edtMaMenu = (EditText) findViewById(R.id.editTextMaMonAn);
        edtTen = (EditText) findViewById(R.id.editTextTenMonAn);
        edtDongia = (EditText) findViewById(R.id.editTextDonGia);
        edtMota = (EditText) findViewById(R.id.editTextMoTa);
        edtghiChu = (EditText) findViewById(R.id.editTextGhiChu);
        loai_spin = (Spinner) findViewById(R.id.spinnerMaLoai);
        edtHinhAnh = (EditText) findViewById(R.id.editHinhAnh);

        btnSave = (Button) findViewById(R.id.buttonSuaMonAn);
        btnThoat = (Button) findViewById(R.id.btn_back);
    }

    private void loadSpinnerData(){
        // database handler
        // Spinner Drop down elements
        ArrayList<Loai2> arrayLop = dal_monAn2.getAllLoai();

        ArrayList<String> tenLoaiList = new ArrayList<>();

        for(Loai2 loai2 : arrayLop){
            tenLoaiList.add(loai2.getTenLoai());
        }
        tenLoaiList.add(0,"Loại");
        ArrayAdapter adapter2 = new ArrayAdapter(this,android.R.layout.simple_spinner_item,tenLoaiList);
        loai_spin.setAdapter(adapter2);

    }
}
