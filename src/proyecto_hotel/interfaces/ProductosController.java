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
import java.util.Optional;
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
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TreeItemPropertyValueFactory;
import javafx.scene.image.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
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
public class ProductosController implements Initializable {
    
    @FXML
    private AnchorPane anchorpane;

    @FXML
    private ComboBox<String> combo_buscar;
    
    @FXML
    private HBox panel_edicion;

    @FXML
    private JFXTextField txtbuscar;

    @FXML
    private JFXButton btnBuscar;

    @FXML
    private ListView<Productos> lista_productos;

    @FXML
    private JFXToggleButton check_estado;

    @FXML
    private ImageView screen_img;

    @FXML
    private JFXButton btnCambiarImagen;

    @FXML
    private JFXTextField txtnombre;

    @FXML
    private JFXTextField txttipo;

    @FXML
    private JFXTextField txtprecio;
    
    @FXML
    private JFXTextField txtcantidad;

    @FXML
    private JFXButton btnNuevo;

    @FXML
    private JFXButton btnEditar;

    @FXML
    private JFXButton btnGuardar;

    @FXML
    private JFXButton btnEliminar;
    
    @FXML
    private HBox boxContenedor;
    
    @FXML
    private VBox boxCambios;

    //Importante: De aqui se obtiene la ruta de la imagen
    String nombre_img;
    int click = 0;
    
    @FXML
    void Cambiar_Imagen(ActionEvent event) throws IOException {
        
        Stage stage = (Stage) anchorpane.getScene().getWindow();
        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialDirectory(new File(dir));
        File file = fileChooser.showOpenDialog(stage);
            if (file != null) {
                
                //Se lee la imagen a cargar
                Image image = new Image(file.toURI().toString());
               
                //Se agarra el origen
                Path from = Paths.get(file.toURI());
                //Se obtiene el nombre de la imagen
                String name = file.getName();
                nombre_img = name;
                //Se agrega el destinatario
                Path to = Paths.get(dir+name);
                
                //Se hacen los metodos de copia
                CopyOption[] options = new CopyOption[]{
                StandardCopyOption.REPLACE_EXISTING,
                StandardCopyOption.COPY_ATTRIBUTES
                };
                
                //Se hace la copia con las opciones y las rutas
                Files.copy(from, to, options);
                
                //Se hace una nueva imagen con la direccion en donde se copio la nueva
                Image Nueva = new Image(new File(dir+name).toURI().toString());
                //Se agrega al imageview
                screen_img.setImage(Nueva);
                click = 1;
            }
    }
    
    @FXML
    void Editar_Registro(ActionEvent event) throws SQLException {
        
        String nombre, tipo, telefono, descripcion;
        double precio;
        int ident,cantidad;
        
        ident = id;
        nombre = txtnombre.getText().toString();
        tipo = txttipo.getText().toString();
        cantidad = Integer.parseInt(txtcantidad.getText().toString());
        precio = Double.parseDouble(txtprecio.getText().toString());
       
        Image imagenP = screen_img.getImage();
        
        int estado;
                
        if (check_estado.isSelected()) {
            estado = 1;
        }else{
            estado = 2;
        }
        
        Connection connection = (Connection) DriverManager.getConnection(c.getString_connection(), c.getUsername(), c.getPassword());
        Statement stm = (Statement) connection.createStatement();
        String query = null;
        
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("¿Confirmar Acción?");
        alert.setHeaderText("¿Está seguro que desea editar el registro "+nombre+" de C$"+precio+" de la base?");
        alert.setContentText("Se reescribirá la información con los datos introcucidos en los campos.\nPara Editar, presione aceptar.");
        
        ButtonType buttonTypeCancel = new ButtonType("Cancelar", ButtonBar.ButtonData.CANCEL_CLOSE);
        ButtonType buttonTypeOk = new ButtonType("Aceptar", ButtonBar.ButtonData.OK_DONE);
        
        alert.getButtonTypes().setAll(buttonTypeCancel,buttonTypeOk);

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == buttonTypeOk){
            // ... user chose OK
            data.add(new Productos(imagenP,id,nombre,precio,tipo,cantidad,estado));
        
            if (click == 1) {  
                query = "update Producto set Nombre = '"+nombre+"', Tipo_producto = '"+tipo+"', Precio = "+precio+" , "
                    + "Estado = "+estado+", Cantidad = "+cantidad+", Imagen = '"+nombre_img+"' " 
                    + "where Id_producto = "+id+";";
            }else{         
                query = "update Producto set Nombre = '"+nombre+"', Tipo_producto = '"+tipo+"', Precio = "+precio+" , "
                    + "Estado = "+estado+",  Cantidad = "+cantidad+" " 
                    + "where Id_producto = "+id+";";    
            }
            
            click = 0;
            stm.executeUpdate(query);
            Crear_Lista("select * from Producto where Eliminado = 0;");
            
            Dialogo("Se ha editado el registro.", "Exito al Editar!",
            "Operación Realizada", Alert.AlertType.CONFIRMATION);
            
        } else if(result.get() == buttonTypeCancel){
            // ... user chose CANCEL or closed the dialog
            alert.close();
        }
        
        
        
    }

    @FXML
    void Eliminar_Registro(ActionEvent event) throws SQLException {
        
        String nombre, tipo, telefono, descripcion;
        double precio;
        int ident,cantidad;
        
        ident = id;
        nombre = txtnombre.getText().toString();
        tipo = txttipo.getText().toString();
        cantidad = Integer.parseInt(txtcantidad.getText().toString());
        precio = Double.parseDouble(txtprecio.getText().toString());
       
        Image imagenP = screen_img.getImage();
        
        int estado;
                
        if (check_estado.isSelected()) {
            estado = 1;
        }else{
            estado = 2;
        }
        
        Connection connection = (Connection) DriverManager.getConnection(c.getString_connection(), c.getUsername(), c.getPassword());
        Statement stm = (Statement) connection.createStatement();
        String query = null;
        
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("¿Confirmar Acción?");
        alert.setHeaderText("¿Está seguro que desea eliminar el registro "+nombre+" de C$"+precio+" de la base?");
        alert.setContentText("Si lo elimina, no podrá acceder luego a este registro.");
        
        ButtonType buttonTypeCancel = new ButtonType("Cancelar", ButtonBar.ButtonData.CANCEL_CLOSE);
        ButtonType buttonTypeOk = new ButtonType("Aceptar", ButtonBar.ButtonData.OK_DONE);
        
        alert.getButtonTypes().setAll(buttonTypeCancel,buttonTypeOk);

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == buttonTypeOk){
            // ... user chose OK
            query = "update Producto set Eliminado = "+1+" "
                    +"where Id_producto = "+id+" and Nombre = '"+nombre+"';";
            click = 0;
            stm.executeUpdate(query);
            Crear_Lista("select * from Producto where Eliminado = 0;");
            
            Dialogo("Se ha eliminado el registro.", "Exito al Eliminar!",
            "Operación Realizada", Alert.AlertType.CONFIRMATION);
            
        } else if(result.get() == buttonTypeCancel){
            // ... user chose CANCEL or closed the dialog
            alert.close();
        }

    }

    @FXML
    void Guardar_Registro(ActionEvent event) throws SQLException {
        
        if (Valida() == true) {
            
            String nombre, tipo, telefono, descripcion;
            int cantidad;
            double precio;

            nombre = txtnombre.getText().toString();
            tipo = txttipo.getText().toString();
            precio = Double.parseDouble(txtprecio.getText().toString());
            cantidad = Integer.parseInt(txtcantidad.getText().toString());

            Image imagen = screen_img.getImage();
            String dir = imagen.impl_getUrl();

            int estado;

            if (check_estado.isSelected()) {
                estado = 1;
            }else{
                estado = 2;
            }

            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("¿Confirmar Acción?");
            alert.setHeaderText("¿Está seguro que desea guardar el registro "+nombre+" de C$"+precio+" a la base?");
            alert.setContentText("Se guardará la información con los datos introcucidos en los campos.\nPara Guardar, presione aceptar.");

            ButtonType buttonTypeCancel = new ButtonType("Cancelar", ButtonBar.ButtonData.CANCEL_CLOSE);
            ButtonType buttonTypeOk = new ButtonType("Aceptar", ButtonBar.ButtonData.OK_DONE);

            alert.getButtonTypes().setAll(buttonTypeCancel,buttonTypeOk);

            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == buttonTypeOk){
                // ... user chose OK
                data.add(new Productos(imagen,id,nombre,precio,tipo,cantidad,estado));

                Connection connection = (Connection) DriverManager.getConnection(c.getString_connection(), c.getUsername(), c.getPassword());
                Statement stm = (Statement) connection.createStatement();

                String query = "insert into Producto (Nombre, Tipo_producto, Precio, Estado,Cantidad,Imagen,Eliminado) " +
                "values ('"+nombre+"','"+tipo+"',"+precio+","+estado+","+cantidad+",'"+nombre_img+"',0);";

                stm.executeUpdate(query);

                Crear_Lista("select * from Producto where Eliminado = 0;");
                
                Dialogo("Se ha guardado el registro.", "Exito al Guardar!",
                "Operación Realizada", Alert.AlertType.CONFIRMATION);
                
                Nuevo();

            } else if(result.get() == buttonTypeCancel){
                // ... user chose CANCEL or closed the dialog
                alert.close();
            }
        }
        
    }

    @FXML
    void Nuevo_Registro(ActionEvent event) {
        
        Nuevo();
        
        txtnombre.setText("");
        txttipo.setText("");
        check_estado.setSelected(true);
        screen_img.setImage(null);
        txtprecio.setText("");
        txtcantidad.setText("");
        
    }
    
    String d = "src\\proyecto_hotel\\imagenes\\login\\";
        
    void Nuevo(){
        screen_img.setImage(new Image(new File(d+"no_image.jpg").toURI().toString()));
        btnCambiarImagen.setText("Seleccionar Imagen");
        btnNuevo.setDisable(false);
        btnGuardar.setDisable(false);
        btnEditar.setDisable(true);
        btnEliminar.setDisable(true);
    }
    
    void Editar(){
        btnCambiarImagen.setText("Cambiar Imagen");
        btnNuevo.setDisable(false);
        btnGuardar.setDisable(true);
        btnEditar.setDisable(false);
        btnEliminar.setDisable(false);
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

    
    int id = 0;
    
    @FXML
    void click(MouseEvent event) {
        
        Editar();
        
        id = lista_productos.getSelectionModel().getSelectedItem().getId();
        String nombre = lista_productos.getSelectionModel().getSelectedItem().getNombre();
        String tipo = lista_productos.getSelectionModel().getSelectedItem().getTipo();
        int estado = lista_productos.getSelectionModel().getSelectedItem().getEstado();
        int cantidad = lista_productos.getSelectionModel().getSelectedItem().getCantidad();
        Image imagen = lista_productos.getSelectionModel().getSelectedItem().getImagen();
        double precio = lista_productos.getSelectionModel().getSelectedItem().getPrecio();
        
        txtnombre.setText(nombre);
        txttipo.setText(tipo);
        txtcantidad.setText(""+cantidad);
        if (estado==1) {
            check_estado.setSelected(true);
            check_estado.setText("Activo");
        }else{
            check_estado.setSelected(false);
            check_estado.setText("Inactivo");
        }
        screen_img.setImage(imagen);
        txtprecio.setText(""+precio);
        
    }
    
    Conexion c = new Conexion();
    Connection connection ;
    ObservableList<Productos> data = FXCollections.observableArrayList();
    String dir = "src\\proyecto_hotel\\imagenes\\productos\\";
    
    void Conexion(String query) throws SQLException{ 
        
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
        
    }
    
    boolean Valida(){
        if (txtnombre.getText().equals("") || txttipo.getText().equals("")
                || txtprecio.getText().equals("") || txtcantidad.getText().equals("")) {
            
            Dialogo("Al parecer hay algunos campos que necesitan ser rellenados.", "¡Necesita rellenar todos los campos!",
                    "Error", Alert.AlertType.ERROR);
            
        }else{
            
            if (screen_img.getImage() == null) {
                Dialogo("Al parecer necesita agregar una imagen.", "¡No imagen!",
                    "Error", Alert.AlertType.ERROR);
            }else{
            
                return true;
                
            }
        }
        
        return false;
    }
    
    void Dialogo(String mensaje, String cabecera, String titulo, Alert.AlertType e){
        
        Alert alert = new Alert(e);
        alert.setTitle(titulo);
        alert.setHeaderText(cabecera);
        alert.setContentText(mensaje);
        
        ButtonType buttonTypeOk = new ButtonType("Aceptar", ButtonBar.ButtonData.OK_DONE);
        
        alert.getButtonTypes().setAll(buttonTypeOk);
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == buttonTypeOk){
                alert.close();
        }
        
    }
    
    
    void Crear_Lista(String query){
        
        lista_productos.getItems().clear();
        data.clear();
        
        try {
            Conexion(query);
        } catch (SQLException ex) {
            Logger.getLogger(HabitacionesController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
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
    
    int Tipo_usuario_copia;
    public void Botones(int type){
        
        if (type == 2) {
            panel_edicion.getChildren().remove(btnEditar);
            panel_edicion.getChildren().remove(btnEliminar);
        }else if (type == 3) {
            boxContenedor.getChildren().remove(boxCambios);
        }
        
    }


    @Override
    public void initialize(URL url, ResourceBundle rb) {
       
        Tipo_usuario_copia = MenuController.tipo_usuario;
        Botones(Tipo_usuario_copia);
        
        ObservableList busquedas = FXCollections.observableArrayList();
        busquedas.add("Nombre");
        busquedas.add("Tipo_Producto");
        busquedas.add("Precio");
        busquedas.add("Cantidad");
        
        combo_buscar.getItems().addAll(busquedas);
        
        Crear_Lista("select * from Producto where Eliminado = 0;");
        
        Nuevo();

    }  
    
   
    
}
