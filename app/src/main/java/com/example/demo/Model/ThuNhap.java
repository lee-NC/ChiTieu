package com.example.demo.Model;

public class ThuNhap {
    private int id;
    private int srcimg;
    private String loai;
    private Double tien;
    private String tgian;
    private String ghiChu;


    public ThuNhap() {
    }

    public ThuNhap(int id, int srcimg, String loai, Double tien, String tgian, String ghiChu) {
        this.id = id;
        this.srcimg = srcimg;
        this.loai = loai;
        this.tien = tien;
        this.tgian = tgian;
        this.ghiChu = ghiChu;
    }

    public ThuNhap(Double tien, String tgian) {
        this.tien = tien;
        this.tgian = tgian;
    }

    public ThuNhap(int srcimg, String loai, Double tien, String tgian, String ghiChu) {
        this.srcimg = srcimg;
        this.loai = loai;
        this.tien = tien;
        this.tgian = tgian;
        this.ghiChu = ghiChu;
    }

    public int getSrcimg() {
        return srcimg;
    }

    public void setSrcimg(int srcimg) {
        this.srcimg = srcimg;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    public String getLoai() {
        return loai;
    }

    public void setLoai(String loai) {
        this.loai = loai;
    }

    public Double getTien() {
        return tien;
    }

    public void setTien(Double tien) {
        this.tien = tien;
    }

    public String getTgian() {
        return tgian;
    }

    public void setTgian(String tgian) {
        this.tgian = tgian;
    }

    public String getGhiChu() {
        return ghiChu;
    }

    public void setGhiChu(String ghiChu) {
        this.ghiChu = ghiChu;
    }


    @Override
    public String toString() {
        return id+"-"+loai + "-" +tien + "-" + tgian + "-" + ghiChu ;
    }
}
