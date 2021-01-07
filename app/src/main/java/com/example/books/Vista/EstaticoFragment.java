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
    TextView tvNome, tvMorada, tvImagem, tvSalas;
    ImageView imgimagem;

    public EstaticoFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.item_lista_livro, container, false);
       tvNome = view.findViewById(R.id.tvNome);
        tvMorada = view.findViewById(R.id.tvMorada);
        tvImagem = view.findViewById(R.id.tvImagem);
        tvSalas = view.findViewById(R.id.tvSalas);
        imgimagem = view.findViewById(R.id.imgimagem);

        tvNome.setText("Programar em Android AMSI");
        tvMorada.setText("Android Saga");
        tvImagem.setText("Equipa AMSI");
        tvSalas.setText("2020");
        imgimagem.setImageResource(R.drawable.programarandroid1);

        return view;
    }
}