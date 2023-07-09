package com.hotrodatmon_app;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.hotrodatmon_app.Adapter.MonAn_Adapter2;
import com.hotrodatmon_app.DAL.dal_monAn2;
import com.hotrodatmon_app.MODEL.Loai2;
import com.hotrodatmon_app.MODEL.MonAn2;

import java.util.ArrayList;
import java.util.List;

public class addMonAn extends AppCompatActivity {


    List<Uri> listuri = new ArrayList<>();

    EditText edtMaMenu, edtTen, edtDongia, edtghiChu, edtMota, editHinhAnh, editImage;

    Spinner loai_spin;


    Button btnSave, btnThoat;

    ArrayList<MonAn2> list=new ArrayList<>();
    dal_monAn2 dal_monAn2;

    MonAn_Adapter2 monAn_adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.them_monan);
        addControls();
        dal_monAn2 =new dal_monAn2(this);
        String newMaMenu= dal_monAn2.generateCode();
        edtMaMenu.setText(newMaMenu);

        loadSpinnerData();



        btnThoat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(addMonAn.this);
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
                String maMA = edtMaMenu.getText().toString();
                String ten = edtTen.getText().toString();

               String hinhanh = editHinhAnh.getText().toString();

                Integer donGia = Integer.valueOf( edtDongia.getText().toString());
                String mota = edtMota.getText().toString();
                String ghiChu = edtghiChu.getText().toString();
                //String maLoai = edtLoai.getText().toString();

                String gt = loai_spin.getSelectedItem().toString();
                String idLoai = dal_monAn2.getIDLoai(gt);


                if (dal_monAn2.kiemTraMaMATonTai(maMA)==true) {
                    Toast.makeText(getApplicationContext(), "Mã món ăn đã tồn tại", Toast.LENGTH_SHORT).show();
                    edtMaMenu.requestFocus();

                } else {
                    boolean result = dal_monAn2.themMA(maMA, ten, hinhanh, donGia, mota, ghiChu,idLoai);
                    if (result) {
                        Toast.makeText(getApplicationContext(), "Thêm món ăn thành công", Toast.LENGTH_SHORT).show();
                        list.addAll(dal_monAn2.loadMonAn());
                        Log.d("List","list= "+list);
                        Intent intent = new Intent(addMonAn.this, ThucDonActivity.class);
                        startActivity(intent);
                        finish();
                    } else {
                        Toast.makeText(getApplicationContext(), "Thêm món ăn không thành công", Toast.LENGTH_SHORT).show();
                    }
                }

            }
        });

    }

    private void openGallery() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
        intent.setAction(Intent.ACTION_GET_CONTENT);
        activityResultLauncher.launch(Intent.createChooser(intent, "Select Pictures"));
    }

    private void addControls() {

        edtMaMenu = (EditText) findViewById(R.id.editTextMaMonAn);
        edtTen = (EditText) findViewById(R.id.editTextTenMonAn);
        edtDongia = (EditText) findViewById(R.id.editTextDonGia);
        edtMota = (EditText) findViewById(R.id.editTextMoTa);
        edtghiChu = (EditText) findViewById(R.id.editTextGhiChu);
        loai_spin = (Spinner) findViewById(R.id.spinnerMaLoai);
        editHinhAnh = (EditText) findViewById(R.id.editHinhAnh);

        btnSave = (Button) findViewById(R.id.buttonThemMonAn);
        btnThoat = (Button) findViewById(R.id.btn_back);
    }

    private void loadSpinnerData(){
        // database handler
        // Spinner Drop down elements
        ArrayList <Loai2> arrayLop = dal_monAn2.getAllLoai();

        ArrayList<String> tenLoaiList = new ArrayList<>();

        for(Loai2 loai2 : arrayLop){
            tenLoaiList.add(loai2.getTenLoai());
        }
        tenLoaiList.add(0,"Loại");
        ArrayAdapter adapter2 = new ArrayAdapter(this,android.R.layout.simple_spinner_item,tenLoaiList);
        loai_spin.setAdapter(adapter2);

    }

    private ActivityResultLauncher<Intent> activityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>(){
                @Override
                public void onActivityResult(ActivityResult result) {
                    Intent data = result.getData();
                    Uri imageUri = data.getData();
                    listuri.add(imageUri);
                }


            }
    );
}