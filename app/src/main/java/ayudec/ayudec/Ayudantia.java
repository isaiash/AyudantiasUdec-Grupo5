package ayudec.ayudec;

public class Ayudantia {

    private String nombre_ayudante, carrera, ramo, rating, horario, sala, cupos, imagen_url;

    public Ayudantia(String nombre, String carrera, String ramo, String horario, String sala, String cupos, String imagen_url, String rating){
        this.nombre_ayudante = nombre;
        this.carrera = carrera;
        this.ramo = ramo;
        this.horario = horario;
        this.sala = sala;
        this.cupos = cupos;
        this.rating = rating;
        this.imagen_url = imagen_url;
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

    public String getImagen_url(){
        return this.imagen_url;
    }
}
