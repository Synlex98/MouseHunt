package com.synthia.mousehunt;

import java.io.Serializable;

public class Level implements Serializable {
    int number, image;

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }
    public Level(int number,int image){
        this.number=number;
        this.image=image;
    }
}
