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
public class Servicio_cuarto {
    
    int id_servicio_al_cuarto;
    int id_estancia;
    String fecha_hora;
    double total;
    
    Image imagenProducto;
    
    int id_producto;
    String nombre_producto;
    double precio;
    int cantidad;
    double subtotal;

    public Servicio_cuarto(int id_servicio_al_cuarto, int id_estancia, String fecha_hora, double total, Image imagenProducto, int id_producto, String nombre_producto, double precio, int cantidad, double subtotal) {
        this.id_servicio_al_cuarto = id_servicio_al_cuarto;
        this.id_estancia = id_estancia;
        this.fecha_hora = fecha_hora;
        this.total = total;
        this.imagenProducto = imagenProducto;
        this.id_producto = id_producto;
        this.nombre_producto = nombre_producto;
        this.precio = precio;
        this.cantidad = cantidad;
        this.subtotal = subtotal;
    }

    public int getId_servicio_al_cuarto() {
        return id_servicio_al_cuarto;
    }

    public void setId_servicio_al_cuarto(int id_servicio_al_cuarto) {
        this.id_servicio_al_cuarto = id_servicio_al_cuarto;
    }

    public int getId_estancia() {
        return id_estancia;
    }

    public void setId_estancia(int id_estancia) {
        this.id_estancia = id_estancia;
    }

    public String getFecha_hora() {
        return fecha_hora;
    }

    public void setFecha_hora(String fecha_hora) {
        this.fecha_hora = fecha_hora;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public Image getImagenProducto() {
        return imagenProducto;
    }

    public void setImagenProducto(Image imagenProducto) {
        this.imagenProducto = imagenProducto;
    }

    public int getId_producto() {
        return id_producto;
    }

    public void setId_producto(int id_producto) {
        this.id_producto = id_producto;
    }

    public String getNombre_producto() {
        return nombre_producto;
    }

    public void setNombre_producto(String nombre_producto) {
        this.nombre_producto = nombre_producto;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public double getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(double subtotal) {
        this.subtotal = subtotal;
    }

   
}
