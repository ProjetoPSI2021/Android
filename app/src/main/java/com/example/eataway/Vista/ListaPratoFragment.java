package com.example.eataway.Vista;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;


import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.eataway.Listener.RestauranteListener;
import com.example.eataway.Modelo.Pedido;
import com.example.eataway.Modelo.Prato;
import com.example.eataway.Modelo.Restaurante;
import com.example.eataway.Modelo.SingletonGestorRestaurante;
import com.example.eataway.R;
import com.example.eataway.adaptadores.ListaPratoAdaptador;


import java.util.ArrayList;


public class ListaPratoFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener, RestauranteListener {

    private ListView lvlistapratos;
    private SwipeRefreshLayout swipeRefreshLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        setHasOptionsMenu(true);
        View rootView = inflater.inflate(R.layout.fragment_lista_pratos, container, false);

        lvlistapratos = rootView.findViewById(R.id.lvlistapratos);
        //Ao clicar no prato, abrir o fragmento dos detalhes do prato

        lvlistapratos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Prato tempPrato = (Prato) parent.getItemAtPosition(position);
                System.out.println("--->" + tempPrato.getNome());
                //intent que vai encaminhar para uma atividade que vamos criar detalhesactivity
                //intent this application content + id
                //buscar no outro lado
                Log.d(getTag(),"onItemClick" + tempPrato.toString());
                Intent intent = new Intent(getContext(), DetalhesPratoActivity.class);
                intent.putExtra(DetalhesPratoActivity.DETALHES, tempPrato.getId());
                startActivityForResult(intent, DetalhesPratoActivity.EDITAR);
            }
        });



        swipeRefreshLayout = rootView.findViewById(R.id.swipe);
        swipeRefreshLayout.setOnRefreshListener(this);

        SingletonGestorRestaurante.getInstance(getContext()).setRestauranteListener(this);
        SingletonGestorRestaurante.getInstance(getContext()).getAllPratosAPI(getContext());
        return rootView;
    }


    //Função que era para ser feita com o carrinho de itens
    public void addItem(Prato prato){
        Log.d(getTag(),"onItemClick" + prato.toString());
    }


    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.menu_reserva, menu);
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
        if(listaPratos != null){
            lvlistapratos.setAdapter(new ListaPratoAdaptador(getContext(), listaPratos));
        }
    }

    @Override
    public void onRefreshListaPedidos(ArrayList<Pedido> listaPedidos) {

    }


}