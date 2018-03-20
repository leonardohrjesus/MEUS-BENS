package com.example.leonardo.meusbens;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;

public class DashboradActivity extends AppCompatActivity {

    private FirebaseAuth autenticacao;
    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashborad);

        button = (Button) findViewById(R.id.id_button_sair);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                autenticacao.getInstance().signOut();
                Intent intent = new Intent(DashboradActivity.this,LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });



    }

}
