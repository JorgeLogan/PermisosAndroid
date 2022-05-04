package com.example.gestionpermisos001;

import android.database.Cursor;
import android.media.MediaParser;
import android.net.Uri;
import android.os.Bundle;

import android.app.Fragment;
import android.media.MediaPlayer;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;


/**
 * Clase para el fragmento central de sonido. Incorporo un listener para hacerlo con carga
 * asincrona para cuando lee desde almacenamiento interno o desde internet.
 * Desde recursos internos no lo necesita (da error al prepararlo)
 */
public class FragSonido extends Fragment implements InterfazAccionFragments, MediaPlayer.OnPreparedListener {
    // Atributos
    private ViewGroup contenedor;
    private ImageView imgSonido;
    private static MediaPlayer mp = null;
    private static Uri uriActual = null;
    private static int posicion = 0;


    // Constructor vacio
    public FragSonido() {
        // Required empty public constructor
        this.uriActual = Uri.parse(String.valueOf(R.raw.musica));
        this.posicion = 0;
    }


    // Nos vale para la creacion y la recuperacion de la vista
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    // Nos vale para la creacion y la recuperacion de la vista
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View vista =  inflater.inflate(R.layout.fragment_sonido, container, false);
        this.imgSonido = (ImageView) vista.findViewById(R.id.imgSonido);
        this.contenedor = container;

        // Ponemos el archivo que tenemos en la uri a sonar en la posicion adecuada
        this.setArchivo(uriActual, posicion);

        // Devolvemos la vista
        return vista;
    }

    // Para abrir un URI y que empiece a funcionar
    @Override
    public void setArchivo(Uri uri, int pos) {
        uriActual = uri;
        posicion = pos;
        uriActual = uri;

        // Por si esta sonando, intentamos pararlo. No necesito mensaje de excepcion para esto
        try{
            mp.stop();
        }catch (Exception e){}

        // Ahora intentamos crear un nuevo media player, y cargar la uri teniendo en cuenta sus
        // origenes
        try {
            mp = new MediaPlayer();

            if (uri.toString().contains("http")) { // Buscamos desde internet
                mp.setDataSource(uri.toString());
                mp.prepareAsync();
                mp.setOnPreparedListener(this);
            }
            else if (uri.toString().contains("/")) { // Buscamos desde una ruta
                mp.setDataSource(this.getArchivo(uri));
                mp.prepareAsync();
                mp.setOnPreparedListener(this);
            }
            else { // Tenemos un id de recurso
                //mp.release();
                final int numId = Integer.parseInt(uri.toString());
                mp = MediaPlayer.create(contenedor.getContext(), numId);
            }

            // Empezamos la reproduccion y lo ponemos en posicion
            mp.start();
            mp.seekTo(posicion);

        } catch (Exception e) {
            Log.d("Pruebas", "Error al intentar sacar el archivo de sonido: " + e.getMessage());
        }
    }


    // Metodo para cerrar el fragment y que no siga sonando
    @Override
    public void cerrarFragment() {
        try{
            if(mp!= null){
                if (mp.isPlaying()){
                    posicion = mp.getCurrentPosition();
                    mp.stop();
                }
                mp.release();
            }
        }catch(Exception e){} // No necesito feedback si da error
    }


    // Funcion de la interfaz para reiniciar el sonido cuando vuelva de otra actividad
    @Override
    public void reiniciar() {
        this.setArchivo(uriActual, posicion);
    }


    /**
     * Para poder localizar un archivo del almacenamiento, el uri por si solo no vale porque da error
     * Hay que hacer una consulta y buscarlo del listado que tengamos
     * @param uri El uri que tenemos que no funciona de por si
     * @return El string del archivo que necesitamos para abrirlo
     */
    public String getArchivo(Uri uri){
        String resultado= "";

        // Cogemos el listado de datos del mediastore
        String [] archivosCamino = { MediaStore.Files.FileColumns.DATA };

        // Ahora necesitamos un cursor para realizar la busqueda del uri que tenemos en el array
        Cursor c = contenedor.getContext().getContentResolver().query(uri, archivosCamino,
                null, null, null);

        // Comprobamos que el cursor no es nulo, y tenemos informacion en el
        if(c!= null){
            c.moveToFirst(); // Nos movemos al primer registro (y unico, o deberia)

            // El archivo, esta en la primera columna, asi que es lo que vamos a usar
            int indiceColumna = c.getColumnIndex(archivosCamino[0]);

            resultado = c.getString(indiceColumna);
        }
        return resultado;
    }


    // Para que no siga sonando al cambiar
    @Override
    public void onPause() {
        super.onPause();
        this.cerrarFragment();
    }


    // Para la carga asincrona cuando se lee desde internet o desde el almancenamiento
    @Override
    public void onPrepared(MediaPlayer mediaPlayer) {
        mediaPlayer.start();
    }
}