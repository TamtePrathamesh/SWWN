package com.handsintech.coder.e_astro.tab_by_brands;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.widget.TextView;


import com.handsintech.coder.e_astro.R;
import com.squareup.picasso.Picasso;


import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class BrandAdapter extends RecyclerView.Adapter<BrandAdapter.BrandViewHolder> {


private Context mCtx;
private List<Brands> brandList;
private static final int LIST_ITEM = 0;
private static final int GRID_ITEM = 1;
boolean isSwitchView = true;
private List<Brands> brandListFiltered;
private final static int FADE_DURATION = 1000;





public BrandAdapter(Context mCtx, List<Brands> brandList) {
        this.mCtx = mCtx;
        this.brandList = brandList;
        this.brandListFiltered=brandList;
        }


    @NonNull
    @Override
    public BrandViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView;
//        if (viewType == LIST_ITEM){
            itemView = LayoutInflater.from(mCtx).inflate( R.layout.brands_single_item, null);
//        }else{
//            itemView = LayoutInflater.from(mCtx).inflate(R.layout.brand_single_item_grid, null);
//        }
        return new BrandViewHolder(itemView);

    }

    @Override
    public void onBindViewHolder( BrandViewHolder holder, int position) {




        Brands brands = brandList.get(position);

        String url = brands.getBrand_logo();
        String done = " ";
        String[] hope = url.split("/ ");

        for ( int i = 0; i < hope.length; i++)
        {
            done = done + hope[i];
        }

        Log.d("url", done);

        Picasso.with(mCtx).
       load(brands.getBrand_logo()).error(android.R.drawable.stat_notify_error).into(holder.civ);
        holder.brand_name.setText(brands.getBrand_name());
     holder.brand_des.setText(brands.getBrand_des());


    }

    @Override
    public int getItemCount() {
        return brandList.size();
    }
    class BrandViewHolder extends RecyclerView.ViewHolder {

        TextView brand_name, brand_des;
        CircleImageView civ;


        public BrandViewHolder(View itemView) {
            super(itemView);

            civ = itemView.findViewById(R.id.brnd_logo);
            brand_name = itemView.findViewById(R.id.brnd_name);
            brand_des = itemView.findViewById(R.id.brnd_desc);



        }
    }
    public void setFilter(List<Brands> countryModels) {
        brandList = new ArrayList<>();
        brandList.addAll(countryModels);
        notifyDataSetChanged();
    }
    @Override
    public int getItemViewType (int position) {
//        if (isSwitchView){
            return LIST_ITEM;
//        }else{
//            return GRID_ITEM;
//        }
    }

//    public boolean toggleItemViewType () {
//        isSwitchView = !isSwitchView;
//        return isSwitchView;
//    }
    private void setFadeAnimation(View view, int position) {
        AlphaAnimation anim = new AlphaAnimation(0.0f, 1.0f);
        anim.setDuration(FADE_DURATION);
        view.startAnimation(anim);
    }



}
