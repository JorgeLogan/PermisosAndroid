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
    private MediaPlayer mp;

    public FragSonido() {
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
        View vista =  inflater.inflate(R.layout.fragment_sonido, container, false);
        this.imgSonido = (ImageView) vista.findViewById(R.id.imgSonido);
        this.contenedor = container;
        // Al principio carga un sonido desde recursos
        mp = MediaPlayer.create(container.getContext(), R.raw.musica);
        mp.start();

        // Devolvemos la vista
        return vista;
    }

    // Para abrir un URI y que empiece a funcionar
    @Override
    public void setArchivo(Uri uri) {


        try {
            Log.d("Pruebas", "Intento cargar sonido " + uri.toString());
            if(mp.isPlaying()) mp.stop();
            mp.release();
            try{
                mp = new MediaPlayer();

                if(uri.toString().contains("http")){ // Buscamos desde internet
                    mp.setDataSource(uri.toString());
                }
                else if(uri.toString().contains("/external/")){ // Buscamos desde la SD
                    mp.setDataSource(this.getArchivo(uri));
                }
                else{
                    mp.setDataSource(uri.getPath());
                }
                mp.prepare();
                mp.start();

            }catch(Exception e){
                Log.d("Pruebas", "Excepcion al crear el nuevo media player " + e.getMessage());
            }
        } catch (Exception e) {
            Toast.makeText(getActivity().getBaseContext(), "No se pudo cargar " + e.getMessage(), Toast.LENGTH_LONG).show();
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
            Toast.makeText(getActivity().getBaseContext(), "Cerrando fragment de sonido", Toast.LENGTH_LONG).show();
            mp.release();
        }else{
            Toast.makeText(getActivity().getBaseContext(), "No se puede cerrar MediaPlayer de sonido", Toast.LENGTH_LONG).show();
        }
    }
}