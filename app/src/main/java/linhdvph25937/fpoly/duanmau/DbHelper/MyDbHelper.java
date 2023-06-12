package linhdvph25937.fpoly.duanmau.DbHelper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MyDbHelper extends SQLiteOpenHelper {
    static final String dbName = "testBai";
    static final int dbVersion = 1;

   public MyDbHelper(Context context){
       super(context, dbName, null, dbVersion);
   }
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String createTbLoaiSach = "CREATE TABLE LoaiSach ( maLoai INTEGER, tenLoai TEXT NOT NULL," +
                " PRIMARY KEY(maLoai AUTOINCREMENT) )";
        sqLiteDatabase.execSQL(createTbLoaiSach);


        String createPhieuMuon =
                "CREATE TABLE PhieuMuon ( maPM INTEGER PRIMARY KEY AUTOINCREMENT, maTT TEXT REFERENCES ThuThu(maTT)," +
                        " maTV INTEGER REFERENCES ThanhVien(maTV)," +
                        " maSach INTEGER REFERENCES Sach(maSach), tienThue INTEGER , ngay DATE ," +
                        " traSach INTEGER )";
        sqLiteDatabase.execSQL(createPhieuMuon);

        String createTbSach = "CREATE TABLE Sach ( maSach INTEGER, tenSach TEXT NOT NULL," +
                " giaThue INTEGER NOT NULL, maLoai INTEGER NOT NULL, PRIMARY KEY(maSach AUTOINCREMENT) )";
        sqLiteDatabase.execSQL(createTbSach);

        String createTbThanhVien = "CREATE TABLE ThanhVien ( maTV INTEGER, hoTen TEXT NOT NULL," +
                " namSinh TEXT NOT NULL, PRIMARY KEY(maTV AUTOINCREMENT) )";
        sqLiteDatabase.execSQL(createTbThanhVien);

        String createTbThuThu = "CREATE TABLE ThuThu ( maTT TEXT, hoTen TEXT NOT NULL," +
                " matKhau TEXT NOT NULL, PRIMARY KEY(maTT) )";
        sqLiteDatabase.execSQL(createTbThuThu);

        String sql = "alter table ThuThu add diachi TEXT";
        sqLiteDatabase.execSQL(sql);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

            String dropTbLoaiSach = "drop table if exists LoaiSach";
            sqLiteDatabase.execSQL(dropTbLoaiSach);

            String dropTbPhieuMuon = "drop table if exists PhieuMuon";
            sqLiteDatabase.execSQL(dropTbPhieuMuon);

            String dropTbSach = "drop table if exists Sach";
            sqLiteDatabase.execSQL(dropTbSach);

            String dropTbThanhVien = "drop table if exists ThanhVien";
            sqLiteDatabase.execSQL(dropTbThanhVien);

            String dropTbThuThu = "drop table if exists ThuThu";
            sqLiteDatabase.execSQL(dropTbThuThu);

            onCreate(sqLiteDatabase);

    }
}
