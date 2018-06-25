
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
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.*;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
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
import javafx.stage.WindowEvent;
import javafx.util.Callback;
import javax.swing.JOptionPane;
import static jdk.nashorn.internal.objects.NativeFunction.call;
import proyecto_hotel.clases.CustomThing;
import proyecto_hotel.clases.Habitaciones;
import proyecto_hotel.*;
import proyecto_hotel.clases.*;

public class ReservaController implements Initializable {
    
     @FXML
    private AnchorPane anchorpane;


    @FXML
    private ListView<Reserva> lista_reservas;
    
 
    @FXML
    private ImageView screen_img;

    @FXML
    public JFXTextField txtnombre;

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
    
    public static int Id_habitacion=-1;
    public static String nombre;
    public static Image imagen;
    public static String Fecha_Inicial;
    public static String Fecha_Final;
    Conexion c = new Conexion();
    Connection connection ;    
    Stage stage_buscar=new Stage();
    @FXML
    void Buscar_habitacion(ActionEvent event) throws SQLException, IOException {
        
        stage_buscar.close();
        if (Fecha_valida() && !stage_buscar.isShowing()) {
        Fecha_Inicial=fecha_inicio.getValue().toString();
        Fecha_Final=fecha_final.getValue().toString();
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("/proyecto_hotel/interfaces/Ventana_Habitaciones.fxml"));            
        stage_buscar = new Stage();          
        Scene scene = new Scene(fxmlLoader.load(),Color.TRANSPARENT);       
        stage_buscar.setTitle("Buscar Habitación");
        stage_buscar.setScene(scene);
        Id_habitacion=-1;
        stage_buscar.show();
        stage_buscar.setOnHidden(new EventHandler<WindowEvent> (){
            @Override
            public void handle(WindowEvent event) {
                if (Id_habitacion!=-1) {
                    try {
                        Habitacion_Seleccionada();
                    } catch (SQLException ex) {
                        System.out.println(""+ex);
                        Logger.getLogger(ReservaController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
        }   });
        
        
        
        }
 
    }
 
    void Habitacion_Seleccionada() throws SQLException{
    screen_img.setImage(imagen);
    txtnombre.setText(nombre);
   
        Connection connection = (Connection) DriverManager.getConnection(c.getString_connection(), c.getUsername(), c.getPassword());
        Statement stm = (Statement) connection.createStatement();
        String query = " call getCosto_total('"+Fecha_Inicial+"','"+Fecha_Final+"',"+Id_habitacion+")";
        ResultSet rs = stm.executeQuery(query);
        rs.next();
        txtcosto.setText(""+rs.getDouble("Costo_total"));
        txtdias.setText(""+rs.getInt("dias"));
    }
    
    boolean Fecha_valida() throws SQLException{
   
        if (fecha_inicio.getValue()==null || fecha_final.getValue()==null) {
            JOptionPane.showMessageDialog(null,"Complete la fecha de inicio y final.");
            return false;
        }
        Connection connection = (Connection) DriverManager.getConnection(c.getString_connection(), c.getUsername(), c.getPassword());
        Statement stm = (Statement) connection.createStatement();
       
        String query = "call  getVerificar('"+fecha_inicio.getValue()+"','"+fecha_final.getValue()+"');";
       
        int diff1=0,diff2=0;
        String fecha_permitida="";
        ResultSet rs = stm.executeQuery(query);
        while (rs.next()) {
                 diff1 = rs.getInt("diferencia_rango");   
                 diff2 = rs.getInt("diferencia_actual"); 
                 fecha_permitida=rs.getString("fecha_permitida");
        }
       
        if (diff1<0) {
            JOptionPane.showMessageDialog(null,"Fechas Invalidas");
            return false;
        }
   if (diff2<5) {
            JOptionPane.showMessageDialog(null,"Las politicas del hotel expresan que el contrator de la reserva \npuede hacerse desde "+fecha_permitida);
            return false;
        }
        
    
    return true;
    }
    
    @FXML
    void Sugerencia(KeyEvent event) {
       
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
    
    // Variables universales
    String habitacion, tipo, telefono, descripcion;
    double tarifa; 
    int estado;

    @FXML
    void click(MouseEvent event) {
        
        
    }
    

    @Override
    public void initialize(URL url, ResourceBundle rb) {
     
        Fecha_Defauld();
    }  
    
    void Fecha_Defauld(){
     try {
        Connection connection = (Connection) DriverManager.getConnection(c.getString_connection(), c.getUsername(), c.getPassword());
        Statement stm = (Statement) connection.createStatement();
        String query = "select cast(ADDDATE(now(), INTERVAL 5 DAY) as date) as Fecha_defauld;";
        String Fecha="";
        ResultSet rs = stm.executeQuery(query);
        while (rs.next()) {
            Fecha=rs.getString("Fecha_defauld");
        }   
        fecha_inicio.setValue(LocalDate.parse(Fecha, DateTimeFormatter.ISO_LOCAL_DATE));
        fecha_final.setValue(LocalDate.parse(Fecha, DateTimeFormatter.ISO_LOCAL_DATE));
        } catch (Exception e) {
            System.out.println("Error Fecha por defecto");
        }
    }
    
    
    
}
