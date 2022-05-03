package com.example.gestionpermisos001;

import android.graphics.Color;
import android.media.Image;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;


public class FragMenu extends Fragment {
    private int opcion = 0;

    private ImageButton imgImagen;
    private ImageButton imgVideo;
    private ImageButton imgSonido;


    // Constructor publico requerido
    public FragMenu() {}


    // Para poder controlar que opcion tiene guardada cuando cambie la rotacion
    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("opcion", opcion);
    }

    // Para cuando se crea la vista
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(savedInstanceState!= null && savedInstanceState.containsKey("opcion")){
            this.opcion = savedInstanceState.getInt("opcion");
        }
    }

    // Para cuando se crea la vista
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View vista =  inflater.inflate(R.layout.fragment_frag_menu, container, false);

        imgImagen = (ImageButton)vista.findViewById(R.id.itemCamara);
        imgVideo = (ImageButton)vista.findViewById(R.id.itemWWW);
        imgSonido = (ImageButton)vista.findViewById(R.id.itemSD);

        // Para colocar el boton activo
        controlBotones();

        imgImagen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                opcion = 0;
                controlBotones();
                ((InterfazFragments)getActivity()).seleccionarImagen();
            }
        });

        imgVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                opcion = 1;
                controlBotones();
                ((InterfazFragments)getActivity()).seleccionaVideo();
            }
        });

        imgSonido.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                opcion = 2;
                controlBotones();
                ((InterfazFragments)getActivity()).seleccionarSonido();
            }
        });

        return vista;
    }

    /**
     * Para ajustar el encendido de los botones segun la opcion actual
     */
    private void controlBotones(){
        imgImagen.setBackground(getResources().getDrawable(opcion == 0?
                R.drawable.forma_redondeada_encendida : R.drawable.forma_redondeada_apagada));
        imgVideo.setBackground(getResources().getDrawable(opcion == 1?
                R.drawable.forma_redondeada_encendida : R.drawable.forma_redondeada_apagada));
        imgSonido.setBackground(getResources().getDrawable(opcion == 2?
                R.drawable.forma_redondeada_encendida : R.drawable.forma_redondeada_apagada));
    }
}