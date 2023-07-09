package com.hotrodatmon_app.Adapter;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.hotrodatmon_app.DAL.QL_NhanVien;
import com.hotrodatmon_app.MODEL.NHANVIEN;
import com.hotrodatmon_app.R;
import com.hotrodatmon_app.updateNV;

import java.util.ArrayList;
import java.util.Locale;

public class NhanVien_Adapter extends RecyclerView.Adapter<NhanVien_Adapter.ViewHolder> implements Filterable {

    private final ArrayList<NHANVIEN> mlist;
    Activity context;
    ArrayList<NHANVIEN> list;
    QL_NhanVien ql_nhanVien;
    private boolean shouldScrollToLastPosition = false;

    public NhanVien_Adapter(Activity context, ArrayList<NHANVIEN> list, QL_NhanVien ql_nhanVien) {
        this.context = context;
        this.list = list;
        this.mlist=list;
        this.ql_nhanVien = ql_nhanVien;
    }




    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.listview_cus, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        NHANVIEN nhanvien = list.get(position);

        holder.maNV.setText(nhanvien.getMaNV());
        holder.Ten.setText(nhanvien.getTenNV());
        holder.cccd.setText(nhanvien.getCCCD());





        holder.btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(context, updateNV.class);
                intent.putExtra("MaNV",nhanvien.getMaNV());
                intent.putExtra("Ten",nhanvien.getTenNV());

                intent.putExtra("Sdt",nhanvien.getSDT());

                intent.putExtra("cccd",nhanvien.getCCCD());

                intent.putExtra("TK",nhanvien.getTaiKhoan());
                intent.putExtra("Pass",nhanvien.getMatKhau());
                shouldScrollToLastPosition = true;

                context.startActivity(intent);
            }
        });

        final ViewHolder finalHolder = holder;
        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean result = ql_nhanVien.deleteNV(nhanvien.getMaNV());
                if (result) {
                    // Nếu xóa thành công, cập nhật lại danh sách và hiển thị thông báo
                    list.remove(nhanvien);
                    notifyDataSetChanged();
                    Toast.makeText(context, "Đã xóa nhân viên " + nhanvien.getTenNV(), Toast.LENGTH_SHORT).show();
                } else {
                    // Nếu xóa không thành công, hiển thị thông báo lỗi
                    Toast.makeText(context, "Lỗi: Không thể xóa nhân viên " + nhanvien.getTenNV(), Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    @Override
    public int getItemCount(){return list.size();

    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String strSearch = charSequence.toString().toLowerCase();
                if (strSearch.isEmpty()) {
                    list = mlist;
                } else {
                    ArrayList<NHANVIEN> listNEW = new ArrayList<>();
                    String[] searchWords = strSearch.split(" "); // Tách chuỗi tìm kiếm thành các từ đơn
                    for (NHANVIEN nhanvien : list) {
                        String tenNV = nhanvien.getTenNV().toLowerCase();
                        boolean match = true;
                        for (String word : searchWords) {
                            if (!tenNV.contains(word)) {
                                match = false;
                                break;
                            }
                        }
                        if (match) {
                            listNEW.add(nhanvien);
                        }
                    }
                    list = listNEW;
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = list;
                return filterResults;
            }


            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                list= (ArrayList<NHANVIEN>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageButton btnDelete,btnEdit;

        TextView maNV, Ten, cccd;



        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            btnDelete = itemView.findViewById(R.id.btnXoa);
            btnEdit = itemView.findViewById(R.id.btnSua);



            maNV = itemView.findViewById(R.id.txtId);
            Ten = itemView.findViewById(R.id.txtTen);
            cccd = itemView.findViewById(R.id.cccd);






        }


    }
}

