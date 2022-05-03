package com.huutocdai.barbershopapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseException;
//import com.google.firebase.appcheck.FirebaseAppCheck;
//import com.google.firebase.appcheck.safetynet.SafetyNetAppCheckProviderFactory;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import com.hbb20.CountryCodePicker;

import java.util.concurrent.TimeUnit;

public class Signin extends AppCompatActivity {

    private FirebaseAuth mAuth;
    EditText etxtPhoneNumber;
    CountryCodePicker ccp;
    Button DangKy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);
        mAuth = FirebaseAuth.getInstance();
        etxtPhoneNumber = findViewById(R.id.etxtPhoneNumber);
        DangKy = findViewById(R.id.btnSignin);
//        ccp = findViewById(R.id.ccp);
//        ccp.registerCarrierNumberEditText(etxtPhoneNumber);
        DangKy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!etxtPhoneNumber.getText().toString().trim().isEmpty()) {

                        Intent intent = new Intent(getApplicationContext(), OTP.class);
//                    intent.putExtra("phonenumber", ccp.getFullNumberWithPlus().replace("",""));
                        intent.putExtra("phonenumber",etxtPhoneNumber.getText().toString());
                        startActivity(intent);
                } else {
                    Toast.makeText(Signin.this, "Vui lòng nhập số điện thoại", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}