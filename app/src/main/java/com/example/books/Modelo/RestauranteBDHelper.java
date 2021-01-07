package com.example.books.Modelo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class RestauranteBDHelper extends SQLiteOpenHelper {

    private static final int DB_VERSION = 1;
    private static final String DB_NAME = "RestauranteDBB";
    private static final String TABLE_NAME = "restaurante";

    private static final String ID_RESTAURANTE = "idRestaurante";
    private static final String NOME_RESTAURANTE = "nome";
    private static final String MORADA_RESTAURANTE = "morada";
    private static final String IMAGEM_RESTAURANTE = "imagem";
    private static final String SALAS_RESTAURANTE = "salas";
    private static final String MESAS_RESTAURANTE = "mesas";
    private static final String TELEFONE_RESTAURANTE = "telefone";

    private final SQLiteDatabase sqLiteDatabase;

    public RestauranteBDHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        this.sqLiteDatabase =  this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String createLivroTable =
                "CREATE TABLE " + TABLE_NAME +
                        "( " + ID_RESTAURANTE + " INTEGER PRIMARY KEY," +
                        NOME_RESTAURANTE + " TEXT NOT NULL, " +
                        MORADA_RESTAURANTE  + " TEXT NOT NULL, " +
                        IMAGEM_RESTAURANTE  + " TEXT NOT NULL, " +
                        SALAS_RESTAURANTE  + " INTEGER NOT NULL, " +
                        MESAS_RESTAURANTE  + " INTEGER NOT NULL, " +
                        TELEFONE_RESTAURANTE  + " INTEGER NOT NULL, " +
                         " TEXT" +
                        ")";
        sqLiteDatabase.execSQL(createLivroTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        this.onCreate(sqLiteDatabase);
    }

    public void adicionarLivroBD(Restaurante restaurante){
        ContentValues values = new ContentValues();
                values.put(ID_RESTAURANTE, restaurante.getId());
                values.put(NOME_RESTAURANTE, restaurante.getNome());
                values.put(MORADA_RESTAURANTE, restaurante.getMorada());
                values.put(IMAGEM_RESTAURANTE, restaurante.getimagem());
                values.put(SALAS_RESTAURANTE, restaurante.getSalas());
                values.put(MESAS_RESTAURANTE, restaurante.getMesas());
                values.put(TELEFONE_RESTAURANTE, restaurante.getTelefone());

                 this.sqLiteDatabase.insert(TABLE_NAME, null, values);



    }

    public boolean editarLivroBD(Restaurante restaurante){
        ContentValues values = new ContentValues();
        values.put(NOME_RESTAURANTE, restaurante.getNome());
        values.put(MORADA_RESTAURANTE, restaurante.getMorada());
        values.put(IMAGEM_RESTAURANTE, restaurante.getimagem());
        values.put(SALAS_RESTAURANTE, restaurante.getSalas());
        values.put(MESAS_RESTAURANTE, restaurante.getMesas());
        values.put(TELEFONE_RESTAURANTE, restaurante.getTelefone());


        return this.sqLiteDatabase.update(TABLE_NAME,values,"idRestaurante = ?",new String[]{"" + restaurante.getId()}) > 0;

    }

    public boolean removerRestauranteBD(int idRestaurante){
        return (this.sqLiteDatabase.delete(TABLE_NAME,"idRestaurante=?",new String[]{"" + idRestaurante} )) == 1;
    }

    public ArrayList<Restaurante> getAllRestaurantesBD(){
        ArrayList<Restaurante> restaurantes = new ArrayList<>();

        Cursor cursor = this.sqLiteDatabase.query(TABLE_NAME, new String[]{
                ID_RESTAURANTE,MORADA_RESTAURANTE,NOME_RESTAURANTE,
                        IMAGEM_RESTAURANTE,SALAS_RESTAURANTE,MESAS_RESTAURANTE,TELEFONE_RESTAURANTE}, null, null,//ESTA ORDEM EM BAIXO(0,5,4,..)
                null, null, null);

        if(cursor.moveToFirst()){
            do{
                Restaurante auxRestaurante = new Restaurante(cursor.getInt(0),cursor.getString(1),
                        cursor.getString(2),cursor.getString(3),cursor.getInt(4),cursor.getInt(5),cursor.getInt(6));


                restaurantes.add(auxRestaurante);
            }while(cursor.moveToNext());
        }
        return restaurantes;
    }

    public void removerALLRestaurantesBD(){
        this.sqLiteDatabase.delete(TABLE_NAME,null,null);
    }

}
