package com.handsintech.coder.e_astro;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.handsintech.coder.e_astro.Activites.LoginActivity;
import com.handsintech.coder.e_astro.Activites.MainActivity;
import com.hbb20.CountryCodePicker;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class UserSignUpFragmenrt extends Fragment {
    View view;
    private EditText first,email,passwd,address;
    TextView registered;
    Button register;

    CheckBox checkBox;
    ProgressDialog pDialog;
    CountryCodePicker ccp;
    public String country;
    String URL_REGISTER="http://handsinservices.com/teachingApp/Api/userRegistration.php";
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
      }
    public static UserSignUpFragmenrt newInstance(){
        UserSignUpFragmenrt userfragment = new UserSignUpFragmenrt();
        return  userfragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
        Log.d("Second","..");

        view = inflater.inflate(R.layout.activity_user_sign_up_fragmenrt,container, false);
        first = view.findViewById(R.id.firstid);
        email = view.findViewById(R.id.emailid);
        passwd = view.findViewById(R.id.passid);
        address = view.findViewById(R.id.addressid);
        registered = view.findViewById(R.id.textview);
        checkBox=view.findViewById(R.id.checkbox);
        register=view.findViewById(R.id.registerbtnid);
        pDialog = new ProgressDialog(getContext());
        ccp=view.findViewById(R.id.countryid);
        pDialog.setCancelable(false);
        registered.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent Login = new Intent(getActivity(),LoginActivity.class);
                startActivity(Login);
            }
        });
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = first.getText().toString().trim();
                String useremail = email.getText().toString().trim();
                String password = passwd.getText().toString().trim();
                String useraddress=address.getText().toString();
                country= ccp.getSelectedCountryName();


                if (!username.isEmpty() && !username.isEmpty() && !password.isEmpty()&& Patterns.EMAIL_ADDRESS.matcher(useremail).matches()) {
                    if(checkBox.isChecked())
                        registerUser(username,useremail, password,useraddress,country);
                    else checkBox.setError("Plese agree the terms and conditions.");
                }
                else if(!Patterns.EMAIL_ADDRESS.matcher(useremail).matches())
                {
                    email.setError("enter valid email");
                }
                else {
                    Toast.makeText(getContext(),
                            "Please enter all fields!", Toast.LENGTH_LONG)
                            .show();
                }
            }
        });

        return  view;
    }
    private void registerUser(final String name, final String email,
                              final String password,final String country,final String address) {
        // Tag used to cancel the request
        String tag_string_req = "req_register";

        pDialog.setMessage("Registering ...");
        showDialog();

        StringRequest strReq = new StringRequest(Request.Method.POST,
                URL_REGISTER, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d("response recieved:", "Register Response: " + response.toString());
                hideDialog();

                try {
                    JSONObject jObj = new JSONObject(response);
                    String success = jObj.getString("success");
                    if (!success.equalsIgnoreCase("false")) {

                        // Inserting row in users table

                        Toast.makeText(getContext(), "User successfully registered. Try login now!", Toast.LENGTH_LONG).show();

                        // Launch login activity
                        Intent i=new Intent(getContext(),LoginActivity.class);

                        startActivity(i);

                    } else {

                        // Error occurred in registration. Get the error
                        // message
                        String errorMsg = jObj.getString("message");
                        Toast.makeText(getContext(),
                                errorMsg, Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("error response", "Registration Error: " + error.getMessage());
                Toast.makeText(getContext(),"check your network connection",
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
                params.put("passwd", password);
                params.put("address", String.valueOf(address));
                params.put("country",country);

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
