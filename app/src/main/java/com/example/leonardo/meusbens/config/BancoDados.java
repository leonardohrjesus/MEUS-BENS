package com.example.leonardo.meusbens.config;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.leonardo.meusbens.model.Categoria;
import com.example.leonardo.meusbens.model.Item;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Amministratore on 03/08/2017.
 */

public class BancoDados extends SQLiteOpenHelper {
    private static final int VERSAO_BANCO = 1;
    private static  final String BANCO_MEUS_BENS = "bd_meus_bens";

    private static  final String TABELA_CATEGORIA = "tb_categoria";
    private static  final String COLUNA_C_CATEGORIA_PRINCIPAL = "categoria_principal";
    private static  final String COLUNA_C_SUB__CATEGORIA = "sub_categoria";
    private static  final String COLUNA_C_ID= "id_categoria";


    private static  final String TABELA_ITENS = "tb_itens";
    private static  final String COLUNA_I_CATEGORIA_PRINCIPAL = "categoria_principal";
    private static  final String COLUNA_I_SUB_CATEGORIA = "sub_categoria";
    private static  final String COLUNA_I_ID= "id_itens";
    private static  final String COLUNA_I_VALOR= "valor";
    private static  final String COLUNA_I_DESCRICAO= "descricao";





    public BancoDados(Context context){
        super(context, BANCO_MEUS_BENS, null, VERSAO_BANCO);
    }


    //CRIAR BANCO DE DADOS
    @Override
    public void onCreate(SQLiteDatabase db) {
        String QUERY_COLUNA = "CREATE TABLE " +TABELA_CATEGORIA +"("
                + COLUNA_C_ID+ " INTEGER PRIMARY KEY," + COLUNA_C_CATEGORIA_PRINCIPAL +" TEXT ,"
                + COLUNA_C_SUB__CATEGORIA + " TEXT )";
        db.execSQL(QUERY_COLUNA);

        String QUERY_COLUNA1 = "CREATE TABLE " +TABELA_ITENS +"("
                + COLUNA_I_ID+ " INTEGER PRIMARY KEY," + COLUNA_I_CATEGORIA_PRINCIPAL +" TEXT ,"
                + COLUNA_I_SUB_CATEGORIA +" TEXT ,"
                + COLUNA_I_VALOR +" REAL ,"
                + COLUNA_I_DESCRICAO + " TEXT )";
        db.execSQL(QUERY_COLUNA1);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
    public  void  addCategoria(Categoria categoria){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(COLUNA_C_CATEGORIA_PRINCIPAL,categoria.getCategoria());
        values.put(COLUNA_C_SUB__CATEGORIA, categoria.getSubCategoria());


        db.insert(TABELA_CATEGORIA,null,values);

        db.close();

    }

   public  void  addItens(Item  item){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(COLUNA_I_CATEGORIA_PRINCIPAL,item.getIdCategoria());
        values.put(COLUNA_I_SUB_CATEGORIA, item.getSubCategoria());
        values.put(COLUNA_I_VALOR, item.getValor());
        values.put(COLUNA_I_DESCRICAO,item.getDescricao());

        db.insert(TABELA_ITENS,null,values);

        db.close();

    }
  /*  void apagarCliente(Cliente cliente){
        SQLiteDatabase db = this.getWritableDatabase();

        db.delete(TABELA_CLIENTE,COLUNA_CODIGO + "=?", new String[]{String.valueOf(cliente.getCodigo())});

        db.close();

    }

    Cliente selecionarCliente(int  codigo){
        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.query(TABELA_CLIENTE,new String[]{COLUNA_CODIGO,COLUNA_NOME,COLUNA_TELEFONE,COLUNA_EMAIL},
                COLUNA_CODIGO + "= ?",  new String[]{String.valueOf(codigo)},null,null,null,null);


        if(cursor != null){
            cursor.moveToFirst();
        }
        Cliente cliente = new Cliente(Integer.parseInt(cursor.getString(0)),
                cursor.getString(1),
                cursor.getString(2),
                cursor.getString(3));

        return cliente;
    }

    void atualizarCliente(Cliente cliente){

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUNA_NOME, cliente.getNome());
        values.put(COLUNA_TELEFONE, cliente.getTelefone());
        values.put(COLUNA_EMAIL, cliente.getEmail());

        db.update(TABELA_CLIENTE, values, COLUNA_CODIGO + "= ?", new String[] {String.valueOf(cliente.getCodigo())});

    }*/

    public List<Categoria> listaTodasCategorias(){
        List<Categoria> listaCategorias = new ArrayList<Categoria>();

        String query  = "SELECT  * FROM " + TABELA_CATEGORIA;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor c = db.rawQuery(query,null);
        if (c.moveToFirst()){
            do {
                Categoria categoria= new Categoria();
                categoria.setIdCategoria(Integer.parseInt(c.getString(0)));
                categoria.setCategoria(c.getString(1));
                categoria.setSubCategoria(c.getString(2));

                listaCategorias.add(categoria);

            }while (c.moveToNext());
        }
        return listaCategorias;
    }

}

