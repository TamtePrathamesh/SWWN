package com.handsintech.coder.e_astro;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class ProductsAdapter extends RecyclerView.Adapter<ProductsAdapter.ProductViewHolder> {


    private Context mCtx;
    private List<Product> productList;

    public ProductsAdapter(Context mCtx, List<Product> productList) {
        this.mCtx = mCtx;
        this.productList = productList;
    }

    @Override
    public ProductViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.products_single_items_view, null);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ProductViewHolder holder, int position) {
        Product product = productList.get(position);



        holder.nameandlast.setText(product.getName()+" "+product.getLastname());
        holder.exp.setText(product.getExperience());
        holder.details.setText(String.valueOf(product.getDetails()));

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
}
