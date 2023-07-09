package com.hotrodatmon_app;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.hotrodatmon_app.Adapter.Menu_Adapter;
import com.hotrodatmon_app.Adapter.MonAn_adapter;
import com.hotrodatmon_app.Adapter.Recommended_adapter;
import com.hotrodatmon_app.Adapter.Table_adapter;
import com.hotrodatmon_app.DAL.dal_BanAn;
import com.hotrodatmon_app.DAL.dal_MonAn;
import com.hotrodatmon_app.MODEL.BanAn;
import com.hotrodatmon_app.MODEL.HoaDon;
import com.hotrodatmon_app.MODEL.MonAn;
import com.hotrodatmon_app.MODEL.Recommended;
import com.hotrodatmon_app.Util.DatabaseHelper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GoiMonActivity extends AppCompatActivity {
    TextView txt_soban, txt_tenkhach, txt_sdt;
    EditText edtTimKiem;
    Button btnTim;
    RecyclerView rcvMonAn;
    Menu_Adapter menu_adapter;
    List<MonAn> monAnList;
    dal_MonAn dal_monAn;

    HorizontalScrollView horizontalScrollView ;
    LinearLayout linearLayout ;
    private DatabaseHelper dbHelper;
    HoaDon hoaDon;
    String tenBan;
    String tenKhachHang;
    String sdt;


    public GoiMonActivity() {
        // Constructor không tham số
    }


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_goimon);
        addControls();

        Intent intent = getIntent();

        Bundle bundle = intent.getExtras();
        if (bundle != null) {
             hoaDon = (HoaDon) bundle.getSerializable("Oject_HoaDon");
             tenBan = intent.getStringExtra("tenban");
            tenKhachHang = intent.getStringExtra("tenkh");
            sdt = intent.getStringExtra("sdt");

            txt_soban.setText(tenBan);
            txt_tenkhach.setText(tenKhachHang);
            txt_sdt.setText(sdt);

            menu_adapter = new Menu_Adapter(this, monAnList, hoaDon.getMaHD(),hoaDon,tenBan,tenKhachHang,sdt);
            GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 3);
            rcvMonAn.setLayoutManager(gridLayoutManager);

            menu_adapter.setData(this, loadMenu(hoaDon.getMaHD()));
            rcvMonAn.setAdapter(menu_adapter);
            loadLoai(hoaDon.getMaHD());

            btnTim.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    menu_adapter = new Menu_Adapter(GoiMonActivity.this, monAnList, hoaDon.getMaHD(),hoaDon,tenBan,tenKhachHang,sdt);
                    GridLayoutManager gridLayoutManager = new GridLayoutManager(GoiMonActivity.this, 3);
                    rcvMonAn.setLayoutManager(gridLayoutManager);

                    menu_adapter.setData(GoiMonActivity.this, loadMenu_sreach(edtTimKiem.getText().toString()));
                    rcvMonAn.setAdapter(menu_adapter);
                    loadLoai(hoaDon.getMaHD());
                }
            });
        }
    }
    void loadLoai(String mahoadon)
    {
        dbHelper = new DatabaseHelper();
        try {
            dbHelper.copyDatabaseFromAssets(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
        dbHelper.openDatabase(this);
        SQLiteDatabase database =dbHelper.getData();

        Cursor cursor = database.query("LOAI", null, null, null, null, null, null);
        cursor.moveToFirst();
        String data ="";
        while (cursor.isAfterLast() == false) {
            Button button = new Button(this);
            button.setText(cursor.getString(1));
            button.setBackgroundResource(R.drawable.custom_bogoc_green);
            button.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );
            layoutParams.setMargins(30, 0, 0, 0); // set margin-left = 20dp
            button.setPadding(20, 0, 20, 0);
            button.setTextColor(getResources().getColor(R.color.Pink_1));
            // Đặt LayoutParams cho button
            button.setLayoutParams(layoutParams);

            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                  //  Toast.makeText(this, "Bạn đã chọn ", Toast.LENGTH_SHORT).show();
                    menu_adapter=new Menu_Adapter(GoiMonActivity.this,monAnList,mahoadon,hoaDon,tenBan,tenKhachHang,sdt);
                    GridLayoutManager gridLayoutManager = new GridLayoutManager(GoiMonActivity.this,3);
                    rcvMonAn.setLayoutManager(gridLayoutManager);

                    menu_adapter.setData(GoiMonActivity.this,getListMonAn1(mahoadon, button.getText().toString()));
                    rcvMonAn.setAdapter(menu_adapter);
                }
            });


            linearLayout.addView(button);

            cursor.moveToNext();
        }
        horizontalScrollView.setHorizontalScrollBarEnabled(false);
        horizontalScrollView.setSmoothScrollingEnabled(true);

        cursor.close();
        dbHelper.closeDatabase();
    }


    List<MonAn> loadMenu(String mahd)
    {
        List<MonAn> listMonan= new ArrayList<>();
        dal_monAn = new dal_MonAn(this);
        List<MonAn> listMon= dal_monAn.loadMonan();
        listMonan.addAll(listMon);
        return listMonan;
    }

    List<MonAn> loadMenu_sreach(String tensreach)
    {
        List<MonAn> listMonan= new ArrayList<>();
        dal_monAn = new dal_MonAn(this);
        List<MonAn> listMon= dal_monAn.loadMonan_find(tensreach);
        listMonan.addAll(listMon);
        return listMonan;
    }

    private List<MonAn> getListMonAn1(String mahd,String tenMon)
    {
        List<MonAn> listMonan= new ArrayList<>();
        dal_monAn = new dal_MonAn(this);
        List<MonAn> listMon= dal_monAn.loadMonan_loai(tenMon);
        listMonan.addAll(listMon);
        return listMonan;
    }

    void addControls()
    {
        txt_soban = findViewById(R.id.txtSoBan);
        txt_tenkhach=findViewById(R.id.txtTenKhach);
        txt_sdt=findViewById(R.id.txtSDT);
        rcvMonAn = findViewById(R.id.rcvMon);
        horizontalScrollView =  findViewById(R.id.horizotal);
        linearLayout = findViewById(R.id.linear);
        edtTimKiem=findViewById(R.id.edtTimKiem);
        btnTim=findViewById(R.id.btnTim);
    }
}
