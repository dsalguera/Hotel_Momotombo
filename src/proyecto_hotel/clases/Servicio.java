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
public class Servicio {
    int id_servicio_cuarto;
    int id_estancia;
    String fecha_hota;
    double costo_total;

    public Servicio(int id_servicio_cuarto, int id_estancia, String fecha_hota, double costo_total) {
        this.id_servicio_cuarto = id_servicio_cuarto;
        this.id_estancia = id_estancia;
        this.fecha_hota = fecha_hota;
        this.costo_total = costo_total;
    }

    public int getId_servicio_cuarto() {
        return id_servicio_cuarto;
    }

    public void setId_servicio_cuarto(int id_servicio_cuarto) {
        this.id_servicio_cuarto = id_servicio_cuarto;
    }

    public int getId_estancia() {
        return id_estancia;
    }

    public void setId_estancia(int id_estancia) {
        this.id_estancia = id_estancia;
    }

    public String getFecha_hota() {
        return fecha_hota;
    }

    public void setFecha_hota(String fecha_hota) {
        this.fecha_hota = fecha_hota;
    }

    public double getCosto_total() {
        return costo_total;
    }

    public void setCosto_total(double costo_total) {
        this.costo_total = costo_total;
    }
    
    
}
