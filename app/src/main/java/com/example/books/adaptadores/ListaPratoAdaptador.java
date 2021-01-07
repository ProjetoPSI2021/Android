package com.example.books.adaptadores;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.books.Modelo.Prato;
import com.example.books.R;

import java.util.ArrayList;

public class ListaPratoAdaptador extends BaseAdapter {

    private Context context;
    private LayoutInflater layoutInflater;
    private ArrayList<Prato> pratos;

    public ListaPratoAdaptador(Context context, ArrayList<Prato> pratos) {
        this.context = context;
        this.pratos = pratos;
    }

    @Override
    public int getCount() {
        return pratos.size();
    }

    @Override
    public Object getItem(int i) {
        return pratos.get(i);
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
                convertView = layoutInflater.inflate(R.layout.item_lista_prato, null);


            ViewHolderLista viewHolderLista = (ViewHolderLista) convertView.getTag();
                    if(viewHolderLista==null){
                        viewHolderLista = new ViewHolderLista(convertView);
                        convertView.setTag(viewHolderLista);
                    }
                    viewHolderLista.update(pratos.get(position));
                    return convertView;
    }
        private class ViewHolderLista{
            private TextView nome;
            private ImageView imagem;
            private TextView tipo;
            private TextView preco;
            private TextView ingr;
            String image_path = "http://192.168.1.82/advanced1/images/comida/";

            public ViewHolderLista(View convertView){
                nome = convertView.findViewById(R.id.tvNomePrato);
                tipo = convertView.findViewById(R.id.tvTipoPrato);
                preco = convertView.findViewById(R.id.tvPrecoPrato);
                ingr = convertView.findViewById(R.id.tvIngredientesPrato);
                imagem = convertView.findViewById(R.id.bannerprato);

            }
            public void update(Prato prato){
                nome.setText(prato.getNome());
                tipo.setText(prato.getTipo());
                preco.setText(""+ prato.getPreco());
                ingr.setText(prato.getIngr());
                Glide.with(context)
                        .load(image_path + prato.getImagem())
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .into(imagem);
            }

        }
}
