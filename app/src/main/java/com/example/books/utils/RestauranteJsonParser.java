package com.example.books.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.example.books.Modelo.Restaurante;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class RestauranteJsonParser {
    public static ArrayList<Restaurante> parserJsonLivros(JSONArray response){
    ArrayList<Restaurante> listaRestaurantes = new ArrayList<>();

    try{
        for(int i=0; i < response.length(); i++){
            JSONObject livro = (JSONObject) response.get(i);
            int idRestaurante = livro.getInt("idRestaurante");
            String nome = livro.getString("nome");
            String morada = livro.getString("morada");
            String imagem = livro.getString("imagem");
            int salas = livro.getInt("salas");
            int mesas = livro.getInt("mesas");
            int telefone = livro.getInt("telefone");

            Restaurante auxRestaurante = new Restaurante(idRestaurante,nome,morada,imagem,salas,mesas,telefone);
            listaRestaurantes.add(auxRestaurante);

        }
    }catch(JSONException e){
        e.printStackTrace();
    }

    return listaRestaurantes;
}

public static Restaurante parserJsonLivro(String response){
        Restaurante auxRestaurante = null;

    try{
            JSONObject livro = new JSONObject(response);
            int idRestaurante = livro.getInt("id");
            int salas = livro.getInt("salas");
        int mesas = livro.getInt("salas");
        int telefone = livro.getInt("salas");
            String nome = livro.getString("nome");
            String morada = livro.getString("morada");
            String imagem = livro.getString("imagem");
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