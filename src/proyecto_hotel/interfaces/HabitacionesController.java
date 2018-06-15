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
import java.util.stream.IntStream;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.*;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TreeItemPropertyValueFactory;
import javafx.scene.image.*;
import javafx.scene.input.InputMethodEvent;
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
/**
 * FXML Controller class
 *
 * @author David Salguera
 */
public class HabitacionesController implements Initializable {

    @FXML
    private AnchorPane anchorpane;
    
    @FXML
    private ComboBox<String> combo_buscar;

    @FXML
    private JFXTextField txtbuscar;
    
    @FXML
    private JFXButton btnBuscar;
     
    @FXML
    private ListView<Habitaciones> lista_habitaciones;
   
    @FXML
    private ImageView screen_img;

    @FXML
    private JFXButton btnCambiarImagen;

    @FXML
    private JFXTextField txtnombre;

    @FXML
    private JFXTextField txttipo;

    @FXML
    private JFXTextField txttarifa;

    @FXML
    private JFXTextField txttelefono;

    @FXML
    private JFXTextField txtdesc;
    
    @FXML
    private JFXButton btnNuevo;

    @FXML
    private JFXButton btnEditar;

    @FXML
    private JFXButton btnGuardar;

    @FXML
    private JFXButton btnEliminar;
    
    @FXML
    private JFXToggleButton check_estado;
    
    // Variables universales
    String nombre, tipo, telefono, descripcion;
    int estado,id;
    Image imagen;
    double tarifa; 
     
    @FXML
    void click(MouseEvent event) {
        
        Editar();
        
        id = lista_habitaciones.getSelectionModel().getSelectedItem().getId();
        nombre = lista_habitaciones.getSelectionModel().getSelectedItem().getNombre();
        tipo = lista_habitaciones.getSelectionModel().getSelectedItem().getTipo();
        estado = lista_habitaciones.getSelectionModel().getSelectedItem().getEstado();
        imagen = lista_habitaciones.getSelectionModel().getSelectedItem().getImagen();
        telefono = lista_habitaciones.getSelectionModel().getSelectedItem().getTelefono();
        tarifa = lista_habitaciones.getSelectionModel().getSelectedItem().getTarifa();
        descripcion = lista_habitaciones.getSelectionModel().getSelectedItem().getDescripcion();
        
        txtnombre.setText(nombre);
        txttipo.setText(tipo);
        if (estado==1) {
            check_estado.setSelected(true);
        }else{
            check_estado.setSelected(false);
        }
        
        screen_img.setImage(imagen);
        txttelefono.setText(telefono);
        txttarifa.setText(""+tarifa);
        txtdesc.setText(descripcion);
        
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
        String query = "select * from Habitacion where "+filtro+" like '%"+(busq)+"%'";
        
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
        double tarifa;
        int ident;
        
        ident = id;
        nombre = txtnombre.getText().toString();
        tipo = txttipo.getText().toString();
        tarifa = Double.parseDouble(txttarifa.getText().toString());
        telefono = txttelefono.getText().toString();
        descripcion = txtdesc.getText().toString();
        Image imagenP = screen_img.getImage();
        
        int estado;
                
        if (check_estado.isSelected()) {
            estado = 1;
        }else{
            estado = 2;
        }
        
        data.add(new Habitaciones(imagenP,id,nombre,tipo,tarifa,telefono,estado,descripcion));
        
        Connection connection = (Connection) DriverManager.getConnection(c.getString_connection(), c.getUsername(), c.getPassword());
        Statement stm = (Statement) connection.createStatement();
        String query = null;
        
        if (click == 1) {
            
            query = "update Habitacion set Nombre = '"+nombre+"', Tipo = '"+tipo+"', Tarifa = "+tarifa+" , "
                    + "Telefono = '"+telefono+"' , Estado = "+estado+", Descripcion = '"+descripcion+"' ,Imagen = '"+nombre_img+"' " 
                    + "where Id_habitacion = "+id+";";

            
        }else{
            
            query = "update Habitacion set Nombre = '"+nombre+"', Tipo = '"+tipo+"', Tarifa = "+tarifa+" , "
                    + "Telefono = '"+telefono+"' , Estado = "+estado+", Descripcion = '"+descripcion+"' " 
                    + "where Id_habitacion = "+id+";";
            
        }
        
        click = 0;
        stm.executeUpdate(query);
        Crear_Lista("select * from habitacion;");
    }

    @FXML
    void Eliminar_Registro(ActionEvent event) {

    }
    
    @FXML
    void Nuevo_Registro(ActionEvent event) {
        
        Nuevo();
        
        txtnombre.setText("");
        txttipo.setText("");
        check_estado.setSelected(true);
        screen_img.setImage(null);
        txttelefono.setText("");
        txttarifa.setText("");
        txtdesc.setText("");
    }

    @FXML
    void Guardar_Registro(ActionEvent event) throws SQLException {
        
        String nombre, tipo, telefono, descripcion;
        double tarifa;
        
        nombre = txtnombre.getText().toString();
        tipo = txttipo.getText().toString();
        tarifa = Double.parseDouble(txttarifa.getText().toString());
        telefono = txttelefono.getText().toString();
        descripcion = txtdesc.getText().toString();
        
        Image imagen = screen_img.getImage();
        String dir = imagen.impl_getUrl();
        
        int estado;
        
        if (check_estado.isSelected()) {
            estado = 1;
        }else{
            estado = 2;
        }
        
        data.add(new Habitaciones(imagen,id,nombre,tipo,tarifa,telefono,estado,descripcion));
        
        Connection connection = (Connection) DriverManager.getConnection(c.getString_connection(), c.getUsername(), c.getPassword());
        Statement stm = (Statement) connection.createStatement();
        
        String query = "insert into Habitacion (Nombre, Tipo, Tarifa, Telefono, Estado, Descripcion,Imagen) " +
        "values ('"+nombre+"','"+tipo+"',"+tarifa+",'"+telefono+"',"+estado+",'"+descripcion+"','"+nombre_img+"');";
        
        stm.executeUpdate(query);
        
        Crear_Lista("select * from habitacion;");
        
    }
    
    void Nuevo(){
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
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        ObservableList busquedas = FXCollections.observableArrayList();
        busquedas.add("Nombre");
        busquedas.add("Tipo");
        busquedas.add("Tarifa");
        
        combo_buscar.getItems().addAll(busquedas);
        
        Crear_Lista("select * from habitacion;");
        
        Nuevo();

    }  
    
   
    
}
