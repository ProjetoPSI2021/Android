package com.example.books.Vista;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.books.MenuMainActivity;
import com.example.books.Modelo.SingletonGestorRestaurante;
import com.example.books.R;

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
    private static String URL_LOGIN ="http://192.168.1.82/advanced1/android_register_login/login.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        loading = findViewById(R.id.loading);
        email = findViewById(R.id.inputEmail);
        password = findViewById(R.id.inputPassword);
        btn_login = findViewById(R.id.btnLogin);
        link_regist = findViewById(R.id.gotoRegister);
        Bundle bundle = new Bundle();
        bundle.putString("edttext", "");

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

                                    Intent intent = new Intent(LoginActivity.this,HomeActivity.class);
                                    intent.putExtra("username",username);
                                    intent.putExtra("email", email);
                                    intent.putExtra("id", id);
                                    System.out.println("LOGIN --->" + id);
                                    SingletonGestorRestaurante.LoginIdCliente.getInstance().someValueIWantToKeep = id;
                                    SingletonGestorRestaurante.LoginEmailCliente.getInstance().emailcliente = email;

                                    startActivity(intent);



                                }
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            loading.setVisibility(View.GONE);
                            btn_login.setVisibility(View.VISIBLE);
                            Toast.makeText(LoginActivity.this, "Error" + e.toString(), Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        loading.setVisibility(View.GONE);
                        btn_login.setVisibility(View.VISIBLE);
                        Toast.makeText(LoginActivity.this, "Error" + error.toString(), Toast.LENGTH_SHORT).show();
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