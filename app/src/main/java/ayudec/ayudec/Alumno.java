package ayudec.ayudec;

public class Alumno {
    
    private String _nombre, _user, _password, _matricula;

    public Alumno(String nombre, String user, String password, String matricula){
        _nombre = nombre;
        _user = user;
        _password = password;
        _matricula = matricula;

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
}