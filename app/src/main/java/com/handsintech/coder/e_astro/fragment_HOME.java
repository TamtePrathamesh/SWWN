package com.handsintech.coder.e_astro;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class fragment_HOME extends Fragment {


    private SectionsPagerAdapter mSectionsPagerAdapter;

    private ViewPager mViewPager;
    View v;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        v= inflater.inflate(R.layout.fragment_home, container, false);

        mSectionsPagerAdapter = new SectionsPagerAdapter(getChildFragmentManager());
        mViewPager = (ViewPager)v. findViewById(R.id.container1);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout)v. findViewById(R.id.tabs);

        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mViewPager));
//        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
//            @Override
//            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
//
//            }
//
//            @Override
//            public void onPageSelected(int position) {
//
//
//
//
//            }
//
//            @Override
//            public void onPageScrollStateChanged(int state) {
//
//            }
//        });


        return v;
    }

    public class SectionsPagerAdapter extends FragmentPagerAdapter {

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
                    fragment_new_Home pd = new fragment_new_Home();
                    return pd;
            }
            return null;

        }
//        @Override
//        public int getItemPosition(Object o)
//        {
//
//            return POSITION_NONE;
//        }


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