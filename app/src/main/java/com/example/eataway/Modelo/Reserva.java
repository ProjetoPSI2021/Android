package com.example.eataway.Modelo;

public class Reserva {
    // atributos declarados
    private int id,salas,mesas,telefone;
    private String nome,imagem,morada;

    private static int autoIncrementId = 1;

    public int getId() {
        return id;
    }
    //public int setID(int id) { this.id = id;}
    public String getimagem() {
        return imagem;
    }
    public void setimagem(String imagem) {
        this.imagem = imagem;
    }
    public String getNome() {
        return nome;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }
    public String getMorada() {
        return morada;
    }
    public void setMorada(String morada) {
        this.nome = morada;
    }
    public int getMesas() {
        return mesas;
    }
    public void setMesas(int mesas) {
        this.mesas = mesas;
    }
    public int getSalas() {
        return mesas;
    }
    public void setSalas(int salas) {
        this.salas = salas;
    }
    public int getTelefone() {
        return telefone;
    }
    public void setTelefone(int telefone) {
        this.telefone = telefone;
    }




    public Reserva(int id, String nome, String morada, String imagem, int salas, int mesas, int telefone) {
        this.id = id;
        this.nome = nome;
        this.morada = morada;
        this.imagem = imagem;
        this.salas = salas;
        this.mesas = mesas;
        this.telefone = telefone;
    }



    public void setId(int id) {
        this.id = id;
    }
}
