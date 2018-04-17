package com.example.leonardo.meusbens.fragments;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.leonardo.meusbens.R;

/**
 * Created by Leonardo on 16/04/2018.
 */

public class AdicionarSubCategoriaFragment  extends DialogFragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.caixa_texto_categoria, container,
                false);



        // Do something else
        return rootView;
    }
}
