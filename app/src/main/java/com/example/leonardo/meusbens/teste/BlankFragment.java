package com.example.leonardo.meusbens.teste;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.leonardo.meusbens.R;
import com.example.leonardo.meusbens.config.BancoDados;
import com.example.leonardo.meusbens.model.Categoria;


public class BlankFragment extends DialogFragment {

    private Button botaoAdicionarSubCategoria;
    private EditText textoAdicionarSubCategoria;
    private String retornoCategoriaEspecifica;
    private BancoDados db = new BancoDados(getContext());


    public BlankFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_blank,container,false);
        getDialog().setTitle("Simple Dialog");

        return view;

        //  return inflater.inflate(R.layout.fragment_blank, container, false);

//        botaoAdicionarSubCategoria = (Button)  view.findViewById(R.id.botaoAdicionarSubCategoria);
//
//        textoAdicionarSubCategoria  = (EditText) view.findViewById(R.id.categoriaCaixaCategoria);
//
//        botaoAdicionarSubCategoria.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                String subCategoria = textoAdicionarSubCategoria.getText().toString();
//                // textoAdicionarSubCategoria.setError("Este campo è obrigatorio");
//                db.addCategoria(new Categoria(retornoCategoriaEspecifica,subCategoria));
//
//
//            }
//        });

    }


}
