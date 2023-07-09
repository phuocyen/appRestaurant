package com.hotrodatmon_app;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class OtherFragment extends Fragment {

    Button btnNhanSu, btnphanloai, btnmenu, btnDangXuat;
    private View view;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.layout_quantrihethong, container, false);
        btnNhanSu = view.findViewById(R.id.btnNhansu);
        btnphanloai = view.findViewById(R.id.btnLoai);
        btnmenu = view.findViewById(R.id.btnThucDon);
        btnDangXuat=view.findViewById(R.id.btnDangXuat);

        btnNhanSu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), NhanSuActivity.class);
                startActivity(intent);
            }
        });

        btnphanloai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), PhanLoaiActivity.class);
                startActivity(intent);
            }
        });

        btnmenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), ThucDonActivity.class);
                startActivity(intent);
            }
        });

        btnDangXuat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), DangNhapActivity.class);
                startActivity(intent);
            }
        });

        return view;
    }
}
