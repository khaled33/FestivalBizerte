package com.user.festivalbizerte;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.drawee.generic.RoundingParams;
import com.facebook.drawee.view.SimpleDraweeView;
import com.google.gson.Gson;
import com.user.festivalbizerte.Adapter.ArtisteAdapter;
import com.user.festivalbizerte.Model.Artistes;
import com.user.festivalbizerte.Model.RSResponse;
import com.user.festivalbizerte.Model.UserInfos;
import com.user.festivalbizerte.Utils.Helpers;
import com.user.festivalbizerte.WebService.Urls;
import com.user.festivalbizerte.WebService.WebService;
import com.user.festivalbizerte.session.RSSession;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.github.inflationx.calligraphy3.CalligraphyConfig;
import io.github.inflationx.calligraphy3.CalligraphyInterceptor;
import io.github.inflationx.viewpump.ViewPump;
import io.github.inflationx.viewpump.ViewPumpContextWrapper;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ArtistesActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    @BindView(R.id.news_rv)
    RecyclerView NewsRecyclerview;
    @BindView(R.id.drawerLayout)
    DrawerLayout drawerLayout;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.navigation)
    NavigationView navigationView;
    ActionBarDrawerToggle actionBarDrawerToggle;
    ArtisteAdapter newsAdapter;
    List<Artistes> listArtiste = new ArrayList<>();
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_artistes);
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

        if (Helpers.isConnected(context)) {
            loadArtiste();
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

    private void loadArtiste() {
        Call<RSResponse> callUpload = WebService.getInstance().getApi().loadArtiste();
        callUpload.enqueue(new Callback<RSResponse>() {
            @Override
            public void onResponse(Call<RSResponse> call, Response<RSResponse> response) {
                if (response.body() != null) {
                    if (response.body().getStatus() == 1) {
                        Artistes[] tab = new Gson().fromJson(new Gson().toJson(response.body().getData()), Artistes[].class);
                        listArtiste = Arrays.asList(tab);

                        newsAdapter = new ArtisteAdapter(context, listArtiste);
                        NewsRecyclerview.setAdapter(newsAdapter);
                        NewsRecyclerview.setLayoutManager(new GridLayoutManager(context, getResources().getInteger(R.integer.artiste_item)));
                    } else if (response.body().getStatus() == 0) {
                        Toast.makeText(getApplicationContext(), "Pas de Artiste ", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<RSResponse> call, Throwable t) {
                Log.d("err", t.getMessage());
            }
        });
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(ViewPumpContextWrapper.wrap(newBase));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (actionBarDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);

    }

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


}
