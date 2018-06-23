package com.handsintech.coder.e_astro;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class fragment_new_Home extends Fragment {

    Button btnByBrand,btnByProduct;

    ProductsAdapter adapter;
    //a list to store all the products
    List<Product> productList;

    ProgressBar pb;
    //the recyclerview
    RecyclerView recyclerView;
    String request_url = "http://handintech.000webhostapp.com/NEW_HIT/products.php";




    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_new_home, container, false);




        recyclerView = v.findViewById(R.id.recylcerView);

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        //initializing the productlist
        productList = new ArrayList<>();

   adapter = new ProductsAdapter(getActivity(), productList);
        recyclerView.setAdapter(adapter);

        pb=v.findViewById(R.id.pb);

            pb.setVisibility(View.VISIBLE);
        //this method will fetch and parse json
        //to display it in recyclerview
        loadProducts();




        return  v;
    }
    private void loadProducts() {


        StringRequest stringRequest = new StringRequest(Request.Method.GET, request_url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            //converting the string to json array object
                            JSONArray array = new JSONArray(response);

                            //traversing through all the object
                            for (int i = 0; i < array.length(); i++) {

                                //getting product object from json array
                                JSONObject product = array.getJSONObject(i);
                                pb.setVisibility(View.GONE);
                                //adding the product to product list
                                productList.add(new Product(



                                        product.getString("name"),
                                        product.getString("lastname"),
                                        product.getString("details"),
                                        product.getString("experience")

                                ));
                            }

                            //creating adapter object and setting it to recyclerview
//                            adapter = new ProductsAdapter(getActivity(), productList);
                           recyclerView.setAdapter(adapter);



                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });

        //adding our stringrequest to queue
        Volley.newRequestQueue(getActivity()).add(stringRequest);
    }
}

