package com.example.gestionpermisos001;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import android.app.AlertDialog;
import android.app.Fragment;
import android.Manifest;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.FrameLayout;
import java.io.File;
import static android.Manifest.permission.READ_EXTERNAL_STORAGE;

/**
 * Para trabajar con imagen, video y sonido, usaremos la Main Activity y un varios fragments
 * En un inicio tendremos seleccionado el modo imagen.
 * Luego según los controles del mando, el fragment central se irá cambiando a video, sonido, o
 * vuelta a imagen segun el boton pulsado.
 *
 * Tambien tiene un menu, donde podremos pasar a la activity de contactos, ver un dialogo simple
 * de acerca de... y el boton de salir
 */
public class MainActivity extends AppCompatActivity implements  InterfazFragments{

    final int PERMISO_GALERIA = 1;// 4
    final int PERMISO_CAMINO = 4;

    final String[] PERMISOS = {Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.INTERNET, Manifest.permission.READ_CONTACTS};

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
            ActivityCompat.requestPermissions(MainActivity.this, this.PERMISOS, this.PERMISO_GALERIA ); // Es el 1
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
    /**********************************************************************************************
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
        this.cargarFragment();
    }

    /**********************************************************************************************
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
        this.cargarFragment();
    }

    /**********************************************************************************************
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
        this.cargarFragment();
    }

    /*****************************************************************************************
     *  Funciones de seleccion de origen de datos
     * ***************************************************************************************
     */
    /**********************************************************************************************
     * Funcion para seleccionar elementos desde la galeria interna
     */
    @Override
    public void abrirGaleria() {
        Log.d("Pruebas", "Abrir galeria");

        // Por si es necesario, convocamos la funcion de pedir permisos runtime
        // (si no lo necesita no los pedira)
        //this.pedirPermisosGaleria();

        /**
         * Cuando se abre un dialogo de buscar archivos, el codigo de OK es RESULT_CODE = -1
         * Ahora segun el valor de eleccion, sabremos si tenemos que abrir una imagen, un video o un sonido
         * Como lo buscara del almacenamiento interno, usaremos un Intent para buscar
         */
        switch(this.eleccion){
            case 0: // Imagen desde Almacenamiento interno
                Intent intentImagen = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.INTERNAL_CONTENT_URI);
                intentImagen.setType("image/");
                startActivityForResult(intentImagen.createChooser(intentImagen, "Selecciona imagen"), 10);
                break;
            case 1: // Video desde almacenamiento interno
                Intent intentVideo = new Intent(Intent.ACTION_PICK, MediaStore.Video.Media.INTERNAL_CONTENT_URI);
                intentVideo.setType("video/mp4");
                startActivityForResult(intentVideo.createChooser(intentVideo, "Selecciona video"), 10);
                break;
            case 2: // Sonido desde el almacenamiento interno
                Intent intentSonido = new Intent(Intent.ACTION_PICK, MediaStore.Audio.Media.INTERNAL_CONTENT_URI);
                intentSonido.setType("audio/mp3");
                startActivityForResult(intentSonido.createChooser(intentSonido, "Selecciona audio"), 10);
                break;
        }
    }

    /**********************************************************************************************
     * Funcion para seleccionar desde internet
     */
    @Override
    public void abrirWWW() {
        /**
         * Cuando se abre un dialogo de buscar archivos, el codigo de OK es RESULT_CODE = -1
         */

        String ruta ="";

        switch(this.eleccion){
            case 0: // Imagen desde Internet
                Log.d("Pruebas", "Abrir imagen de internet");
                ruta = "https://imagenpng.com/wp-content/uploads/2015/09/imagenes-png.png";

                ((InterfazAccionFragments)this.listadoFragmentos[this.eleccion]).setArchivo(Uri.parse(ruta));

                break;
            case 1: // Video desde Internet
                this.cargarFragment();
                Log.d("Pruebas", "Abrir video de internet");
                String rutaVideo = "https://player.vimeo.com/external/498228565.hd.mp4?s=a32a9a677a152be823a5ca87d3765c208e36d8bc&profile_id=174";
                //String rutaVideo = "https://github.com/JorgeLogan/MisCosas/blob/main/LadyPirataIntroReel.mp4";
                try{
                    ((InterfazAccionFragments)this.listadoFragmentos[this.eleccion]).setArchivo(Uri.parse(rutaVideo));

                }catch(Exception e){
                    Log.d("Pruebas", "Excepcion en intento de abrir video: " + e.getMessage());
                }
                break;
            case 2: // Sonido desde internet
                String rutaSonido = "https://www.freemusicprojects.com/mp3/Lluvia-1.mp3";
                Uri uri = Uri.parse(rutaSonido);
                ((InterfazAccionFragments)this.listadoFragmentos[this.eleccion]).setArchivo(uri);
                break;
        }
    }

    /**********************************************************************************************
     * Funcion para seleccionar la tarjeta SD
     */
    @Override
    public void abrirSD() {
        Log.d("Pruebas", "Abrir SD");
        /**
         * Cuando se abre un dialogo de buscar archivos, el codigo de OK es RESULT_CODE = -1
         */
        this.pedirPermisosSD();

        switch(this.eleccion){
            case 0: // Imagen desde la SD
                Log.d("Pruebas", "Intento abrir imagen desde la galeria");

                Intent intentImagen = null;
                File ruta = Environment.getExternalStorageDirectory();
                intentImagen = new Intent(Intent.ACTION_PICK).setData(Uri.fromFile(ruta));
                intentImagen.setType("image/*");
                startActivityForResult(intentImagen.createChooser(intentImagen, "Selecciona imagen"), 10);
                break;
            case 1: // Video desde SD
                Intent intentVideo = new Intent(Intent.ACTION_PICK);
                intentVideo.setType("video/");
                startActivityForResult(intentVideo.createChooser(intentVideo, "Selecciona video"), 10);
                break;
            case 2: // Sonido desde SD
                Intent intentSonido = new Intent(Intent.ACTION_PICK);
                intentSonido.setData(Uri.fromFile(Environment.getExternalStorageDirectory()));
                intentSonido.setType("audio/mp3");
                startActivityForResult(intentSonido.createChooser(intentSonido, "Selecciona audio"), 10);
                break;
        }
    }

    /********************************************************************************************
     * Para el resultado del intent que abrimos para cargar los archivos
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK){
            Uri path = data.getData();
            ((InterfazAccionFragments)this.listadoFragmentos[this.eleccion]).setArchivo(path);
        }
    }

    /**********************************************************************************************
    Para cargar el fragment central segun la eleccion
     */
    private void cargarFragment(){
        // Ahora ya preparamos para cargar el nuevo fragment
        FragmentManager manejadorFragments =  getFragmentManager();
        FragmentTransaction transaccion = manejadorFragments.beginTransaction();
        transaccion.replace(R.id.contenedorFragments, this.listadoFragmentos[this.eleccion]);
        transaccion.commit();
    }

    /**********************************************************************************************
     * Menu de llamadas a contactos
     *
     * ********************************************************************************************
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater infladorMenus = getMenuInflater();
        infladorMenus.inflate(R.menu.menu_contactos, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){
            case R.id.itemContactos:
                this.listaContactos();
                break;
            case R.id.itemAcercaDe:
                this.acercaDe();
                break;
            case R.id.itemSalir:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * Para la lista de contactos
     */
    private void listaContactos(){
        Intent intentContactos = new Intent(Intent.ACTION_DIAL);
        startActivity(intentContactos);
    }

    /**
     * Para un alert dialog simple con un acerca de...
     */
    private void acercaDe(){
        AlertDialog.Builder dialogo =  new AlertDialog.Builder(this);
        String mensaje = "Creado por \n\tJorge Alvarez Ceñal";
        dialogo.setMessage(mensaje);
        dialogo.setTitle(getResources().getString(R.string.acerca_de));
        dialogo.setPositiveButton(R.string.genial, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) { }
        });
        dialogo.show();
    }
}