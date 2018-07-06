package com.handsintech.coder.e_astro;


import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bogdwellers.pinchtozoom.ImageMatrixTouchHandler;
import com.github.chrisbanes.photoview.PhotoView;
import com.squareup.picasso.Picasso;


/**
 * A simple {@link Fragment} subclass.
 */
public class fullImage extends Fragment {



    public fullImage() {
        // Required empty public constructor
    }
private ImageView iv;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_full_image, container, false);


        iv=v.findViewById(R.id.backgroundLinear);
        String url= this.getArguments().getString("123");

        Picasso.with(getActivity()).load(url).into(iv);


        iv.setOnTouchListener(new ImageMatrixTouchHandler(v.getContext()));
//        PhotoView photoView = (PhotoView)v.findViewById(R.id.photo_view);
//        photoView.setImage();





        return v;
    }



}
