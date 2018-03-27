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
import com.google.firebase.auth.FirebaseAuth;




public class PrincipalFragment extends Fragment {

    private FirebaseAuth autenticacao;
    private Button button;

    public PrincipalFragment() {
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment


        View view = inflater.inflate(R.layout.fragment_principal,container,false);

        //Montar listView e adapter

        button = (Button) view.findViewById(R.id.id_button_sair);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                autenticacao.getInstance().signOut();
                Intent intent = new Intent(getActivity(),LoginActivity.class);
                startActivity(intent);
            }
        });


        return view;

    }





}
