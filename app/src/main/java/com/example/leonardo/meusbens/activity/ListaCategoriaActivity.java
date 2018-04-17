package com.example.leonardo.meusbens.activity;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ExpandableListView;
import android.widget.Toast;

import com.example.leonardo.meusbens.R;
import com.example.leonardo.meusbens.adapter.Adaptador;
import com.example.leonardo.meusbens.model.Produto;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ListaCategoriaActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private String retornoCategoriaEspecifica;
    private ExpandableListView elvCompra;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_categoria);

        RecebeCategoria();

        /**************************************************************************
         * configurar Toolbar
         *
         **************************************************************************/
        toolbar = (Toolbar) findViewById(R.id.toolbalistacadastro);
        setSupportActionBar(toolbar);


        ExpandableListView elvCompra = (ExpandableListView) findViewById(R.id.elvCompra);

        List<String> lstGrupos = new ArrayList<>();
        lstGrupos.add("Doces");
        lstGrupos.add("Legumes");
        lstGrupos.add("Outros");

        // cria os itens de cada grupo
        List<Produto> lstDoces = new ArrayList<>();
        lstDoces.add(new Produto("Pacote de bala", 4.5));
        lstDoces.add(new Produto("Pacote de chiclete", 3.5));
        lstDoces.add(new Produto("Bolo de chocolate", 50.0));

        List<Produto> lstLegumes = new ArrayList<>();
        lstLegumes.add(new Produto("Alface", 0.5));
        lstLegumes.add(new Produto("Tomate", 2.5));

        List<Produto> lstProdutos = new ArrayList<>();
        lstProdutos.add(new Produto("Chave de Fenda", 7.5));

        // cria o "relacionamento" dos grupos com seus itens
        HashMap<String, List<Produto>> lstItensGrupo = new HashMap<>();
        lstItensGrupo.put(lstGrupos.get(0), lstDoces);
        lstItensGrupo.put(lstGrupos.get(1), lstLegumes);
        lstItensGrupo.put(lstGrupos.get(2), lstProdutos);

        // cria um adaptador (BaseExpandableListAdapter) com os dados acima
        com.example.leonardo.meusbens.teste.Adaptador adaptador = new com.example.leonardo.meusbens.teste.Adaptador(this, lstGrupos, lstItensGrupo);
        // define o apadtador do ExpandableListView
        elvCompra.setAdapter(adaptador);


    }


    private void RecebeCategoria(){
        Intent intent = getIntent();
        retornoCategoriaEspecifica =  intent.getStringExtra("categoriaEscolhida");
        Toast.makeText(this,retornoCategoriaEspecifica,Toast.LENGTH_LONG).show();
    }

    @Override
    public  boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_lista_cadastro,menu);
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_adicionar:
                irParaItem();
                return  true;

            default:
                return super.onOptionsItemSelected(item);
        }

    }

    private void irParaItem() {
        Intent intent = new Intent(ListaCategoriaActivity.this,ItemActivity.class);
        intent.putExtra("categoriaEscolhida",retornoCategoriaEspecifica);

        startActivity(intent);

    }


}
