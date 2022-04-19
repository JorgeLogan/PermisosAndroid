package com.example.gestionpermisos001;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import org.w3c.dom.Text;

import java.util.List;

public class AdaptadorContactos extends ArrayAdapter<DTOContactos> {
    // Atributos
    private Context contexto;
    private DTOContactos[] listado;

    // Constructor pedido por el IDE
    public AdaptadorContactos(@NonNull Context context, int resource, @NonNull DTOContactos[] objects) {
        super(context, resource, objects);
        this.contexto = context;
        this.listado = objects;
    }

    /**
     * Sobreescribimos el getView para configurar su interfaz
     * @param position la posicion a mostrar del listado
     * @param convertView la vista convertida
     * @param parent el viewgroup padre
     * @return
     */
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        // Necesitamos inflar una vista XML hecha para poder mostrar estos elementos
        LayoutInflater inflador = LayoutInflater.from(contexto);
        View vista = inflador.inflate(R.layout.adaptador_contactos, null);

        // Una vez inflada, cargamos los elementos del XML para poder insertar los elementos
        TextView tvNombre = (TextView)vista.findViewById(R.id.tvContactoNombre);
        TextView tvTelefono = (TextView)vista.findViewById(R.id.tvContactoTelefono);

        // Ahora actualizamos su valor segun el elemento del listado en la posicion dada
        tvNombre.setText(this.listado[position].getNombre());
        tvTelefono.setText(this.listado[position].getTelefono());

        // Devolvemos la vista
        return vista;
    }
}
