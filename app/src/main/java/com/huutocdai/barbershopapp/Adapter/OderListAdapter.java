package com.huutocdai.barbershopapp.Adapter;

import static android.content.ContentValues.TAG;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.huutocdai.barbershopapp.Booking;
import com.huutocdai.barbershopapp.Model.BookingOb;
import com.huutocdai.barbershopapp.R;
import com.huutocdai.barbershopapp.Signin;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OderListAdapter extends ArrayAdapter<BookingOb> {
     Context context;
     int resource;
     List<BookingOb> objects;
    int count = 0;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    public OderListAdapter(@NonNull Context context, int resource, @NonNull List<BookingOb> objects) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
        this.objects = objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        convertView = LayoutInflater.from(context).inflate(R.layout.custom_oder_list_layout,parent,false);
        TextView Name = convertView.findViewById(R.id.txtName);
        TextView PhoneNumber = convertView.findViewById(R.id.txtPhoneNumber);
        TextView Date = convertView.findViewById(R.id.txtDate);
        TextView Time = convertView.findViewById(R.id.txtTime);
        ImageButton Accept = convertView.findViewById(R.id.imgbtnAccept);
        ImageButton Refuse = convertView.findViewById(R.id.imgbtnRefuse);
        BookingOb ob = objects.get(position);
        Name.setText(ob.getName());
        PhoneNumber.setText(ob.getPhoneNumber());
        Date.setText(ob.getDate());
        Time.setText(ob.getTime());
        if(Booking.Role.equals("Admin"))
        {
            /// admin Role
            Accept.setVisibility(View.VISIBLE);
            Accept.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder thongBao = new AlertDialog.Builder(getContext());
                    thongBao.setTitle("Yêu cầu xác nhận");
                    thongBao.setMessage("Bạn đã hoàn thành khách hàng này")
                            .setNegativeButton("Đã xong", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    AcceptDocument();
                                   // AcceptDocument(ob.toMap());
                                    notifyDataSetChanged();
                                }
                            })
                            .setPositiveButton("Chưa", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Toast.makeText(getContext(), "Cancel", Toast.LENGTH_SHORT).show();
                                    dialog.cancel();
                                }
                            });
                    AlertDialog hienthi = thongBao.create();
                    hienthi.show();
                }
                private void AcceptDocument()
                {
                    db.collection("DoneList").document(ob.getPhoneNumber())
               .set(ob.toMap()).addOnCompleteListener(new OnCompleteListener<Void>() {
           @Override
           public void onComplete(@NonNull Task<Void> task)
                    {
                                DeleteDocument(ob.getPhoneNumber()); }
                    });
                }
            });
            Refuse.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder thongBao = new AlertDialog.Builder(getContext());
                    thongBao.setTitle("Yêu cầu xác nhận");
                    thongBao.setMessage("Bạn muốn xóa lịch đặt này")
                            .setNegativeButton("Xóa", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    RefuseDocument();
                                    DeleteDocument(ob.getPhoneNumber());
                                    notifyDataSetChanged();
                                }
                            })
                    .setPositiveButton("Hủy", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Toast.makeText(getContext(), "Cancel", Toast.LENGTH_SHORT).show();
                            dialog.cancel();
                        }
                    });
                    AlertDialog hienthi = thongBao.create();
                    hienthi.show();
                }
                private void RefuseDocument() {
                    db.collection("RefuseList")
                            .document(ob.getPhoneNumber())
                            .set(ob.toMap()).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                        }
                    })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                }
                            });
                }
            });

        }
                        /////////////////////User////////////////////////
        else
        {
            // User Role
            Refuse.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder thongBao = new AlertDialog.Builder(getContext());
                    thongBao.setTitle("Yêu cầu xác nhận");
                    thongBao.setMessage("Bạn muốn xóa lịch đặt này")
                            .setNegativeButton("Xóa", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    RefuseDocument();
                                    DeleteDocument(ob.getPhoneNumber());
                                    notifyDataSetChanged();
                                }
                            })
                            .setPositiveButton("Hủy", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Toast.makeText(getContext(), "Cancel", Toast.LENGTH_SHORT).show();
                                    dialog.cancel();
                                }
                            });
                    AlertDialog hienthi = thongBao.create();
                    hienthi.show();
                }
                private void RefuseDocument() {
                        db.collection("RefuseList")
                                .document(ob.getPhoneNumber())
                                .set(ob.toMap()).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                notifyDataSetChanged();
                            }
                        })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                    }
                                });
                }
            });
            count = count + 1;
            if(count >= 2)
            {
                BlackList(ob.toMap());
            }
            notifyDataSetChanged();
        }
        return convertView;
    }
    private void BlackList(Map<String, Object> toMap) {
        db.collection("BlackList")
                .document(mAuth.getCurrentUser().getPhoneNumber())
                .set(toMap).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
            }
        })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                    }
                });
    }
    private void DeleteDocument(String document)
    {
        db.collection("users").document(document)
                .delete()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(getContext(), "Thành Công", Toast.LENGTH_SHORT).show();
                        notifyDataSetChanged();
                        Log.d("DELETE", "DocumentSnapshot successfully deleted!");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("FAILED DELETE", "Error deleting document", e);
                    }
                });
    }
}
