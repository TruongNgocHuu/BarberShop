package com.huutocdai.barbershopapp.Model;

import java.io.Serializable;

public class HairStyleOb implements Serializable {
    private int img;

    public HairStyleOb(int img) {
        this.img = img;
    }

    public HairStyleOb() {
    }

    public int getImg() {
        return img;
    }

    public void setImg(int img) {
        this.img = img;
    }
}
