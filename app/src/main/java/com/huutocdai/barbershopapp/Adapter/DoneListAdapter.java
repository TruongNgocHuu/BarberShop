package com.huutocdai.barbershopapp.Adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.huutocdai.barbershopapp.Booking;
import com.huutocdai.barbershopapp.Model.BookingOb;
import com.huutocdai.barbershopapp.R;

import java.util.List;
import java.util.Map;

public class DoneListAdapter extends ArrayAdapter<BookingOb> {
    Context context;
    int resource;
    List<BookingOb> objects;
    boolean save;
    int count = 0;
    int dem = 0;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    public static int RefuseState = 0;

    public DoneListAdapter(@NonNull Context context, int resource, @NonNull List<BookingOb> objects) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
        this.objects = objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        convertView = LayoutInflater.from(context).inflate(R.layout.custom_done_list_layout, parent, false);
        TextView Name = convertView.findViewById(R.id.txtName);
        TextView PhoneNumber = convertView.findViewById(R.id.txtPhoneNumber);
        TextView Date = convertView.findViewById(R.id.txtDate);
        TextView Time = convertView.findViewById(R.id.txtTime);
        BookingOb ob = objects.get(position);
        Name.setText(ob.getName());
        PhoneNumber.setText(ob.getPhoneNumber());
        Date.setText(ob.getDate());
        Time.setText(ob.getTime());
        return convertView;
    }
}
