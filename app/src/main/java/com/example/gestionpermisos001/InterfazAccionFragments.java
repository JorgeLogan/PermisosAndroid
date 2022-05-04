package com.example.gestionpermisos001;

import android.net.Uri;

/**
 * Interfaz para los fragments centrales
 */
public interface InterfazAccionFragments {
    public void setArchivo(Uri uri, int posicion);
    public void cerrarFragment();
    public void reiniciar();
}
