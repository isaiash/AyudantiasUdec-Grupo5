package ayudec.ayudec;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;


import com.allyants.chipview.ChipView;
import com.allyants.chipview.SimpleChipAdapter;

import java.util.ArrayList;

public class ProfileActivity extends AppCompatActivity {
    Alumno _alumno;

    private Alumno _alumno;
    private ControladorBase _cb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_activity_layout);

        ScrollView sv = (ScrollView) findViewById(R.id.principal);

        // Listener para hacer swipe a la derecha y volver a la activity Home
        sv.setOnTouchListener(new OnSwipeTouchListener(ProfileActivity.this) {
            @Override
            public void onSwipeLeft() {
                finish();
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
        });

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

<<<<<<< HEAD
=======
        // Se obtiene el alumno a partir de la variable global
        _alumno = ((GlobalVariables) this.getApplication()).getAlumno();
        ((TextView)findViewById(R.id.userName)).setText(_alumno.get_nombre());
        ((TextView)findViewById(R.id.userCar)).setText(_alumno.get_carrera());
        ((TextView)findViewById(R.id.userType)).setText(_alumno.get_matricula());

>>>>>>> 299667b83039d389c6a2becf5711431bb81dfcad
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.home)
            finish();
        return super.onOptionsItemSelected(item);
    }
<<<<<<< HEAD
=======
// Borra las variables globales, el stack de activities y redirige al activity del login
    public void cerrarSesion(View view){
        ((GlobalVariables) this.getApplication()).setSesion_iniciada(false); //Deja la variable global sesion_iniciada en false
        ((GlobalVariables) this.getApplication()).setAlumno(null);//Borra al alumno que estaba logeado
        Intent login = new Intent(ProfileActivity.this, Login.class);
        login.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK); //borra el stack de actividades
        Toast.makeText(getApplicationContext(), "Cerrando sesion ",Toast.LENGTH_SHORT).show();
        startActivity(login); //vuelve al login
    }

    public void ToHorario(View view){
        Intent i = new Intent(this, Horario.class);
        //i.putExtra("alumno", getIntent().getStringExtra("alumno"));
        startActivity(i);
    }

//    public void Profile(View view){
//        startActivity(new Intent(this, ProfileActivity.class));
//    }
>>>>>>> 299667b83039d389c6a2becf5711431bb81dfcad
}













