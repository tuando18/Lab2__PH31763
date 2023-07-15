package com.dovantuan.lab2_ph31763.model;

public class CongViec {
    private int id;
    private String tieude, noidung, ngay, loai;
    private int trangthai;

    public CongViec() {
    }

    public CongViec(int id, String tieude, String noidung, String ngay, String loai, int trangthai) {
        this.id = id;
        this.tieude = tieude;
        this.noidung = noidung;
        this.ngay = ngay;
        this.loai = loai;
        this.trangthai = trangthai;
    }

    public CongViec(String tieude, String noidung, String ngay, String loai) {
        this.tieude = tieude;
        this.noidung = noidung;
        this.ngay = ngay;
        this.loai = loai;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTieude() {
        return tieude;
    }

    public void setTieude(String tieude) {
        this.tieude = tieude;
    }

    public String getNoidung() {
        return noidung;
    }

    public void setNoidung(String noidung) {
        this.noidung = noidung;
    }

    public String getNgay() {
        return ngay;
    }

    public void setNgay(String ngay) {
        this.ngay = ngay;
    }

    public String getLoai() {
        return loai;
    }

    public void setLoai(String loai) {
        this.loai = loai;
    }

    public int getTrangthai() {
        return trangthai;
    }

    public void setTrangthai(int trangthai) {
        this.trangthai = trangthai;
    }
}
