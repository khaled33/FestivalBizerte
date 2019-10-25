package com.user.festivalbizerte.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.user.festivalbizerte.Model.ServiceItem;
import com.user.festivalbizerte.R;

import java.util.List;

public class ServiceAdapter extends RecyclerView.Adapter<ServiceAdapter.NewsViewHolder>  {


    Context mContext;
    List<ServiceItem> mData ;
    List<ServiceItem> List ;



    public ServiceAdapter(Context mContext, List<ServiceItem> mData) {
        this.mContext = mContext;
        this.mData = mData;
        this.List = mData;

    }

    @NonNull
    @Override
    public NewsViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View layout;
        layout = LayoutInflater.from(mContext).inflate(R.layout.item_service,viewGroup,false);
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

        newsViewHolder.NomService.setText(List.get(position).getNomService());
        newsViewHolder.IconService.setImageResource(List.get(position).getImageService());

    }

    @Override
    public int getItemCount() {
        return List.size();
    }


    public class NewsViewHolder extends RecyclerView.ViewHolder {



        TextView NomService;
        ImageView IconService;


        public NewsViewHolder(@NonNull View itemView) {
            super(itemView);


            NomService = itemView.findViewById(R.id.NomService);
            IconService = itemView.findViewById(R.id.IconService);



        }






    }
}
