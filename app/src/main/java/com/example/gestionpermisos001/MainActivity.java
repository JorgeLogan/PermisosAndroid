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
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.MediaActionSound;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.VideoView;

import java.net.ConnectException;

/**
 * Solo vamos a tener 1 actividad de momento, asi que de entrada, vamos a tener por defecto
 * el Fragment de imagen cargado.
 * Luego según los controles del mando, el fragment central se irá cambiando a video, sonido, o
 * vuelta a imagen segun el boton pulsado.
 */
public class MainActivity extends AppCompatActivity implements  InterfazFragments{

    private int eleccion = 0; // Sera 0 para Imagen, 1 para Video y 2 para Sonido

    // El contenedor de los fragmentos
    private FrameLayout contenedorFragmentos;

    /**
     *  Para almacenar los 3 fragments creo un listado, donde
     *  0 = imgaen
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








        // COmpruebo permisos
        String[] permisos = { Manifest.permission.READ_CONTACTS };








        this.cargarFragment();
    }



    /************************************************************************************
     * Implementaciones de la interfaz
     */
    @Override
    public void seleccionarImagen() {
        this.eleccion = 0;
        Log.d("Pruebas", "Abrir imagen. Eleccion actual: " + this.eleccion);
        Toast.makeText(getBaseContext(), "Eleccion: " + eleccion, Toast.LENGTH_SHORT).show();
        this.cargarFragment();
    }

    @Override
    public void seleccionaVideo() {
        this.eleccion = 1;
        Log.d("Pruebas", "Abrir Video. Eleccion actual: " + this.eleccion);
        Toast.makeText(getBaseContext(), "Eleccion: " + eleccion, Toast.LENGTH_SHORT).show();
        this.cargarFragment();
    }

    @Override
    public void seleccionarSonido() {
        this.eleccion = 2;
        Log.d("Pruebas", "Abrir Sonido. Eleccion actual: " + this.eleccion);
        Toast.makeText(getBaseContext(), "Eleccion: " + eleccion, Toast.LENGTH_SHORT).show();
        this.cargarFragment();
    }

    @Override
    public void abrirGaleria() {
        Log.d("Pruebas", "Abrir galeria");

        /**
         * Cuando se abre un dialogo de buscar archivos, el codigo de OK es RESULT_CODE = -1
         */
        switch(this.eleccion){
            case 0: // Imagen
                Intent intentImagen = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                //intentImagen.setType("image/");
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

    @Override
    public void abrirWWW() {
        Log.d("Pruebas", "Abrir internet");
        /**
         * Cuando se abre un dialogo de buscar archivos, el codigo de OK es RESULT_CODE = -1
         */
        switch(this.eleccion){
            case 0: // Imagen
                String ruta = "https://www.educaciontrespuntocero.com/wp-content/uploads/2019/02/girasoles-978x652.jpg.webp";
                ((InterfazAccionFragments)this.listadoFragmentos[this.eleccion]).setArchivo(Uri.parse(ruta));
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

    @Override
    public void abrirSD() {
        Log.d("Pruebas", "Abrir SD");
        /**
         * Cuando se abre un dialogo de buscar archivos, el codigo de OK es RESULT_CODE = -1
         */
        switch(this.eleccion){
            case 0: // Imagen
                Intent intentImagen = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                intentImagen.setType("image/jpg");
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
        FragmentManager manejadorFragments =  getFragmentManager();
        FragmentTransaction transaccion = manejadorFragments.beginTransaction();
        transaccion.replace(R.id.contenedorFragments, this.listadoFragmentos[this.eleccion]);
        transaccion.commit();
    }
}