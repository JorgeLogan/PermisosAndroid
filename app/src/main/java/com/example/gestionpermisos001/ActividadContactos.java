package com.example.gestionpermisos001;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

/**
 * Clase para manejar la lista de contactos
 */
public class ActividadContactos extends AppCompatActivity {
    private int PERMISO_LEER_CONTACTOS = 100;
    private DTOContactos[] contactos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actividad_contactos);

        // Cargamos los elementos
        Button btnLlamar = (Button)findViewById(R.id.btnContactosLlamar);
        Button btnVolver = (Button)findViewById(R.id.btnContactosVolver);
        Button btnSalir = (Button)findViewById(R.id.btnContactosSalir);
        ListView lvContactos = (ListView)findViewById(R.id.lvContactos);

        // Comprobamos si tenemos permisos para mirar las llamadas
        this.comprobarPermisosLlamada();

        try{
            // Preparamos el adaptador de contactos
            sacarContactos();

            if(this.contactos ==null || this.contactos.length == 0)
                Toast.makeText(this, "No saco contactos", Toast.LENGTH_SHORT).show();
            else
                Toast.makeText(this, "Contactos: " + contactos.length, Toast.LENGTH_SHORT).show();

            AdaptadorContactos adaptador = new AdaptadorContactos(this, R.layout.adaptador_contactos, contactos);
            lvContactos.setAdapter(adaptador);
        }catch (Exception e){
            Toast.makeText(ActividadContactos.this, "Error: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }




        // Damos funcionalidad al boton de salir
        btnSalir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        // Damos funcionalidad al boton de volver
        btnVolver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        // Damos funcionalidad al boton de llamar
        btnLlamar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                llamar();
            }
        });
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
            // Sabemos el tamaño del cursor, asi que inicializamos el array que tenemos de contactos
            this.contactos = new DTOContactos[cursor.getCount()];

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
                            contactos[i] = new DTOContactos(nombre, numero);
                            i++;
                        }
                    }
                }


            }catch(Exception e){
                Log.d("Pruebas", "Error " + e.getMessage());
            }
        }
        Log.d("Pruebas", "He sacado " + contactos.length);
    }

    private void llamar(){

    }
}