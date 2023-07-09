package com.hotrodatmon_app;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.hotrodatmon_app.Adapter.LoaiAdapter;
import com.hotrodatmon_app.DAL.QL_Loai;
import com.hotrodatmon_app.MODEL.LOAI;
import com.hotrodatmon_app.Util.DatabaseHelper;

import java.util.ArrayList;

public class PhanLoaiActivity extends AppCompatActivity {


    Button btnadd;
    ListView listView;

    ArrayList<LOAI> list;
    LoaiAdapter adapter;

    DatabaseHelper databaseHelper;
    QL_Loai ql_loai;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_phanloai);
        addControls();


        ql_loai = new QL_Loai(this);

        list.addAll(ql_loai.loadLoai()); // Gán giá trị cho biến list
        listView=findViewById(R.id.listView);
        adapter = new LoaiAdapter(this, list, ql_loai);
        listView.setAdapter(adapter);

        refreshList();
        btnadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PhanLoaiActivity.this, addLoai.class);
                startActivity(intent);
            }
        });


    }
    private void refreshList() {
        // Update the data source (ArrayList) with new data
        list.clear();
        // Add new items to the dataList, or retrieve data from a source

        // Notify the adapter that the underlying data has changed
        adapter.notifyDataSetChanged();

        // Refresh the ListView by invalidating its views
        listView.invalidateViews();
        listView.refreshDrawableState();
    }

    @Override
    protected void onResume() {
        super.onResume();
        list.clear();
        ql_loai = new QL_Loai(this);
        list.addAll(ql_loai.loadLoai());
        adapter.notifyDataSetChanged();
    }


    private void addControls(){
        btnadd=(Button) findViewById(R.id.btnadd);
        list = new ArrayList<>(); // Khai báo ArrayList tại đây
    }
}