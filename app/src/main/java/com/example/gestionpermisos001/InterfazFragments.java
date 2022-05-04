package com.example.gestionpermisos001;

/**
 * Interfaz para la MainActivity. Se usa para poder invocar los metodos desde los fragments,
 * casteando la actividad a esta interfaz
 */
public interface InterfazFragments {
    public void seleccionarImagen();
    public void seleccionaVideo();
    public void seleccionarSonido();

    public void abrirGaleria();
    public void abrirWWW();
    public void abrirRecursos();
}
