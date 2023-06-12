package linhdvph25937.fpoly.duanmau.DAO;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;


import linhdvph25937.fpoly.duanmau.DTO.Sach;
import linhdvph25937.fpoly.duanmau.DbHelper.MyDbHelper;

public class SachDAO {
    SQLiteDatabase database;
    public SachDAO(Context context){
        MyDbHelper dbHelper = new MyDbHelper(context);
        database = dbHelper.getWritableDatabase();
    }

    public long insert(Sach obj){
        ContentValues cv = new ContentValues();
        cv.put(Sach.TenSach, obj.getTenSach());
        cv.put(Sach.GiaThue, obj.getGiaThue());
        cv.put(Sach.MaLoai, obj.getMaLoai());
        return database.insert(Sach.TB_NAME, null, cv);
    }

    public int update(Sach obj){
        ContentValues cv = new ContentValues();
        cv.put(Sach.TenSach, obj.getTenSach());
        cv.put(Sach.GiaThue, obj.getGiaThue());
        cv.put(Sach.MaLoai, obj.getMaLoai());
        return database.update(Sach.TB_NAME,  cv, "maSach=?", new String[]{obj.getMaSach()+""});
    }

    public int delete(Sach obj){
        return database.delete(Sach.TB_NAME, "maSach=?", new String[]{obj.getMaSach()+""});
    }

    @SuppressLint("Range")
    private ArrayList<Sach> getData(String sql, String...select){
        ArrayList<Sach> list = new ArrayList<>();
        Cursor c = database.rawQuery(sql, select);
        while (c.moveToNext()){
            Sach obj = new Sach();
            obj.setMaSach(Integer.parseInt(c.getString(c.getColumnIndex(Sach.MaSach))));
            obj.setTenSach(c.getString(c.getColumnIndex(Sach.TenSach)));
            obj.setGiaThue(Integer.parseInt(c.getString(c.getColumnIndex(Sach.GiaThue))));
            obj.setMaLoai(Integer.parseInt(c.getString(c.getColumnIndex(Sach.MaLoai))));
            list.add(obj);
        }
        return list;
    }

    public ArrayList<Sach> getAll(){
        String sql = "SELECT * FROM Sach";
        return getData(sql);
    }

    public Sach getId(String id){
        String sql = "SELECT * FROM Sach WHERE maSach=?";
        ArrayList<Sach> list = getData(sql, id);
        return list.get(0);
    }
}
