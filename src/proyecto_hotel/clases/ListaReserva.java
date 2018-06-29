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
public class ListaReserva {
    
    int Id_reserva;
    int Id_cliente;
    String Fecha_inicio; 
    String Fecha_final;	
    String Estado;
    Double Costo_total;	
    int Id_habitacion;
    String Fecha_reserva;
    
    Image imagenCliente, imagenHabitacion;

    public ListaReserva(int Id_reserva, int Id_cliente, String Fecha_inicio, String Fecha_final, String Estado, Double Costo_total, int Id_habitacion, String Fecha_reserva, Image imagenCliente, Image imagenHabitacion) {
        this.Id_reserva = Id_reserva;
        this.Id_cliente = Id_cliente;
        this.Fecha_inicio = Fecha_inicio;
        this.Fecha_final = Fecha_final;
        this.Estado = Estado;
        this.Costo_total = Costo_total;
        this.Id_habitacion = Id_habitacion;
        this.Fecha_reserva = Fecha_reserva;
        this.imagenCliente = imagenCliente;
        this.imagenHabitacion = imagenHabitacion;
    }

    public int getId_reserva() {
        return Id_reserva;
    }

    public void setId_reserva(int Id_reserva) {
        this.Id_reserva = Id_reserva;
    }

    public int getId_cliente() {
        return Id_cliente;
    }

    public void setId_cliente(int Id_cliente) {
        this.Id_cliente = Id_cliente;
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

    public String getEstado() {
        return Estado;
    }

    public void setEstado(String Estado) {
        this.Estado = Estado;
    }

    public Double getCosto_total() {
        return Costo_total;
    }

    public void setCosto_total(Double Costo_total) {
        this.Costo_total = Costo_total;
    }

    public int getId_habitacion() {
        return Id_habitacion;
    }

    public void setId_habitacion(int Id_habitacion) {
        this.Id_habitacion = Id_habitacion;
    }

    public String getFecha_reserva() {
        return Fecha_reserva;
    }

    public void setFecha_reserva(String Fecha_reserva) {
        this.Fecha_reserva = Fecha_reserva;
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
