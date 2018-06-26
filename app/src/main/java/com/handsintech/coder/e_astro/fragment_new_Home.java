package com.handsintech.coder.e_astro;


import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.SearchView.OnQueryTextListener;
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

public class fragment_new_Home extends Fragment implements SearchView.OnQueryTextListener{


    public FloatingActionButton fab;

    ProductsAdapter adapter;

    List<Product> productList;
    View v;

    //the recyclerview
    RecyclerView recyclerView;
    String request_url = "http://handintech.000webhostapp.com/NEW_HIT/new_products.php?start=";
    int page=0;


    public  ProgressBar pb,pb1;
    //Volley Request Queue
    SearchView searchView;


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        v= inflater.inflate(R.layout.fragment_new_home, container, false);


    //    pb =
        pb=(ProgressBar)v.findViewById(R.id.pb);
        pb1=(ProgressBar)v.findViewById(R.id.pb1);

        recyclerView = v.findViewById(R.id.recylcerView);

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        setHasOptionsMenu(true);
        //initializing the productlist
        productList = new ArrayList<>();
        // recyclerView.setOnScrollChangeListener(this);
        adapter = new ProductsAdapter(getActivity(), productList);
        recyclerView.setAdapter(adapter);

        fab = (FloatingActionButton)v. findViewById(R.id.fab);
        fab.setVisibility(View.GONE);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        if(SharedPref.getInstance(getActivity()).IsexpertRegiserted())
        {
                    Log.d("cq","true");
            fab.setVisibility(View.VISIBLE);

            recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
                @Override
                public void onScrollStateChanged(RecyclerView recyclerView, int newState) {

                    if (newState == RecyclerView.SCROLL_STATE_IDLE){
                        fab.show();
                    }

                    super.onScrollStateChanged(recyclerView, newState);
                }

                @Override
                public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                    super.onScrolled(recyclerView, dx, dy);
                    if (dy > 0 ||dy<0 && fab.isShown())
                        fab.hide();
                }

            });
        }else {
            Log.d("cq","false");
            fab.setVisibility(View.GONE);

        }



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
                            loadProducts();
                            //adapter.notifyItemInserted(page*10);
                          //  adapter.notifyDataSetChanged();
                        }
                    }

                }
            }
        });
        loadProducts();
        return v;
    }
//--


    private void loadProducts() {
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
                                    productList.add(new Product(
                                            product.getString("name"),
                                            product.getString("lastname"),
                                            product.getString("details"),
                                            product.getString("experience")

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
        adapter.notifyItemRangeInserted(page*10,productList.size());
        //adding our stringrequest to queue
        Volley.newRequestQueue(getActivity()).add(stringRequest);
    }
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_homee, menu);
        super.onCreateOptionsMenu(menu, inflater);

        final MenuItem item = menu.findItem(R.id.search_menu);
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(item);
        searchView.setOnQueryTextListener(this);

        MenuItemCompat.setOnActionExpandListener(item,
                new MenuItemCompat.OnActionExpandListener() {
                    @Override
                    public boolean onMenuItemActionCollapse(MenuItem item) {
// Do something when collapsed
                        adapter.setFilter(productList);
                        return true; // Return true to collapse action view
                    }

                    @Override
                    public boolean onMenuItemActionExpand(MenuItem item) {
// Do something when expanded
                        return true; // Return true to expand action view
                    }
                });

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
//
//        switch (id) {
//            case R.id.action_settings:
//                getActivity().supportInvalidateOptionsMenu();
//                boolean isSwitched = adapter.toggleItemViewType();
//                recyclerView.setLayoutManager(isSwitched ? new LinearLayoutManager(getActivity()) : new GridLayoutManager(getActivity(), 2));
//                adapter.notifyDataSetChanged();
//                break;
//            case R.id.search_menu:
////                SearchView searchView=(SearchView)item.getActionView();
////                searchView.setOnQueryTextListener(this);
//                return  true;
//
//                break;
//        }
        if (id == R.id.action_settings) {
            getActivity().supportInvalidateOptionsMenu();
            boolean isSwitched = adapter.toggleItemViewType();
            recyclerView.setLayoutManager(isSwitched ? new LinearLayoutManager(getActivity()) : new GridLayoutManager(getActivity(), 2));
            adapter.notifyDataSetChanged();

        }
        if (id == R.id.search_menu) {

            return true;
        }
        return super.onOptionsItemSelected(item);
    }







    @Override
    public boolean onQueryTextSubmit(String query) {
        final List<Product> filteredModelList = filter(productList, query);

        adapter.setFilter(filteredModelList);
        return true;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        final List<Product> filteredModelList = filter(productList, newText);

        adapter.setFilter(filteredModelList);
        return true;
    }
    private List<Product> filter(List<Product> models, String query) {
        query = query.toLowerCase();final List<Product> filteredModelList = new ArrayList<>();
        for (Product model : models) {
            final String text = model.getName().toLowerCase();
            if (text.contains(query)) {
                filteredModelList.add(model);
            }
        }
        return filteredModelList;
    }
    private boolean isLastItemDisplaying(RecyclerView recyclerView) {
        if (recyclerView.getAdapter().getItemCount() != 0) {
            int lastVisibleItemPosition = ((LinearLayoutManager) recyclerView.getLayoutManager()).findLastCompletelyVisibleItemPosition();
            if (lastVisibleItemPosition != RecyclerView.NO_POSITION && lastVisibleItemPosition == recyclerView.getAdapter().getItemCount() - 1)
                return true;
        }
        return false;
    }


}












