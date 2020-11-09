package com.example.books;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.books.Modelo.Livro;
import com.example.books.Modelo.SingletonGestorLivros;

public class DetalhesLivroActivity extends AppCompatActivity {
    public static final String DETALHES_LIVRO = "livro";
    private int idLivro;
    private Livro livro;

    private EditText etTitulo, etSerie, etAutor, etAno;
    private ImageView imgCapa;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhes_livro);

        idLivro = getIntent().getIntExtra(DETALHES_LIVRO,0);
       livro = SingletonGestorLivros.getInstance().getLivro(idLivro);

       etTitulo = findViewById(R.id.etTitulo);
        etSerie = findViewById(R.id.etSerie);
        etAutor = findViewById(R.id.etAutor);
        etAno = findViewById(R.id.etAno);
        imgCapa = findViewById(R.id.imgCapa);

        setTitle("Detalhes: " + livro.getTitulo());

        etTitulo.setText(livro.getTitulo());
        etSerie.setText(livro.getSerie());
        etAutor.setText(livro.getAutor());
        etAno.setText(livro.getAno()+"");
        imgCapa.setImageResource(livro.getCapa());
    }
}