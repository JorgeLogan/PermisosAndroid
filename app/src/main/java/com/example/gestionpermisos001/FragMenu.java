package com.example.gestionpermisos001;

import android.graphics.Color;
import android.media.Image;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragMenu#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragMenu extends Fragment {
    public FragMenu() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragMenu.
     */
    // TODO: Rename and change types and number of parameters
    public static FragMenu newInstance(String param1, String param2) {
        FragMenu fragment = new FragMenu();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
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