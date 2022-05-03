package com.huutocdai.barbershopapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.huutocdai.barbershopapp.Fragment.bookingfragment;
import com.huutocdai.barbershopapp.Fragment.managefragment;
import com.huutocdai.barbershopapp.Fragment.oderlistfragment;

public class Booking extends AppCompatActivity {
    public static String Role = "User";
    BottomNavigationView bottom_navigation;
    FragmentTransaction transaction;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking);
        bottom_navigation = findViewById(R.id.bottom_navigation);
        onStart();
        setBottomNavigation();
    }
    private void setBottomNavigation() {
        bottom_navigation.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment select = null;
                switch (item.getItemId())
                {
                    case R.id.nav_bookingdate:
                        select = new bookingfragment();
                        break;
                    case R.id.nav_checkbook:
                        select = new oderlistfragment();
                        break;
                    case R.id.nav_manage:
                        select = new managefragment();
                        break;
                }
                getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout,select).commit();
                return true;
            }
        });
    }
    @Override
    protected void onStart() {
        super.onStart();
        transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frameLayout, new bookingfragment());
        transaction.commit();
    }
}