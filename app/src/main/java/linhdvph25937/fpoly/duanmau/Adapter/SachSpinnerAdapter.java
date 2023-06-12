package linhdvph25937.fpoly.duanmau.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import linhdvph25937.fpoly.duanmau.DTO.Sach;
import linhdvph25937.fpoly.duanmau.R;

public class SachSpinnerAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<Sach> list;

    public SachSpinnerAdapter(Context context, ArrayList<Sach> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        Sach obj = list.get(i);
        return obj;
    }

    @Override
    public long getItemId(int i) {
        Sach obj = list.get(i);
        return obj.getMaSach();
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
            view = inflater.inflate(R.layout.layout_spinner_sach, null);

            bau.tvMaLoai = view.findViewById(R.id.tv_id_spinner_sach);
            bau.tvTenLoai = view.findViewById(R.id.tv_ten_spinner_sach);

            view.setTag(bau);
        }else{
            bau = (ViewHolder) view.getTag();
        }
        Sach obj = list.get(i);
        bau.tvMaLoai.setText(obj.getMaSach()+"");
        bau.tvTenLoai.setText(obj.getTenSach());
        return view;
    }
}
