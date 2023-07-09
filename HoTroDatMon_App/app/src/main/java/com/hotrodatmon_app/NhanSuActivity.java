package com.hotrodatmon_app;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.hotrodatmon_app.Adapter.NhanVien_Adapter;
import com.hotrodatmon_app.DAL.QL_NhanVien;
import com.hotrodatmon_app.MODEL.NHANVIEN;
import com.hotrodatmon_app.Util.DatabaseHelper;

import java.io.IOException;
import java.util.ArrayList;

public class NhanSuActivity extends AppCompatActivity {

    RecyclerView listdsNhanVien;
    ImageButton btnadd;
    SearchView searchView;
    ArrayList<NHANVIEN> list1;
    NhanVien_Adapter adapter;
    QL_NhanVien ql_nhanVien;
    DatabaseHelper databaseHelper;
    SQLiteDatabase database;
    Toolbar toolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_nhansu);
        addControls();
        ql_nhanVien = new QL_NhanVien(this);
        toolbar =findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        listdsNhanVien=findViewById(R.id.dsNhanVien);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        listdsNhanVien.setLayoutManager(layoutManager);
        adapter = new NhanVien_Adapter(this, list1, ql_nhanVien);
        listdsNhanVien.setAdapter(adapter);
        list1.addAll(ql_nhanVien.loadNhanVien()); // Gán giá trị cho biến list1
        btnadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(NhanSuActivity.this, addNhanVien.class);
                startActivity(intent);
            }
        });
    }



    @Override
    protected void onResume() {
        super.onResume();
        ql_nhanVien = new QL_NhanVien(this);
        list1.clear();
        list1.addAll(ql_nhanVien.loadNhanVien());
        adapter.notifyDataSetChanged();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        SearchManager searchManager=(SearchManager) getSystemService(Context.SEARCH_SERVICE);
        searchView= (SearchView) menu.findItem(R.id.search_btn).getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setMaxWidth(Integer.MAX_VALUE);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                adapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);
                return false;
            }
        });
        return true;
    }

    @Override
    public void onBackPressed() {
    if(!searchView.isIconified()){
        searchView.setIconified(true);
        return;}
        super.onBackPressed();
    }

    private void addControls(){
        btnadd=(ImageButton) findViewById(R.id.btnadd);
        list1 = new ArrayList<>();
    }
}
