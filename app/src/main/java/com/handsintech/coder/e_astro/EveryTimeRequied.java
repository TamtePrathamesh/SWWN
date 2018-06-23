package com.handsintech.coder.e_astro;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by Admin on 8/12/2017.
 */

public class EveryTimeRequied {


   public static EveryTimeRequied mInstance;
    public static Context mCtx;

    public EveryTimeRequied(Context context) {
        mCtx = context;
    }

    public static synchronized EveryTimeRequied getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new EveryTimeRequied(context);
        }
        return mInstance;
    }


    public boolean isNetworkAvailable() {
        // Using ConnectivityManager to check for Network Connection
        ConnectivityManager connectivityManager = (ConnectivityManager)mCtx.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager
                .getActiveNetworkInfo();
        return activeNetworkInfo != null;
    }

//    public void showAboutUS(){
//        /*Dialog dialog=new Dialog(mCtx);
//        dialog.setTitle("MGM's COE");
//        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//        dialog.setContentView(R.layout.developed_by);
//        WindowManager.LayoutParams lp=new WindowManager.LayoutParams();
//        lp.width = WindowManager.LayoutParams.WRAP_CONTENT;
//        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
//
//        dialog.getWindow().setAttributes(lp);
//        //dialog.setTitle("MGM");
//
//        dialog.show();*/
//
//    }
}
