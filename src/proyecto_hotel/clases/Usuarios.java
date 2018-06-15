/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyecto_hotel.clases;

import javafx.scene.image.Image;

/**
 *
 * @author David Salguera
 */
public class Usuarios {
    
    Image imagen;
    String nombre;
    int tipo;
    int estado;
    String contra;

    public Usuarios(Image imagen, String nombre, int tipo, int estado, String contra) {
        this.imagen = imagen;
        this.nombre = nombre;
        this.tipo = tipo;
        this.estado = estado;
        this.contra = contra;
    }

    public Image getImagen() {
        return imagen;
    }

    public void setImagen(Image imagen) {
        this.imagen = imagen;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getTipo() {
        return tipo;
    }

    public void setTipo(int tipo) {
        this.tipo = tipo;
    }

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }

    public String getContra() {
        return contra;
    }

    public void setContra(String contra) {
        this.contra = contra;
    }

    
    
  
}
