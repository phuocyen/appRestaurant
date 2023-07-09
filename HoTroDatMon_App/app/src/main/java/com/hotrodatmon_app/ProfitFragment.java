package com.hotrodatmon_app;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.hotrodatmon_app.DAL.dal_MonAn;
import com.hotrodatmon_app.MODEL.DoanhThu;

import java.util.ArrayList;
import java.util.List;

public class ProfitFragment extends Fragment {
    private  View view;
    ArrayList barArraylist;
    dal_MonAn dal_monAn;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.layout_fragment_profit, container, false);

        BarChart barChart = view.findViewById(R.id.barchart);
        getData();
        BarDataSet barDataSet=new BarDataSet(barArraylist,"Nguồn tiền vào 5 tháng gần nhất");
        BarData barData =new BarData(barDataSet);
        barChart.setData(barData);
        barDataSet.setColor(Color.rgb(153, 102, 0));
        barDataSet.setValueTextColor(Color.BLACK);
        barDataSet.setValueTextSize(16f);
        barChart.getDescription().setEnabled(true);

        return view;
    }

    private  void getData()
    {
        barArraylist = new ArrayList();
        dal_monAn =new dal_MonAn(getContext());
        List<DoanhThu> doanhThuList=dal_monAn.chart();
        float ddd= 2f;
        for (DoanhThu item: doanhThuList) {
            barArraylist.add(new BarEntry(ddd,Integer.parseInt(item.getData())));
            ddd++;
        }


    }



}
