package com.example.gestionpermisos001;

import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

import android.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import com.bumptech.glide.Glide;

public class FragImagen extends Fragment implements InterfazAccionFragments {

    private ImageView imgSalida;
    private static Uri uriActual = null;

    public FragImagen() {
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
        View vista =  inflater.inflate(R.layout.fragment_frag_imagen, container, false);

        this.imgSalida = (ImageView)vista.findViewById(R.id.imgSalida);

        // En el inicio, carga la imagen desde recursos
        if(uriActual == null){
            uriActual = Uri.parse(String.valueOf(R.raw.warnerbrospresents));
        }
        this.setArchivo(uriActual,0);

        return vista;
    }

    // Aqui en las imagenes, no usamos el parametro de posicion
    @Override
    public void setArchivo(Uri uri, int posicion) {
        this.uriActual = uri;
        Bitmap bmp = null;

        try
        {
            if(uri.toString().contains("http")){
                Glide.with(getActivity().getBaseContext()).load(uri).into(this.imgSalida);
            }
            else if(!uri.toString().contains("/")){ // Si es un recurso, solo es un numero, sin ruta
                this.imgSalida.setImageResource(Integer.parseInt(uri.toString()));
                this.imgSalida.setScaleType(ImageView.ScaleType.FIT_CENTER);
            }
            else{
            this.imgSalida.setImageURI(uri);
            this.imgSalida.setScaleType(ImageView.ScaleType.CENTER_CROP);
        }
        }
        catch (Exception e) { }
    }

    @Override
    public void cerrarFragment() {
        // No necesita nada
    }

    @Override
    public void reiniciar() {
        this.setArchivo(uriActual, 0);
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
}