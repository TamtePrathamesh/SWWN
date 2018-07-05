package com.handsintech.coder.e_astro.tab_by_brands;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.handsintech.coder.e_astro.CustomVolleyRequest;
import com.handsintech.coder.e_astro.R;
import com.handsintech.coder.e_astro.SliderUtils;
import com.handsintech.coder.e_astro.ViewPagerAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class fragment_product_details extends Fragment{


    public fragment_product_details() {
    }
    private TextView product_detail_name_txtview,product_details_txtview,product_detail_price_textview;
    ViewPager mViewPager;
    LinearLayout sliderDotspanel;
    private int dotscount;
    private ImageView[] dots;
    List<SliderUtils> sliderImg;
    ViewPagerAdapter viewPagerAdapter;

    private String request_url="http://handintech.000webhostapp.com/NEW_HIT/brand_product_details.php?product_name=";
    ProgressBar pb;
    String urls;
    String pro_detail_name="";
    View v;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       v = inflater.inflate(R.layout.fragment_product_detail_layout, container, false);

        pro_detail_name= this.getArguments().getString("57");//get your parameters


        product_detail_name_txtview=v.findViewById(R.id.product_detail_name);
       product_details_txtview=v.findViewById(R.id.product_details);
       product_detail_price_textview=v.findViewById(R.id.product_detail_price);
        mViewPager=v.findViewById(R.id.product_detail_viewPager);
        sliderImg = new ArrayList<>();
        pb=v.findViewById(R.id.product_detail_pb);

        sliderDotspanel = (LinearLayout)v.findViewById(R.id.SliderDots);


        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

                for(int i = 0; i< dotscount; i++){
                    dots[i].setImageDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.dot_not_selected));
                }

                dots[position].setImageDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.dot_blue_over));

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    load_product_details();
    //geturlss();

        return v;

    }
    private void load_product_details() {


        StringRequest stringRequest = new StringRequest(Request.Method.GET, request_url+pro_detail_name,
                new Response.Listener<String>() {
                    @Override


                    public void onResponse(String response) {
                        try {
                            //converting the string to json array object
                            JSONArray array = new JSONArray(response);
                            if (array.isNull(0)) {
                                pb.setVisibility(View.GONE);
                                Toast.makeText(getContext(), "No More Items Available", Toast.LENGTH_SHORT).show();
                            } else {

                                //traversing through all the object
                                for (int i = 0; i < array.length(); i++) {

                                    //getting product object from json array
                                    JSONObject product = array.getJSONObject(i);


                                        pb.setVisibility(View.GONE);

                                    //adding the product to product list
                                  urls=product.getString("brand_product_detail_logo");
                                  String tempname=product.getString("brand_product_detail_name");
                                  String tempdes=product.getString("brand_product_detail_des");
                                  String tempprice=product.getString("brand_product_detail_price");
                                  product_detail_name_txtview.setText(tempname);
                                  product_details_txtview.setText(tempdes);
                                  product_detail_price_textview.setText("Price"+" "+tempprice);


//

                                }
                                List<String> extractedUrls = extractUrls(urls);
                                for (String url : extractedUrls)
                                {

                                    SliderUtils sliderUtils = new SliderUtils();
                                    sliderUtils.setSliderImageUrl(url);
                                    sliderImg.add(sliderUtils);
                                }
                                viewPagerAdapter = new ViewPagerAdapter(sliderImg, getActivity());

                                mViewPager.setAdapter(viewPagerAdapter);

                                dotscount = viewPagerAdapter.getCount();
                                dots = new ImageView[dotscount];

                                for(int i = 0; i < dotscount; i++){

                                    dots[i] = new ImageView(getActivity());
                                    dots[i].setImageDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.dot));

                                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);

                                    params.setMargins(8, 0, 8, 0);

                                    sliderDotspanel.addView(dots[i], params);

                                }

                                dots[0].setImageDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.dot));
                            }


                        } catch(JSONException e){
                            pb.setVisibility(View.GONE);
                            //Toast.makeText(getContext(), error.toString(), Toast.LENGTH_SHORT).show();
                        }

                    }

                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        pb.setVisibility(View.GONE);
                        Toast.makeText(getContext(), "Request Timeout, Please try again.", Toast.LENGTH_SHORT).show();

                    }
                });

        //adding our stringrequest to queue
//        Volley.newRequestQueue(getActivity()).add(stringRequest);
        CustomVolleyRequest.getInstance(getActivity()).addToRequestQueue(stringRequest);
    }
//   public void geturlss()
//   {
//
//
//       String url = urls;
//       String done = " ";
//       String[] hope = url.split("/ ");
//
//       for ( int i = 0; i < hope.length; i++)
//       {
//           done = done + hope[i];
//       }
//
//       Log.d("url", done);
//   }
    public static List<String> extractUrls(String text)
    {
        List<String> containedUrls = new ArrayList<String>();
        String urlRegex = "((https?|ftp|gopher|telnet|file):((//)|(\\\\))+[\\w\\d:#@%/;$()~_?\\+-=\\\\\\.&]*)";
        Pattern pattern = Pattern.compile(urlRegex, Pattern.CASE_INSENSITIVE);
        Matcher urlMatcher = pattern.matcher(text);

        while (urlMatcher.find())
        {
            containedUrls.add(text.substring(urlMatcher.start(0),
                    urlMatcher.end(0)));
        }

        return containedUrls;
    }
}
