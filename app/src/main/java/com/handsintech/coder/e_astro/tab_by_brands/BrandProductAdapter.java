package com.handsintech.coder.e_astro.tab_by_brands;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import com.handsintech.coder.e_astro.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class BrandProductAdapter extends RecyclerView.Adapter<BrandProductAdapter.BrandProductViewHolder>{



    private Context mCtx;
    private List<BrandProduct> brandproductList;
    private static final int LIST_ITEM = 0;
    private static final int GRID_ITEM = 1;
    boolean isSwitchView = true;
    private List<BrandProduct> brandproductListFiltered;
    private final static int FADE_DURATION = 1000;





    public BrandProductAdapter(Context mCtx, List<BrandProduct> brandproductList) {
        this.mCtx = mCtx;
        this.brandproductList = brandproductList;
        this.brandproductListFiltered=brandproductList;
    }


    @NonNull
    @Override
    public BrandProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView;
//        if (viewType == LIST_ITEM){
            itemView = LayoutInflater.from(mCtx).inflate( R.layout.single_brand_product_item, null);
//        }else{
//            itemView = LayoutInflater.from(mCtx).inflate(R.layout.single_brand_product_grid_item, null);
//        }
        return new BrandProductViewHolder(itemView);

    }

    @Override
    public void onBindViewHolder(BrandProductViewHolder holder, int position) {




        BrandProduct brands = brandproductList.get(position);

        String url = brands.getBrand_product_logo();
        String done = " ";
        String[] hope = url.split("/ ");

        for ( int i = 0; i < hope.length; i++)
        {
            done = done + hope[i];
        }

        Log.d("url", done);

        if(isSwitchView)
        {


            Picasso.with(mCtx).
                    load(brands.getBrand_product_logo()).error(android.R.drawable.stat_notify_error).into(holder.civ);
            holder.brand_name.setText(brands.getBrand_product_name());
            holder.brand_price.setText("$"+" "+brands.getBrand_product_price());


        }
        else
        {
            Picasso.with(mCtx).
                    load(brands.getBrand_product_logo()).error(android.R.drawable.stat_notify_error).into(holder.im);
            holder.brand_name.setText(brands.getBrand_product_name());
            holder.brand_price.setText("$"+" "+brands.getBrand_product_price());
        }





    }

    @Override
    public int getItemCount() {
        return brandproductList.size();
    }
    class BrandProductViewHolder extends RecyclerView.ViewHolder {

        TextView brand_name,brand_price;
        CircleImageView civ;
        ImageView im;


        public BrandProductViewHolder(View itemView) {
            super(itemView);
//            if(isSwitchView)
//            {

                civ = itemView.findViewById(R.id.brnd_product_logo);
                brand_name = itemView.findViewById(R.id.brnd_product_name);
                brand_price=itemView.findViewById(R.id.brnd_product_price);


//            }
//            else
//            {
//                im=itemView.findViewById(R.id.brnd_product_logo);
//                brand_name = itemView.findViewById(R.id.brnd_product_name);
//                brand_price=itemView.findViewById(R.id.brnd_product_price);
//            }








        }
    }
    public void setFilter(List<BrandProduct> countryModels) {
        brandproductList = new ArrayList<>();
        brandproductList.addAll(countryModels);
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
