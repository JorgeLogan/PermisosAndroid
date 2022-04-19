package com.example.gestionpermisos001;

/**
 * Clase para manejar la lista de contactos
 */
public class DTOContactos {
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

    // Getters y Setters
    public String getNombre(){
        return this.nombre;
    }

    public void setNombre(String nombre){
        this.nombre = nombre;
    }

    public String getTelefono(){
        return this.telefono;
    }

    public void setTelefono(String telefono){
        this.telefono = telefono;
    }
}
