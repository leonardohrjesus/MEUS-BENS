package com.example.leonardo.meusbens.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.leonardo.meusbens.R;
import com.example.leonardo.meusbens.activity.ListaCategoriaActivity;
import com.example.leonardo.meusbens.teste.TesteActivity;


public class CategoriaFragment extends Fragment {

    private ImageView imoveisImageView;
    private ImageView veiculosImageView;
    private ImageView vestimentosImageView;
    private ImageView eletronicosImageView;
    private ImageView outrosImageView;

    private  final String imoves = "imoveis";
    private  final String veiculos = "veiculos";
    private  final String vestimentos = "vestimentos";
    private  final String eletronicos = "eletronicos";
    private  final String outros = "outros";

    public CategoriaFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layoutListExpansivel for this fragment


        View view = inflater.inflate(R.layout.fragment_categoria,container,false);
    /**************************************************************************
    * Definicao findview
    *
    **************************************************************************/

        imoveisImageView = (ImageView) view.findViewById(R.id.imageButtonImoveis);
        veiculosImageView = (ImageView) view.findViewById(R.id.imageButtonVeiculo);
        vestimentosImageView  = (ImageView) view.findViewById(R.id.imageButtonVestimentos);
        eletronicosImageView = (ImageView) view.findViewById(R.id.imageButtontEletronicos);
        outrosImageView = (ImageView) view.findViewById(R.id.imageButtonOutros);

        imoveisImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                irParaCategoriaEspecifica(imoves);
            }
        });


        veiculosImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                irParaCategoriaEspecifica(veiculos);
            }
        });

        vestimentosImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                irParaCategoriaEspecifica(vestimentos);
            }
        });

        eletronicosImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                irParaCategoriaEspecifica(eletronicos);
            }
        });

        outrosImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                irParaCategoriaEspecifica(outros);
            }
        });
        return view;

    }


    private void irParaCategoriaEspecifica(String categoriaEspecifica) {

            Intent intent = new Intent(getActivity(), ListaCategoriaActivity.class);
        //        intent.putExtra("categoriaEscolhida",categoriaEspecifica);
            startActivity(intent);


    }


}
