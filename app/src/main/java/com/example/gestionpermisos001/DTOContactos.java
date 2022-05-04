package com.example.gestionpermisos001;

import androidx.recyclerview.widget.RecyclerView;

/**
 * Clase entidad para manejar la lista de contactos
 */
public class DTOContactos{
    // Atributos
    private String nombre;
    private String telefono;

    // Constructor vacio
    public DTOContactos(){}

    // Constructor con parametros
    public DTOContactos(String nombre, String telefono){
        this.nombre = nombre;
        this.telefono = telefono;
    }

    // Getters (no necesitamos setters)
    public String getNombre(){
        return this.nombre;
    }
    public String getTelefono(){
        return this.telefono;
    }
}
