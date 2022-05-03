package com.huutocdai.barbershopapp.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.huutocdai.barbershopapp.Booking;
import com.huutocdai.barbershopapp.Model.BookingOb;
import com.huutocdai.barbershopapp.R;

import java.util.List;

public class TimeAdapter extends ArrayAdapter<BookingOb> {
    Context context;
    int resource;
    List<BookingOb> objects;
    public TimeAdapter(@NonNull Context context, int resource, @NonNull List<BookingOb> objects) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
        this.objects = objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        convertView = LayoutInflater.from(context).inflate(R.layout.custom_layout_time,parent,false);
        TextView txtTime = convertView.findViewById(R.id.txtTime);
        BookingOb bob = objects.get(position);
        txtTime.setText(bob.getTime());
        //// 1 là có người đặt
        ///// 0 là đang chọn
//        convertView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                objects.get(position).setState("0");
//                notifyDataSetChanged();
//            }
//        });
        // 1 co nguoi dat
        // 0 dang chon
        if(bob.getState()=="1")
        {
            txtTime.setBackgroundColor(Color.parseColor("#D3D3D3"));
//           txtTime.setEnabled(false);
        }
        else if(bob.getState()=="0")
        {
            txtTime.setBackgroundColor(Color.parseColor("#0000FF"));
        }else if(bob.getState()=="2"){
            txtTime.setBackgroundResource(R.drawable.border_time);
        }

        //xet trong ds acceptlist
        notifyDataSetChanged();
        return convertView;
    }
}
