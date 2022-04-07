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



    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.

     * @return A new instance of fragment FragmentOrigen.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentOrigen newInstance(String param1, String param2) {
        FragmentOrigen fragment = new FragmentOrigen();
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
        View vista = inflater.inflate(R.layout.fragment_origen, container, false);

        this.btnGaleria = (Button)vista.findViewById(R.id.btnGaleria);
        this.btnWWW = (Button)vista.findViewById(R.id.btnWWW);
        this.btnSD = (Button)vista.findViewById(R.id.btnSD);

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
                ((InterfazFragments)getActivity()).abrirSD();
            }
        });

        return vista;
    }
}