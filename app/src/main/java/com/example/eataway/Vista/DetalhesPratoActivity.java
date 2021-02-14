package com.example.eataway.Vista;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.eataway.Modelo.Prato;
import com.example.eataway.Modelo.SingletonGestorRestaurante;
import com.example.eataway.R;

public class DetalhesPratoActivity extends AppCompatActivity {
    public static final String DETALHES = "prato";
    public static final int ADICIONAR = 1;
    public static final int EDITAR = 2;
    private Context context;


    private int idPrato;
    private Prato prato;

    private TextView etNome, etTipo, etPraco, etIngredientes;
    private ImageView imgprato;
    private Button btn_pedido;
    String image_path = "http://192.168.1.82/advanced1/images/comida/";
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhes_prato);

        idPrato = getIntent().getIntExtra(DETALHES,0);
       prato = SingletonGestorRestaurante.getInstance(getApplicationContext()).getPrato(idPrato);

        btn_pedido = findViewById(R.id.btn_pedido);

        etNome = findViewById(R.id.etNome);
        etTipo = findViewById(R.id.etTipo);
        etIngredientes = findViewById(R.id.etIngredientes);
        etPraco = findViewById(R.id.etPraco);
        imgprato = findViewById(R.id.pedidofinalimg);

        //  fab = findViewById(R.id.fab_detalhes);

        if (prato != null) {
            setTitle("Pedido: " + prato.getNome());
            //Mostrar detalhes, se fosse mais de um pedido(com carrinho) implementar for para cada id que era guardado
            etNome.setText(prato.getNome());
            etTipo.setText(prato.getTipo());
            etIngredientes.setText(prato.getIngr());
            etPraco.setText(prato.getPreco()+"€");
            Glide.with(this)
                    .load(image_path + prato.getImagem())
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(imgprato);
        }
      else{
            Toast.makeText(context, "Erro ao carregar pratos", Toast.LENGTH_SHORT).show();
        }

      //Ir para os detalhes do pedido final
        btn_pedido.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               int idrestauranteescolhido = SingletonGestorRestaurante.RestauranteIdFood.getInstance().idrestaurantefood;

                Intent myIntent = new Intent(DetalhesPratoActivity.this, DetalhesPedidoFinalActivity.class);
                myIntent.putExtra(DetalhesPratoActivity.DETALHES, idPrato);
                myIntent.putExtra("idrestauranteescolhido", idrestauranteescolhido);
                DetalhesPratoActivity.this.startActivity(myIntent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
       if(prato != null){
           MenuInflater menuInflater = getMenuInflater();
           menuInflater.inflate(R.menu.menu_cart, menu);
           return super.onCreateOptionsMenu(menu);
       }
       return false;
    }

}