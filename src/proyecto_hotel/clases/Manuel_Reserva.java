
package proyecto_hotel.clases;

import javafx.scene.image.Image;

public class Manuel_Reserva {
   public int Id_reserva, Id_cliente,Id_habitacion;
   public String Fecha_inicio, Fecha_final, Fecha_reserva, Estado;
   public double Costo_total;
   public String nombre_cliente,nombre_habitacion;
   public Image imgcliente,imghabitacion;

    public Manuel_Reserva(int Id_reserva, int Id_cliente, int Id_habitacion, String Fecha_inicio, String Fecha_final, String Fecha_reserva, String Estado, double Costo_total, String nombre_cliente, String nombre_habitacion, Image imgcliente, Image imghabitacion) {
        this.Id_reserva = Id_reserva;
        this.Id_cliente = Id_cliente;
        this.Id_habitacion = Id_habitacion;
        this.Fecha_inicio = Fecha_inicio;
        this.Fecha_final = Fecha_final;
        this.Fecha_reserva = Fecha_reserva;
        this.Estado = Estado;
        this.Costo_total = Costo_total;
        this.nombre_cliente = nombre_cliente;
        this.nombre_habitacion = nombre_habitacion;
        this.imgcliente = imgcliente;
        this.imghabitacion = imghabitacion;
    }

    public Image getImgcliente() {
        return imgcliente;
    }

    public Image getImghabitacion() {
        return imghabitacion;
    }
    public void setNombre_cliente(String nombre_cliente) {
        this.nombre_cliente = nombre_cliente;
    }

    public void setNombre_habitacion(String nombre_habitacion) {
        this.nombre_habitacion = nombre_habitacion;
    }

    public String getNombre_cliente() {
        return nombre_cliente;
    }

    public String getNombre_habitacion() {
        return nombre_habitacion;
    }

    public Manuel_Reserva(int Id_reserva, int Id_cliente, int Id_habitacion, String Fecha_inicio, String Fecha_final, String Fecha_reserva, String Estado, double Costo_total) {
        this.Id_reserva = Id_reserva;
        this.Id_cliente = Id_cliente;
        this.Id_habitacion = Id_habitacion;
        this.Fecha_inicio = Fecha_inicio;
        this.Fecha_final = Fecha_final;
        this.Fecha_reserva = Fecha_reserva;
        this.Estado = Estado;
        this.Costo_total = Costo_total;
    }

    public int getId_reserva() {
        return Id_reserva;
    }

    public int getId_cliente() {
        return Id_cliente;
    }

    public int getId_habitacion() {
        return Id_habitacion;
    }

    public Manuel_Reserva(int Id_reserva, int Id_cliente, int Id_habitacion, String Fecha_inicio, String Fecha_final, String Fecha_reserva, String Estado, double Costo_total, String nombre_cliente, String nombre_habitacion) {
        this.Id_reserva = Id_reserva;
        this.Id_cliente = Id_cliente;
        this.Id_habitacion = Id_habitacion;
        this.Fecha_inicio = Fecha_inicio;
        this.Fecha_final = Fecha_final;
        this.Fecha_reserva = Fecha_reserva;
        this.Estado = Estado;
        this.Costo_total = Costo_total;
        this.nombre_cliente = nombre_cliente;
        this.nombre_habitacion = nombre_habitacion;
    }

    public String getFecha_inicio() {
        return Fecha_inicio;
    }

    public String getFecha_final() {
        return Fecha_final;
    }

    public String getFecha_reserva() {
        return Fecha_reserva;
    }

    public String getEstado() {
        return Estado;
    }

    public double getCosto_total() {
        return Costo_total;
    }

    public void setId_reserva(int Id_reserva) {
        this.Id_reserva = Id_reserva;
    }

    public void setId_cliente(int Id_cliente) {
        this.Id_cliente = Id_cliente;
    }

    public void setId_habitacion(int Id_habitacion) {
        this.Id_habitacion = Id_habitacion;
    }

    public void setFecha_inicio(String Fecha_inicio) {
        this.Fecha_inicio = Fecha_inicio;
    }

    public void setFecha_final(String Fecha_final) {
        this.Fecha_final = Fecha_final;
    }

    public void setFecha_reserva(String Fecha_reserva) {
        this.Fecha_reserva = Fecha_reserva;
    }

    public void setEstado(String Estado) {
        this.Estado = Estado;
    }

    public void setCosto_total(double Costo_total) {
        this.Costo_total = Costo_total;
    }
    
    
    
    
   
   
   
   
}
