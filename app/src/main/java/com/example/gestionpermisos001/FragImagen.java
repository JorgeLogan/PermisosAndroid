package com.example.gestionpermisos001;

import android.media.Image;
import android.net.Uri;
import android.os.Bundle;

import android.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;


public class FragImagen extends Fragment implements InterfazAccionFragments {

    private ImageView imgSalida;

    public FragImagen() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static FragImagen newInstance(String param1, String param2) {
        FragImagen fragment = new FragImagen();
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
        View vista =  inflater.inflate(R.layout.fragment_frag_imagen, container, false);

        this.imgSalida = (ImageView)vista.findViewById(R.id.imgSalida);
        return vista;
    }


    @Override
    public void setArchivo(Uri uri) {
        this.imgSalida.setImageURI(uri);
    }
}