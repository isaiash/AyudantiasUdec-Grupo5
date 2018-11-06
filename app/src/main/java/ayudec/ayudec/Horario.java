package ayudec.ayudec;

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
import android.widget.RelativeLayout;

public class Horario extends AppCompatActivity{
    Alumno _alumno;

    private ArrayList<RelativeLayout> day = new ArrayList<>();

    public static int size = 13*5;
    boolean[] weekDatas = new boolean[size];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.horario);

        _alumno = ((GlobalVariables) this.getApplication()).getAlumno();

        day.add((RelativeLayout)findViewById(R.id.relativeLayoutMonDay));
        day.add((RelativeLayout)findViewById(R.id.relativeLayoutTueDay));
        day.add((RelativeLayout)findViewById(R.id.relativeLayoutWedDay));
        day.add((RelativeLayout)findViewById(R.id.relativeLayoutThuDay));
        day.add((RelativeLayout)findViewById(R.id.relativeLayoutFriDay));

        try
        {
            new LoadViewsInToWeekView().execute("");
        } catch (Exception e)
        {
            Log.getStackTraceString(e);
        }
    }

    public Button getButtonToLayout(int marginTop, String jobID, int buttonID, boolean status) {

        @SuppressWarnings("deprecation")
        final RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.FILL_PARENT, 70-1);
        Button button = new Button(getApplicationContext());
        button.setLayoutParams(params);
        if (status) {
            button.setBackgroundColor(Color.parseColor("#9ACC61"));
            button.setOnClickListener(ClickOnGreen);
        }else{
            button.setBackgroundColor(Color.parseColor("#ffffff"));
            button.setOnClickListener(ClickOnWhite);
        }
        button.setId(buttonID);
        params.setMargins(0, marginTop, 1, 0);

        return button;
    }

    public OnClickListener ClickOnGreen = new OnClickListener() {

        @Override
        public void onClick(View v) {
            Button b = (Button) v;
            int id = b.getId();
            weekDatas[id] = false;
            b.setBackgroundColor(Color.parseColor("#ffffff"));
            b.setOnClickListener(ClickOnWhite);
        }
    };
    public OnClickListener ClickOnWhite = new OnClickListener() {

        @Override
        public void onClick(View v) {
            Button b = (Button) v;
            int id = b.getId();
            weekDatas[id] = true;
            b.setBackgroundColor(Color.parseColor("#9ACC61"));
            b.setOnClickListener(ClickOnGreen);
        }
    };

    public class LoadViewsInToWeekView extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {
            try {
                int hor = _alumno.get_horario();

                for(int i = size-1; i >= 0; i--){
                    weekDatas[i] = (hor & (1 << i)) != 0;
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
                    System.out.println(i);
                    if (weekDatas[i]) {
                        day.get(i/13).addView(getButtonToLayout((i%13)*70, "id1", i, weekDatas[i]));
                    }
                    else{
                        day.get(i/13).addView(getButtonToLayout((i%13)*70, "id1", i, weekDatas[i]));
                    }
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
