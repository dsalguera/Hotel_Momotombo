/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyecto_hotel.interfaces;

import com.jfoenix.controls.*;
import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import proyecto_hotel.Conexion;
import proyecto_hotel.FXMLDocumentController;
import proyecto_hotel.clases.Productos;
import proyecto_hotel.clases.Usuarios;

/**
 * FXML Controller class
 *
 * @author David Salguera
 */
public class MenuController implements Initializable {

    @FXML
    private AnchorPane lienzo;

    @FXML
    private JFXButton btnInicio;

    @FXML
    private JFXButton btnHabitaciones;

    @FXML
    private JFXButton btnClientes;

    @FXML
    private JFXButton btnReservas;

    @FXML
    private JFXButton btnEstancias;

    @FXML
    private JFXButton btnDetalle_servicio;

    @FXML
    private JFXButton btnProductos;

    @FXML
    private JFXButton btnServicio_cuarto;

    @FXML
    private JFXButton btnBitacora;

    @FXML
    private JFXButton btnCerrar_sesion;
    
    @FXML
    private JFXButton btnMinimizar;

    @FXML
    private JFXButton btnMaximizar;

    @FXML
    private JFXButton btnCerrar;

    @FXML
    private ImageView img_logo;

    @FXML
    private ImageView img_user;

    @FXML
    private Label txtuser;

    @FXML
    private Label txttipo;
    
    @FXML
    private Label labHora;
    
    @FXML
    private Label labFecha;
    
    double xOffset, yOffset;
    
    
    @FXML
    void Presionar(MouseEvent event) {
        xOffset = lienzo.getScene().getWindow().getX() - event.getScreenX();
        yOffset = lienzo.getScene().getWindow().getY() - event.getScreenY();
    }

    @FXML
    void Arrastrar(MouseEvent event) {
        lienzo.getScene().getWindow().setX(event.getScreenX() + xOffset);
        lienzo.getScene().getWindow().setY(event.getScreenY() + yOffset);
    }
    
    @FXML
    void Maximizar(ActionEvent event) {
        Stage stage = (Stage) ((Node)(event.getSource())).getScene().getWindow();
        stage.setFullScreen(true);
    }

    @FXML
    void Minimizar(ActionEvent event) {
        Stage stage = (Stage) ((Node)(event.getSource())).getScene().getWindow();
        stage.setIconified(true);
    }
    
    @FXML
    void Cerrar(ActionEvent event) {
        try {
            
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("/proyecto_hotel/FXMLDocument.fxml"));

        Scene scene = new Scene(fxmlLoader.load());
        Stage stage = new Stage();
        stage.setTitle("Principal");
        stage.setScene(scene);
        stage.initStyle(StageStyle.UNDECORATED);
        stage.show();
        
        
        ((Node)(event.getSource())).getScene().getWindow().hide();
        
        } catch (IOException e) {
        } 
    }

    
    String user ;
    int type;
    
    public void setUser(String username, int type) throws SQLException{
        
        txtuser.setText(username);
        this.type = type;
        if (this.type == 1) {
            txttipo.setText("Administrador");
        }else if (this.type == 2) {
            txttipo.setText("Secretario");
        }else if (this.type == 3) {
            txttipo.setText("Visitante");
        }
        Conexion();
        
    }
    
    Conexion c = new Conexion();
    Connection connection ;
    
    void Conexion() throws SQLException{ 
        
        String dir = "src\\proyecto_hotel\\imagenes\\usuarios\\";
        
        connection = (Connection) DriverManager.getConnection(c.getString_connection(), c.getUsername(), c.getPassword());
        Statement stm = (Statement) connection.createStatement();
        
        ResultSet rs = stm.executeQuery("select * from usuario where Nombre_usuario = '"+txtuser.getText()+"';");
                
        String nombre,imagen = null,contra;
        int tipo,estado;
                
        while (rs.next()) {
                nombre = rs.getString("nombre_usuario");
                tipo = rs.getInt("tipo_cuenta");
                estado = rs.getInt("estado");
                imagen = rs.getString("imagen");
                contra = rs.getString("contrasena"); 
                img_user.setImage(new Image(new File(dir+imagen).toURI().toString()));
        }
        
        
    }

    @FXML
    void Bitacora(ActionEvent event) throws IOException {
        
        
        
    }

    @FXML
    void Cerrar_sesion(ActionEvent event) {
        System.out.println("user "+user);
        System.out.println("type "+type);
    }

    @FXML
    void Clientes(ActionEvent event) throws IOException {
        
        Pane clientes = FXMLLoader.load(getClass().getResource("/proyecto_hotel/interfaces/Clientes.fxml"));
        AjustePagina(clientes);
        lienzo.getChildren().add(clientes);
        
    }

    @FXML
    void Detalle_servicio(ActionEvent event) throws IOException {
        
        Pane detalle_servicio = FXMLLoader.load(getClass().getResource("/proyecto_hotel/interfaces/Detalle_servicio.fxml"));
        AjustePagina(detalle_servicio);
        lienzo.getChildren().add(detalle_servicio);
        
    }

    @FXML
    void Estancias(ActionEvent event) throws IOException {
        
        Pane estancias = FXMLLoader.load(getClass().getResource("/proyecto_hotel/interfaces/Estancias.fxml"));
        AjustePagina(estancias);
        lienzo.getChildren().add(estancias);
        
    }

    @FXML
    void Inicio(ActionEvent event) throws IOException {
        
        Pane inicio = FXMLLoader.load(getClass().getResource("/proyecto_hotel/interfaces/Inicio.fxml"));
        AjustePagina(inicio);
        lienzo.getChildren().add(inicio);
        
    }

    @FXML
    void Reservas(ActionEvent event) throws IOException {
        
        Pane reservas = FXMLLoader.load(getClass().getResource("/proyecto_hotel/interfaces/Reserva.fxml"));
        AjustePagina(reservas);
        lienzo.getChildren().add(reservas);
        
    }

    @FXML
    void Servicio_cuarto(ActionEvent event) throws IOException {
        
        Pane servicio_cuarto = FXMLLoader.load(getClass().getResource("/proyecto_hotel/interfaces/Servicio_cuarto.fxml"));
        AjustePagina(servicio_cuarto);
        lienzo.getChildren().add(servicio_cuarto);
        
    }
     
    
    void AjustePagina(Node n){
        AnchorPane.setLeftAnchor(n, 205.00);
        AnchorPane.setTopAnchor(n, 60.00);
        AnchorPane.setRightAnchor(n, 0.00);
        AnchorPane.setBottomAnchor(n, 0.00);
    }

    
    @FXML
    void Habitaciones(ActionEvent event) throws IOException {
        
        Pane Habitaciones = FXMLLoader.load(getClass().getResource("/proyecto_hotel/interfaces/Habitaciones.fxml"));
        AjustePagina(Habitaciones);
        lienzo.getChildren().add(Habitaciones); 
        
    }
    
    @FXML
    void Productos(ActionEvent event) throws IOException {
        
        Pane Productos = FXMLLoader.load(getClass().getResource("/proyecto_hotel/interfaces/Productos.fxml"));
        AjustePagina(Productos);
        lienzo.getChildren().add(Productos); 
        
    }
    
    String s;
    Format formatter;
    Date date = new Date();
    
    void Fecha_Hora(){
        
        formatter = new SimpleDateFormat("EEEE, dd MMMM yyyy");
        s = formatter.format(date); 
        labFecha.setText(s+" |");
        
        Timeline clock = new Timeline(new KeyFrame(Duration.ZERO, e -> {            
        Calendar cal = Calendar.getInstance();
        int segundos = cal.get(Calendar.SECOND);
        int minutos = cal.get(Calendar.MINUTE);
        int hora = cal.get(Calendar.HOUR);
        //System.out.println(hour + ":" + (minute) + ":" + second);
        if (segundos<10) {
            labHora.setText(" "+(hora)+":"+(minutos)+":0"+(segundos)+" |");    
        }else{
            labHora.setText(" "+(hora)+":"+(minutos)+":"+(segundos)+" ");
        }
        
    }),
         new KeyFrame(Duration.seconds(1))
    );
    clock.setCycleCount(Animation.INDEFINITE);
    clock.play();
    
        
    }
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        String d = "src\\proyecto_hotel\\imagenes\\";
        img_logo.setImage(new Image(new File(d+"logo.png").toURI().toString()));
        Fecha_Hora();
        
    }    

    
    
}
