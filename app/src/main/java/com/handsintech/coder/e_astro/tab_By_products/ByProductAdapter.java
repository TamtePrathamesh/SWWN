package com.handsintech.coder.e_astro.tab_By_products;

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
import com.handsintech.coder.e_astro.tab_by_brands.Brands;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class ByProductAdapter extends RecyclerView.Adapter<ByProductAdapter.ByProductAdapterViewHolder> {


        private Context mCtx;
        private List<Product> mProductsList;
        private static final int LIST_ITEM = 0;
        private static final int GRID_ITEM = 1;
        boolean isSwitchView = true;
        private List<Product> mProductListFiltered;
        private final static int FADE_DURATION = 1000;




    public ByProductAdapter(Context mCtx, List< Product > productlist) {
            this.mCtx = mCtx;
            this.mProductsList = productlist;
            this.mProductListFiltered=productlist;
        }


        @NonNull
        @Override
        public ByProductAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View itemView;
            if (viewType == LIST_ITEM){
                itemView = LayoutInflater.from(mCtx).inflate( R.layout.product_single_item, null);
            }else{
                itemView = LayoutInflater.from(mCtx).inflate(R.layout.product_single_grid, null);
            }
            return new ByProductAdapterViewHolder(itemView);

        }

        @Override
        public void onBindViewHolder(ByProductAdapterViewHolder holder, int position) {




            Product brands = mProductsList.get(position);

            String url = brands.getProduct_logo();
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
                        load(brands.getProduct_logo()).error(android.R.drawable.stat_notify_error).into(holder.civ);
                holder.product_name.setText(brands.getProduct_name());
                holder.product_des.setText(brands.getProduct_des());


            }
            else
            {
                Picasso.with(mCtx).
                        load(brands.getProduct_logo()).error(android.R.drawable.stat_notify_error).into(holder.im);
                holder.product_name.setText(brands.getProduct_name());
              //  holder.product_des.setText(brands.getProduct_des());
            }



        }

        @Override
        public int getItemCount() {
            return mProductsList.size();
        }
        class ByProductAdapterViewHolder extends RecyclerView.ViewHolder {

            TextView product_name, product_des;
            CircleImageView civ;
            ImageView im;






            public ByProductAdapterViewHolder(View itemView) {
                super(itemView);

                if(isSwitchView)
                {



                    civ = itemView.findViewById(R.id.product_logo);
                    product_name = itemView.findViewById(R.id.product_name);
                    product_des=itemView.findViewById(R.id.product_desc);


                }
                else
                {
                    im=itemView.findViewById(R.id.img_product_logo);
                    product_name = itemView.findViewById(R.id.product_name);
                   // product_des=itemView.findViewById(R.id.product_desc);
                }



            }
        }
        public void setFilter(List<Product> countryModels) {
            mProductsList = new ArrayList<>();
            mProductsList.addAll(countryModels);
            notifyDataSetChanged();
        }
        @Override
        public int getItemViewType (int position) {
            if (isSwitchView){
                return LIST_ITEM;
            }else{
                return GRID_ITEM;
            }
        }

        public boolean toggleItemViewType () {
            isSwitchView = !isSwitchView;
            return isSwitchView;
        }
        private void setFadeAnimation(View view, int position) {
            AlphaAnimation anim = new AlphaAnimation(0.0f, 1.0f);
            anim.setDuration(FADE_DURATION);
            view.startAnimation(anim);
        }


    }
