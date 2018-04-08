package com.example.leonardo.meusbens.model;

/**
 * Created by Leonardo on 07/04/2018.
 */

public class Item {
    private String id;
    private String nome;
    private double valor;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }
}
