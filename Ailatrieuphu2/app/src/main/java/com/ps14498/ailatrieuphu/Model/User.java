package com.ps14498.ailatrieuphu.Model;

public class User {
    private String ten, cap, id;
    private int diem;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTen() {
        return ten;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }

    public String getCap() {
        return cap;
    }

    public void setCap(String cap) {
        this.cap = cap;
    }

    public int getDiem() {
        return diem;
    }

    public void setDiem(int diem) {
        this.diem = diem;
    }

    public User(String id,String ten, String cap, int diem) {
        this.id = id;
        this.ten = ten;
        this.cap = cap;
        this.diem = diem;
    }
    public User(){

    }
}
