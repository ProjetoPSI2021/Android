package com.example.eataway.Vista;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.eataway.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {

    private EditText username, email, password, c_password;
    private Button btn_regist;
    private ProgressBar loading;
    private static String URL_REGIST = "http://192.168.1.82/advanced1/api/methods/register.php";

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        username = findViewById(R.id.username);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        c_password = findViewById(R.id.c_password);
        btn_regist = findViewById(R.id.btn_regist);
        //Validar os campos para registo
        btn_regist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!validatePassword() | !validateEmail() | !validateUsername() | !validatePasswordConf()){
                   return;
                }else{
                Regist();}
            }
        });


    }

    private Boolean validateUsername() {
        String val = username.getText().toString();

        if (val.isEmpty()) {
            username.setError("Campo não pode estar em branco");
            return false;
        } else if (val.length() >= 15) {
            username.setError("Username demasiado grande");
            return false;
        } else if (val.length() <= 5) {
            username.setError("Username muito pequeno");
            return false;
        } else {
            username.setError(null);
            return true;
        }
    }

    private Boolean validateEmail() {
        String val = email.getText().toString();
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

        if (val.isEmpty()) {
            email.setError("Campo não pode estar em branco");
            return false;
        } else if (!val.matches(emailPattern)) {
            email.setError("Email inválido");
            return false;
        } else {
            email.setError(null);
            return true;
        }
    }

    private Boolean validatePassword() {
        String val = password.getText().toString();
        String passwordVal = "^" +
                //"(?=.*[0-9])" +         //at least 1 digit
                //"(?=.*[a-z])" +         //at least 1 lower case letter
                //"(?=.*[A-Z])" +         //at least 1 upper case letter
                "(?=.*[a-zA-Z])" +      //any letter
                ".{4,}" +               //at least 4 characters
                "$";

        if (val.isEmpty()) {
            password.setError("Campo não pode estar em branco");
            return false;
        } else if (!val.matches(passwordVal)) {
            password.setError("Password é muito fraca");
            return false;
        } else {
            password.setError(null);
            return true;
        }
    }

    private Boolean validatePasswordConf() {
        String val = c_password.getText().toString();
        String passwordVal = "^" +
                //"(?=.*[0-9])" +         //at least 1 digit
                //"(?=.*[a-z])" +         //at least 1 lower case letter
                //"(?=.*[A-Z])" +         //at least 1 upper case letter
                "(?=.*[a-zA-Z])" +      //any letter
                ".{4,}" +               //at least 4 characters
                "$";

        if (val.isEmpty()) {
            c_password.setError("Campo não pode estar em branco");
            return false;
        } else if (!val.matches(passwordVal)) {
            c_password.setError("Password é muito fraca");
            return false;
        } else if (!val.equals(password.getText().toString())) {
            c_password.setError("As passwords não coincidem");
            return false;
        } else {
            c_password.setError(null);
            return true;
        }
    }

    //Registar utilizador
    private void Regist(){


        final String username = this.username.getText().toString().trim();
        final String email = this.email.getText().toString().trim();
        final String password = this.password.getText().toString().trim();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_REGIST,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                    try{
                        JSONObject jsonObject = new JSONObject(response);
                        String success = jsonObject.getString("success");
                        if(success.equals("1")){
                            Toast.makeText(RegisterActivity.this, "Registo completo!",Toast.LENGTH_SHORT).show();
                            Intent myIntent = new Intent(RegisterActivity.this, LoginActivity.class);
                            RegisterActivity.this.startActivity(myIntent);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(RegisterActivity.this, "Erro ao registar!" + e.toString(),Toast.LENGTH_SHORT).show();
                    }
                    }
                },

                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(RegisterActivity.this, "Erro - Verifique a sua conexão à internet",Toast.LENGTH_SHORT).show();
                    }
                })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("username", username);
                params.put("email", email);
                params.put("password", password);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
}
