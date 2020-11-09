package com.example.books.Modelo;

public class Livro {
    // atributos declarados
    private int id,capa,ano;
    private String titulo,serie,autor;

    // Botao direito do rato GENERATE e dp Constructor/Getter e Setter
    public int getId() {
        return id;
    }
    public int getCapa() {
        return capa;
    }
    public void setCapa(int capa) {
        this.capa = capa;
    }
    public int getAno() {
        return ano;
    }
    public void setAno(int ano) {
        this.ano = ano;
    }
    public String getTitulo() {
        return titulo;
    }
    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }
    public String getSerie() {
        return serie;
    }
    public void setSerie(String serie) {
        this.serie = serie;
    }
    public String getAutor() {
        return autor;
    }
    public void setAutor(String autor) {
        this.autor = autor;
    }


    public Livro(int id, int capa, int ano, String titulo, String serie, String autor) {
        this.id = id;
        this.capa = capa;
        this.ano = ano;
        this.titulo = titulo;
        this.serie = serie;
        this.autor = autor;
    }



}
