package linhdvph25937.fpoly.duanmau.DAO;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import linhdvph25937.fpoly.duanmau.DTO.Sach;
import linhdvph25937.fpoly.duanmau.DTO.Top;
import linhdvph25937.fpoly.duanmau.DbHelper.MyDbHelper;

public class ThongKeDAO {
    private SQLiteDatabase database;
    private Context context;
    public ThongKeDAO(Context context){
        this.context = context;
        MyDbHelper dbHelper = new MyDbHelper(context);
        database = dbHelper.getWritableDatabase();
    }

    //Thong ke top 10
    @SuppressLint("Range")
    public List<Top> getTop(){
        String sql = "SELECT maSach, count(maSach) as soLuong FROM PhieuMuon GROUP BY maSach ORDER BY soLuong DESC LIMIT 10";
        List<Top> list = new ArrayList<>();
        SachDAO sachDAO = new SachDAO(context);
        Cursor c = database.rawQuery(sql, null);
        while (c.moveToNext()){
            Top top = new Top();
            Sach sach = sachDAO.getId(c.getString(c.getColumnIndex("maSach")));
            top.tenSach = sach.getTenSach();
            top.soLuong = Integer.parseInt(c.getString(c.getColumnIndex("soLuong")));
            list.add(top);
        }
        return list;
    }

    @SuppressLint("Range")
    public int getDoanhThu(String tuNgay, String denNgay){
        String sql = "SELECT SUM(tienThue) as doanhThu FROM PhieuMuon WHERE ngay BETWEEN ? AND ?";
        ArrayList<Integer> list = new ArrayList<>();
        Cursor c = database.rawQuery(sql, new String[]{tuNgay, denNgay});
        while (c.moveToNext()){
            try {
                list.add(Integer.parseInt(c.getString(c.getColumnIndex("doanhThu"))));
            }catch (Exception e){
                list.add(0);
            }
        }
        return list.get(0);
    }
}
