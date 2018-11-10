package ayudec.ayudec;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Debug;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity {

    private Ayudantia[] ayudantias;
    private Alumno _alumno;
    private GridView gridView;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);



        // se settea el toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        if(!((GlobalVariables)this.getApplication()).getSesion_iniciada()){
            Toast.makeText(this, "Deslggeado", Toast.LENGTH_SHORT).show();
            Intent login = new Intent(HomeActivity.this,Login.class);
            startActivity(login);
        }


        // se crean la ayudantias
        //ayudantias = crearAyudantias();

        gridView = (GridView) findViewById(R.id.gridview);


        // Llama al controlador para que se encargue de llenar la grilla con los datos de la base
        callController();

        // Se setean los listener del layout para que identifique cuando se deslice a la derecha e izquierda
        RelativeLayout mainLayout = findViewById(R.id.main_layout);
        mainLayout.setOnTouchListener(new OnSwipeTouchListener(HomeActivity.this) {
            @Override
            public void onSwipeRight() {
                Toast.makeText(HomeActivity.this, "right", Toast.LENGTH_SHORT).show();
                Intent i = new Intent(HomeActivity.this,ProfileActivity.class);
                startActivity(i);
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
            }

            @Override
            public void onSwipeLeft() {
                Toast.makeText(HomeActivity.this, "left", Toast.LENGTH_SHORT).show();
                Intent i = new Intent(HomeActivity.this,Chat.class);
                startActivity(i);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
        });
    }

    // Método que instancia un controlador y lo ejecuta para obtener las ayudantías para la grilla
    public void callController(){
        ControladorBase _cb = new ControladorBase();
        _cb.setHome(HomeActivity.this);
        _cb.setTipo(2);
        _cb.ejecutar();
    }

    // Método que setea la lista de ayudantías obtenidas por el controlador en la grilla
    public void setAyudantias(final Ayudantia[] listaAyudantias){

        this.ayudantias = listaAyudantias;

        CustomAdapter ca = new CustomAdapter(this, this.ayudantias);
        gridView.setAdapter(ca);

        // Se agrega el Listener
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {

                String ramo = ayudantias[position].getRamo();
                String ayudante = ayudantias[position].getNombre();
                Toast.makeText(HomeActivity.this, " Ayudantia: " + ramo + " Ayudante: " + ayudante,
                        Toast.LENGTH_SHORT).show();

            }

        });
    }

    // método llamado por el botón (+)
    public void addAyudantia(View view){
        //Toast.makeText(HomeActivity.this,"Se apretó botón de agregar ayudantía.",Toast.LENGTH_SHORT).show();
        Intent crearAyudantia = new Intent(HomeActivity.this, NuevaAyudantia.class);
        startActivity(crearAyudantia);
    }

    public void sendAlgo(View view){
        Toast.makeText(HomeActivity.this,"Se apretó botón de chat.",Toast.LENGTH_SHORT).show();
    }

    public void ToProfile(View view){
        Intent i = new Intent(this, ProfileActivity.class);
        i.putExtra("alumno", getIntent().getStringExtra("alumno"));
        startActivity(i);
    }
}
