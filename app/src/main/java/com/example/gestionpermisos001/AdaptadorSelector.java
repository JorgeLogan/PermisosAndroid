package com.example.gestionpermisos001;

import android.content.Context;
import android.media.Image;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class AdaptadorSelector extends ArrayAdapter<DTOElementoMultimedia> {
    private DTOElementoMultimedia[] listado;
    private Context contexto;

    /*
    Constructor basado en el padre.
        Necesita el contexto, el id del recurso layout que usara, y el listado con el que rellenarlo
     */
    public AdaptadorSelector(@NonNull Context context, int resource, @NonNull DTOElementoMultimedia[] objects) {
        super(context, resource, objects);
        this.contexto = context;
        this.listado = objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        //View vista =  super.getView(position, convertView, parent);
        View vista = LayoutInflater.from(this.contexto).inflate(R.layout.layout_seleccion, null);

        ImageView imagen = (ImageView)vista.findViewById(R.id.selec_img);
        TextView nombre = (TextView)vista.findViewById(R.id.selec_nombre);
        TextView tipo =(TextView)vista.findViewById(R.id.selec_tipo);

        // Segun el tipo de objeto ponemos un icono u otro
        switch(this.listado[position].getTipo()){
            case Imagen:
                imagen.setImageResource(R.drawable.item_camara);
                break;
            case Sonido:
                imagen.setImageResource(R.drawable.item_audio);
                break;
            case Video:
                imagen.setImageResource(R.drawable.item_video);
                break;
        }

        // Ponemos el nombre del recurso y el tipo
        nombre.setText(this.listado[position].getNombre());
        tipo.setText(this.listado[position].getTipo().toString());

        // Devolvemos la vista
        return vista;
    }
}
