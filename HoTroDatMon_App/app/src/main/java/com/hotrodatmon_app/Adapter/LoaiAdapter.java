package com.hotrodatmon_app.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import com.hotrodatmon_app.DAL.QL_Loai;
import com.hotrodatmon_app.MODEL.LOAI;
import com.hotrodatmon_app.R;
import com.hotrodatmon_app.updateLoai;

import java.util.ArrayList;

public class LoaiAdapter extends BaseAdapter {
    Activity context;
    ArrayList<LOAI> list;
    QL_Loai ql_loai;

    public LoaiAdapter(Activity context, ArrayList<LOAI> list, QL_Loai ql_loai) {
        this.context = context;
        this.list = list;
        this.ql_loai = ql_loai;
    }

    @Override
    public int getCount() {
        return list.size();

    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View row=inflater.inflate(R.layout.listview_loai,null);
        TextView txtId=(TextView) row.findViewById(R.id.txtId);
        TextView txtTen=(TextView) row.findViewById(R.id.txtTen);
        ImageButton btnXoa=(ImageButton) row.findViewById(R.id.btnXoa);
        ImageButton btnSua=(ImageButton) row.findViewById(R.id.btnSua);

        ql_loai = new QL_Loai(context);
        final LOAI loai=list.get(i);
        txtId.setText(loai.getMaLoai());
        txtTen.setText(loai.getTenLoai());

        btnSua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context, updateLoai.class);
                intent.putExtra("MaLoai",loai.getMaLoai());
                intent.putExtra("Ten",loai.getTenLoai());
                context.startActivity(intent);
            }
        });

        btnXoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Xác nhận xóa");
                builder.setMessage("Bạn có chắc chắn muốn xóa loại món ăn " + loai.getTenLoai() + "?");
                builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        boolean result = ql_loai.deleteLOAI(loai.getMaLoai());
                        if (result) {
                            // Nếu xóa thành công, cập nhật lại danh sách và hiển thị thông báo
                            list.remove(loai);
                            notifyDataSetChanged();
                            Toast.makeText(context, "Đã xóa loại " + loai.getTenLoai(), Toast.LENGTH_SHORT).show();
                        } else {
                            // Nếu xóa không thành công, hiển thị thông báo lỗi
                            Toast.makeText(context, "Lỗi: Không thể xóa loại " + loai.getTenLoai(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // Không làm gì nếu người dùng chọn "Không"
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });


        return row;
    }
}
