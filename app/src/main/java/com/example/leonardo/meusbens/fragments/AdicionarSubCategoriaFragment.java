package com.example.leonardo.meusbens.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.example.leonardo.meusbens.R;
import com.example.leonardo.meusbens.activity.ItemActivity;
import com.example.leonardo.meusbens.config.BancoDados;
import com.example.leonardo.meusbens.model.Categoria;
import com.example.leonardo.meusbens.util.PassadorDeInformacao;

/**
 * Created by Leonardo on 16/04/2018.
 */

public class AdicionarSubCategoriaFragment  extends DialogFragment implements PassadorDeInformacao {
    private String categoriaPrincipal;
    private EditText subCategoria;
    private Button botaoAdicionar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.caixa_texto_categoria, container, false);
        subCategoria = (EditText) rootView.findViewById(R.id.categoriaCaixaCategoria);
        botaoAdicionar = (Button) rootView.findViewById(R.id.botaoAdicionarSubCategoria);
        BancoDados db = new BancoDados(getContext());


        botaoAdicionar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String categoriaDetalhada = subCategoria.getText().toString();

                if ( categoriaDetalhada.isEmpty()){
                    subCategoria.setError("Este campo è obrigatorio");
                }else{
                    db.addCategoria(new Categoria(categoriaPrincipal,categoriaDetalhada));
                    subCategoria.setText("");
                    Intent intent = new Intent(getActivity(), ItemActivity.class);
                    intent.putExtra("categoriaEscolhida",categoriaPrincipal);
                    startActivity(intent);
                    Toast.makeText(getContext(),"Categoria Incluída com Sucesso!",Toast.LENGTH_SHORT).show();
                }
            }
        });


        return rootView;
    }

    @Override
    public  void passaInformacao(String informacao) {
        setCategoriaPrincipal(informacao);
        Log.d("Valor informacao",categoriaPrincipal);
        Log.d("Valor informacao",informacao);
    }

    public void setCategoriaPrincipal(String categoriaPrincipal) {
        this.categoriaPrincipal = categoriaPrincipal;
    }
}
