/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyecto_hotel.clases;

/**
 *
 * @author David Salguera
 */
public class Reserva {
    
    int Id_reserva;	
    String Fecha_inicio;
    String Fecha_final;
    int Estado;
    float Costo_total;
    int Id_habitacion;
    String Fecha_reserva;

    public Reserva(int Id_reserva, String Fecha_inicio, String Fecha_final, int Estado, float Costo_total, int Id_habitacion, String Fecha_reserva) {
        this.Id_reserva = Id_reserva;
        this.Fecha_inicio = Fecha_inicio;
        this.Fecha_final = Fecha_final;
        this.Estado = Estado;
        this.Costo_total = Costo_total;
        this.Id_habitacion = Id_habitacion;
        this.Fecha_reserva = Fecha_reserva;
    }

    public int getId_reserva() {
        return Id_reserva;
    }

    public void setId_reserva(int Id_reserva) {
        this.Id_reserva = Id_reserva;
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

    public int getEstado() {
        return Estado;
    }

    public void setEstado(int Estado) {
        this.Estado = Estado;
    }

    public float getCosto_total() {
        return Costo_total;
    }

    public void setCosto_total(float Costo_total) {
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

    
}
