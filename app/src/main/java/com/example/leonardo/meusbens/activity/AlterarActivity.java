package com.example.leonardo.meusbens.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import com.example.leonardo.meusbens.R;
import com.example.leonardo.meusbens.config.BancoDados;
import com.example.leonardo.meusbens.model.Categoria;
import com.example.leonardo.meusbens.model.Item;

import java.util.ArrayList;
import java.util.List;

public class AlterarActivity extends AppCompatActivity {
    private Item item ;
    private ImageView imageItem;
    private ArrayAdapter<String> adapter;
    private ArrayList<String> arraylist;
    private Spinner categoria;
    private Button botoaOk;
    private EditText textoNomeItem;
    private EditText valorItem;


    private BancoDados db = new BancoDados(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alterar);


        imageItem = (ImageView) findViewById(R.id.imageViewItem);
        botoaOk = (Button) findViewById(R.id.buttonConfirmar);
        textoNomeItem = (EditText) findViewById(R.id.editTextNome);
        valorItem = (EditText) findViewById(R.id.editTextvalor);
        categoria = (Spinner) findViewById(R.id.spinnerCategoria);

        //listaCategoria();
        receberItem();
        exibirValores();

    }

    private void exibirValores() {

        textoNomeItem.setText(item.getDescricao());
        valorItem.setText(String.valueOf(item.getValor()));

        if (arraylist  == null){
            arraylist = new ArrayList<String>();
        }



        adapter = new ArrayAdapter<String>(AlterarActivity.this,android.R.layout.simple_list_item_1, arraylist);
        categoria.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        arraylist.add(item.getSubCategoria());
        adapter.notifyDataSetChanged();

        /*for (Item i  : item){
            arraylist.add(i.getSubCategoria());
            adapter.notifyDataSetChanged();
        }
        arraylist.add(item.getCategoria());
        adapter.notifyDataSetChanged();*/

        Bitmap bitmap = BitmapFactory.decodeByteArray(item.getFoto(),0,item.getFoto().length);
        imageItem.setImageBitmap(bitmap);

    }

    private void listaCategoria() {
        List<Categoria> categorias = db.listaTodasCategorias();
        if (arraylist  == null){
            arraylist = new ArrayList<String>();
        }

        if (adapter == null){
            adapter = new ArrayAdapter<String>(AlterarActivity.this,android.R.layout.simple_list_item_1, arraylist);
            categoria.setAdapter(adapter);
        }

        for (Categoria c : categorias){
            arraylist.add(c.getSubCategoria());
            adapter.notifyDataSetChanged();
        }
    }

    private void receberItem() {
        Intent intent = getIntent();
        String subCategoria = intent.getStringExtra("objeto");
        String CategoriaPrincipal = intent.getStringExtra("CategoriaPrinciapal");
        Item itemRecuperado = (Item) intent.getSerializableExtra("item");

        itemRecuperado.setSubCategoria(subCategoria);

        Item itemRetorno = db.retornaItem(CategoriaPrincipal,subCategoria,itemRecuperado.getDescricao(),itemRecuperado.getValor());
        setItem(itemRetorno );
    }


    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {

        this.item = item;
    }
}
