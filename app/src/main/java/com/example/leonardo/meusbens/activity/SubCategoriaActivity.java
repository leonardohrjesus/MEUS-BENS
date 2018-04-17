package com.example.leonardo.meusbens.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.leonardo.meusbens.R;
import com.example.leonardo.meusbens.config.BancoDados;
import com.example.leonardo.meusbens.model.Categoria;

/**
 * Created by Leonardo on 14/04/2018.
 */

public class SubCategoriaActivity extends AppCompatActivity{

    private Button botaoAdicionarSubCategoria;
    private EditText textoAdicionarSubCategoria;
    private String retornoCategoriaEspecifica;
    private BancoDados db = new BancoDados(this);



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.caixa_texto_categoria);

        receberActivity();

        botaoAdicionarSubCategoria = (Button) findViewById(R.id.botaoAdicionarSubCategoria);

        textoAdicionarSubCategoria  = (EditText) findViewById(R.id.categoriaCaixaCategoria);

        botaoAdicionarSubCategoria.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String subCategoria = textoAdicionarSubCategoria.getText().toString();
                   // textoAdicionarSubCategoria.setError("Este campo è obrigatorio");
                    db.addCategoria(new Categoria(retornoCategoriaEspecifica,subCategoria));


            }
        });


    }

    private void receberActivity() {
        Intent intent = getIntent();
        retornoCategoriaEspecifica =  intent.getStringExtra("categoriaEscolhida");
        Toast.makeText(this,retornoCategoriaEspecifica,Toast.LENGTH_LONG).show();

    }
}
