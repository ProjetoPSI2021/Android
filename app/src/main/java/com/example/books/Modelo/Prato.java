package com.example.books.Modelo;

public class Prato {
    // atributos declarados
    private int id, idr,preco;
    private String nome,imagem,tipo,ingr;

    private static int autoIncrementId = 1;


    public int getId() {
        return id;
    }

    public int getIdr() {
        return idr;
    }

    public void setIdr(int idr) {
        this.idr = idr;
    }

    public int getPreco() {
        return preco;
    }

    public void setPreco(int preco) {
        this.preco = preco;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getImagem() {
        return imagem;
    }

    public void setImagem(String imagem) {
        this.imagem = imagem;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getIngr() {
        return ingr;
    }

    public void setIngr(String ingr) {
        this.ingr = ingr;
    }

    public Prato(int id, String nome, String imagem, String tipo, int idr, int preco, String ingr) {
        this.id = id;
        this.nome = nome;
        this.imagem = imagem;
        this.tipo = tipo;
        this.idr = idr;
        this.preco = preco;
        this.ingr = ingr;
    }



    public void setId(int id) {
        this.id = id;
    }
}
