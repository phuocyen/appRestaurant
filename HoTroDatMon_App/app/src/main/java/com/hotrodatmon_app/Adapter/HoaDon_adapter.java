package com.hotrodatmon_app.Adapter;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.hotrodatmon_app.DAL.dal_BanAn;
import com.hotrodatmon_app.DetailBanAnActivity;
import com.hotrodatmon_app.MODEL.CTHD;
import com.hotrodatmon_app.DAL.dal_MonAn;
import com.hotrodatmon_app.MODEL.HoaDon;
import com.hotrodatmon_app.MODEL.MonAn;
import com.hotrodatmon_app.R;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class HoaDon_adapter  extends RecyclerView.Adapter<HoaDon_adapter.HoaDonViewHoder>{

    dal_MonAn dal_monAn;
    List<CTHD> listCTHDs;
    private Context mcontext;
    String maban;
    private DetailBanAnActivity activity;


    List<HoaDon> listhd; // Add this line

    public HoaDon_adapter(Context mcontext, List<HoaDon> listhd,DetailBanAnActivity activity,String maban) { // Modify the constructor
        this.mcontext = mcontext;
        this.listhd = listhd;
        this.activity=activity;
        this.maban=maban;
    }


    public void setData(Context context, List<CTHD> list) {
        this.mcontext = context;
        this.listCTHDs = list;

        notifyDataSetChanged();
    }



    @NonNull
    @Override
    public HoaDonViewHoder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_hoadon,parent,false);

        return new HoaDon_adapter.HoaDonViewHoder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull HoaDonViewHoder holder, int position) {
        CTHD cthd = listCTHDs.get(position);
        if (cthd == null)
        {
            return;
        }
        dal_monAn =new dal_MonAn(mcontext);
         // Lấy tên file hình ảnh từ đối tượng monan

        List <MonAn> listMA= dal_monAn.loadmonan(cthd.getMaMenu());

        for (MonAn item : listMA) {
            int resourceId = holder.itemView.getContext().getResources().getIdentifier(item.getHinhMon(), "drawable", holder.itemView.getContext().getPackageName());

            holder.imghinh.setImageResource(resourceId);
            holder.txtTensp.setText(item.getTenMon());
            NumberFormat formatter = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));
            holder.txtDongia_sl.setText(cthd.getSl() +" x"+ formatter.format(cthd.getDongia()));
            holder.edtSL.setText(cthd.getSl().toString());
            holder.txtThanhtien.setText( formatter.format(cthd.getThanhtien()));
        }
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
                { NumberFormat formatter = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));
                    holder.txtDongia_sl.setText(newValue +" x"+ formatter.format(cthd.getDongia()));
                    int sll = Integer.parseInt(newValue);
                    float dongia = cthd.getDongia().floatValue(); // Chuyển đổi BigDecimal thành float
                    float tong = sll * dongia;
                    BigDecimal thanhttt = new BigDecimal(tong);
                    holder.txtThanhtien.setText(formatter.format(thanhttt));

                    dal_BanAn dal_banAn = new dal_BanAn(mcontext);

                    dal_banAn.update_CTHD(cthd.getMaCTHD(),sll,thanhttt);
                    activity.loadd(maban);

                }

            }
        };
        holder.edtSL.addTextChangedListener(textWatcher);


    }



    @Override
    public int getItemCount() {


        if(listCTHDs!=null)
        {
            return listCTHDs.size();
        }

        return 0;
    }

    public class  HoaDonViewHoder extends RecyclerView.ViewHolder
    {
        TextView txtDongia_sl, txtTensp, txtThanhtien;
        ImageView  imghinh;
        EditText edtSL;
        public HoaDonViewHoder(@NonNull View itemView) {
            super(itemView);

            imghinh = itemView.findViewById(R.id.img_hinhanh);
            txtTensp=itemView.findViewById(R.id.txtTenMon);
            txtDongia_sl=itemView.findViewById(R.id.txtsl_dg);
            txtThanhtien=itemView.findViewById(R.id.txtThanhTien);
            edtSL =itemView.findViewById(R.id.edtSL);

        }
    }



    public  void deleteItem (int position)
    {
        if (position >= 0 && position < listCTHDs.size()) {
            CTHD chtd = listCTHDs.get(position);
           // Toast.makeText(mcontext, chtd.getDongia().toString(), Toast.LENGTH_SHORT).show();
            dal_BanAn dal_banAn = new dal_BanAn(mcontext);
            dal_banAn.delete_CTHD(chtd.getMaCTHD());
            listCTHDs.remove(position);
            notifyItemRemoved(position);
            activity.loadd(maban);
        } else {
            // Xử lý trường hợp vị trí không hợp lệ
            Toast.makeText(mcontext, "Invalid position", Toast.LENGTH_SHORT).show();
        }
    }
}
