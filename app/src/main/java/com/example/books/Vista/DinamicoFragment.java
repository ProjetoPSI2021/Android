package com.example.books.Vista;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.books.Modelo.Livro;
import com.example.books.Modelo.SingletonGestorLivros;
import com.example.books.R;

import java.util.ArrayList;


public class DinamicoFragment extends Fragment {
    TextView tvTitulo, tvSerie, tvAutor, tvAno;
    ImageView imgCapa;

    public DinamicoFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dinamico, container, false);
        tvTitulo = view.findViewById(R.id.tvTitulo);
        tvSerie = view.findViewById(R.id.tvSerie);
        tvAutor = view.findViewById(R.id.tvAutor);
        tvAno = view.findViewById(R.id.tvAno);
        imgCapa = view.findViewById(R.id.imgCapa);

        tvTitulo.setText("Programar em Android AMSI");
        tvSerie.setText("Android Saga");
        tvAutor.setText("Equipa AMSI");
        tvAno.setText("2020");
        imgCapa.setImageResource(R.drawable.programarandroid1);

        carregarLivro();
        return view;
    }

    private void carregarLivro() {

        ArrayList<Livro> livros = SingletonGestorLivros.getInstance().getLivros();
        if (livros.size() > 0) {
            Livro livro = livros.get(0);

            tvTitulo.setText(livro.getTitulo());
            tvSerie.setText(livro.getSerie());
            tvAutor.setText(livro.getAutor());
            tvAno.setText(" " + livro.getAno());
            imgCapa.setImageResource(livro.getCapa());
        }
    }
}