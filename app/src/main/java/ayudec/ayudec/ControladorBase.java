package ayudec.ayudec;

import android.os.AsyncTask;
import android.util.Log;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class ControladorBase extends AsyncTask<Void, Void, Void> {
    private Alumno _alumno;
    private Login _login;
    private Connection c;
    private Statement stmt;
    private String query;


    private int tipo, estado;

    private boolean conectado = false, entro = false;

    ResultSet rs;


    public ControladorBase(Alumno alumno, Login login) {
        _login = login;
        _alumno = alumno;
    }


    public void ConectarBaseDeDatos() {
        try {
            Class.forName("org.postgresql.Driver");
            c = DriverManager.getConnection("jdbc:postgresql://plop.inf.udec.cl:5432/karleyparada/", "karleyparada", "karley.123");
            c.setAutoCommit(false);
            conectado = true;
            c.close();
            conectado = true;
        } catch (ClassNotFoundException | SQLException e) {
            Log.e("redirect", "Falló la conexión :(");
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            conectado = false;
        }
    }


    public void ejecutar() {
        Log.e("redirect", "execute");
        execute();
    }

    @Override
    protected Void doInBackground(Void... params) {
        Log.e("redirect", "llegue al try");
        try {
            this.ConectarBaseDeDatos();
            Log.e("redirect", "conecte?" + !c.isClosed());
            if (conectado) {
                Log.e("redirect", "llegue al switch " + tipo);
                switch (tipo) {
                    case 1:
                        query = "SELECT * FROM ayudantia_udec.alumno as al  WHERE al.usuario  = '" + _alumno.get_user() + "' AND al.password = '" + _alumno.get_password() + "';";
                        System.out.println(query);
                        c = DriverManager.getConnection("jdbc:postgresql://plop.inf.udec.cl:5432/karleyparada/ayudantia_udec", "karleyparada", "karley.123");
                        c.setAutoCommit(false);
                        stmt = c.createStatement();
                        rs = stmt.executeQuery(query);

                        entro = false;
                        //System.out.println("+++" + rs.first()); // comente esta wea
                        while (rs.next()) {
                            entro = true;
                            Log.e("validado", "usuario encontrado");
                            _alumno.set_matricula(rs.getString(1));
                            _alumno.set_nombre(rs.getString(3));
                            _alumno.set_carrera(rs.getString(5));

                        }
                        stmt.close();
                        rs.close();
                        c.commit();
                        c.close();
                        break;
                }
            }
        } catch (SQLException e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            estado = 0;
        }
        return null;
    }

    protected void onPostExecute(Void result) {
        switch (tipo) {
            case 1:
                if (entro) _login.validarEntrada();
                else _login.negarEntrada();
                break;
            default:
                break;
        }
    }

    public void setTipo(int tipo) {
        this.tipo = tipo;
    }

    public Alumno get_alumno() {
        return _alumno;
    }

    public void set_alumno(Alumno _alumno) {
        this._alumno = _alumno;
    }

    public boolean isEntro() {
        return entro;
    }

    public void setEntro(boolean entro) {
        this.entro = entro;
    }
}