package linhdvph25937.fpoly.duanmau.DAO;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import linhdvph25937.fpoly.duanmau.DTO.LoaiSach;
import linhdvph25937.fpoly.duanmau.DbHelper.MyDbHelper;

public class LoaiSachDAO {
    SQLiteDatabase database;
    public LoaiSachDAO(Context context){
        MyDbHelper dbHelper = new MyDbHelper(context);
        database = dbHelper.getWritableDatabase();
    }

    public long insert(LoaiSach obj){
        ContentValues cv = new ContentValues();
        cv.put(LoaiSach.TenLoai, obj.getTenLoai());
        return database.insert(LoaiSach.NAME_TB, null, cv);
    }

    public int update(LoaiSach obj){
        ContentValues cv = new ContentValues();
        cv.put(LoaiSach.TenLoai, obj.getTenLoai());
        return database.update(LoaiSach.NAME_TB,  cv, "maLoai=?", new String[]{obj.getMaLoai()+""});
    }

    public int delete(LoaiSach obj){
        return database.delete(LoaiSach.NAME_TB, "maLoai=?", new String[]{obj.getMaLoai()+""});
    }

    @SuppressLint("Range")
    private ArrayList<LoaiSach> getData(String sql, String...select){
        ArrayList<LoaiSach> list = new ArrayList<>();
        Cursor c = database.rawQuery(sql, select);
        while (c.moveToNext()){
            LoaiSach obj = new LoaiSach();
            obj.setMaLoai(Integer.parseInt(c.getString(c.getColumnIndex(LoaiSach.MaLoai))));
            obj.setTenLoai(c.getString(c.getColumnIndex(LoaiSach.TenLoai)));
            list.add(obj);
        }
        return list;
    }

    public ArrayList<LoaiSach> getAll(){
        String sql = "SELECT * FROM LoaiSach";
        return getData(sql);
    }

    public LoaiSach getId(String id){
        String sql = "SELECT * FROM LoaiSach WHERE maLoai=?";
        ArrayList<LoaiSach> list = getData(sql, id);
        return list.get(0);
    }
}
