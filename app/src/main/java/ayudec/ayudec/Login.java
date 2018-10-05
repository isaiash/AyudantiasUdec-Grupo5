package ayudec.ayudec;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class Login extends AppCompatActivity {
    private Alumno _alumno;
    private ControladorBase _cb;
    private ProgressDialog _pDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        final Button boton = findViewById(R.id.iniciar_sesion);
        boton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String usuario = ((EditText) findViewById(R.id.correo)).getText().toString();
                String password = ((EditText) findViewById(R.id.password)).getText().toString();
                _alumno = new Alumno("", usuario, password, "");


                _pDialog = new ProgressDialog(Login.this);
                _pDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                _pDialog.setMessage("Procesando...");
                _pDialog.setMax(100);
                _pDialog.show();

                _cb = new ControladorBase(_alumno,Login.this);
                _cb.setTipo(1);
                _cb.ejecutar();
            }
        });
    }
    public void validarEntrada(){
        _alumno = _cb.get_alumno();
        Intent nuevoform = new Intent(Login.this, HomeActivity.class);
        startActivity(nuevoform);
        Toast.makeText(getApplicationContext(), "Bienvenido " + _alumno.get_nombre(), Toast.LENGTH_SHORT).show();
        _pDialog.dismiss();
    }
    public void negarEntrada(){
        Toast.makeText(getApplicationContext(), "Usuario y/o contraseña no válidos", Toast.LENGTH_SHORT).show();
        _pDialog.dismiss();
    }
}