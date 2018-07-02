package com.handsintech.coder.e_astro.Activites;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
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
import com.hbb20.CountryCodePicker;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import es.dmoral.toasty.Toasty;

public class RegisterActivity extends AppCompatActivity {

    MainActivity ma=new MainActivity();
    public TextView tvstatusReg;
    RadioButton rb1,rb2;

    private static final String TAG = RegisterActivity.class.getSimpleName();
    private Button btnRegister;
    private Button btnLinkToLogin;
    private EditText inputFullName;
    private EditText inputEmail;
    private EditText inputPassword,editTextphone;
    private ProgressDialog pDialog;
    private SessionManager session;
    private SQLiteHandler db;
    CountryCodePicker cpp;
    public String msg;
    String s,check;
    public static String URL_REGISTER = "http://handintech.000webhostapp.com/NEW_HIT/register.php?check=";
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        tvstatusReg=findViewById(R.id.textViewStatusRegister);

        s=getIntent().getStringExtra("check");
        if(s.equals("user")){
            tvstatusReg.setVisibility(View.VISIBLE);
            tvstatusReg.setText("User Signup");

        }
        else if(s.equals("expert")){
            tvstatusReg.setVisibility(View.VISIBLE);
            tvstatusReg.setText("Expert Signup");

        }

        rb1=findViewById(R.id.Male);
        rb2=findViewById(R.id.Female);


        cpp=findViewById(R.id.ccp);
        inputFullName = (EditText) findViewById(R.id.inputname);
        inputEmail = (EditText) findViewById(R.id.inputemail);
        inputPassword = (EditText) findViewById(R.id.inputpass);
        btnRegister = (Button) findViewById(R.id.btnRegister);
        btnLinkToLogin = (Button) findViewById(R.id.btnLinkToLoginScreen);
        editTextphone=findViewById(R.id.inputphone);




        // Progress dialog
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);

        // Session manager
        session = new SessionManager(getApplicationContext());

        // SQLite database handler
        db = new SQLiteHandler(getApplicationContext());

        // Check if user is already logged in or not
        if (session.isLoggedIn()) {
            // User is already logged in. Take him to main activity
            Intent intent = new Intent(RegisterActivity.this,
                    MainActivity.class);
            startActivity(intent);
            finish();
        }

        // Register Button Click event
        btnRegister.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                String gender="";
                if(rb1.isChecked())
                    gender="Male";
                else
                    gender="Female";

                String name = inputFullName.getText().toString().trim();
                String email = inputEmail.getText().toString().trim();
                String password = inputPassword.getText().toString().trim();
                String country=cpp.getSelectedCountryName().toString();
                String phoneno=editTextphone.getText().toString();


                if (!name.isEmpty() && !email.isEmpty() && !password.isEmpty()&&!phoneno.isEmpty()&&Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    registerUser(name, email, password,country,phoneno,gender);
                }
                else if(!Patterns.EMAIL_ADDRESS.matcher(email).matches())
                {
                    inputEmail.setError("enter valid email");
                }
                else if(phoneno.length()<10){editTextphone.setError("enter 10 digit number");}

                else {
                    Toasty.error(getApplicationContext(),
                            "Please enter all fields!", Toast.LENGTH_LONG)
                            .show();
                }
            }
        });

        // Link to Login Screen
        btnLinkToLogin.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                Intent i = new Intent(RegisterActivity.this,
                        LoginActivity.class);
                overridePendingTransition(R.anim.leftenter, R.anim.rightexit);

                if(s.equals("user"))
                    i.putExtra("check","user");
                else
                    i.putExtra("check","expert");

                    startActivity(i);
                }





        });


    }

    /**
     * Function to store user in MySQL database will post params(tag, name,
     * email, password) to register url
     * */


    private void registerUser(final String name, final String email,
                              final String password,final String country,final String phoneno,final String gender) {
        // Tag used to cancel the request
        String tag_string_req = "req_register";

        pDialog.setMessage("Registering ...");
        showDialog();

        StringRequest strReq = new StringRequest(Request.Method.POST,
                URL_REGISTER+s, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(TAG, "Register Response: " + response.toString());
                hideDialog();

                try {
                    JSONObject jObj = new JSONObject(response);
                    boolean error = jObj.getBoolean("error");
                    if (!error) {
                        // User successfully stored in MySQL
                        // Now store the user in sqlite
                        String uid = jObj.getString("uid");

                        JSONObject user = jObj.getJSONObject("user");
                        String name = user.getString("name");
                        String email = user.getString("email");

                        String created_at = user
                                .getString("created_at");


                        // Inserting row in users table
                        db.addUser(name, email, uid, created_at);

                        Toasty.success(RegisterActivity.this, "User successfully registered. Try login now!", Toast.LENGTH_LONG).show();

                        if(s.equals("expert"))
                        {
                            SharedPref.getInstance(RegisterActivity.this).ExpertRegiserted(true);
                        }
                        else if(s.equals("user"))
                        {
                            SharedPref.getInstance(RegisterActivity.this).UserRegiserted(true);
                        }

                        // Launch login activity
                    Intent i=new Intent(RegisterActivity.this,LoginActivity.class);
                    if(s.equals("user"))
                    i.putExtra("check","user");
                    else if(s.equals("expert"))
                        i.putExtra("check","expert");

                    } else {

                        // Error occurred in registration. Get the error
                        // message
                        String errorMsg = jObj.getString("error_msg");
                        Toast.makeText(getApplicationContext(),
                                errorMsg, Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Registration Error: " + error.getMessage());
                Toasty.error(RegisterActivity.this,"check your network connection",
                         Toast.LENGTH_LONG).show();
                hideDialog();
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting params to register url
                Map<String, String> params = new HashMap<String, String>();
                params.put("name", name);
                params.put("email", email);
                params.put("password", password);

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
    @Override
    public void startActivityForResult(Intent intent, int requestCode) {
        super.startActivityForResult(intent, requestCode);
        overridePendingTransition(R.anim.leftenter, R.anim.rightexit);

    }
}