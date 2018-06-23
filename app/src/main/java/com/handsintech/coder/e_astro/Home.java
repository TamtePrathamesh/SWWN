package com.handsintech.coder.e_astro;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import java.util.HashMap;

public class Home extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, BottomNavigationView.OnNavigationItemSelectedListener {
    SQLiteHandler db;
    Toolbar toolbar;
    SessionManager session;
    DrawerLayout dl;
    TextView navheadername,navheader_email;
    BottomNavigationView bm;
    Snackbar snackbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // getSupportActionBar().hide();
        setContentView(R.layout.activity_home);
        toolbar = (Toolbar) findViewById(R.id.toolbar);

        db = new SQLiteHandler(getApplicationContext());
        dl=findViewById(R.id.drawer_layout);//using this ID for displaying SnackBar
        session = new SessionManager(getApplicationContext());
        setSupportActionBar(toolbar);
//       getActionBar().setDisplayHomeAsUpEnabled(true);
        bm = (BottomNavigationView) findViewById(R.id.navigation);
        bm.setOnNavigationItemSelectedListener
                (new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                        switch (item.getItemId()) {
                            case R.id.navigation_home:

                                getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout, new fragment_new_Home()).commit();

                                break;
                            case R.id.navigation_ask:

                                getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout, new fragment_new_Home()).commit();

                                break;
                            case R.id.navigation_messages:

                                getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout, new fragment_new_Home()).commit();

                                break;
                            case R.id.navigation_product_details:
                                getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout, new BlankFragment()).commit();

                        }

                        return true;
                    }
                });
        getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout, new fragment_new_Home()).commit();


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();


        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View header = navigationView.getHeaderView(0);
        navheadername = header.findViewById(R.id.drawer_layout_name);
        navheader_email = header.findViewById(R.id.drawer_layout_email);


        session = new SessionManager(getApplicationContext());

        if (!session.isLoggedIn()) {
            logoutUser();
        }

        // Fetching user details from sqlite
        HashMap<String, String> user = db.getUserDetails();

        String name = user.get("name");
        String email = user.get("email");

        // Displaying the user details on the screen
        navheadername.setText(name);
        navheader_email.setText(email);


        if (!EveryTimeRequied.getInstance(Home.this).isNetworkAvailable()) {

            snackbar = Snackbar
                    .make(dl, "No internet connection!", Snackbar.LENGTH_LONG)
                    .setAction("Dismiss", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            snackbar.dismiss();
                        }
                    });

// Changing message text color
            snackbar.setActionTextColor(Color.RED);
            snackbar.show();

        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected (MenuItem item){
        // Handle navigation view item clicks here.
        int id = item.getItemId();


        if (id == R.id.nav_camera) {
            logoutUser();

        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    private void logoutUser() {
        session.setLogin(false);

        db.deleteUsers();

        // Launching the login activity
        Intent intent = new Intent(Home.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

//    @Override
//    protected void onStart() {
//        super.onStart();
//
//
//             bm = (BottomNavigationView) findViewById(R.id.navigation);
//
//
//
//    }
}







