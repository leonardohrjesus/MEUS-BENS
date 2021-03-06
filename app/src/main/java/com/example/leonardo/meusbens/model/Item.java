package com.example.leonardo.meusbens.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Leonardo on 07/04/2018.
 */

public class Item extends Categoria implements Serializable {
    private String id;
    private String descricao;
    private double valor;
    private byte[] foto;


    public Item() {

    }

    public Item(String nome, double valor){
        this.descricao = nome;
        this.valor = valor;
    }

    public Item(String categoria, String subCategoria,  String nome, double valor) {
        super(categoria, subCategoria );
        this.descricao = nome;
        this.valor = valor;
    }

    public Item(String categoria, String subCategoria,  String nome, double valor, byte[] foto) {
        super(categoria, subCategoria );
        this.descricao = nome;
        this.valor = valor;
        this.foto = foto;
    }

    public List<String> pegarDescricaoValor(){
        List<String> retorno = new ArrayList<>();
        retorno.add(getDescricao());
        retorno.add(String.valueOf(getValor()));

        return  retorno;

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public byte[] getFoto() {
        return foto;
    }

    public void setFoto(byte[] foto) {
        this.foto = foto;
    }
}
