package com.example.gestionpermisos001;

import android.app.ProgressDialog;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;

import android.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.MediaController;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.appcompat.widget.ViewUtils;

/**
 * Para el fragment central de video
 * Uso atributos estaticos, porque asi en esta clase la carga al rotar no necesitaria grabarse
 * Al ser estatica la posicion y la uri, aunque cree el objeto de nuevo, los valores se mantienen
 * y evito mas codigo y codigo. Y es otra forma de hacerlo, asi uso las dos
 */
public class FragVideo extends Fragment implements InterfazAccionFragments{
    private static VideoView vidSalida;
    private static Uri uriActual = null; // La hago static para que no se ponga a null cada vez
    private static int posicion = 0;


    // Constructor neceasario
    public FragVideo() {
        // Required empty public constructor
    }


     /**
     * Metodo que se llama al crear el layout
     * @param savedInstanceState el conjunto de datos que puede recibir
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    /**
     * Metodo que crea la vista del layout
     * @param inflater El layout inflador
     * @param container El contenedor
     * @param savedInstanceState El conjunto de datos que puede reccibit
     * @return La vista finalizada
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View vista =  inflater.inflate(R.layout.fragment_video, container, false);

        // Cargamos el videoview
        vidSalida = (VideoView)vista.findViewById(R.id.vidSalida);

        // Si no tenemos uri, acabamos de arrancar la aplicacion, asi que le asigno una, y la
        // posicion de inicio 0
        if(uriActual == null){
            // Cargara el video de recursos al iniciar
            String paquete = getActivity().getPackageName();
            uriActual = Uri.parse("android.resource://" + paquete + "/" + R.raw.mi_reel);
            posicion = 0;
        }

        this.setArchivo(uriActual, posicion);

        // Devolvemos la vista
        return vista;
    }


    /**
     * Funcion para pasar un recurso al video del fragment y ponerlo en marcha
     * @param uri el recurso a colocar
     * @param pos la posicion del video
     */
    @Override
    public void setArchivo(Uri uri, int pos) {
        // Ponemos los valores estaticos actualizados
        uriActual = uri;
        posicion = pos;

        // Intentamos arrancar el video segun el tipo de uri que tenemos
        try{
            // Primero, si esta funcionando, lo paro
            if(this.vidSalida.isPlaying())this.vidSalida.stopPlayback();

            // Ahora compruebo el origen de la uri, para saber como funcionar con ella
            if(uri.toString().contains("/")){
                this.vidSalida.setVideoURI(uri);
            }
            else{
                this.vidSalida.setVideoURI(Uri.parse("android.resource://" + getActivity().
                        getPackageName() + "/" + uri.toString()));
            }

            // pedimos el foco
            vidSalida.requestFocus();

            // Nos ponemos en posicion, y empezamos el video
            vidSalida.seekTo(posicion);
            vidSalida.start();
        }catch(Exception e){
            Log.d("Pruebas", "No se pudo cargar el video: " + e.getMessage());
        }
    }


    /**
     * FUncion para cerrar el fragment
     */
    @Override
    public void cerrarFragment() {
        // Por si queda enganchado en algun cambio
        if(vidSalida!= null && vidSalida.isPlaying()){
            posicion = vidSalida.getCurrentPosition();
            vidSalida.stopPlayback();
            vidSalida.resume();
        }
    }

    // Metodo para que al reiniciar, si el fragment esta activo, arranque en donde estaba antes.
    // Se usa al volver de la otra actividad
    @Override
    public void reiniciar() {
        this.setArchivo(uriActual, posicion);
    }

    // Metodo para cerar el fragment al invocar el onPause
    @Override
    public void onPause() {
        super.onPause();
        this.cerrarFragment();
    }
}