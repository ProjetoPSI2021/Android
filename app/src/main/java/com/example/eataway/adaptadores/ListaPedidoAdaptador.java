package com.example.eataway.adaptadores;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


import com.example.eataway.Modelo.Pedido;
import com.example.eataway.R;

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
        //Implementar os items de Pedidos
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
            private TextView idpedido,datapedido,tipopedido,estadopedido,precopedido;



            public ViewHolderLista(View convertView){
                idpedido = convertView.findViewById(R.id.tvIdPedido);
                datapedido = convertView.findViewById(R.id.tvDataPedido);
                tipopedido = convertView.findViewById(R.id.tvTipoPedido);
                precopedido = convertView.findViewById(R.id.tvPrecoPedido);
                estadopedido = convertView.findViewById(R.id.tvEstadoPedido);

            }
            public void update(Pedido pedido){
                idpedido.setText("#"+pedido.getIdpedido());
                datapedido.setText(pedido.getData());
                tipopedido.setText(pedido.getTipo());
                precopedido.setText("Total:"+pedido.getPreco()+"€");
                estadopedido.setText(pedido.getEstadopedido());
            }

        }
}
