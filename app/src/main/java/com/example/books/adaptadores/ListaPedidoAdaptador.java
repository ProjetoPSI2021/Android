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
import com.example.books.Modelo.Pedido;
import com.example.books.Modelo.Prato;
import com.example.books.R;

import java.util.ArrayList;

public class ListaPedidoAdaptador extends BaseAdapter {

    private Context context;
    private LayoutInflater layoutInflater;
    private ArrayList<Pedido> pedidos;

    public ListaPedidoAdaptador(Context context, ArrayList<Pedido> pedidos) {
        this.context = context;
        this.pedidos = pedidos;
    }

    @Override
    public int getCount() {
        return pedidos.size();
    }

    @Override
    public Object getItem(int i) {
        return pedidos.get(i);
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
                convertView = layoutInflater.inflate(R.layout.item_lista_pedido, null);


            ViewHolderLista viewHolderLista = (ViewHolderLista) convertView.getTag();
                    if(viewHolderLista==null){
                        viewHolderLista = new ViewHolderLista(convertView);
                        convertView.setTag(viewHolderLista);
                    }
                    viewHolderLista.update(pedidos.get(position));
                    return convertView;
    }
        private class ViewHolderLista{
            private TextView idpedido,idreserva,datapedido,tipopedido,idclientepedido,estadopedido,idrestaurantepedido,precopedido,idpratoorder;



            public ViewHolderLista(View convertView){
                idpedido = convertView.findViewById(R.id.tvIdPedido);
                //idreserva = convertView.findViewById(R.id.tvIdReserva);
                datapedido = convertView.findViewById(R.id.tvDataPedido);
                tipopedido = convertView.findViewById(R.id.tvTipoPedido);
                //idclientepedido = convertView.findViewById(R.id.tvIdClientePedido);
                precopedido = convertView.findViewById(R.id.tvPrecoPedido);
               // idpratoorder = convertView.findViewById(R.id.tvIdPratoOrder);
                //idrestaurantepedido = convertView.findViewById(R.id.tvIdRestaurantePedido);
                estadopedido = convertView.findViewById(R.id.tvEstadoPedido);

            }
            public void update(Pedido pedido){
                idpedido.setText("#"+pedido.getIdpedido());
                //idreserva.setText(""+pedido.getId_reserva());
                datapedido.setText(pedido.getData());
                tipopedido.setText(pedido.getTipo());
                //idclientepedido.setText(""+pedido.getId_clientes());
                precopedido.setText("Total:"+pedido.getPreco()+"€");
                //idpratoorder.setText("ID Prato"+pedido.getIdpratoorder());
                //idrestaurantepedido.setText("Restaurante:"+pedido.getIdrestaurantepedido());
                estadopedido.setText(pedido.getEstadopedido());
            }

        }
}
