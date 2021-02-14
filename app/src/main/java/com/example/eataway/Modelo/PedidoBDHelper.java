package com.example.eataway.Modelo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class PedidoBDHelper extends SQLiteOpenHelper {

    private static final int DB_VERSION = 1;
    private static final String DB_NAME = "PedidosDB";
    private static final String TABLE_NAME = "pedido";

    private static final String IDPEDIDO = "idpedido";
    private static final String IDRESTAURANTEPEDIDO = "idrestaurantepedido";
    private static final String IDPRATOORDER = "idpratoorder";
    private static final String ID_RESERVA = "id_reserva";
    private static final String DATA = "data";
    private static final String TIPO = "tipo";
    private static final String ID_CLIENTES = "id_clientes";
    private static final String PRECO = "preco";
    private static final String ESTADOPEDIDO = "estadopedido";


    private final SQLiteDatabase sqLiteDatabase;

    public PedidoBDHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        this.sqLiteDatabase =  this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String createPedidoTable =
                "CREATE TABLE " + TABLE_NAME +
                        "( " + IDPEDIDO + " INTEGER PRIMARY KEY," +
                        IDRESTAURANTEPEDIDO + " INTEGER NOT NULL, " +
                        IDPRATOORDER + " INTEGER NOT NULL, " +
                        ID_RESERVA + " INTEGER, " +
                        DATA + " TEXT NOT NULL, " +
                        TIPO + " TEXT NOT NULL, " +
                        ID_CLIENTES  + " INTEGER NOT NULL, " +
                        PRECO  + " INTEGER NOT NULL, " +
                        ESTADOPEDIDO + " TEXT NOT NULL" +
                        ")";
        sqLiteDatabase.execSQL(createPedidoTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        this.onCreate(sqLiteDatabase);
    }

    public void adicionarPedidoBD(Pedido pedido){
        ContentValues values = new ContentValues();
                values.put(IDPEDIDO, pedido.getIdpedido());
                values.put(IDRESTAURANTEPEDIDO, pedido.getIdrestaurantepedido());
                values.put(IDPRATOORDER, pedido.getIdpratoorder());
                values.put(ID_RESERVA, pedido.getId_reserva());
                values.put(DATA, pedido.getData());
                values.put(TIPO, pedido.getTipo());
                values.put(ID_CLIENTES, pedido.getId_clientes());
                values.put(PRECO, pedido.getPreco());
                values.put(ESTADOPEDIDO, pedido.getEstadopedido());


        this.sqLiteDatabase.insert(TABLE_NAME, null, values);



    }

    /*
    public boolean editarPedidoBD(Pedido pedido){
        ContentValues values = new ContentValues();
        values.put(IDPEDIDO, pedido.getIdpedido());
        values.put(IDRESTAURANTEPEDIDO, pedido.getIdrestaurantepedido());
        values.put(IDPRATOORDER, pedido.getIdpratoorder());
        values.put(ID_RESERVA, pedido.getId_reserva());
        values.put(DATA, pedido.getData());
        values.put(TIPO, pedido.getTipo());
        values.put(ID_CLIENTES, pedido.getId_clientes());
        values.put(PRECO, pedido.getPreco());
        values.put(ESTADOPEDIDO, pedido.getEstadopedido());


        return this.sqLiteDatabase.update(TABLE_NAME,values,"idpedido = ?",new String[]{"" + pedido.getIdpedido()}) > 0;

    }

    public boolean removerPedidoBD(int idpedido){
        return (this.sqLiteDatabase.delete(TABLE_NAME,"idpedido = ?",new String[]{"" + idpedido} )) == 1;
    }*/

    public ArrayList<Pedido> getAllPedidosBD(){
        ArrayList<Pedido> pedidos = new ArrayList<>();

        Cursor cursor = this.sqLiteDatabase.query(TABLE_NAME, new String[]{
                IDPEDIDO,IDRESTAURANTEPEDIDO,IDPRATOORDER,
                        ID_RESERVA,DATA,TIPO,ID_CLIENTES,PRECO,ESTADOPEDIDO}, null, null,//ESTA ORDEM EM BAIXO(0,5,4,..)
                null, null, null);

        if(cursor.moveToFirst()){
            do{
                Pedido pedido = new Pedido(cursor.getInt(0),cursor.getInt(3),
                        cursor.getString(4),cursor.getString(5),cursor.getInt(6),cursor.getInt(7),cursor.getInt(2),cursor.getInt(1),
                        cursor.getString(8));
                pedidos.add(pedido);
            }while(cursor.moveToNext());
        }
        return pedidos;
    }

    public void removerALLPedidosBD(){
        this.sqLiteDatabase.delete(TABLE_NAME,null,null);
    }

}
