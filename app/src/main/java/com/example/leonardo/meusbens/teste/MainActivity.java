package com.example.leonardo.meusbens.teste;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;

import com.example.leonardo.meusbens.Manifest;
import com.example.leonardo.meusbens.R;

public class MainActivity extends AppCompatActivity {
    private  ImageView imagem;
    private  Button galeria;
    private  final int GALERIA_IMAGENS = 1;
    private  final  int PERMISSAO_REQUEST = 2;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
         setContentView(R.layout.activity_main);


         if (ContextCompat.checkSelfPermission(this,
                 android.Manifest.permission.READ_EXTERNAL_STORAGE)  != PackageManager.PERMISSION_DENIED){

             if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                     android.Manifest.permission.READ_EXTERNAL_STORAGE)){

             }else{
                 ActivityCompat.requestPermissions(this,
                         new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE},PERMISSAO_REQUEST);

             }
         }

        imagem = (ImageView) findViewById(R.id.ivImagem);
        galeria = (Button) findViewById(R.id.btnImagem);

        galeria.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent,GALERIA_IMAGENS);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK  &&  requestCode ==GALERIA_IMAGENS){
            Uri selectedImage = data.getData();
            String[] filePath = {MediaStore.Images.Media.DATA};
            Cursor c = getContentResolver().query(selectedImage,filePath,null,null,null);
            c.moveToFirst();
            int columnIndex = c.getColumnIndex(filePath[0]);
            String picturePath =c.getString(columnIndex);
            c.close();
            Bitmap imagemGaleria = (BitmapFactory.decodeFile(picturePath));
            imagem.setImageBitmap(imagemGaleria);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,  String[] permissions, int[] grantResults) {
    if (requestCode == PERMISSAO_REQUEST){


        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){


        }else {


        }

        return;
    }
    }
}
