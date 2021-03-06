package com.example.leonardo.meusbens.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.content.ContextCompat;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ImageSpan;
import android.view.ViewGroup;

import com.example.leonardo.meusbens.R;
import com.example.leonardo.meusbens.fragments.CategoriaFragment;
import com.example.leonardo.meusbens.fragments.PrincipalFragment;

import java.util.HashMap;

/**
 * Created by Leonardo on 25/03/2018.
 */

public class TabsAdapter extends FragmentStatePagerAdapter  {

    private Context context;
    private int[] icones = new int[] {R.drawable.ic_home_white_24dp,R.drawable.ic_chrome_reader_mode_white_24dp};
    private int tamanhoIcone;
    private HashMap<Integer,Fragment> fragmentosUtilizados = new HashMap<>();



    public TabsAdapter(FragmentManager fm, Context c) {
        super(fm);
        this.context = c;
        double escala = this.context.getResources().getDisplayMetrics().density;
        tamanhoIcone = (int)(36*escala);
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;

        switch (position){
            case 0:
                fragment = new PrincipalFragment();
                fragmentosUtilizados.put(position,fragment);
                break;
            case 1:
                fragment = new CategoriaFragment();
                break;
        }
        return  fragment;
    }
    public Fragment getFragment(Integer indice){

        return fragmentosUtilizados.get(indice);
    }


    @Override
    public CharSequence getPageTitle(int position) {

        //Recuperar o icone de acordo com a posicao
        Drawable drawable = ContextCompat.getDrawable(this.context,icones[position]);
        drawable.setBounds(0,0,tamanhoIcone ,tamanhoIcone );

        //permite colocar uma imagem dentro de um texto
        ImageSpan imageSpan = new ImageSpan(drawable);

        //Classe utilizada para retomar CharSequence
        SpannableString spannableString = new SpannableString(" ");
        spannableString.setSpan(imageSpan,0,spannableString.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return spannableString;
    }

    @Override
    public int getCount() {
        return icones.length;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        super.destroyItem(container, position, object);
        fragmentosUtilizados.remove(position);
    }
}
