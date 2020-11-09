package com.example.books;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.books.Vista.DinamicoFragment;
import com.example.books.Vista.EstaticoFragment;
import com.google.android.material.navigation.NavigationView;

public class MenuMainActivity extends AppCompatActivity implements  NavigationView.OnNavigationItemSelectedListener {
    public static final String EMAIL="EMAIL";
    private String email = "";
    private NavigationView navigationView;
    private DrawerLayout drawer;
    private FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
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
        View hView = navigationView.getHeaderView(0);
        TextView nav_user = hView.findViewById(R.id.tv_Email);
        nav_user.setText(email);
    }

    private void carregarFragmentoInicial(){
        navigationView.setCheckedItem(R.id.nav_estatico);
        Fragment fragment = new ListaLivrosFragment();
        fragmentManager.beginTransaction().replace(R.id.contentFragment, fragment).commit();
                setTitle(getString(R.string.menu_lista));
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
       Fragment fragment = null;
        switch (item.getItemId()) {
            case R.id.nav_estatico:
                System.out.println("-->Nav Estatico");
                fragment = new ListaLivrosFragment();
                setTitle(item.getTitle());
                break;
            case R.id.nav_dinamico:
                System.out.println("-->Nav Dinamico");
                fragment = new DinamicoFragment();
                setTitle(item.getTitle());
                break;
            case R.id.nav_email:
                System.out.println("-->Nav Email");
                String subject ="AMSI 2020";
                String messagem="Isto é messagem enviada pela minha aplicação";
                Uri uri = Uri.parse("mailto:")
                        .buildUpon()
                        .appendQueryParameter("to",email)
                        .appendQueryParameter("subject", subject)
                        .appendQueryParameter("body", messagem)
                        .build();
                Intent intent = new Intent(Intent.ACTION_SENDTO,uri);
                if (intent.resolveActivity(getPackageManager()) != null) {
                    startActivity(intent);
                }
                break;
                //System.out.println("-->Nav Estatico");
        }
        if(fragment != null)
            fragmentManager.beginTransaction().replace(R.id.contentFragment, fragment).commit();
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}