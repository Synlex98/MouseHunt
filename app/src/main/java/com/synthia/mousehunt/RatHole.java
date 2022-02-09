package com.synthia.mousehunt;

import android.util.Property;

import androidx.annotation.Nullable;

public class RatHole {
    int id;
    boolean isMain;

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setMain(boolean main) {
        isMain = main;
    }

    public boolean isMain() {
        return isMain;
    }

    public RatHole(int id, boolean isMain){
        this.id=id;
        this.isMain=isMain;
}
}
