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
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;

import java.io.File;
import static android.Manifest.permission.READ_EXTERNAL_STORAGE;

/**
 * Solo vamos a tener 1 actividad para el control multimedia, y vamos a tener por defecto
 * el Fragment de imagen cargado.
 * Luego según los controles del mando, el fragment central se irá cambiando a video, sonido, o
 * vuelta a imagen segun el boton pulsado.
 * Tambien tendremos un menu para acceder a la actividad de contactos de telefono, y un acerca de..
 * sencillo, usando un dialogo simple para usar un poco de resumen de contenido. (usaremos uno
 * personalizado para acceder a los elementos multimedia desde internet y los recursos)
 *
 */
public class MainActivity extends AppCompatActivity implements  InterfazFragments{
    // Para poder salir desde otra activity
    public static MainActivity instancia = null;

    // Para el control de los permisos
    private final int PERMISO_GALERIA = 4;
    private final String[] PERMISOS = {Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.INTERNET, Manifest.permission.READ_CONTACTS};

    // Para contolar la eleccion actual al reiniciar la vista
    // Sera 0 para Imagen, 1 para Video y 2 para Sonido, Estatica para conservar la eleccion
    private static int eleccion = 0;
    private String ruta ="";

    // El contenedor de los fragmentos centrales
    private FrameLayout contenedorFragmentos;

    /**
     *  Para almacenar los 3 fragments creo un listado, donde
     *  0 = imagen
     *  1 = video
     *  2 = sonido
     */
    private Fragment[] listadoFragmentos = new Fragment[3];


    // Metodo llamado al crear la vista
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Preparamos la instancia
        if(instancia == null) instancia = this;

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
        if(ActivityCompat.checkSelfPermission(MainActivity.this, READ_EXTERNAL_STORAGE)!=
                PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(MainActivity.this, this.PERMISOS, this.PERMISO_GALERIA );
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
         this.pedirPermisosGaleria();

        /**
         * Cuando se abre un dialogo de buscar archivos, el codigo de OK es RESULT_CODE = -1
         * Ahora segun el valor de eleccion, sabremos si tenemos que abrir una imagen, un video o un sonido
         * Como lo buscara del almacenamiento interno, usaremos un Intent para buscar
         */
        switch(this.eleccion){
            case 0: // Imagen desde Galeria
                Intent intentImagen = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                intentImagen.setType("image/");
                startActivityForResult(intentImagen.createChooser(intentImagen, "Selecciona imagen"), 10);
                break;
            case 1: // Video desde Galeria
                Intent intentVideo = new Intent(Intent.ACTION_PICK, MediaStore.Video.Media.INTERNAL_CONTENT_URI);
                intentVideo.setType("video/mp4");
                startActivityForResult(intentVideo.createChooser(intentVideo, "Selecciona video"), 10);
                break;
            case 2: // Sonido desde Galeria
                Intent intentSonido = new Intent(Intent.ACTION_PICK, MediaStore.Audio.Media.INTERNAL_CONTENT_URI);
                intentSonido.setType("audio/mp3");
                startActivityForResult(intentSonido.createChooser(intentSonido, "Selecciona audio"), 10);
                break;
        }
    }

    /**********************************************************************************************
     * Funcion para seleccionar desde internet.
     * En el caso del video, puse 2 veces el mismo porque no logre encontrar otro enlace a un video
     * que funcionara. Te lo comente por Teams, pero como tenias lio, y yo tambien estoy saturado,
     * lo dejo doble. Al final, es cambiar la url por una de internet que sea valida
     */
    @Override
    public void abrirWWW() {
        /**
         * Cuando se abre un dialogo de buscar archivos, el codigo de OK es RESULT_CODE = -1
         */

        switch(this.eleccion){
            case 0: // Imagen desde Internet
                Log.d("Pruebas", "Abrir imagen de internet");

                // Cargamos unos elementos para pasarlos a un listview personalizado de seleccion
                DTOElementoMultimedia ei1 = new DTOElementoMultimedia("Hommer",
                        DTOElementoMultimedia.tipoElemento.Imagen,
                        "https://imagenpng.com/wp-content/uploads/2015/09/imagenes-png.png");
                DTOElementoMultimedia ei2 = new DTOElementoMultimedia("Marge",
                        DTOElementoMultimedia.tipoElemento.Imagen,
                        "https://cdn.icon-icons.com/icons2/21/PNG/256/toons_marge_simpson_margesimpson_2379.png");

                DTOElementoMultimedia listaElementosI[] = {ei1, ei2};

                this.cargarListadoElementos(listaElementosI);
                break;
            case 1: // Video desde Internet
                this.cargarFragment();

                // Cargamos unos elementos para pasarlos a un listview personalizado de seleccion
                DTOElementoMultimedia ev1 = new DTOElementoMultimedia("Rama",
                        DTOElementoMultimedia.tipoElemento.Video,
                        "https://player.vimeo.com/external/498228565.hd.mp4?s=a32a9a677a152be823a5ca87d3765c208e36d8bc&profile_id=174");
                DTOElementoMultimedia ev2 = new DTOElementoMultimedia("Misma rama",
                        DTOElementoMultimedia.tipoElemento.Video,
                        "https://github.com/JorgeLogan/PermisosAndroid/blob/develop/abuelo.mp4");

                DTOElementoMultimedia listaElementosV[] = {ev1, ev2};
                this.cargarListadoElementos(listaElementosV);
                break;
            case 2: // Sonido desde internet

                // Cargamos unos elementos para pasarlos a un listview personalizado de seleccion
                DTOElementoMultimedia es1 = new DTOElementoMultimedia("Lluvia relajante",
                        DTOElementoMultimedia.tipoElemento.Sonido,
                        "https://www.freemusicprojects.com/mp3/Lluvia-1.mp3");
                DTOElementoMultimedia es2 = new DTOElementoMultimedia("Piano blues",
                        DTOElementoMultimedia.tipoElemento.Sonido,
                        "https://www.freemusicprojects.com/mp3/the-blues.mp3");

                DTOElementoMultimedia listaElementosS[] = {es1, es2};
                this.cargarListadoElementos(listaElementosS);
                break;
        }
    }

    /**********************************************************************************************
     * Funcion para seleccionar la tarjeta SD
     */
    @Override
    public void abrirRecursos() {
        Log.d("Pruebas", "Abrir SD");

        switch(this.eleccion){
            case 0: // Imagen desde recursos
                Log.d("Pruebas", "Intento abrir imagen desde la galeria");

                // Cargamos unos elementos para pasarlos a un listview personalizado de seleccion
                DTOElementoMultimedia ei1 = new DTOElementoMultimedia("Egroj",
                        DTOElementoMultimedia.tipoElemento.Imagen,
                        String.valueOf(R.raw.egroj));
                DTOElementoMultimedia ei2 = new DTOElementoMultimedia("Lady Pirata",
                        DTOElementoMultimedia.tipoElemento.Imagen,
                        String.valueOf(R.raw.ladypirata_juego));
                DTOElementoMultimedia ei3 = new DTOElementoMultimedia("Malos de Egroj",
                        DTOElementoMultimedia.tipoElemento.Imagen,
                        String.valueOf(R.raw.egroj_magos));
                DTOElementoMultimedia ei4 = new DTOElementoMultimedia("escena de Egroj",
                        DTOElementoMultimedia.tipoElemento.Imagen,
                        String.valueOf(R.raw.egroj_fuego));
                DTOElementoMultimedia ei5 = new DTOElementoMultimedia("Inicio Lady Pirada",
                        DTOElementoMultimedia.tipoElemento.Imagen,
                        String.valueOf(R.raw.pirata_inicio));
                DTOElementoMultimedia ei6 = new DTOElementoMultimedia("Pausa Egroj",
                        DTOElementoMultimedia.tipoElemento.Imagen,
                        String.valueOf(R.raw.egroj_juego));

                DTOElementoMultimedia[] elementosI = {ei1, ei2, ei3, ei4, ei5, ei6};
                this.cargarListadoElementos(elementosI);
                break;
            case 1: // Video desde recursos
                // Cargamos unos elementos para pasarlos a un listview personalizado de seleccion
                DTOElementoMultimedia ev1 = new DTOElementoMultimedia("Reel",
                        DTOElementoMultimedia.tipoElemento.Video,
                        String.valueOf(R.raw.mi_reel));
                DTOElementoMultimedia ev2 = new DTOElementoMultimedia("Lady Pirata",
                        DTOElementoMultimedia.tipoElemento.Video,
                        String.valueOf(R.raw.ladypirata));
                DTOElementoMultimedia ev3 = new DTOElementoMultimedia("Egroj",
                        DTOElementoMultimedia.tipoElemento.Video,
                        String.valueOf(R.raw.egroj_intro));

                DTOElementoMultimedia[] elementosV = {ev1, ev2, ev3};
                this.cargarListadoElementos(elementosV);
                break;
            case 2: // Sonido desde recursos
                // Cargamos unos elementos para pasarlos a un listview personalizado de seleccion
                DTOElementoMultimedia es1 = new DTOElementoMultimedia("Musica 1",
                        DTOElementoMultimedia.tipoElemento.Sonido,
                        String.valueOf(R.raw.musica));
                DTOElementoMultimedia es2 = new DTOElementoMultimedia("Sonido retro juegos",
                        DTOElementoMultimedia.tipoElemento.Sonido,
                        String.valueOf(R.raw.retro));

                DTOElementoMultimedia[] elementosS = {es1, es2};
                this.cargarListadoElementos(elementosS);
                break;
        }
    }

    /********************************************************************************************
     * Para el resultado del intent que abrimos para cargar los archivos
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d("Pruebas", "Resultado -> " + requestCode + "  " + resultCode);
        if(resultCode == RESULT_OK && requestCode!= 100){ // Si recibimos una uri de un dialogo de seleccion
            Uri path = data.getData();
            ((InterfazAccionFragments)this.listadoFragmentos[this.eleccion]).setArchivo(path,0);
        }
        else if(resultCode == RESULT_OK && requestCode == 100){ // Es decir, volvemos de la actividad de contactos
            ((InterfazAccionFragments)this.listadoFragmentos[eleccion]).reiniciar();
        }
    }

    /**********************************************************************************************
    Para cargar el fragment central segun la eleccio: imagen, video, sonido
     */
    private void cargarFragment(){
        // Ahora ya preparamos para cargar el nuevo fragment
        FragmentManager manejadorFragments =  getFragmentManager();
        FragmentTransaction transaccion = manejadorFragments.beginTransaction();
        transaccion.replace(R.id.contenedorFragments, this.listadoFragmentos[this.eleccion]);
        transaccion.commit();
    }


    /**********************************************************************************************
     * Menu superior, donde podremos acceder a la activity de contactos, el acerca de..., o salir
     * de la aplicacion
     * ********************************************************************************************
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater infladorMenus = getMenuInflater();
        infladorMenus.inflate(R.menu.menu_contactos, menu);
        return super.onCreateOptionsMenu(menu);
    }

    /**
     * Para el control del item seleccionado del menu superior
     * @param item el item seleccionado
     * @return devuelvo el mismo valor que devolveria el padre
     */
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
                salir();
                break;
        }
        return super.onOptionsItemSelected(item);
    }


    // Para salir desde cualquier actividad
    public void salir(){
        finish();
    }


    /**********************************************************************************************
     * Para la lista de contactos creo un intent y llamo a su actividad
     * Queremos que devuelva resultado, ya que al volver necesitamos que indique que el reproductor
     * de video o sonido, arranquen donde estaban, o de lo contrario, se quedara detenido
     */
    private void listaContactos(){
        try{
            Intent intent = new Intent(this, ActividadContactos.class);
            startActivityForResult(intent, 100);
        }catch(Exception e){
            Log.d("Pruebas", "-->" + e.getMessage());
        }
    }


    /**********************************************************************************************
     * Para un alert dialog simple con un acerca de...
     * Lo he creado simple para poder usar la activity de repaso de contenido visto, y layouts
     * personalizados los tenemos para la busqueda de archivos (y contactos en la actividad de
     * contctos con un RecyclerView)
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


    /**********************************************************************************************
     * Para poder hacer un listview personalizado y seleccionar una imagen de recursos o intenet
     * @param elementos los elementos DTOELementos
     */
    private void cargarListadoElementos(DTOElementoMultimedia[] elementos){
        // Preparo un adaptador para el selector
        AdaptadorSelector selector = new AdaptadorSelector(getBaseContext(), R.layout.layout_seleccion, elementos);

        // Creo un dialogo builder al que pasare el selector como adaptador personalizado
        AlertDialog.Builder dialogo = new AlertDialog.Builder(MainActivity.this);
        dialogo.setAdapter(selector, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                // Extraemos los datos del item seleccionado (i)
                DTOElementoMultimedia elem = selector.getItem(i);
                ruta = selector.getItem(i).getDatos();

                // Usamos la intermfaz de los fragmentos, para poder castear e invocar el metodo
                // en el que ejecutara el uri recogido
                ((InterfazAccionFragments)listadoFragmentos[eleccion]).setArchivo(Uri.parse(ruta),0);
            }
        });

        // Mostramos el dialogo
        dialogo.show();
    }
}