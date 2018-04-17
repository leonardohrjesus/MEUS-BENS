package com.example.leonardo.meusbens.activity;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.FragmentManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;
import com.example.leonardo.meusbens.R;
import com.example.leonardo.meusbens.config.BancoDados;
import com.example.leonardo.meusbens.fragments.AdicionarSubCategoriaFragment;
import com.example.leonardo.meusbens.teste.AlertDFragment;
import com.example.leonardo.meusbens.teste.BlankFragment;
import com.example.leonardo.meusbens.teste.DFragment;

public class ItemActivity extends AppCompatActivity  {
    private Toolbar toolbarPrincipal;
    private Spinner categoria;
    android.support.v4.app.FragmentManager fm = getSupportFragmentManager();

    private String retornoCategoriaEspecifica;

    private  BancoDados db = new BancoDados(this);



    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item);
        receberActivity();


        /**************************************************************************
         * configurar Toolbar
         *
         **************************************************************************/
         toolbarPrincipal = findViewById(R.id.toolbaritem);


        //    setActionBar(toolbarPrincipal);
        setSupportActionBar(toolbarPrincipal);


        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this, R.array.categoria_gasto,  android.R.layout.simple_spinner_item);
        categoria = (Spinner) findViewById(R.id.spinnerCategoria);
        categoria.setAdapter(adapter);

    }

    private void receberActivity() {
        Intent intent = getIntent();
        retornoCategoriaEspecifica =  intent.getStringExtra("categoriaEscolhida");
        Toast.makeText(this,retornoCategoriaEspecifica,Toast.LENGTH_LONG).show();
    }

    @Override
    public  boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_item,menu);
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_subcategoria:
                abrirCaixaAdicionarCategoria();

                return  true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void abrirCaixaAdicionarCategoria() {
        AdicionarSubCategoriaFragment  adicionarSubCategoriaFragment = new AdicionarSubCategoriaFragment();
        //DFragment dFragment = new DFragment();
        // Show DialogFragment
        adicionarSubCategoriaFragment.show(fm, "Dialog Fragment");
/*        Intent intent =  new Intent(ItemActivity.this,SubCategoriaActivity.class);
        intent.putExtra("categoriaEscolhida",retornoCategoriaEspecifica);
        startActivity(intent);*/
    }



}
