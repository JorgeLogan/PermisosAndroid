package com.example.gestionpermisos001;

/**
 * Clase entidad para los selectores de elementos multimedia
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


    // Getters (No necesito setters)
    public String getNombre() {
        return nombre;
    }
    public tipoElemento getTipo() {
        return tipo;
    }
    public String getDatos() {
        return datos;
    }
}
