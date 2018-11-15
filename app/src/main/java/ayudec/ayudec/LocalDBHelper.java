package ayudec.ayudec;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.math.BigInteger;

public class LocalDBHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "logged.db";
    public static final String TABLE_NAME = "alumno";
    public static final int DATABASE_VERSION = 3;


    public LocalDBHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(
                "CREATE TABLE " + TABLE_NAME + " (matricula varchar(50) primary key, usuario varchar(100), nombre varchar(100), carrera varchar(100))"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS alumno");
        onCreate(db);
    }



    public void loggear(Alumno alumno){
        SQLiteDatabase db = this.getWritableDatabase();
        Log.d("LOCAL_DB",alumno.get_matricula());
        ContentValues values = new ContentValues();
        values.put("matricula", alumno.get_matricula());
        values.put("usuario", alumno.get_user());
        values.put("nombre", alumno.get_nombre());
        values.put("carrera", alumno.get_carrera());
        db.insert(TABLE_NAME, null, values);
        db.close();
    }

    public boolean isLogged(){
        SQLiteDatabase db = this.getWritableDatabase();
        int numRows = (int) DatabaseUtils.queryNumEntries(db, TABLE_NAME);
        if(numRows > 0) {
            return true;
        }
        return false;
    }

    public void closeSession(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_NAME);
        db.close();
    }

    public Alumno getConnected(){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT matricula, usuario, nombre, carrera  FROM " + TABLE_NAME,null);
        Alumno alumno = null;
        while(cursor.moveToNext()){
            Log.d("alumnox", cursor.getString(1));
            Log.d("alumnox", cursor.getString(0));
            alumno = new Alumno(cursor.getString(2),cursor.getString(1),"",cursor.getString(0), cursor.getString(3), BigInteger.valueOf(0));
        }
        cursor.close();
        db.close();
        return alumno;
    }
}
