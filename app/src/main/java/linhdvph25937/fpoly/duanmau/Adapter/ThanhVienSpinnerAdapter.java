package linhdvph25937.fpoly.duanmau.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;


import linhdvph25937.fpoly.duanmau.DTO.ThanhVien;
import linhdvph25937.fpoly.duanmau.R;

public class ThanhVienSpinnerAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<ThanhVien> list;

    public ThanhVienSpinnerAdapter(Context context, ArrayList<ThanhVien> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        ThanhVien obj = list.get(i);
        return obj;
    }

    @Override
    public long getItemId(int i) {
        ThanhVien obj = list.get(i);
        return obj.getMaTV();
    }

    private class ViewHolder{
        TextView tvMaLoai, tvTenLoai;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder bau;
        if (view == null){
            bau = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.layout_spinner_thanh_vien, null);

            bau.tvMaLoai = view.findViewById(R.id.tv_id_spinner_thanh_vien);
            bau.tvTenLoai = view.findViewById(R.id.tv_ten_spinner_thanh_vien);

            view.setTag(bau);
        }else{
            bau = (ViewHolder) view.getTag();
        }
        ThanhVien obj = list.get(i);
        bau.tvMaLoai.setText(obj.getMaTV()+"");
        bau.tvTenLoai.setText(obj.getHoTen());
        return view;
    }
}
