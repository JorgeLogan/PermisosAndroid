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
 * A simple {@link Fragment} subclass.
 * Use the {@link FragSonido#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragSonido extends Fragment implements InterfazAccionFragments {

    ImageView imgSonido;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public FragSonido() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentSonido.
     */
    // TODO: Rename and change types and number of parameters
    public static FragSonido newInstance(String param1, String param2) {
        FragSonido fragment = new FragSonido();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View vista =  inflater.inflate(R.layout.fragment_sonido, container, false);
        this.imgSonido = (ImageView) vista.findViewById(R.id.imgSonido);
        return vista;
    }

    @Override
    public void setArchivo(Uri uri) {

        MediaPlayer mp = new MediaPlayer();
        try {
            mp.setDataSource(uri.getPath());
            mp.prepare();
            mp.start();
        } catch (IOException e) {
            Toast.makeText(getActivity().getBaseContext(), "No se pudo cargar " + e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }
}