package com.example.books;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.books.Modelo.Livro;
import com.example.books.Modelo.SingletonGestorLivros;
import com.example.books.adaptadores.ListaLivroAdaptador;

import java.util.ArrayList;

// git comentario teste

public class ListaLivrosFragment extends Fragment {

    private ListView lvlistaLivros;
    private ArrayList<Livro> listaLivros;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_lista_livros, container, false);

        listaLivros = SingletonGestorLivros.getInstance().getLivros();

        lvlistaLivros = rootView.findViewById(R.id.lvListaLivros);

        lvlistaLivros.setAdapter(new ListaLivroAdaptador(getContext(), listaLivros));

        lvlistaLivros.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Livro tempLivro = (Livro) parent.getItemAtPosition(position);
                System.out.println("--->" + tempLivro.getTitulo());
                //intent que vai encaminhar para uma atividade que vamos criar detalheslivroactivity
                //intent this application content + livro ou id do livro
                //buscar livro no outro lado
                Intent intent = new Intent(getContext(), DetalhesLivroActivity.class);
                intent.putExtra(DetalhesLivroActivity.DETALHES_LIVRO, tempLivro.getId());
                startActivity(intent);
            }
        });

        return rootView;
    }
}