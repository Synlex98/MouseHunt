package com.synthia.mousehunt;

import com.google.gson.Gson;

import java.io.Serializable;

public class Points  implements Serializable {
    int _userId,_points;

    public Points(int _userId, int _points) {
        this._userId = _userId;
        this._points = _points;
    }

    public Points() {
    }

    public int get_userId() {
        return _userId;
    }

    public void set_userId(int _userId) {
        this._userId = _userId;
    }

    public int get_points() {
        return _points;
    }

    public void set_points(int _points) {
        this._points = _points;
    }

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }
}
