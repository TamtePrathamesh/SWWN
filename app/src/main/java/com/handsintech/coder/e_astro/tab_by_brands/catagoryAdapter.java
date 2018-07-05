package com.handsintech.coder.e_astro.tab_by_brands;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.handsintech.coder.e_astro.R;

import java.util.ArrayList;
import java.util.List;

public class catagoryAdapter extends RecyclerView.Adapter<catagoryAdapter.ViewHolder> {
    private List<listitems> list=new ArrayList<>();
    private Context context;

    public catagoryAdapter(List<listitems> listitem, Context context) {
        this.list= listitem;
        this.context = context;
        Log.d("in adapter", String.valueOf(list.size()));
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.list_items,parent,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull catagoryAdapter.ViewHolder holder, int position) {
        String array[]={"BCA1FC","F2D40F","F891BA","8DDCEB"};
        listitems l=list.get(position);
        holder.textView.setText(l.getText1());
        holder.cardView.setCardBackgroundColor(Color.parseColor("#"+array[position%array.length]));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView textView;
        private CardView cardView;
        public ViewHolder(final View itemView) {
            super(itemView);
           // t1=(TextView)itemView.findViewById(R.id.t1);
            textView=(TextView) itemView.findViewById(R.id.catagory_text);
            cardView=(CardView)itemView.findViewById(R.id.card);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(itemView.getContext(),"you have selected "+textView.getText(),Toast.LENGTH_LONG).show();
                }
            });
        }
    }
}
