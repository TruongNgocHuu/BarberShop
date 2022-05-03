package com.huutocdai.barbershopapp.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.huutocdai.barbershopapp.BlackList;
import com.huutocdai.barbershopapp.Done;
import com.huutocdai.barbershopapp.R;

public class managefragment extends Fragment {
    Button btnDoneList, btnBlackList;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_manage,container,false);
        btnDoneList = view.findViewById(R.id.btnDoneList);
        btnBlackList = view.findViewById(R.id.btnBlackList);
        btnDoneList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), Done.class));
            }
        });
        btnBlackList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), BlackList.class));
            }
        });
        return view;
    }

}
