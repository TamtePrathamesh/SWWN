package com.handsintech.coder.e_astro.Activites;

import android.app.ProgressDialog;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.handsintech.coder.e_astro.AppController;
import com.handsintech.coder.e_astro.R;
import com.handsintech.coder.e_astro.SQLiteHandler;
import com.handsintech.coder.e_astro.SessionManager;
import com.handsintech.coder.e_astro.SharedPref;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {

    Button sellerbtn,userbtn,signin;
    EditText emailField,passwordField;
    TextView forgot,newuser;
    private static final String TAG = MainActivity.class.getSimpleName();
    String URL_LOGIN = "http://handsinservices.com/teachingApp/Api/userLogin.php";
    public String email,password;
    private SessionManager session;
    private SQLiteHandler db;
    public static Animation shakeAnimation;
    ConstraintLayout cl;
    ProgressDialog pDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        sellerbtn = findViewById(R.id.sellerbtnid);
        userbtn = findViewById(R.id.userbtnid);
        signin = findViewById(R.id.loginbtnid);
        forgot = findViewById(R.id.forgotid);
        newuser = findViewById(R.id.newuserid);
        emailField = findViewById(R.id.emailid1);
        passwordField = findViewById(R.id.passid1);
        userbtn.setActivated(true);
        pDialog = new ProgressDialog(this);
        cl=new ConstraintLayout(getApplicationContext());
        pDialog.setCancelable(false);
        db = new SQLiteHandler(getApplicationContext());
        session = new SessionManager(getApplicationContext());
        shakeAnimation = AnimationUtils.loadAnimation(getApplication(),R.anim.shake);
        newuser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent SignIn = new Intent(LoginActivity.this,MainActivity.class);
                startActivity(SignIn);
            }
        });
        sellerbtn.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                sellerbtn.setActivated(true);
                userbtn.setActivated(false);
                return true;
            }
        });
        userbtn.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                userbtn.setActivated(true);
                sellerbtn.setActivated(false);
                return true;
            }
        });

        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                email = emailField.getText().toString().trim();
                password =passwordField.getText().toString().trim();

                // Check for empty data in the form
                if (!email.isEmpty() && !password.isEmpty()) {
                    // login user
                    if(userbtn.isActivated()) {
                        Toast.makeText(getApplicationContext(),"user selected",Toast.LENGTH_SHORT).show();
                        URL_LOGIN = "http://handsinservices.com/teachingApp/Api/userLogin.php";
                        checkLogin(email, password);
                        SharedPref.getInstance(LoginActivity.this).UserRegiserted(true);
                        SharedPref.getInstance(LoginActivity.this).ExpertRegiserted(false);

                    }
                    else if(sellerbtn.isActivated()) {
                        URL_LOGIN = "http://handsinservices.com/teachingApp/Api/sellerLogin.php";
                        checkLogin(email,password);
                        SharedPref.getInstance(LoginActivity.this).ExpertRegiserted(true);
                        SharedPref.getInstance(LoginActivity.this).UserRegiserted(false);
                    }
                    else {
                        Toast.makeText(getApplicationContext(), "Please select login type.", Toast.LENGTH_LONG).show();

                    }

                } else {
                    // Prompt user to enter credentials
                    cl.startAnimation(shakeAnimation);
                    Toast.makeText(getApplicationContext(),
                            "Please enter the credentials!", Toast.LENGTH_LONG)
                            .show();
                }

            }

        });
    }
    private void checkLogin(final String email, final String password) {
        // Tag used to cancel the request
        String tag_string_req = "req_login";

        pDialog.setMessage("Logging in ...");
        showDialog();


        StringRequest strReq = new StringRequest(Request.Method.POST,
                URL_LOGIN, new Response.Listener<String>() {
            @Override
            public void onResponse(String response1) {
                JSONObject response= null;
                try {
                    response = new JSONObject(response1);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                try {

                    Log.d(TAG, "Login Response: " + response.getString("success"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                hideDialog();

                try {


                    // Check for error node in json
                    String success=response.getString("success");
                    String name=response.getString("name");
                    String uid=response.getString("uid");

                    if (!success.equalsIgnoreCase("false")) {
                        // user successfully logged in
                        // Create login session
                        session.setLogin(true);

                        // Now store the user in SQLite
                        // Inserting row in users table
                        db.addUser(uid,name,email);
                        Toast.makeText(getApplication().getBaseContext(),"login succesfull",Toast.LENGTH_LONG).show();
                        // Launch main activity
                        Intent intent = new Intent(LoginActivity.this,
                                Home.class);
                        startActivity(intent);


                    } else {
                        // Error in login. Get the error message
                        String errorMsg =  response.getString("message");
                        Toast.makeText(getApplicationContext(),
                                errorMsg, Toast.LENGTH_LONG).show();
                        //Toast.makeText(LoginActivity.this,"Email:"+email+" password:"+password+"",Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    // JSON error
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), "Json error: " + e.getMessage(), Toast.LENGTH_LONG).show();
                }
            }

        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Login Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(),
                        error.getMessage(), Toast.LENGTH_LONG).show();
                hideDialog();
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting parameters to login url
                Map<String, String> params = new HashMap<String, String>();

                params.put("email", email);
                params.put("passwd", password);

                return params;
            }

        };

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
    }
    private void showDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hideDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }


}
