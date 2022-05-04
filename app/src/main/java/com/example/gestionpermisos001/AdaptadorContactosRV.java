package com.example.gestionpermisos001;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

/**
 * Creo una clase para usar como los ArrayAdapter de los ListView pero para un RecyclerView.
 * Su comportamiento es bastante mas complejo
 * Esta clase debe extender de RecyclerView.Adapter<T> siendo T una clase que creamos aqui mismo
 * y luego implementamos todos los metodos que nos pida.
 *
 */
public class AdaptadorContactosRV extends RecyclerView.Adapter<AdaptadorContactosRV.ViewHolder> {
    Context contexto;
    ArrayList<DTOContactos> contactos;


    // Constructor
    public AdaptadorContactosRV(Context c, ArrayList<DTOContactos> contactos) {
        this.contexto = c;
        this.contactos = contactos;
    }


    /**
     * Funcion para crear la interfaz del elemento adaptador. Inflara un layout como cuando inflabamos
     * un arrayAdapter normal, usando el XML que diseñamos para alojar los items
     * @param parent El padre desde donde sacaremos el contexto
     * @param viewType
     * @return un ViewHolder basado en la vista inflada
     */
    @NonNull
    @Override
    public AdaptadorContactosRV.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflamos y sacamos la vista, de la misma forma que con los ArrayAdapter
        LayoutInflater inflador = LayoutInflater.from(contexto);
        View vista = inflador.inflate(R.layout.adaptador_contactos, parent, false);

        // Devolvemos
        return new ViewHolder(contexto, vista);
    }


    /**
     * Establece la cominicacion con la clase creada, es decir, pasamos la onformacion del elemento
     * a pasar a la vista inflada del item ( lo que antes hacia en el getView)
     * @param holder el objeto de la clase que creamos para manejar los datos
     * @param position la posicion del listado que colocar
     */
    @Override
    public void onBindViewHolder(@NonNull AdaptadorContactosRV.ViewHolder holder, int position) {
         holder.asignarDatos(contactos.get(position));
    }

    // Para obtener el numero de contactos que tenemos
    @Override
    public int getItemCount() {
        return contactos.size();
    }


    /*********************************************************************************************
     * La clase creada para manejar los datos del adaptador
     */
    public class ViewHolder extends RecyclerView.ViewHolder {
        Context contexto;
        TextView tvNombre;
        TextView tvTelefono;

        // Constructor con el contexto y la vista
        public ViewHolder(Context c, View v){
            super(v);
            this.contexto =c;
            tvNombre = itemView.findViewById(R.id.tvContactoNombre);
            tvTelefono = itemView.findViewById(R.id.tvContactoTelefono);
        }


        /* Funcion creada para asignar los datos del contacto desde la clase padre en el metodo
        *  onBindViewHolder
        * */
        public void asignarDatos(DTOContactos dtoContactos) {
            tvNombre.setText(dtoContactos.getNombre());
            tvTelefono.setText(dtoContactos.getTelefono());
        }
    }
}
