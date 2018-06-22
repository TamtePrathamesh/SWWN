package com.handsintech.coder.e_astro;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
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
        BottomNavigationView bm = (BottomNavigationView) findViewById(R.id.navigation);
        bm.setOnNavigationItemSelectedListener
                (new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                        switch (item.getItemId()) {
                            case R.id.navigation_home:
                                FragmentManager fragmentManager = getSupportFragmentManager();
                                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                                fragmentTransaction.replace(R.id.frame_layout, new fragment_new_Home());
                                fragmentTransaction.addToBackStack(null);
                                fragmentTransaction.commit();
                               // getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout, new fragment_new_Home()).commit();

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
        ;


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
}







