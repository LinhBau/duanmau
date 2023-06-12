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
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;

import linhdvph25937.fpoly.duanmau.DAO.LoaiSachDAO;
import linhdvph25937.fpoly.duanmau.DAO.SachDAO;
import linhdvph25937.fpoly.duanmau.DTO.LoaiSach;
import linhdvph25937.fpoly.duanmau.DTO.Sach;
import linhdvph25937.fpoly.duanmau.R;

public class SachAdapter extends RecyclerView.Adapter<SachAdapter.BauHolder>{
    private Context context;
    private SachDAO dao;
    private ArrayList<Sach> list;
    private ArrayList<Sach> listOld;

    public SachAdapter(Context context, SachDAO dao) {
        this.context = context;
        this.dao = dao;
    }

    public void setData(ArrayList<Sach> arr){
        this.list = arr;
        this.listOld = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public BauHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_item_sach, parent, false);
        return new BauHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BauHolder holder, int position) {
        Sach obj = list.get(position);
        if (obj == null){
            return;
        }
        holder.tvMaSach.setText(obj.getMaSach()+"");
        holder.tvTenSach.setText(obj.getTenSach());
        holder.tvGiaThue.setText(obj.getGiaThue()+"");
        LoaiSachDAO loaiSachDAO = new LoaiSachDAO(context);
        LoaiSach objSach = loaiSachDAO.getId(obj.getMaLoai()+"");
        holder.tvTenLoai.setText(objSach.getTenLoai());


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

    public void ShowEditDialog(Context context, int index, Sach obj){
        Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.layout_dialog_sach);
        dialog.setCanceledOnTouchOutside(false);
        Window window = dialog.getWindow();
        if (window == null){
            return;
        }
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        TextInputEditText edTenSach = dialog.findViewById(R.id.ed_dialog_ten_sach);
        edTenSach.setText(obj.getTenSach());
        TextInputEditText edGiaThue = dialog.findViewById(R.id.ed_dialog_gia_thue);
        edGiaThue.setText(obj.getGiaThue()+"");
        Button btnSave = dialog.findViewById(R.id.btn_save_dialog_sach);
        Button btnCancel = dialog.findViewById(R.id.btn_cancel_dialog_sach);

        Spinner spinner = dialog.findViewById(R.id.spinner_loai_sach_in_sach);
        LoaiSachDAO loaiSachDAO = new LoaiSachDAO(context);
        LoaiSachSpinnerAdapter loaiSachSpinnerAdapter = new LoaiSachSpinnerAdapter(context, loaiSachDAO.getAll());
        spinner.setAdapter(loaiSachSpinnerAdapter);
        for (int i = 0; i < loaiSachDAO.getAll().size(); i++) {
            if (obj.getMaLoai() == (loaiSachDAO.getAll().get(i).getMaLoai())){
                spinner.setSelection(i);
                spinner.setSelected(true);
            }
        }


        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String tenSach = edTenSach.getText().toString();
                String giaThue = edGiaThue.getText().toString();

                if (tenSach.isEmpty() || giaThue.isEmpty()){
                    Toast.makeText(context, "Không được để trống thông tin", Toast.LENGTH_SHORT).show();
                }else if(!checkGia(giaThue)){
                    Toast.makeText(context, "Giá thuê phải là số", Toast.LENGTH_SHORT).show();
                }else{
                    obj.setTenSach(tenSach);
                    obj.setGiaThue(Integer.parseInt(giaThue));
                    LoaiSach objLS = (LoaiSach) spinner.getSelectedItem();
                    obj.setMaLoai(objLS.getMaLoai());
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
        dialog.setContentView(R.layout.layout_dialog_sach);
        dialog.setCanceledOnTouchOutside(false);
        Window window = dialog.getWindow();
        if (window == null){
            return;
        }
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        TextInputEditText edTenSach = dialog.findViewById(R.id.ed_dialog_ten_sach);
        TextInputEditText edGiaThue = dialog.findViewById(R.id.ed_dialog_gia_thue);
        Button btnSave = dialog.findViewById(R.id.btn_save_dialog_sach);
        Button btnCancel = dialog.findViewById(R.id.btn_cancel_dialog_sach);

        Spinner spinner = dialog.findViewById(R.id.spinner_loai_sach_in_sach);
        LoaiSachDAO loaiSachDAO = new LoaiSachDAO(context);
        LoaiSachSpinnerAdapter loaiSachSpinnerAdapter = new LoaiSachSpinnerAdapter(context, loaiSachDAO.getAll());
        spinner.setAdapter(loaiSachSpinnerAdapter);

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Sach obj = new Sach();
                String tenSach = edTenSach.getText().toString();
                String giaThue = edGiaThue.getText().toString();

               if (tenSach.isEmpty() || giaThue.isEmpty()){
                   Toast.makeText(context, "Không được để trống thông tin", Toast.LENGTH_SHORT).show();
               }else if(!checkGia(giaThue)){
                   Toast.makeText(context, "Giá thuê phải là số", Toast.LENGTH_SHORT).show();
               }else {
                   obj.setTenSach(tenSach);
                   obj.setGiaThue(Integer.parseInt(giaThue));
                   LoaiSach objLS = (LoaiSach) spinner.getSelectedItem();
                   obj.setMaLoai(objLS.getMaLoai());
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
    
    public boolean checkGia(String price){
        try{
            Integer.parseInt(price);
        }catch (Exception e){
            return false;
        }
        return true;
    }



    public class BauHolder extends RecyclerView.ViewHolder {
        private TextView tvMaSach, tvTenSach, tvGiaThue, tvTenLoai;
        private ImageView imgDel;
        public BauHolder(@NonNull View itemView) {
            super(itemView);

            tvMaSach = itemView.findViewById(R.id.tv_ma_sach);
            tvTenSach = itemView.findViewById(R.id.tv_ten_sach);
            tvGiaThue = itemView.findViewById(R.id.tv_gia_thue);
            tvTenLoai = itemView.findViewById(R.id.tv_ten_loai_sach_in_sach);
            imgDel = itemView.findViewById(R.id.img_del_sach);
        }
    }
}
