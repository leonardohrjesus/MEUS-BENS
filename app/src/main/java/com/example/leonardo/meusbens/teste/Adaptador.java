package com.example.leonardo.meusbens.teste;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.example.leonardo.meusbens.R;
import com.example.leonardo.meusbens.activity.ListaCategoriaActivity;
import com.example.leonardo.meusbens.config.BancoDados;
import com.example.leonardo.meusbens.model.Item;

import java.util.HashMap;
import java.util.List;

/**
 * Created by Leonardo on 08/04/2018.
 */

public class Adaptador extends BaseExpandableListAdapter {

    private List<String> lstGrupos;
    private HashMap<String, List<Item>> lstItensGrupos;
    private Context context;


    private String subCategoria;
    private String categoriaPrincipal;

    public Adaptador(Context context, List<String> grupos, HashMap<String, List<Item>> itensGrupos) {
        this.context = context;
        this.lstItensGrupos = itensGrupos;
        this.lstGrupos=grupos;

    }

    public  Adaptador(Context context, List<String> grupos, HashMap<String, List<Item>> itensGrupos,String categoriaPrincipal){
        this.context = context;
        this.lstGrupos=grupos;
        this.categoriaPrincipal=categoriaPrincipal;
        this.lstItensGrupos = itensGrupos;


    }


    @Override
    public int getGroupCount() {
        // retorna a quantidade de grupos
        return lstGrupos.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        // retorna a quantidade de itens de um grupo
        return lstItensGrupos.get(getGroup(groupPosition)).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        // retorna um grupo
        return lstGrupos.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        // retorna um item do grupo
        return lstItensGrupos.get(getGroup(groupPosition)).get(childPosition);
    }

    public Item getFilho(int groupPosition, int childPosition) {
        // retorna um item do grupo
        return lstItensGrupos.get(getGroup(groupPosition)).get(childPosition);
    }

    /*public  String getFilhoEspecifico( int childPosition){

        return String.valueOf(lstItensGrupos.get(childPosition));
    }*/

    @Override
    public long getGroupId(int groupPosition) {
        // retorna o id do grupo, porém como nesse exemplo
        // o grupo não possui um id específico, o retorno
        // será o próprio groupPosition
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        // retorna o id do item do grupo, porém como nesse exemplo
        // o item do grupo não possui um id específico, o retorno
        // será o próprio childPosition
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        // retorna se os ids são específicos (únicos para cada
        // grupo ou item) ou relativos
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        // cria os itens principais (grupos)

        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(
                    Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.grupo, null);
        }

        TextView tvGrupo = (TextView) convertView.findViewById(R.id.tvGrupo);
        TextView tvQtde = (TextView) convertView.findViewById(R.id.tvQtde);

        tvGrupo.setText((String) getGroup(groupPosition));

        tvQtde.setText(String.valueOf(getChildrenCount(groupPosition)));

        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        // cria os subitens (itens dos grupos)

        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(
                    Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.item_grupo, null);
        }

        TextView tvItem = (TextView) convertView.findViewById(R.id.tvItem);
        TextView tvValor = (TextView) convertView.findViewById(R.id.tvValor);


        Item produto = (Item) getChild(groupPosition, childPosition);
        tvItem.setText(produto.getDescricao());
        tvValor.setText(String.valueOf(produto.getValor()));



        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        // retorna se o subitem (item do grupo) é selecionável
        return true;
    }
}

