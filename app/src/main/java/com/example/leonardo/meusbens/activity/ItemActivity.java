package com.example.leonardo.meusbens.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.ParcelFileDescriptor;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
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
import com.example.leonardo.meusbens.util.MaskEditUtil;
import com.example.leonardo.meusbens.util.MoneyTextWatcher;
import com.example.leonardo.meusbens.util.PassadorDeInformacao;
import com.example.leonardo.meusbens.util.PassadorItem;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileDescriptor;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ItemActivity extends AppCompatActivity{

    private Toolbar toolbarPrincipal;
    private Spinner categoria;
    private Button botoaOk;
    private EditText textoNomeItem;
    private EditText valorItem;
    private android.support.v4.app.FragmentManager fm = getSupportFragmentManager();
    private String retornoCategoriaEspecifica;
    private BancoDados db = new BancoDados(this);
    private ArrayAdapter<String> adapter;
    private ArrayList<String> arraylist;
    private ImageView imageItem;
    private  byte[] fototipoBD;
    private  Bitmap bitmap = null;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item);
        receberActivity();

        imageItem = (ImageView) findViewById(R.id.imageViewItem);
        botoaOk = (Button) findViewById(R.id.buttonConfirmar);
        textoNomeItem = (EditText) findViewById(R.id.editTextNome);
        valorItem = (EditText) findViewById(R.id.editTextvalor);
        categoria = (Spinner) findViewById(R.id.spinnerCategoria);

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
                // abrirCamera();

            }
        });

        /**************************************************************************
         * MASCARA DE VALOR
         *
         **************************************************************************/

        Locale mLocale = new Locale("pt", "BR");
        valorItem.addTextChangedListener(new MoneyTextWatcher(valorItem, mLocale));

        listaCategoria();

        /**************************************************************************
         * configurar Toolbar
         *
         **************************************************************************/
        toolbarPrincipal = findViewById(R.id.toolbaritem);
        setSupportActionBar(toolbarPrincipal);

    }

    public void listaCategoria() {

        List<Categoria> categorias = db.listaTodasCategorias();
        if (arraylist  == null){
            arraylist = new ArrayList<String>();
        }

        if (adapter == null){
            adapter = new ArrayAdapter<String>(ItemActivity.this,android.R.layout.simple_list_item_1, arraylist);
            categoria.setAdapter(adapter);
        }
        for (Categoria c : categorias){
            arraylist.add(c.getSubCategoria());
            adapter.notifyDataSetChanged();
        }
    }

    private void abrirCamera() {

        File file = new File(Environment.getExternalStorageDirectory() + "/arquivo.jpg");
        Uri outputFileUri = Uri.fromFile(file);
        Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, outputFileUri);
        startActivityForResult(intent, 1);
    }

    private void inserirItem() {
        String subCategoria= null;
        String nome = textoNomeItem.getText().toString();
        String valor = valorItem.getText().toString();

        if (arraylist.size()  == 0){
            Toast.makeText(ItemActivity.this,"Por favor escolha uma categoria, caso não exista ad" +
                    "iciona uma! ",Toast.LENGTH_SHORT).show();
            abrirCaixaAdicionarCategoria();
        }else{
            subCategoria = categoria.getSelectedItem().toString();
        }

        String categoriaPrincipal = retornoCategoriaEspecifica;

        byte[] foto = fototipoBD;

        boolean  resultadoCampoVazios = verificarDadosVazios(nome, valorItem.getText().toString(), subCategoria,bitmap);
        if (resultadoCampoVazios){
            db.addItens(new Item(categoriaPrincipal,subCategoria,nome,Double.parseDouble(valor),foto));
            limparCampos();
        }

    }

    private void limparCampos() {
        textoNomeItem.setText("");
        valorItem.setText("");
        imageItem.setImageBitmap(null);

    }

    private boolean verificarDadosVazios(String nome, String valor, String subCategoria,  Bitmap foto) {
        if (nome.isEmpty()){
            textoNomeItem.setError("Campo Obrigatório");
            return  false;
        }
        if (valor.isEmpty()  ){
            valorItem.setError("Campo Obrigatório");
            return  false;
        }
        if (subCategoria.isEmpty() ||  subCategoria.equals(null) ){
            Toast.makeText(ItemActivity.this,"Por favor escolha uma categoria, caso não exista ad" +
                    "iciona uma! ",Toast.LENGTH_SHORT).show();
            return  false;

        }

        if ( null ==  foto){
            Toast.makeText(ItemActivity.this,"Por favor escolha uma foto! ",Toast.LENGTH_LONG  ).show();
            return  false;
        }else {
            foto = null;
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
                bitmap = BitmapFactory.decodeFileDescriptor(fileDescriptor, null, options);
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

/*    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        System.gc(); // garbage colector
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                try {
                    BitmapFactory.Options options = new BitmapFactory.Options();
                    options.inSampleSize = 3;
                    Bitmap imageBitmap = BitmapFactory.decodeFile(Environment.getExternalStorageDirectory() + "/arquivo.jpg", options);

                    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                    boolean validaCompressao = imageBitmap.compress(Bitmap.CompressFormat.JPEG, 50, outputStream);
                    byte[] fotoBinario = outputStream.toByteArray();

                    String encodedImage = Base64.encodeToString(fotoBinario, Base64.DEFAULT);

                    imageItem.setImageBitmap(imageBitmap); // ImageButton, seto a foto como imagem do botão

                    boolean isImageTaken = true;
                } catch (Exception e) {
                    Toast.makeText(this, "Picture Not taken",Toast.LENGTH_LONG).show();e.printStackTrace();
                }
            } else if (resultCode == RESULT_CANCELED) {
                Toast.makeText(this, "Picture was not taken 1 ", Toast.LENGTH_SHORT);
            } else {
                Toast.makeText(this, "Picture was not taken 2 ", Toast.LENGTH_SHORT);
            }
        }
    }*/

    private void receberActivity() {
        Intent intent = getIntent();
        String categoriaEscolhida =   intent.getStringExtra("categoriaEscolhida");
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
        listaCategoria();

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


    private class Processo extends AsyncTask<String, String, String> {
        //Método que é responsável por executar a sua tarefa que vai demorar um pouco
        @Override
        protected String doInBackground(String... params) {
            //aqui eu faço um while so para demonstração, mais você retira esse codigo e coloca o seu.
            /*int i = 0;
            while (i < 1000) {
                //aqui ele vai "falar" para  metodo onProgressUpdate para atualizar a tela com a sua string
                publishProgress("Processo em: " + i);
                i++;
            }*/
            Log.i("Rei android","doInBackground");
            return null;
        }

        @Override
        protected void onProgressUpdate(String... values) {
            //Faz o setText no seu textView da tela

            Log.i("Rei android","onProgressUpdate");
        }
    }


}
