package linhdvph25937.fpoly.duanmau.Adapter;


import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import linhdvph25937.fpoly.duanmau.DAO.ThongKeDAO;
import linhdvph25937.fpoly.duanmau.DAO.ThuThuDAO;
import linhdvph25937.fpoly.duanmau.DTO.ThuThu;
import linhdvph25937.fpoly.duanmau.DTO.Top;
import linhdvph25937.fpoly.duanmau.R;

public class NguoiDungAdapter extends RecyclerView.Adapter<NguoiDungAdapter.BauHolder> implements Filterable {
    private Context context;
    private ThuThuDAO thuThuDAO;
    private ArrayList<ThuThu> list;
    private ArrayList<ThuThu> listOld;

    public NguoiDungAdapter(Context context, ThuThuDAO thuThuDAO) {
        this.context = context;
        this.thuThuDAO = thuThuDAO;
    }

    public void setData(ArrayList<ThuThu> arr){
        this.list = arr;
        this.listOld = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public BauHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_item_nguoi_dung, parent, false);
        return new BauHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BauHolder holder, int position) {
        ThuThu obj = list.get(position);
        holder.tvTenNguoiDung.setText("Tên người dùng: "+obj.getHoTen());
        holder.tvTenDangNhap.setText("Tên đăng nhập: "+obj.getMaTT());
        if (position % 2 != 0){
            holder.tvTenNguoiDung.setTextColor(Color.RED);
            holder.tvTenDangNhap.setTextColor(Color.RED);
        }else {
            holder.tvTenNguoiDung.setTextColor(Color.GREEN);
            holder.tvTenDangNhap.setTextColor(Color.GREEN);
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String bau = charSequence.toString();
                if (bau.isEmpty()){
                    list = listOld;
                }else {
                    ArrayList<ThuThu> listTT = new ArrayList<>();
                    for (ThuThu obj : listOld){
                        if (obj.getHoTen().toLowerCase().contains(bau.toLowerCase())){
                            listTT.add(obj);
                        }
                    }
                    list = listTT;
                }
                FilterResults results = new FilterResults();
                results.values = list;
                return results;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                list = (ArrayList<ThuThu>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    public class BauHolder extends RecyclerView.ViewHolder {
        private TextView tvTenNguoiDung, tvTenDangNhap;
        public BauHolder(@NonNull View itemView) {
            super(itemView);
            tvTenNguoiDung = itemView.findViewById(R.id.tv_ten_nguoi_dung);
            tvTenDangNhap = itemView.findViewById(R.id.tv_ten_dang_nhap);
        }
    }
}
