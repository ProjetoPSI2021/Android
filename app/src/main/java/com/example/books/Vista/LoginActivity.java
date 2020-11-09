package com.example.books.Vista;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;

import com.example.books.MenuMainActivity;
import com.example.books.R;

public class LoginActivity extends AppCompatActivity {
    private EditText edtemail, edtpass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

         edtemail = findViewById(R.id.edtEmail);
        edtpass = findViewById(R.id.edtPassword);
    }

    private boolean isEmailValido(String email){
        if(email==null){
            return false;
        }
        return Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    private boolean isPasswordValida(String pass){
        if(pass== null){
            return false;
        }
        return pass.length()>=4;
    }

    public void onClickLogin(View view) {
            String email= edtemail.getText().toString();
            String pass=edtpass.getText().toString();

            if (!isEmailValido(email)){
                edtemail.setError("Email Inválido");
                return;
            }
            if (!isPasswordValida(pass)){
                edtemail.setError("Email Inválido");
                return;
            }
            Intent intent = new Intent(getApplicationContext(), MenuMainActivity.class);
            intent.putExtra(MenuMainActivity.EMAIL,email);
            //intent.putExtra(,pass);
            startActivity(intent);

    }
}