package linhdvph25937.fpoly.duanmau.Adapter;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


import linhdvph25937.fpoly.duanmau.DAO.PhieuMuonDAO;
import linhdvph25937.fpoly.duanmau.DAO.SachDAO;
import linhdvph25937.fpoly.duanmau.DAO.ThanhVienDAO;
import linhdvph25937.fpoly.duanmau.DTO.PhieuMuon;
import linhdvph25937.fpoly.duanmau.DTO.Sach;
import linhdvph25937.fpoly.duanmau.DTO.ThanhVien;
import linhdvph25937.fpoly.duanmau.R;

public class PhieuMuonAdapter extends RecyclerView.Adapter<PhieuMuonAdapter.BauHolder>{
    private Context context;
    private PhieuMuonDAO dao;
    private ArrayList<PhieuMuon> list;
    private SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

    public PhieuMuonAdapter(Context context, PhieuMuonDAO dao) {
        this.context = context;
        this.dao = dao;
    }

    public void setData(ArrayList<PhieuMuon> arr){
        this.list = arr;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public BauHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_item_phieu_muon, parent, false);
        return new BauHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BauHolder holder, int position) {
        PhieuMuon obj = list.get(position);
        if (obj == null){
            return;
        }
        holder.tvMaPM.setText(obj.getMaPM()+"");


        ThanhVienDAO thanhVienDAO = new ThanhVienDAO(context);
        ThanhVien objTV = thanhVienDAO.getId(obj.getMaTV()+"");
        holder.tvTenTV.setText(objTV.getHoTen());

        SachDAO sachDAO = new SachDAO(context);
        Sach objSach = sachDAO.getId(obj.getMaSach()+"");
        holder.tvTenSach.setText(objSach.getTenSach());

        holder.tvTienThue.setText(objSach.getGiaThue()+"");
        if (obj.getTraSach() == 1){
            holder.tvTraSach.setText("Đã trả sách");
            holder.tvTraSach.setTextColor(Color.BLUE);
        }else {
            holder.tvTraSach.setText("Chưa trả sách");
            holder.tvTraSach.setTextColor(Color.RED);
        }

        holder.tvNgayThue.setText("Ngày thuê: "+sdf.format(obj.getNgay()));


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

    public void ShowEditDialog(Context context, int index, PhieuMuon obj){
        Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.layout_dialog_phieu_muon);
        dialog.setCanceledOnTouchOutside(false);
        Window window = dialog.getWindow();
        if (window == null){
            return;
        }
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        Spinner spinnerTV = dialog.findViewById(R.id.spMaTV);
        Spinner spinnerSach = dialog.findViewById(R.id.spMaSach);

        CheckBox chkTraSach = dialog.findViewById(R.id.chkTraSach);
        if (obj.getTraSach() == 1){
            chkTraSach.setChecked(true);
        }else{
            chkTraSach.setChecked(false);
        }

        Button btnSave = dialog.findViewById(R.id.btnSavePM);
        Button btnCancel = dialog.findViewById(R.id.btnCancelPM);

        ThanhVienDAO thanhVienDAO = new ThanhVienDAO(context);
        ThanhVienSpinnerAdapter thanhVienSpinnerAdapter = new ThanhVienSpinnerAdapter(context, thanhVienDAO.getAll());
        spinnerTV.setAdapter(thanhVienSpinnerAdapter);
        for (int i = 0; i < thanhVienDAO.getAll().size(); i++) {
            if (obj.getMaTV() == (thanhVienDAO.getAll().get(i).getMaTV())){
                spinnerTV.setSelected(true);
                spinnerTV.setSelection(i);
            }
        }

        SachDAO sachDAO = new SachDAO(context);
        SachSpinnerAdapter sachSpinnerAdapter = new SachSpinnerAdapter(context, sachDAO.getAll());
        spinnerSach.setAdapter(sachSpinnerAdapter);
        for (int i = 0; i < sachDAO.getAll().size(); i++) {
            if (obj.getMaSach() == (sachDAO.getAll().get(i).getMaSach())){
                spinnerSach.setSelected(true);
                spinnerSach.setSelection(i);
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
                ThanhVien objTV = (ThanhVien) spinnerTV.getSelectedItem();
                obj.setMaTV(objTV.getMaTV());
                Sach objSach = (Sach) spinnerSach.getSelectedItem();
                obj.setMaSach(objSach.getMaSach());
                if (chkTraSach.isChecked()){
                    obj.setTraSach(1);
                }else{
                    obj.setTraSach(0);
                }
                obj.setNgay(new Date());
                obj.setTienThue(objSach.getGiaThue());
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
        });

        dialog.show();
    }

    public void ShowAddDialog(Context context){
        Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.layout_dialog_phieu_muon);
        dialog.setCanceledOnTouchOutside(false);
        Window window = dialog.getWindow();
        if (window == null){
            return;
        }
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        Spinner spinnerTV = dialog.findViewById(R.id.spMaTV);
        Spinner spinnerSach = dialog.findViewById(R.id.spMaSach);
        CheckBox chkTraSach = dialog.findViewById(R.id.chkTraSach);
        Button btnSave = dialog.findViewById(R.id.btnSavePM);
        Button btnCancel = dialog.findViewById(R.id.btnCancelPM);

        ThanhVienDAO thanhVienDAO = new ThanhVienDAO(context);
        ThanhVienSpinnerAdapter thanhVienSpinnerAdapter = new ThanhVienSpinnerAdapter(context, thanhVienDAO.getAll());
        spinnerTV.setAdapter(thanhVienSpinnerAdapter);

        SachDAO sachDAO = new SachDAO(context);
        SachSpinnerAdapter sachSpinnerAdapter = new SachSpinnerAdapter(context, sachDAO.getAll());
        spinnerSach.setAdapter(sachSpinnerAdapter);

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PhieuMuon obj = new PhieuMuon();
                ThanhVien objTV = (ThanhVien) spinnerTV.getSelectedItem();
                obj.setMaTV(objTV.getMaTV());
                Sach objSach = (Sach) spinnerSach.getSelectedItem();
                obj.setMaSach(objSach.getMaSach());
                if (chkTraSach.isChecked()){
                    obj.setTraSach(1);
                }else{
                    obj.setTraSach(0);
                }
                obj.setNgay(new Date());
                obj.setTienThue(objSach.getGiaThue());
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
        private TextView tvMaPM, tvTenTV, tvTenSach, tvTienThue, tvTraSach, tvNgayThue;
        private ImageView imgDel;
        public BauHolder(@NonNull View itemView) {
            super(itemView);

            tvMaPM = itemView.findViewById(R.id.tvMaPm);
            tvTenTV = itemView.findViewById(R.id.tvTenTV);
            tvTenSach = itemView.findViewById(R.id.tvTenSach);
            tvTienThue = itemView.findViewById(R.id.tvTienThue);
            tvTraSach = itemView.findViewById(R.id.tvTraSach);
            tvNgayThue = itemView.findViewById(R.id.tvNgayPM);
            imgDel = itemView.findViewById(R.id.imgDeletePM);
        }
    }
}
