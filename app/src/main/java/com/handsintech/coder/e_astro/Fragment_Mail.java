package com.handsintech.coder.e_astro;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;



public class Fragment_Mail extends Fragment {



    public Fragment_Mail() {
        // Required empty public constructor
    }
   public View v;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
      v = inflater.inflate(R.layout.fragment_fragment__mail, container, false);




        return v;
    }



}
