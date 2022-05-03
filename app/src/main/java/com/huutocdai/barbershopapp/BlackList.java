package com.huutocdai.barbershopapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.huutocdai.barbershopapp.Adapter.DoneListAdapter;
import com.huutocdai.barbershopapp.Model.BookingOb;

import java.util.ArrayList;

public class BlackList extends AppCompatActivity {
    ListView BlackList;
    ArrayList<BookingOb> list;
    DoneListAdapter adapter;
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    BookingOb bob = new BookingOb();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_black_list);
        BlackList = findViewById(R.id.lvBlackList);
        list = new ArrayList<>();
        CheckRole();
    }

    private void CheckRole() {
        if (mAuth.getCurrentUser().getPhoneNumber().equals("+84828468260")) {
            db.collection("BlackList").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    if (!task.getResult().isEmpty()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            bob = new BookingOb();
                            bob.setName("Tên: " + document.get("Name").toString());
                            bob.setPhoneNumber("Số điện thoại: " + document.get("PhoneNumber").toString());
                            bob.setTime("Thời gian: " + document.get("Time").toString());
                            bob.setDate("Ngày đặt: " + document.get("Date").toString());
                            list.add(bob);
                        }
                        adapter = new DoneListAdapter(getApplicationContext(), R.layout.custom_done_list_layout, list);
                        BlackList.setAdapter(adapter);
                        adapter.notifyDataSetChanged();
                    } else {
                        Toast.makeText(getApplicationContext(), "", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
        else if (!mAuth.getCurrentUser().getPhoneNumber().equals("+84828468260")) {
            db.collection("BlackList").document(mAuth.getCurrentUser().getPhoneNumber()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {

                        bob = new BookingOb();
                        bob.setName("Tên: " + document.get("Name").toString());
                        bob.setPhoneNumber("Số điện thoại: " + document.get("PhoneNumber").toString());
                        bob.setTime("Thời gian: " + document.get("Time").toString());
                        bob.setDate("Ngày đặt: " + document.get("Date").toString());
                        list.add(bob);

                        adapter = new DoneListAdapter(getApplicationContext(), R.layout.custom_done_list_layout, list);
                        BlackList.setAdapter(adapter);
                        adapter.notifyDataSetChanged();
                    } else {
                        Toast.makeText(getApplicationContext(), "", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }
}