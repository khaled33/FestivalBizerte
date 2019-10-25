package com.user.festivalbizerte;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.drawee.generic.RoundingParams;
import com.facebook.drawee.view.SimpleDraweeView;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.gson.Gson;
import com.user.festivalbizerte.Model.RSResponse;
import com.user.festivalbizerte.Model.UserInfos;
import com.user.festivalbizerte.Model.UserQuiz;
import com.user.festivalbizerte.Utils.Helpers;
import com.user.festivalbizerte.WebService.Urls;
import com.user.festivalbizerte.WebService.WebService;
import com.user.festivalbizerte.session.RSSession;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.github.inflationx.calligraphy3.CalligraphyConfig;
import io.github.inflationx.calligraphy3.CalligraphyInterceptor;
import io.github.inflationx.viewpump.ViewPump;
import io.github.inflationx.viewpump.ViewPumpContextWrapper;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FinchQuizActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    @BindView(R.id.drawerLayout)
    DrawerLayout drawerLayout;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.navigation)
    NavigationView navigationView;
    @BindView(R.id.score)
    TextView txt_score;
    @BindView(R.id.textView17)
    TextView txt_qtVrai;
    @BindView(R.id.textView16)
    TextView txt_qtFaux;
    ActionBarDrawerToggle actionBarDrawerToggle;
    Context context;
//    @BindView(R.id.piechart)
//    PieChart pieChart;
//    List<PieEntry> pieEntries = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_finch_quiz);
        ButterKnife.bind(this);
        context = this;
        ViewPump.init(ViewPump.builder()
                .addInterceptor(new CalligraphyInterceptor(
                        new CalligraphyConfig.Builder()
                                .setDefaultFontPath("font/raleway_light.ttf")
                                .setFontAttrId(R.attr.fontPath)
                                .build()))
                .build());
        setSupportActionBar(toolbar);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.drawer_open, R.string.drawer_close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        loadHeaderView(RSSession.getLocalStorage(context));

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            UserQuiz userQuiz = new Gson().fromJson(extras.getString("userQuiz"), UserQuiz.class);
            addUserQuiz(userQuiz);
            int Nb_questionVrai = extras.getInt("questionCorrect");
            int Nb_questionFalse = extras.getInt("questionfalse");
            txt_score.setText(String.valueOf(userQuiz.getScore_jour()));
            txt_qtVrai.setText(String.valueOf(Nb_questionVrai));
            txt_qtFaux.setText(String.valueOf(Nb_questionFalse));
//            pieEntries.add(new PieEntry(Nb_questionVrai, "correct"));
//            pieEntries.add(new PieEntry(Nb_questionFalse, "worng"));
            Log.i("afichage", "score:" + userQuiz.getScore_jour() + " /nbqtv:" +
                    Nb_questionVrai + " /nbqtf:" + Nb_questionFalse);
        }
//        pieChart.getDescription().setEnabled(false);
//        pieChart.setRotationEnabled(true);
//        pieChart.setHoleRadius(50f);
//        pieChart.setDrawHoleEnabled(true);
//        pieChart.setHoleColor(Color.TRANSPARENT);
//        pieChart.animateY(1000, Easing.EaseInOutCubic);
//        pieChart.getLegend().setEnabled(true);
//        pieChart.getLegend().setTextSize(10f);
//        pieChart.getLegend().setTextColor(Color.WHITE);
//
//        PieDataSet pieDataSet = new PieDataSet(pieEntries, "");
//        pieDataSet.setSliceSpace(3f);
//        pieDataSet.setSelectionShift(5f);
//        pieDataSet.setColors(ColorTemplate.createColors(new int[]{Color.GREEN, Color.RED}));
//
//        PieData pieData = new PieData(pieDataSet);
//        pieData.setValueTextSize(10f);
//        pieData.setValueTextColor(Color.WHITE);
//
//        pieChart.setData(pieData);

    }

    private void addUserQuiz(UserQuiz userQuiz) {
        if (Helpers.isConnected(context)) {
            Call<RSResponse> callUpload = WebService.getInstance().getApi().addUserQuiz(userQuiz);
            callUpload.enqueue(new Callback<RSResponse>() {
                @Override
                public void onResponse(Call<RSResponse> call, Response<RSResponse> response) {
                    if (response.body() != null) {
                        if (response.body().getStatus() == 1) {
                            Log.i("ok", "insertt");
//                            Toast.makeText(getApplicationContext(), "Insert", Toast.LENGTH_SHORT).show();
                        } else if (response.body().getStatus() == 0) {
                            Log.i("ok", "no");
//                            Toast.makeText(getApplicationContext(), "errr ", Toast.LENGTH_SHORT).show();
                        }
                    }
                }

                @Override
                public void onFailure(Call<RSResponse> call, Throwable t) {
                    Log.d("err", t.getMessage());
                }
            });
        } else {
            Helpers.ShowMessageConnection(context);
        }
    }

    private void loadHeaderView(UserInfos userInfos) {
        navigationView.bringToFront();
        navigationView.setNavigationItemSelectedListener(this);
        if (userInfos != null) {
            View headerView = navigationView.getHeaderView(0);
            SimpleDraweeView imageProfile = headerView.findViewById(R.id.ImageUser);
            TextView EmailProfile = headerView.findViewById(R.id.Email);
            EmailProfile.setText(userInfos.getEmail());
            RoundingParams roundingParams = RoundingParams.fromCornersRadius(5f);
            roundingParams.setBorder(getResources().getColor(R.color.white), 2f);
            roundingParams.setRoundAsCircle(true);
            imageProfile.getHierarchy().setRoundingParams(roundingParams);
            imageProfile.setImageURI(Urls.IMAGE_PROFIL + userInfos.getPhoto());
        }
    }

    @OnClick(R.id.valide)
    public void Retour() {
        startActivity(new Intent(context, JeuxActivity.class));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (actionBarDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.acueil:
                startActivity(new Intent(context, MainActivity.class));
                break;
            case R.id.programme:
                startActivity(new Intent(context, ProgrameActivity.class));
                break;
            case R.id.artiste:
                startActivity(new Intent(context, ArtistesActivity.class));
                break;
            case R.id.service:
                startActivity(new Intent(context, ServiceActivity.class));
                break;
            case R.id.Sponsor:
                startActivity(new Intent(context, SponsorActivity.class));
                break;
            case R.id.Quiz:
                startActivity(new Intent(context, JeuxActivity.class));
                break;
            case R.id.addamis:
                startActivity(new Intent(context, InviteAmisActivity.class));
                break;
            case R.id.info:
                startActivity(new Intent(context, InfoActivity.class));
                break;
            case R.id.Profile:
                startActivity(new Intent(context, ProfileActivity.class));
                break;
            case R.id.Deconnexion:
                startActivity(new Intent(context, LoginActivity.class));
                finishAffinity();
                break;
        }
        return false;
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(ViewPumpContextWrapper.wrap(newBase));
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            startActivity(new Intent(context, JeuxActivity.class));
        }
        return false;
    }
}
