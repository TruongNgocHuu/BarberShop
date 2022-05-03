package com.huutocdai.barbershopapp.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.huutocdai.barbershopapp.Model.HairStyleOb;
import com.huutocdai.barbershopapp.R;

import java.util.ArrayList;

public class HairStyleAdapter extends RecyclerView.Adapter<HairStyleAdapter.ViewHolder> {
    ArrayList<HairStyleOb> lsHairStyle;
    Context context;

    public HairStyleAdapter(ArrayList<HairStyleOb> lsHairStyle, Context context) {
        this.lsHairStyle = lsHairStyle;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View convertview = layoutInflater.inflate(R.layout.custom_listview,parent,false);
        return new ViewHolder(convertview);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        HairStyleOb hairStyle = lsHairStyle.get(position);
        holder.ivHairStyle.setImageResource(hairStyle.getImg());
    }

    @Override
    public int getItemCount() {
        return lsHairStyle.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView ivHairStyle;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ivHairStyle = itemView.findViewById(R.id.ivHairStyle);
        }
    }
}
