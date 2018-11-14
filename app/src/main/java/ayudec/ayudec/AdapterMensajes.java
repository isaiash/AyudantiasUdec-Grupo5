package ayudec.ayudec;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by user on 04/09/2017. 04
 */

public class AdapterMensajes extends RecyclerView.Adapter<HolderMensaje> {

    private List<MensajeRecibir> listMensaje = new ArrayList<>();
    private Context c;

    public AdapterMensajes(Context c) {
        this.c = c;
    }

    public void addMensaje(MensajeRecibir m){
        listMensaje.add(m);
        notifyItemInserted(listMensaje.size());
    }

    @Override
    public HolderMensaje onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(c).inflate(R.layout.card_view_mensajes,parent,false);
        return new HolderMensaje(v);
    }

    @Override
    public void onBindViewHolder(HolderMensaje holder, int position) {
        holder.getNombre().setText(listMensaje.get(position).getNombre());
        holder.getMensaje().setText(listMensaje.get(position).getMensaje());
        if(listMensaje.get(position).getType_mensaje().equals("2")){
            holder.getFotoMensaje().setVisibility(View.VISIBLE);
            holder.getMensaje().setVisibility(View.VISIBLE);
            Glide.with(c).load(listMensaje.get(position).getUrlFoto()).into(holder.getFotoMensaje());
        }else if(listMensaje.get(position).getType_mensaje().equals("1")){
            holder.getFotoMensaje().setVisibility(View.GONE);
            holder.getMensaje().setVisibility(View.VISIBLE);
        }
        if(listMensaje.get(position).getFotoPerfil().isEmpty()){
            holder.getFotoMensajePerfil().setImageResource(R.mipmap.ic_launcher);
        }else{
            Glide.with(c).load(listMensaje.get(position).getFotoPerfil()).into(holder.getFotoMensajePerfil());
        }
        Long codigoHora = listMensaje.get(position).getHora();
        Date d = new Date(codigoHora);
        SimpleDateFormat sdf = new SimpleDateFormat("hh:mm:ss a");//a pm o am
        holder.getHora().setText(sdf.format(d));

        // ESTA WEA EN ADELANTE
        Alumno _alumno = ((GlobalVariables) ((Activity)c).getApplication()).getAlumno();
        if(listMensaje.get(position).getNombre().equals(_alumno.get_nombre())) {
            Log.d("DATOS:", "--" + listMensaje.get(position).getNombre() + "---" + _alumno.get_nombre()+"---");
            View layout =  holder.getView();
            RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) layout.getLayoutParams();
            layoutParams.addRule(RelativeLayout.ALIGN_PARENT_END, 1);
            layoutParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, 1);
            holder.getView().setLayoutParams(layoutParams);
        }
        else{
            Log.d("DATOS:", "TUDO BEM MANO " + listMensaje.get(position).getMensaje());
            Log.d("DATOS:", "--" + listMensaje.get(position).getNombre() + "---" + _alumno.get_nombre()+"---");
            Log.d("MENSAJE:",  listMensaje.get(position).getMensaje());
        }
    }

    @Override
    public int getItemCount() {
        return listMensaje.size();
    }

}