package com.example.books;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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
import com.example.books.adaptadores.ListaPratoAdaptador;
import com.example.books.adaptadores.ListaRestauranteAdaptador;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;


public class ListaPratoFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener, RestauranteListener {

    private ListView lvlistapratos;
    private FloatingActionButton fab;
    private ArrayList<Prato> listaPratos;
    private SearchView searchView;
    private SwipeRefreshLayout swipeRefreshLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        setHasOptionsMenu(true);
        View rootView = inflater.inflate(R.layout.fragment_lista_pratos, container, false);

        lvlistapratos = rootView.findViewById(R.id.lvlistapratos);
        fab = rootView.findViewById(R.id.fab);
       // lvlistarestaurantes.setAdapter(new ListaRestauranteAdaptador(getContext(), listaRestaurantes));

        lvlistapratos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Prato tempPrato = (Prato) parent.getItemAtPosition(position);
                System.out.println("--->" + tempPrato.getNome());
                //intent que vai encaminhar para uma atividade que vamos criar detalheslivroactivity
                //intent this application content + livro ou id do livro
                //buscar livro no outro lado
                Log.d(getTag(),"onItemClick" + tempPrato.toString());
                Intent intent = new Intent(getContext(), DetalhesRestauranteActivity.class);
                intent.putExtra(DetalhesRestauranteActivity.DETALHES_LIVRO, tempPrato.getId());
                startActivityForResult(intent, DetalhesRestauranteActivity.EDITAR);
            }
        });

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), DetalhesRestauranteActivity.class);
                startActivityForResult(intent, DetalhesRestauranteActivity.ADICIONAR);
            }
        });

        swipeRefreshLayout = rootView.findViewById(R.id.swipe);
        swipeRefreshLayout.setOnRefreshListener(this);

        SingletonGestorRestaurante.getInstance(getContext()).setRestauranteListener(this);
        SingletonGestorRestaurante.getInstance(getContext()).getAllPratosAPI(getContext());
        return rootView;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (resultCode == Activity.RESULT_OK){
            switch (requestCode){
                case DetalhesRestauranteActivity.ADICIONAR:
                    SingletonGestorRestaurante.getInstance(getContext()).getAllLivrosAPI(getContext());
                    Snackbar.make(getView(), R.string.rest_add_suc,Snackbar.LENGTH_LONG).show();
                    break;
                    case DetalhesRestauranteActivity.EDITAR:
                    //listaRestaurantes = SingletonGestorRestaurante.getInstance(getContext()).getRestaurantesBD();
                    Snackbar.make(getView(), R.string.edited_suc,Snackbar.LENGTH_LONG).show();
                    break;
            }
        }
    }

    public void addItem(Prato prato){
        Log.d(getTag(),"onItemClick" + prato.toString());
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.menu_cart, menu);


        /*searchView = (SearchView) itemPesquisa.getActionView();

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
                lvlistapratos.setAdapter(new ListaRestauranteAdaptador(getContext(), tempRestaurantes));
                return true;
            }

        });

        super.onCreateOptionsMenu(menu, inflater);*/
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
        if(listaPratos != null){
            lvlistapratos.setAdapter(new ListaPratoAdaptador(getContext(), listaPratos));
        }
    }

    @Override
    public void onRefreshListaPedidos(ArrayList<Pedido> listaPedidos) {

    }

    @Override
    public void onUpdateListaRestaurantesBD(Restaurante restaurante, int operacao) {

    }
}