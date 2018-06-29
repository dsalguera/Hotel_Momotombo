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
public class ListaEstancia {
    
    int Id_estancia;
    int Id_cliente;
    int Id_habitacion;
    String Fecha_inicio;
    String Fecha_final;
    Double Costo_total;
    String Descripcion;
    String Estado;
    int Id_reserva;
    
    Image imagenCliente, imagenHabitacion;

    public ListaEstancia(int Id_estancia, int Id_cliente, int Id_habitacion, String Fecha_inicio, String Fecha_final, Double Costo_total, String Descripcion, String Estado, int Id_reserva, Image imagenCliente, Image imagenHabitacion) {
        this.Id_estancia = Id_estancia;
        this.Id_cliente = Id_cliente;
        this.Id_habitacion = Id_habitacion;
        this.Fecha_inicio = Fecha_inicio;
        this.Fecha_final = Fecha_final;
        this.Costo_total = Costo_total;
        this.Descripcion = Descripcion;
        this.Estado = Estado;
        this.Id_reserva = Id_reserva;
        this.imagenCliente = imagenCliente;
        this.imagenHabitacion = imagenHabitacion;
    }

    public int getId_estancia() {
        return Id_estancia;
    }

    public void setId_estancia(int Id_estancia) {
        this.Id_estancia = Id_estancia;
    }

    public int getId_cliente() {
        return Id_cliente;
    }

    public void setId_cliente(int Id_cliente) {
        this.Id_cliente = Id_cliente;
    }

    public int getId_habitacion() {
        return Id_habitacion;
    }

    public void setId_habitacion(int Id_habitacion) {
        this.Id_habitacion = Id_habitacion;
    }

    public String getFecha_inicio() {
        return Fecha_inicio;
    }

    public void setFecha_inicio(String Fecha_inicio) {
        this.Fecha_inicio = Fecha_inicio;
    }

    public String getFecha_final() {
        return Fecha_final;
    }

    public void setFecha_final(String Fecha_final) {
        this.Fecha_final = Fecha_final;
    }

    public Double getCosto_total() {
        return Costo_total;
    }

    public void setCosto_total(Double Costo_total) {
        this.Costo_total = Costo_total;
    }

    public String getDescripcion() {
        return Descripcion;
    }

    public void setDescripcion(String Descripcion) {
        this.Descripcion = Descripcion;
    }

    public String getEstado() {
        return Estado;
    }

    public void setEstado(String Estado) {
        this.Estado = Estado;
    }

    public int getId_reserva() {
        return Id_reserva;
    }

    public void setId_reserva(int Id_reserva) {
        this.Id_reserva = Id_reserva;
    }

    public Image getImagenCliente() {
        return imagenCliente;
    }

    public void setImagenCliente(Image imagenCliente) {
        this.imagenCliente = imagenCliente;
    }

    public Image getImagenHabitacion() {
        return imagenHabitacion;
    }

    public void setImagenHabitacion(Image imagenHabitacion) {
        this.imagenHabitacion = imagenHabitacion;
    }

    
    
    
            
}
