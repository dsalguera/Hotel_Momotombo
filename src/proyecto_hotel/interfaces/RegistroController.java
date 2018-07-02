
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
import java.text.Format;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Date;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import proyecto_hotel.Conexion;
import proyecto_hotel.clases.Clientes;

/**
 * FXML Controller class
 *
 * @author David Salguera
 */
public class RegistroController implements Initializable {

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
    private ComboBox<?> combo_tipo;

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
    private JFXButton btnGuardar;

    @FXML
    private HBox panel_edicion1;

    @FXML
    private JFXButton btnLimpiar;

    @FXML
    private HBox panel_edicion11;

    @FXML
    private JFXButton btnRegresar;

    
    Conexion c = new Conexion();
    Connection connection ;
    ObservableList<Clientes> data = FXCollections.observableArrayList();
    String dir = "src\\proyecto_hotel\\imagenes\\clientes\\";
    
    //Importante: De aqui se obtiene la ruta de la imagen
    String nombre_img;
    int click = 0;
    
    
    @FXML
    void Regresar(ActionEvent event) {
        try {
            
                    FXMLLoader fxmlLoader = new FXMLLoader();
                    fxmlLoader.setLocation(getClass().getResource("/proyecto_hotel/FXMLDocument.fxml"));
                    
                    Stage stage = new Stage();
                    stage.initStyle(StageStyle.TRANSPARENT);
                    
                    Scene scene = new Scene(fxmlLoader.load(),Color.TRANSPARENT);
                    
                    stage.setScene(scene);
                    stage.show();
                    
                    ((Node)(event.getSource())).getScene().getWindow().hide();

        } catch (IOException e) {
            
            DialogoOk("Al parecer hay un error de regreso al sistema.\n\n"+e.getMessage()+"", "Error al Acceder!", "Error", Alert.AlertType.ERROR, 1);
                        
        }
    }
    
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
    void Guardar_Registro(ActionEvent event) throws SQLException {
        
        if (Valida() == true) {

                String primerNombre = txtp_nombre.getText();
                String segundoNombre = txts_nombre.getText();
                String primerApellido = txtp_apellido.getText();
                String segundoApellido = txts_apellido.getText();
                
                String identificacion = txtidentificacion.getText();
                String tipo = (String) combo_tipo.getSelectionModel().getSelectedItem();
                String pais = txtpais.getText();
                
                String fecha_inscripcion = fechanac.getValue().toString();
                String fecha_nacimiento = this.fecha_inscripcion.getValue().toString();
                
                int[] nacimiento = Arrays.stream(fecha_nacimiento.trim().split("-"))
                        .mapToInt(Integer::parseInt)
                        .toArray();
                
                int[] inscripcion = Arrays.stream(fecha_inscripcion.trim().split("-"))
                        .mapToInt(Integer::parseInt)
                        .toArray();
                
                
                fecha_nacimiento = (""+nacimiento[0]+"-"+nacimiento[1]+"-"+nacimiento[2]);
                fecha_inscripcion = (""+inscripcion[0]+"-"+inscripcion[1]+"-"+inscripcion[2]);
                
                System.out.println(""+fecha_nacimiento);
                System.out.println(""+fecha_inscripcion);
                
                String telefono = txttelefono.getText();
                String correo = txtcorreo.getText();
                
                Image imagen = screen_img.getImage();
                
                Connection connection = (Connection) DriverManager.getConnection(c.getString_connection(), c.getUsername(), c.getPassword());
                Statement stm = (Statement) connection.createStatement();
                String query = null;
                String query2 = null;
                
                if (Usuario_existe(txtnombreuser.getText()) == true) {
                    
                    Dialogo("No se ha guardado el usuario por que ya existe, pruebe con otro.", "Usuario no valido!",
                            "Error", Alert.AlertType.ERROR);
                    
                }else{
                    
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("¿Confirmar Acción?");
                    alert.setHeaderText("¿Está seguro que desea guardar el cliente "+primerNombre+" "+primerApellido+" a la base?");
                    alert.setContentText("Se guardará la información con los datos introcucidos en los campos.\nPara Guardar, presione aceptar.");
                    
                    ButtonType buttonTypeCancel = new ButtonType("Cancelar", ButtonBar.ButtonData.CANCEL_CLOSE);
                    ButtonType buttonTypeOk = new ButtonType("Aceptar", ButtonBar.ButtonData.OK_DONE);
                    
                    alert.getButtonTypes().setAll(buttonTypeCancel,buttonTypeOk);
                    
                    Optional<ButtonType> result = alert.showAndWait();
                    if (result.get() == buttonTypeOk){
                        try {
                            // ... user chose OK
                            query = "call setUsuario('"+primerNombre+"','"+segundoNombre+"','"+primerApellido+"','"+segundoApellido+"',"
                                    + "'"+identificacion+"','"+tipo+"','"+pais+"',0,0,'"+fecha_inscripcion+"','"+fecha_nacimiento+"',"
                                    + "'"+telefono+"','"+correo+"','"+nombre_img+"',0, '"+txtnombreuser.getText()+"','"+txtpassword.getText()+"',3,1,0);";

                            stm.executeUpdate(query);
                            stm.close();
                            
                            Dialogo("Se ha guardado el registro.", "Exito al Guardar!",
                                    "Operación Realizada", Alert.AlertType.CONFIRMATION);
                            
                        } catch (SQLException ex) {
                            Dialogo("Ha ocurrido un error al guardar el registro.\n\n"+ex.getMessage()+"\n\n"+ex.getSQLState()+"", "Error al Guardar!",
                                    "Error", Alert.AlertType.CONFIRMATION);
                        }
                        
                    } else if(result.get() == buttonTypeCancel){
                        // ... user chose CANCEL or closed the dialog
                        alert.close();
                    }

                }
            
        }
        
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
    
    @FXML
    void Nuevo_Registro(ActionEvent event) {
        Nuevo();
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
        Fecha_hoy();
        
    }
    
    String s;
    Format formatter;
    Date date = new Date();
    
    void Fecha_hoy(){
        
        formatter = new SimpleDateFormat("dd-MM-yyyy");
        s = formatter.format(date); 
        String fecha_inscripcion = s;
        
        int[] inscripcion = Arrays.stream(fecha_inscripcion.trim().split("-"))
                      .mapToInt(Integer::parseInt)
                      .toArray();
                
        
        this.fecha_inscripcion.setValue(LocalDate.of(inscripcion[2],inscripcion[1],inscripcion[0]));
        
    }
    
    String deflt = "";
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        fecha_inscripcion.setEditable(false);
        Nuevo();
        
        ObservableList tipoID = FXCollections.observableArrayList();
        
        tipoID.add("Cedula");
        tipoID.add("Nacionalidad");
        
        deflt = screen_img.getImage().impl_getUrl();
        
        combo_tipo.getItems().addAll(tipoID);
    }    
    
}
