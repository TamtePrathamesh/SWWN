package com.handsintech.coder.e_astro;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

public class fragment_HOME extends Fragment {


    private SectionsPagerAdapter mSectionsPagerAdapter;

    private ViewPager mViewPager;
    View v;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        v= inflater.inflate(R.layout.fragment_home, container, false);

        mSectionsPagerAdapter = new SectionsPagerAdapter(getChildFragmentManager());
        mViewPager = (ViewPager)v. findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout)v. findViewById(R.id.tabs);

        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mViewPager));
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                mViewPager.getAdapter().notifyDataSetChanged();
            }

            @Override
            public void onPageSelected(int position) {
                mViewPager.getAdapter().notifyDataSetChanged();



            }

            @Override
            public void onPageScrollStateChanged(int state) {
                mViewPager.getAdapter().notifyDataSetChanged();
            }
        });

        return v;
    }

    public class SectionsPagerAdapter extends FragmentStatePagerAdapter{

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {

            switch (position) {
                case 0:
                    fragment_brand fb = new fragment_brand();
                    return fb;
                case 1:
                    product_details pd = new product_details();
                    return pd;
            }
            return null;

        }


        @Override
        public int getCount() {
            // Show 3 total pages.
            return 2;

        }


        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {

                case 0:

                    return "By Brands";
                case 1:
                    return "By Products";

            }
            return null;
        }
    }

}