package com.huutocdai.barbershopapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


        public boolean isOnline() {
            ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo netInfo = cm.getActiveNetworkInfo();
            if (netInfo != null && netInfo.isConnectedOrConnecting()) {
                return true;
            } else {
                return false;
            }
        }


    public void onClickMainService(View view) {
        if (isOnline()==true)
        {
            Intent intent = new Intent(MainActivity.this,MenuService.class);
            startActivity(intent);
        }
        else
        {
            Toast.makeText(getApplicationContext(), "Vui long check Internet", Toast.LENGTH_SHORT).show();
        }

    }
}