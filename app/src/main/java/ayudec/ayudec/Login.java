package ayudec.ayudec;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class Login extends AppCompatActivity {
    private Alumno _alumno;
    private ControladorBase _cb;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        final Button boton = (Button) findViewById(R.id.iniciar_sesion);
        boton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String usuario = ((EditText)findViewById(R.id.correo)).getText().toString();
                String password = ((EditText)findViewById(R.id.password)).getText().toString();
                _alumno = new Alumno("",usuario, password, "");
                _cb = new ControladorBase(_alumno);
                _cb.ConectarBaseDeDatos();
                _alumno = _cb.get_alumno();

                System.out.println(_alumno.get_user() + " -- " + _alumno.get_password() + "(" + _alumno.get_nombre() + ")");

                if(usuario.equals(_alumno.get_user())){
                    if(password.equals(_alumno.get_password())){
                        Intent nuevoform = new Intent(Login.this,MainActivity.class);
                        startActivity(nuevoform);
                    }
                    else{
                        Toast.makeText(getApplicationContext(),"Usuario y/o contraseña no válidos",Toast.LENGTH_SHORT).show();
                    }
                }
                else{
                    Toast.makeText(getApplicationContext(),"Usuario y/o contraseña no válidos 2222222",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
