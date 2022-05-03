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

        vidSalida = (VideoView)vista.findViewById(R.id.vidSalida);

        if(uriActual == null){
            // Cargara el video de recursos al iniciar
            String paquete = getActivity().getPackageName();
            uriActual = Uri.parse("android.resource://" + paquete + "/" + R.raw.mi_reel);
            posicion = 0;
        }

        vidSalida.requestFocus();
        this.setArchivo(uriActual, posicion);

        // Devolvemos la vista
        return vista;
    }


    /**
     * Funcion para pasar un recurso al video del fragment
     * @param uri el recurso a colocar
     * @param pos la posicion del video
     */
    @Override
    public void setArchivo(Uri uri, int pos) {
        uriActual = uri;
        posicion = pos;

        try{
            if(this.vidSalida.isPlaying())this.vidSalida.stopPlayback();

            if(uri.toString().contains("/")){
                this.vidSalida.setVideoURI(uri);
            }
            else{
                this.vidSalida.setVideoURI(Uri.parse("android.resource://" + getActivity().
                        getPackageName() + "/" + uri.toString()));
            }

            vidSalida.requestFocus();

            vidSalida.seekTo(posicion);
            vidSalida.start();
        }catch(Exception e){}
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

        Bundle b = new Bundle();
        b.putInt("posicion", posicion);
        this.onSaveInstanceState(b);
    }

    @Override
    public void onPause() {
        super.onPause();
        this.cerrarFragment();
    }
}