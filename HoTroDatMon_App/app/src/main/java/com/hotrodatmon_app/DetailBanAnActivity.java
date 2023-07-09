package com.hotrodatmon_app;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.startup.StartupLogger;

import com.hotrodatmon_app.Adapter.HoaDon_adapter;
import com.hotrodatmon_app.Adapter.SwipeItem;
import com.hotrodatmon_app.Adapter.Table_adapter;
import com.hotrodatmon_app.DAL.dal_BanAn;
import com.hotrodatmon_app.DAL.dal_KhachHang;
import com.hotrodatmon_app.DAL.dal_MonAn;
import com.hotrodatmon_app.MODEL.BanAn;
import com.hotrodatmon_app.MODEL.CTHD;
import com.hotrodatmon_app.MODEL.HoaDon;
import com.hotrodatmon_app.MODEL.KhachHang;
import com.hotrodatmon_app.MODEL.MonAn;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class DetailBanAnActivity  extends AppCompatActivity  {
    TextView txtSoBan, txtHoTen, txtSDT, txtNgay,txtGio,txttongtien;
     Button btnGoiMon, btnThanhToan, btnRefresh;
     dal_KhachHang dal_khachHang;
    HorizontalScrollView horizontalScrollView ;
    LinearLayout linearLayout ;

     dal_MonAn dal_monAn;
     dal_BanAn dal_banAn;
     RecyclerView rcvHD;

     HoaDon_adapter hoaDon_adapter;
     String khach, sdt;
    HoaDon hoaDon = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_orderfood);
        addControls();

        Bundle bundle = getIntent().getExtras();
        BanAn mm = (BanAn) bundle.get("Oject_BanAn");
        if (bundle != null) {
            txtSoBan.setText(mm.getTenban());
            if (mm.getNgaydat() != null) {
                txtNgay.setText(mm.getNgaydat().toString());
            } else {
                txtNgay.setText("Ngày không khả dụng");
            }

            if (mm.getGiodat() != null) {
                txtGio.setText(mm.getGiodat().toString());
            } else {
                txtGio.setText("Giờ không khả dụng");
            }

            List<KhachHang> listKhachHang = new ArrayList<>();
            dal_khachHang = new dal_KhachHang(this);
            listKhachHang = dal_khachHang.loadkhachhang(mm.getMakh());
            for (KhachHang item : listKhachHang) {
                txtHoTen.setText(item.getTenKH());
                txtSDT.setText(item.getSdt());
                khach=item.getTenKH();
                sdt=item.getSdt();
            }
        } else {
            Toast.makeText(this, "mãi keo thôi rỗng", Toast.LENGTH_SHORT).show();
        }

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        rcvHD.setLayoutManager(linearLayoutManager);

        loadd(mm.getMaban());

        btnRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadd(mm.getMaban());
            }
        });

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new SwipeItem(hoaDon_adapter));
        itemTouchHelper.attachToRecyclerView(rcvHD);
        rcvHD.setAdapter(hoaDon_adapter);
        Intent intent = new Intent(this, GoiMonActivity.class);
        btnGoiMon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Gửi HoaHon
                Bundle bundle= new Bundle();
                bundle.putSerializable("Oject_HoaDon",hoaDon);
                intent.putExtras(bundle);
                intent.putExtra("tenban", mm.getTenban());
                intent.putExtra("tenkh", khach);
                intent.putExtra("sdt", sdt);

                startActivity(intent);
            }
        });


        btnThanhToan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dal_banAn = new dal_BanAn(DetailBanAnActivity.this);
                dal_banAn.thanhtoan(txtSoBan.getText().toString(),idhd);
                Intent intent = new Intent(DetailBanAnActivity.this, TrangChuActivity.class);
                intent.putExtra("fragmentIndex", 1); // Truyền thông báo hiển thị HomeFragment
                startActivity(intent);
            }
        });

    }


    String idhd=null;
    public void loadd(String maban)
    {
        List<HoaDon> listhd = new ArrayList<>();
        dal_monAn = new dal_MonAn(this);
        listhd = dal_monAn.loadHoaDon(maban);
        hoaDon_adapter = new HoaDon_adapter(DetailBanAnActivity.this, listhd,DetailBanAnActivity.this,maban);

        for (HoaDon item : listhd) {
            hoaDon_adapter.setData(this, loadthongtinban(item.getMaHD()));
            idhd=item.getMaHD();
            NumberFormat formatter = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));

            txttongtien.setText(formatter.format(item.getTongtien()));

            hoaDon = new HoaDon(item.getMaHD(), item.getNgayxuat(), item.getTongtien(), item.getGhichu(), item.getMaNV(), item.getMaban());

            break;
        }

        rcvHD.setAdapter(hoaDon_adapter);
    }


    private List<CTHD> loadthongtinban(String maban) //load theo trạng thái bàn
    {
        List<CTHD> listcths= new ArrayList<>();
        dal_monAn = new dal_MonAn(this);
        List<CTHD> listcthd=null;
        listcthd = dal_monAn.loadCTHD(maban);


        listcths.addAll(listcthd);
        return listcths;
    }


    void addControls ()
    {
        txtSoBan = findViewById(R.id.txtSoBan);
        txtHoTen = findViewById(R.id.txtTenKhachHang);
        txtSDT = findViewById(R.id.txtSDT);
        txtNgay = findViewById(R.id.txtNgay);
        txtGio=findViewById(R.id.txtGio);
        txttongtien=findViewById(R.id.txtTongTien);
        btnGoiMon =findViewById(R.id.btnGoiMon);
        btnThanhToan= findViewById(R.id.btnThanhToan);
        rcvHD=findViewById(R.id.rcv_CTHD);
        btnRefresh=findViewById(R.id.btnRefresh);
        horizontalScrollView =  findViewById(R.id.horizotal);
        linearLayout = findViewById(R.id.linear);

    }
}
