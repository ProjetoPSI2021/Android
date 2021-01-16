package com.example.books.Vista;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.books.ListaRestauranteFragment;
import com.example.books.MenuMainActivity;
import com.example.books.Modelo.PedidoBDHelper;
import com.example.books.Modelo.SingletonGestorRestaurante;
import com.example.books.R;

public class HomeActivity extends AppCompatActivity {

    private TextView username,email,idCliente;
    RelativeLayout btn_logout,btn_restaurantes;
    private PedidoBDHelper pedidosBDHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_home);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        username = findViewById(R.id.name);
        email = findViewById(R.id.email);
        idCliente = findViewById(R.id.idCliente);
        btn_logout = findViewById(R.id.btn_logout);
        btn_restaurantes = findViewById(R.id.btn_restaurantes);
        SharedPreferences sharedPref = getSharedPreferences("data",MODE_PRIVATE);

        String emailshared =  sharedPref.getString("emailshared", "");
        String usernameshared = sharedPref.getString("usernameshared", "");
        int idclishared = sharedPref.getInt("idclishared", 0);

        Intent intent = getIntent();

       final int temp = idclishared;
        SingletonGestorRestaurante.LoginIdCliente.getInstance().idClienteSingleton = idclishared;
        SingletonGestorRestaurante.LoginIdCliente.getInstance().emailcliente = emailshared;
        username.setText(usernameshared);
       // email.setText(emailshared);
        idCliente.setText("ID:" + idclishared);


        btn_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btn_restaurantes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent myIntent = new Intent(HomeActivity.this, MenuMainActivity.class);
               myIntent.putExtra("id", temp);
                System.out.println("--->"+temp);
                HomeActivity.this.startActivity(myIntent);



            }
        });

        btn_logout.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                startActivityForResult(new Intent(HomeActivity.this,LoginActivity.class), 0);
            }
        });
    }


}
