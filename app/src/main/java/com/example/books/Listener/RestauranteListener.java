package com.example.books.Listener;

import com.example.books.Modelo.Pedido;
import com.example.books.Modelo.Prato;
import com.example.books.Modelo.Restaurante;

import java.util.ArrayList;

public interface RestauranteListener {
    void onRefreshListaRestaurantes(ArrayList<Restaurante> listaRestaurantes);
    void onRefreshListaPratos(ArrayList<Prato> listaPratos);
    void onRefreshListaPedidos(ArrayList<Pedido> listaPedidos);

    void onUpdateListaRestaurantesBD(Restaurante restaurante, int operacao);
}
