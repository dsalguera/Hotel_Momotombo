
package proyecto_hotel.clases;

import javafx.scene.image.Image;


public class Acciones {
 
    public  int Id_accion;
    public  String Nombre;
    public  String Descripcion;
    public String Fecha_hora;
    public String Tipo;
    public String Id_cliente;
     public Image foto;

    public Acciones(int Id_accion, String Nombre, String Descripcion, String Fecha_hora, String Tipo, String Id_cliente, Image foto) {
        this.Id_accion = Id_accion;
        this.Nombre = Nombre;
        this.Descripcion = Descripcion;
        this.Fecha_hora = Fecha_hora;
        this.Tipo = Tipo;
        this.Id_cliente = Id_cliente;
        this.foto=foto;
    }

     public  int getId_accion() {
        return Id_accion;
    }

   public  String getNombre() {
        return Nombre;
    }

    public  String getDescripcion() {
        return Descripcion;
    }

 public  String getFecha_hora() {
        return Fecha_hora;
    }

 public  String getTipo() {
        return Tipo;
    }

   public String getId_cliente() {
        return Id_cliente;
    }

   public  Image getfoto() {
        return foto;
    }

    public void setId_accion(int Id_accion) {
        this.Id_accion = Id_accion;
    }

    public void setNombre(String Nombre) {
        this.Nombre = Nombre;
    }

    public void setDescripcion(String Descripcion) {
        this.Descripcion = Descripcion;
    }

    public void setFecha_hora(String Fecha_hora) {
        this.Fecha_hora = Fecha_hora;
    }

    public void setTipo(String Tipo) {
        this.Tipo = Tipo;
    }

    public void setId_cliente(String Id_cliente) {
        this.Id_cliente = Id_cliente;
    }

    public void setfoto(Image aviso) {
        this.foto = aviso;
    }


    
    
}
