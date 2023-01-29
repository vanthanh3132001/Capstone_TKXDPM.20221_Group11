package entity;

public class station {
    private int id;
    private String tenBaiXe;
    private String viTriBaiXe;
    private String dienTichBaiXe;

    private String soLuongXeTrongBai;
    public station(int id,String tenBaiXe, String viTriBaiXe,String dienTichBaiXe, String soLuongXeTrongBai) {
        this.id = id;
        this.tenBaiXe = tenBaiXe;
        this.viTriBaiXe = viTriBaiXe;
        this.dienTichBaiXe = dienTichBaiXe;
        this.soLuongXeTrongBai=soLuongXeTrongBai;

    }

    public station(int id, String tenBaiXe, String viTriBaiXe) {
        this.id = id;
        this.tenBaiXe = tenBaiXe;
        this.viTriBaiXe = viTriBaiXe;
    }

    public station() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTenBaiXe() {
        return tenBaiXe;
    }

    public void setTenBaiXe(String tenBaiXe) {
        this.tenBaiXe = tenBaiXe;
    }

    public String getViTriBaiXe() {
        return viTriBaiXe;
    }

    public void setViTriBaiXe(String viTriBaiXe) {
        this.viTriBaiXe = viTriBaiXe;
    }

    public String getDienTichBaiXe() {
        return dienTichBaiXe;
    }

    public void setDienTichBaiXe(String dienTichBaiXe) {
        this.dienTichBaiXe = dienTichBaiXe;
    }

    public String getSoLuongXeTrongBai() {
        return soLuongXeTrongBai;
    }

    public void setSoLuongXeTrongBai(String soLuongXeTrongBai) {
        this.soLuongXeTrongBai = soLuongXeTrongBai;
    }
}
