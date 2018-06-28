package com.handsintech.coder.e_astro;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class product_details extends Fragment{


    public product_details() {
    }
    private TextView product_name,product_details;
    ViewPager mViewPager;
    LinearLayout sliderDotspanel;
    private int dotscount;
    private ImageView[] dots;
    List<SliderUtils> sliderImg;
    ViewPagerAdapter viewPagerAdapter;

    private String request_url="";


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_products_details, container, false);

        product_name=v.findViewById(R.id.product_name);
        product_details=v.findViewById(R.id.product_details);
        mViewPager=v.findViewById(R.id.viewPager);
        sliderImg = new ArrayList<>();

        sliderDotspanel = (LinearLayout)v.findViewById(R.id.SliderDots);


        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

                for(int i = 0; i< dotscount; i++){
                    dots[i].setImageDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.dot));
                }

                dots[position].setImageDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.dot_bg));

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

      //  loadProduct();

        return v;

    }
//    private void loadBrands() {
//
//
//        StringRequest stringRequest = new StringRequest(Request.Method.GET, request_url+page,
//                new Response.Listener<String>() {
//                    @Override
//
//
//                    public void onResponse(String response) {
//                        try {
//                            //converting the string to json array object
//                            JSONArray array = new JSONArray(response);
//                            if (array.isNull(0)) {
//                                pb1.setVisibility(View.GONE);
//                                Toast.makeText(getContext(), "No More Items Available", Toast.LENGTH_SHORT).show();
//                            } else {
//
//                                //traversing through all the object
//                                for (int i = 0; i < array.length(); i++) {
//
//                                    //getting product object from json array
//                                    JSONObject product = array.getJSONObject(i);
//                                    if (page==1){
//                                        pb.setVisibility(View.GONE);}
//                                    else {pb1.setVisibility(View.GONE);}
//                                    //adding the product to product list
//                                    brandList.add(new Brands(
//
//                                            product.getString("brand_name"),
//                                            product.getString("brand_des"),
//                                            product.getString("brand_logo")
//
//                                    ));
//
//                                }}
//                            //creating adapter object and setting it to recyclerview
////                            adapter = new ProductsAdapter(getActivity(), productList);
//                            // recyclerView.setAdapter(adapter);
//                            adapter.notifyDataSetChanged();
//                            // adapter.notifyItemRangeInserted((page-1)*10,productList.size());
//                            adapter.notifyItemChanged((page-1)*10);
//
//                            Log.d("count",String.valueOf((page-1)*10));
//
//
//                        } catch(JSONException e){
//                            pb.setVisibility(View.GONE);
//                            //Toast.makeText(getContext(), error.toString(), Toast.LENGTH_SHORT).show();
//                        }
//                    }
//
//                },
//                new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//                        pb1.setVisibility(View.GONE);
//                        Toast.makeText(getContext(), "Request Timeout, Please try again.", Toast.LENGTH_SHORT).show();
//
//                    }
//                });
//        adapter.notifyItemRangeInserted(page*10,brandList.size());
//        //adding our stringrequest to queue
//        Volley.newRequestQueue(getActivity()).add(stringRequest);
//    }
}
