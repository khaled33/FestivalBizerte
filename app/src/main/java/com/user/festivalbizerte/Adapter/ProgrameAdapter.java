package com.user.festivalbizerte.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.user.festivalbizerte.Model.ProgrameItem;
import com.user.festivalbizerte.Model.Programmes;
import com.user.festivalbizerte.R;

import java.text.DateFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class ProgrameAdapter extends RecyclerView.Adapter<ProgrameAdapter.NewsViewHolder> implements Filterable {


    private Context mContext;
    private List<Programmes> mData;
    private List<Programmes> List;

    public class NewsViewHolder extends RecyclerView.ViewHolder {

        TextView date_jour, date_mois, Titre, Description, Horaire, Prix;
        LinearLayout container;

        public NewsViewHolder(@NonNull View itemView) {
            super(itemView);
            container = itemView.findViewById(R.id.container);

            date_jour = itemView.findViewById(R.id.date_jour);
            date_mois = itemView.findViewById(R.id.date_mois);
            Titre = itemView.findViewById(R.id.Titre);
            Prix = itemView.findViewById(R.id.Prix);
            Description = itemView.findViewById(R.id.Description);
            Horaire = itemView.findViewById(R.id.Horaire);

        }
    }

    public ProgrameAdapter(Context mContext, List<Programmes> mData) {
        this.mContext = mContext;
        this.mData = mData;
        this.List = mData;
    }

    @NonNull
    @Override
    public NewsViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View layout;
        layout = LayoutInflater.from(mContext).inflate(R.layout.item_news, viewGroup, false);
        return new NewsViewHolder(layout);
    }

    @Override
    public void onBindViewHolder(@NonNull NewsViewHolder newsViewHolder, int position) {

        newsViewHolder.container.setAnimation(AnimationUtils.loadAnimation(mContext, R.anim.fade_scale_animation));
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        DateFormatSymbols symbols = new DateFormatSymbols(Locale.FRANCE);
        Calendar cal = Calendar.getInstance();
        try {
            cal.setTime(sdf.parse(List.get(position).getDate()));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        newsViewHolder.date_jour.setText(String.valueOf(cal.get(Calendar.DAY_OF_MONTH)));
        newsViewHolder.date_mois.setText(String.valueOf(symbols.getMonths()[cal.get(Calendar.MONTH)]));
        newsViewHolder.Description.setText(List.get(position).getDescription());
        newsViewHolder.Titre.setText(List.get(position).getTitre());
        newsViewHolder.Prix.setText(String.format("%s - %s DT", String.valueOf(List.get(position).getPrix()),
                String.valueOf(List.get(position).getPrix2())));
        newsViewHolder.Horaire.setText(List.get(position).getHoraire());

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
                    List = mData;
                } else {
                    List<Programmes> lstFiltered = new ArrayList<>();
                    for (Programmes row : mData) {
                        if (row.getTitre().toLowerCase().contains(Key.toLowerCase())) {
                            lstFiltered.add(row);
                        }
                    }
                    List = lstFiltered;

                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = List;
                return filterResults;

            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                List = (List<Programmes>) results.values;
                notifyDataSetChanged();
            }
        };
    }
}
