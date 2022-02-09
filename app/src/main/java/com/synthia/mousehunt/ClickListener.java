package com.synthia.mousehunt;

import android.view.View;

public interface ClickListener {
    default  void onClick(View view, Level level){

    }
    default void onClick(View view,RatHole ratHole){

    }

}
