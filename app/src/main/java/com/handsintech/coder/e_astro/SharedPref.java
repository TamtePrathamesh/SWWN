package com.handsintech.coder.e_astro;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

public class SharedPref {




    private static SharedPref mInstance;
    private static Context mCtx;

    private SharedPref(Context context) {
        mCtx = context;
    }

    public static synchronized SharedPref getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new SharedPref(context);
        }
        return mInstance;
    }

    public Boolean ExpertRegiserted(Boolean check){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences("ExpertRegistration", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("Expert_is_registered", check);
        editor.apply();
        editor.commit();
        Log.d("swe",check+" "+"stored");
        return true;

    }
    public Boolean IsexpertRegiserted() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences("ExpertRegistration", Context.MODE_PRIVATE);
        return sharedPreferences.getBoolean("Expert_is_registered", false);

    }
        public Boolean UserRegiserted(Boolean check1){
            SharedPreferences sharedPreferences = mCtx.getSharedPreferences("UserRegistration", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean("User_is_registered", check1);
            editor.apply();
            editor.commit();

            return true;

        }
        public Boolean IsUserRegiserted(){
            SharedPreferences sharedPreferences = mCtx.getSharedPreferences("UserRegistration", Context.MODE_PRIVATE);
            return sharedPreferences.getBoolean("User_is_registered",false);


    }

}
