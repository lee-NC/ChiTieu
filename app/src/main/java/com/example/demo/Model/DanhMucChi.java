package com.example.demo.Model;

public class DanhMucChi {
    private int id, srcimg;
    private String loai;

    public DanhMucChi(int id, int srcimg, String loai) {
        this.id = id;
        this.srcimg = srcimg;
        this.loai = loai;
    }

    public DanhMucChi(int srcimg, String loai) {
        this.srcimg = srcimg;
        this.loai = loai;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSrcimg() {
        return srcimg;
    }

    public void setSrcimg(int srcimg) {
        this.srcimg = srcimg;
    }

    public String getLoai() {
        return loai;
    }

    public void setLoai(String loai) {
        this.loai = loai;
    }
}
