package com.synthia.mousehunt;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;

public class Utils {
    private Utils(){}
    private static Utils utils;
    public static Utils getInstance() {
        if (utils==null){
            utils=new Utils();
        }
        return utils;
    }

    public static int CURRENT_LEVEL=1;

    public void saveUser(Context context,User user){
        SharedPreferences preferences= context.getSharedPreferences("saved",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=preferences.edit();
        editor.putString("cuser",user.toString());
        editor.apply();
    }

    public boolean userExists(Context context){
        SharedPreferences preferences= context.getSharedPreferences("saved",Context.MODE_PRIVATE);
        String userString=preferences.getString("cuser",null);
        if (userString==null){
            return false;
        }
        User user=new Gson().fromJson(userString,User.class);
        return user.get_userId() != 0 && user.get_userId() >= 100;
    }

    public User getUser(Context context){
        SharedPreferences preferences= context.getSharedPreferences("saved",Context.MODE_PRIVATE);
        String userString=preferences.getString("cuser",null);
        return new Gson().fromJson(userString,User.class);
    }
}
