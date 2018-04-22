package com.example.leonardo.meusbens.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


import com.example.leonardo.meusbens.activity.LoginActivity;
import com.example.leonardo.meusbens.R;
import com.example.leonardo.meusbens.teste.MainActivity;
import com.google.firebase.auth.FirebaseAuth;




public class PrincipalFragment extends Fragment {

    private  Button botaoTesteMainActivity;

    public PrincipalFragment() {
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layoutListExpansivel for this fragment


        View view = inflater.inflate(R.layout.fragment_principal,container,false);

        botaoTesteMainActivity  = (Button) view.findViewById(R.id.botaoTesteMainAcivity);

        botaoTesteMainActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), MainActivity.class);
                startActivity(intent);
            }
        });

        //Montar listView e adapter



        return view;

    }





}
