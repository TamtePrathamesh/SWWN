package com.handsintech.coder.e_astro.Activites;



import android.support.v4.app.FragmentManager;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.handsintech.coder.e_astro.EveryTimeRequied;
import com.handsintech.coder.e_astro.R;
import com.handsintech.coder.e_astro.SQLiteHandler;
import com.handsintech.coder.e_astro.SessionManager;
import com.handsintech.coder.e_astro.SharedPref;
import com.handsintech.coder.e_astro.fragment_HOME;
import com.handsintech.coder.e_astro.fragment_brand;
import com.handsintech.coder.e_astro.fragment_new_Home;

import java.util.HashMap;

public class Home extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, BottomNavigationView.OnNavigationItemSelectedListener,FragmentManager.OnBackStackChangedListener {
    SQLiteHandler db;
    Toolbar toolbar;
    SessionManager session;
    DrawerLayout dl;
    TextView navheadername,navheader_email;
    BottomNavigationView bm;
    Snackbar snackbar;
    String s,temp;
   public static DrawerLayout drawer;
    ActionBarDrawerToggle toggle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // getSupportActionBar().hide();
        setContentView(R.layout.activity_home);
        toolbar = (Toolbar) findViewById(R.id.toolbar);



        getSupportFragmentManager().addOnBackStackChangedListener(this);

        db = new SQLiteHandler(getApplicationContext());
        dl=findViewById(R.id.drawer_layout);//using this ID for displaying SnackBar
        session = new SessionManager(getApplicationContext());
        setSupportActionBar(toolbar);
//       getActionBar().setDisplayHomeAsUpEnabled(true);
        bm = (BottomNavigationView) findViewById(R.id.navigation);
        BottomNavigationViewHelper.removeShiftMode(bm);
        bm.setOnNavigationItemSelectedListener
                (new BottomNavigationView.OnNavigationItemSelectedListener() {

                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                        clearBackStack();

                        switch (item.getItemId()) {

                            case R.id.navigation_home:
                               // clearBackStack();

                              getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout, new fragment_HOME()).commit();
                               // viewFragment(new fragment_HOME(), "FRAGMENT_HO ME");

                                break;
                            case R.id.navigation_ask:
                               // clearBackStack();
                                getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout, new fragment_new_Home()).commit();
                               //   // viewFragment(new fragment_new_Home(), "FRAGMENT_OTHER");

                                break;
                            case R.id.navigation_messages:
                               // clearBackStack();

                              getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout, new fragment_new_Home()).commit();
                                //viewFragment(new fragment_new_Home(), "FRAGMENT_OTHER");
                                break;
                            case R.id.navigation_product_details:
                               // clearBackStack();
                             getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout, new fragment_brand()).commit();
                               // viewFragment(new fragment_brand(), "FRAGMENT_OTHER");
                        }


                        return true;
                    }
                });
        getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout, new fragment_HOME()).commit();

       drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
       toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();


        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View header = navigationView.getHeaderView(0);
        navheadername = header.findViewById(R.id.drawer_layout_name);
        navheader_email = header.findViewById(R.id.drawer_layout_email);
//        drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);


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
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(getSupportFragmentManager().getBackStackEntryCount() > 0){
                    getSupportFragmentManager().popBackStack();
                }else {
                    drawer.openDrawer(GravityCompat.START);
                }
            }
        });

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
        }
            super.onBackPressed();


    }

    private void logoutUser() {
        session.setLogin(false);

        db.deleteUsers();



        // Launching the login activity
        Intent intent = new Intent(Home.this, LoginActivity.class);
        if(SharedPref.getInstance(Home.this).IsUserRegiserted()){
            SharedPref.getInstance(Home.this).UserRegiserted(false);
            intent.putExtra("check","user");}
        else if(SharedPref.getInstance(Home.this).IsexpertRegiserted()){
            SharedPref.getInstance(Home.this).ExpertRegiserted(false);
            intent.putExtra("check", "expert");

        }
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


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case android.R.id.home:
                // todo: goto back activity from here
                Log.i("BackButton", "Pressed");
                // getFragmentManager().beginTransaction().replace(R.id.content,home).commit();
               //Going To previous Fragment
                onBackPressed();



                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackStackChanged() {
        if(getSupportFragmentManager().getBackStackEntryCount() > 0){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }else {
            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
            toggle.syncState();
        }
    }
    private void clearBackStack() {
        final FragmentManager fragmentManager = getSupportFragmentManager();
        while (fragmentManager.getBackStackEntryCount()!=0) {
            fragmentManager.popBackStackImmediate();
        }
    }
//
// public void clearBackstack() {
//
//   FragmentManager.BackStackEntry entry = getSupportFragmentManager().getBackStackEntryAt(
//            0);
//    getSupportFragmentManager().popBackStack(entry.getId(),
//            FragmentManager.POP_BACK_STACK_INCLUSIVE);
//    getSupportFragmentManager().executePendingTransactions();
//
//}

}







