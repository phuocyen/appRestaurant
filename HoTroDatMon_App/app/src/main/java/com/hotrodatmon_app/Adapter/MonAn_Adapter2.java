package com.hotrodatmon_app.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.hotrodatmon_app.DAL.dal_monAn2;
import com.hotrodatmon_app.MODEL.MonAn2;
import com.hotrodatmon_app.R;
import com.hotrodatmon_app.updateMonAn;

import java.util.ArrayList;

public class MonAn_Adapter2 extends RecyclerView.Adapter<MonAn_Adapter2.ViewHolder> {

    private ArrayList<MonAn2> monAn2List;
    private Context context;

    dal_monAn2 dal_monan2;




    public MonAn_Adapter2(ArrayList<MonAn2> monAn2List, Context context, dal_monAn2 dal_monan2) {
        this.monAn2List = monAn2List;
        this.context = context;
        this.dal_monan2 = dal_monan2;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.monan_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        MonAn2 monAn2 = monAn2List.get(position);


        String tenHinh = monAn2.getHinhAnh(); // Lấy tên file hình ảnh từ đối tượng monan

        int resourceId = holder.itemView.getContext().getResources().getIdentifier(tenHinh , "drawable", holder.itemView.getContext().getPackageName());

        holder.imgMonAn.setImageResource(resourceId);
        //holder.tvma.setText(monAn.getMaMon());
        holder.tvten.setText(monAn2.getTenMon());
        holder.tvdg.setText(String.valueOf(monAn2.getDonGia()));






        holder.btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, updateMonAn.class);
                intent.putExtra("MaMenu", monAn2.getMaMon());
                intent.putExtra("TenMonAn", monAn2.getTenMon());
                intent.putExtra("HinhAnh", monAn2.getHinhAnh());
                intent.putExtra("DonGia", monAn2.getDonGia());
                intent.putExtra("MoTa", monAn2.getMoTa());
                intent.putExtra("GhiChu", monAn2.getGhiChu());
                intent.putExtra("MaLoai", monAn2.getMaLoai());

                context.startActivity(intent);
            }
        });

        final ViewHolder finalHolder = holder;
        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean result = dal_monan2.deleteMA(monAn2.getMaMon());
                if (result) {
                    // Nếu xóa thành công, cập nhật lại danh sách và hiển thị thông báo
                    monAn2List.remove(monAn2);
                    notifyDataSetChanged();
                    Toast.makeText(context, "Đã xóa món ăn " + monAn2.getTenMon(), Toast.LENGTH_SHORT).show();
                } else {
                    // Nếu xóa không thành công, hiển thị thông báo lỗi
                    Toast.makeText(context, "Lỗi: Không thể xóa món ăn " + monAn2.getTenMon(), Toast.LENGTH_SHORT).show();
                }
            }
        });


    }

    @Override
    public int getItemCount(){return monAn2List.size();

    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageButton btnDelete, btnEdit;
        ImageView imgMonAn;
        TextView  tvten, tvdg;




        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            btnDelete = itemView.findViewById(R.id.delete_button);
            btnEdit = itemView.findViewById(R.id.edit_button);

            imgMonAn = itemView.findViewById(R.id.item_image);

            tvten = itemView.findViewById(R.id.item_name);
            tvdg = itemView.findViewById(R.id.item_price_textview);




        }


    }
}
