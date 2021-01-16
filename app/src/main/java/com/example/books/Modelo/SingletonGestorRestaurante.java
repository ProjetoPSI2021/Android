package com.example.books.Modelo;
import android.content.Context;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.books.Listener.RestauranteListener;
import com.example.books.utils.PedidoJsonParser;
import com.example.books.utils.PratoJsonParser;
import com.example.books.utils.RestauranteJsonParser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SingletonGestorRestaurante implements RestauranteListener {

    private ArrayList<Restaurante> restaurantes;
    private static PedidoBDHelper pedidosBDHelper = null;
    private ArrayList<Prato> pratos;
    private ArrayList<Pedido> pedidos;
    private RestauranteBDHelper livrosBDHelper = null;
    private static RequestQueue volleyQueue = null;
    private RestauranteListener restauranteListener;
    private RestauranteListener pratoListener;
    private static final String mUrlAPIRestaurantes = "http://192.168.1.82/advanced1/api/web/restaurante";
    private static final String mUrlAPIPratos= "http://192.168.1.82/advanced1/api/web/prato";
    private static final String mUrlAPIPedidos= "http://192.168.1.82/advanced1/api/web/pedido";
    private static  String mUrlAPIClientes= "http://192.168.1.82/advanced1/api/web/cliente";



    private static final String mUrlAPILogin = "http://amsi.dei.estg.ipleiria.pt/api/auth/login";

    public String NOIMAGE ="http://amsi.dei.estg.ipleiria.pt/img/ipl_semfundo.png";

    // Só permite iniciar 1 vez
    private static SingletonGestorRestaurante instance=null;

    public static void iniciarBD(Context context) {
        if(pedidosBDHelper == null)
            pedidosBDHelper = new PedidoBDHelper(context);
    }
    public void lerBD(){
        this.pedidos = pedidosBDHelper.getAllPedidosBD();
    }
    public void gravarBD(){
        pedidosBDHelper.removerALLPedidosBD(); //se já existir, remover todos os contactos
        for (Pedido pedido: pedidos) {
            pedidosBDHelper.adicionarPedidoBD(pedido);
        }
    }

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
        pedidosBDHelper = new PedidoBDHelper(context);
     livrosBDHelper = new RestauranteBDHelper(context);
    }

    public void setRestauranteListener(RestauranteListener restauranteListener){
        this.restauranteListener = restauranteListener;
    }

    public ArrayList<Restaurante> getRestaurantesBD(){
        restaurantes = livrosBDHelper.getAllRestaurantesBD();
        return restaurantes;
    }

    public ArrayList<Pedido> getPedidosBD(){
        pedidos = pedidosBDHelper.getAllPedidosBD();
        return pedidos;
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


    public Pedido getPedido(int idpedido) {
        for (Pedido pedido : pedidos
        ) {
            if(pedido.getIdpedido() == idpedido){
                return pedido;
            }
        }
        return null;
    }

    public Prato getPrato(int idPrato) {
        for (Prato prato : pratos
        ) {
            if(prato.getId() == idPrato){
                return prato;
            }
        }
        return null;
    }



    public void adicionarPedidoBD(Pedido pedido){
        pedidosBDHelper.adicionarPedidoBD(pedido);
    }

    public void adicionarPedidosBD(ArrayList<Pedido> pedidos){
        pedidosBDHelper.removerALLPedidosBD();
        for (Pedido k : pedidos){
            adicionarPedidoBD(k);
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
    public void removerPedido(int id){
        Pedido l = getPedido(id);
        if(l != null)
            if(pedidosBDHelper.removerPedidoBD(l.getIdpedido())){
                pedidos.remove(l);
                System.out.println("---> Pedido Removido BD");
            }
            pedidos.remove(l);
    }
    // API



    public void RegistPedido(final Context context, final String idrestaurantepedido, final String idpratoorder,final String id_reserva,final String tipo,final String id_clientes,final String preco,final String estadopedido ){
        String URL_REGIST = "http://192.168.1.82/advanced1/android_register_login/pedido.php";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_REGIST,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        System.out.println("--->"+response);
                        try{
                            JSONObject jsonObject = new JSONObject(response);
                            String success = jsonObject.getString("success");
                            if(success.equals("1")){
                                System.out.println("--->DEU");
                                Toast.makeText(context, "Pedido feito com sucesso!", Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();

                        }
                    }
                },

                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        ;  System.out.println("--->ERRO");
                        Toast.makeText(context, "Erro - Pedido Cancelado!", Toast.LENGTH_SHORT).show();

                    }
                })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("idrestaurantepedido",idrestaurantepedido);
                params.put("idpratoorder",idpratoorder);
                params.put("id_reserva",id_reserva);
                params.put("tipo",tipo);
                params.put("id_clientes",id_clientes);
                params.put("preco",preco);
                params.put("estadopedido",estadopedido);

                return params;
            }
        };

        volleyQueue.add(stringRequest);
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

    public void postPedidosAPI(final Context context) {
        if (!PedidoJsonParser.isConnectionInternet(context)) {
            Toast.makeText(context, "N tens ligacao a rede", Toast.LENGTH_LONG);
            if (restauranteListener != null) {
                // restauranteListener.onRefreshListaRestaurantes(livrosBDHelper.getAllRestaurantesBD());
            }
            //DEVOLVER LIVROS DA BD
        } else {

            JsonArrayRequest postRequest = new JsonArrayRequest(Request.Method.POST, mUrlAPIPedidos, null, new Response.Listener<JSONArray>() {
                @Override
                public void onResponse(JSONArray response) {

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(context, error.getMessage(), Toast.LENGTH_SHORT).show();
                    System.out.println("--->"+error.toString());
                }
            }
            ){
                protected Map<String, String> getParams(){
                    Map<String, String>  params = new HashMap<String, String>();
                    params.put("idpedido", "36");
                    params.put("idrestaurantepedido", "5");
                    params.put("idpratoorder", "6");
                    params.put("id_reserva", "1");
                    params.put("data", "2021-01-01 00:00:00");
                    params.put("tipo", "Takeaway");
                    params.put("id_clientes", "3");
                    params.put("preco", "20");
                    params.put("estadopedido", "Pendente");

                    return params;
                }
            };
            volleyQueue.add(postRequest);
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
        if (!PedidoJsonParser.isConnectionInternet(context)) {
            Toast.makeText(context, "N tens ligacao a rede", Toast.LENGTH_LONG);
            if (restauranteListener != null) {
                 restauranteListener.onRefreshListaPedidos(getPedidosBD());
                 //Ao dar refresh, vai buscar os dados à base de dados
            }
            //DEVOLVER LIVROS DA BD
        } else {
            JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, mUrlAPIPedidos, null, new Response.Listener<JSONArray>() {
                @Override
                public void onResponse(JSONArray response) {
                    //PARSER DA RESPOSTA
                    pedidos = PedidoJsonParser.parserJsonPedidos(response) ;

                    //ADICIONAR OS RESTAURANTES à BD
                    System.out.println("--->PedidosJSON"+ pedidos);
                       adicionarPedidosBD(pedidos);

                    //REPRESENTAR OS RESTAURANTES NAS VISTAS
                    if (restauranteListener != null) {
                        restauranteListener.onRefreshListaPedidos(pedidos);
                    }
                    System.out.println("---> PEDIDO" + response);
                    System.out.println("Pedidos bd: " + pedidos);
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(context, error.getMessage(), Toast.LENGTH_SHORT).show();
                    if (restauranteListener != null) {
                        restauranteListener.onRefreshListaPedidos(pedidos);
                    }
                    System.out.println("Pedidos bd: " + pedidos);

                }
            });
            volleyQueue.add(request);
        }
    }

    public void getPedidoTrackerAPI(final Context context) {
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
                    pedidos = PedidoJsonParser.parserJsonPedidoTracker(response) ;

                    //ADICIONAR OS RESTAURANTES à BD
                    //REPRESENTAR OS RESTAURANTES NAS VISTAS
                    if (restauranteListener != null) {
                        restauranteListener.onRefreshListaPedidos(pedidos);
                    }
                    System.out.println("---> PEDIDO TRACKER" + response);
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

        public int idClienteSingleton;
        public String emailcliente;
        public String estadopedido;

        protected LoginIdCliente(){}

        public static synchronized LoginIdCliente getInstance() {
            if(null == mInstance){
                mInstance = new LoginIdCliente();
            }
            return mInstance;
        }
    }

    public static class RestauranteIdFood {
        private static RestauranteIdFood mInstance= null;

        public int idrestaurantefood;

        protected RestauranteIdFood(){}

        public static synchronized RestauranteIdFood getInstance() {
            if(null == mInstance){
                mInstance = new RestauranteIdFood();
            }
            return mInstance;
        }
    }
}


