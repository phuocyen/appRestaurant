package com.hotrodatmon_app;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.hotrodatmon_app.Adapter.Table_adapter;
import com.hotrodatmon_app.DAL.dal_BanAn;
import com.hotrodatmon_app.DAL.dal_MonAn;
import com.hotrodatmon_app.MODEL.BanAn;

import java.util.ArrayList;
import java.util.List;

public class TableFragment extends Fragment {
    RecyclerView recyclerTable;
    Table_adapter table_adapter;
    dal_BanAn dal_banAn;
    Spinner spinner;

    private  View view;
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        view = inflater.inflate(R.layout.layout_fragment_table, container, false);
        //addControls();
        spinner = view.findViewById(R.id.sprTrangThai);
        recyclerTable = view.findViewById(R.id.rcv_table);
        List<String> spinnerValues = new ArrayList<>();
        spinnerValues.add("Tất cả");
        spinnerValues.add("Trống");
        spinnerValues.add("Hoạt động");
        spinnerValues.add("Đặt");
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, spinnerValues);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        clickSpinner(spinnerValues);

        table_adapter=new Table_adapter(getContext());
        table_adapter.setTableAdapter(table_adapter);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(),2);
        recyclerTable.setLayoutManager(gridLayoutManager);

        table_adapter.setData(getContext(),loadDatBan());
        recyclerTable.setAdapter(table_adapter);

        table_adapter.setOnDatBanClickListener(new Table_adapter.OnDatBanClickListener() {
            @Override
            public void onDatBanClicked(String tenKH, String sdt, String idBan) {
                updateTableData();
            }
        });



        return view;
    }

    private void updateTableData() {
        table_adapter.setData(getContext(), loadDatBan());
    }

    void clickSpinner (List<String> spinnerValues)
    {
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                String selectedValue = spinnerValues.get(position);

                table_adapter.setData(getContext(),loadDatBan_State(selectedValue));
                recyclerTable.setAdapter(table_adapter);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private List<BanAn> loadDatBan_State(String state) //load theo trạng thái bàn
    {
        List<BanAn> listBanban= new ArrayList<>();
        dal_banAn = new dal_BanAn(getContext());
        List<BanAn> listban=null;
        if(state.equals("Tất cả")) {
            listban = dal_banAn.loadbanan();
        }
        else
        {
            listban = dal_banAn.loadbanan_state(state);
        }
        listBanban.addAll(listban);
        return listBanban;
    }

    private List<BanAn> loadDatBan()
    {
        List<BanAn> listBanban= new ArrayList<>();
        dal_banAn = new dal_BanAn(getContext());
        List<BanAn> listban= dal_banAn.loadbanan();
        listBanban.addAll(listban);
        return listBanban;
    }

    void addControls()
    {
        recyclerTable = getView().findViewById(R.id.rcv_table);

    }

}
