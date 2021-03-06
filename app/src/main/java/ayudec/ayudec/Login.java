package ayudec.ayudec;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import java.math.BigInteger;

public class Login extends AppCompatActivity {
    private Alumno _alumno;
    private ControladorBase _cb;
    private ProgressDialog _pDialog;
    private LocalDBHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //Comprueba que la sesion esta iniciada y redirige al home
        if(((GlobalVariables) this.getApplication()).getSesion_iniciada()){
            Intent home = new Intent(Login.this, HomeActivity.class);
            startActivity(home);
        }

        db = new LocalDBHelper(this);
        checkIfLogged();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        final Button boton = findViewById(R.id.iniciar_sesion);
        boton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String usuario = ((EditText) findViewById(R.id.correo)).getText().toString();
                String password = ((EditText) findViewById(R.id.password)).getText().toString();
                _alumno = new Alumno("", usuario, password, "", "", BigInteger.valueOf(0));

                _pDialog = new ProgressDialog(Login.this);
                _pDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                _pDialog.setMessage("Iniciando Sesión...");
                _pDialog.setMax(100);
                _pDialog.show();

                _cb = new ControladorBase();
                _cb.set_alumno(_alumno);
                _cb.setLogin(Login.this);
                _cb.setTipo(1);
                _cb.ejecutar();
            }
        });


    }
    public void validarEntrada(){
        _alumno = _cb.get_alumno();
        ((GlobalVariables) this.getApplication()).setAlumno(_alumno);
        ((GlobalVariables)this.getApplication()).setSesion_iniciada(true);//Le indica a la variable sesion_iniciada que esta logeado
        Intent nuevoform = new Intent(Login.this, HomeActivity.class);
        startActivity(nuevoform);
        Toast.makeText(getApplicationContext(), "Bienvenido " + _alumno.get_nombre(), Toast.LENGTH_SHORT).show();
        db.loggear(_alumno);
        _pDialog.dismiss();
    }
    public void negarEntrada(){
        Toast.makeText(getApplicationContext(), "Usuario y/o contraseña no válidos", Toast.LENGTH_SHORT).show();
        _pDialog.dismiss();
    }

    public void checkIfLogged(){
        Log.d("LOGIN", "checkeando si está loggeado");
        if(db.isLogged()){
            _alumno = db.getConnected();
            ((GlobalVariables) this.getApplication()).setAlumno(_alumno);
            ((GlobalVariables)this.getApplication()).setSesion_iniciada(true);//Le indica a la variable sesion_iniciada que esta logeado
            Intent nuevoform = new Intent(Login.this, HomeActivity.class);
            startActivity(nuevoform);
        }

    }

}