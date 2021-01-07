package com.example.books.Vista;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.books.ListaRestauranteFragment;
import com.example.books.MenuMainActivity;
import com.example.books.R;

public class HomeActivity extends AppCompatActivity {

    private TextView username,email,idCliente;
    private Button btn_logout,btn_restaurantes;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_home);

        username = findViewById(R.id.name);
        email = findViewById(R.id.email);
        idCliente = findViewById(R.id.idCliente);
        btn_logout = findViewById(R.id.btn_logout);
        btn_restaurantes = findViewById(R.id.btn_restaurantes);

        Intent intent = getIntent();
        String extraName = intent.getStringExtra("username");
        String extraEmail = intent.getStringExtra("email");
        final int temp = intent.getIntExtra("id",0);

        System.out.println("HOME --->" + temp);
        username.setText(extraName);
        email.setText(extraEmail);
        idCliente.setText("ID:" + temp);

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

    }

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.activity_main_drawer, menu);
        return true;
    }
}
