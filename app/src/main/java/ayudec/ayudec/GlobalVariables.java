package ayudec.ayudec;

import android.app.Application;

//clase que mantiene variables globales

public class GlobalVariables extends Application {

    private Boolean sesion_iniciada = false;
    private Alumno alumno;
    public Boolean getSesion_iniciada() {
        return sesion_iniciada;
    }

    public void setSesion_iniciada(Boolean sesion_iniciada) {
        this.sesion_iniciada = sesion_iniciada;
    }

    public void setAlumno(Alumno a){
        this.alumno = a;
    }

    public Alumno getAlumno(){
        return this.alumno;
    }
}
