/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyecto_hotel.interfaces;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;
import java.io.File;
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
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.util.Callback;
import proyecto_hotel.Conexion;
import proyecto_hotel.clases.Habitaciones;
import proyecto_hotel.clases.ListaEstancia;

/**
 * FXML Controller class
 *
 * @author David Salguera
 */
public class Ventana_EstanciaController implements Initializable {

    @FXML
    private ComboBox<String> combo_buscar;

    @FXML
    private JFXTextField txtbuscar;

    @FXML
    private JFXButton btnBuscar;

    @FXML
    private ListView<ListaEstancia> lista_estancia;

    @FXML
    private JFXButton btnAceptar;

    @FXML
    private JFXButton btnCancelar;

    @FXML
    void Aceptar(ActionEvent event) {
         Servicio_CuartoController.id_estanciaC = lista_estancia.selectionModelProperty().getValue().getSelectedItem().getId_estancia();
        ((Node)(event.getSource())).getScene().getWindow().hide(); 
    }

    @FXML
    void Cancelar(ActionEvent event) {
        ((Node)(event.getSource())).getScene().getWindow().hide(); 
    }

    Conexion c = new Conexion();
    Connection connection ;
    ObservableList<ListaEstancia> data = FXCollections.observableArrayList();
    String dirCliente = "src\\proyecto_hotel\\imagenes\\clientes\\";
    String dirHabitaciones = "src\\proyecto_hotel\\imagenes\\habitaciones\\";
    
    void Conexion(String query) throws SQLException{ 
        
        connection = (Connection) DriverManager.getConnection(c.getString_connection(), c.getUsername(), c.getPassword());
        Statement stm = (Statement) connection.createStatement();
        
        ResultSet rs = stm.executeQuery(query);
        
        while (rs.next()) {
                int id_estancia = rs.getInt("Id_estancia");
                int id_cliente = rs.getInt("Id_cliente");
                int id_habitacion = rs.getInt("Id_habitacion");
                
                String fecha_inic = rs.getString("Fecha_inicio");
                String fecha_fin = rs.getString("Fecha_final");
                
                Double costo = rs.getDouble("costo_total");
                String descripcion = rs.getString("descripcion");
                String estado = rs.getString("estado");
                int id_reserva = rs.getInt("id_reserva");
                
                String imagenCliente = rs.getString("c.Imagen");
                String imagenHabitacion = rs.getString("h.Imagen");
                
                ListaEstancia estancia = new ListaEstancia(id_estancia, id_cliente, id_habitacion, 
                fecha_inic, fecha_fin, costo, descripcion, estado, id_reserva,
                        new Image(new File(dirCliente+imagenCliente).toURI().toString()), 
                        new Image(new File(dirHabitaciones+imagenHabitacion).toURI().toString()));
               
                data.add(estancia);
                
        }
        
    }
    
    void Crear_Lista(String query){
        
        lista_estancia.getItems().clear();
        data.clear();
        
        try {
            Conexion(query);
        } catch (SQLException ex) {
            Logger.getLogger(HabitacionesController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        lista_estancia.getItems().addAll(data);
        
        lista_estancia.setCellFactory(new Callback<ListView<ListaEstancia>, ListCell<ListaEstancia>>() {

        @Override
        public ListCell<ListaEstancia> call(ListView<ListaEstancia> arg0) {
        return new ListCell<ListaEstancia>() {

            @Override
            protected void updateItem(ListaEstancia item, boolean bln) {
            super.updateItem(item, bln);
            if (item != null) {
                            
            Label id_estancia,id_cliente,id_habitacion,
            Fecha_inicio,Fecha_final,Costo_total,
            Descripcion,Estado,id_reserva;
            ImageView imagen,imagen2;
            
            VBox vBox = new VBox(
                    
                id_estancia = new Label("Id Estancia: "+item.getId_estancia()+"   Id Cliente: "+item.getId_cliente()), 
                id_habitacion = new Label("Id Habitacion: "+item.getId_habitacion()+"   Id Reserva: "+item.getId_reserva()),
                Fecha_inicio = new Label("Fecha Inicio: "+item.getFecha_inicio()),
                Fecha_final = new Label("Fecha Final: "+item.getFecha_final()),
                Costo_total = new Label("Costo Total: "+item.getCosto_total()),
                Descripcion = new Label("Descripcion: "+item.getDescripcion()),
                Estado = new Label("Estado: "+item.getEstado()));
 
                    id_estancia.getStyleClass().add("espacio");
                    id_habitacion.getStyleClass().add("espacio");
                    Fecha_inicio.getStyleClass().add("espacio");
                    Fecha_final.getStyleClass().add("espacio");
                    Costo_total.getStyleClass().add("espacio");
                    Descripcion.getStyleClass().add("espacio");
                    Estado.getStyleClass().add("espacio");
                    
                    HBox hBox = new HBox(
                    imagen = new ImageView(item.getImagenCliente()),imagen2 = new ImageView(item.getImagenHabitacion()),vBox);
                    imagen.setFitHeight(166);
                    imagen.setFitWidth(250);
                    imagen2.setFitHeight(166);
                    imagen2.setFitWidth(250);
                    
                    hBox.setSpacing(10);
                    vBox.setSpacing(5);
                    setGraphic(hBox);
                            
                        }
                    }

                };
            }
        
            

        });
        
    }
    
    // Variables universales
    int id = 0;
    @FXML
    void click(MouseEvent event) {
        id = lista_estancia.getSelectionModel().getSelectedItem().getId_estancia();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        Crear_Lista("select * from Estancia e inner join Cliente c on e.Id_cliente = c.Id_cliente inner join Habitacion h " +
                    "on h.Id_habitacion = e.Id_habitacion");
        
        ObservableList busquedas = FXCollections.observableArrayList();
        busquedas.add("Fecha_Inicio");
        busquedas.add("Fecha_Final");
        busquedas.add("Costo_Total");
        busquedas.add("Numero_Reserva");
        busquedas.add("Numero_Estancia");
        busquedas.add("Cliente.Estado");
        combo_buscar.getItems().addAll(busquedas);
    }    
    
}
