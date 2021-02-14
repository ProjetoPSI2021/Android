package com.example.eataway.adaptadores;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.eataway.Modelo.Restaurante;
import com.example.eataway.R;

import java.util.ArrayList;

public class ListaRestauranteAdaptador extends BaseAdapter {

    private Context context;
    private LayoutInflater layoutInflater;
    private ArrayList<Restaurante> restaurantes;

    public ListaRestauranteAdaptador(Context context, ArrayList<Restaurante> restaurantes) {
        this.context = context;
        this.restaurantes = restaurantes;
    }

    @Override
    public int getCount() {
        return restaurantes.size();
    }

    @Override
    public Object getItem(int i) {
        return restaurantes.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //Implementar os items de Restaurante
        if (layoutInflater == null)
            layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            if (convertView == null)
                convertView = layoutInflater.inflate(R.layout.item_lista_rest, null);


            ViewHolderLista viewHolderLista = (ViewHolderLista) convertView.getTag();
                    if(viewHolderLista==null){
                        viewHolderLista = new ViewHolderLista(convertView);
                        convertView.setTag(viewHolderLista);
                    }
                    viewHolderLista.update(restaurantes.get(position));
                    return convertView;
    }
        private class ViewHolderLista{
            private TextView nome;
            private TextView morada;
            private ImageView imagem;
            private TextView salas;
            private TextView mesas;
            private TextView telefone;
            String image_path = "http://192.168.1.82/advanced1/images/restaurantes/";

            public ViewHolderLista(View convertView){
                nome = convertView.findViewById(R.id.tvNome);
                morada = convertView.findViewById(R.id.tvMorada);
                imagem = convertView.findViewById(R.id.bannerrestaurante);

            }
            public void update(Restaurante restaurante){
                nome.setText(restaurante.getNome());
                morada.setText(restaurante.getMorada());
                Glide.with(context)
                        .load(image_path + restaurante.getimagem())
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .into(imagem);
            }

        }
}
