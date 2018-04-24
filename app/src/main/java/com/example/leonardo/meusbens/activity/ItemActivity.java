package com.example.leonardo.meusbens.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.ParcelFileDescriptor;
import android.provider.MediaStore;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.leonardo.meusbens.R;
import com.example.leonardo.meusbens.config.BancoDados;
import com.example.leonardo.meusbens.fragments.AdicionarSubCategoriaFragment;
import com.example.leonardo.meusbens.model.Categoria;
import com.example.leonardo.meusbens.model.Item;
import com.example.leonardo.meusbens.teste.MainActivity;

import java.io.ByteArrayOutputStream;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ItemActivity extends AppCompatActivity {
    private Toolbar toolbarPrincipal;
    private Spinner categoria;
    private Button botoaOk;
    private EditText textoNomeItem;
    private EditText valorItem;
    android.support.v4.app.FragmentManager fm = getSupportFragmentManager();
    private String retornoCategoriaEspecifica;
    BancoDados db = new BancoDados(this);
    ArrayAdapter<String> adapter;
    ArrayList<String> arraylist;
    private ImageView imageItem;
    private  byte[] fototipoBD;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item);
        receberActivity();

        imageItem = (ImageView) findViewById(R.id.imageViewItem);
        botoaOk = (Button) findViewById(R.id.buttonConfirmar);
        textoNomeItem = (EditText) findViewById(R.id.editTextNome);
        valorItem = (EditText) findViewById(R.id.editTextvalor);

        botoaOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                inserirItem();
            }
        });

        imageItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                obterFoto();
            }
        });


        /**************************************************************************
         * configurar Toolbar
         *
         **************************************************************************/
        toolbarPrincipal = findViewById(R.id.toolbaritem);
        setSupportActionBar(toolbarPrincipal);

        arraylist = new ArrayList<String>();

        List<Categoria> categorias = db.listaTodasCategorias();

        adapter = new ArrayAdapter<String>(ItemActivity.this,android.R.layout.simple_list_item_1, arraylist);

        categoria = (Spinner) findViewById(R.id.spinnerCategoria);
        categoria.setAdapter(adapter);
        for (Categoria c : categorias){
            arraylist.add(c.getSubCategoria());
            adapter.notifyDataSetChanged();
        }

    }

    private void inserirItem() {
        String nome = textoNomeItem.getText().toString();
        double valor = Double.parseDouble(valorItem.getText().toString());
        String subCategoria = categoria.getSelectedItem().toString();
        String categoriaPrincipal = retornoCategoriaEspecifica;
        byte[] foto = fototipoBD;

        boolean  resultadoCampoVazios = verificarDadosVazios(nome, valor, subCategoria,foto);
        if (resultadoCampoVazios){
            db.addItens(new Item(categoriaPrincipal,subCategoria,nome,valor,foto));
        }


    }

    private boolean verificarDadosVazios(String nome, double valor, String subCategoria, byte[] foto) {
        if (nome.isEmpty()){
            textoNomeItem.setError("Campo Obrigatório");
            return  false;
        }
        if (valor <= 0  ){
            valorItem.setError("Campo Obrigatório");
            return  false;
        }
        if (subCategoria.isEmpty()){
            Toast.makeText(ItemActivity.this,"Por favor escolha uma categoria, caso não exista ad" +
                    "iciona uma! ",Toast.LENGTH_SHORT).show();
            return  false;

        }
        if (foto.equals(null)){
            Toast.makeText(ItemActivity.this,"Por favor escolha uma foto! ",Toast.LENGTH_SHORT).show();
            return  false;
        }

        return  true;
    }


    private void obterFoto() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent,1);
    }


    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        //Testar processo de retono dos dados
        if (requestCode==1 && resultCode == RESULT_OK && data != null) {
            try {
                //Recuperar local do Recurso
                Uri localImagemSelecionada = data.getData();
                //recupera a imagem do local que foi selecionada
                ParcelFileDescriptor parcelFileDescriptor = getContentResolver().openFileDescriptor(localImagemSelecionada, "r");

                FileDescriptor fileDescriptor = parcelFileDescriptor.getFileDescriptor();

                BitmapFactory.Options options = new BitmapFactory.Options();

                options.inJustDecodeBounds = true;
                Bitmap bitmap = BitmapFactory.decodeFileDescriptor(fileDescriptor, null, options);
                options.inSampleSize = calculateInSampleSize(options, 300, 300);
                options.inJustDecodeBounds = false;
                bitmap = BitmapFactory.decodeFileDescriptor(fileDescriptor, null, options);

                parcelFileDescriptor.close();

                //comprimir no formato PNG
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG, 20, stream);

                //Cria um array de bytes da imagem
                 fototipoBD = stream.toByteArray();

                imageItem.setImageBitmap(bitmap);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void receberActivity() {
        Intent intent = getIntent();
        setRetornoCategoriaEspecifica(intent.getStringExtra("categoriaEscolhida"));
        Toast.makeText(this, retornoCategoriaEspecifica, Toast.LENGTH_LONG).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_item, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_subcategoria:
                abrirCaixaAdicionarCategoria();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void abrirCaixaAdicionarCategoria() {
        AdicionarSubCategoriaFragment adicionarSubCategoriaFragment = new AdicionarSubCategoriaFragment();
        adicionarSubCategoriaFragment.passaInformacao(getRetornoCategoriaEspecifica());
        adicionarSubCategoriaFragment.show(fm, "Dialog Fragment");
    }

    public String getRetornoCategoriaEspecifica() {
        return retornoCategoriaEspecifica;
    }

    public void setRetornoCategoriaEspecifica(String retornoCategoriaEspecifica) {
        this.retornoCategoriaEspecifica = retornoCategoriaEspecifica;
    }


    public static int calculateInSampleSize(
            BitmapFactory.Options options, int reqWidth, int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {

            final int halfHeight = height / 2;
            final int halfWidth = width / 2;

            // Calculate the largest inSampleSize value that is a power of 2 and keeps both
            // height and width larger than the requested height and width.
            while ((halfHeight / inSampleSize) > reqHeight
                    && (halfWidth / inSampleSize) > reqWidth) {
                inSampleSize *= 2;
            }
        }

        return inSampleSize;
    }
}
