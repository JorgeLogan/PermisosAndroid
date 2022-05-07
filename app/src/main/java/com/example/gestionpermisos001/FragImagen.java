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

/**
 * Clase para el Fragmento central de las imagenes
 */
public class FragImagen extends Fragment implements InterfazAccionFragments {
    // Atributos
    private ImageView imgSalida;
    private static Uri uriActual = null;

    // Constructor vacio
    public FragImagen() {}


    // Metodo llamado en la creacion
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    // Metodo llamado al crear la vista
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

        // Segun el uri, trabaja de una forma u otra
        try
        {
            if(uri.toString().contains("http")){
                Glide.with(getActivity().getBaseContext()).load(uri).into(this.imgSalida);
            }
            else if(!uri.toString().contains("/")){ // Si es un recurso, solo es un numero, sin ruta
                this.imgSalida.setImageResource(Integer.parseInt(uri.toString()));
            }
            else{
            this.imgSalida.setImageURI(uri);
            }
            this.imgSalida.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
        }
        catch (Exception e) { }
    }


    // Funcion para cerrar el fragment. La pide la interfaz, pero aqui no la necesito
    @Override
    public void cerrarFragment() {
        // No necesita nada
    }


    // Ponemos al volver de los contactos, si esta este fragment seleccionado, la imagen que corresponde
    @Override
    public void reiniciar() {
        this.setArchivo(uriActual, 0);
    }


    // Para el control de los permisos.
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
}