package com.example.eataway.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.example.eataway.Modelo.Restaurante;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class RestauranteJsonParser {
    public static ArrayList<Restaurante> parserJsonRestaurantes(JSONArray response){
    ArrayList<Restaurante> listaRestaurantes = new ArrayList<>();

    try{
        for(int i=0; i < response.length(); i++){
            JSONObject rest = (JSONObject) response.get(i);
            int idRestaurante = rest.getInt("idRestaurante");
            String nome = rest.getString("nome");
            String morada = rest.getString("morada");
            String imagem = rest.getString("imagem");
            int salas = rest.getInt("salas");
            int mesas = rest.getInt("mesas");
            int telefone = rest.getInt("telefone");

            Restaurante auxRestaurante = new Restaurante(idRestaurante,nome,morada,imagem,salas,mesas,telefone);
            listaRestaurantes.add(auxRestaurante);

        }
    }catch(JSONException e){
        e.printStackTrace();
    }

    return listaRestaurantes;
}

public static Restaurante parserJsonRest(String response){
        Restaurante auxRestaurante = null;

    try{
            JSONObject restaurante = new JSONObject(response);
            int idRestaurante = restaurante.getInt("id");
            int salas = restaurante.getInt("salas");
        int mesas = restaurante.getInt("salas");
        int telefone = restaurante.getInt("salas");
            String nome = restaurante.getString("nome");
            String morada = restaurante.getString("morada");
            String imagem = restaurante.getString("imagem");
             auxRestaurante = new Restaurante(idRestaurante,nome,morada,imagem,salas,mesas,telefone);
    }catch(JSONException e){
        e.printStackTrace();
    }
    return auxRestaurante;
}

public static String parserJsonLogin(String response){
        String token = null;
        try{
            JSONObject login = new JSONObject(response);
            if(login.getBoolean("success"))
                token = login.getString("token");
        }catch(JSONException e){
            e.printStackTrace();
        }
        return token;
}

public static boolean isConnectionInternet(Context context){
    ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
    NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
    return networkInfo != null && networkInfo.isConnected();
}

}