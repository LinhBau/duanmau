package linhdvph25937.fpoly.duanmau.DTO;

public class LoaiSach {
    private int maLoai;
    private String tenLoai;

    public static final String NAME_TB = "LoaiSach";
    public static final String MaLoai = "maLoai";
    public static final String TenLoai = "tenLoai";

    public int getMaLoai() {
        return maLoai;
    }

    public void setMaLoai(int maLoai) {
        this.maLoai = maLoai;
    }

    public String getTenLoai() {
        return tenLoai;
    }

    public void setTenLoai(String tenLoai) {
        this.tenLoai = tenLoai;
    }
}
