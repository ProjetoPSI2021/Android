package com.example.books.Modelo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class LivroBDHelper extends SQLiteOpenHelper {

    private static final int DB_VERSION = 1;
    private static final String DB_NAME = "LivrosDBB";
    private static final String TABLE_NAME = "Livro";

    private static final String ID_LIVRO = "id";
    private static final String TITULO_LIVRO = "titulo";
    private static final String SERIE_LIVRO = "serie";
    private static final String AUTOR_LIVRO = "autor";
    private static final String ANO_LIVRO = "ano";
    private static final String CAPA_LIVRO = "capa";


    private final SQLiteDatabase sqLiteDatabase;

    public LivroBDHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        this.sqLiteDatabase =  this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String createLivroTable =
                "CREATE TABLE " + TABLE_NAME +
                        "( " + ID_LIVRO + " INTEGER PRIMARY KEY," +
                        TITULO_LIVRO + " TEXT NOT NULL, " +
                        SERIE_LIVRO  + " TEXT NOT NULL, " +
                        AUTOR_LIVRO  + " TEXT NOT NULL, " +
                        ANO_LIVRO  + " INTEGER NOT NULL, " +
                        CAPA_LIVRO + " TEXT" +
                        ")";
        sqLiteDatabase.execSQL(createLivroTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        this.onCreate(sqLiteDatabase);
    }

    public void adicionarLivroBD(Livro livro){
        ContentValues values = new ContentValues();
                values.put(ID_LIVRO, livro.getId());
                values.put(TITULO_LIVRO, livro.getTitulo());
                values.put(SERIE_LIVRO, livro.getSerie());
                values.put(AUTOR_LIVRO, livro.getAutor());
                values.put(ANO_LIVRO, livro.getAno());
                values.put(CAPA_LIVRO, livro.getCapa());

                 this.sqLiteDatabase.insert(TABLE_NAME, null, values);



    }

    public boolean editarLivroBD(Livro livro){
        ContentValues values = new ContentValues();
        values.put(TITULO_LIVRO, livro.getTitulo());
        values.put(SERIE_LIVRO, livro.getSerie());
        values.put(AUTOR_LIVRO, livro.getAutor());
        values.put(ANO_LIVRO, livro.getAno());
        values.put(CAPA_LIVRO, livro.getCapa());

        return this.sqLiteDatabase.update(TABLE_NAME,values,"id = ?",new String[]{"" + livro.getId()}) > 0;

    }

    public boolean removerLivroBD(int idLivro){
        return (this.sqLiteDatabase.delete(TABLE_NAME,"id=?",new String[]{"" + idLivro} )) == 1;
    }

    public ArrayList<Livro> getAllLivrosBD(){
        ArrayList<Livro> livros = new ArrayList<>();

        Cursor cursor = this.sqLiteDatabase.query(TABLE_NAME, new String[]{
                ID_LIVRO,SERIE_LIVRO,TITULO_LIVRO,
                        AUTOR_LIVRO,ANO_LIVRO,CAPA_LIVRO}, null, null,//ESTA ORDEM EM BAIXO(0,5,4,..)
                null, null, null);

        if(cursor.moveToFirst()){
            do{
                Livro auxLivro = new Livro(cursor.getInt(0),cursor.getString(5),cursor.getInt(4),
                        cursor.getString(1),cursor.getString(2),
                        cursor.getString(3));


                livros.add(auxLivro);
            }while(cursor.moveToNext());
        }
        return livros;
    }

    public void removerALLLivrosBD(){
        this.sqLiteDatabase.delete(TABLE_NAME,null,null);
    }

}
