package com.handsintech.coder.e_astro;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link product_desc.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link product_desc#newInstance} factory method to
 * create an instance of this fragment.
 */
public class product_desc extends Fragment {
    public static product_desc newInstance() {
        product_desc fragment = new product_desc();
//        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, param1);
//        args.putString(ARG_PARAM2, param2);
//        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_product_desc, container, false);
    }

    }
