package ayudec.ayudec;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity {

    private GridView gridView;
    private Ayudantia[] ayudantias;
    private String result;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        // se settea el toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);


        // se crean la ayudantias
        ayudantias = crearAyudantias();

        gridView = (GridView) findViewById(R.id.gridview);
        // Se instancia el adaptador y se setea al gridview con el conjunto de ayudantias
        CustomAdapter ca = new CustomAdapter(this, ayudantias);
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

    // Método de prueba para crear la lista de ayudantias (Esto se debe generar a partir de una consulta)
    // En este ejemplo se crean 2 ayudantias, se agregan al ArrayList y luego el ArrayList se transforma en Array
    // Se hizo de esta forma ya que el adaptador requiere un Array (no ArrayList) y los arrays de java deben ser instanciados con su tamaño
    // Al no saber el tamaño se hace de esta forma super weona
    private Ayudantia[] crearAyudantias(){
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

    public void addAyudantia(View view){
        Toast.makeText(HomeActivity.this,"Se apretó botón de agregar ayudantía.",Toast.LENGTH_SHORT).show();
    }

    public void showProfile(View view){
        Toast.makeText(HomeActivity.this,"Se apretó botón de perfil.",Toast.LENGTH_SHORT).show();
    }

    public void sendAlgo(View view){
        Toast.makeText(HomeActivity.this,"Se apretó botón de enviar.",Toast.LENGTH_SHORT).show();
    }
}
