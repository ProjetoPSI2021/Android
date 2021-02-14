package com.example.eataway.Vista;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.example.eataway.Modelo.SingletonGestorRestaurante;
import com.example.eataway.R;

public class TrackActivity extends AppCompatActivity {

    private Context context;


    View view_order_placed,view_order_confirmed,view_order_processed,view_order_pickup,con_divider,ready_divider,placed_divider;
    ImageView img_orderconfirmed,orderprocessed,orderpickup;
    TextView textorderpickup,text_confirmed,textorderprocessed,ordernumber,timeestimated;
    Button keep;
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        context = getApplicationContext();
        try
        {
            this.getSupportActionBar().hide();
        }
        catch (NullPointerException e){}
        setContentView(R.layout.activity_track);
        Intent myIntent = getIntent();
        view_order_placed=findViewById(R.id.view_order_placed);
        view_order_confirmed=findViewById(R.id.view_order_confirmed);
        view_order_processed=findViewById(R.id.view_order_processed);
        view_order_pickup=findViewById(R.id.view_order_pickup);
        placed_divider=findViewById(R.id.placed_divider);
        con_divider=findViewById(R.id.con_divider);
        ready_divider=findViewById(R.id.ready_divider);
        ordernumber=findViewById(R.id.textView4);
        timeestimated=findViewById(R.id.textView3);
        keep=findViewById(R.id.keep);


        textorderpickup=findViewById(R.id.textorderpickup);
        text_confirmed=findViewById(R.id.text_confirmed);
        textorderprocessed=findViewById(R.id.textorderprocessed);

        img_orderconfirmed=findViewById(R.id.img_orderconfirmed);
        orderprocessed=findViewById(R.id.orderprocessed);
        orderpickup=findViewById(R.id.orderpickup);
        Intent intent=getIntent();
        final String orderStatus=intent.getStringExtra("orderStatus");
        createNotificationChannel();


        //Timer de GET para estar sempre a receber o estado de pedido
        final Handler h = new Handler();
        h.postDelayed(new Runnable()
        {
            private long time = 0;

            @Override
            public void run()
            {
                // do stuff then
                // can call h again after work!
                time += 1000;
                RefreshOrderStatus();
                h.postDelayed(this, 5000);
            }
        }, 1000); // 1 second delay (takes millis)

        keep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();

            }
        });
    }

    //Criação de canal para as notificações
    private void createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "orderChannel";
            String description = "Channel for Track Order";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel("trackorder", name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }


    public void RefreshOrderStatus(){
    //Vai estar a fazer GET e dependendo do estado de pedido, o layout muda.
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "trackorder")
                .setSmallIcon(R.drawable.cart_icon)
                .setContentTitle("A sua comida está pronta!")
                .setContentText("Recolha o pedido com segurança")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        String orderTipo="0";
        SingletonGestorRestaurante.getInstance(this).getPedidoTrackerAPI(this);
        String statuspedidoapi = SingletonGestorRestaurante.LoginIdCliente.getInstance().estadopedido;
        if(statuspedidoapi != null && statuspedidoapi.equals( "Pendente")){
            orderTipo = "1";
            timeestimated.setText("20 Minutos");
        }else if(statuspedidoapi != null && statuspedidoapi.equals ("Em curso") ){
            orderTipo = "2";
            timeestimated.setText("15 Minutos");

        }else if(statuspedidoapi != null && statuspedidoapi.equals("Concluido")){
            orderTipo = "3";
            timeestimated.setText("Pronto!");
            keep.setVisibility(View.VISIBLE);
            notificationManager.notify(100, builder.build());
        }
        System.out.println("orderTipo:"+ orderTipo);
        getOrderStatus(orderTipo);
    }

    public void onBackPressed() {

        return;
    }
    private void getOrderStatus(String orderStatus) {
        if (orderStatus.equals("0")){
            float alfa= (float) 0.5;
            setStatus(alfa);

        }else if (orderStatus.equals("1")){
            float alfa= (float) 1;
            setStatus1(alfa);



        }else if (orderStatus.equals("2")){
            float alfa= (float) 1;
            setStatus2(alfa);


        }else if (orderStatus.equals("3")){
            float alfa= (float) 1;
            setStatus3(alfa);
        }
    }


    private void setStatus(float alfa) {
        float myf= (float) 0.5;
        view_order_placed.setBackground(getResources().getDrawable(R.drawable.shape_status_completed));
        view_order_confirmed.setBackground(getResources().getDrawable(R.drawable.shape_status_current));
        orderprocessed.setAlpha(alfa);
        view_order_processed.setBackground(getResources().getDrawable(R.drawable.shape_status_current));
        con_divider.setBackground(getResources().getDrawable(R.drawable.shape_status_current));
        placed_divider.setAlpha(alfa);
        img_orderconfirmed.setAlpha(alfa);
        placed_divider.setBackground(getResources().getDrawable(R.drawable.shape_status_current));
        text_confirmed.setAlpha(alfa);
        textorderprocessed.setAlpha(alfa);
        view_order_pickup.setBackground(getResources().getDrawable(R.drawable.shape_status_current));
        ready_divider.setBackground(getResources().getDrawable(R.drawable.shape_status_current));
        orderpickup.setAlpha(alfa);

        textorderpickup.setAlpha(myf);




    }

    private void setStatus1(float alfa) {
        float myf= (float) 0.5;
        view_order_placed.setBackground(getResources().getDrawable(R.drawable.shape_status_completed));
        view_order_confirmed.setBackground(getResources().getDrawable(R.drawable.shape_status_completed));
        orderprocessed.setAlpha(myf);
        view_order_processed.setBackground(getResources().getDrawable(R.drawable.shape_status_current));
        con_divider.setBackground(getResources().getDrawable(R.drawable.shape_status_current));
        placed_divider.setBackground(getResources().getDrawable(R.drawable.shape_status_completed));
        img_orderconfirmed.setAlpha(alfa);

        text_confirmed.setAlpha(alfa);
        textorderprocessed.setAlpha(myf);
        view_order_pickup.setAlpha(myf);
        ready_divider.setBackground(getResources().getDrawable(R.drawable.shape_status_current));
        orderpickup.setAlpha(myf);
        view_order_pickup.setBackground(getResources().getDrawable(R.drawable.shape_status_current));
        textorderpickup.setAlpha(myf);
    }

    private void setStatus2(float alfa) {
        float myf= (float) 0.5;
        view_order_placed.setBackground(getResources().getDrawable(R.drawable.shape_status_completed));
        view_order_confirmed.setBackground(getResources().getDrawable(R.drawable.shape_status_completed));
        orderprocessed.setAlpha(alfa);
        view_order_processed.setBackground(getResources().getDrawable(R.drawable.shape_status_completed));
        con_divider.setBackground(getResources().getDrawable(R.drawable.shape_status_completed));
        placed_divider.setBackground(getResources().getDrawable(R.drawable.shape_status_completed));
        img_orderconfirmed.setAlpha(alfa);

        text_confirmed.setAlpha(alfa);
        textorderprocessed.setAlpha(alfa);
        view_order_pickup.setBackground(getResources().getDrawable(R.drawable.shape_status_current));
        ready_divider.setBackground(getResources().getDrawable(R.drawable.shape_status_current));
        textorderpickup.setAlpha(myf);
        orderpickup.setAlpha(myf);

    }

    private void setStatus3(float alfa) {
        view_order_placed.setBackground(getResources().getDrawable(R.drawable.shape_status_completed));
        view_order_confirmed.setBackground(getResources().getDrawable(R.drawable.shape_status_completed));
        orderprocessed.setAlpha(alfa);
        view_order_processed.setBackground(getResources().getDrawable(R.drawable.shape_status_completed));
        con_divider.setBackground(getResources().getDrawable(R.drawable.shape_status_completed));

        img_orderconfirmed.setAlpha(alfa);
        placed_divider.setBackground(getResources().getDrawable(R.drawable.shape_status_completed));
        text_confirmed.setAlpha(alfa);
        textorderprocessed.setAlpha(alfa);
        view_order_pickup.setBackground(getResources().getDrawable(R.drawable.shape_status_completed));
        ready_divider.setBackground(getResources().getDrawable(R.drawable.shape_status_completed));
        textorderpickup.setAlpha(alfa);
        orderpickup.setAlpha(alfa);
    }
    }




