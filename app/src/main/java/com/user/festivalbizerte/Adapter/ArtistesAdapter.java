package com.user.festivalbizerte.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.TextView;
import com.facebook.drawee.view.SimpleDraweeView;
import com.user.festivalbizerte.Model.ArtisteProgramee;
import com.user.festivalbizerte.R;
import com.user.festivalbizerte.WebService.Urls;

import java.text.DateFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class ArtistesAdapter extends RecyclerView.Adapter<ArtistesAdapter.NewsViewHolder> {

    private Context mContext;
    private List<ArtisteProgramee> List;

    public class NewsViewHolder extends RecyclerView.ViewHolder {

        TextView jourNum, Id_prog, Id_art, jourLet, NomArtiste,HeurPassage;
        SimpleDraweeView imageArtiste;

        public NewsViewHolder(@NonNull View itemView) {
            super(itemView);
            jourNum = itemView.findViewById(R.id.jourNum);
            jourLet = itemView.findViewById(R.id.jourLet);
            NomArtiste = itemView.findViewById(R.id.NomArtiste);
            HeurPassage = itemView.findViewById(R.id.heurpassage);
            Id_prog = itemView.findViewById(R.id.idprog);
            Id_art = itemView.findViewById(R.id.idart);
            imageArtiste = itemView.findViewById(R.id.imageArtiste);
        }
    }

    public ArtistesAdapter(Context mContext, List<ArtisteProgramee> mData) {
        this.mContext = mContext;
        this.List = mData;
    }

    @NonNull
    @Override
    public NewsViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View layout;
        layout = LayoutInflater.from(mContext).inflate(R.layout.item_artistes, viewGroup, false);
        return new NewsViewHolder(layout);
    }

    @Override
    public void onBindViewHolder(@NonNull NewsViewHolder newsViewHolder, int position) {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        DateFormatSymbols symbols = new DateFormatSymbols(Locale.FRANCE);
        Calendar cal = Calendar.getInstance();
        try {
            cal.setTime(sdf.parse(List.get(position).getDatePassage()));
            Log.i("dayName",symbols.getShortWeekdays()[cal.get(Calendar.DAY_OF_WEEK)]+"");
            System.out.println(symbols.getWeekdays()[cal.get(Calendar.DAY_OF_WEEK)]+"");
            Log.i("dayNum",cal.get(Calendar.DAY_OF_MONTH)+"");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        newsViewHolder.jourNum.setText(String.valueOf(cal.get(Calendar.DAY_OF_MONTH)));
        newsViewHolder.jourLet.setText(symbols.getShortWeekdays()[cal.get(Calendar.DAY_OF_WEEK)]);
        newsViewHolder.HeurPassage.setText(List.get(position).getHeurePassage());
        newsViewHolder.Id_prog.setText(String.valueOf(List.get(position).getId_prog()));
        newsViewHolder.Id_art.setText(String.valueOf(List.get(position).getArtiste().getId_art()));
        newsViewHolder.NomArtiste.setText(String.format("%s %s", List.get(position).getArtiste().getNom(), List.get(position).getArtiste().getPrenom()));
        newsViewHolder.imageArtiste.setImageURI(Urls.IMAGE_ARTISTE + List.get(position).getArtiste().getPhoto());
    }

    @Override
    public int getItemCount() {
        return List.size();
    }

}
