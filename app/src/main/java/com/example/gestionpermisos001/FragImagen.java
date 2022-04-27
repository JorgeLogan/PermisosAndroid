package com.example.gestionpermisos001;

import android.Manifest;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.icu.text.UnicodeSetSpanner;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;

import android.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import com.bumptech.glide.Glide;

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

        // En el inicio, carga la imagen desde recursos
        this.imgSalida.setImageBitmap(BitmapFactory.decodeResource(getResources(),R.raw.warnerbrospresents));

        return vista;
    }


    @Override
    public void setArchivo(Uri uri) {
        Log.d("Pruebas", "Intento cargar la imagen  " + uri.toString());
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
        catch (Exception e) {
            Log.d("Pruebas", "Error sin descripcion");
        }
    }

    @Override
    public void cerrarFragment() {
        // No necesita nada
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        Log.d("Pruebas", "Busqueda de permisos override onRequestPermissionsResult");
    }
}