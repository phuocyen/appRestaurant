package com.hotrodatmon_app.Adapter;



import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.hotrodatmon_app.DetailMonAnActivity;
import com.hotrodatmon_app.HomeFragment;
import com.hotrodatmon_app.MODEL.MonAn;
import com.hotrodatmon_app.R;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

import kotlin.coroutines.EmptyCoroutineContext;

public class MonAn_adapter extends RecyclerView.Adapter<MonAn_adapter.MonAnViewHoder> {

    List<MonAn> listMonAns;
    private Context mcontext;

    public void setData(Context context,List<MonAn> list)
    {
        this.mcontext=context;
        this.listMonAns = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MonAnViewHoder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recom,parent,false);

        return new MonAnViewHoder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MonAnViewHoder holder, int position) {

        MonAn monan = listMonAns.get(position);
        if (monan == null)
        {
            return;
        }
        String tenHinh = monan.getHinhMon(); // Lấy tên file hình ảnh từ đối tượng monan

       int resourceId = holder.itemView.getContext().getResources().getIdentifier(tenHinh , "drawable", holder.itemView.getContext().getPackageName());

      holder.imgMonAn.setImageResource(resourceId);
        holder.txtTenMon.setText(monan.getTenMon());
        if(monan.getMoTa().length()<80) {
            holder.txtMoTa.setText(monan.getMoTa());
        }
        else
        {
            holder.txtMoTa.setText(monan.getMoTa().substring(0,75)+"...");
        }
        NumberFormat formatter = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));

        holder.txtDonGia.setText(formatter.format(monan.getDonGia()));

        holder.layout_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickgotoDeteil(monan);
            }
        });
    }

    public  void onClickgotoDeteil( MonAn monan)
    {
        Intent intent = new Intent(mcontext, DetailMonAnActivity.class);
        Bundle bundle= new Bundle();
        bundle.putSerializable("Oject_MonAn",monan);
        intent.putExtras(bundle);
       // Toast.makeText(mcontext, "mãi keo thôi "+monan.getTenMon(), Toast.LENGTH_SHORT).show();
        mcontext.startActivity(intent);
    }

    @Override
    public int getItemCount() {
        if(listMonAns !=null)
        {
          //  return listMonAns.size();
            return 5;
        }

            return 0;


    }

    public class MonAnViewHoder extends RecyclerView.ViewHolder
    {
        ImageView imgMonAn;
        TextView txtTenMon;
        TextView txtMoTa;
        LinearLayout layout_item;
        TextView txtDonGia;

        public MonAnViewHoder(@NonNull View itemView) {
            super(itemView);
            layout_item=itemView.findViewById(R.id.layout_item);
            imgMonAn= itemView.findViewById(R.id.imgHinhMon);
            txtTenMon=itemView.findViewById(R.id.txtTenMon);
            txtMoTa=itemView.findViewById(R.id.txtMoTa);
            txtDonGia=itemView.findViewById(R.id.txtDonGia);
        }
    }
}
