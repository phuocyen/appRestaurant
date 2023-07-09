package com.hotrodatmon_app;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.NoCopySpan;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.hotrodatmon_app.Adapter.ClickItem_adapter;
import com.hotrodatmon_app.Adapter.Recommended_adapter;
import com.hotrodatmon_app.DAL.dal_MonAn;
import com.hotrodatmon_app.MODEL.MonAn;
import com.hotrodatmon_app.MODEL.PhanQuyen;
import com.hotrodatmon_app.MODEL.Recommended;
import com.hotrodatmon_app.Util.DatabaseHelper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    HorizontalScrollView horizontalScrollView ;
    LinearLayout linearLayout ;
    private DatabaseHelper dbHelper;
    private dal_MonAn dal_monAn;
    RecyclerView rcvMuc;
    Recommended_adapter rcm_adap;
    TextView txtNV;

    private  View view;

    @Nullable@Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

         view = inflater.inflate(R.layout.layout_fragment_home, container, false);

        horizontalScrollView =  view.findViewById(R.id.horizotal);
        linearLayout = view.findViewById(R.id.linear);
        rcvMuc = view.findViewById(R.id.rcv_Muc);
        txtNV=view.findViewById(R.id.txtNV);
        txtNV.setText(PhanQuyen.getTenNV());
        rcm_adap = new Recommended_adapter(getContext());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false);
        rcvMuc.setLayoutManager(linearLayoutManager);
        rcm_adap.setData(getListMonAn());
        rcvMuc.setAdapter(rcm_adap);


        loadLoai();


        return view;
    }



    List<MonAn> listMonAns;
    private List<Recommended> getListMonAn()
    {
        List<Recommended> listRCM = new ArrayList<>();
        dal_monAn = new dal_MonAn(getContext());
        listMonAns = dal_monAn.loadMonan();
        listRCM.add(new Recommended(listMonAns));
        return listRCM;
    }



    private List<Recommended> getListMonAn1(String tenMon)
    {
        List<Recommended> listRCM = new ArrayList<>();
        dal_monAn = new dal_MonAn(getContext());
        listMonAns= dal_monAn.loadMonan_loai(tenMon);
        listRCM.add(new Recommended(listMonAns));
        return listRCM;
    }

    void loadLoai()
    {
        dbHelper = new DatabaseHelper();
        try {
            dbHelper.copyDatabaseFromAssets(getContext());
        } catch (IOException e) {
            e.printStackTrace();
        }
        dbHelper.openDatabase(getContext());
        SQLiteDatabase database =dbHelper.getData();

        Cursor cursor = database.query("LOAI", null, null, null, null, null, null);
        cursor.moveToFirst();
        String data ="";
        while (cursor.isAfterLast() == false) {
            Button button = new Button(getContext());
            button.setText(cursor.getString(1));
            button.setBackgroundResource(R.drawable.hover_button);
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
                    Toast.makeText(getContext(), "Bạn đã chọn " + button.getText().toString(), Toast.LENGTH_SHORT).show();


                    rcm_adap = new Recommended_adapter(getContext());
                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false);
                    rcvMuc.setLayoutManager(linearLayoutManager);
                    Toast.makeText(getContext(), button.getText().toString() + button.getText().toString(), Toast.LENGTH_SHORT).show();

                    rcm_adap.setData(getListMonAn1(button.getText().toString()));
                    rcvMuc.setAdapter(rcm_adap);


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

    void addControls()
    {
        horizontalScrollView =  getView().findViewById(R.id.horizotal);
        linearLayout = getView().findViewById(R.id.linear);

    }


}
