package com.example.leonardo.meusbens.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import com.example.leonardo.meusbens.R;

public class ListaCategoriaActivity extends AppCompatActivity {

    private String retornoCategoriaEspecifica;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_categoria);

        RecebeCategoria();


    }

    private void RecebeCategoria(){
        Intent intent = getIntent();

        retornoCategoriaEspecifica =  intent.getStringExtra("categoriaEscolhida");

        Toast.makeText(this,retornoCategoriaEspecifica,Toast.LENGTH_LONG).show();

    }



}
