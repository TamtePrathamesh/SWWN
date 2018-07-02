package com.handsintech.coder.e_astro;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;

import java.util.HashMap;


public class Setting extends Fragment {


    SessionManager session;
    SQLiteHandler db;

    public Setting() {
        // Required empty public constructor
    }

    ImageView profile_image_view;
    TextView profilename_txtView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_setting, container, false);

        profile_image_view=v.findViewById(R.id.profile_image);
        profilename_txtView=v.findViewById(R.id.profile_name);


        db = new SQLiteHandler(getActivity());

        ColorGenerator generator = ColorGenerator.MATERIAL;

        HashMap<String, String> user = db.getUserDetails();

        String name = user.get("name");
       // String email = user.get("email");

        // Displaying the user details on the screen
        profilename_txtView.setText(name);
       // navheader_email.setText(email);


        int color = generator.getColor(name);

        TextDrawable drawable = TextDrawable.builder()
                .buildRound(String.valueOf(name.charAt(0)).toUpperCase(), color);


         profile_image_view = (ImageView) v.findViewById(R.id.profile_image);
        profile_image_view.setImageDrawable(drawable);
        profile_image_view.bringToFront();




        return v;
    }
    @Override
    public void onPrepareOptionsMenu(Menu menu) {
//        MenuItem item = menu.findItem(R.id.search_menu);
//        MenuItem i=menu.findItem(R.id.action_settings);
////        i.setVisible(false);
//        item.setVisible(false);
        menu.clear();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }
}
