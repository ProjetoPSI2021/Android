package com.example.eataway.Vista;

import android.content.Intent;
import android.content.SharedPreferences;
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

public class EditActivity extends AppCompatActivity {

    private static final String TAG = EditActivity.class.getSimpleName();
    private EditText usernameedit, emailedit;
    private Button btn_edit;
    private ProgressBar loading;
    private static String URL_EDIT = "http://192.168.1.82/advanced1/api/methods/edit_detail.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        usernameedit = findViewById(R.id.usernameedit);
        emailedit = findViewById(R.id.emailedit);
        btn_edit = findViewById(R.id.btn_edit);

        final SharedPreferences sharedPref = getSharedPreferences("data",MODE_PRIVATE);
        String emailshared =  sharedPref.getString("emailshared", "");
        String usernameshared = sharedPref.getString("usernameshared", "");


        usernameedit.setText(usernameshared);
        emailedit.setText(emailshared);

        btn_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SaveEditDetail();
                Intent myIntent = new Intent(EditActivity.this, HomeActivity.class);
                EditActivity.this.startActivity(myIntent);
            }
        });
    }
        //Salvar os dados apos editar
        public void SaveEditDetail(){
            Intent myIntent = getIntent();
            final int getid = myIntent.getIntExtra("id", 0);
            final String getidstring = String.valueOf(getid);
            final String usernameeditado = usernameedit.getText().toString();
            final String emaileditado = emailedit.getText().toString();
            StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_EDIT,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {

                            JSONObject jsonObject = null;
                            try {
                                jsonObject = new JSONObject(response);
                                String success = jsonObject.getString("success");

                                if(success.equals("1")){
                                    Toast.makeText(EditActivity.this, "Success!", Toast.LENGTH_SHORT).show();
                                    final SharedPreferences sharedPref = getSharedPreferences("data",MODE_PRIVATE);
                                    SharedPreferences.Editor e = sharedPref.edit();
                                    e.putString("emailshared", emaileditado);
                                    e.putString("usernameshared", usernameeditado);
                                    e.commit();
                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
                                Toast.makeText(EditActivity.this, "Erro - Não foi possivel obter dados, confira a sua conexão à internet ", Toast.LENGTH_SHORT).show();
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(EditActivity.this, "Erro - Não foi possivel obter dados, confira a sua conexão à internet" , Toast.LENGTH_SHORT).show();

                        }
                    })
            {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<>();
                    params.put("username", usernameeditado);
                    params.put("email", emaileditado);
                    params.put("idcliente",getidstring);
                    return params;
                }
            };
            RequestQueue requestQueue = Volley.newRequestQueue(this);
            requestQueue.add(stringRequest);
        }



}
