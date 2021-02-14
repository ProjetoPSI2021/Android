package com.example.eataway.Vista;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SearchView;

import com.example.eataway.Listener.RestauranteListener;
import com.example.eataway.Modelo.Pedido;
import com.example.eataway.Modelo.Prato;
import com.example.eataway.Modelo.Restaurante;
import com.example.eataway.Modelo.SingletonGestorRestaurante;
import com.example.eataway.R;
import com.example.eataway.adaptadores.ListaRestauranteAdaptador;


import java.util.ArrayList;


public class ListaRestauranteFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener, RestauranteListener {

    private ListView lvlistarestaurantes;
    private SearchView searchView;
    private SwipeRefreshLayout swipeRefreshLayout;
    public Context context;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        View rootView = inflater.inflate(R.layout.fragment_lista_rest, container, false);

        lvlistarestaurantes = rootView.findViewById(R.id.lvlistarestaurantes);

        //Ao clicar no restaurante, abrir o fragmento dos pratos desse mesmo restaurante
        lvlistarestaurantes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Restaurante tempRestaurante = (Restaurante) parent.getItemAtPosition(position);

                SingletonGestorRestaurante.RestauranteIdFood.getInstance().idrestaurantefood = tempRestaurante.getId();
                Fragment listaprato = new ListaPratoFragment();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.contentFragment, listaprato );
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });



        swipeRefreshLayout = rootView.findViewById(R.id.swipe);
        swipeRefreshLayout.setOnRefreshListener(this);

        SingletonGestorRestaurante.getInstance(getContext()).setRestauranteListener(this);
        SingletonGestorRestaurante.getInstance(getContext()).getAllRestAPI(getContext());
        return rootView;
    }


    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        //Pesquisa do nome de restaurante
        inflater.inflate(R.menu.menu_pesquisa, menu);

        MenuItem itemPesquisa = menu.findItem(R.id.itemPesquisa);

        searchView = (SearchView) itemPesquisa.getActionView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {

                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                ArrayList<Restaurante> tempRestaurantes = new ArrayList();
                for(Restaurante restaurante : SingletonGestorRestaurante.getInstance(getContext()).getRestaurantesBD())
                    if(restaurante.getNome().toLowerCase().contains(s.toLowerCase())){
                       tempRestaurantes.add(restaurante);
                    }
                lvlistarestaurantes.setAdapter(new ListaRestauranteAdaptador(getContext(), tempRestaurantes));
                return true;
            }

        });

        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public void onRefresh() {
        SingletonGestorRestaurante.getInstance(getContext()).getAllRestAPI(getContext());
        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void onRefreshListaRestaurantes(ArrayList<Restaurante> listaRestaurantes) {
        if(listaRestaurantes != null){
            lvlistarestaurantes.setAdapter(new ListaRestauranteAdaptador(getContext(), listaRestaurantes));
        }
    }

    @Override
    public void onRefreshListaPratos(ArrayList<Prato> listaPratos) {

    }

    @Override
    public void onRefreshListaPedidos(ArrayList<Pedido> listaPedidos) {

    }


}