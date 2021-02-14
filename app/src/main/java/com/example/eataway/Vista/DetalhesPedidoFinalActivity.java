package com.example.eataway.Vista;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.eataway.Modelo.Prato;
import com.example.eataway.Modelo.SingletonGestorRestaurante;
import com.example.eataway.R;

import java.util.Calendar;
import java.util.Date;

public class DetalhesPedidoFinalActivity extends AppCompatActivity {
    public static final String DETALHES = "prato";
    private Context context;


    private int idPrato, idRestaurante;
    private Prato prato;
    Date currentTime;
    private TextView etNomePedido, etPrecoPedido;
    private Button btn_f1;
    Spinner  tipopedido;


    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        context = getApplicationContext();
        setContentView(R.layout.activity_pedidofinal);
        Intent myIntent = getIntent();
        Bundle extras = myIntent.getExtras();
        if(extras != null){
             idRestaurante = extras.getInt("idrestauranteescolhido");}

        idPrato = getIntent().getIntExtra(DETALHES,0);

        prato = SingletonGestorRestaurante.getInstance(getApplicationContext()).getPrato(idPrato);
        currentTime = Calendar.getInstance().getTime();
        btn_f1 = findViewById(R.id.buttonf1);
        etNomePedido = findViewById(R.id.etNomePratoPedido);
        tipopedido = findViewById(R.id.spinnerPedido);
        etPrecoPedido = findViewById(R.id.etPrecoPratoPedido);


        if (prato != null) {
            setTitle("Pedido: " + prato.getNome());
            etNomePedido.setText(prato.getNome());
            etPrecoPedido.setText("Total:"+prato.getPreco()+"€");
        }
        else{
            Toast.makeText(context, "Erro ao carregar Pedido", Toast.LENGTH_SHORT).show();        }


        btn_f1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int idclientesingle= SingletonGestorRestaurante.LoginIdCliente.getInstance().idClienteSingleton;
                int preco = prato.getPreco();
                final String tipopedidoselected = tipopedido.getSelectedItem().toString();
                String orderStatus="0";
                String estadopedido = "Pendente";
                System.out.println("IdRestaurantePedido--->"+idRestaurante); //IdRestaurantePedido
                System.out.println("Idpratoorder--->"+idPrato); //Idpratoorder
                System.out.println("currentTime--->"+currentTime); //data
                System.out.println("tipopedidoselected--->"+tipopedidoselected); //tipopedido
                System.out.println("preco--->"+prato.getPreco()); //preco

                if(tipopedidoselected != null && tipopedidoselected.equals("Takeaway")){
                    SingletonGestorRestaurante.getInstance(context).RegistPedido(context,String.valueOf(idRestaurante),String.valueOf(idPrato),"0",String.valueOf(tipopedidoselected),String.valueOf(idclientesingle),String.valueOf(preco),String.valueOf(estadopedido));
                    Intent intent = new Intent(context, TrackActivity.class);
                    intent.putExtra("orderStatus",orderStatus);
                    System.out.println("PEDIDO X: "+tipopedidoselected);
                    startActivity(intent);
                    finish();
                }else if(tipopedidoselected != null && tipopedidoselected.equals("Reserva")){
                    // A reserva não chegou a ser implementada visto exceder o numero de atividades, explicação no relatório
                    //Intent intent = new Intent(context, DetalhesReservaFinalActivity.class);

                }
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