package com.example.gestionpermisos001;

import android.net.Uri;
import android.os.Bundle;

import android.app.Fragment;
import android.media.MediaPlayer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.IOException;

/**
 * create an instance of this fragment.
 */
public class FragSonido extends Fragment implements InterfazAccionFragments {

    private ImageView imgSonido;
    private MediaPlayer mp;

    public FragSonido() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View vista =  inflater.inflate(R.layout.fragment_sonido, container, false);
        this.imgSonido = (ImageView) vista.findViewById(R.id.imgSonido);

        // Al principio carga un sonido desde recursos
        mp = MediaPlayer.create(getActivity().getBaseContext(), R.raw.musica);
        mp.start();

        // Devolvemos la vista
        return vista;
    }

    // Para abrir un URI y que empiece a funcionar
    @Override
    public void setArchivo(Uri uri) {

        mp = new MediaPlayer();
        try {
            mp.setDataSource(uri.getPath());
            mp.setLooping(true);
            mp.prepare();
            mp.start();
        } catch (IOException e) {
            Toast.makeText(getActivity().getBaseContext(), "No se pudo cargar " + e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    // Para cerrar y liberar memoria
    @Override
    public void cerrarFragment() {
        if(mp!= null){
            Toast.makeText(getActivity().getBaseContext(), "Cerrando fragment de sonido", Toast.LENGTH_LONG).show();
            mp.release();
        }else{
            Toast.makeText(getActivity().getBaseContext(), "No se puede cerrar MediaPlayer de sonido", Toast.LENGTH_LONG).show();
        }
    }
}