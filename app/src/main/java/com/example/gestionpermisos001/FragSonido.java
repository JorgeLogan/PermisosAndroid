package com.example.gestionpermisos001;

import android.database.Cursor;
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
import android.widget.Toast;

import java.io.IOException;

/**
 * create an instance of this fragment.
 */
public class FragSonido extends Fragment implements InterfazAccionFragments {
    private ViewGroup contenedor;
    private ImageView imgSonido;
    private static MediaPlayer mp = null;

    private static Uri uriActual = null;
    private static int posicion = 0;

    // Para cuando se cambie la actividad o se rote, guardamos los datos
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.d("Pruebas", "<-- <-- Se inicia onSaveInstanceState --> -->");
        if(mp != null){
            try{
                outState.putString("uri", this.uriActual.toString());
                this.posicion = mp.getCurrentPosition();
                outState.putInt("posicion", this.posicion);

                if(mp.isPlaying()) mp.stop();

                Log.d("Pruebas","Guardados " + this.uriActual.toString() + " en la pos " + this.posicion);
            }catch(Exception e){
               Log.d("Pruebas","Error al cargar el frag de sonido: "
                       + e.getMessage());
            }
        }
    }

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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View vista =  inflater.inflate(R.layout.fragment_sonido, container, false);
        this.imgSonido = (ImageView) vista.findViewById(R.id.imgSonido);
        this.contenedor = container;

        try{
            // Si no pasamos un bundle, estamos creando, sino, recuperando informacion
            if(savedInstanceState!= null && savedInstanceState.containsKey("uri")){
                this.uriActual = Uri.parse(savedInstanceState.getString("uri"));
                this.posicion = savedInstanceState.getInt("posicion");
                this.setArchivo(this.uriActual, this.posicion);
                Log.d("Pruebas","<<<<<<<<   En onCreate recuperamos " +
                        this.uriActual.toString() + " " + this.posicion);
            }else{
                Log.d("Pruebas","<<<<<<<<   En onCreate CREAMOS " +
                        this.uriActual.toString() + " " + this.posicion);
            }

            mp = MediaPlayer.create(contenedor.getContext(), R.raw.musica);
            mp.start();

        }catch(Exception e){
            Log.d("Pruebas","Error al cargar el bundle en el oncreate: " + e.getMessage());
        }



        // Devolvemos la vista
        return vista;
    }

    // Para abrir un URI y que empiece a funcionar
    @Override
    public void setArchivo(Uri uri, int posicion) {
        Log.d("Pruebas","\\\\\\\\ Uri pasado: " + uri.toString());
        try {
            if(mp !=null){
                if (mp.isPlaying()) mp.stop();
                mp.release();
                try {
                    mp = new MediaPlayer();

                    if (uri.toString().contains("http")) { // Buscamos desde internet
                        mp.setDataSource(uri.toString());
                    } else if (uri.toString().contains("/")) { // Buscamos desde una ruta
                        mp.setDataSource(this.getArchivo(uri));
                    } else { // Tenemos un id de recurso
                        mp.release();
                        final int numId = Integer.parseInt(uri.toString());
                        //Log.d("Pruebas", " Intenta carfar rec sonico " + numId);
                        mp = MediaPlayer.create(contenedor.getContext(), numId);
                        mp.start();
                    }

                } catch (Exception e) {
                    Log.d("Pruebas", "Excepcion al crear el nuevo media player " + e.getMessage());
                }
                if(mp.isPlaying() == false) mp.prepare(); // Si se usa create, el prepare la lia parda
                mp.start();

                this.uriActual = uri;
            }
        } catch (Exception e) {
            Log.d("Pruebas" , "No se pudo cargar " + e.getMessage());
        }
    }

    // Para poder localizar un archivo del almacenamiento

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

    // Para cerrar y liberar memoria
    @Override
    public void cerrarFragment() {
        if(mp!= null){
            mp.release();
        }
    }
}