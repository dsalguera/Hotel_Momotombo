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
public class Clientes {
    
    Image imagen;
    int id;
    String primerNombre;
    String segundoNombre;
    String primerApellido;
    String segundoApellido;
    String identificacion;
    String tipo;
    String pais;
    int numero_reserva;
    int numero_estancia;
    String fecha_inscripcion;
    String fecha_nacimiento;
    String telefono;
    String correo;

    public Clientes(Image imagen, int id, String primerNombre, String segundoNombre, String primerApellido, String segundoApellido, String identificacion, String tipo, String pais, int numero_reserva, int numero_estancia, String fecha_inscripcion, String fecha_nacimiento, String telefono, String correo) {
        this.imagen = imagen;
        this.id = id;
        this.primerNombre = primerNombre;
        this.segundoNombre = segundoNombre;
        this.primerApellido = primerApellido;
        this.segundoApellido = segundoApellido;
        this.identificacion = identificacion;
        this.tipo = tipo;
        this.pais = pais;
        this.numero_reserva = numero_reserva;
        this.numero_estancia = numero_estancia;
        this.fecha_inscripcion = fecha_inscripcion;
        this.fecha_nacimiento = fecha_nacimiento;
        this.telefono = telefono;
        this.correo = correo;
    }

    public Image getImagen() {
        return imagen;
    }

    public void setImagen(Image imagen) {
        this.imagen = imagen;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPrimerNombre() {
        return primerNombre;
    }

    public void setPrimerNombre(String primerNombre) {
        this.primerNombre = primerNombre;
    }

    public String getSegundoNombre() {
        return segundoNombre;
    }

    public void setSegundoNombre(String segundoNombre) {
        this.segundoNombre = segundoNombre;
    }

    public String getPrimerApellido() {
        return primerApellido;
    }

    public void setPrimerApellido(String primerApellido) {
        this.primerApellido = primerApellido;
    }

    public String getSegundoApellido() {
        return segundoApellido;
    }

    public void setSegundoApellido(String segundoApellido) {
        this.segundoApellido = segundoApellido;
    }

    public String getIdentificacion() {
        return identificacion;
    }

    public void setIdentificacion(String identificacion) {
        this.identificacion = identificacion;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    public int getNumero_reserva() {
        return numero_reserva;
    }

    public void setNumero_reserva(int numero_reserva) {
        this.numero_reserva = numero_reserva;
    }

    public int getNumero_estancia() {
        return numero_estancia;
    }

    public void setNumero_estancia(int numero_estancia) {
        this.numero_estancia = numero_estancia;
    }

    public String getFecha_inscripcion() {
        return fecha_inscripcion;
    }

    public void setFecha_inscripcion(String fecha_inscripcion) {
        this.fecha_inscripcion = fecha_inscripcion;
    }

    public String getFecha_nacimiento() {
        return fecha_nacimiento;
    }

    public void setFecha_nacimiento(String fecha_nacimiento) {
        this.fecha_nacimiento = fecha_nacimiento;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    
    

    
}
