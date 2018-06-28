package com.handsintech.coder.e_astro;


import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.futuremind.recyclerviewfastscroll.Utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class fragment_brand extends Fragment  implements SearchView.OnQueryTextListener{

    public FloatingActionButton fab;

    BrandAdapter adapter;

    List<Brands> brandList;
    View v;

    //the recyclerview
    RecyclerView recyclerView;
    String request_url = "http://handintech.000webhostapp.com/NEW_HIT/brands.php?start=";
    int page=0;

    public static interface ClickListener{
        public void onClick(View view,int position);
        public void onLongClick(View view,int position);
    }


    public ProgressBar pb,pb1;
    public fragment_brand() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_blank, container, false);


        pb=(ProgressBar)v.findViewById(R.id.brands_pb);
        pb1=(ProgressBar)v.findViewById(R.id.brands_pb1);

        recyclerView = v.findViewById(R.id.brands_recylcerView);

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new MyDividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL, 36));

        //initializing the productlist
        brandList = new ArrayList<>();
        // recyclerView.setOnScrollChangeListener(this);
        adapter = new BrandAdapter(getActivity(), brandList);
        recyclerView.setAdapter(adapter);

//        fab = (FloatingActionButton)v. findViewById(R.id.brands_fab);
//        fab.setVisibility(View.GONE);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });

//        if(SharedPref.getInstance(getActivity()).IsexpertRegiserted())
//        {
//            Log.d("cq","true");
////            fab.setVisibility(View.VISIBLE);
////
////            recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
////                @Override
////                public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
////
////                    if (newState == RecyclerView.SCROLL_STATE_IDLE){
////                        fab.show();
////                    }
////
////                    super.onScrollStateChanged(recyclerView, newState);
////                }
////
////                @Override
////                public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
////                    super.onScrolled(recyclerView, dx, dy);
////                    if (dy > 0 ||dy<0 && fab.isShown())
////                        fab.hide();
////                }
//
//            });
//        }else {
//            Log.d("cq","false");
//            fab.setVisibility(View.GONE);
//
//        }

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {

            // for this tutorial, this is the ONLY method that we need, ignore the rest
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (dy > 0) {
                    // Recycle view scrolling downwards...
                    // this if statement detects when user reaches the end of recyclerView, this is only time we should load more
                    if (!recyclerView.canScrollVertically(RecyclerView.FOCUS_DOWN)) {
                        // remember "!" is the same as "== false"
                        // here we are now allowed to load more, but we need to be careful
                        // we must check if itShouldLoadMore variable is true [unlocked]
                        if (isLastItemDisplaying(recyclerView)) {
                            // getDataFromServer(requestCount);
                            loadBrands();
                            //adapter.notifyItemInserted(page*10);
                            //  adapter.notifyDataSetChanged();
                        }
                    }

                }
            }
        });

        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getActivity(),
                recyclerView, new ClickListener() {
            @Override
            public void onClick(View view, final int position) {
                //Values are passing to activity & to fragment as well
                Toast.makeText(getActivity(), "Single Click on position        :"+position,
                        Toast.LENGTH_SHORT).show();

                String brandname=brandList.get(position).getBrand_name();
                Log.d("test",brandname);
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout, new product_details()).addToBackStack("fragment_brand.java").commit();
            }

            @Override
            public void onLongClick(View view, int position) {
//                Toast.makeText(getActivity(), "Long press on position :"+position,
//                        Toast.LENGTH_LONG).show();
            }
        }));

        loadBrands();


        return  v;
    }

    private void loadBrands() {
        page+=1;
        if(page==1){
            pb.setVisibility(View.VISIBLE);}
        else{pb1.setVisibility(View.VISIBLE);  }

        StringRequest stringRequest = new StringRequest(Request.Method.GET, request_url+page,
                new Response.Listener<String>() {
                    @Override


                    public void onResponse(String response) {
                        try {
                            //converting the string to json array object
                            JSONArray array = new JSONArray(response);
                            if (array.isNull(0)) {
                                pb1.setVisibility(View.GONE);
                                Toast.makeText(getContext(), "No More Items Available", Toast.LENGTH_SHORT).show();
                            } else {

                                //traversing through all the object
                                for (int i = 0; i < array.length(); i++) {

                                    //getting product object from json array
                                    JSONObject product = array.getJSONObject(i);
                                    if (page==1){
                                        pb.setVisibility(View.GONE);}
                                    else {pb1.setVisibility(View.GONE);}
                                    //adding the product to product list
                                    brandList.add(new Brands(

                                            product.getString("brand_name"),
                                            product.getString("brand_des"),
                                            product.getString("brand_logo")

                                    ));

                                }}
                            //creating adapter object and setting it to recyclerview
//                            adapter = new ProductsAdapter(getActivity(), productList);
                            // recyclerView.setAdapter(adapter);
                            adapter.notifyDataSetChanged();
                            // adapter.notifyItemRangeInserted((page-1)*10,productList.size());
                            adapter.notifyItemChanged((page-1)*10);

                            Log.d("count",String.valueOf((page-1)*10));


                        } catch(JSONException e){
                            pb.setVisibility(View.GONE);
                            //Toast.makeText(getContext(), error.toString(), Toast.LENGTH_SHORT).show();
                        }
                    }

                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        pb1.setVisibility(View.GONE);
                        Toast.makeText(getContext(), "Request Timeout, Please try again.", Toast.LENGTH_SHORT).show();

                    }
                });
        adapter.notifyItemRangeInserted(page*10,brandList.size());
        //adding our stringrequest to queue
        Volley.newRequestQueue(getActivity()).add(stringRequest);
    }
    private boolean isLastItemDisplaying(RecyclerView recyclerView) {
        if (recyclerView.getAdapter().getItemCount() != 0) {
            int lastVisibleItemPosition = ((LinearLayoutManager) recyclerView.getLayoutManager()).findLastCompletelyVisibleItemPosition();
            if (lastVisibleItemPosition != RecyclerView.NO_POSITION && lastVisibleItemPosition == recyclerView.getAdapter().getItemCount() - 1)
                return true;
        }
        return false;
    }
    @Override
    public boolean onQueryTextSubmit(String query) {
        final List<Brands> filteredModelList = filter(brandList, query);

        adapter.setFilter(filteredModelList);
        return true;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        final List<Brands> filteredModelList = filter(brandList, newText);

        adapter.setFilter(filteredModelList);
        return true;
    }
    private List<Brands> filter(List<Brands> models, String query) {
        query = query.toLowerCase();final List<Brands> filteredModelList = new ArrayList<>();
        for (Brands model : models) {
            final String text = model.getBrand_name().toLowerCase();
            if (text.contains(query)) {
                filteredModelList.add(model);
            }
        }
        return filteredModelList;
    }


    class RecyclerTouchListener implements RecyclerView.OnItemTouchListener{

        private ClickListener clicklistener;
        private GestureDetector gestureDetector;

        public RecyclerTouchListener(Context context, final RecyclerView recycleView, final ClickListener clicklistener){

            this.clicklistener=clicklistener;
            gestureDetector=new GestureDetector(context,new GestureDetector.SimpleOnGestureListener(){
                @Override
                public boolean onSingleTapUp(MotionEvent e) {
                    return true;
                }

                @Override
                public void onLongPress(MotionEvent e) {
                    View child=recycleView.findChildViewUnder(e.getX(),e.getY());
                    if(child!=null && clicklistener!=null){
                        clicklistener.onLongClick(child,recycleView.getChildAdapterPosition(child));
                    }
                }
            });
        }

        @Override
        public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
            View child=rv.findChildViewUnder(e.getX(),e.getY());
            if(child!=null && clicklistener!=null && gestureDetector.onTouchEvent(e)){
                clicklistener.onClick(child,rv.getChildAdapterPosition(child));
            }

            return false;
        }

        @Override
        public void onTouchEvent(RecyclerView rv, MotionEvent e) {

        }

        @Override
        public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

        }
    }

//    @Override
//    public void onResume() {
//        super.onResume();
//        loadBrands();
//    }
}
