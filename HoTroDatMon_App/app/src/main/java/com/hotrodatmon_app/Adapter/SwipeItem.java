package com.hotrodatmon_app.Adapter;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

public class SwipeItem extends ItemTouchHelper.SimpleCallback {

    HoaDon_adapter hoaDon_adapter;

   public SwipeItem(HoaDon_adapter hoaDon_adapter)
    {
        super(0,ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT);
        this.hoaDon_adapter = hoaDon_adapter;
    }
    @Override
    public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
        return false;
    }

    @Override
    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
        int position = viewHolder.getAbsoluteAdapterPosition();
        this.hoaDon_adapter.deleteItem(position);
    }
}
