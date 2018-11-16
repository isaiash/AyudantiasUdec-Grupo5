package ayudec.ayudec;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.math.BigInteger;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import org.junit.runners.MethodSorters;
import org.junit.FixMethodOrder;

import static com.google.android.gms.common.internal.safeparcel.SafeParcelable.NULL;
import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class UnitTest {

    public int matricula = 123456789;
    public String usuario = "Test";
    public String nombre = "Test";
    public String password = "test";
    public String carrera = "Test";
    public BigInteger horario = BigInteger.valueOf(999);
    public String correo = "test@test";
    public String telefono = "999";
    public String sala = "Test";
    public String ramo = "Test";
    public String cupo = "999";
    Alumno _alumno;
    Login login;
    Connection c;
    Statement stmt = null;
    ControladorBase _cb;

    @Test
    public void t1_Coneccion() {
        boolean conectado = true;
        _cb = new ControladorBase();
        _cb.ConectarBaseDeDatos();
        assertEquals(_cb.isConectado(), conectado);
    }

    @Test
    public void t2_logearse() {
        login = new Login();
        _alumno = new Alumno("", usuario, password, "", "", horario);
        _cb = new ControladorBase();
        _cb.set_alumno(_alumno);
        _cb.setLogin(login);
        _cb.setTipo(1);
    }

    @Test
    public void t3_ayudantia() {
        Ayudantia aux = new Ayudantia("999",nombre, "", ramo, "", sala, cupo,"", "",false,"999");
        _cb = new ControladorBase();
        _cb.setAyudantia(aux);
        _cb.setTipo(4);
    }
}


