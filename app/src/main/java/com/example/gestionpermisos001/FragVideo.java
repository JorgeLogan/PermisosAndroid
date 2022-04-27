package com.example.gestionpermisos001;

import android.app.ProgressDialog;
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
 * A simple {@link Fragment} subclass.
 * Use the {@link FragVideo#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragVideo extends Fragment implements InterfazAccionFragments{

    VideoView vidSalida;
//    MediaController mc = null;

    public FragVideo() {
        // Required empty public constructor
    }

    public static FragVideo newInstance(String param1, String param2) {
        FragVideo fragment = new FragVideo();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View vista =  inflater.inflate(R.layout.fragment_video, container, false);

        this.vidSalida = (VideoView)vista.findViewById(R.id.vidSalida);

        // Cargara el video de recursos al iniciar
        String paquete = getActivity().getPackageName();

        this.vidSalida.setVideoURI(Uri.parse("android.resource://" + paquete + "/" + R.raw.mi_reel));
        this.vidSalida.requestFocus();
        this.vidSalida.start();

        // Devolvemos la vista
        return vista;
    }

    @Override
    public void setArchivo(Uri uri) {
        try{
            Log.d("Pruebas", "Intento cargar el video de internet: " + uri.toString());
            if(this.vidSalida.isPlaying())this.vidSalida.stopPlayback();

            if(uri.toString().contains("/")){
                this.vidSalida.setVideoURI(uri);
            }
            else{
                this.vidSalida.setVideoURI(Uri.parse("android.resource://" + getActivity().getPackageName() + "/" + uri.toString()));

            }


            this.vidSalida.requestFocus();
            this.vidSalida.start();
        }catch(Exception e){
            Log.d("Pruebas", e.getMessage());
        }
    }

    @Override
    public void cerrarFragment() {
        Log.d("Pruebas", "Cerrando fragment de video");
    }
}