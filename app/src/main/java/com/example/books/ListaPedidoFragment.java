package com.example.books;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.renderscript.Sampler;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SearchView;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.books.Listener.RestauranteListener;
import com.example.books.Modelo.Pedido;
import com.example.books.Modelo.Prato;
import com.example.books.Modelo.Restaurante;
import com.example.books.Modelo.SingletonGestorRestaurante;
import com.example.books.adaptadores.ListaPedidoAdaptador;
import com.example.books.adaptadores.ListaPratoAdaptador;
import com.example.books.adaptadores.ListaRestauranteAdaptador;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.LinkedList;


public class ListaPedidoFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener, RestauranteListener {

    public ListView lvlistapedidos;
    private FloatingActionButton fab;
    private ArrayList<Pedido> listaPedidos;
    private SearchView searchView;
    private SwipeRefreshLayout swipeRefreshLayout;
    public Context context;
    private final String VALUES = "_values_";
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        setHasOptionsMenu(true);
        View rootView = inflater.inflate(R.layout.fragment_lista_pedidos, container, false);

        lvlistapedidos = rootView.findViewById(R.id.lvlistapedidos);
        fab = rootView.findViewById(R.id.fab);
        SingletonGestorRestaurante.iniciarBD(context);
        if(savedInstanceState == null) {
            SingletonGestorRestaurante.getInstance(context).lerBD();
            this.listaPedidos = SingletonGestorRestaurante.getInstance(context).getPedidosBD();
        }else{

        }
        if(this.listaPedidos == null) {
            lvlistapedidos.setAdapter(new ListaPedidoAdaptador(getContext(), listaPedidos));
        }


       // lvlistarestaurantes.setAdapter(new ListaRestauranteAdaptador(getContext(), listaRestaurantes));

        lvlistapedidos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Pedido tempPedido = (Pedido) parent.getItemAtPosition(position);
                System.out.println("--->" + tempPedido.getIdpedido());
                //intent que vai encaminhar para uma atividade que vamos criar detalheslivroactivity
                //intent this application content + livro ou id do livro
                //buscar livro no outro lado
                Intent intent = new Intent(getContext(), DetalhesRestauranteActivity.class);
                intent.putExtra(DetalhesRestauranteActivity.DETALHES_LIVRO, tempPedido.getIdpedido());
                startActivityForResult(intent, DetalhesRestauranteActivity.EDITAR);
            }
        });

        /*fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), DetalhesRestauranteActivity.class);
                startActivityForResult(intent, DetalhesRestauranteActivity.ADICIONAR);
            }
        });*/

        swipeRefreshLayout = rootView.findViewById(R.id.swipe);
        swipeRefreshLayout.setOnRefreshListener(this);

        SingletonGestorRestaurante.getInstance(getContext()).setRestauranteListener(this);
        SingletonGestorRestaurante.getInstance(getContext()).getAllPedidosAPI(getContext());
        return rootView;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (resultCode == Activity.RESULT_OK){
                    SingletonGestorRestaurante.getInstance(getContext()).getAllPedidosAPI(getContext());
                    Snackbar.make(getView(), R.string.rest_add_suc,Snackbar.LENGTH_LONG).show();

        }
    }




    @Override
    public void onRefresh() {
        SingletonGestorRestaurante.getInstance(getContext()).getAllLivrosAPI(getContext());
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

    @Override
    public void onUpdateListaRestaurantesBD(Restaurante restaurante, int operacao) {

    }
}