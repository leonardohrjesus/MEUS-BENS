package com.example.leonardo.meusbens.model;

/**
 * Created by Leonardo on 13/04/2018.
 */

public class Categoria {

    private String categoria;
    private String subCategoria;
    private int idCategoria;

    public Categoria(){

    }

    public Categoria(String categoria, String subCategoria, int idCategoria) {
        this.categoria = categoria;
        this.subCategoria = subCategoria;
        this.idCategoria = idCategoria;
    }

    public Categoria(String categoria, String subCategoria) {
        this.categoria = categoria;
        this.subCategoria = subCategoria;

    }


    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getSubCategoria() {
        return subCategoria;
    }

    public void setSubCategoria(String subCategoria) {
        this.subCategoria = subCategoria;
    }

    public int getIdCategoria() {
        return idCategoria;
    }

    public void setIdCategoria(int idCategoria) {
        this.idCategoria = idCategoria;
    }
}
