package com.user.festivalbizerte.Utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

import com.user.festivalbizerte.R;

public class Helpers {
    public static boolean isConnected(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnected();
    }

    public static void ShowMessageConnection(Context context) {
        Toast.makeText(context, R.string.chek_internet, Toast.LENGTH_SHORT).show();
    }

}
