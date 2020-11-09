package com.example.books.adaptadores;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.books.Modelo.Livro;
import com.example.books.R;

import java.util.ArrayList;

public class ListaLivroAdaptador extends BaseAdapter {

    private Context context;
    private LayoutInflater layoutInflater;
    private ArrayList<Livro> livros;

    public ListaLivroAdaptador(Context context, ArrayList<Livro> livros) {
        this.context = context;
        this.livros = livros;
    }

    @Override
    public int getCount() {
        return livros.size();
    }

    @Override
    public Object getItem(int i) {
        return livros.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (layoutInflater == null)
            layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            if (convertView == null)
                convertView = layoutInflater.inflate(R.layout.item_lista_livro, null);


            ViewHolderLista viewHolderLista = (ViewHolderLista) convertView.getTag();
                    if(viewHolderLista==null){
                        viewHolderLista = new ViewHolderLista(convertView);
                        convertView.setTag(viewHolderLista);
                    }
                    viewHolderLista.update(livros.get(position));
                    return convertView;
    }
        private class ViewHolderLista{
            private TextView titulo;
            private TextView serie;
            private TextView autor;
            private TextView ano;
            private ImageView capa;

            public ViewHolderLista(View convertView){
                titulo = convertView.findViewById(R.id.tvTitulo);
                serie = convertView.findViewById(R.id.tvSerie);
                autor = convertView.findViewById(R.id.tvAutor);
                ano = convertView.findViewById(R.id.tvAno);
                capa = convertView.findViewById(R.id.imgCapa);
            }
            public void update(Livro livro){
                titulo.setText(livro.getTitulo());
                serie.setText(livro.getSerie());
                autor.setText(livro.getAutor());
                ano.setText(""+livro.getAno());
                capa.setImageResource(livro.getCapa());
            }

        }
}
