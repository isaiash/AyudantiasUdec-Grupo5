package ayudec.ayudec;

public class Alumno {
    
    private String _nombre, _user, _password, _matricula, _carrera;

    public Alumno(String nombre, String user, String password, String matricula,String carrera){
        _nombre = nombre;
        _user = user;
        _password = password;
        _matricula = matricula;
        _carrera = carrera;
    }

    public String get_nombre() {
        return _nombre;
    }

    public void set_nombre(String _nombre) {
        this._nombre = _nombre;
    }

    public String get_user() {
        return _user;
    }

    public void set_user(String _user) {
        this._user = _user;
    }

    public String get_password() {
        return _password;
    }

    public void set_password(String _password) {
        this._password = _password;
    }

    public String get_matricula() {
        return _matricula;
    }

    public void set_matricula(String _matricula) {
        this._matricula = _matricula;
    }

    public String get_carrera() {
        return _carrera;
    }

    public void set_carrera(String _carrera) {
        this._carrera = _carrera;
    }
}
