package com.example.gestionpermisos001;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


public class FragmentOrigen extends Fragment {

    private Button btnGaleria;
    private Button btnWWW;
    private Button btnSD;

    private InterfazFragments interfazFragments;

    public FragmentOrigen() {
        // Required empty public constructor
    }

    // Constructor con la interfaz
    public FragmentOrigen(InterfazFragments interfazFragments){
        this.interfazFragments = interfazFragments;
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
        View vista = inflater.inflate(R.layout.fragment_origen, container, false);

        this.btnGaleria = (Button)vista.findViewById(R.id.btnGaleria);
        this.btnWWW = (Button)vista.findViewById(R.id.btnWWW);
        this.btnSD = (Button)vista.findViewById(R.id.btnRecursos);

        this.btnGaleria.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((InterfazFragments)getActivity()).abrirGaleria();
            }
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

        return vista;
    }
}