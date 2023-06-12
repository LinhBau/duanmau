package linhdvph25937.fpoly.duanmau.DAO;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;


import linhdvph25937.fpoly.duanmau.DTO.PhieuMuon;
import linhdvph25937.fpoly.duanmau.DbHelper.MyDbHelper;

public class PhieuMuonDAO {
    SQLiteDatabase database;
    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    public PhieuMuonDAO(Context context){
        MyDbHelper dbHelper = new MyDbHelper(context);
        database = dbHelper.getWritableDatabase();
    }

    public long insert(PhieuMuon obj){
        ContentValues cv = new ContentValues();
        cv.put(PhieuMuon.MaTV, obj.getMaTV());
        cv.put(PhieuMuon.MaSach, obj.getMaSach());
        cv.put(PhieuMuon.TienThue, obj.getTienThue());
        cv.put(PhieuMuon.TraSach, obj.getTraSach());
        cv.put(PhieuMuon.Ngay, sdf.format(obj.getNgay()));
        return database.insert(PhieuMuon.TB_NAME, null, cv);
    }

    public int update(PhieuMuon obj){
        ContentValues cv = new ContentValues();
        cv.put(PhieuMuon.MaTV, obj.getMaTV());
        cv.put(PhieuMuon.MaSach, obj.getMaSach());
        cv.put(PhieuMuon.TienThue, obj.getTienThue());
        cv.put(PhieuMuon.TraSach, obj.getTraSach());
        cv.put(PhieuMuon.Ngay, sdf.format(obj.getNgay()));
        return database.update(PhieuMuon.TB_NAME,  cv, "maPM=?", new String[]{obj.getMaPM()+""});
    }

    public int delete(PhieuMuon obj){
        return database.delete(PhieuMuon.TB_NAME, "maPM=?", new String[]{obj.getMaPM()+""});
    }

    @SuppressLint("Range")
    private ArrayList<PhieuMuon> getData(String sql, String...select){
        ArrayList<PhieuMuon> list = new ArrayList<>();
        Cursor c = database.rawQuery(sql, select);
        while (c.moveToNext()){
            PhieuMuon obj = new PhieuMuon();
            obj.setMaPM(Integer.parseInt(c.getString(c.getColumnIndex(PhieuMuon.MaPM))));
            obj.setMaTV(Integer.parseInt(c.getString(c.getColumnIndex(PhieuMuon.MaTV))));
            obj.setMaSach(Integer.parseInt(c.getString(c.getColumnIndex(PhieuMuon.MaSach))));
            obj.setTienThue(Integer.parseInt(c.getString(c.getColumnIndex(PhieuMuon.TienThue))));
            obj.setTraSach(Integer.parseInt(c.getString(c.getColumnIndex(PhieuMuon.TraSach))));

            try {
                obj.setNgay(sdf.parse(c.getString(c.getColumnIndex(PhieuMuon.Ngay))));
            } catch (ParseException e) {
                e.printStackTrace();
            }




            list.add(obj);
        }
        return list;
    }

    public ArrayList<PhieuMuon> getAll(){
        String sql = "SELECT * FROM PhieuMuon";
        return getData(sql);
    }

    public PhieuMuon getId(String id){
        String sql = "SELECT * FROM PhieuMuon WHERE maPM=?";
        ArrayList<PhieuMuon> list = getData(sql, id);
        return list.get(0);
    }
}
