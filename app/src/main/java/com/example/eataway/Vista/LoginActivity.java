package com.example.eataway.Vista;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.eataway.Modelo.SingletonGestorRestaurante;
import com.example.eataway.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {
    private EditText email, password;
    private Button btn_login;
    private TextView link_regist;
    private ProgressBar loading;

    private static String URL_LOGIN ="http://192.168.1.82/advanced1/api/methods/login.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        email = findViewById(R.id.inputEmail);
        password = findViewById(R.id.inputPassword);
        btn_login = findViewById(R.id.btnLogin);
        link_regist = findViewById(R.id.gotoRegister);
        Bundle bundle = new Bundle();
        bundle.putString("edttext", "");
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences login = getSharedPreferences("data",MODE_PRIVATE);


        int number = login.getInt("isLogged", 0);
        /*SharedPreferences.Editor editor = sharedPref.edit();
        editor.clear();
        editor.commit(); -> Para apagar os dados da sharedprefences login e fazer login novamente*/

        if(number == 0) {

        } else {

            Intent intent = new Intent(LoginActivity.this,HomeActivity.class);
            startActivity(intent);
        }

        //Botão para fazer login com validações para cada campo
       btn_login.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {

               String mEmail = email.getText().toString().trim();
               String mPass = password.getText().toString().trim();

               if(!mEmail.isEmpty() || !mPass.isEmpty()){
                   Login(mEmail,mPass);
               } else{

                   email.setError("Please insert email");
                   password.setError("Please insert password");
               }
           }
       });
       link_regist.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
           }
       });
    }

    //Houve várias tentativas para fazer a função no singleton mas não foi possivel devido a problemas de retornar valores e intents
    private void Login(final String email, final String password) {


        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_LOGIN,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String success = jsonObject.getString("success");
                            JSONArray jsonArray = jsonObject.getJSONArray("login");

                            if(success.equals("1")){
                                for (int i = 0; i < jsonArray.length(); i++){

                                    JSONObject object = jsonArray.getJSONObject(i);

                                    String username = object.getString("username").trim();
                                    String email = object.getString("email").trim();
                                    Integer id = object.getInt("idCliente");

                                    //Levar os valores para a seguinte atividade
                                    Intent intent = new Intent(LoginActivity.this,HomeActivity.class);
                                    intent.putExtra("username",username);
                                    intent.putExtra("email", email);
                                    intent.putExtra("id", id);
                                    System.out.println("LOGIN --->" + id);
                                    SingletonGestorRestaurante.LoginIdCliente.getInstance().idClienteSingleton = id;
                                    SingletonGestorRestaurante.LoginIdCliente.getInstance().emailcliente = email;
                                   SharedPreferences sharedPref = getSharedPreferences("data",MODE_PRIVATE);
                                    SharedPreferences login = getSharedPreferences("data",MODE_PRIVATE);
                                    SharedPreferences.Editor e = sharedPref.edit();
                                    e.putString("emailshared", email);
                                    e.putInt("idclishared", id);
                                    e.putString("usernameshared", username);
                                    e.commit();
                                    SharedPreferences.Editor prefLoginEditor = login.edit();
                                    prefLoginEditor.putInt("isLogged",1);
                                    prefLoginEditor.commit();
                                    startActivity(intent);
                                }
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            btn_login.setVisibility(View.VISIBLE);
                            Toast.makeText(LoginActivity.this, "Dados inválidos", Toast.LENGTH_SHORT).show();

                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        String message = null;
                        //Mensagens de erro
                        Toast.makeText(LoginActivity.this, "Erro - Verifique a sua conexão à internet", Toast.LENGTH_SHORT).show();
                    }
                })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("email", email);
                params.put("password", password);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }




}