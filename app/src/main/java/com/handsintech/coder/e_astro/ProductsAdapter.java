package com.handsintech.coder.e_astro;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class ProductsAdapter extends RecyclerView.Adapter<ProductsAdapter.ProductViewHolder> {


    private Context mCtx;
    private List<Product> productList;
    private static final int LIST_ITEM = 0;
    private static final int GRID_ITEM = 1;
    boolean isSwitchView = true;
    private List<Product> productListFiltered;
    private final static int FADE_DURATION = 1000;
    //...

    //...

    public ProductsAdapter(Context mCtx, List<Product> productList) {
        this.mCtx = mCtx;
        this.productList = productList;
        this.productListFiltered=productList;
    }

    @Override
    public ProductViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        LayoutInflater inflater = LayoutInflater.from(mCtx);
//
//            View view = inflater.inflate(R.layout.products_single_items_view, null);
//            return new ProductViewHolder(view);
        View itemView;
        if (viewType == LIST_ITEM){
            itemView = LayoutInflater.from(mCtx).inflate( R.layout.products_single_items_view, null);
        }else{
            itemView = LayoutInflater.from(mCtx).inflate(R.layout.item_grid_layout, null);
        }
        return new ProductViewHolder(itemView);


    }





    @Override
    public void onBindViewHolder(ProductViewHolder holder, int position) {





            Product product = productList.get(position);
            holder.nameandlast.setText(product.getName()+" "+product.getLastname());
            holder.exp.setText(product.getExperience());
            holder.details.setText(String.valueOf(product.getDetails()));
            //setFadeAnimation(holder.itemView,position);

        //No else part needed as load holder doesn't bind any data



//        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    class ProductViewHolder extends RecyclerView.ViewHolder {

        TextView nameandlast,exp,details;


        public ProductViewHolder(View itemView) {
            super(itemView);

            nameandlast = itemView.findViewById(R.id.nameandlastname);
            exp = itemView.findViewById(R.id.experince);
            details = itemView.findViewById(R.id.details);

        }
    }




    /* notifyDataSetChanged is final method so we can't override it
         call adapter.notifyDataChanged(); after update the list
         */



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
public void updatelistsearch(ArrayList<Product>newarraylist)
{
    newarraylist=new ArrayList<>();
    productList.addAll(newarraylist);
    notifyDataSetChanged();
}
//    @Override
//    public Filter getFilter() {
//        return new Filter() {
//            @Override
//            protected FilterResults performFiltering(CharSequence charSequence) {
//                String charString = charSequence.toString();
//                if (charString.isEmpty()) {
//                    productListFiltered = productList;
//                } else {
//                    List<Product> filteredList = new ArrayList<>();
//                    for (Product row : productList) {
//
//                        // name match condition. this might differ depending on your requirement
//                        // here we are looking for name or phone number match
//
//
//                        if ((row.getName().toLowerCase().toLowerCase()).contains(charString.toString().toLowerCase()) ) {
//                            filteredList.add(row);
//                        }
//                    }
//
//                    productListFiltered = filteredList;
//                }
//
//                FilterResults filterResults = new FilterResults();
//                filterResults.values = productListFiltered;
//                return filterResults;
//            }
//
//            @Override
//            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
//
//                productListFiltered = (ArrayList<Product>) filterResults.values;
//
//                notifyDataSetChanged();
//
//            }
//        };
//    }
public void setFilter(List<Product> countryModels) {
    productList = new ArrayList<>();
    productList.addAll(countryModels);
    notifyDataSetChanged();
}
    private void setFadeAnimation(View view, int position) {
        AlphaAnimation anim = new AlphaAnimation(0.0f, 1.0f);
        anim.setDuration(FADE_DURATION);
        view.startAnimation(anim);
    }

}


