package com.hotrodatmon_app.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.hotrodatmon_app.MODEL.Recommended;
import com.hotrodatmon_app.R;

import java.util.List;

public class Recommended_adapter extends  RecyclerView.Adapter<Recommended_adapter.RecommendedViewHoder>{

    private Context mContext;
    private List<Recommended> listRCM;

    public Recommended_adapter(Context mContext) {
        this.mContext = mContext;
    }

    public void setData(List<Recommended> list)
    {
        this.listRCM=list;
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public RecommendedViewHoder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_muc,parent,false);

        return new RecommendedViewHoder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecommendedViewHoder holder, int position) {
        Recommended rcm = listRCM.get(position);
        if(rcm==null)
        {
            return;
        }

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext,RecyclerView.HORIZONTAL,false);
        holder.rcv_MonAn.setLayoutManager(linearLayoutManager);
        MonAn_adapter monan_adt = new MonAn_adapter();
        monan_adt.setData(mContext,rcm.getMonans());
        holder.rcv_MonAn.setAdapter(monan_adt);
    }

    @Override
    public int getItemCount() {
        if(listRCM!=null)
        {
            return listRCM.size();
        }

            return 0;

    }

    public  class RecommendedViewHoder extends RecyclerView.ViewHolder
    {
        RecyclerView rcv_MonAn;
        public RecommendedViewHoder(@NonNull View itemView) {
            super(itemView);
            rcv_MonAn= itemView.findViewById(R.id.rcv_MonAn);
        }
    }
}
