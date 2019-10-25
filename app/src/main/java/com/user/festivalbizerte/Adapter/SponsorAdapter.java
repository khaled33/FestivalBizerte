package com.user.festivalbizerte.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.drawee.view.SimpleDraweeView;
import com.user.festivalbizerte.Model.Sponsors;
import com.user.festivalbizerte.R;
import com.user.festivalbizerte.WebService.Urls;

import java.util.Calendar;
import java.util.List;

public class SponsorAdapter extends RecyclerView.Adapter<SponsorAdapter.NewsViewHolder> {

    private Context mContext;
    private List<Sponsors> List;

    class NewsViewHolder extends RecyclerView.ViewHolder {
        TextView NomSP, Id,Description,Site;
        //ImageView Logo;
        SimpleDraweeView Logo;

        NewsViewHolder(@NonNull View itemView) {
            super(itemView);
            NomSP = itemView.findViewById(R.id.Nomsp);
            Description = itemView.findViewById(R.id.description);
            Site = itemView.findViewById(R.id.site);
            Id = itemView.findViewById(R.id.Id);
           Logo = itemView.findViewById(R.id.logo);

        }
    }

    public SponsorAdapter(Context mContext, java.util.List<Sponsors> list) {
        this.mContext = mContext;
        this.List = list;
    }

    @NonNull
    @Override
    public NewsViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View layout = LayoutInflater.from(mContext).inflate(R.layout.item_sponsor, viewGroup, false);
        return new NewsViewHolder(layout);
    }

    @Override
    public void onBindViewHolder(@NonNull SponsorAdapter.NewsViewHolder newsViewHolder, int position) {

        newsViewHolder.Id.setText(String.valueOf(List.get(position).getId_sp()));
        newsViewHolder.NomSP.setText(List.get(position).getNom());
        newsViewHolder.Description.setText(List.get(position).getDescription());
        newsViewHolder.Site.setText(List.get(position).getWebsite());

        newsViewHolder.Description.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext, "site web", Toast.LENGTH_SHORT).show();
            }
        });
        newsViewHolder.Logo.setImageURI(Urls.IMAGE_SPONSOR + List.get(position).getLogo());
    }

    @Override
    public int getItemCount() {
        return List.size();
    }
}
