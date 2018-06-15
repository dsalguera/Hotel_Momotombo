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
import java.io.IOException;
import java.net.URL;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.*;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;
import proyecto_hotel.clases.Habitaciones;
import proyecto_hotel.Conexion;

/**
 * FXML Controller class
 *
 * @author David Salguera
 */
public class Ventana_HabitacionesController implements Initializable {

    @FXML
    private ComboBox<String> combo_buscar;

    @FXML
    private JFXTextField txtbuscar;

    @FXML
    private JFXButton btnBuscar;

    @FXML
    private ListView<Habitaciones> lista_habitaciones;

    @FXML
    private JFXButton btnAceptar;

    @FXML
    private JFXButton btnCancelar;

    @FXML
    void Aceptar(ActionEvent event) throws SQLException {
        
    }

    @FXML
    void Cancelar(ActionEvent event) {

    }
    
    Conexion c = new Conexion();
    Connection connection ;
    ObservableList<Habitaciones> data = FXCollections.observableArrayList();
    String dir = "src\\proyecto_hotel\\imagenes\\habitaciones\\";
    
    void Conexion(String query) throws SQLException{ 
        
        connection = (Connection) DriverManager.getConnection(c.getString_connection(), c.getUsername(), c.getPassword());
        Statement stm = (Statement) connection.createStatement();
        
        ResultSet rs = stm.executeQuery(query);
        
        while (rs.next()) {
                int id = rs.getInt("Id_habitacion");
                String nombre = rs.getString("nombre");
                String tipo = rs.getString("tipo");
                double tarifa = rs.getDouble("tarifa");
                String telefono = rs.getString("telefono");
                int estado = rs.getInt("estado");
                String descripcion = rs.getString("descripcion");
                String imagen = rs.getString("imagen");
                
                Habitaciones habitacion = new Habitaciones(
                        
                new Image(new File(dir+imagen).toURI().toString()),id,nombre, tipo, tarifa,telefono,estado,descripcion);
                data.add(habitacion);
        }
        
    }
    
    

    @FXML
    void buscar(KeyEvent event) throws SQLException {
        consulta();
    }
    
    @FXML
    void consulta() throws SQLException {
    
        Connection connection = (Connection) DriverManager.getConnection(c.getString_connection(), c.getUsername(), c.getPassword());
        Statement stm = (Statement) connection.createStatement();
        
        String busq = txtbuscar.getText();
        String filtro = combo_buscar.getSelectionModel().getSelectedItem();
        String query = "select * from Habitacion where "+filtro+" like '%"+(busq)+"%' and Estado = 1";
        
        ResultSet rs = stm.executeQuery(query);
        
        while (rs.next()) {
                int id = rs.getInt("Id_habitacion");
                String nombre = rs.getString("nombre");
                String tipo = rs.getString("tipo");
                double tarifa = rs.getDouble("tarifa");
                String telefono = rs.getString("telefono");
                int estado = rs.getInt("estado");
                String descripcion = rs.getString("descripcion");
                String imagen = rs.getString("imagen");
                
                Habitaciones habitacion = new Habitaciones(
                        
                new Image(new File(dir+imagen).toURI().toString()),id,nombre, tipo, tarifa,telefono,estado,descripcion);
                data.add(habitacion);
        }
        Crear_Lista(query);
     
    }

    // Variables universales
    String habitacion, tipo, telefono, descripcion;
    int estado,id;
    Image imagen;
    double tarifa; 
    
    @FXML
    void click(MouseEvent event) throws SQLException {
        id = lista_habitaciones.getSelectionModel().getSelectedItem().getId();
        habitacion = lista_habitaciones.getSelectionModel().getSelectedItem().getNombre();
        tipo = lista_habitaciones.getSelectionModel().getSelectedItem().getTipo();
        estado = lista_habitaciones.getSelectionModel().getSelectedItem().getEstado();
        imagen = lista_habitaciones.getSelectionModel().getSelectedItem().getImagen();
        telefono = lista_habitaciones.getSelectionModel().getSelectedItem().getTelefono();
        tarifa = lista_habitaciones.getSelectionModel().getSelectedItem().getTarifa();
        descripcion = lista_habitaciones.getSelectionModel().getSelectedItem().getDescripcion();
        
        
    }
    
    void Crear_Lista(String query){
        
        lista_habitaciones.getItems().clear();
        data.clear();
        
        try {
            Conexion(query);
        } catch (SQLException ex) {
            Logger.getLogger(HabitacionesController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        lista_habitaciones.getItems().addAll(data);
        
        lista_habitaciones.setCellFactory(new Callback<ListView<Habitaciones>, ListCell<Habitaciones>>() {

        @Override
        public ListCell<Habitaciones> call(ListView<Habitaciones> arg0) {
        return new ListCell<Habitaciones>() {

            @Override
            protected void updateItem(Habitaciones item, boolean bln) {
            super.updateItem(item, bln);
            if (item != null) {
                            
            String estado = "";
                            
            if(item.getEstado()==1){
                estado = "   Disponible   ";
            }else{
                estado = "   Ocupado   ";
            }
            
            Label Nombre,Tipo,Tarifa,Telefono,Estado,Descripcion;
            ImageView imagen;
            
            VBox vBox = new VBox(
                    
                Nombre = new Label("Nombre: "+item.getNombre()), 
                Tipo = new Label("Tipo: "+item.getTipo()), 
                Tarifa = new Label("Tarifa: $ "+item.getTarifa().toString()+" al mes."),
                Telefono = new Label("Teléfono: "+item.getTelefono()),
                Descripcion = new Label("Descripción: "+item.getDescripcion()),
                Estado = new Label(""+estado)     
                );
             
                    Nombre.getStyleClass().add("espacio");
                    Tipo.getStyleClass().add("espacio");
                    Tarifa.getStyleClass().add("espacio");
                    Telefono.getStyleClass().add("espacio");
                    Descripcion.getStyleClass().add("espacio");
                    
                    if (estado == "   Disponible   ") {
                        Estado.getStyleClass().add("round-green");
                    }else{
                        Estado.getStyleClass().add("round-red");
                    }
                                               
                    HBox hBox = new HBox(
                            
                    imagen = new ImageView(item.getImagen()), vBox);
                    imagen.setFitHeight(166);
                    imagen.setFitWidth(250);
                    
                    hBox.setSpacing(10);
                    vBox.setSpacing(5);
                    setGraphic(hBox);
                            
                        }
                    }

                };
            }
        
            

        });
        
    }
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        ObservableList busquedas = FXCollections.observableArrayList();
        busquedas.add("Nombre");
        busquedas.add("Tipo");
        busquedas.add("Tarifa");
        
        combo_buscar.getItems().addAll(busquedas);
        
        Crear_Lista("select * from habitacion where Estado = 1;");
    }    
    
}
