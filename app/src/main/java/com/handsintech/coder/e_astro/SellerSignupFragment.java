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
import android.widget.AdapterView;
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
import com.hbb20.CountryCodePicker;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class SellerSignupFragment extends Fragment implements AdapterView.OnItemSelectedListener {
    View view;
    private EditText first,last,email,passwd,brand;
    TextView registered;
    Button register;
    CheckBox checkBox;
    ProgressDialog pDialog;
    String URL_REGISTER="http://handsinservices.com/teachingApp/Api/sellerRegistration.php";
  /*  private Spinner spinner;
    private static final String[]brands = {"Choose Brand","Olay", "Artistry", "L'Oreal","Garnier","Lorem Ipsum"};*/
    private CountryCodePicker ccp;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public static SellerSignupFragment newInstance(){
        SellerSignupFragment sellerfragment = new SellerSignupFragment();
        return  sellerfragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.d("Second","..");
        view = inflater.inflate(R.layout.activity_seller_signup_fragment,container, false);
        first = view.findViewById(R.id.firstid);
        last = view.findViewById(R.id.lastid);
        email = view.findViewById(R.id.emailid);
        passwd = view.findViewById(R.id.passid);
        brand = view.findViewById(R.id.brandid);
        register=view.findViewById(R.id.registerbtnid);
        checkBox=view.findViewById(R.id.checkbox);
        pDialog = new ProgressDialog(getContext());
        pDialog.setCancelable(false);
       /* spinner = (Spinner)view.findViewById(R.id.brandid);*/
        registered = view.findViewById(R.id.textview);
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
                String firstname = first.getText().toString().trim();
                String lastname=last.getText().toString();
                String selleremail = email.getText().toString().trim();
                String password = passwd.getText().toString().trim();
                String sellerbrand=brand.getText().toString();



                if (!firstname.isEmpty() && !selleremail.isEmpty() && !password.isEmpty()&& Patterns.EMAIL_ADDRESS.matcher(selleremail).matches()) {
                    if(checkBox.isChecked())
                        registerSeller(firstname,lastname,selleremail, password,sellerbrand);
                    else checkBox.setError("Plese agree the terms and conditions.");
                }
                else if(!Patterns.EMAIL_ADDRESS.matcher(selleremail).matches())
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
/*
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_spinner_item,brands);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);
*/
        return view;
    }
    private void registerSeller(final String name,final String last, final String email,
                              final String password,final String brand) {
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
                params.put("fname", name);
                params.put("lname",last);
                params.put("email", email);
                params.put("passwd", password);
                params.put("product",brand);


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
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        switch (position) {
            case 0:
                // Whatever you want to happen when the first item gets selected
                break;
            case 1:
                // Whatever you want to happen when the second item gets selected
                break;
            case 2:
                // Whatever you want to happen when the thrid item gets selected
                break;

        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }


}
