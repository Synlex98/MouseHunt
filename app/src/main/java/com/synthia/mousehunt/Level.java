package com.synthia.mousehunt;

import com.google.gson.Gson;

import java.io.Serializable;

public class Level implements Serializable {
    int _levelNumber, image=0,_userId;

    public Level(int _levelNumber, int image, int _userId) {
        this._levelNumber = _levelNumber;
        this.image = image;
        this._userId = _userId;
    }

    public int get_levelNumber() {
        return _levelNumber;
    }

    public void set_levelNumber(int _levelNumber) {
        this._levelNumber = _levelNumber;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public int get_userId() {
        return _userId;
    }

    public void set_userId(int _userId) {
        this._userId = _userId;
    }

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }
}
