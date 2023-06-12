package linhdvph25937.fpoly.duanmau.DTO;

public class Sach {
    private int maSach;
    private String tenSach;
    private int giaThue;
    private int maLoai;

    public static final String TB_NAME = "Sach";
    public static final String MaSach = "maSach";
    public static final String TenSach = "tenSach";
    public static final String GiaThue = "giaThue";
    public static final String MaLoai = "maLoai";


    public int getMaSach() {
        return maSach;
    }

    public void setMaSach(int maSach) {
        this.maSach = maSach;
    }

    public String getTenSach() {
        return tenSach;
    }

    public void setTenSach(String tenSach) {
        this.tenSach = tenSach;
    }

    public int getGiaThue() {
        return giaThue;
    }

    public void setGiaThue(int giaThue) {
        this.giaThue = giaThue;
    }

    public int getMaLoai() {
        return maLoai;
    }

    public void setMaLoai(int maLoai) {
        this.maLoai = maLoai;
    }

}
