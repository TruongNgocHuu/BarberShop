package com.huutocdai.barbershopapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.transition.Slide;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.huutocdai.barbershopapp.Adapter.SliderAdapter;
import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;

public class MenuService extends AppCompatActivity {
    SliderView slider;
    int [] image = {R.drawable.discount
            , R.drawable.addressshop
            , R.drawable.fanpage};
    FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_service);
        mAuth = FirebaseAuth.getInstance();
        slider= findViewById(R.id.image_slider);
        SliderAdapter sliderAdapter = new SliderAdapter(image);
        slider.setSliderAdapter(sliderAdapter);
        slider.setIndicatorAnimation(IndicatorAnimationType.WORM);
        slider.setSliderTransformAnimation(SliderAnimations.DEPTHTRANSFORMATION);
        slider.startAutoCycle();
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


    public void btnMap(View view) {
        if(isOnline()==true)
        {
            startActivity(new Intent(MenuService.this, MapsActivity.class));
        }
        else {
            Toast.makeText(getApplicationContext(), "Kiểm tra kết nối mạng", Toast.LENGTH_SHORT).show();
        }
    }

    public void btnPrice(View view) {
        if(isOnline()==true)
        {
            startActivity(new Intent(MenuService.this, Prices.class));
        }
        else {
            Toast.makeText(getApplicationContext(), "Kiểm tra kết nối mạng", Toast.LENGTH_SHORT).show();

        }

    }

    public void btnHairStyle(View view) {
        if(isOnline()==true)
        {
            startActivity(new Intent(MenuService.this, HairStyle.class));
        }
        else
        {
            Toast.makeText(getApplicationContext(), "Kiểm tra kết nối mạng", Toast.LENGTH_SHORT).show();
        }

    }

    public void Booking(View view) {
        if(isOnline()==true)
        {
            startActivity(new Intent(MenuService.this,Booking.class));
        }
        else
        {
            Toast.makeText(getApplicationContext(), "Kiểm tra kết nối mạng", Toast.LENGTH_SHORT).show();
        }

    }

    public void btnLogout(View view) {
        FirebaseAuth.getInstance().signOut();
        finish();
    }

    public void btnAbout(View view) {
        if(isOnline()==true)
        {
            startActivity(new Intent(MenuService.this,About.class));
        }
        else
        {
            Toast.makeText(getApplicationContext(), "Kiểm tra kết nối mạng", Toast.LENGTH_SHORT).show();
        }
    }
}