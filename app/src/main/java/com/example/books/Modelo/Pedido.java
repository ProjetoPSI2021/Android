package com.example.books.Modelo;

import java.util.Date;

public class Pedido {
    // atributos declarados
    private int idpedido,id_reserva,preco,id_clientes,idpratoorder,idrestaurantepedido;
    private String tipo,data,estadopedido;

    private static int autoIncrementId = 1;

    // Botao direito do rato GENERATE e dp Constructor/Getter e Setter


    public int getIdrestaurantepedido() {
        return idrestaurantepedido;
    }

    public void setIdrestaurantepedido(int idrestaurantepedido) {
        this.idrestaurantepedido = idrestaurantepedido;
    }

    public int getIdpratoorder() {
        return idpratoorder;
    }

    public void setIdpratoorder(int idpratoorder) {
        this.idpratoorder = idpratoorder;
    }

    public int getIdpedido() {
        return idpedido;
    }

    public void setIdpedido(int idpedido) {
        this.idpedido = idpedido;
    }

    public int getId_reserva() {
        return id_reserva;
    }

    public void setId_reserva(int id_reserva) {
        this.id_reserva = id_reserva;
    }

    public String getEstadopedido() {
        return estadopedido;
    }

    public void setEstadopedido(String estadopedido) {
        this.estadopedido = estadopedido;
    }

    public int getPreco() {
        return preco;
    }

    public void setPreco(int preco) {
        this.preco = preco;
    }

    public int getId_clientes() {
        return id_clientes;
    }

    public void setId_clientes(int id_clientes) {
        this.id_clientes = id_clientes;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public Pedido(int idpedido, int id_reserva, String data, String tipo , int id_clientes, int preco, int idpratoorder, int idrestaurantepedido, String estadopedido) {
        this.idpedido = idpedido;
        this.id_reserva = id_reserva;
        this.data = data;
        this.tipo = tipo;
        this.id_clientes = id_clientes;
        this.preco = preco;
        this.idpratoorder = idpratoorder;
        this.idrestaurantepedido = idrestaurantepedido;
        this.estadopedido = estadopedido;
    }



    public void setId(int id) {
        this.idpedido = id;
    }
}
