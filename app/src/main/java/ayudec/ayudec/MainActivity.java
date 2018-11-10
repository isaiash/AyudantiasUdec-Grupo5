package ayudec.ayudec;

import android.app.ActionBar;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.content.res.ResourcesCompat;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    GridView gridView;
    private Ayudantia[] ayudantias;
    DrawerLayout drawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        // Para cambiar los logos de la toolbar por el de perfil y send
        toolbar.post(new Runnable() {
            @Override
            public void run() {
                Drawable perfil_icon = ResourcesCompat.getDrawable(getResources(), R.drawable.rsz_perfil, null);
                Drawable send_icon = ResourcesCompat.getDrawable(getResources(), R.drawable.rsz_send, null);
                toolbar.setNavigationIcon(perfil_icon);
                toolbar.setOverflowIcon(send_icon);

            }
        });
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);


        // se crean la ayudantias para ejemplo
        ayudantias = crearAyudantias();

        // se crea la gridview y se llena con el adaptador lleno de ayudantias
        gridView = (GridView) findViewById(R.id.gridview);
        // Se instancia el adaptador y se setea al gridview con el conjunto de ayudantias
        CustomAdapter ca = new CustomAdapter(this, ayudantias);
        gridView.setAdapter(ca);

        // Se agrega el Listener para saber que item se presionó
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {
                String ramo = ayudantias[position].getRamo();
                String ayudante = ayudantias[position].getNombre();
                Toast.makeText(MainActivity.this, " Ayudantia: " + ramo + " Ayudante: " + ayudante,
                        Toast.LENGTH_SHORT).show();

            }

        });

        // listener del boton '+'
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });


        // cosas que se crearon por defecto para manejar los eventos de la toolbar
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.cerrar_sesion) {
            Toast.makeText(this, "Cerrando sesión", Toast.LENGTH_SHORT).show();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    // Método de prueba para crear la lista de ayudantias (esto se debe generar a partir de una consulta)
    // En este ejemplo se crean 3 ayudantias, se agregan al ArrayList y luego el ArrayList se transforma en Array
    // Se hizo de esta forma ya que el adaptador requiere un Array (no ArrayList) y los arrays de java deben ser instanciados con su tamaño
    // Al no saber el tamaño se hace de esta forma super weona
    private Ayudantia[] crearAyudantias() {
        ArrayList<Ayudantia> ayudantias_arraylist = new ArrayList<Ayudantia>();

        Ayudantia ayudantia1 = new Ayudantia("Francisco Salas",
                "Ingeniería Civil Informática",
                "IMU",
                "Jueves 5 PM",
                "TM 3-1",
                "13",
                "https://cdn.xl.thumbs.canstockphoto.es/lindo-triste-malhumorado-gato-icon-gato-avatar-eps-vectorial_csp49239081.jpg",
                "3");

        Ayudantia ayudantia2 = new Ayudantia("Tania Rivas",
                "Geofísica",
                "Física 2",
                "Martes 10 AM",
                "IS 2-2",
                "1",
                "https://lh5.googleusercontent.com/-NVHdsx0r0Xk/TYyS1Qen3JI/AAAAAAAAAGU/AMvdDulXehs/w1200-h630-p-nu/zarpas.png",
                "5");

        Ayudantia ayudantia3 = new Ayudantia("Juan Perez",
                "Ingeniería Civil Industrial",
                "Cálculo 3",
                "Lunes 3 PM",
                "TM 3-8",
                "18",
                "https://lh5.googleusercontent.com/-NVHdsx0r0Xk/TYyS1Qen3JI/AAAAAAAAAGU/AMvdDulXehs/w1200-h630-p-nu/zarpas.png",
                "5");

        ayudantias_arraylist.add(ayudantia1);
        ayudantias_arraylist.add(ayudantia2);
        ayudantias_arraylist.add(ayudantia3);

        // Se crea el array a partir del ArrayList
        Ayudantia[] ayudantias = new Ayudantia[ayudantias_arraylist.size()];
        ayudantias = ayudantias_arraylist.toArray(ayudantias);

        return ayudantias;
    }

}