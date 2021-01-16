package com.example.books;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.books.Modelo.Prato;
import com.example.books.Modelo.Restaurante;
import com.example.books.Modelo.SingletonGestorRestaurante;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static java.security.AccessController.getContext;

public class DetalhesPedidoFinalActivity extends AppCompatActivity {
    public static final String DETALHES_LIVRO = "prato";
    public static final int ADICIONAR = 1;
    public static final int EDITAR = 2;
    private Context context;


    private int idPrato, idRestaurante;
    private Prato prato;
    private FloatingActionButton fab;
    Date currentTime;
    private TextView etNomePedido, etMorada, etImagem, etSalas, etMesas, etPrecoPedido;
    private ImageView imgpratopedido;
    private Button btn_f1;
    Spinner  tipopedido;


    String image_path = "http://192.168.1.82/advanced1/images/comida/";
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        context = getApplicationContext();
        setContentView(R.layout.activity_pedidofinal);
        Intent myIntent = getIntent();
        Bundle extras = myIntent.getExtras();
        if(extras != null){
             idRestaurante = extras.getInt("idrestauranteescolhido");}

        idPrato = getIntent().getIntExtra(DETALHES_LIVRO,0);

        prato = SingletonGestorRestaurante.getInstance(getApplicationContext()).getPrato(idPrato);
        currentTime = Calendar.getInstance().getTime();
        btn_f1 = findViewById(R.id.buttonf1);
        etNomePedido = findViewById(R.id.etNomePratoPedido);
        tipopedido = findViewById(R.id.spinnerPedido);
        final String tipopedidoselected = tipopedido.getSelectedItem().toString();

        // etImagem = findViewById(R.id.etImagem);
        // etSalas = findViewById(R.id.etSalas);
        //  etMesas = findViewById(R.id.etMesas);
        etPrecoPedido = findViewById(R.id.etPrecoPratoPedido);
        /*JSONObject postData = new JSONObject();
        try {
            postData.put("name", "Jonathan");
            postData.put("job", "Software Engineer");

        } catch (JSONException e) {
            e.printStackTrace();
        }*/
        //  fab = findViewById(R.id.fab_detalhes);

        if (prato != null) {
            setTitle("Pedido: " + prato.getNome());

            etNomePedido.setText(prato.getNome());
            //etMorada.setText(prato.getTipo());
            // etImagem.setText(restaurante.getimagem());
            //  etSalas.setText(restaurante.getSalas()+"");
            //  etMesas.setText(restaurante.getMesas()+"");
            etPrecoPedido.setText("Total:"+prato.getPreco()+"€");

            //  fab.setImageResource(R.drawable.ic_menu_save);
        }
        else{
            setTitle(getString(R.string.add_livro));
            // fab.setImageResource(R.drawable.ic_menu_add);
        }

      /*fab.setOnClickListener(new View.OnClickListener(){
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
      });*/
        btn_f1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*int idrestauranteescolhido = SingletonGestorRestaurante.RestauranteIdFood.getInstance().idrestaurantefood;

                Intent myIntent = new Intent(DetalhesPedidoFinalActivity.this, DetalhesPedidoFinalActivity.class);
                myIntent.putExtra(DetalhesRestauranteActivity.DETALHES_LIVRO, idPrato);
                myIntent.putExtra("idrestauranteescolhido", idrestauranteescolhido);
                DetalhesRestauranteActivity.this.startActivity(myIntent);*/
                int idclientesingle= SingletonGestorRestaurante.LoginIdCliente.getInstance().idClienteSingleton;
                int preco = prato.getPreco();
                String orderStatus="0";
                String estadopedido = "Pendente";
                System.out.println("IdRestaurantePedido--->"+idRestaurante); //IdRestaurantePedido
                System.out.println("Idpratoorder--->"+idPrato); //Idpratoorder
                System.out.println("currentTime--->"+currentTime); //data
                System.out.println("tipopedidoselected--->"+tipopedidoselected); //tipopedido
                System.out.println("preco--->"+prato.getPreco()); //tipopedido

                SingletonGestorRestaurante.getInstance(context).RegistPedido(context,String.valueOf(idRestaurante),String.valueOf(idPrato),"1",String.valueOf(tipopedidoselected),String.valueOf(idclientesingle),String.valueOf(preco),String.valueOf(estadopedido));
                Intent intent = new Intent(context, TrackActivity.class);
                //intent.putExtra(TrackActivity.DETALHES_LIVRO, tempPrato.getId());
                intent.putExtra("orderStatus",orderStatus);
                startActivity(intent);
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

    /*public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()){
            case R.id.itemRemover:
                dialogRemover();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }*/
    /*private void dialogRemover(){
        AlertDialog.Builder builder;
        builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.removerLivro)
                .setMessage("Pretende mesmo remover o Restaurante?")
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        SingletonGestorRestaurante.getInstance(getApplicationContext()).removerLivro(idPrato);
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
    }*/
}