package com.handsintech.coder.e_astro.tab_by_brands;


import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
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
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.handsintech.coder.e_astro.MyDividerItemDecoration;
import com.handsintech.coder.e_astro.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;



public class fragment_brand extends Fragment  implements SearchView.OnQueryTextListener{
    public RecyclerView catrecyclerView;
    public catagoryAdapter catadapter;
    public List<listitems> list ;




    BrandAdapter adapter;

    List<Brands> brandList;
    Context mContext;
    View v;
    int page=0;
    //the recyclerview
    RecyclerView recyclerView;
    String request_url = "http://handintech.000webhostapp.com/NEW_HIT/brands.php?start=";


    public static interface ClickListener{
        public void onClick(View view,int position);
        public void onLongClick(View view,int position);
    }
    public void catrecycler()
    {   list= new ArrayList<>();
        String request="http://handintech.000webhostapp.com/NEW_HIT/getcatagories.php";
        catrecyclerView=(RecyclerView)v.findViewById(R.id.recycler_view);
        catrecyclerView.setHasFixedSize(true);

        catrecyclerView.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false));
       // catrecyclerView.setAdapter(catadapter);
        catadapter=new catagoryAdapter(list,getContext());
        catrecyclerView.setAdapter(catadapter);
        StringRequest stringRequest= new StringRequest(Request.Method.GET, request,
                new Response.Listener<String>() {
                    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                    @Override
                    public void onResponse(String response) {
                        JSONArray json= null;
                        try {
                            json = new JSONArray(response);
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(getContext(),e.toString(),Toast.LENGTH_LONG).show();
                        }
                        int i=json.length();
                        // Toast.makeText(getContext(),"response recieved with "+i+" results",Toast.LENGTH_LONG).show();
                        for(int j=0;j<i;j++)
                        {
                            try {
                                JSONObject jsonObject=json.getJSONObject(j);
                                listitems l=new listitems(jsonObject.getString("categery"));
                                list.add(l);
                           //     Log.d("cat",jsonObject.getString("categery"));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        catadapter=new catagoryAdapter(list,getContext());
                        catrecyclerView.setAdapter(catadapter);
                       // catadapter.notifyDataSetChanged();
                    }},
                new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });
        Volley.newRequestQueue(getContext()).add(stringRequest);

    }


    public ProgressBar pb,pb1;
    public fragment_brand() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v= inflater.inflate(R.layout.fragment_brand, container, false);
        page=0;

        pb=(ProgressBar)v.findViewById(R.id.brands_pb);
        pb1=(ProgressBar)v.findViewById(R.id.brands_pb1);

        recyclerView = v.findViewById(R.id.brands_recylcerView);
        setHasOptionsMenu(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new MyDividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL, 36));




        mContext=getActivity();

        //initializing the productlist
        brandList = new ArrayList<>();
        // recyclerView.setOnScrollChangeListener(this);
        adapter = new BrandAdapter(getActivity(), brandList);
        recyclerView.setAdapter(adapter);


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

                    Bundle bundle = new Bundle();
                    bundle.putString("key",brandname); // set your parameteres

                    fragment_brand_products nextFragment = new fragment_brand_products();
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
        if(getActivity()!=null) {
            loadBrands();
            catrecycler();
        }


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
                                Toast.makeText(getActivity(), "No More Items Available", Toast.LENGTH_SHORT).show();
                            } else {
                                int i1=array.length();
//                                Toast.makeText(getContext(),i1+""+"brands",Toast.LENGTH_LONG).show();

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
                        Toast.makeText(getActivity(), "Request Timeout, Please try again.", Toast.LENGTH_SHORT).show();

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

    //////
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
                        adapter.setFilter(brandList);
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
//        if (id == R.id.action_settings) {
//            getActivity().supportInvalidateOptionsMenu();
//            boolean isSwitched = adapter.toggleItemViewType();
//            recyclerView.setLayoutManager(isSwitched ? new LinearLayoutManager(getActivity()) : new GridLayoutManager(getActivity(), 2));
//            adapter.notifyDataSetChanged();
//
//        }
        if (id == R.id.search_menu) {

            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onDetach() {
        super.onDetach();
    }
}