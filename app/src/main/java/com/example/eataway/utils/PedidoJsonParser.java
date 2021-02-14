package com.example.eataway.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.example.eataway.Modelo.Pedido;
import com.example.eataway.Modelo.SingletonGestorRestaurante;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class PedidoJsonParser {
    public static ArrayList<Pedido> parserJsonPedidos(JSONArray response){
    ArrayList<Pedido> listaPedidos = new ArrayList<>();
    try{
        System.out.println("TESTE SINGLETON VALUE --->" + SingletonGestorRestaurante.LoginIdCliente.getInstance().idClienteSingleton);
        int idclientesingle= SingletonGestorRestaurante.LoginIdCliente.getInstance().idClienteSingleton;
        for(int i=0; i < response.length(); i++){
            JSONObject pedido = (JSONObject) response.get(i);
            int id_clientes = pedido.getInt("id_clientes");
            if(id_clientes == idclientesingle){
                int idpedido = pedido.getInt("idpedido");
                int id_reserva = pedido.getInt("id_reserva");
                String data = pedido.getString("data");
                String tipo = pedido.getString("tipo");
                int preco = pedido.getInt("preco");
                int idpratoorder = pedido.getInt("idpratoorder");
                int idrestaurantepedido = pedido.getInt("idrestaurantepedido");
                String estadopedido = pedido.getString("estadopedido");

            Pedido auxPedido = new Pedido(idpedido,id_reserva,data,tipo,id_clientes,preco,idpratoorder,idrestaurantepedido,estadopedido);
            listaPedidos.add(auxPedido);}
        }


    }catch(JSONException e){
        e.printStackTrace();
    }

    return listaPedidos;
}

    public static ArrayList<Pedido> parserJsonPedidoTracker(JSONArray response){
        ArrayList<Pedido> listaPedidos = new ArrayList<>();
        try{
            System.out.println("TESTE SINGLETON VALUE --->" + SingletonGestorRestaurante.LoginIdCliente.getInstance().idClienteSingleton);
            int idclientesingle= SingletonGestorRestaurante.LoginIdCliente.getInstance().idClienteSingleton;
            String estadopedidosingle = SingletonGestorRestaurante.LoginIdCliente.getInstance().estadopedido;

            for(int i=0; i < response.length(); i++){
                JSONObject pedido = (JSONObject) response.get(i);
                int id_clientes = pedido.getInt("id_clientes");
                String estadopedido = pedido.getString("estadopedido");
                if(id_clientes == idclientesingle){
                    int idpedido = pedido.getInt("idpedido");
                    int id_reserva = pedido.getInt("id_reserva");
                    String data = pedido.getString("data");
                    String tipo = pedido.getString("tipo");
                    int preco = pedido.getInt("preco");
                    int idpratoorder = pedido.getInt("idpratoorder");
                    int idrestaurantepedido = pedido.getInt("idrestaurantepedido");



                    Pedido auxPedido = new Pedido(idpedido,id_reserva,data,tipo,id_clientes,preco,idpratoorder,idrestaurantepedido,estadopedido);
                    SingletonGestorRestaurante.LoginIdCliente.getInstance().estadopedido = estadopedido;
                    System.out.println("TESTE ESTADO PEDIDO--->"+estadopedido);
                    listaPedidos.add(auxPedido);}
            }


        }catch(JSONException e){
            e.printStackTrace();
        }

        return listaPedidos;
    }

public static Pedido parserJsonPedido(String response){
        Pedido auxPedido = null;

    try{
            JSONObject pedido = new JSONObject(response);
        int idpedido = pedido.getInt("idpedido");
        int id_reserva = pedido.getInt("id_reserva");
        String data = pedido.getString("data");
        String tipo = pedido.getString("tipo");
        int id_clientes = pedido.getInt("id_clientes");
        int preco = pedido.getInt("preco");
        int idpratoorder = pedido.getInt("idpratoorder");
        int idrestaurantepedido = pedido.getInt("idrestaurantepedido");
        String estadopedido = pedido.getString("estadopedido");
        auxPedido = new Pedido(idpedido,id_reserva,data,tipo,id_clientes,preco,idpratoorder,idrestaurantepedido,estadopedido);
    }catch(JSONException e){
        e.printStackTrace();
    }
    return auxPedido;
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