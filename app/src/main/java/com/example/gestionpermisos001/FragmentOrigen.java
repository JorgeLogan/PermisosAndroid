package com.example.gestionpermisos001;

import android.graphics.drawable.TransitionDrawable;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.transition.Transition;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.Button;

/**
 * Clase creada para el control del fragment de los botones de seleccion de archivos según
 * galeria, internet o recursos
 */
public class FragmentOrigen extends Fragment {
    // Atributos
    private Button btnGaleria;
    private Button btnWWW;
    private Button btnSD;
    private InterfazFragments interfazFragments;


    // Constructor vacio
    public FragmentOrigen() {
        // Required empty public constructor
    }


    // Constructor con la interfaz asociada
    public FragmentOrigen(InterfazFragments interfazFragments){
        this.interfazFragments = interfazFragments;
    }


    // Metodo llamado en la creacion de la vista
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }


    // Metodo llamado en la creacion de la vista que devuelve la vista propia
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View vista = inflater.inflate(R.layout.fragment_origen, container, false);

        // Cargamos los elementos
        this.btnGaleria = (Button)vista.findViewById(R.id.btnGaleria);
        this.btnWWW = (Button)vista.findViewById(R.id.btnWWW);
        this.btnSD = (Button)vista.findViewById(R.id.btnRecursos);

        // Damos funcionalidad a los botones, casteando la main activity a traves de la interfaz,
        // para poder acceder a los metodos desde una activity
        this.btnGaleria.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)  { ((InterfazFragments)getActivity()).abrirGaleria();  }
        });

        this.btnWWW.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((InterfazFragments)getActivity()).abrirWWW();
            }
        });

        this.btnSD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((InterfazFragments)getActivity()).abrirRecursos();
            }
        });

        // Devolvemos la vista
        return vista;
    }
}