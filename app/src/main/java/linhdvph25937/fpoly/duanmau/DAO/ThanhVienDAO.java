package linhdvph25937.fpoly.duanmau.DAO;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;


import linhdvph25937.fpoly.duanmau.DTO.ThanhVien;
import linhdvph25937.fpoly.duanmau.DbHelper.MyDbHelper;

public class ThanhVienDAO {
    SQLiteDatabase database;
    public ThanhVienDAO(Context context){
        MyDbHelper dbHelper = new MyDbHelper(context);
        database = dbHelper.getWritableDatabase();
    }

    public long insert(ThanhVien obj){
        ContentValues cv = new ContentValues();
        cv.put(ThanhVien.HoTen, obj.getHoTen());
        cv.put(ThanhVien.NamSinh, obj.getNameSinh());
        return database.insert(ThanhVien.TB_NAME, null, cv);
    }

    public int update(ThanhVien obj){
        ContentValues cv = new ContentValues();
        cv.put(ThanhVien.HoTen, obj.getHoTen());
        cv.put(ThanhVien.NamSinh, obj.getNameSinh());
        return database.update(ThanhVien.TB_NAME,  cv, "maTV=?", new String[]{obj.getMaTV()+""});
    }

    public int delete(ThanhVien obj){
        return database.delete(ThanhVien.TB_NAME, "maTV=?", new String[]{obj.getMaTV()+""});
    }

    @SuppressLint("Range")
    private ArrayList<ThanhVien> getData(String sql, String...select){
        ArrayList<ThanhVien> list = new ArrayList<>();
        Cursor c = database.rawQuery(sql, select);
        while (c.moveToNext()){
            ThanhVien obj = new ThanhVien();
            obj.setMaTV(Integer.parseInt(c.getString(c.getColumnIndex(ThanhVien.MaTV))));
            obj.setHoTen(c.getString(c.getColumnIndex(ThanhVien.HoTen)));
            obj.setNameSinh(c.getString(c.getColumnIndex(ThanhVien.NamSinh)));
            list.add(obj);
        }
        return list;
    }

    public ArrayList<ThanhVien> getAll(){
        String sql = "SELECT * FROM ThanhVien";
        return getData(sql);
    }

    public ThanhVien getId(String id){
        String sql = "SELECT * FROM ThanhVien WHERE maTV=?";
        ArrayList<ThanhVien> list = getData(sql, id);
        return list.get(0);
    }
}
