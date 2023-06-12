package linhdvph25937.fpoly.duanmau.DAO;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;


import linhdvph25937.fpoly.duanmau.DTO.ThuThu;
import linhdvph25937.fpoly.duanmau.DbHelper.MyDbHelper;

public class ThuThuDAO {
    SQLiteDatabase database;
    public ThuThuDAO(Context context){
        MyDbHelper dbHelper = new MyDbHelper(context);
        database = dbHelper.getWritableDatabase();
    }

    public long insert(ThuThu obj){
        ContentValues cv = new ContentValues();
        cv.put(ThuThu.MaTT, obj.getMaTT());
        cv.put(ThuThu.HoTen, obj.getHoTen());
        cv.put(ThuThu.MatKhau, obj.getMatKhau());
        return database.insert(ThuThu.TB_NAME, null, cv);
    }

    public int update(ThuThu obj){
        ContentValues cv = new ContentValues();
        cv.put(ThuThu.MaTT, obj.getMaTT());
        cv.put(ThuThu.MatKhau, obj.getMatKhau());
        return database.update(ThuThu.TB_NAME,  cv, "maTT=?", new String[]{obj.getMaTT()});
    }

    public int delete(ThuThu obj){
        return database.delete(ThuThu.TB_NAME, "maTT=?", new String[]{obj.getMaTT()});
    }

    @SuppressLint("Range")
    private ArrayList<ThuThu> getData(String sql, String...select){
        ArrayList<ThuThu> list = new ArrayList<>();
        Cursor c = database.rawQuery(sql, select);
        while (c.moveToNext()){
            ThuThu obj = new ThuThu();
            obj.setMaTT(c.getString(c.getColumnIndex(ThuThu.MaTT)));
            obj.setHoTen(c.getString(c.getColumnIndex(ThuThu.HoTen)));
            obj.setMatKhau(c.getString(c.getColumnIndex(ThuThu.MatKhau)));
            list.add(obj);
        }
        return list;
    }

    public ArrayList<ThuThu> getAll(){
        String sql = "SELECT * FROM ThuThu";
        return getData(sql);
    }

    public ThuThu getId(String id){
        String sql = "SELECT * FROM ThuThu WHERE maTT=?";
        ArrayList<ThuThu> list = getData(sql, id);
        return list.get(0);
    }

    public int CheckLogin(String user, String pass){
        String sql = "SELECT * FROM ThuThu WHERE maTT=? AND matKhau=?";
        ArrayList<ThuThu> list = getData(sql, user, pass);
        if (list.size() > 0){
            return 1;
        }
        return 0;
    }
}
