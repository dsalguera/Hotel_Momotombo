/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyecto_hotel.interfaces;

import com.jfoenix.controls.*;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.nio.file.CopyOption;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TreeItemPropertyValueFactory;
import javafx.scene.image.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;
import javax.swing.JOptionPane;
import proyecto_hotel.clases.CustomThing;
import proyecto_hotel.clases.Habitaciones;
import proyecto_hotel.Conexion;
import proyecto_hotel.clases.Productos;
/**
 * FXML Controller class
 *
 * @author David Salguera
 */
public class ReservaController implements Initializable {
    
     @FXML
    private AnchorPane anchorpane;

    @FXML
    private ComboBox<?> combo_buscar;

    @FXML
    private JFXTextField txtbuscar;

    @FXML
    private JFXButton btnBuscar;

    @FXML
    private ListView<?> lista;

    @FXML
    private JFXToggleButton check_estado;

    @FXML
    private ImageView screen_img;

    @FXML
    private JFXTextField txtnombre;

    @FXML
    private JFXButton btnBuscar_habitacion;

    @FXML
    private JFXDatePicker fecha_inicio;

    @FXML
    private JFXDatePicker fecha_final;

    @FXML
    private JFXTextField txtdias;

    @FXML
    private JFXTextField txtcosto;

    @FXML
    private JFXButton btnNuevo;

    @FXML
    private JFXButton btnEditar;

    @FXML
    private JFXButton btnGuardar;

    @FXML
    private JFXButton btnEliminar;

    @FXML
    void Buscar_habitacion(ActionEvent event) throws SQLException {
        
        String nombre = txtnombre.getText();
        Conexion("Select * from Habitacion where Nombre = '"+nombre+"' and Estado = 1");
        
    }
    
    @FXML
    void Sugerencia(KeyEvent event) {
       
    }
    
    Image imagen;
    int id;
    
    Conexion c = new Conexion();
    Connection connection ;
    
    void Conexion(String query) throws SQLException{ 
        
        String dir = "src\\proyecto_hotel\\imagenes\\habitaciones\\";
        
        connection = (Connection) DriverManager.getConnection(c.getString_connection(), c.getUsername(), c.getPassword());
        Statement stm = (Statement) connection.createStatement();
        
        ResultSet rs = stm.executeQuery(query);
        
        while (rs.next()) {
                id = rs.getInt("Id_habitacion");
                String imagenS = rs.getString("imagen");
                imagen = new Image(new File(dir+imagenS).toURI().toString());
                
                screen_img.setImage(imagen);
        }
        
    }
    

    @FXML
    void Editar_Registro(ActionEvent event) {

    }

    @FXML
    void Eliminar_Registro(ActionEvent event) {

    }

    @FXML
    void Guardar_Registro(ActionEvent event) {

    }

    @FXML
    void Nuevo_Registro(ActionEvent event) {

    }

    @FXML
    void buscar(KeyEvent event) {

    }

    @FXML
    void click(MouseEvent event) {

    }


    @Override
    public void initialize(URL url, ResourceBundle rb) {
       
        txtdias.setEditable(false);
        ObservableList busquedas = FXCollections.observableArrayList();
        busquedas.add("Nombre");
        busquedas.add("Tipo_Producto");
        busquedas.add("Precio");
        combo_buscar.getItems().addAll(busquedas);

    }  
    
    
    
}
