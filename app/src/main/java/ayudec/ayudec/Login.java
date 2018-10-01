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

                if(usuario.equals("admin")&& password.equals("admin")){
                    Intent nuevoform = new Intent(Login.this,MainActivity.class);
                    startActivity(nuevoform);
                }
                else{
                    Toast.makeText(getApplicationContext(),"Usuario Incorrecto",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
