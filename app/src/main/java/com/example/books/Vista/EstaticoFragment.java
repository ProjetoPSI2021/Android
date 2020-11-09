package com.example.books.Vista;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.books.R;
public class EstaticoFragment extends Fragment {
    TextView tvTitulo, tvSerie, tvAutor, tvAno;
    ImageView imgCapa;

    public EstaticoFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.item_lista_livro, container, false);
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

        return view;
    }
}