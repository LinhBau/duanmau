package linhdvph25937.fpoly.duanmau.Adapter;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;

import linhdvph25937.fpoly.duanmau.DAO.LoaiSachDAO;
import linhdvph25937.fpoly.duanmau.DTO.LoaiSach;
import linhdvph25937.fpoly.duanmau.R;

public class LoaiSachAdapter extends RecyclerView.Adapter<LoaiSachAdapter.BauHolder> implements Filterable{
    private Context context;
    private LoaiSachDAO dao;
    private ArrayList<LoaiSach> list;
    private ArrayList<LoaiSach> listOld;

    public LoaiSachAdapter(Context context, LoaiSachDAO dao) {
        this.context = context;
        this.dao = dao;
    }

    public void setData(ArrayList<LoaiSach> arr){
        this.list = arr;
        this.listOld = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public BauHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_item_loai_sach, parent, false);
        return new BauHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BauHolder holder, int position) {
        LoaiSach obj = list.get(position);
        if (obj == null){
            return;
        }

        holder.tvMaLoai.setText(obj.getMaLoai()+"");
        holder.tvTenLoai.setText(obj.getTenLoai());
        holder.imgDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Delete");
                builder.setIcon(android.R.drawable.ic_delete);
                builder.setMessage("Are you sure?");
                builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        int res = dao.delete(obj);
                        if (res>0){
                            list.remove(holder.getAdapterPosition());
                            notifyDataSetChanged();
                            Toast.makeText(context, "Deleted", Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(context, "Can't delete", Toast.LENGTH_SHORT).show();
                        }
                        dialogInterface.dismiss();
                    }
                });

                builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });

                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                ShowEditDialog(context, holder.getAdapterPosition(), obj);
                return false;
            }
        });
    }

    public void ShowEditDialog(Context context, int index, LoaiSach obj){
        Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.layout_dialog_loai_sach);
        dialog.setCanceledOnTouchOutside(false);
        Window window = dialog.getWindow();
        if (window == null){
            return;
        }
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        TextInputEditText edTenLoai = dialog.findViewById(R.id.ed_dialog_ten_loai_sach);
        edTenLoai.setText(obj.getTenLoai());
        Button btnSave = dialog.findViewById(R.id.btn_save_dialog_loai_sach);
        Button btnCancel = dialog.findViewById(R.id.btn_cancel_dialog_loai_sach);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String tenLoai = edTenLoai.getText().toString();
                obj.setTenLoai(tenLoai);
                if (tenLoai.isEmpty()){
                    Toast.makeText(context, "Không được để trống thông tin", Toast.LENGTH_SHORT).show();
                }else {
                    int res = dao.update(obj);
                    if (res>0){
                        list.set(index, obj);
                        notifyDataSetChanged();
                        Toast.makeText(context, "Updated", Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(context, "Can't update", Toast.LENGTH_SHORT).show();
                    }
                    dialog.dismiss();
                }
            }
        });

        dialog.show();
    }

    public void ShowAddDialog(Context context){
        Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.layout_dialog_loai_sach);
        dialog.setCanceledOnTouchOutside(false);
        Window window = dialog.getWindow();
        if (window == null){
            return;
        }
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        TextInputEditText edTenLoai = dialog.findViewById(R.id.ed_dialog_ten_loai_sach);
        Button btnSave = dialog.findViewById(R.id.btn_save_dialog_loai_sach);
        Button btnCancel = dialog.findViewById(R.id.btn_cancel_dialog_loai_sach);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LoaiSach obj = new LoaiSach();
                String tenLoai = edTenLoai.getText().toString();
                if (tenLoai.isEmpty()){
                    Toast.makeText(context, "Không được để trống thông tin", Toast.LENGTH_SHORT).show();
                }else {
                    obj.setTenLoai(tenLoai);
                    long res = dao.insert(obj);
                    if (res>0){
                        list.clear();
                        list.addAll(dao.getAll());
                        notifyDataSetChanged();
                        Toast.makeText(context, "Inserted", Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(context, "Can't insert", Toast.LENGTH_SHORT).show();
                    }
                    dialog.dismiss();
                }

            }
        });

        dialog.show();
    }

    @Override
    public int getItemCount() {
        if (list != null){
            return list.size();
        }
        return 0;
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String bau = charSequence.toString();
                if (bau.isEmpty()){
                    list = listOld;
                }else{
                    ArrayList<LoaiSach> listLS = new ArrayList<>();
                    for (LoaiSach obj : listOld){
                        if (obj.getTenLoai().toLowerCase().contains(bau.toLowerCase())){
                            listLS.add(obj);
                        }
                    }
                    list = listLS;
                }
                FilterResults results = new FilterResults();
                results.values = list;
                return results;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                list = (ArrayList<LoaiSach>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }


    public class BauHolder extends RecyclerView.ViewHolder {
        private TextView tvMaLoai, tvTenLoai;
        private ImageView imgDel;
        public BauHolder(@NonNull View itemView) {
            super(itemView);

            tvMaLoai = itemView.findViewById(R.id.tv_ma_loai_sach);
            tvTenLoai = itemView.findViewById(R.id.tv_ten_loai_sach);
            imgDel = itemView.findViewById(R.id.img_del_loai_sach);
        }
    }
}
