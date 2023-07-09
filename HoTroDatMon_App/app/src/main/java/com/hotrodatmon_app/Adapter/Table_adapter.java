package com.hotrodatmon_app.Adapter;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.hotrodatmon_app.AddKhachHangActivity;
import com.hotrodatmon_app.DAL.dal_BanAn;
import com.hotrodatmon_app.DAL.dal_KhachHang;
import com.hotrodatmon_app.DetailBanAnActivity;
import com.hotrodatmon_app.DetailMonAnActivity;
import com.hotrodatmon_app.MODEL.BanAn;
import com.hotrodatmon_app.MODEL.KhachHang;
import com.hotrodatmon_app.MODEL.MonAn;
import com.hotrodatmon_app.R;
import com.hotrodatmon_app.ThongTinDatBanActivity;
import com.hotrodatmon_app.TrangChuActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Table_adapter extends  RecyclerView.Adapter<Table_adapter.TableViewHoder>{

    private Context mcontext;
    dal_BanAn dal_banAn;
    dal_KhachHang dal_khachHang;
    List<BanAn> listBanAns;
    Table_adapter table_adapter;
    private OnDatBanClickListener mListener;

    public Table_adapter(Context mcontext) {
        this.mcontext = mcontext;
    }

    public void setData(Context context, List<BanAn> list) {
        this.mcontext = context;
        this.listBanAns = list;
        notifyDataSetChanged();
    }

    public void updateData(List<BanAn> newList) {
        listBanAns.clear();
        listBanAns.addAll(newList);
        notifyDataSetChanged();
    }

    public interface OnDatBanClickListener {
        void onDatBanClicked(String tenKH, String sdt, String idBan);
    }

    public void setOnDatBanClickListener(OnDatBanClickListener listener) {
        mListener = listener;
    }

    @NonNull
    @Override
    public TableViewHoder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_table,parent,false);

        return new Table_adapter.TableViewHoder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull TableViewHoder holder, int position) {

        BanAn banAn = listBanAns.get(position);
        if (banAn == null)
        {
            return;
        }
        holder.soban.setText(banAn.getTenban());
        holder.idban.setText(banAn.getMaban());
        if (Objects.equals(banAn.getGhichu(), "Đặt")) {
            holder.imgHinh.setImageResource(R.drawable.dadat);
        }

        if (Objects.equals(banAn.getGhichu(), "Trống") ) {
            holder.imgHinh.setImageResource(R.drawable.trong1);
        }

        if (Objects.equals(banAn.getGhichu(), "Hoạt động")) {
            holder.imgHinh.setImageResource(R.drawable.hoatdong);
        }

        holder.itembanan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(Objects.equals(banAn.getGhichu(), "Trống"))
                {
                    onClickgotoDeteil_null(banAn);

                }
                else if(Objects.equals(banAn.getGhichu(), "Đặt"))
                {
                    onClickgotoDeteil_ordered(banAn);
                }
                else
                {
                    onClickgotoDeteil(banAn);
                }

            }
        });

    }


    public  void onClickgotoDeteil_null(BanAn banan)//Vàng
    {
        Dialog dialog=new Dialog(mcontext);
        dialog.setContentView(R.layout.layout_khachhang);

       Button btnDatban= dialog.findViewById(R.id.btnDatBan);
        Button  btnGoimon=dialog.findViewById(R.id.btnGoiMon);
        TextView txtSoban=dialog.findViewById(R.id.txtSoban);
        EditText edtTenKH=dialog.findViewById(R.id.edtKhachhang);
        EditText edtSDT=dialog.findViewById(R.id.edtDienthoai);
       dal_KhachHang dal_khachHang = new dal_KhachHang(mcontext);
        TextView txtidban = dialog.findViewById(R.id.idBan);
        txtidban.setVisibility(View.GONE);

        txtSoban.setText(banan.getTenban());
        txtidban.setText(banan.getMaban());
        btnDatban.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dal_khachHang.themkhachhang(edtTenKH.getText().toString(), edtSDT.getText().toString(), txtidban.getText().toString());
                table_adapter.updateData(loadDatBan());
                dialog.dismiss();
            }
        });

        btnGoimon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Tạo hóa đơn
                dal_khachHang.themhoadon(edtTenKH.getText().toString(), edtSDT.getText().toString(), txtidban.getText().toString());
                table_adapter.updateData(loadDatBan());
                onClickgotoDeteil(banan);
                dialog.dismiss();

            }
        });

        dialog.show();
    }


    private List<BanAn> loadDatBan()
    {
        List<BanAn> listBanban= new ArrayList<>();
        dal_banAn = new dal_BanAn(mcontext);
        List<BanAn> listban= dal_banAn.loadbanan();
        listBanban.addAll(listban);
        return listBanban;
    }
    public void setTableAdapter(Table_adapter adapter) {
        this.table_adapter = adapter;
    }
    public  void onClickgotoDeteil(BanAn banan)
    {
        Intent intent = new Intent(mcontext, DetailBanAnActivity.class);
        Bundle bundle= new Bundle();
        bundle.putSerializable("Oject_BanAn",banan);
        intent.putExtras(bundle);
        mcontext.startActivity(intent);
    }

    public  void onClickgotoDeteil_ordered(BanAn banan)//đỏ
    {

        Dialog dialog=new Dialog(mcontext);
        dialog.setContentView(R.layout.layout_thongtindatban);

        TextView tenban= dialog.findViewById(R.id.txtSoban);
        TextView idban= dialog.findViewById(R.id.idBann);
        TextView tenkh = dialog.findViewById(R.id.txtKhachhang);
        TextView sdt=dialog.findViewById(R.id.txtDienthoai);
        TextView sluong=dialog.findViewById(R.id.txtSL);
        TextView ngaygio=dialog.findViewById(R.id.txtNgayGio);
        Button btnHuyBan=dialog.findViewById(R.id.btnHuyBan);
        Button btnGoiMon=dialog.findViewById(R.id.btnGoiMon);


        tenban.setText(banan.getTenban());
        idban.setText(banan.getMaban());
        List<KhachHang> listKhachHang = new ArrayList<>();
        dal_khachHang = new dal_KhachHang(mcontext);
        listKhachHang = dal_khachHang.loadkhachhang(banan.getMakh().toString());
        String makh;
        for (KhachHang item : listKhachHang) {
            tenkh.setText(item.getTenKH());
            sdt.setText(item.getSdt());
            sluong.setText(String.valueOf(banan.getSoluong()));
            makh= item.getIdKH();
            ngaygio.setText(banan.getNgaydat() + " _" + banan.getGiodat());
        }

        btnHuyBan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {dal_banAn = new dal_BanAn(mcontext);
                dal_banAn.huyDatban(idban.getText().toString());
                table_adapter.updateData(loadDatBan());
                dialog.dismiss();
            }
        });


        btnGoiMon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dal_khachHang.themhoadon2(banan.getMaban());
                table_adapter.updateData(loadDatBan());
                onClickgotoDeteil(banan);
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    @Override
    public int getItemCount() {
        if(listBanAns !=null)
        {
             return listBanAns.size();

        }

        return 0;
    }

    public class TableViewHoder extends RecyclerView.ViewHolder
    {
        ImageView imgHinh;
        TextView soban;
        TextView idban;

        RelativeLayout itembanan;

        public TableViewHoder(@NonNull View itemView) {
            super(itemView);

            imgHinh = itemView.findViewById(R.id.idTrangThai);
            soban=itemView.findViewById(R.id.soban);
            idban=itemView.findViewById(R.id.idban);
            idban.setVisibility(View.GONE);
            itembanan=itemView.findViewById(R.id.item_BanAn);
        }
    }
}
