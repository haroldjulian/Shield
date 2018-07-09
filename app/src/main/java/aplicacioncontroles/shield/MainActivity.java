package aplicacioncontroles.shield;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import aplicacioncontroles.shield.fragments.AgentesFragment;
import aplicacioncontroles.shield.fragments.ContactosFragment;
import aplicacioncontroles.shield.fragments.InicioFragment;
import aplicacioncontroles.shield.fragments.NoticiasFragment;
import aplicacioncontroles.shield.util.Functions;
import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.drawer)
    DrawerLayout drawer;
    @BindView(R.id.nav)
    NavigationView nav;


    String title = "Inicio";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer,toolbar,R.string.Abrir,R.string.Cerrar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_menu);

        nav.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener(){


            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                item.setChecked(true);
                title = item.getTitle().toString();
                switch (item.getItemId()){
                    case R.id.actionInicio:
                        cargarFragmento(new InicioFragment());
                        break;
                    case R.id.actionContactos:
                        cargarFragmento(new ContactosFragment());
                        break;
                    case R.id.actionNoticias:
                        cargarFragmento(new NoticiasFragment());
                        break;
                    case R.id.actionAgentes:
                        cargarFragmento(new AgentesFragment());
                        break;
                    case R.id.actionCerrarSesion:
                        Functions.eliminarUsuario(MainActivity.this);
                        Intent i = new Intent(MainActivity.this,LoginActivity.class);
                        startActivity(i);
                        finish();
                        break;


                }
                drawer.closeDrawer(nav);
                return false;
            }
        });

        DrawerLayout.DrawerListener listener = new DrawerLayout.DrawerListener(){

            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {

            }

            @Override
            public void onDrawerOpened(View drawerView) {

            }

            @Override
            public void onDrawerClosed(View drawerView) {
                getSupportActionBar().setTitle(title);

            }

            @Override
            public void onDrawerStateChanged(int newState) {

            }
        };
 drawer.setDrawerListener(listener);  //asigna el nombre del lisener

        TextView lblNombreUsuario = (TextView) nav.getHeaderView(0).findViewById(R.id.lblNombreUsuario);
        TextView lblEmailUsuario = (TextView) nav.getHeaderView(0).findViewById(R.id.lblEmailUsuario);

        lblNombreUsuario.setText(Functions.obtenerUsuario(this).nombres);
        lblEmailUsuario.setText(Functions.obtenerUsuario(this).email);
    cargarFragmento(new InicioFragment());

    }

    private  void cargarFragmento(Fragment fragment){
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.main_content,fragment);
        transaction.commit();
    }

}
