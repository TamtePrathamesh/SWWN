package com.handsintech.coder.e_astro.tab_By_products;


import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.handsintech.coder.e_astro.MyDividerItemDecoration;
import com.handsintech.coder.e_astro.R;
import com.handsintech.coder.e_astro.tab_by_brands.BrandAdapter;
import com.handsintech.coder.e_astro.tab_by_brands.Brands;
import com.handsintech.coder.e_astro.tab_by_brands.catagoryAdapter;
import com.handsintech.coder.e_astro.tab_by_brands.fragment_brand;
import com.handsintech.coder.e_astro.tab_by_brands.fragment_brand_products;
import com.handsintech.coder.e_astro.tab_by_brands.listitems;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.simple.parser.JSONParser;

import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;


public class tab2_by_products extends Fragment implements SearchView.OnQueryTextListener {


    public tab2_by_products() {
        // Required empty public constructor
    }






    ByProductAdapter adapter;
    CategoriesAdapter mCategoriesAdapter;

    List<Product> productList;
    List<CategoriesModel>mCategoriesModelList;

    String proudct_by_category_id="";

    View v;

    RecyclerView recyclerView,category_recyclerView;
    //String request_url = "http://handintech.000webhostapp.com/NEW_HIT/brands.php?start=";
    String request_url = "https://socialworldwidenetwork.com/productlist.php";
    String category_product_url="https://socialworldwidenetwork.com/getProduct.php?categoryId=";
    public ProgressBar pb,pb1;

    public static interface ClickListener{
        public void onClick(View view,int position);
        public void onLongClick(View view,int position);
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v= inflater.inflate(R.layout.fragment_tab2_by_products, container, false);


        pb=(ProgressBar)v.findViewById(R.id.products_pb);
        pb1=(ProgressBar)v.findViewById(R.id.products_pb1);

        recyclerView = v.findViewById(R.id.products_recylcerView);
        category_recyclerView=v.findViewById(R.id.product_category_recycler_view);
        setHasOptionsMenu(true);

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        LinearLayoutManager lm=new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL,false);
        lm.setAutoMeasureEnabled(true);
       // category_recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL,false));
       category_recyclerView.setLayoutManager(lm);


        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new MyDividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL, 36));




        //initializing the productlist
        productList = new ArrayList<>();
        mCategoriesModelList=new ArrayList<>();
        // recyclerView.setOnScrollChangeListener(this);
        adapter = new ByProductAdapter(getActivity(), productList);
        mCategoriesAdapter=new CategoriesAdapter(getActivity(),mCategoriesModelList);

        recyclerView.setAdapter(adapter);
        category_recyclerView.setAdapter(mCategoriesAdapter);


        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getActivity(),
                recyclerView, new ClickListener() {
            @Override
            public void onClick(View view, final int position) {
                //Values are passing to activity & to fragment as well


                String product_id=(productList.get(position).getId());
                Log.d("test57",product_id);

                Bundle bundle = new Bundle();
                bundle.putString("key_id",product_id); // set your parameteres

                ByProductTab_product_details nextFragment = new ByProductTab_product_details();
                nextFragment.setArguments(bundle);

                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction().addToBackStack(null);
                fragmentTransaction.replace(R.id.frame_layout, nextFragment, "fragment_brand_products");


                fragmentTransaction.commit();
                // getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout, nextFragment).addToBackStack("fragment_brand.java").commit();
            }

            @Override
            public void onLongClick(View view, int position) {
//                Toast.makeText(getActivity(), "Long press on position :"+position,
//                        Toast.LENGTH_LONG).show();
            }
        }));

        category_recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getActivity(),
                recyclerView, new ClickListener() {
            @Override
            public void onClick(View view, final int position) {
                //Values are passing to activity & to fragment as well
//                Toast.makeText(getActivity(), "Long press on position :"+position,
//                       Toast.LENGTH_LONG).show();
                proudct_by_category_id=mCategoriesModelList.get(position).getCategory_id();
                Log.d("categories",proudct_by_category_id);
                getproductsbycategory(proudct_by_category_id);

            }

            @Override
            public void onLongClick(View view, int position) {
//                Toast.makeText(getActivity(), "Long press on position :"+position,
//                        Toast.LENGTH_LONG).show();
            }
        }));


        loadProducts();
        categories_product();

        return v;
    }


    public void categories_product()
    {
        String request="https://socialworldwidenetwork.com/getCategory.php";

        JsonObjectRequest stringRequest= new JsonObjectRequest(Request.Method.GET, request,null,
                new Response.Listener<JSONObject>() {
                    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                    @Override
                    public void onResponse(JSONObject response) {

                        try {
                            JSONArray json = response.getJSONArray("categories");

                            int i = json.length();
                            // Toast.makeText(getContext(),"response recieved with "+i+" results",Toast.LENGTH_LONG).show();
                            for (int j = 0; j < i; j++) {

                                JSONObject jsonObject = json.getJSONObject(j);

                                mCategoriesModelList.add(new CategoriesModel(
                                        jsonObject.getString("id"),
                                        jsonObject.getString("name")


                                ));

                                category_recyclerView.setAdapter(mCategoriesAdapter);
                                mCategoriesAdapter.notifyDataSetChanged();

                                Log.d("cat", jsonObject.getString("name"));
                            }
                        }
                        catch (JSONException e) {
                                e.printStackTrace();
                            }


                    }
                    },
                new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });
        Volley.newRequestQueue(getContext()).add(stringRequest);

    }
    private void loadProducts() {

            pb.setVisibility(View.VISIBLE);

        final JsonObjectRequest jsonArray = new JsonObjectRequest(Request.Method.GET, request_url,null,
                new Response.Listener<JSONObject>() {
                    @Override


                    public void onResponse(JSONObject response) {
                        try {
                            //converting the string to json array object
                            //JSONParser parser_obj = new JSONParser();
                           JSONArray array = response.getJSONArray("message");


                                //traversing through all the object
                                for (int i = 0; i < array.length(); i++) {

                                    //getting product object from json array
                                    JSONObject product  =array.getJSONObject(i);

                                        pb.setVisibility(View.GONE);
                                    //adding the product to product list
                                    productList.add(new Product(
                                            product.getString("id"),
                                            product.getString("name"),
                                            product.getString("sku"),
                                            product.getString("description"),
                                            product.getString("small_image")

                                    ));

                                }
                            //creating adapter object and setting it to recyclerview
//                            adapter = new ProductsAdapter(getActivity(), productList);
                             recyclerView.setAdapter(adapter);
                            adapter.notifyDataSetChanged();
                            // adapter.notifyItemRangeInserted((page-1)*10,productList.size());
                           // adapter.notifyItemChanged((page-1)*10);

                           // Log.d("count",String.valueOf((page-1)*10));


                        } catch(JSONException e){
                            pb.setVisibility(View.GONE);
                            Log.d("error57123",e.toString());
                            //Toast.makeText(getContext(), error.toString(), Toast.LENGTH_SHORT).show();
                        }
                    }

                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        pb1.setVisibility(View.GONE);
                        Log.d("error57123",error.toString());
                        Toast.makeText(getActivity(), "Request Timeout, Please try again.", Toast.LENGTH_SHORT).show();

                    }
                });
      //  adapter.notifyItemRangeInserted(page*10,brandList.size());
        //adding our stringrequest to queue
        Volley.newRequestQueue(getActivity()).add(jsonArray);
    }
//    private boolean isLastItemDisplaying(RecyclerView recyclerView) {
//        if (recyclerView.getAdapter().getItemCount() != 0) {
//            int lastVisibleItemPosition = ((LinearLayoutManager) recyclerView.getLayoutManager()).findLastCompletelyVisibleItemPosition();
//            if (lastVisibleItemPosition != RecyclerView.NO_POSITION && lastVisibleItemPosition == recyclerView.getAdapter().getItemCount() - 1)
//                return true;
//        }
//        return false;
//    }
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
            final String text = model.getProduct_name().toLowerCase();
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

    private void getproductsbycategory(String ids) {

        productList.clear();
        adapter.notifyDataSetChanged();
        pb.setVisibility(View.VISIBLE);

        final JsonObjectRequest jsonArray = new JsonObjectRequest(Request.Method.GET, category_product_url+ids,null,
                new Response.Listener<JSONObject>() {
                    @Override


                    public void onResponse(JSONObject response) {
                        try {
                            //converting the string to json array object
                            //JSONParser parser_obj = new JSONParser();
                            JSONArray array = response.getJSONArray("message");


                            //traversing through all the object
                            for (int i = 0; i < array.length(); i++) {

                                //getting product object from json array
                                JSONObject product  =array.getJSONObject(i);

                                pb.setVisibility(View.GONE);
                                //adding the product to product list
                                productList.add(new Product(
                                        product.getString("id"),
                                        product.getString("name"),
                                        product.getString("sku"),
                                        product.getString("small_image")

                                ));

                            }
                            //creating adapter object and setting it to recyclerview
//                            adapter = new ProductsAdapter(getActivity(), productList);
                            recyclerView.setAdapter(adapter);
                            adapter.notifyDataSetChanged();
                            // adapter.notifyItemRangeInserted((page-1)*10,productList.size());
                            // adapter.notifyItemChanged((page-1)*10);

                            // Log.d("count",String.valueOf((page-1)*10));


                        } catch(JSONException e){
                            pb.setVisibility(View.GONE);
                            Log.d("error57123",e.toString());
                            //Toast.makeText(getContext(), error.toString(), Toast.LENGTH_SHORT).show();
                        }
                    }

                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        pb1.setVisibility(View.GONE);
                        Log.d("error57123",error.toString());
                        Toast.makeText(getActivity(), "Request Timeout, Please try again.", Toast.LENGTH_SHORT).show();

                    }
                });
        //  adapter.notifyItemRangeInserted(page*10,brandList.size());
        //adding our stringrequest to queue
        Volley.newRequestQueue(getActivity()).add(jsonArray);
    }


}
