package com.example.eataway.Vista;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.eataway.Modelo.PedidoBDHelper;
import com.example.eataway.Modelo.SingletonGestorRestaurante;
import com.example.eataway.R;

public class HomeActivity extends AppCompatActivity {

    private TextView username,email,idCliente;
    RelativeLayout btn_logout,btn_restaurantes,btneditprofile;
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
        btneditprofile = findViewById(R.id.btneditprofile);
        final SharedPreferences sharedPref = getSharedPreferences("data",MODE_PRIVATE);
        //Retirar valores após Login
         final String emailshared =  sharedPref.getString("emailshared", "");
        final String usernameshared = sharedPref.getString("usernameshared", "");
        int idclishared = sharedPref.getInt("idclishared", 0);


       final int temp = idclishared;

        SingletonGestorRestaurante.LoginIdCliente.getInstance().idClienteSingleton = idclishared;
        SingletonGestorRestaurante.LoginIdCliente.getInstance().emailcliente = emailshared;

        //Após edição do nome, o nome não muda instantaneamente mas ao mudar de atividade ele muda
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
                System.out.println("--->o"+temp);
                myIntent.putExtra("atividade", 0);

                HomeActivity.this.startActivity(myIntent);

            }
        });

        btneditprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent myIntent = new Intent(HomeActivity.this, EditActivity.class);
                myIntent.putExtra("id", temp);
                myIntent.putExtra("email", emailshared);
                myIntent.putExtra("username", usernameshared);

                System.out.println("id--->"+temp);
                System.out.println("email--->"+emailshared);
                System.out.println("username--->"+usernameshared);

                myIntent.putExtra("atividade", 0);
                HomeActivity.this.startActivity(myIntent);

            }
        });




        btn_logout.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                SharedPreferences.Editor e = sharedPref.edit();
                e.putString("emailshared", "");
                e.putString("usernameshared", "");
                e.commit();
                SharedPreferences login = getSharedPreferences("data",MODE_PRIVATE);
                SharedPreferences.Editor prefLoginEditor = login.edit();
                //Caso isLogged = 0 significa que não está "logado", 1= Logado
                prefLoginEditor.putInt("isLogged",0);
                prefLoginEditor.commit();
                Intent logoutact = new Intent(HomeActivity.this, LoginActivity.class);
                HomeActivity.this.startActivity(logoutact);
            }
        });
    }


}
