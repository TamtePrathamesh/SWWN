package com.handsintech.coder.e_astro;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;
import com.handsintech.coder.e_astro.Activites.Home;
import com.handsintech.coder.e_astro.Activites.LoginActivity;

import java.util.HashMap;


public class Setting extends Fragment {


    SessionManager session;
    SQLiteHandler db;
    View v;

    public Setting() {
        // Required empty public constructor
    }

    ImageView profile_image_view;
    TextView profilename_txtView,logouttxt;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       v= inflater.inflate(R.layout.fragment_setting, container, false);

        session=new SessionManager(getActivity());

        profile_image_view=v.findViewById(R.id.profile_image);
        profilename_txtView=v.findViewById(R.id.profile_name);
        logouttxt=v.findViewById(R.id.profile_logout_txtview);

        Toolbar bar=Toolbar.class.cast(getActivity().findViewById(R.id.toolbar));
        bar.setTitle("Settings");


        db = new SQLiteHandler(getActivity());

        ColorGenerator generator = ColorGenerator.MATERIAL;

        HashMap<String, String> user = db.getUserDetails();

        String name = user.get("name");
       String email = user.get("email");

        // Displaying the user details on the screen
        profilename_txtView.setText(name);
      // navheader_email.setText(email);


        int color = generator.getColor(name);

        TextDrawable drawable = TextDrawable.builder()
                .buildRound(String.valueOf(name.charAt(0)).toUpperCase(), color);


         profile_image_view = (ImageView) v.findViewById(R.id.profile_image);
        profile_image_view.setImageDrawable(drawable);
        profile_image_view.bringToFront();

logouttxt.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        logoutUser();
    }
});


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
    private void logoutUser() {
        session.setLogin(false);

        db.deleteUsers();



        // Launching the login activity
        Intent intent = new Intent(getActivity(), LoginActivity.class);
        if(SharedPref.getInstance(getActivity()).IsUserRegiserted()){
            SharedPref.getInstance(getActivity()).UserRegiserted(false);
            intent.putExtra("check","user");}
        else if(SharedPref.getInstance(getActivity()).IsexpertRegiserted()){
            SharedPref.getInstance(getActivity()).ExpertRegiserted(false);
            intent.putExtra("check", "expert");

        }
        startActivity(intent);
        getActivity().finish();
    }
}
