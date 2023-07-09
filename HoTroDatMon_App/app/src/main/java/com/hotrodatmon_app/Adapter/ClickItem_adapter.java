package com.hotrodatmon_app.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.hotrodatmon_app.HomeFragment;
import com.hotrodatmon_app.MODEL.MonAn;
import com.hotrodatmon_app.R;

import java.util.ArrayList;
import java.util.List;

public class ClickItem_adapter  extends RecyclerView.Adapter<ClickItem_adapter.ViewHolder> {

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    private List<MonAn> mData; // Declare mData as an ArrayList of String
    private Context mContext;
    public ClickItem_adapter(List<MonAn> data, Context context) {
        mData = data; // Initialize mData in the constructor
        mContext=context;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public ViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
        }



        @Override
        public void onClick(View view) {
            int position = getBindingAdapterPosition();
            if (position != RecyclerView.NO_POSITION) {
                String data = String.valueOf(mData.get(position));
                Intent intent = new Intent(mContext, HomeFragment.class);
                intent.putExtra("data", data);

                mContext.startActivity(intent);
            }
        }
    }
}
