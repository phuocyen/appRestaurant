package com.hotrodatmon_app.Adapter;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.hotrodatmon_app.DAL.dal_BanAn;
import com.hotrodatmon_app.DAL.dal_KhachHang;
import com.hotrodatmon_app.DetailBanAnActivity;
import com.hotrodatmon_app.DetailMonAnActivity;
import com.hotrodatmon_app.GoiMonActivity;
import com.hotrodatmon_app.MODEL.BanAn;
import com.hotrodatmon_app.MODEL.HoaDon;
import com.hotrodatmon_app.MODEL.MonAn;
import com.hotrodatmon_app.R;
import com.hotrodatmon_app.TrangChuActivity;

import org.w3c.dom.Text;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class Menu_Adapter extends  RecyclerView.Adapter<Menu_Adapter.MenuViewHoder> {
    private Context mcontext;
    List<MonAn> listMonAns;
    String mahd;
    BigDecimal thanhttt;
    private DetailBanAnActivity activity;
    String maban;

    HoaDon hoaDon;
    String tenban;
    String tenKH;
    String sdt;
    public Menu_Adapter(Context mcontext, List<MonAn> listMonAns, String mahd,HoaDon hoaDon,String tenban, String tenKH,String sdt) {
        this.mcontext = mcontext;
        this.listMonAns= listMonAns;
        this.mahd=mahd;
        this.hoaDon=hoaDon;
        this.tenban=tenban;
        this.tenKH=tenKH;
        this.sdt=sdt;



    }

    public void setData(Context context, List<MonAn> list) {
        this.mcontext = context;
        this.listMonAns = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MenuViewHoder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_monan,parent,false);

        return new Menu_Adapter.MenuViewHoder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull MenuViewHoder holder, int position) {
        MonAn monan = listMonAns.get(position);
        if (monan == null)
        {
            return;
        }

        String tenHinh = monan.getHinhMon(); // Lấy tên file hình ảnh từ đối tượng monan

        int resourceId = holder.itemView.getContext().getResources().getIdentifier(tenHinh , "drawable", holder.itemView.getContext().getPackageName());

        holder.imgHinh.setImageResource(resourceId);
        if(monan.getTenMon().length()>13)
        {
            holder.txtTenMon.setText(monan.getTenMon().substring(0,11)+"...");
        }
        else
        {
            holder.txtTenMon.setText(monan.getTenMon());
        }

        NumberFormat formatter = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));
        holder.txtDonGia.setText(formatter.format(monan.getDonGia()));

        holder.layoutItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Dialog dialog=new Dialog(mcontext);
                dialog.setContentView(R.layout.layout_addmenu);


                ImageView imageView=dialog.findViewById(R.id.imgHinh);
                TextView tenmon= dialog.findViewById(R.id.txtTenMon);
                TextView dongia= dialog.findViewById(R.id.txtDonGia);
                TextView thanhtien = dialog.findViewById(R.id.txtThanhTien);
                EditText sl=dialog.findViewById(R.id.edtSL);
                Button btnCong=dialog.findViewById(R.id.btnCong);
                Button btnTru=dialog.findViewById(R.id.btnTru);
                Button btnThemm=dialog.findViewById(R.id.btnThem);
                NumberFormat formatter = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));

                imageView.setImageResource(resourceId);
                tenmon.setText(monan.getTenMon());
                dongia.setText(formatter.format(monan.getDonGia()));


                sl.setText("0");
                btnCong.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        int sll=Integer.parseInt(sl.getText().toString());
                        sll++;
                        sl.setText(String.valueOf(sll));
                    }
                });

                btnTru.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        int sll=Integer.parseInt(sl.getText().toString());
                        sll--;
                        sl.setText(String.valueOf(sll));
                    }
                });

                TextWatcher textWatcher = new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                    }

                    @Override
                    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                        // Được gọi khi giá trị của EditText đang thay đổi
                    }

                    @Override
                    public void afterTextChanged(Editable editable) {
                        String newValue = editable.toString();
                        if(!newValue.equals(""))
                        {

                            int sll = Integer.parseInt(newValue);
                            float dongia = monan.getDonGia().floatValue(); // Chuyển đổi BigDecimal thành float
                            float tong = sll * dongia;
                            thanhttt = new BigDecimal(tong);
                            thanhtien .setText(formatter.format(thanhttt));

                        }

                    }
                };
                sl.addTextChangedListener(textWatcher);
                //Bắt sự kiện thêm món

                btnThemm.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dal_KhachHang dal_khachhang= new dal_KhachHang(mcontext);
                        dal_khachhang.themCTHD(mahd, monan.getIdMon(),thanhttt.toString(),sl.getText().toString(),monan.getDonGia().toString() );


                        dialog.dismiss();

                    }
                });



                dialog.show();
            }
        });


    }

    @Override
    public int getItemCount() {

        if(listMonAns.size()>10)
        {
            return 10;
        }
        else if(listMonAns.size()<10)
        {
            return listMonAns.size();

        }

        return 0;
    }

    public class MenuViewHoder extends RecyclerView.ViewHolder
    {
        TextView txtTenMon, txtDonGia;
        ImageView imgHinh;
        RelativeLayout layoutItem;
        public MenuViewHoder(@NonNull View itemView) {
            super(itemView);
            txtTenMon =itemView.findViewById(R.id.txtTenMon);
            txtDonGia=itemView.findViewById(R.id.txtGia);
            imgHinh=itemView.findViewById(R.id.img_hinh);
            layoutItem=itemView.findViewById(R.id.layoutItem);

        }
    }



}
