package com.example.books;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.books.Modelo.Restaurante;
import com.example.books.Modelo.SingletonGestorRestaurante;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class DetalhesRestActivity extends AppCompatActivity {
    public static final String DETALHES_LIVRO = "restaurante";
    public static final int ADICIONAR = 1;
    public static final int EDITAR = 2;

    private int idRestaurante;
    private Restaurante restaurante;
    private FloatingActionButton fab;

    private EditText etNome, etMorada, etImagem, etSalas, etMesas, etTelefone;
    private ImageView imgimagem;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhes_livro);

        idRestaurante = getIntent().getIntExtra(DETALHES_LIVRO,0);
        restaurante = SingletonGestorRestaurante.getInstance(getApplicationContext()).getRestaurante(idRestaurante);

        etNome = findViewById(R.id.etNome);
        etMorada = findViewById(R.id.etMorada);
        etImagem = findViewById(R.id.etImagem);
        etSalas = findViewById(R.id.etSalas);
        etMesas = findViewById(R.id.etMesas);
        etTelefone = findViewById(R.id.etTelefone);
        fab = findViewById(R.id.fab_detalhes);

        if (restaurante != null) {
            setTitle("Detalhes: " + restaurante.getNome());

            etNome.setText(restaurante.getNome());
            etMorada.setText(restaurante.getMorada());
            etImagem.setText(restaurante.getimagem());
            etSalas.setText(restaurante.getSalas()+"");
            etMesas.setText(restaurante.getMesas()+"");
            etTelefone.setText(restaurante.getTelefone()+"");


            fab.setImageResource(R.drawable.ic_menu_save);
        }
        else{
            setTitle(getString(R.string.add_livro));
            fab.setImageResource(R.drawable.ic_menu_add);
        }

        fab.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                if(restaurante !=null){
                    restaurante.setNome(etNome.getText().toString());
                    restaurante.setMorada(etMorada.getText().toString());
                    restaurante.setimagem(etImagem.getText().toString());
                    restaurante.setSalas(Integer.parseInt(etSalas.getText().toString()));
                    restaurante.setMesas(Integer.parseInt(etMesas.getText().toString()));
                    restaurante.setTelefone(Integer.parseInt(etTelefone.getText().toString()));
                    SingletonGestorRestaurante.getInstance(getApplicationContext()).editarLivroBD(restaurante);
                }else{
                    Restaurante auxRestaurante = new Restaurante(0,etNome.getText().toString(),etMorada.getText().toString(),
                            etImagem.getText().toString(),
                            Integer.parseInt(etSalas.getText().toString()),
                            Integer.parseInt(etMesas.getText().toString()),
                            Integer.parseInt(etTelefone.getText().toString())

                    );
                    SingletonGestorRestaurante.getInstance(getApplicationContext()).adicionarLivroBD(auxRestaurante);
                }
                setResult(RESULT_OK);
                finish();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if(restaurante != null){
            MenuInflater menuInflater = getMenuInflater();
            menuInflater.inflate(R.menu.menu_detalhes_livro, menu);
            return super.onCreateOptionsMenu(menu);
        }
        return false;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()){
            case R.id.itemRemover:
                dialogRemover();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
    private void dialogRemover(){
        AlertDialog.Builder builder;
        builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.removerLivro)
                .setMessage("Pretende mesmo remover o Restaurante?")
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        SingletonGestorRestaurante.getInstance(getApplicationContext()).removerLivro(idRestaurante);
                        setResult(RESULT_OK);
                        finish();
                    }
                })
                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                })
                .setIcon(android.R.drawable.ic_delete)
                .show();
    }
}