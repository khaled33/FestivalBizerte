package com.user.festivalbizerte.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.user.festivalbizerte.Model.ContactTelItem;
import com.user.festivalbizerte.R;

import java.util.ArrayList;
import java.util.List;

public class ContactTelAdapter extends RecyclerView.Adapter<ContactTelAdapter.NewsViewHolder> implements Filterable {


    Context mContext;
    List<ContactTelItem> mData ;
    List<ContactTelItem> List ;



//    public ProgrameAdapter(Context mContext, List<ProgrameItem> mData, boolean isDar) {
//        this.mContext = mContext;
//        this.mData = mData;
//        this.mDataFiltered = mData;
//    }

    public ContactTelAdapter(Context mContext, List<ContactTelItem> mData) {
        this.mContext = mContext;
        this.mData = mData;
        this.List = mData;

    }

    @NonNull
    @Override
    public NewsViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View layout;
        layout = LayoutInflater.from(mContext).inflate(R.layout.item_contact,viewGroup,false);
        return new NewsViewHolder(layout);
    }

    @Override
    public void onBindViewHolder(@NonNull NewsViewHolder newsViewHolder, int position) {

        // bind data here

        // we apply animation to views here
        // first lets create an animation for user photo
//        newsViewHolder.img_user.setAnimation(AnimationUtils.loadAnimation(mContext,R.anim.fade_transition_animation));

        // lets create the animation for the whole card
        // first lets create a reference to it
        // you ca use the previous same animation like the following

        // but i want to use a different one so lets create it ..
        newsViewHolder.container.setAnimation(AnimationUtils.loadAnimation(mContext,R.anim.fade_scale_animation));


        newsViewHolder.C_nom.setText(List.get(position).getName());
        newsViewHolder.C_num.setText(List.get(position).getDesc());




    }

    @Override
    public int getItemCount() {
        return List.size();
    }

    @Override
    public Filter getFilter() {

        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {

                String Key = constraint.toString();
                if (Key.isEmpty()) {

                    List = mData ;

                }
                else {
                    List<ContactTelItem> lstFiltered = new ArrayList<>();
                    for (ContactTelItem row : mData) {

                        if (row.getName().toLowerCase().contains(Key.toLowerCase())){
                            lstFiltered.add(row);
                        }

                    }

                    List = lstFiltered;

                }


                FilterResults filterResults = new FilterResults();
                filterResults.values= List;
                return filterResults;

            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {


                List = (List<ContactTelItem>) results.values;
                notifyDataSetChanged();

            }
        };




    }

    public class NewsViewHolder extends RecyclerView.ViewHolder {



        TextView C_nom,C_num,Titre,Description,Horaire,Prix;

        LinearLayout container;





        public NewsViewHolder(@NonNull View itemView) {
            super(itemView);
            container = itemView.findViewById(R.id.container);

            C_nom = itemView.findViewById(R.id.nom);
            C_num = itemView.findViewById(R.id.num);






        }






    }
}
