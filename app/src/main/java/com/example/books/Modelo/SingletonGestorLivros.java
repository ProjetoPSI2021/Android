package com.example.books.Modelo;

import com.example.books.R;

import java.util.ArrayList;

public class SingletonGestorLivros {

    private ArrayList<Livro> livros;

    // Só permite iniciar 1 vez
    private static SingletonGestorLivros instance=null;

    public  static synchronized SingletonGestorLivros getInstance(){
        if(instance== null)
        {
            instance= new SingletonGestorLivros();
        }
        return instance;
    }

    private SingletonGestorLivros(){
        // inicializar os metodos aqui
        // isto é o construtor
        gerarFakeData();
    }

    //método
    private void gerarFakeData(){
        livros=new ArrayList<Livro>();
        //new Livro(14,1,1999,"Title","Serie","Autor");
        livros.add(new Livro(1, R.drawable.programarandroid2,2020, "Programar em Android AMSI - 1","2ª Temporada", "AMSI TEAM"));
        livros.add(new Livro(2, R.drawable.programarandroid1,2020, "Programar em Android AMSI - 2","2ª Temporada", "AMSI TEAM"));
        livros.add(new Livro(3, R.drawable.logoipl,2020, "Programar em Android AMSI - 3","2ª Temporada", "AMSI TEAM"));
        livros.add(new Livro(4,R.drawable.programarandroid2,2020,  "Programar em Android AMSI - 4","2ª Temporada", "AMSI TEAM"));
        livros.add(new Livro(5,R.drawable.programarandroid1,2020,  "Programar em Android AMSI - 5","2ª Temporada", "AMSI TEAM"));
        livros.add(new Livro(6, R.drawable.logoipl,2020, "Programar em Android AMSI - 6","2ª Temporada", "AMSI TEAM"));
        livros.add(new Livro(7, R.drawable.programarandroid2,2020, "Programar em Android AMSI - 7","2ª Temporada", "AMSI TEAM"));
        livros.add(new Livro(8, R.drawable.programarandroid1,2020, "Programar em Android AMSI - 8","2ª Temporada", "AMSI TEAM"));
        livros.add(new Livro(9, R.drawable.logoipl,2020, "Programar em Android AMSI - 9","2ª Temporada", "AMSI TEAM"));
        livros.add(new Livro(10, R.drawable.programarandroid2,2020, "Programar em Android AMSI - 10","2ª Temporada", "AMSI TEAM"));
    }


    public ArrayList<Livro> getLivros(){
        return new ArrayList<>(livros) ;
    }

    public Livro getLivro(int idLivro) {
        for (Livro livro: livros
             ) {
            if(livro.getId() == idLivro){
                return livro;
            }
        }
        return null;
    }
}
