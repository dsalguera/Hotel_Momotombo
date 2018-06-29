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
import java.nio.file.CopyOption;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.image.*;
import javafx.scene.input.InputMethodEvent;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import proyecto_hotel.Conexion;
import proyecto_hotel.clases.Clientes;

/**
 * FXML Controller class
 *
 * @author David Salguera
 */
public class PerfilClienteController implements Initializable {

    @FXML
    private AnchorPane anchorpane;

    @FXML
    private VBox boxCambios;

    @FXML
    private HBox box_estancia_reserva;

    @FXML
    private ImageView screen_img;

    @FXML
    private JFXButton btnCambiarImagen;

    @FXML
    private JFXTextField txtp_nombre;

    @FXML
    private JFXTextField txts_nombre;

    @FXML
    private JFXTextField txtp_apellido;

    @FXML
    private JFXTextField txts_apellido;

    @FXML
    private JFXTextField txtidentificacion;

    @FXML
    private ComboBox<String> combo_tipo;

    @FXML
    private JFXDatePicker fecha_inscripcion;

    @FXML
    private JFXDatePicker fechanac;

    @FXML
    private JFXTextField txtpais;

    @FXML
    private JFXTextField txtnombreuser;

    @FXML
    private JFXPasswordField txtpassword;

    @FXML
    private JFXPasswordField txtpassword2;

    @FXML
    private JFXTextField txtcorreo;

    @FXML
    private JFXTextField txttelefono;

    @FXML
    private HBox panel_edicion;

    @FXML
    private HBox panel_edicion1;

    @FXML
    private HBox panel_edicion11;

    @FXML
    private HBox panel_edicion2;

    @FXML
    private JFXButton btnEditar;

    
    @FXML
    private JFXButton btnEliminar;
    
    // Variables universales
    int id = 0;
    Image imagen;

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

    String pNombre, pApellido;
    String oldUser;
    String vigente;
    
    @FXML
    void Editar_Registro(ActionEvent event) {
        
        try {
            String primerNombre = txtp_nombre.getText().toString();
            String segundoNombre = txts_nombre.getText().toString();
            String primerApellido = txtp_apellido.getText().toString();
            String segundoApellido = txts_apellido.getText().toString();

            String identificacion = txtidentificacion.getText().toString();
            String tipo = combo_tipo.getSelectionModel().getSelectedItem().toString();
            String pais = txtpais.getText().toString();

            String fecha_inscripcion = this.fecha_inscripcion.getValue().toString();
            String fecha_nacimiento = this.fechanac.getValue().toString();
            
            
            int[] nacimiento = Arrays.stream(fecha_nacimiento.trim().split("-"))
                    .mapToInt(Integer::parseInt)
                    .toArray();
            
            int[] inscripcion = Arrays.stream(fecha_inscripcion.trim().split("-"))
                    .mapToInt(Integer::parseInt)
                    .toArray();
            
            
            fecha_nacimiento = (""+nacimiento[0]+"-"+nacimiento[1]+"-"+nacimiento[2]);
            fecha_inscripcion = (""+inscripcion[0]+"-"+inscripcion[1]+"-"+inscripcion[2]);
            
            
            String telefono = txttelefono.getText().toString();
            String correo = txtcorreo.getText().toString();

            Image imagen = screen_img.getImage();

            Connection connection = (Connection) DriverManager.getConnection(c.getString_connection(), c.getUsername(), c.getPassword());
            Statement stm = (Statement) connection.createStatement();
            String query = null;

            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("¿Confirmar Acción?");
            alert.setHeaderText("¿Está seguro que desea editar el cliente "+pNombre+" "+pApellido+" de la base?");
            alert.setContentText("Se reescribirá la información con los datos introcucidos en los campos.\nPara Editar, presione aceptar.");

            ButtonType buttonTypeCancel = new ButtonType("Cancelar", ButtonBar.ButtonData.CANCEL_CLOSE);
            ButtonType buttonTypeOk = new ButtonType("Aceptar", ButtonBar.ButtonData.OK_DONE);

            alert.getButtonTypes().setAll(buttonTypeCancel,buttonTypeOk);

            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == buttonTypeOk){
                // ... user chose OK

                if (click == 1) {
                    
                    query = "Update Cliente set Primer_nombre = '"+primerNombre+"',"
                            +"Segundo_nombre = '"+segundoNombre+"',"
                            +"Primer_apellido = '"+primerApellido+"',"
                            +"Segundo_apellido = '"+segundoApellido+"',"
                            +"Identificacion = '"+identificacion+"',"
                            +"Tipo_identificacion = '"+tipo+"',"
                            +"Pais_origen = '"+pais+"',"
                            +"Fecha_inscripcion = '"+fecha_inscripcion+"',"
                            +"Fecha_nacimiento = '"+fecha_nacimiento+"',"
                            +"Telefono = '"+telefono+"',"
                            +"Correo = '"+correo+"',"
                            +"Imagen = '"+nombre_img+"' where Id_cliente = "+id+";";
                    
                }else{
                    
                    query = "Update Cliente set Primer_nombre = '"+primerNombre+"',"
                            +"Segundo_nombre = '"+segundoNombre+"',"
                            +"Primer_apellido = '"+primerApellido+"',"
                            +"Segundo_apellido = '"+segundoApellido+"',"
                            +"Identificacion = '"+identificacion+"',"
                            +"Tipo_identificacion = '"+tipo+"',"
                            +"Pais_origen = '"+pais+"',"
                            +"Fecha_inscripcion = '"+fecha_inscripcion+"',"
                            +"Fecha_nacimiento = '"+fecha_nacimiento+"',"
                            +"Telefono = '"+telefono+"',"
                            +"Correo = '"+correo+"' "
                            +"where Id_cliente = "+id+";";
                    
                }
                
                stm.executeUpdate(query);

                if (Usuario_existe(txtnombreuser.getText()) == true) {
                    Dialogo("Ocurrio un error al editar el registro. Al parecer el usuario ya existe.", "¡Error al Editar!",
                    "Error", Alert.AlertType.ERROR);
                }else{
                    
                    if (click == 1) {
                        
                        query = "update Usuario set Nombre_usuario = '"+txtnombreuser.getText()+"',"
                            + "Contrasena = '"+txtpassword.getText()+"',"
                            + "Imagen = '"+nombre_img+"'"
                            + " where Id_cliente = "+id+";";
                        
                        
                
                    }else{
                        
                        query = "update Usuario set Nombre_usuario = '"+txtnombreuser.getText()+"',"
                            + "Contrasena = '"+txtpassword.getText()+"'"
                            + " where Id_cliente = "+id+";";
                                               
                    }
                    
                    stm.executeUpdate(query);
                    stm.close();
                    
                    click = 0;
                
                    Dialogo("Se ha editado el registro.", "Exito al Editar!",
                        "Operación Realizada", Alert.AlertType.CONFIRMATION);
                    
                }
                
            } else if(result.get() == buttonTypeCancel){
                // ... user chose CANCEL or closed the dialog
                alert.close();
            }
        } catch (SQLException ex) {
            Logger.getLogger(PerfilClienteController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    @FXML
    void Eliminar_Registro(ActionEvent event) {
        
    }

    boolean Usuario_existe(String usuario) throws SQLException{
        
            String dir = "src\\proyecto_hotel\\imagenes\\usuarios\\";
            connection = (Connection) DriverManager.getConnection(c.getString_connection(), c.getUsername(), c.getPassword());
            Statement stm = (Statement) connection.createStatement();
            ResultSet rs = stm.executeQuery("select * from usuario where Nombre_usuario = '"+usuario+"';");
            String nombre = null,contra;
            int tipo,estado;
            while (rs.next()) {
                nombre = rs.getString("nombre_usuario");
                tipo = rs.getInt("tipo_cuenta");
            }   
            
            if (nombre == null) {
                return false;
            }
        
        return true;
    }
    
    boolean Valida(){
        
        if (txtp_nombre.getText().equals("") || txtp_apellido.getText().equals("")
                || txts_nombre.getText().equals("") || txts_apellido.getText().equals("")
                || txtidentificacion.getText().equals("") || txtpais.getText().equals("")
                || txttelefono.getText().equals("") || txtcorreo.getText().equals("")
                || combo_tipo.getSelectionModel().isEmpty()
                || fechanac.getValue() == null
                || txtnombreuser.getText().equals("") 
                || txtpassword.getText().equals("")
                || txtpassword2.getText().equals("")
                ) {
            
            Dialogo("Al parecer hay algunos campos que necesitan ser rellenados.", "¡Necesita rellenar todos los campos!",
                    "Error", Alert.AlertType.ERROR);
            
        }else{
            
            if (screen_img.getImage() == null || screen_img.getImage().impl_getUrl() == deflt) {
                Dialogo("Al parecer necesita agregar una imagen.", "¡No imagen!",
                    "Error", Alert.AlertType.ERROR);
            }else{
                
                if (txtpassword.getText().equals(txtpassword2.getText())) {
                    return true;
                }else{
                    
                    Dialogo("Al parecer las contraseañas no coinciden.", "¡Contraseñas incorrectas!",
                    "Error", Alert.AlertType.ERROR);
                }
            
            }
            
        }
        
        return false;
    }
    
    String deflt = "";
    
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

    void DialogoOk(String mensaje, String cabecera, String titulo, Alert.AlertType e, int SoloOk){
        
        Alert alert = new Alert(e);
        alert.setTitle(titulo);
        alert.setHeaderText(cabecera);
        alert.setContentText(mensaje);
        
        ButtonType buttonTypeOk = new ButtonType("Aceptar", ButtonBar.ButtonData.OK_DONE);
        
        if (SoloOk == 1) {
            
            alert.getButtonTypes().setAll(buttonTypeOk);
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == buttonTypeOk){
                alert.close();
            }
            
        }else{
            
            ButtonType buttonTypeCancel = new ButtonType("Cancelar", ButtonBar.ButtonData.CANCEL_CLOSE);
            alert.getButtonTypes().setAll(buttonTypeCancel,buttonTypeOk);
            
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == buttonTypeOk){
                // ... user chose OK
                
            } else if(result.get() == buttonTypeCancel){
                // ... user chose CANCEL or closed the dialog
                alert.close();
            }
        }
        
    }
    
    void Nuevo(){
        
        txtnombreuser.setText("");
        txtpassword.setText("");
        txtpassword2.setText("");
        
        txtp_nombre.setText("");
        txts_nombre.setText("");
        txtp_apellido.setText("");
        txts_apellido.setText("");
        
        txtidentificacion.setText("");
        txtpais.setText("");
        fechanac.setValue(null);
        fecha_inscripcion.setValue(null);
        txttelefono.setText("");
        txtcorreo.setText("");
        
    }
    
    int Tipo_usuario_copia;
    String nombre_user_copia;
    
    String tipo = "";
    
    public void Info(String name, int type) throws SQLException{
        
        connection = (Connection) DriverManager.getConnection(c.getString_connection(), c.getUsername(), c.getPassword());
        Statement stm = (Statement) connection.createStatement();
        
        ResultSet rs = stm.executeQuery("select * from Usuario u "
                + "inner join Cliente c on u.Id_cliente = c.Id_cliente "
                + "where u.Nombre_usuario = '"+nombre_user_copia+"';");
        
         while (rs.next()) {
                
                String imagen = rs.getString("imagen");
                id = rs.getInt("Id_cliente");
                txtp_nombre.setText(rs.getString("Primer_nombre"));
                txts_nombre.setText(rs.getString("Segundo_nombre"));
                txtp_apellido.setText(rs.getString("Primer_apellido"));
                txts_apellido.setText(rs.getString("Segundo_apellido"));
                txtidentificacion.setText(rs.getString("Identificacion"));
                tipo = rs.getString("Tipo_identificacion");
                combo_tipo.setValue(tipo);
                
                pNombre = (rs.getString("Primer_nombre"));
                pApellido = rs.getString("Primer_apellido");
                
                txtpais.setText(rs.getString("Pais_origen"));
                String fecha_inscripcion = rs.getString("Fecha_inscripcion");
                String fecha_nacimiento = rs.getString("Fecha_nacimiento");
                
                int[] nacimiento = Arrays.stream(fecha_nacimiento.trim().split("-"))
                      .mapToInt(Integer::parseInt)
                      .toArray();
        
                int[] inscripcion = Arrays.stream(fecha_inscripcion.trim().split("-"))
                      .mapToInt(Integer::parseInt)
                      .toArray();
                
        
                fechanac.setValue(LocalDate.of(nacimiento[0],nacimiento[1],nacimiento[2]));
                this.fecha_inscripcion.setValue(LocalDate.of(inscripcion[0],inscripcion[1],inscripcion[2]));
                
                txtcorreo.setText(rs.getString("Correo"));
                screen_img.setImage(new Image(new File(dir+imagen).toURI().toString()));
                txttelefono.setText(rs.getString("Telefono"));
                
                txtnombreuser.setText(rs.getString("Nombre_usuario"));
                oldUser = rs.getString("Nombre_usuario");
                txtpassword.setText(rs.getString("Contrasena"));
                txtpassword2.setText(rs.getString("Contrasena"));
                
                
        }
         
    }

    @FXML
    void Nuevo_Registro(ActionEvent event) {
        Nuevo();
    }
    
    Conexion c = new Conexion();
    Connection connection ;
    ObservableList<Clientes> data = FXCollections.observableArrayList();
    String dir = "src\\proyecto_hotel\\imagenes\\clientes\\";
    
    
    //Importante: De aqui se obtiene la ruta de la imagen
    String nombre_img;
    int click = 0;
    
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Tipo_usuario_copia = MenuController.tipo_usuario;
        nombre_user_copia = MenuController.nombre_usuario_entra;
        
        ObservableList tipoID = FXCollections.observableArrayList();
        tipoID.add("Cedula");
        tipoID.add("Nacionalidad");
        
        combo_tipo.getItems().addAll(tipoID);
        
        try {
            Info(nombre_user_copia,Tipo_usuario_copia);
        } catch (SQLException ex) {
            Logger.getLogger(PerfilClienteController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }    
    
}
