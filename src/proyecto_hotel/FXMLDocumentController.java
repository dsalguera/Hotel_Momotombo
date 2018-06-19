/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyecto_hotel;

import com.jfoenix.controls.*;
import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javax.swing.JOptionPane;
import proyecto_hotel.clases.Habitaciones;
import proyecto_hotel.clases.Usuarios;
import proyecto_hotel.interfaces.HabitacionesController;
import proyecto_hotel.interfaces.MenuController;

/**
 *
 * @author David Salguera
 */
public class FXMLDocumentController implements Initializable {
    
    @FXML
    private AnchorPane lienzo;

    @FXML
    private JFXButton btnMinimizar;

    @FXML
    private JFXButton btnCerrar;

    @FXML
    private JFXTextField txtuser;

    @FXML
    private JFXPasswordField txtpass;

    @FXML
    private JFXButton btnIniciar_sesion;

    @FXML
    private JFXButton btnRegistrarse;
    
    double xOffset, yOffset;

    @FXML
    void Arrastrar(MouseEvent event) {
        lienzo.getScene().getWindow().setX(event.getScreenX() + xOffset);
        lienzo.getScene().getWindow().setY(event.getScreenY() + yOffset);
    }

    @FXML
    void Cerrar(ActionEvent event) {
        Platform.exit();
    }

    @FXML
    void Minimizar(ActionEvent event) {
        Stage stage = (Stage) ((Node)(event.getSource())).getScene().getWindow();
        stage.setIconified(true);
    }

    @FXML
    void Presionar(MouseEvent event) {
        xOffset = lienzo.getScene().getWindow().getX() - event.getScreenX();
        yOffset = lienzo.getScene().getWindow().getY() - event.getScreenY();
    }

    @FXML
    void Registrarse(ActionEvent event) {
        
    }
    
    @FXML
    void Iniciar_sesion(ActionEvent event) throws SQLException {
        Conexion(event);
    }
    
    Conexion c = new Conexion();
    Connection connection ;
    Usuarios u;
    
    void Siguiente(String nombre, int tipo, ActionEvent event) throws SQLException{
        
        try {
            
                    FXMLLoader fxmlLoader = new FXMLLoader();
                    fxmlLoader.setLocation(getClass().getResource("/proyecto_hotel/interfaces/Menu.fxml"));
                    
                    Stage stage = new Stage();
                    stage.initStyle(StageStyle.TRANSPARENT);
                    
                    Scene scene = new Scene(fxmlLoader.load(),Color.TRANSPARENT);
                    
                    MenuController menu = fxmlLoader.getController();
                    menu.setUser(nombre, tipo);
                    
                    stage.setTitle("Principal");
                    stage.setScene(scene);
                    stage.show();
                    
                    ((Node)(event.getSource())).getScene().getWindow().hide();

        } catch (IOException e) {
            
            JOptionPane.showMessageDialog(null,"Ocurrio un error al acceder!");
                        
        }
        
    }
    
    void Conexion(ActionEvent event) throws SQLException{ 
              
        String dir = "src\\proyecto_hotel\\imagenes\\usuarios\\";
        
        connection = (Connection) DriverManager.getConnection(c.getString_connection(), c.getUsername(), c.getPassword());
        Statement stm = (Statement) connection.createStatement();
        
        ResultSet rs = stm.executeQuery("select * from usuario where Nombre_usuario = '"+txtuser.getText()+"';");
                
        while (rs.next()) {
                String nombre = rs.getString("nombre_usuario");
                int tipo = rs.getInt("tipo_cuenta");
                int estado = rs.getInt("estado");
                String imagen = rs.getString("imagen");
                String contra = rs.getString("contrasena"); 
                
                if(txtuser.getText().toString().equals(nombre) && txtpass.getText().toString().equals(contra)){
                    
                    Siguiente(nombre,tipo,event);
                    
                }else{
                    
                } 
        }
         
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }    
    
    
}
