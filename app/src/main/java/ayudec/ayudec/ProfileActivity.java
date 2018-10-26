package ayudec.ayudec;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;


import com.allyants.chipview.ChipView;
import com.allyants.chipview.SimpleChipAdapter;

import java.util.ArrayList;

public class ProfileActivity extends AppCompatActivity {
    Alumno _alumno;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_activity_layout);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        _alumno = ((GlobalVariables) this.getApplication()).getAlumno();
        ((TextView)findViewById(R.id.userName)).setText(_alumno.get_nombre());
        ((TextView)findViewById(R.id.userCar)).setText(_alumno.get_carrera());
        ((TextView)findViewById(R.id.userType)).setText(_alumno.get_matricula());


        // Prueba con Chipviews
        ChipView cvTag = findViewById(R.id.especView);
        ArrayList<Object> data = new ArrayList<>();
        data.add("First Item");
        data.add("Second Item");
        data.add("Third Item");
        data.add("Fourth Item");
        data.add("Fifth Item");
        data.add("Sixth Item");
        data.add("Seventh Item");
        SimpleChipAdapter adapter = new SimpleChipAdapter(data);
        cvTag.setAdapter(adapter);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.home)
            finish();
        return super.onOptionsItemSelected(item);
    }
//borra las variables globales, el stack activities y redirige al activity del login
    public void cerrarSesion(View view){
        ((GlobalVariables) this.getApplication()).setSesion_iniciada(false); //Deja la variable global sesion_iniciada en false
        ((GlobalVariables) this.getApplication()).setAlumno(null);//Borra al alumno que estaba logeado
        Intent login = new Intent(ProfileActivity.this, Login.class);
        login.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK); //borra el stack de actividades
        Toast.makeText(getApplicationContext(), "Cerrando sesion ",Toast.LENGTH_SHORT).show();
        startActivity(login); //vuelve al login
    }

//    public void Profile(View view){
//        startActivity(new Intent(this, ProfileActivity.class));
//    }
}













