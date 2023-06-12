package linhdvph25937.fpoly.duanmau.Adapter;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;

import linhdvph25937.fpoly.duanmau.DAO.ThanhVienDAO;
import linhdvph25937.fpoly.duanmau.DTO.ThanhVien;
import linhdvph25937.fpoly.duanmau.R;

public class ThanhVienAdapter extends RecyclerView.Adapter<ThanhVienAdapter.BauHolder>{
    private Context context;
    private ThanhVienDAO dao;
    private ArrayList<ThanhVien> list;

    public ThanhVienAdapter(Context context, ThanhVienDAO dao) {
        this.context = context;
        this.dao = dao;
    }

    public void setData(ArrayList<ThanhVien> arr){
        this.list = arr;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public BauHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_item_thanh_vien, parent, false);
        return new BauHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BauHolder holder, int position) {
        ThanhVien obj = list.get(position);
        if (obj == null){
            return;
        }

        holder.tvMaTV.setText(obj.getMaTV()+"");
        holder.tvTenTV.setText(obj.getHoTen());
        holder.tvNamSinh.setText(obj.getNameSinh());
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

    public void ShowEditDialog(Context context, int index, ThanhVien obj){
        Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.layout_dialog_thanh_vien);
        dialog.setCanceledOnTouchOutside(false);
        Window window = dialog.getWindow();
        if (window == null){
            return;
        }
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        TextInputEditText edTenTV = dialog.findViewById(R.id.ed_dialog_ten_thanh_vien);
        edTenTV.setText(obj.getHoTen());
        TextInputEditText edNamSinh = dialog.findViewById(R.id.ed_dialog_nam_sinh);
        edNamSinh.setText(obj.getNameSinh());
        Button btnSave = dialog.findViewById(R.id.btn_save_dialog_thanh_vien);
        Button btnCancel = dialog.findViewById(R.id.btn_cancel_dialog_thanh_vien);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String hoTen = edTenTV.getText().toString();
                String namSinh = edNamSinh.getText().toString();
                if (hoTen.isEmpty() || namSinh.isEmpty()){
                    Toast.makeText(context, "Không được để trống thông tin", Toast.LENGTH_SHORT).show();
                }else{
                    obj.setHoTen(hoTen);
                    obj.setNameSinh(namSinh);
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
        dialog.setContentView(R.layout.layout_dialog_thanh_vien);
        dialog.setCanceledOnTouchOutside(false);
        Window window = dialog.getWindow();
        if (window == null){
            return;
        }
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        TextInputEditText edTenTV = dialog.findViewById(R.id.ed_dialog_ten_thanh_vien);
        TextInputEditText edNamSinh = dialog.findViewById(R.id.ed_dialog_nam_sinh);
        Button btnSave = dialog.findViewById(R.id.btn_save_dialog_thanh_vien);
        Button btnCancel = dialog.findViewById(R.id.btn_cancel_dialog_thanh_vien);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ThanhVien obj = new ThanhVien();
                String hoTen = edTenTV.getText().toString();
                String namSinh = edNamSinh.getText().toString();
                if (hoTen.isEmpty() || namSinh.isEmpty()){
                    Toast.makeText(context, "Không được để trống thông tin", Toast.LENGTH_SHORT).show();
                }else{
                    obj.setHoTen(hoTen);
                    obj.setNameSinh(namSinh);
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

    public class BauHolder extends RecyclerView.ViewHolder {
        private TextView tvMaTV, tvTenTV, tvNamSinh;
        private ImageView imgDel;
        public BauHolder(@NonNull View itemView) {
            super(itemView);

            tvMaTV = itemView.findViewById(R.id.tv_ma_thanh_vien);
            tvTenTV = itemView.findViewById(R.id.tv_ten_thanh_vien);
            tvNamSinh = itemView.findViewById(R.id.tv_nam_sinh_thanh_vien);
            imgDel = itemView.findViewById(R.id.img_del_thanh_vien);
        }
    }
}
