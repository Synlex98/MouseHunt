package com.synthia.mousehunt;

import com.google.gson.Gson;

import java.io.Serializable;

public class User implements Serializable {
    Level _level;
    int _userId=0;
    Points _points;

    public User(Level _level, int _userId, Points _points) {
        this._level = _level;
        this._userId = _userId;
        this._points = _points;
    }

    public Level get_level() {
        return _level;
    }

    public void set_level(Level _level) {
        this._level = _level;
    }

    public int get_userId() {
        return _userId;
    }

    public void set_userId(int _userId) {
        this._userId = _userId;
    }

    public Points get_points() {
        return _points;
    }

    public void set_points(Points _points) {
        this._points = _points;
    }

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }
}
