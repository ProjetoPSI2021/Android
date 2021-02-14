package com.example.eataway.Listener;

import com.example.eataway.Modelo.Pedido;
import com.example.eataway.Modelo.Prato;
import com.example.eataway.Modelo.Restaurante;

import java.util.ArrayList;

public interface RestauranteListener {
    void onRefreshListaRestaurantes(ArrayList<Restaurante> listaRestaurantes);
    void onRefreshListaPratos(ArrayList<Prato> listaPratos);
    void onRefreshListaPedidos(ArrayList<Pedido> listaPedidos);

    //void onUpdateListaRestaurantesBD(Restaurante restaurante, int operacao);
}
