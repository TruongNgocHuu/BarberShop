package com.huutocdai.barbershopapp.Fragment;

import static android.content.ContentValues.TAG;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.huutocdai.barbershopapp.Adapter.OderListAdapter;
import com.huutocdai.barbershopapp.Booking;
import com.huutocdai.barbershopapp.Model.BookingOb;
import com.huutocdai.barbershopapp.R;
import com.huutocdai.barbershopapp.Signin;

import java.util.ArrayList;

public class oderlistfragment extends Fragment {
    ListView OderList;
    FirebaseAuth mAuth;
    FirebaseFirestore db;
    ArrayList<BookingOb>listOderDate;
    BookingOb bob;
    OderListAdapter Oderlistadapter;
    Context context;
    String admin;
    FragmentTransaction transaction;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_oderlist,container,false);
        OderList = view.findViewById(R.id.lvOderList);
        context = view.getContext();
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        listOderDate = new ArrayList<>();
        admin = "+84828468260";
        bob = new BookingOb();
        FirebaseListener();
        return view;
    }
    private void getAllOderBookingDate() {
            if(mAuth.getCurrentUser().getPhoneNumber().contains(admin))
            {
                ////Admin
                Booking.Role="Admin";
                listOderDate.clear();
                db.collection("users").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if(!task.getResult().isEmpty())
                            {
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    bob = new BookingOb();
                                    bob.setName(document.get("Name").toString());
                                    bob.setPhoneNumber(document.get("PhoneNumber").toString());
                                    bob.setTime(document.get("Time").toString());
                                    bob.setDate(document.get("Date").toString());
                                    listOderDate.add(bob);
                                    // list.add(document.get("NameProduct")+""+document.get("PriceProduct"));
                                }
                                Oderlistadapter = new OderListAdapter(context,
                                        R.layout.custom_oder_list_layout,
                                        listOderDate);
                                OderList.setAdapter(Oderlistadapter);
                                Oderlistadapter.notifyDataSetChanged();
                            }
                            else
                            {
                                Log.d(TAG, "Document does not exist!");
                            }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                    }
                });
            }
            else if(!mAuth.getCurrentUser().getPhoneNumber().contains(admin))
            {
                listOderDate.clear();
                Booking.Role="User";
                DocumentReference docIdRef = db.collection("users").document(mAuth.getCurrentUser().getPhoneNumber());
                docIdRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                            DocumentSnapshot document = task.getResult();
                            if (document.exists()) {
                                bob = new BookingOb();
                                bob.setName(document.get("Name").toString());
                                bob.setPhoneNumber(document.get("PhoneNumber").toString());
                                bob.setTime(document.get("Time").toString());
                                bob.setDate(document.get("Date").toString());
                                listOderDate.add(bob);
                                Oderlistadapter = new OderListAdapter(context,
                                        R.layout.custom_oder_list_layout,
                                        listOderDate);
                                OderList.setAdapter(Oderlistadapter);
                                Oderlistadapter.notifyDataSetChanged();
                                Log.d(TAG, "Document exists!");
                            } else {
                                Log.d(TAG, "Document does not exist!");
                            }
                    }
                });
            }
    }
    private void FirebaseListener() {
            db.collection("users")
                    .addSnapshotListener(new EventListener<QuerySnapshot>() {
                        @Override
                        public void onEvent(@Nullable QuerySnapshot snapshots,
                                            @Nullable FirebaseFirestoreException e) {
                            if (e != null) {
                                Log.w(TAG, "listen:error", e);
                                Oderlistadapter.notifyDataSetChanged();
                                return;
                            }
                            getAllOderBookingDate();
                        }
                    });
    }
}
