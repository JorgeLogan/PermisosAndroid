package com.example.gestionpermisos001;

/**
 * Clase para los selectores de elementos multimedia
 *
 */
public class DTOElementoMultimedia {
    public enum tipoElemento {Imagen, Sonido, Video};
    private String nombre;
    private tipoElemento tipo;
    private String datos; // Para el elemento a cargar en la uri


    // Constructor vacio
    public DTOElementoMultimedia(){}


    // Cosntructor con parametros
    public DTOElementoMultimedia(String nombre, tipoElemento tipo, String datos) {
        this.nombre = nombre;
        this.tipo = tipo;
        this.datos = datos;
    }


    // Getters y Setters
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public tipoElemento getTipo() {
        return tipo;
    }

    public void setTipo(tipoElemento tipo) {
        this.tipo = tipo;
    }

    public String getDatos() {
        return datos;
    }

    public void setDatos(String datos) {
        this.datos = datos;
    }
}
