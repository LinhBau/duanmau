package linhdvph25937.fpoly.duanmau.Adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import linhdvph25937.fpoly.duanmau.DAO.ThongKeDAO;
import linhdvph25937.fpoly.duanmau.DTO.Top;
import linhdvph25937.fpoly.duanmau.R;

public class TopAdapter extends RecyclerView.Adapter<TopAdapter.BauHolder>{
    private Context context;
    private ThongKeDAO dao;
    private ArrayList<Top> list;

    public TopAdapter(Context context, ThongKeDAO dao) {
        this.context = context;
        this.dao = dao;
    }

    public void setData(ArrayList<Top> arr){
        this.list = arr;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public BauHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_item_top_10, parent, false);
        return new BauHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BauHolder holder, int position) {
        Top obj = list.get(position);
        holder.tvTenSach.setText("Tên sách: "+obj.tenSach);
        holder.tvSl.setText("Số lượng: "+obj.soLuong);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class BauHolder extends RecyclerView.ViewHolder {
        private TextView tvTenSach, tvSl;
        public BauHolder(@NonNull View itemView) {
            super(itemView);
            tvTenSach = itemView.findViewById(R.id.tv_sach_top10);
            tvSl = itemView.findViewById(R.id.tv_so_luong_top10);
        }
    }
}
