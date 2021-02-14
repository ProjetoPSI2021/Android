package com.example.eataway.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.example.eataway.Modelo.Prato;
import com.example.eataway.Modelo.SingletonGestorRestaurante;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class PratoJsonParser {
    public static ArrayList<Prato> parserJsonPratos(JSONArray response){
    ArrayList<Prato> listaPratos = new ArrayList<>();
        int idrestingle= SingletonGestorRestaurante.RestauranteIdFood.getInstance().idrestaurantefood;

    try{
        for(int i=0; i < response.length(); i++){
            JSONObject prato = (JSONObject) response.get(i);
            int r_id = prato.getInt("r_id");
            if(r_id==idrestingle) {
                int idPratos = prato.getInt("idPratos");
                String nome = prato.getString("nome");
                String imagem = prato.getString("imagem");
                String tipo = prato.getString("tipo");
                int r_preco = prato.getInt("r_preco");
                String r_ingredientes = prato.getString("r_ingredientes");

            Prato auxPrato = new Prato(idPratos,nome,imagem,tipo,r_id,r_preco,r_ingredientes);
            listaPratos.add(auxPrato); }

        }

    }catch(JSONException e){
        e.printStackTrace();
    }

    return listaPratos;
}

public static Prato parserJsonPrato(String response){
        Prato auxPrato = null;

    try{
            JSONObject prato = new JSONObject(response);
            int idPratos = prato.getInt("id");
            int r_id = prato.getInt("r_id");
            int r_preco = prato.getInt("r_preco");
            String nome = prato.getString("nome");
            String tipo = prato.getString("tipo");
            String imagem = prato.getString("imagem");
        String r_ingredientes = prato.getString("r_ingredientes");
        auxPrato = new Prato(idPratos,nome,imagem,tipo,r_id,r_preco,r_ingredientes);
    }catch(JSONException e){
        e.printStackTrace();
    }
    return auxPrato;
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