package ayudec.ayudec;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class NuevaAyudantia extends AppCompatActivity {

    private List<String> asignaturas;
    private List<String> salas;
    private Spinner sp_salas, sp_asignaturas;
    private Ayudantia newAyudantia;
    private boolean answer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nueva_ayudantia);

        sp_salas = findViewById(R.id.lista_salas);
        sp_asignaturas = findViewById(R.id.lista_asignaturas);
        answer = false;
        getAll();


    }

    public void getAll(){
        ControladorBase _cb = new ControladorBase();
        _cb.setNueva(NuevaAyudantia.this);
        _cb.setTipo(3);
        _cb.ejecutar();
    }

    public void setAsignaturas(List<String> listaAsignaturas){
        this.asignaturas = listaAsignaturas;
        ArrayAdapter<String> dataAtapter = new ArrayAdapter<String>(this,
                R.layout.support_simple_spinner_dropdown_item, asignaturas);

        dataAtapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);

        this.sp_asignaturas.setAdapter(dataAtapter);
    }

    public void setSalas(List<String> listaSalas){
        this.salas = listaSalas;
        ArrayAdapter<String> dataAtapter = new ArrayAdapter<String>(this,
                R.layout.support_simple_spinner_dropdown_item, salas);

        dataAtapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);

        this.sp_salas.setAdapter(dataAtapter);
    }

    public void save(View view){
        String asignatura = this.sp_asignaturas.getSelectedItem().toString();
        String sala = this.sp_salas.getSelectedItem().toString();
        EditText _cupos = findViewById(R.id.cupos_ev);
        EditText _horario = findViewById(R.id.horario_ev);
        String cupos = _cupos.getText().toString();
        Log.d("save", cupos);
        String horario = _horario.getText().toString();
        GlobalVariables app = (GlobalVariables) getApplication();
        Alumno current = app.getAlumno();
        Ayudantia aux = new Ayudantia(current.get_matricula(), "", asignatura, "", sala, cupos,"", "");
        ControladorBase _cb = new ControladorBase();
        _cb.setAyudantia(aux);
        _cb.setTipo(4);
        _cb.setNueva(NuevaAyudantia.this);
        _cb.ejecutar();
    }

    public Ayudantia getAyudantia(){
        return this.newAyudantia;
    }

    public void creada(){
        Toast.makeText(this,"Ayudantía creada con éxito", Toast.LENGTH_SHORT).show();
        finish();
    }

    public void fallada(String error){
        Toast.makeText(this,error, Toast.LENGTH_SHORT).show();
    }
}
