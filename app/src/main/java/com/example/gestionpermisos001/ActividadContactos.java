package com.example.gestionpermisos001;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Clase para manejar la lista de contactos
 */
public class ActividadContactos extends AppCompatActivity {
    private final int PERMISO_LEER_CONTACTOS = 100;
    private List<DTOContactos> contactos = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try{
            setContentView(R.layout.activity_actividad_contactos);

            // Cargamos los elementos
            Button btnVolver = (Button)findViewById(R.id.btnContactosVolver);
            Button btnSalir = (Button)findViewById(R.id.btnContactosSalir);

            RecyclerView lvContactos = (RecyclerView)findViewById(R.id.lvContactos);

            // Comprobamos si tenemos permisos para mirar las llamadas
            this.comprobarPermisosLlamada();
            // Preparamos el adaptador de contactos
            sacarContactos();

            if(this.contactos ==null || this.contactos.size() == 0)
                Toast.makeText(this, "No saco contactos", Toast.LENGTH_SHORT).show();
            else
                Toast.makeText(this, "Contactos: " + contactos.size(), Toast.LENGTH_SHORT).show();

            lvContactos.setHasFixedSize(true); // Para que no cambie de tamaño
            AdaptadorContactosRV adaptadorRv = new AdaptadorContactosRV(this, (ArrayList<DTOContactos>) contactos);
            lvContactos.setAdapter(adaptadorRv);

            // Damos funcionalidad al boton de salir
            btnSalir.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    MainActivity.instancia.salir();
                    finish();
                }
            });

            // Damos funcionalidad al boton de volver
            btnVolver.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    setResult(-1);
                    finish();
                }
            });

        }catch (Exception e){}
    }

    // Funcion para comprobar los permisos de llamada
    private void comprobarPermisosLlamada(){
        if(ActivityCompat.checkSelfPermission(ActividadContactos.this,
                Manifest.permission.READ_CONTACTS)!= PackageManager.PERMISSION_GRANTED){
            // Si no tenemos permisos, los pedimos
            ActivityCompat.requestPermissions(ActividadContactos.this,
                    new String[] { Manifest.permission.READ_CONTACTS}, PERMISO_LEER_CONTACTOS );
        }
    }


    // Funcion para hacer pruebas de layouts antes de conseguir sacar los contactos
    private void crearListadoPrueba(){
        DTOContactos[] contactos = new DTOContactos[6];
        contactos[0] = new DTOContactos("Mary",  "123456");
        contactos[1] = new DTOContactos("Jorge", "78l910");
        contactos[2] = new DTOContactos("Logan", "112213");
        contactos[3] = new DTOContactos("Lunina",  "123456");
        contactos[4] = new DTOContactos("Gruño", "78l910");
        contactos[5] = new DTOContactos("Yaki y Canelo", "112213");
    }


    // Funcion para acceder al listado de contactos y pasarlos al array de DTOContactos
    @SuppressLint("Range")
    private void sacarContactos() {
        // Sacamos en un uri los contactos, con el ContentProvider especial
        Uri uri = ContactsContract.Contacts.CONTENT_URI;

        // Ya lo tenemos... Ahora ordenamos el listado por nombre
        String orden = ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME + "ASC";

        // Inicializamos el cursor para buscar la informacion del listado, indicando que queremos
        // que siga el orden que preparamos arriba
        Cursor cursor = getContentResolver().query(uri,
                null,null,null, orden);

        // Ahora recorremos el cursor
        if(cursor!= null){

            // Nos movemos al primer elemento para empezar a rellenar el listado
            cursor.moveToFirst();

            int i = 0; // Para el control del array.
            String id ="", nombre="", numero ="";
            // Empezamos a buscar
            try{
                if(cursor.getCount()>0){
                    while(cursor.moveToNext()){


                        // El id y el nombre se sacan de forma "directa"
                        // Veremos que en la busqueda, tenemos @Suppresslint.. obliga el IDE a ponerlo
                        id = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));
                        nombre = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));

                        // Pero el numero de telefono va mediante Uris, y tenemos que hacer otra consulta mas
                        Uri uriNumero = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;

                        // Inicializamos la seleccion
                        String seleccion = ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " =?";

                        // Y ahora el cursor para buscar el numero de telefono asociado al id de la persona actual
                        Cursor cursorTel = getContentResolver().query(uriNumero,
                                null, seleccion, new String[]{id}, null,null);

                        // Comprobamos que el contacto tenia numero
                        if(cursorTel!= null && cursor.getCount() > 0){
                            cursorTel.moveToFirst();
                            numero = cursorTel.getString(cursorTel.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                        }
                        if(nombre!= null && numero!= null){
                            contactos.add(new DTOContactos(nombre, numero));
                            i++;
                        }
                    }
                }
            }catch(Exception e){}
        }
        Log.d("Pruebas", "He sacado " + contactos.size());
    }
}