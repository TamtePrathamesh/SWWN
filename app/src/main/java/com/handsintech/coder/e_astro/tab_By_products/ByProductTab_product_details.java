package com.handsintech.coder.e_astro.tab_By_products;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
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

/**
 * A simple {@link Fragment} subclass.
 */
public class ByProductTab_product_details extends Fragment {

    private TextView product_detail_name_txtview,product_details_txtview;
    ViewPager mViewPager;
    LinearLayout sliderDotspanel;
    private int dotscount;
    private ImageView[] dots;
    List<SliderUtils> sliderImg;
    ViewPagerAdapter viewPagerAdapter;

    private String request_url="https://socialworldwidenetwork.com/productview.php?product_id=";
    ProgressBar pb;
    String urls;
    String pro_detail_id="";
    public Button btn_askExpert;

    public ByProductTab_product_details() {
        // Required empty public constructor
    }
View v;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
         v= inflater.inflate(R.layout.fragment_by_product_tab_product_details, container, false);

        pro_detail_id= this.getArguments().getString("key_id");//get your parameters

        btn_askExpert=v.findViewById(R.id.buttonExpertlogin);


        product_detail_name_txtview=v.findViewById(R.id.by_protab_product_detail_name);
        product_details_txtview=v.findViewById(R.id.by_protab_product_details);

        mViewPager=v.findViewById(R.id.by_protab_product_detail_viewPager);
        sliderImg = new ArrayList<>();
        pb=v.findViewById(R.id.by_protab_product_detail_pb);

        sliderDotspanel = (LinearLayout)v.findViewById(R.id.by_productTab_SliderDots);


        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

                for(int i = 0; i< dotscount; i++){
                    dots[i].setImageDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.dot));
                }

                dots[position].setImageDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.dot_over));

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        by_product_tab_load_product_details();

        btn_askExpert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        return v;
    }

    private void by_product_tab_load_product_details() {


        JsonObjectRequest stringRequest = new JsonObjectRequest(Request.Method.GET, request_url+pro_detail_id,null,
                new Response.Listener<JSONObject>() {
                    @Override


                    public void onResponse(JSONObject response) {
                        try {
                            //converting the string to json array object
                            JSONObject array = response.getJSONObject("message");


                                //traversing through all the object
//                                for (int i = 0; i < array.length(); i++) {

                                    //getting product object from json array
                                   // JSONObject product = array.getJSONObject(i);


                                    pb.setVisibility(View.GONE);

                                    //adding the product to product list
                                    urls=array.getString("small_image");
                                    String tempname=array.getString("name");
                                    String tempdes=array.getString("description");

                                    product_detail_name_txtview.setText(tempname);
                                    product_details_txtview.setText(tempdes);



//

                           //     }
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



                        } catch(JSONException e){
                            pb.setVisibility(View.GONE);
                            //Toast.makeText(getContext(), error.toString(), Toast.LENGTH_SHORT).show();
                            Log.d("details",e.toString());
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

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
//        MenuItem item = menu.findItem(R.id.search_menu);
//        MenuItem i=menu.findItem(R.id.action_settings);
////        i.setVisible(false);
//        item.setVisible(false);
        menu.clear();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

}
