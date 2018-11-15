package ayudec.ayudec;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class CustomAdapter extends BaseAdapter {

    private Ayudantia[] ayudantias;
    private Context context;
    private HomeActivity homeActivity;
    Activity activity;
    final ControladorBase _cb;
    private Alumno alumno;



    public CustomAdapter(Context context, Ayudantia[] ayudantias, HomeActivity homeActivity, Alumno alumno){
        this.context = context;
        this.ayudantias = ayudantias;
        this.homeActivity = homeActivity;
        this.alumno = alumno;
        _cb = new ControladorBase();
        _cb.setHome(homeActivity);
        _cb.set_alumno(this.alumno);
        _cb.setTipo(6);
    }

    public int setColor(String cupos_){
        int cupos = Integer.parseInt(cupos_);

        if(cupos > 15){
            return Color.parseColor("#CC2828");
        }
        else if(cupos > 10){
            return Color.parseColor("#CDC648");
        }
        else{
            return Color.parseColor("#19571E");
        }

    }


    @Override
    public int getCount() {
        return ayudantias.length;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View view, ViewGroup viewGroup) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View myView = view;

        if(view == null){
            myView = inflater.inflate(R.layout.activity_gridview,null);
        }

        // Se le pone el nombre del ayudante a la textview
        TextView ayudante_nombre_tv = (TextView) myView.findViewById(R.id.ayudante_nombre);
        String ayudante_nombre = ayudantias[position].getNombre();
        ayudante_nombre_tv.setText(ayudante_nombre);

        // Se le pone el ramo a la textview
        TextView ramo_tv = (TextView) myView.findViewById(R.id.ramo_tv);
        String ramo = ayudantias[position].getRamo();
        ramo_tv.setText(ramo);

        // Se le pone la sala a la textview
        TextView sala_tv = (TextView) myView.findViewById(R.id.sala_tv);
        String sala = ayudantias[position].getSala();
        sala_tv.setText(sala);

        // Se le pone el horario a la textview
        TextView horario_tv = (TextView) myView.findViewById(R.id.horario_tv);
        String horario = ayudantias[position].getHorario();
        horario_tv.setText(horario);

        // Se le pone los cupos a la textview
        TextView cupos_tv = (TextView) myView.findViewById(R.id.cupos_tv);
        String cupos = ayudantias[position].getCupos();
        String current_cupos = ayudantias[position].getCurrent_cupos();
        cupos_tv.setText(current_cupos + "/" + cupos);
        cupos_tv.setTextColor(setColor(current_cupos));

        // Se le pone la carrera a la textview
        TextView carrera_tv = (TextView) myView.findViewById(R.id.ayudante_carrera);
        String carrera = ayudantias[position].getCarrera();
        carrera_tv.setText(carrera);


        // Se le pone la imagen (Si es que existe)
        ImageView ayudante_iv = (ImageView) myView.findViewById(R.id.ayudante_imagen);
        ayudante_iv.setImageResource(R.drawable.icono);

        TextView inscrito_tv = (TextView) myView.findViewById(R.id.inscrito);
        boolean inscrito = ayudantias[position].getInscrito();
        if(inscrito){
            inscrito_tv.setText("Si");
            inscrito_tv.setTextColor(Color.parseColor("#2A9267"));
        }
        else{
            inscrito_tv.setText("No");
            inscrito_tv.setTextColor(Color.parseColor("#B20000"));
        }

        // Se pone el rating (mientras por defecto se ponen las 5 estrellas siempre)
        ImageView rating_iv = (ImageView) myView.findViewById(R.id.rating_imagen);
        rating_iv.setImageResource(R.drawable.rating);

        myView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if(ayudantias[position].getInscrito()){
                    AlertDialog.Builder builder1 = new AlertDialog.Builder(context);
                    builder1.setMessage("¿ Desea desinscribirse de la ayudantía ?");
                    builder1.setCancelable(true);

                    builder1.setPositiveButton(
                            "Si",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    _cb.setTipo(7);
                                    _cb.setAyudantia(ayudantias[position]);
                                    _cb.ejecutar();
                                    //_cb.setTipo(6);
                                    homeActivity.callController();
                                }
                            });
                    builder1.setNegativeButton(
                            "No",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.cancel();
                                }
                            });
                    AlertDialog alert11 = builder1.create();
                    alert11.show();
                }

                return true;
            }
        });

        myView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id_ayudantia = ayudantias[position].getId_ayudantia();
                if(!ayudantias[position].getInscrito()) {
                    AlertDialog.Builder builder1 = new AlertDialog.Builder(context);
                    builder1.setMessage("¿ Desea inscribirse a " + ayudantias[position].getRamo() + " ?");
                    builder1.setCancelable(true);

                    builder1.setPositiveButton(
                            "Si",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    _cb.setAyudantia(ayudantias[position]);
                                    _cb.setTipo(6);
                                    _cb.ejecutar();
                                    homeActivity.callController();
                                }
                            });
                    builder1.setNegativeButton(
                            "No",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.cancel();
                                }
                            });
                    AlertDialog alert11 = builder1.create();
                    alert11.show();
                }
                else{
                    Toast.makeText(homeActivity, "Entrando al chat " + ayudantias[position].getId_ayudantia(), Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(homeActivity, Chat.class);
                    i.putExtra("idAyudantia",ayudantias[position].getId_ayudantia());
                    homeActivity.startActivity(i);
                    homeActivity.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                }
            }
        });

        return myView;

    }

}
