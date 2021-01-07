package com.example.books.Modelo;
import android.content.Context;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.books.Listener.RestauranteListener;
import com.example.books.utils.PedidoJsonParser;
import com.example.books.utils.PratoJsonParser;
import com.example.books.utils.RestauranteJsonParser;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SingletonGestorRestaurante implements RestauranteListener {

    private ArrayList<Restaurante> restaurantes;
    private ArrayList<Prato> pratos;
    private ArrayList<Pedido> pedidos;
    private RestauranteBDHelper livrosBDHelper = null;
    private static RequestQueue volleyQueue = null;
    private RestauranteListener restauranteListener;
    private RestauranteListener pratoListener;
    private static final String mUrlAPIRestaurantes = "http://192.168.1.82/advanced1/api/web/restaurante";
    private static final String mUrlAPIPratos= "http://192.168.1.82/advanced1/api/web/prato";
    private static final String mUrlAPIPedidos= "http://192.168.1.82/advanced1/api/web/pedido";


    private static final String mUrlAPILogin = "http://amsi.dei.estg.ipleiria.pt/api/auth/login";

    public String NOIMAGE ="http://amsi.dei.estg.ipleiria.pt/img/ipl_semfundo.png";

    // Só permite iniciar 1 vez
    private static SingletonGestorRestaurante instance=null;



    public  static synchronized SingletonGestorRestaurante getInstance(Context context){
        if(instance== null)
        {
            instance= new SingletonGestorRestaurante(context);
            volleyQueue = Volley.newRequestQueue(context);
        }
        return instance;
    }

    private SingletonGestorRestaurante(Context context){
        restaurantes = new ArrayList<Restaurante>();
     livrosBDHelper = new RestauranteBDHelper(context);
    }

    public void setRestauranteListener(RestauranteListener restauranteListener){
        this.restauranteListener = restauranteListener;
    }

    public ArrayList<Restaurante> getRestaurantesBD(){
        restaurantes = livrosBDHelper.getAllRestaurantesBD();
        return restaurantes;
    }

    public Restaurante getRestaurante(int idRestaurante) {
        for (Restaurante restaurante : restaurantes
             ) {
            if(restaurante.getId() == idRestaurante){
                return restaurante;
            }
        }
        return null;
    }

    public Prato getPrato(int idRestaurante) {
        for (Prato prato : pratos
        ) {
            if(prato.getIdr() == idRestaurante){
                return prato;
            }
        }
        return null;
    }

    public void adicionarLivroBD(Restaurante restaurante){
         livrosBDHelper.adicionarLivroBD(restaurante);
    }

    public void adicionarLivrosBD(ArrayList<Restaurante> restaurantes){
        livrosBDHelper.removerALLRestaurantesBD();
        for (Restaurante l : restaurantes){
            adicionarLivroBD(l);
        }
    }

    public void editarLivroBD(Restaurante restaurante){
        if(restaurantes.contains(restaurante)){
            return;
        }
        Restaurante l = getRestaurante(restaurante.getId());
                l.setNome(restaurante.getNome());
                l.setSalas(restaurante.getSalas());
                l.setimagem(restaurante.getimagem());
                l.setMorada(restaurante.getMorada());

         if(livrosBDHelper.editarLivroBD(l)){
             System.out.println("---> Restaurante atualizado BD");
         }
    }
    public void removerLivro(int id){
        Restaurante l = getRestaurante(id);
        if(l != null)
            if(livrosBDHelper.removerRestauranteBD(l.getId())){
                restaurantes.remove(l);
                System.out.println("---> Restaurante Removido BD");
            }
            restaurantes.remove(l);
    }
    // API
    public void adicionarLivroAPI(final Restaurante restaurante, final Context context){
        StringRequest request = new StringRequest(Request.Method.POST, mUrlAPIRestaurantes, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //ATUALIZAR BD
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        })
        {
            protected Map<String, String> getParams(){
                Map<String, String> params = new HashMap<>();
               // params.put("token","AMSI-TOKEN");
                params.put("nome", restaurante.getNome());
                params.put("morada", restaurante.getMorada());
                params.put("imagem", restaurante.getimagem());
                params.put("salas",""+ restaurante.getSalas());
                params.put("mesas",""+ restaurante.getMesas());
                params.put("telefone",""+ restaurante.getTelefone());
                //params.put("imagem", restaurante.getimagem() == null ? NOIMAGE : restaurante.getimagem());
            return params;
        }
        };
        volleyQueue.add(request);
    }
    public void getAllLivrosAPI(final Context context){
    if(!RestauranteJsonParser.isConnectionInternet(context)){
        Toast.makeText(context,"N tens ligacao a rede", Toast.LENGTH_LONG);
        if(restauranteListener != null){
           // restauranteListener.onRefreshListaRestaurantes(livrosBDHelper.getAllRestaurantesBD());
        }
        //DEVOLVER LIVROS DA BD
    }else{
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, mUrlAPIRestaurantes, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                //PARSER DA RESPOSTA
                restaurantes = RestauranteJsonParser.parserJsonLivros(response);
                //ADICIONAR OS RESTAURANTES à BD
                adicionarLivrosBD(restaurantes);
                //REPRESENTAR OS RESTAURANTES NAS VISTAS
                if(restauranteListener != null){
                    restauranteListener.onRefreshListaRestaurantes(restaurantes);
                }
                System.out.println("---> RESTAURANTE" + response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        volleyQueue.add(request);
    }


    }

    public void getAllPratosAPI(final Context context) {
        if (!PratoJsonParser.isConnectionInternet(context)) {
            Toast.makeText(context, "N tens ligacao a rede", Toast.LENGTH_LONG);
            if (restauranteListener != null) {
                // restauranteListener.onRefreshListaRestaurantes(livrosBDHelper.getAllRestaurantesBD());
            }
            //DEVOLVER LIVROS DA BD
        } else {
            JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, mUrlAPIPratos, null, new Response.Listener<JSONArray>() {
                @Override
                public void onResponse(JSONArray response) {
                    //PARSER DA RESPOSTA
                    pratos = PratoJsonParser.parserJsonPratos(response) ;

                    //ADICIONAR OS RESTAURANTES à BD
                    adicionarLivrosBD(restaurantes);
                    //REPRESENTAR OS RESTAURANTES NAS VISTAS
                    if (restauranteListener != null) {
                        restauranteListener.onRefreshListaPratos(pratos);
                    }
                    System.out.println("---> PRATO" + response);
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(context, error.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
            volleyQueue.add(request);
        }
    }

    public void getAllPedidosAPI(final Context context) {
        if (!PratoJsonParser.isConnectionInternet(context)) {
            Toast.makeText(context, "N tens ligacao a rede", Toast.LENGTH_LONG);
            if (restauranteListener != null) {
                // restauranteListener.onRefreshListaRestaurantes(livrosBDHelper.getAllRestaurantesBD());
            }
            //DEVOLVER LIVROS DA BD
        } else {
            JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, mUrlAPIPedidos, null, new Response.Listener<JSONArray>() {
                @Override
                public void onResponse(JSONArray response) {
                    //PARSER DA RESPOSTA
                    pedidos = PedidoJsonParser.parserJsonPedidos(response) ;

                    //ADICIONAR OS RESTAURANTES à BD
                    adicionarLivrosBD(restaurantes);
                    //REPRESENTAR OS RESTAURANTES NAS VISTAS
                    if (restauranteListener != null) {
                        restauranteListener.onRefreshListaPedidos(pedidos);
                    }
                    System.out.println("---> PEDIDO" + response);
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(context, error.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
            volleyQueue.add(request);
        }
    }


        void removerRestauranteAPI(final Restaurante restaurante, final Context context){
        StringRequest request = new StringRequest(Request.Method.DELETE, mUrlAPIRestaurantes + "" + restaurante.getId(), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    void editarRestauranteAPI(final Restaurante restaurante, final Context context){
        StringRequest request = new StringRequest(Request.Method.PUT, mUrlAPIRestaurantes + "" + restaurante.getId(), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {
            protected Map<String, String> getParams(){
                Map<String, String> params = new HashMap<>();
                //params.put("token","AMSI-TOKEN");
                params.put("nome", restaurante.getNome());
                params.put("morada", restaurante.getMorada());
                params.put("imagem", restaurante.getimagem());
                params.put("salas",""+ restaurante.getSalas());
                params.put("mesas",""+ restaurante.getMesas());
                params.put("telefone",""+ restaurante.getTelefone());
                return params;
            }
        };
        volleyQueue.add(request);
    }

    @Override
    public void onRefreshListaRestaurantes(ArrayList<Restaurante> listaRestaurantes) {

    }

    @Override
    public void onRefreshListaPratos(ArrayList<Prato> listaPratos) {

    }

    @Override
    public void onRefreshListaPedidos(ArrayList<Pedido> listaPedidos) {

    }

    @Override
    public void onUpdateListaRestaurantesBD(Restaurante restaurante, int operacao) {

    }
    public static class LoginIdCliente {
        private static LoginIdCliente mInstance= null;

        public int someValueIWantToKeep;

        protected LoginIdCliente(){}

        public static synchronized LoginIdCliente getInstance() {
            if(null == mInstance){
                mInstance = new LoginIdCliente();
            }
            return mInstance;
        }
    }

    public static class LoginEmailCliente {
        private static LoginEmailCliente mInstance= null;

        public String emailcliente;

        protected LoginEmailCliente(){}

        public static synchronized LoginEmailCliente getInstance() {
            if(null == mInstance){
                mInstance = new LoginEmailCliente();
            }
            return mInstance;
        }
    }

}


