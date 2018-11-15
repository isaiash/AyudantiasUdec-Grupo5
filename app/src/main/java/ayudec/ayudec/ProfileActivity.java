package ayudec.ayudec;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.inputmethodservice.Keyboard;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.CompletionInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;


import com.allyants.chipview.ChipView;
import com.allyants.chipview.SimpleChipAdapter;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.Profile;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import java.util.ArrayList;

public class ProfileActivity extends AppCompatActivity {

    private Alumno _alumno;
    private ControladorBase _cb;
    String defCorreoText = "Presiona para ingresar un correo electrónico...";
    String defFonoText = "Presiona para ingresar un número telefónico...";
    CallbackManager callbackManager;
    private LocalDBHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        db = new LocalDBHelper(this);

        setContentView(R.layout.profile_activity_layout);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        _cb = new ControladorBase();

        ScrollView sv = (ScrollView) findViewById(R.id.principal);
        final TextView correoElectText = findViewById(R.id.mailDisplay);
        final TextView fonoText = findViewById(R.id.phoneDisplay);
        final EditText inputCorreo = findViewById(R.id.correoInput);
        final EditText inputFono = findViewById(R.id.fonoInput);

        inputCorreo.setVisibility(View.GONE);
        inputFono.setVisibility(View.GONE);

        callbackManager = CallbackManager.Factory.create();

        // Se obtiene el alumno a partir de la variable global
        _alumno = ((GlobalVariables) this.getApplication()).getAlumno();

        // Obtiene el correo del alumno desde la BD
        String correoEnBase = _alumno.get_correo();
        correoElectText.setText(correoEnBase);

        // Obtiene el teléfono del alumno desde la BD
        String fonoEnBase = _alumno.get_telefono();
        fonoText.setText(fonoEnBase);
        //Mostrar correo en la actividad
        if (correoEnBase != null) {
            correoElectText.setText(correoEnBase);
        }
        //Mostrar fono en la actividad
        if (fonoEnBase != null) {
            fonoText.setText(fonoEnBase);
        }

        ((TextView)findViewById(R.id.userName)).setText(_alumno.get_nombre());

        ((TextView)findViewById(R.id.userCar)).setText(_alumno.get_carrera());

        ((TextView)findViewById(R.id.userType)).setText(_alumno.get_matricula());

        // Listener para hacer swipe a la derecha y volver a la activity Home
        sv.setOnTouchListener(new OnSwipeTouchListener(ProfileActivity.this) {
            @Override
            public void onSwipeLeft() {
                finish();
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
        });

        // "Convertir" de TextView a EditText
        correoElectText.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                correoElectText.setVisibility(View.GONE);
                inputCorreo.setVisibility(View.VISIBLE);
                inputCorreo.requestFocus();
                return true;
            }
        });

        inputCorreo.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int keyCode, KeyEvent keyEvent) {
                if ((keyEvent.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    String correo = inputCorreo.getText().toString();
                    if (correo.equals("")){
                        Toast.makeText(getApplicationContext(),"Ingrese un correo electrónico.", Toast.LENGTH_SHORT).show();
                    } else {
                        if (isValidEmail(correo)){
                            correoElectText.setText(correo);
                            _alumno.set_correo(correo);
                            correoElectText.setVisibility(View.VISIBLE);
                            Toast.makeText(getApplicationContext(),"Correo electrónico actualizado.", Toast.LENGTH_SHORT).show();
                            hideKeyboardFrom(getApplicationContext(),view);
                            correoElectText.setCursorVisible(false);
                            inputCorreo.setVisibility(View.GONE);
                            _cb.set_alumno(_alumno);
                            _cb.ejecutar();
                            return true;
                        } else {
                            Toast.makeText(getApplicationContext(),"El texto ingresado no corresponde a un correo electrónico.", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
                return false;
            }
        });

        inputCorreo.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if(!b){
                    inputCorreo.setVisibility(View.GONE);
                    inputCorreo.clearFocus();
                    correoElectText.setVisibility(View.VISIBLE);
                }
            }
        });

        fonoText.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                fonoText.setVisibility(View.GONE);
                inputFono.setVisibility(View.VISIBLE);
                inputFono.requestFocus();
                return true;
            }
        });

        inputFono.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int keyCode, KeyEvent keyEvent) {
                if ((keyEvent.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    String numeroTel = inputFono.getText().toString();
                    if (numeroTel.equals("")){
                        Toast.makeText(getApplicationContext(),"Ingrese un correo electrónico.", Toast.LENGTH_SHORT).show();
                    } else {
                        if (isValidPhone(numeroTel) ) {
                            fonoText.setText(numeroTel);
                            _alumno.set_telefono(numeroTel);
                            fonoText.setVisibility(View.VISIBLE);
                            Toast.makeText(getApplicationContext(), "Número de teléfono actualizado.", Toast.LENGTH_SHORT).show();
                            hideKeyboardFrom(getApplicationContext(), view);
                            inputFono.setCursorVisible(false);
                            inputFono.setVisibility(View.GONE);
                            _cb.set_alumno(_alumno);
                            _cb.ejecutar();
                            return true;
                        } else {
                            Toast.makeText(getApplicationContext(),"El texto ingresado no corresponde a un número telefónico.", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
                return false;
            }
        });

        inputFono.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if(!b){
                    inputFono.setVisibility(View.GONE);
                    inputFono.clearFocus();
                    fonoText.setVisibility(View.VISIBLE);
                }
            }
        });

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }


        LoginButton loginButton = (LoginButton) findViewById(R.id.login_button);
        loginButton.setReadPermissions("email");

        // Callback registration
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                // App code
                Profile profile = Profile.getCurrentProfile();
                String msg = profile.getFirstName() + " " + profile.getLastName();
                Toast.makeText(ProfileActivity.this, msg, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancel() {
                // App code
            }

            @Override
            public void onError(FacebookException exception) {
                // App code
                Toast.makeText(ProfileActivity.this, "", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.home)
            finish();
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }


// Borra las variables globales, el stack de activities y redirige al activity del login
    public void cerrarSesion(View view){
        ((GlobalVariables) this.getApplication()).setSesion_iniciada(false); //Deja la variable global sesion_iniciada en false
        ((GlobalVariables) this.getApplication()).setAlumno(null);//Borra al alumno que estaba logeado
        db.closeSession();
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

    public static void hideKeyboardFrom(Context context, View view) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    public final static boolean isValidEmail(CharSequence target) {
        return !TextUtils.isEmpty(target) && android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
    }

    public final static boolean isValidPhone(CharSequence target) {
        return !TextUtils.isEmpty(target) && Patterns.PHONE.matcher(target).matches();
    }
//    public void Profile(View view){
//        startActivity(new Intent(this, ProfileActivity.class));
//    }
}













