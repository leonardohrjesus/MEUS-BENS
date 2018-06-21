package com.example.leonardo.meusbens.activity;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ExpandableListView;
import android.widget.Toast;

import com.example.leonardo.meusbens.R;
import com.example.leonardo.meusbens.config.BancoDados;
import com.example.leonardo.meusbens.model.Categoria;
import com.example.leonardo.meusbens.model.Item;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ListaCategoriaActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private String retornoCategoriaEspecifica;
    private com.example.leonardo.meusbens.teste.Adaptador adaptador;
    //   private ExpandableListView listaExpansivel;
    BancoDados db = new BancoDados(this);



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_categoria);
        /**************************************************************************
         * configurar Toolbar
         *
         **************************************************************************/
        toolbar = (Toolbar) findViewById(R.id.toolbalistacadastro);
        setSupportActionBar(toolbar);
        //setContentView(R.layout.layoutlistexpansivel);
        RecebeCategoria();
        ExpandableListView listaExpansivel= (ExpandableListView) findViewById(R.id.listaexpansivel);

        List<Categoria> categorias = db.listaTodasCategorias();
        List<String> lstGrupos = new ArrayList<>();
        HashMap<String, List<Item>> lstItensGrupo = new HashMap<>();
        int contadorCategoria = 0;
        int contadorItems = 0;
        String categoriaAuxiliar="";
        List<Item> listaItem = null ;
        for (Categoria c : categorias ) {
            Log.e("Rei android",c.getSubCategoria());
/*
            if (c.getSubCategoria()!= categoriaAuxiliar) {
            }
*/
            List<Item> listaItemRetorno = db.listarTodoItemDeUmaCategoria(String.valueOf(c.getSubCategoria()),retornoCategoriaEspecifica);
            for (Item item : listaItemRetorno) {
                if(categoriaAuxiliar != c.getSubCategoria()){
                    lstGrupos.add(c.getSubCategoria());
                    listaItem = new ArrayList<>();
                    categoriaAuxiliar = c.getSubCategoria();
                }
                listaItem.add(new Item(item.getDescricao(), item.getValor()));
                lstItensGrupo.put(lstGrupos.get(contadorCategoria), listaItem);
                contadorItems++;
            }
            contadorCategoria++;
        }

        adaptador = new com.example.leonardo.meusbens.teste.Adaptador(this, lstGrupos, lstItensGrupo);
        //adaptador = new com.example.leonardo.meusbens.teste.Adaptador(this, lstGrupos,lstItensGrupo, retornoCategoriaEspecifica);

        // define o apadtador do ExpandableListView
        listaExpansivel.setAdapter(adaptador);

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
