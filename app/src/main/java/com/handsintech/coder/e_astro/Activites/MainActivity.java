package com.handsintech.coder.e_astro.Activites;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.handsintech.coder.e_astro.R;
import com.handsintech.coder.e_astro.SQLiteHandler;
import com.handsintech.coder.e_astro.SellerSignupFragment;
import com.handsintech.coder.e_astro.SessionManager;
import com.handsintech.coder.e_astro.UserSignUpFragmenrt;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    Button user,seller;
    TextView registered;
    EditText input1,input2,input3,input4,input5;
    SessionManager session;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        UserSignUpFragmenrt userfragment = new UserSignUpFragmenrt();
        loadFragment(userfragment);
        user=findViewById(R.id.userbtnid);
        seller=findViewById(R.id.sellerbtnid);
        session=new SessionManager(getApplicationContext());
        user.setActivated(true); //default screen user
        if (session.isLoggedIn()) {
            // User is already logged in. Take him to main activity
            Intent intent = new Intent(MainActivity.this, Home.class);
            startActivity(intent);
            finish();
        }
        user.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
               /* user.setPressed(true);
                seller.setPressed(false);
               */
                user.setActivated(true);
                seller.setActivated(false);
                UserSignUpFragmenrt userfragment = new UserSignUpFragmenrt();
                loadFragment(userfragment);
                return  true;
            }
        });
        seller.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
             /*   seller.setPressed(true);
                user.setPressed(false);*/
                seller.setActivated(true);
                user.setActivated(false);
                SellerSignupFragment seller = new SellerSignupFragment();
                loadFragment(seller);
                return true;
            }
        });

        /*final CountryPickerDialog countryPicker =
                new CountryPickerDialog(getApplicationContext() , new CountryPickerCallbacks() {
                    @Override
                    public void onCountrySelected(Country country, int flagResId) {

                    }
                });
        countryPicker.show();
*/
    }
    private boolean loadFragment(Fragment fragment) {
        //switching fragment
        if (fragment != null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.new_frame, fragment)
                    .addToBackStack(fragment.toString())
                    .commit();
            return true;
        }
        return false;
    }

    @Override
    public void onClick(View view) {

    }


}
