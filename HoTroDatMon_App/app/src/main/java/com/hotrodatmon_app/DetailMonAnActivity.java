package com.hotrodatmon_app;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.hotrodatmon_app.MODEL.MonAn;
import com.hotrodatmon_app.MainActivity;

import java.text.NumberFormat;
import java.util.Locale;

public class DetailMonAnActivity extends AppCompatActivity {

    ImageView hinhMon;
    TextView tenMon;
    Button giaMon;
   TextView mota;
   Button back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_monan);
        addControls();
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            MonAn mm = (MonAn) bundle.get("Oject_MonAn");
            //Toast.makeText(this, "mãi keo thôi" +mm.getTenMon() , Toast.LENGTH_SHORT).show();
            tenMon.setText(mm.getTenMon());
            mota.setText(mm.getMoTa());
            NumberFormat formatter = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));
            giaMon.setText(formatter.format(mm.getDonGia()));

            int resourceId =this.getResources().getIdentifier(mm.getHinhMon(), "drawable", this.getPackageName());
            hinhMon.setImageResource(resourceId);

        }
        else
        {
           // Toast.makeText(this, "mãi keo thôi rỗng", Toast.LENGTH_SHORT).show();
        }

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DetailMonAnActivity.this, TrangChuActivity.class);
                intent.putExtra("fragmentIndex", 0); // Truyền thông báo hiển thị HomeFragment
                startActivity(intent);
            }
        });


    }

    void addControls()
    {
        hinhMon = findViewById(R.id.imgHinhMon);
        tenMon= findViewById(R.id.txtTenMon);
        giaMon =findViewById(R.id.txtDonGia);
        mota =findViewById(R.id.txtMoTa);
        back=findViewById(R.id.back);

    }
}
