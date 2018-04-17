package com.example.leonardo.meusbens.model;

/**
 * Created by Leonardo on 07/04/2018.
 */

public class Item extends Categoria {
    private String id;
    private String descricao;
    private double valor;

    public Item(String categoria, String subCategoria, int idCategoria, String id, String nome, double valor) {
        super(categoria, subCategoria, idCategoria);
        this.id = id;
        this.descricao = nome;
        this.valor = valor;
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
}
