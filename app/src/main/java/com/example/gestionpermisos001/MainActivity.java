package com.example.gestionpermisos001;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
//import androidx.fragment.app.Fragment;

import android.app.Fragment;

import android.Manifest;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.PermissionInfo;
import android.media.MediaActionSound;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.VideoView;

import com.bumptech.glide.Glide;

import java.io.File;
import java.net.ConnectException;

import static android.Manifest.permission.READ_EXTERNAL_STORAGE;

/**
 * Solo vamos a tener 1 actividad de momento, asi que de entrada, vamos a tener por defecto
 * el Fragment de imagen cargado.
 * Luego según los controles del mando, el fragment central se irá cambiando a video, sonido, o
 * vuelta a imagen segun el boton pulsado.
 * Debajo de
 */
public class MainActivity extends AppCompatActivity implements  InterfazFragments{

    final int PERMISO_GALERIA = 4;
    final int PERMISO_CAMINO = 1;
    final int PERMISO_LECTURA = 13;
    final int PERMISO_CAMARA = 11;

    final String[] PERMISOS = {Manifest.permission.READ_EXTERNAL_STORAGE,  Manifest.permission.INTERNET, Manifest.permission.READ_CONTACTS};

    private int eleccion = 0; // Sera 0 para Imagen, 1 para Video y 2 para Sonido

    // El contenedor de los fragmentos
    private FrameLayout contenedorFragmentos;

    /**
     *  Para almacenar los 3 fragments creo un listado, donde
     *  0 = imagen
     *  1 = video
     *  2 = sonido
     */
    private Fragment[] listadoFragmentos = new Fragment[3];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Cargo el contenedor de los fragmentos variables
        this.contenedorFragmentos = (FrameLayout)findViewById(R.id.contenedorFragments);

        // Ahora necesito pasar los elementos del listado de fragmentos
        // IMPORTANTE: HAY QUE CAMBIAR EL IMPORT QUE HACE ANDROID STUDIO SOBRE FRAGMENTS, YA QUE
        // EL QUE NECESITA ES import android.app.Fragment;
        this.listadoFragmentos[0] = new FragImagen();
        this.listadoFragmentos[1] = new FragVideo();
        this.listadoFragmentos[2] = new FragSonido();

        // Cargamos el fragment inicial, de cargar imagenes
        this.cargarFragment();
    }

    /***************************************************************************************
     *   Comprobar permisos runtime
     * *************************************************************************************
     */

    public void pedirPermisosGaleria(){
        if(ActivityCompat.checkSelfPermission(MainActivity.this, READ_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(MainActivity.this, this.PERMISOS, this.PERMISO_GALERIA );
        }
    }

    public void pedirPermisosSD(){
        if(ActivityCompat.checkSelfPermission(MainActivity.this, READ_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(MainActivity.this, PERMISOS, this.PERMISO_CAMINO);
        }
    }

    /*********************************************************************************************
     * Implementaciones de la interfaz
     * ********************************************************************************************
     */
    /**
     * Funcion para cargar el fragment central de elegir imagenes
     */
    @Override
    public void seleccionarImagen() {
        if(this.eleccion == 0) return; // Si ya estamos en la seleccion, no hacemos nada

        // Primero cerramos el fragment actual
        ((InterfazAccionFragments)this.listadoFragmentos[this.eleccion]).cerrarFragment();

        // Ya podemos preparar y cargar el nuevo fragment
        this.eleccion = 0;
        Log.d("Pruebas", "Abrir imagen. Eleccion actual: " + this.eleccion);
        Toast.makeText(getBaseContext(), "Eleccion: " + eleccion, Toast.LENGTH_SHORT).show();
        this.cargarFragment();
    }

    /**
     * Funcion para cargar el fragment central de video
     */
    @Override
    public void seleccionaVideo() {
        if(this.eleccion == 1) return; // Si ya estamos en la seleccion, no hacemos nada

        // Primero cerramos el fragment actual
        ((InterfazAccionFragments)this.listadoFragmentos[this.eleccion]).cerrarFragment();

        // Ya podemos preparar y cargar el nuevo fragment
        this.eleccion = 1;
        Log.d("Pruebas", "Abrir Video. Eleccion actual: " + this.eleccion);
        Toast.makeText(getBaseContext(), "Eleccion: " + eleccion, Toast.LENGTH_SHORT).show();
        this.cargarFragment();
    }

    /**
     * Funcion para cargar el fragment central de sonido
     */
    @Override
    public void seleccionarSonido() {
        if(this.eleccion == 2) return; // Si ya estamos en la seleccion, no hacemos nada

        // Primero cerramos el fragment actual
        ((InterfazAccionFragments)this.listadoFragmentos[this.eleccion]).cerrarFragment();

        // Ya podemos preparar y cargar el nuevo fragment
        this.eleccion = 2;
        Log.d("Pruebas", "Abrir Sonido. Eleccion actual: " + this.eleccion);
        Toast.makeText(getBaseContext(), "Eleccion: " + eleccion, Toast.LENGTH_SHORT).show();
        this.cargarFragment();
    }

    /*****************************************************************************************
     *  Funciones de seleccion de origen de datos
     * ***************************************************************************************
     */
    /**
     * Funcion para seleccionar desde la galeria
     */
    @Override
    public void abrirGaleria() {
        Log.d("Pruebas", "Abrir galeria");

        // Por si es necesario, convocamos la funcion de pedir permisos runtime
        // (si no lo necesita no los pedira)
        this.pedirPermisosGaleria();

        /**
         * Cuando se abre un dialogo de buscar archivos, el codigo de OK es RESULT_CODE = -1
         */
        switch(this.eleccion){
            case 0: // Imagen desde Almacenamiento interno
                Intent intentImagen = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
                intentImagen.setType("image/");
                startActivityForResult(intentImagen.createChooser(intentImagen, "Selecciona imagen"), 10);
                break;
            case 1: // Video
                Intent intentVideo = new Intent(Intent.ACTION_PICK);
                intentVideo.setType("video/");
                startActivityForResult(intentVideo.createChooser(intentVideo, "Selecciona video"), 10);
                break;
            case 2: // Sonido
                Intent intentSonido = new Intent(Intent.ACTION_PICK, MediaStore.Audio.Media.EXTERNAL_CONTENT_URI);
                intentSonido.setType("audio/mp3");
                startActivityForResult(intentSonido.createChooser(intentSonido, "Selecciona audio"), 10);
                break;
        }
    }

    /**
     * Funcion para seleccionar desde internet
     */
    @Override
    public void abrirWWW() {
        /**
         * Cuando se abre un dialogo de buscar archivos, el codigo de OK es RESULT_CODE = -1
         */

        String ruta ="";

        switch(this.eleccion){
            case 0: // Imagen
                Log.d("Pruebas", "Abrir imagen de internet");
                ruta = "https://imagenpng.com/wp-content/uploads/2015/09/imagenes-png.png";

                ((InterfazAccionFragments)this.listadoFragmentos[this.eleccion]).setArchivo(Uri.parse(ruta));

                break;
            case 1: // Video
                Log.d("Pruebas", "Abrir video de internet");
                ruta = "https://github.com/JorgeLogan/MisCosas/blob/main/LadyPirataIntroReel.mp4";
                //ruta = "http://techslides.com/demos/sample-videos/small.mp4";
                ((InterfazAccionFragments)this.listadoFragmentos[this.eleccion]).setArchivo(Uri.parse(ruta));
                break;
            case 2: // Sonido
                String rutaSonido = "https://wynk.in/u/1wzgcDky5";
                Uri uri = Uri.parse(rutaSonido);
                ((InterfazAccionFragments)this.listadoFragmentos[this.eleccion]).setArchivo(Uri.parse(rutaSonido));

                break;
        }
    }

    @Override
    public void abrirSD() {
        Log.d("Pruebas", "Abrir SD");
        /**
         * Cuando se abre un dialogo de buscar archivos, el codigo de OK es RESULT_CODE = -1
         */
        this.pedirPermisosSD();

        switch(this.eleccion){
            case 0: // Imagen desde sd
                Intent intentImagen = null;

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                    intentImagen = new Intent(Intent.ACTION_PICK, MediaStore.Audio.Media.EXTERNAL_CONTENT_URI);
                }else{
                    intentImagen = new Intent(Intent.ACTION_GET_CONTENT);
                }

                intentImagen.setType("image/*");
                startActivityForResult(intentImagen.createChooser(intentImagen, "Selecciona imagen"), 10);
                break;
            case 1: // Video desde la galeria
                Intent intentVideo = new Intent(Intent.ACTION_PICK);
                intentVideo.setType("video/");
                startActivityForResult(intentVideo.createChooser(intentVideo, "Selecciona video"), 10);
                break;
            case 2: // Sonido desde la galeria
                Intent intentSonido = new Intent(Intent.ACTION_PICK, MediaStore.Audio.Media.EXTERNAL_CONTENT_URI);
                intentSonido.setType("audio/mp3");
                startActivityForResult(intentSonido.createChooser(intentSonido, "Selecciona audio"), 10);
                break;
        }
    }

    /********************************************************************************************
     * Para el resultado de la actividad que abrimos para cargar los archivos
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK){
            Toast.makeText(this, "Codigo OK, salida " + data.toString(), Toast.LENGTH_SHORT)
                    .show();
            Uri path = data.getData();
            ((InterfazAccionFragments)this.listadoFragmentos[this.eleccion]).setArchivo(path);
        }else{
            Toast.makeText(this, "No se pudo realizar. Cod Requerido " + requestCode + " Codigo recibido " + resultCode, Toast.LENGTH_SHORT)
                    .show();
        }
    }

    /*
    Para cargar el fragment de Imagenes
     */
    private void cargarFragment(){
        // Ahora ya preparamos para cargar el nuevo fragment
        FragmentManager manejadorFragments =  getFragmentManager();
        FragmentTransaction transaccion = manejadorFragments.beginTransaction();
        transaccion.replace(R.id.contenedorFragments, this.listadoFragmentos[this.eleccion]);
        transaccion.commit();
    }
}