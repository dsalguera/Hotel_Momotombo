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
import proyecto_hotel.clases.Productos;

/**
 * FXML Controller class
 *
 * @author David Salguera
 */
public class Ventana_ProductosController implements Initializable {

    @FXML
    private ComboBox<?> combo_buscar;

    @FXML
    private JFXTextField txtbuscar;

    @FXML
    private JFXButton btnBuscar;

    @FXML
    private ListView<Productos> lista_productos;

    @FXML
    private JFXButton btnAceptar;

    @FXML
    private JFXButton btnCancelar;

    @FXML
    void Aceptar(ActionEvent event) {
        Servicio_CuartoController.id_producto=lista_productos.selectionModelProperty().getValue().getSelectedItem().getId();
        Servicio_CuartoController.precio_producto=lista_productos.selectionModelProperty().getValue().getSelectedItem().getPrecio();
        Servicio_CuartoController.imagen_producto=lista_productos.selectionModelProperty().getValue().getSelectedItem().getImagen();
        Servicio_CuartoController.cantidad=lista_productos.selectionModelProperty().getValue().getSelectedItem().getCantidad();
        Servicio_CuartoController.nombre_producto=lista_productos.selectionModelProperty().getValue().getSelectedItem().getNombre();
        Servicio_CuartoController.tipo_producto=lista_productos.selectionModelProperty().getValue().getSelectedItem().getTipo();
        Servicio_CuartoController.estadoP=lista_productos.selectionModelProperty().getValue().getSelectedItem().getEstado();
        
        ((Node)(event.getSource())).getScene().getWindow().hide(); 
    }

    @FXML
    void Cancelar(ActionEvent event) {
        ((Node)(event.getSource())).getScene().getWindow().hide(); 
    }
    
    Conexion c = new Conexion();
    Connection connection ;
    ObservableList<Productos> data = FXCollections.observableArrayList();
    String dir = "src\\proyecto_hotel\\imagenes\\productos\\";
    
    static String nombre_habitacion_copia;
    
    void Conexion(String query) { 
        
        try {
            connection = (Connection) DriverManager.getConnection(c.getString_connection(), c.getUsername(), c.getPassword());
            Statement stm = (Statement) connection.createStatement();

            ResultSet rs = stm.executeQuery(query);

             while (rs.next()) {
                    int id = rs.getInt("Id_producto");
                    String nombre = rs.getString("nombre");
                    String tipo = rs.getString("tipo_producto");
                    double precio = rs.getDouble("precio");
                    int estado = rs.getInt("estado");
                    int cantidad = rs.getInt("cantidad");
                    String imagen = rs.getString("imagen");

                    Productos producto = new Productos(

                    new Image(new File(dir+imagen).toURI().toString()),id,nombre, precio, tipo,cantidad,estado);
                    data.add(producto);
            }
        } catch (SQLException ex) {
            System.out.println("Invalida busqueda");
        }
        
    }
    
    void Crear_Lista(String query){
        
        lista_productos.getItems().clear();
        data.clear();
        
        Conexion(query);
        
        lista_productos.getItems().addAll(data);
        
        lista_productos.setCellFactory(new Callback<ListView<Productos>, ListCell<Productos>>() {

        @Override
        public ListCell<Productos> call(ListView<Productos> arg0) {
        return new ListCell<Productos>() {

            @Override
            protected void updateItem(Productos item, boolean bln) {
            super.updateItem(item, bln);
            if (item != null) {
                            
            String estado = "";
                            
            if(item.getEstado() == 1 && item.getCantidad() > 0){
                estado = "Producto Disponible | Circulando";
            }else if(item.getEstado() == 0 && item.getCantidad() > 0){
                estado = "Producto Disponible | No Circulando";
            }else if(item.getEstado() == 1 && item.getCantidad() <= 0){
                estado = "Producto Agotado | Circulando";
            }
            
            Label Nombre,Tipo,Precio,Estado,Cantidad;
            ImageView imagen;
            
            VBox vBox = new VBox(
                    
                Nombre = new Label("Nombre: "+item.getNombre()), 
                Tipo = new Label("Tipo: "+item.getTipo()), 
                Precio = new Label("Precio: $ "+item.getPrecio()),
                Cantidad = new Label("Cantidad: "+item.getCantidad()),
                Estado = new Label(""+estado)     
                );
             
                    Nombre.getStyleClass().add("espacio");
                    Tipo.getStyleClass().add("espacio");
                    Precio.getStyleClass().add("espacio");
                    
                    if (estado == "Producto Disponible | Circulando") {
                        Estado.getStyleClass().add("round-green");
                    }else if(estado == "Producto Disponible | No Circulando"){
                        Estado.getStyleClass().add("round-yellow");
                    }else if(estado == "Producto Agotado | Circulando"){
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

    int id = 0;
    
    @FXML
    void click(MouseEvent event) {
        id = lista_productos.getSelectionModel().getSelectedItem().getId();
        String nombre = lista_productos.getSelectionModel().getSelectedItem().getNombre();
        String tipo = lista_productos.getSelectionModel().getSelectedItem().getTipo();
        int estado = lista_productos.getSelectionModel().getSelectedItem().getEstado();
        int cantidad = lista_productos.getSelectionModel().getSelectedItem().getCantidad();
        Image imagen = lista_productos.getSelectionModel().getSelectedItem().getImagen();
        double precio = lista_productos.getSelectionModel().getSelectedItem().getPrecio();
    }
    
    void consulta() throws SQLException {
    
        Connection connection = (Connection) DriverManager.getConnection(c.getString_connection(), c.getUsername(), c.getPassword());
        Statement stm = (Statement) connection.createStatement();
        
        String busq = txtbuscar.getText();
        String filtro = (String) combo_buscar.getSelectionModel().getSelectedItem();
        String query = "select * from Producto where "+filtro+" like '%"+(busq)+"%' and Eliminado = 0";
        
        ResultSet rs = stm.executeQuery(query);
        
         while (rs.next()) {
                int id = rs.getInt("Id_producto");
                String nombre = rs.getString("nombre");
                String tipo = rs.getString("tipo_producto");
                double precio = rs.getDouble("precio");
                int estado = rs.getInt("estado");
                int cantidad = rs.getInt("cantidad");
                String imagen = rs.getString("imagen");
                
                Productos producto = new Productos(
                        
                new Image(new File(dir+imagen).toURI().toString()),id,nombre, precio, tipo,cantidad,estado);
                data.add(producto);
        }
         
        Crear_Lista(query);
     
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        ObservableList busquedas = FXCollections.observableArrayList();
        
        busquedas.add("Nombre");
        busquedas.add("Tipo_Producto");
        busquedas.add("Precio");
        busquedas.add("Cantidad");
        
        combo_buscar.getItems().addAll(busquedas);
        
        Crear_Lista("select * from Producto where Eliminado = 0;");
        
        txtbuscar.setOnKeyTyped(event -> {
            try {
                consulta();
            } catch (SQLException ex) {
                Logger.getLogger(Ventana_ProductosController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }    
    
}
