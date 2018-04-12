package com.example.leonardo.meusbens.activity;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.example.leonardo.meusbens.R;

public class ItemActivity extends AppCompatActivity {
    private Toolbar toolbarPrincipal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item);

        /**************************************************************************
         * configurar Toolbar
         *
         **************************************************************************/
        toolbarPrincipal = (Toolbar) findViewById(R.id.toolbaritem);
        setSupportActionBar(toolbarPrincipal);

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
                return  true;

            default:
                return super.onOptionsItemSelected(item);
        }

    }

}
