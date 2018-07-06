package com.handsintech.coder.e_astro.tab_By_products;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.handsintech.coder.e_astro.R;
import com.handsintech.coder.e_astro.tab_by_brands.listitems;
import com.squareup.picasso.Picasso;

import java.util.List;


public class CategoriesAdapter extends RecyclerView.Adapter<CategoriesAdapter.CategoriesViewHolder> {



    private Context mCtx;
    private List<CategoriesModel> mcategoriesList;

    public CategoriesAdapter(Context ctx, List<CategoriesModel> mcategoriesList) {
        mCtx = ctx;
        this.mcategoriesList = mcategoriesList;
    }







    @NonNull
    @Override
    public CategoriesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView;
        itemView = LayoutInflater.from(mCtx).inflate( R.layout.product_category_single_item, null);




        return new CategoriesViewHolder(itemView);

    }

    @Override
    public void onBindViewHolder(CategoriesViewHolder holder, int position) {


        CategoriesModel brands = mcategoriesList.get(position);




        String array[]={"BCA1FC","F2D40F","F891BA","8DDCEB"};


        holder.category_cardView.setCardBackgroundColor(Color.parseColor("#"+array[position%array.length]));


        holder.product_categories_name.setText(brands.getCategriesname());
        Log.d("value set",brands.getCategriesname());


    }

    @Override
    public int getItemCount() {
        return mcategoriesList.size();
    }

    class CategoriesViewHolder extends RecyclerView.ViewHolder {

        TextView product_categories_name;
        private CardView category_cardView;



        public CategoriesViewHolder(View itemView) {
            super(itemView);




                product_categories_name = itemView.findViewById(R.id.product_catagory_text);
                category_cardView=itemView.findViewById(R.id.product_categries_card);



        }
    }


}
