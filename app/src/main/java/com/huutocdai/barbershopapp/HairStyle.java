package com.huutocdai.barbershopapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.huutocdai.barbershopapp.Adapter.HairStyleAdapter;
import com.huutocdai.barbershopapp.Model.HairStyleOb;

import java.util.ArrayList;

public class HairStyle extends AppCompatActivity {
    HairStyleAdapter adapter;
    RecyclerView rcHongKong, rcEU,rcKorea;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hair_style);
        rcHongKong = findViewById(R.id.rcHongKong);
        rcEU = findViewById(R.id.rcEU);
        rcKorea = findViewById(R.id.rcKorea);
        HongKonghair();
        EUhair();
        Koreahair();
    }

    private void Koreahair() {
        LinearLayoutManager layoutManager= new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL, false);
        rcKorea.setLayoutManager(layoutManager);
        ArrayList<HairStyleOb> lsHairStyle = new ArrayList<>();;
        lsHairStyle.add(new HairStyleOb(R.drawable.koreahair1));
        lsHairStyle.add(new HairStyleOb(R.drawable.gdhair));
        lsHairStyle.add(new HairStyleOb(R.drawable.gdhair1));
        lsHairStyle.add(new HairStyleOb(R.drawable.gdhair2));
        lsHairStyle.add(new HairStyleOb(R.drawable.gdhair3));
        adapter = new HairStyleAdapter(lsHairStyle,getApplicationContext());
        rcKorea.setAdapter(adapter);
    }

    private void HongKonghair() {
        LinearLayoutManager layoutManager= new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL, false);
        rcHongKong.setLayoutManager(layoutManager);
        ArrayList<HairStyleOb> lsHairStyle = new ArrayList<>();;
        lsHairStyle.add(new HairStyleOb(R.drawable.luuduchoa));
        lsHairStyle.add(new HairStyleOb(R.drawable.truonghochuu));
        lsHairStyle.add(new HairStyleOb(R.drawable.quachphuthanh));
        lsHairStyle.add(new HairStyleOb(R.drawable.quachphuthanh2));
        adapter = new HairStyleAdapter(lsHairStyle,getApplicationContext());
        rcHongKong.setAdapter(adapter);
    }
    private void EUhair() {
        LinearLayoutManager layoutManager= new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL, false);
        rcEU.setLayoutManager(layoutManager);
        ArrayList<HairStyleOb> lsHairStyle = new ArrayList<>();;
        lsHairStyle.add(new HairStyleOb(R.drawable.beckham));
        lsHairStyle.add(new HairStyleOb(R.drawable.ronaldo));
        lsHairStyle.add(new HairStyleOb(R.drawable.justin));
        lsHairStyle.add(new HairStyleOb(R.drawable.manbun));
        lsHairStyle.add(new HairStyleOb(R.drawable.europe));
        adapter = new HairStyleAdapter(lsHairStyle,getApplicationContext());
        rcEU.setAdapter(adapter);
    }
}