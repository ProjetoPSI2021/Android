package com.example.eataway.Vista;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;



import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.eataway.Modelo.SingletonGestorRestaurante;
import com.example.eataway.R;
import com.google.android.material.navigation.NavigationView;

public class MenuMainActivity extends AppCompatActivity implements  NavigationView.OnNavigationItemSelectedListener {
    public static final String EMAIL="EMAIL";
    public static final String USER_EMAIL="USER_EMAIL";
    private String email = "";
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private NavigationView navigationView;
    private DrawerLayout drawer;
    private FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //Carregar Menu com email / Carregar fragmento
        Intent intent = getIntent();
        final int temp = intent.getIntExtra("id",0);

        System.out.println("TESTE SINGLETON VALUE --->" + SingletonGestorRestaurante.LoginIdCliente.getInstance().idClienteSingleton);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        navigationView = findViewById(R.id.nav_view);
        drawer = findViewById(R.id.drawer_layout);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer,
                toolbar, R.string.nd_Open, R.string.nd_Close);
        toggle.syncState();
        drawer.addDrawerListener(toggle);
        navigationView.setNavigationItemSelectedListener(this);
        fragmentManager = getSupportFragmentManager();
        carregarCabecalho();
        carregarFragmentoInicial();
        // TODO:criar método

    }

    private void carregarCabecalho(){
        email = getIntent().getStringExtra(EMAIL);

        sharedPreferences = getSharedPreferences(USER_EMAIL, Context.MODE_PRIVATE);

        if(email == null){
            email = sharedPreferences.getString(USER_EMAIL, getString(R.string.no_email));
        }
        else{
            editor = sharedPreferences.edit();
            editor.putString(USER_EMAIL, email) ;
            editor.apply();
        }
        View hView = navigationView.getHeaderView(0);
        TextView nav_user = hView.findViewById(R.id.tv_Email);
        nav_user.setText(SingletonGestorRestaurante.LoginIdCliente.getInstance().emailcliente);
    }

    private void carregarFragmentoInicial(){
        Intent intent = getIntent();
        final int temp = intent.getIntExtra("id",0);
        final int menuatividade = intent.getIntExtra("atividade",0);

        String tempstring = "" + temp;
        System.out.println("MENUMAINTESTE " + tempstring);

        try {
            if(menuatividade==0) {
                Bundle bundle = new Bundle();
                bundle.putString("key", tempstring);    // data that you want to send
                navigationView.setCheckedItem(R.id.nav_estatico);
                Fragment fragment = new ListaRestauranteFragment();
                fragmentManager.beginTransaction().replace(R.id.contentFragment, fragment).commit();
                System.out.println("MENUMAINTESTE " + tempstring);
            }else{
                Fragment fragment = new ListaPedidoFragment();
                fragmentManager.beginTransaction().replace(R.id.contentFragment, fragment).commit();
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
       Fragment fragment = null;
        switch (item.getItemId()) {
            case R.id.nav_estatico:
                System.out.println("-->Nav Estatico");
                fragment = new ListaRestauranteFragment();
                setTitle(item.getTitle());
                break;
            case R.id.nav_estatico_pedidos:
                System.out.println("-->Nav Estatico Pedidos");
                fragment = new ListaPedidoFragment();
                setTitle(item.getTitle());
                break;
            case R.id.nav_dash:
                Intent newAct = new Intent(getApplicationContext(), HomeActivity.class);
                startActivity(newAct);
                setTitle(item.getTitle());
                break;
            case R.id.nav_profile:
                Intent newActedit = new Intent(getApplicationContext(), EditActivity.class);
                startActivity(newActedit);
                break;
            case R.id.nav_logout:
                final SharedPreferences sharedPref = getSharedPreferences("data",MODE_PRIVATE);
                SharedPreferences.Editor e = sharedPref.edit();
                e.putString("emailshared", "");
                e.putString("usernameshared", "");
                e.commit();
                SharedPreferences login = getSharedPreferences("data",MODE_PRIVATE);
                SharedPreferences.Editor prefLoginEditor = login.edit();
                prefLoginEditor.putInt("isLogged",0);
                prefLoginEditor.commit();
                Intent logoutact = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(logoutact);
                break;
        }
        if(fragment != null)
            fragmentManager.beginTransaction().replace(R.id.contentFragment, fragment).commit();
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}