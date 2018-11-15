package ayudec.ayudec;

public class Ayudantia {

    private String nombre_ayudante, carrera, ramo, rating, horario, sala, cupos, imagen_url, id_ayudantia, current_cupos;
    private boolean inscrito;

    public Ayudantia(String id_ayudantia, String nombre, String carrera, String ramo, String horario, String sala, String cupos, String imagen_url, String rating, boolean inscrito, String current_cupos){
        this.id_ayudantia = id_ayudantia;
        this.nombre_ayudante = nombre;
        this.carrera = carrera;
        this.ramo = ramo;
        this.horario = horario;
        this.sala = sala;
        this.cupos = cupos;
        this.rating = rating;
        this.imagen_url = imagen_url;
        this.inscrito = inscrito;
        this.current_cupos = current_cupos;
    }

    public String getNombre() {
        return nombre_ayudante;
    }

    public String getCarrera() {
        return carrera;
    }

    public String getRamo() {
        return ramo;
    }

    public String getRating() {
        return rating;
    }

    public String getHorario() {
        return horario;
    }

    public String getSala() {
        return sala;
    }

    public String getCupos(){
        return this.cupos;
    }

    public String getId_ayudantia(){ return this.id_ayudantia; }

    public String getImagen_url(){
        return this.imagen_url;
    }

    public String getCurrent_cupos(){ return current_cupos; }

    public void setInscrito(boolean value){this.inscrito = value;}

    public boolean getInscrito(){ return this.inscrito; }
}
