package linhdvph25937.fpoly.duanmau.DTO;

public class ThanhVien {
    private int maTV;
    private String hoTen;
    private String namSinh;

    public static final String TB_NAME = "ThanhVien";
    public static final String MaTV = "maTV";
    public static final String HoTen = "hoTen";
    public static final String NamSinh = "namSinh";

    public int getMaTV() {
        return maTV;
    }

    public void setMaTV(int maTV) {
        this.maTV = maTV;
    }

    public String getHoTen() {
        return hoTen;
    }

    public void setHoTen(String hoTen) {
        this.hoTen = hoTen;
    }

    public String getNameSinh() {
        return namSinh;
    }

    public void setNameSinh(String nameSinh) {
        this.namSinh = nameSinh;
    }
}
