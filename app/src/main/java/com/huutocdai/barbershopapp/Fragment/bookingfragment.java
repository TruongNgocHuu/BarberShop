package com.huutocdai.barbershopapp.Fragment;

import static android.content.ContentValues.TAG;

import android.Manifest;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.huutocdai.barbershopapp.Adapter.TimeAdapter;
import com.huutocdai.barbershopapp.Booking;
import com.huutocdai.barbershopapp.Model.BookingOb;
import com.huutocdai.barbershopapp.R;
import com.huutocdai.barbershopapp.Signin;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class bookingfragment extends Fragment {
    public static boolean clause = false;
    FirebaseFirestore db;
    FirebaseAuth mAuth;
    EditText test, etxtName;
    TextView etxtDate;
    Button btnLay;
    DatePickerDialog.OnDateSetListener setListener;
    PhoneAuthProvider.ForceResendingToken mResendToken;
    PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;
    Context context;
    GridView gvTime;
    TimeAdapter timeAdapter;
    ArrayList<BookingOb> listBooking;
    BookingOb bob;
    Button btnDatLich;
    ArrayList<BookingOb>listGio;
    boolean check = false;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_booking_layout,container,false);
        etxtDate = view.findViewById(R.id.etxtDate);
        // set ngay mac dinh
        etxtName = view.findViewById(R.id.etxtName);
        gvTime = view.findViewById(R.id.gvTime);
        btnDatLich = view.findViewById(R.id.btnDatLich);
        db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
        bob = new BookingOb();
        listGio = new ArrayList<>();
        CheckState();
        BookingAddTime();
        Calendar();
        ClickTime();
        setGrid(etxtDate.getText().toString());
        DatLich();
        timeAdapter.notifyDataSetChanged();
        return view;
    }

    private boolean daChonGio()
    {
        timeAdapter.notifyDataSetChanged();
        boolean isSelected = false;
        for (int i=0; i<listBooking.size();i++ )
        {
            if(listBooking.get(i).getState().equals("0"))
            {
                isSelected = true;
            }
        }
        return isSelected;
    }
    private void DatLich() {
        btnDatLich.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    if(!etxtDate.getText().toString().isEmpty() && daChonGio() && !etxtName.getText().toString().isEmpty())
                    {
                        if(mAuth.getCurrentUser()!=null)
                        {
                            bob.setPhoneNumber(mAuth.getCurrentUser().getPhoneNumber());
                        }
                        bob.setName(etxtName.getText().toString());
                        bob.setDate(etxtDate.getText().toString());
                        db.collection("users")
                          .document(mAuth.getCurrentUser().getPhoneNumber())
                          .set(bob.toMap()).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                clause=true;
                                Toast.makeText(getContext(), "Đã tạo được lịch", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                            }
                        });
                    }
                    else
                    {
                        Toast.makeText(getContext(), "Bạn vui lòng chọn ngày + giờ", Toast.LENGTH_LONG).show();
                    }
                    timeAdapter.notifyDataSetChanged();
            }
        });
    }
    private void ClickTime() {
        timeAdapter.notifyDataSetChanged();
        gvTime.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                for (int i=0; i<listBooking.size();i++ )
                {
                    if(listBooking.get(i).getState().equals("0")){
                        listBooking.get(i).setState("2");
                        timeAdapter.notifyDataSetChanged();
                    }
                }
                if(listBooking.get(position).getState()!="1"){
                    listBooking.get(position).setState("0");
                    timeAdapter.notifyDataSetChanged();
                }
                bob.setTime(listBooking.get(position).getTime());
                setGrid(etxtDate.getText().toString());
                timeAdapter.notifyDataSetChanged();
                if(Booking.Role=="Admin")
                {
                    //mAuth.getCurrentUser().getPhoneNumber().equals("+84828468260");

                    if(listBooking.get(position).getState().equals("1")){
                        for (int i =0; i<listGio.size(); i++){
                            if(listBooking.get(position).getTime().equals(listGio.get(i).getTime())){
                                Toast.makeText(getContext(), listGio.get(i).getTime()+" "+listGio.get(i).getPhoneNumber(), Toast.LENGTH_SHORT).show();
                                Call(listGio.get(i).getPhoneNumber());
                            }
                        }
                    }
                }

            }
        });
    }
    private void Call(String sdt){
        Uri uri = Uri.parse("tel: "+ sdt);
        Intent intent = new Intent(Intent.ACTION_CALL);
        intent.setData(uri);
        if(ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions((Activity) getContext(), new String[]{Manifest.permission.CALL_PHONE},1235);
            return;
        }
        getContext().startActivity(intent);
    }
    private void setGrid(String ngay){
        timeAdapter.notifyDataSetChanged();
        db.collection("users").whereEqualTo("Date", ngay)
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                db.collection("users").whereEqualTo("Date", ngay)
                        .addSnapshotListener(new EventListener<QuerySnapshot>() {
                            @Override
                            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                               listGio.clear();
                                for (QueryDocumentSnapshot doc : value) {
                                    if (doc.get("Time") != null) {
                                        listGio.add(new BookingOb(doc.getString("Name"), doc.getString("PhoneNumber"),doc.getString("Time")) );
                                    }
                                }
                                for (int i = 0; i< listBooking.size(); i++)
                                {
                                    for (int j =0 ; j< listGio.size(); j++)
                                    {
                                        if(listBooking.get(i).getTime().equals(listGio.get(j).getTime().toString()))
                                        {
                                            listBooking.get(i).setState("1");
                                            timeAdapter.notifyDataSetChanged();
                                        }
                                        else if(!listBooking.get(i).getState().equals("1") && !listBooking.get(i).getState().equals("0"))
                                        {
                                            listBooking.get(i).setState("2");
                                            timeAdapter.notifyDataSetChanged();
                                        }

                                        Log.e(TAG, listGio.get(j).getTime()+ ", ");
                                        timeAdapter.notifyDataSetChanged();
                                    }
                                }
                                                //////////////////////
                                if (listGio.size()<1 && check)
                                {
                                    for (int i=0; i<listBooking.size();i++)
                                    {
                                        listBooking.get(i).setState("2");
                                        timeAdapter.notifyDataSetChanged();
                                    }
                                    check = false;
                                }
                            }
                        });
            }
        });
    }

    private void BookingAddTime() {
        BookingOb bo1 = new BookingOb("6:00","2");
        BookingOb bo2 = new BookingOb("7:00","2");
        BookingOb bo3 = new BookingOb("8:00","2");
        BookingOb bo4 = new BookingOb("9:00","2");
        BookingOb bo5 = new BookingOb("10:00","2");
        BookingOb bo6 = new BookingOb("11:00","2");
        BookingOb bo7 = new BookingOb("13:00","2");
        BookingOb bo8 = new BookingOb("14:00","2");
        BookingOb bo9 = new BookingOb("15:00","2");
        BookingOb bo10 = new BookingOb("16:00","2");
        BookingOb bo11 = new BookingOb("17:00","2");
        listBooking = new ArrayList<>();
        listBooking.add(bo1);
        listBooking.add(bo2);
        listBooking.add(bo3);
        listBooking.add(bo4);
        listBooking.add(bo5);
        listBooking.add(bo6);
        listBooking.add(bo7);
        listBooking.add(bo8);
        listBooking.add(bo9);
        listBooking.add(bo10);
        listBooking.add(bo11);
        timeAdapter = new TimeAdapter(getContext(),R.layout.custom_layout_time,listBooking);
        gvTime.setAdapter(timeAdapter);
        timeAdapter.notifyDataSetChanged();
    }
    private void CheckState() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user==null)
        {
            AlertDialog.Builder thongBao = new AlertDialog.Builder(getContext());
            thongBao.setMessage("Bạn chưa có tài khoản, bạn sẽ được đưa đến trang đăng ký")
                    .setPositiveButton("Chấp nhận", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            startActivity(new Intent(getContext(), Signin.class));
                        }
                    });
            AlertDialog hienthi = thongBao.create();
            hienthi.show();
        }
    }
    private void Calendar() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        etxtDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClickTime();
                DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int day) {
                        Toast.makeText(getContext(), "Reload", Toast.LENGTH_SHORT).show();
                        month= month+1;
                        String date = day+"/"+month+"/"+year;
                        etxtDate.setText(date);
                        bob.setDate(etxtDate.getText().toString());
                        setGrid(etxtDate.getText().toString());
                    }
                },year,month,day);
                timeAdapter.notifyDataSetChanged();
                datePickerDialog.show();
                check = true;
            }
        });

    }
}
