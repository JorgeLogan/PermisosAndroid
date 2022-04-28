package com.example.gestionpermisos001;

import android.graphics.Color;
import android.media.Image;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;


public class FragMenu extends Fragment {
    public FragMenu() {
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
        View vista =  inflater.inflate(R.layout.fragment_frag_menu, container, false);

        ImageButton imgImagen = (ImageButton)vista.findViewById(R.id.itemCamara);
        ImageButton imgVideo = (ImageButton)vista.findViewById(R.id.itemWWW);
        ImageButton imgSonido = (ImageButton)vista.findViewById(R.id.itemSD);

        imgImagen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imgImagen.setBackground(getResources().getDrawable(R.drawable.forma_redondeada_encendida));
                imgSonido.setBackground(getResources().getDrawable(R.drawable.forma_redondeada_apagada));
                imgVideo.setBackground(getResources().getDrawable(R.drawable.forma_redondeada_apagada));
                ((InterfazFragments)getActivity()).seleccionarImagen();
            }
        });

        imgVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imgImagen.setBackground(getResources().getDrawable(R.drawable.forma_redondeada_apagada));
                imgSonido.setBackground(getResources().getDrawable(R.drawable.forma_redondeada_apagada));
                imgVideo.setBackground(getResources().getDrawable(R.drawable.forma_redondeada_encendida));
                ((InterfazFragments)getActivity()).seleccionaVideo();
            }
        });

        imgSonido.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imgImagen.setBackground(getResources().getDrawable(R.drawable.forma_redondeada_apagada));
                imgSonido.setBackground(getResources().getDrawable(R.drawable.forma_redondeada_encendida));
                imgVideo.setBackground(getResources().getDrawable(R.drawable.forma_redondeada_apagada));
                ((InterfazFragments)getActivity()).seleccionarSonido();
            }
        });

        return vista;
    }
}