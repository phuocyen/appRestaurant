package com.hotrodatmon_app;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.hotrodatmon_app.Adapter.MonAn_Adapter2;
import com.hotrodatmon_app.DAL.dal_monAn2;
import com.hotrodatmon_app.MODEL.MonAn2;
import com.hotrodatmon_app.Util.DatabaseHelper;

import java.util.ArrayList;

public class ThucDonActivity extends AppCompatActivity {

    ImageButton btnthem;
    RecyclerView monan_recycler;
    ArrayList<MonAn2> monAnlist2;
    MonAn_Adapter2 monAnadapter;

    dal_monAn2 dal_monan2;

    DatabaseHelper databaseHelper;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_thucdon);

        addControls();


        dal_monan2 = new dal_monAn2(this);

        monan_recycler = findViewById(R.id.ds_monan);

        monAnlist2 = new ArrayList<>();
        monAnadapter = new MonAn_Adapter2(monAnlist2,this, dal_monan2);

        monAnlist2.addAll(dal_monan2.loadMonAn());
        monAnadapter.notifyDataSetChanged();

        int numColumns = 2;

// Khởi tạo GridLayoutManager với số cột
        GridLayoutManager layoutManager = new GridLayoutManager(this, numColumns);
        monan_recycler.setLayoutManager(layoutManager);

        monan_recycler.setAdapter(monAnadapter);


         // Gán giá trị cho biến list1
        //refreshList();

        Intent intent = getIntent();
        if (intent != null && intent.getBooleanExtra("shouldScrollToLastPosition", false)) {
            monan_recycler.scrollToPosition(monAnlist2.size() - 1);
        }

        btnthem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ThucDonActivity.this, addMonAn.class);
                startActivity(intent);
            }
        });
    }

    private void refreshList() {
        // Update the data source (ArrayList) with new data
        monAnlist2.clear();
        // Add new items to the dataList, or retrieve data from a source

        // Notify the adapter that the underlying data has changed
        monAnadapter.notifyDataSetChanged();

        // Refresh the ListView by invalidating its views
      //  monan_recycler.invalidateViews();
      //  monan_recycler.refreshDrawableState();
    }

    private void addControls() {
        btnthem =findViewById(R.id.addButton);

        monAnlist2 = new ArrayList<>();

    }

    @Override
    protected void onResume() {
        super.onResume();
        dal_monan2 = new dal_monAn2(this);
        monAnlist2.clear();
        monAnlist2.addAll(dal_monan2.loadMonAn());
        monAnadapter.notifyDataSetChanged();
    }

}