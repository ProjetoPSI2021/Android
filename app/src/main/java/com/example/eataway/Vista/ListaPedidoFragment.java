package com.example.eataway.Vista;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;


import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.eataway.Listener.RestauranteListener;
import com.example.eataway.Modelo.Pedido;
import com.example.eataway.Modelo.Prato;
import com.example.eataway.Modelo.Restaurante;
import com.example.eataway.Modelo.SingletonGestorRestaurante;
import com.example.eataway.R;
import com.example.eataway.adaptadores.ListaPedidoAdaptador;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;


public class ListaPedidoFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener, RestauranteListener {

    public ListView lvlistapedidos;
    private FloatingActionButton fab;
    private ArrayList<Pedido> listaPedidos;
    private SwipeRefreshLayout swipeRefreshLayout;
    public Context context;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        setHasOptionsMenu(true);
        View rootView = inflater.inflate(R.layout.fragment_lista_pedidos, container, false);

        lvlistapedidos = rootView.findViewById(R.id.lvlistapedidos);


        //Mostrar os pedidos da base de dados local
        SingletonGestorRestaurante.iniciarBDPedidos(context);
        if(savedInstanceState == null) {
            SingletonGestorRestaurante.getInstance(context).lerBDPedidos();
            this.listaPedidos = SingletonGestorRestaurante.getInstance(context).getPedidosBD();
        }else{

        }
        if(this.listaPedidos == null) {
            lvlistapedidos.setAdapter(new ListaPedidoAdaptador(getContext(), listaPedidos));
        }
        swipeRefreshLayout = rootView.findViewById(R.id.swipe);
        swipeRefreshLayout.setOnRefreshListener(this);

        SingletonGestorRestaurante.getInstance(getContext()).setRestauranteListener(this);
        SingletonGestorRestaurante.getInstance(getContext()).getAllPedidosAPI(getContext());
        return rootView;
    }


    @Override
    public void onRefresh() {
        SingletonGestorRestaurante.getInstance(getContext()).getAllRestAPI(getContext());
        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void onRefreshListaRestaurantes(ArrayList<Restaurante> listaRestaurantes) {

    }

    @Override
    public void onRefreshListaPratos(ArrayList<Prato> listaPratos) {

    }

    @Override
    public void onRefreshListaPedidos(ArrayList<Pedido> listaPedidos) {
        if(listaPedidos != null){
            lvlistapedidos.setAdapter(new ListaPedidoAdaptador(getContext(), listaPedidos));
        }
    }


}