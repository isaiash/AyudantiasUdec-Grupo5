package ayudec.ayudec;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Debug;
import android.util.Log;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;


public class ControladorBase extends AsyncTask<Void, Void, Void> {
    private Alumno _alumno;
    private Login _login;
    private Connection c;
    private Statement stmt;
    private String query;
    private HomeActivity homeActivity;
    private Ayudantia[] ayudantias;
    private List<String> asignaturas, salas;
    private NuevaAyudantia nuevaAyudantia;
    private Ayudantia ayudantia;

    private int tipo, estado;

    private boolean conectado = false;
    private boolean entro = false;

    ResultSet rs;


    public ControladorBase() {
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
                        //System.out.println("+++" + rs.first()); // comente esto
                        while (rs.next()) {
                            entro = true;
                            Log.e("validado", "usuario encontrado");
                            _alumno.set_matricula(rs.getString(1));
                            _alumno.set_nombre(rs.getString(3));
                            _alumno.set_carrera(rs.getString(5));
                            _alumno.set_horario(rs.getInt(6));

                        }
                        stmt.close();
                        rs.close();
                        c.commit();
                        c.close();
                        break;
                    // Case2: query para obtener lista de ayudantias del homeActivity
                    case 2:
                        Log.d("case 2","entre al case 2");
                        GlobalVariables app = (GlobalVariables) homeActivity.getApplication();
                        Alumno current_alumno = app.getAlumno();
                        Log.d("global variable alumno", current_alumno.get_nombre());
                        if (current_alumno != null){
                            Log.d("else", "haciendo la query");
                            query = "SELECT ayudante.nombre, ayudante.carrera, asignatura.nombre, sala.id_sala, sala.horario, ayudantia.capacidad " +
                                    "FROM ayudantia_udec.alumno, ayudantia_udec.inscribe, ayudantia_udec.pertenece, ayudantia_udec.asignatura, ayudantia_udec.sala, ayudantia_udec.alumno as ayudante, ayudantia_udec.estudia, ayudantia_udec.ayudantia, ayudantia_udec.pide " +
                                    "WHERE alumno.matricula = " + current_alumno.get_matricula() + " and " +
                                    "inscribe.matricula = alumno.matricula and " +
                                    "inscribe.cod_asignatura = asignatura.codigo and " +
                                    "inscribe.semestre LIKE '%2013%' and " +
                                    "estudia.cod_asignatura = asignatura.codigo and " +
                                    "estudia.id_ayudantia = ayudantia.id and " +
                                    "pertenece.matricula = ayudante.matricula and " +
                                    "pertenece.id_ayudantia = ayudantia.id and " +
                                    "pertenece.ayudante = TRUE and " +
                                    "pide.sala = sala.id_sala and " +
                                    "pide.id_ayudantia = ayudantia.id;";


                            Log.d("query2", query);
                            c = DriverManager.getConnection("jdbc:postgresql://plop.inf.udec.cl:5432/karleyparada/ayudantia_udec", "karleyparada", "karley.123");
                            c.setAutoCommit(false);
                            stmt = c.createStatement();
                            rs = stmt.executeQuery(query);
                            // nuevo array list donde guardar las ayudantias creadas a partir del query
                            ArrayList<Ayudantia> ayudantias_arraylist = new ArrayList<Ayudantia>();
                            entro = false;
                            while (rs.next()) {
                                entro = true;
                                Log.d("query2", rs.getString(1));
                                // String nombre, String carrera, String ramo, String horario, String sala, String cupos, String imagen_url, String rating
                                // select asignatura.nombre, ayudante.carrera, asignatura.nombre, sala.id_sala, sala.horario, ayudantia.capacidad
                                Ayudantia aux = new Ayudantia(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(5), rs.getString(4), rs.getString(6), "","");
                                Log.d("cupos",aux.getCupos());
                                ayudantias_arraylist.add(aux);
                            }
                            if(ayudantias_arraylist.size() > 0){
                                this.ayudantias = new Ayudantia[ayudantias_arraylist.size()];
                                ayudantias = ayudantias_arraylist.toArray(ayudantias);

                            }
                            else{
                                entro = false;
                            }
                        }
                        stmt.close();
                        rs.close();
                        c.commit();
                        c.close();
                        break;
                    //Case3: llamado para obtener la lista de ramos y salas
                    case 3:
                        Log.d("case 3","entré al case 3");
                        query = "SELECT nombre " +
                                "FROM ayudantia_udec.asignatura;";
                        Log.d("query3", query);
                        c = DriverManager.getConnection("jdbc:postgresql://plop.inf.udec.cl:5432/karleyparada/ayudantia_udec", "karleyparada", "karley.123");
                        c.setAutoCommit(false);
                        stmt = c.createStatement();
                        rs = stmt.executeQuery(query);
                        entro = false;
                        asignaturas = new ArrayList<String>();
                        while(rs.next()){
                            entro = true;
                            //Log.d("query3", rs.getString(1));
                            asignaturas.add(rs.getString(1));
                        }
                        query = "SELECT id_sala " +
                                "FROM ayudantia_udec.sala;";
                        rs = stmt.executeQuery(query);
                        salas = new ArrayList<String>();
                        if (entro){
                            entro = false;
                            while(rs.next()){
                                entro = true;
                                salas.add(rs.getString(1));
                            }
                        }
                        break;
                    case 4:
                        Log.d("case 4", "entré al case 4");
                        Log.d("case4", ayudantia.getNombre());
                        Log.d("case 4", ayudantia.getCupos());
                        int matricula = Integer.parseInt(ayudantia.getNombre());
                        int cupos = Integer.parseInt((ayudantia.getCupos()));
                        //query = "SELECT \"crearayudantia\" (" + this.ayudantia.getNombre() + ",'" + this.ayudantia.getSala() +"', '" + this.ayudantia.getRamo() + "',"+ this.ayudantia.getCupos() + ");";

                        //Log.d("case 4", query);
                        c = DriverManager.getConnection("jdbc:postgresql://plop.inf.udec.cl:5432/karleyparada/ayudantia_udec", "karleyparada", "karley.123");
                        c.setAutoCommit(false);
                        CallableStatement upperProc = c.prepareCall("{ call ayudantia_udec.crearayudantia( ?, ?, ?, ?) }");
                        upperProc.setInt(1, matricula);
                        upperProc.setString(2,ayudantia.getSala());
                        upperProc.setString(3,ayudantia.getRamo());
                        upperProc.setInt(4,cupos);
                        upperProc.execute();
                        entro = true;
                        c.commit();
                        c.close();
                        Log.d("case 4", "ayudantia creada");
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
            case 2:
                if (entro) homeActivity.setAyudantias(ayudantias);
                break;
            case 3:
                if (entro) {
                    nuevaAyudantia.setAsignaturas(this.asignaturas);
                    nuevaAyudantia.setSalas(this.salas);
                }
                break;
            case 4:
                if (entro){
                    nuevaAyudantia.creada();
                }
                else{
                    nuevaAyudantia.fallada("NO SE PUDO CREAR LA AYUDANTÍA");
                }
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

    public void setLogin(Login login){
        this._login = login;
    }

    public void setAyudantia(Ayudantia ayudantia){
        this.ayudantia = ayudantia;
    }

    public boolean isEntro() {
        return entro;
    }

    public void setEntro(boolean entro) {
        this.entro = entro;
    }

    public void setHome(HomeActivity home){
        this.homeActivity = home;
    }

    public void setNueva(NuevaAyudantia nueva){
        this.nuevaAyudantia = nueva;
    }

    public String getQuery() { return query; }

    public boolean isConectado() { return conectado; }

}