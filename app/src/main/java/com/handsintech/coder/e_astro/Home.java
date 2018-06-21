package com.handsintech.coder.e_astro;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.HashMap;

public class Home extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    SQLiteHandler db;
    Toolbar toolbar;
    SessionManager session;
    TextView navheadername,navheader_email;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // getSupportActionBar().hide();
        setContentView(R.layout.activity_home);
       toolbar = (Toolbar) findViewById(R.id.toolbar);

       db=new SQLiteHandler(getApplicationContext());
       session=new SessionManager(getApplicationContext());
        setSupportActionBar(toolbar);
        BottomNavigationView bm = (BottomNavigationView) findViewById(R.id.bottom_nev);
        bm.setOnNavigationItemSelectedListener
                (new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        //Fragment selectedFragment = null;
                        // FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                        switch (item.getItemId()) {
                            case R.id.Home:

                                getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout, new home1()).commit();

                                break;
                            case R.id.Ask:

                                getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout, new ask()).commit();

                                break;
                            case R.id.Messages:

                                getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout, new messages()).commit();

                                break;
                            case R.id.Prod_desc:
                                getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout, new product_desc()).commit();

                        }

                        return true;
                    }
                });
        getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout, new home1()).commit();
        ;
//
//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();


        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View header=navigationView.getHeaderView(0);
         navheadername=header.findViewById(R.id.drawer_layout_name);
         navheader_email=header.findViewById(R.id.drawer_layout_email);


//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.main, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }
//
//        return super.onOptionsItemSelected(item);



        // session manager
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

        // Logout button click event
//        btnLogout.setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View v) {
//                logoutUser();
//            }
//        });
//    }

        /**
         * Logging out the user. Will set isLoggedIn flag to false in shared
         * preferences Clears the user data from sqlite users table
         * */
    }

//    @SuppressWarnings("StatementWithEmptyBody")
//    @Override
//    public boolean onNavigationItemSelected (MenuItem item){
//        // Handle navigation view item clicks here.
//        int id = item.getItemId();
//
//        if (id == R.id.nav_camera) {
//            // Handle the camera action
//        } else if (id == R.id.nav_gallery) {
//
//        } else if (id == R.id.nav_slideshow) {
//
//        } else if (id == R.id.nav_manage) {
//
//        } else if (id == R.id.nav_share) {
//
//        } else if (id == R.id.nav_send) {
//
//        }
//
//        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
//        drawer.closeDrawer(GravityCompat.START);
//        return true;
//    }
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.Home:

                getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout, new home1()).commit();
                ;
                break;
            case R.id.Ask:

                getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout, new ask()).commit();
                ;
                break;
            case R.id.Messages:

                getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout, new messages()).commit();
                ;
                break;
            case R.id.Prod_desc:
                getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout, new product_desc()).commit();
            case R.id.nav_camera:
                    logoutUser();

        }

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
}







