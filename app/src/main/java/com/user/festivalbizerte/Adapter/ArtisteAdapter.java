package com.user.festivalbizerte.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.user.festivalbizerte.Model.Artistes;
import com.user.festivalbizerte.R;
import com.user.festivalbizerte.WebService.Urls;

import java.util.List;

public class ArtisteAdapter extends RecyclerView.Adapter<ArtisteAdapter.NewsViewHolder> {


    private Context mContext;
    private List<Artistes> List;

    class NewsViewHolder extends RecyclerView.ViewHolder {
        TextView NomArtiste, Id;
        SimpleDraweeView imageArtiste;

        NewsViewHolder(@NonNull View itemView) {
            super(itemView);
            NomArtiste = itemView.findViewById(R.id.NomArtiste);
            Id = itemView.findViewById(R.id.id_art);
            imageArtiste = itemView.findViewById(R.id.imageArtiste);

        }
    }

    public ArtisteAdapter(Context mContext, List<Artistes> mData) {
        this.mContext = mContext;
        this.List = mData;
    }

    @NonNull
    @Override
    public NewsViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View layout = LayoutInflater.from(mContext).inflate(R.layout.item_artiste, viewGroup, false);
        return new NewsViewHolder(layout);
    }

    @Override
    public void onBindViewHolder(@NonNull NewsViewHolder newsViewHolder, int position) {

        newsViewHolder.NomArtiste.setText(String.format("%s %s", List.get(position).getNom(), List.get(position).getPrenom()));
        newsViewHolder.Id.setText(String.valueOf(List.get(position).getId_art()));
        newsViewHolder.imageArtiste.setImageURI(Urls.IMAGE_ARTISTE + List.get(position).getPhoto());
    }

    @Override
    public int getItemCount() {
        return List.size();
    }

}
