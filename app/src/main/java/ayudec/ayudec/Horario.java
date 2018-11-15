package ayudec.ayudec;

import java.math.BigInteger;
import java.util.ArrayList;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class Horario extends AppCompatActivity{
    Alumno _alumno;

    private ArrayList<Button> hour = new ArrayList<>();

    public static int size = 13*5;
    boolean[] weekDatas = new boolean[size];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.horario);

        _alumno = ((GlobalVariables) this.getApplication()).getAlumno();

        for (int i = 0; i < size; i++) {
            String buttonID = "button" + i;
            int resID = getResources().getIdentifier(buttonID, "id", getPackageName());
            hour.add((Button) findViewById(resID));
        }

        try
        {
            new LoadViewsInToWeekView().execute("");
        } catch (Exception e)
        {
            Log.getStackTraceString(e);
        }
    }

    public void setButton(int i) {
        Button b = hour.get(i);
        b.setId(i);

        if (weekDatas[i]) {
            b.setBackgroundColor(Color.parseColor("#9ACC61"));
            b.setOnClickListener(ClickOnGreen);
        }else{
            b.setBackgroundColor(Color.parseColor("#ffffff"));
            b.setOnClickListener(ClickOnWhite);
        }
    }

    public OnClickListener ClickOnGreen = new OnClickListener() {

        @Override
        public void onClick(View v) {
            v.setBackgroundColor(Color.parseColor("#ffffff"));
            int id = v.getId();
            weekDatas[id] = false;
            saveHorario();
            v.setOnClickListener(ClickOnWhite);
        }
    };
    public OnClickListener ClickOnWhite = new OnClickListener() {

        @Override
        public void onClick(View v) {
            v.setBackgroundColor(Color.parseColor("#9ACC61"));
            int id = v.getId();
            weekDatas[id] = true;
            saveHorario();
            v.setOnClickListener(ClickOnGreen);
        }
    };
    public void saveHorario(){
        BigInteger n = BigInteger.valueOf(0);
        for (int i = size-1; i >= 0; --i) {
            n = n.shiftLeft(1).add(BigInteger.valueOf(weekDatas[i] ? 1 : 0));
        }
        this._alumno.set_horario(n);
        ControladorBase _cb = new ControladorBase();
        _cb.set_alumno(this._alumno);
        _cb.setTipo(5);
        _cb.ejecutar();
    }

    public class LoadViewsInToWeekView extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {
            try {
                BigInteger hor = _alumno.get_horario();
                for(int i = size-1; i >= 0; i--) {
                    weekDatas[i] = !(hor.and(BigInteger.valueOf(1).shiftLeft(i))).equals(BigInteger.valueOf(0));
                }

            } catch (Exception e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(String str) {
            try {
                for (int i = 0; i < size; i++) {
                    setButton(i);
                }
            } catch (Exception e) {
                Log.getStackTraceString(e);
            }
            if (dialog.isShowing()) {
                dialog.dismiss();
            }
        }
        ProgressDialog dialog;

        @Override
        protected void onPreExecute() {
            try {
                dialog = ProgressDialog.show(Horario.this, null, null,
                        true, false);
                dialog.setContentView(R.layout.horario);
            } catch (Exception e) {
                Log.getStackTraceString(e);
            }
        }
    }
}