
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
import java.util.HashMap;
import java.util.Map;
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
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
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
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;
import javafx.util.Callback;
import javax.swing.JOptionPane;
import static jdk.nashorn.internal.objects.NativeFunction.call;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;
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
    private JFXButton btnGuardar;

    
    String dir = "src\\proyecto_hotel\\imagenes\\clientes\\";
    public static int Id_habitacion=-1;
    public static String nombre;
    public static Image imagen;
      public static int Id_Cliente=-1;
    public static String nombre_cliente;
    public static Image imagen_cliente;
    public static String Fecha_Inicial;
    public static String Fecha_Final;
    int limit=3;
    int Conteo=0;
    Conexion c = new Conexion();
    Connection connection ;    
    Stage stage_buscar=new Stage();
    
    @FXML
    private ImageView screen_img1;
    
    @FXML
    private Label id_count;

    @FXML
    private JFXTextField txtnombre_cliente;

    @FXML
    private JFXButton btnBuscar_cliente;

    @FXML
    private RadioButton Radio_efectivo;
      @FXML
    private RadioButton Radio_Credito;
      
    @FXML
    private JFXTextField txtnumero_tarjeta;

    @FXML
    private ToggleGroup Radio;
    
    int aux_id_cliente=-1;
    
        @FXML
    void Buscar_cliente(ActionEvent event)  {
           
        stage_buscar.close();
        if (!stage_buscar.isShowing()) {
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("/proyecto_hotel/interfaces/Ventana_Cliente.fxml"));            
        stage_buscar = new Stage();   
        Scene scene = null;       
            try { 
                
                scene = new Scene(fxmlLoader.load(),Color.TRANSPARENT);
            } catch (IOException ex) {
                Logger.getLogger(ReservaController.class.getName()).log(Level.SEVERE, null, ex);
            }
        stage_buscar.setTitle("Buscar Cliente");
        stage_buscar.setScene(scene);
        aux_id_cliente=Id_Cliente;
        Id_Cliente=-1;
        stage_buscar.initOwner(InicioController.stage);
        stage_buscar.initModality(Modality.WINDOW_MODAL);
        stage_buscar.show();
        stage_buscar.setOnHidden(new EventHandler<WindowEvent> (){
            @Override
            public void handle(WindowEvent event) {
                if (Id_Cliente!=-1) {
                    try {
                        Cliente_Seleccionada();
                    } catch (Exception ex) {
                        System.out.println(""+ex);
                        Logger.getLogger(ReservaController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }else{Id_Cliente=aux_id_cliente;}
        }   });
        
        
        
        } }
    int aux_id_habitacion=-1;
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
        stage_buscar.initOwner(InicioController.stage);
        stage_buscar.initModality(Modality.WINDOW_MODAL);
        stage_buscar.setScene(scene);
         aux_id_habitacion=Id_habitacion;
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
                }else{Id_habitacion=aux_id_habitacion;}
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
    
    void Cliente_Seleccionada(){
    screen_img1.setImage(imagen_cliente);
    txtnombre_cliente.setText(nombre_cliente);
    setcount();
    }
 
        @FXML
    void Click_Radio_Credito(ActionEvent event) {
        txtnumero_tarjeta.setText(Guardar_tarjeta);
        txtnumero_tarjeta.setDisable(false);
    }
   String Guardar_tarjeta="";
    @FXML
    void Click_Radio_Efectivo(ActionEvent event) {
      txtnumero_tarjeta.setDisable(true);
      Guardar_tarjeta=txtnumero_tarjeta.getText();
      txtnumero_tarjeta.setText("");
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
    void Guardar_Registro(ActionEvent event) {
        if (Conteo>=limit) {
            JOptionPane.showMessageDialog(null, "El usuario no puede contratar otra reserva.");
            return;
        }
        if (Radio_Credito.isSelected() && txtnumero_tarjeta.getText().trim().equals("")) {
            JOptionPane.showMessageDialog(null, "Ingrese numero de tarjeta.");
            return;
        }
        if (Id_Cliente!=-1 && Id_habitacion!=-1 && Conteo<limit ) {
            if ( JOptionPane.showConfirmDialog(null, "Esta seguro que quiere contratar la reserva desde "+fecha_inicio.getValue()+" hasta "+fecha_final.getValue()+".", "Confirmar Operacion", JOptionPane.YES_NO_OPTION)==JOptionPane.YES_OPTION ) {
                try {
                Connection connection = (Connection) DriverManager.getConnection(c.getString_connection(), c.getUsername(), c.getPassword());
                Statement stm = (Statement) connection.createStatement(); 
                String query;
                String mensaje="";
                 if (Radio_efectivo.isSelected()) {
                  query="call setReserva("+Id_Cliente+","+Id_habitacion+",'"+Fecha_Inicial+"','"+Fecha_Final+"','Efectivo',null);";
                 }else{
                 query="call setPago("+txtcosto.getText()+","+txtnumero_tarjeta.getText()+");";
                 ResultSet rs = stm.executeQuery(query);
                 rs.next();
                 mensaje=rs.getString("mensaje");
                     if (!mensaje.equals("perfecto")) {
                         JOptionPane.showMessageDialog(null, mensaje);
                         return;
                     }
                  query="call setReserva("+Id_Cliente+","+Id_habitacion+",'"+Fecha_Inicial+"','"+Fecha_Final+"','Credito',"+txtnumero_tarjeta.getText()+");";
                 }
                
                ResultSet rs = stm.executeQuery(query);
                rs.next();
                mensaje=rs.getString("mensaje");
                JOptionPane.showMessageDialog(null, mensaje);
     if (true) {
                            
    try {
    Conexion c=new Conexion();
    connection = (Connection) DriverManager.getConnection(c.getString_connection(), c.getUsername(), c.getPassword());
    JasperReport reporte = (JasperReport) JRLoader.loadObject("src//Reportes//Certificado_Reserva.jasper"); 
    Map parametro=new HashMap();
        System.out.println("R:"+getReservaID());
        System.out.println("U:"+typeUser());
    parametro.put("DId_Reserva",getReservaID());
    parametro.put("DTipo_User",typeUser());
    parametro.put("permiso",1);
    JasperPrint j = JasperFillManager.fillReport(reporte,parametro, connection); 
    JasperViewer jv=new JasperViewer(j,false);
    jv.setTitle("Certificado de Reserva (Original)");
    jv.setVisible(true);
        } catch (Exception e) {
            System.out.println("2"+e);
        }
           
                    }
                
                Nuevo_Registro(event);
            } catch (SQLException ex) {
                    System.out.println("2"+ex);
            } 
            }
            
        }else{
        
        JOptionPane.showMessageDialog(null, "Seleccione Usuario y/o Habitacion.");
        }
    }

    @FXML
    void Nuevo_Registro(ActionEvent event) {
      txtnumero_tarjeta.setDisable(true);
      Guardar_tarjeta=txtnumero_tarjeta.getText();
      txtnumero_tarjeta.setText("");
      screen_img.setImage(null);
      txtcosto.setText("");
      txtnombre.setText("");
      txtnombre_cliente.setText("");
      Fecha_Defauld();
      txtdias.setText("");
      id_count.setText("0");
      Id_habitacion=-1;
       if (MenuController.tipo_usuario==3) {
           
            Radio_efectivo.setDisable(true);
            Radio_Credito.setSelected(true);
            txtnumero_tarjeta.setDisable(false);
            btnBuscar_cliente.setVisible(false);
            
        }else{
       Radio_efectivo.setSelected(true);
        screen_img1.setImage(null);
         Id_Cliente=-1;
       }
       setcount();
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
    MenuController.Audit_habitacion();
    Id_habitacion=-1;
    Id_Cliente=-1;
    Conteo=0;
        txtnumero_tarjeta.setOnKeyTyped(event -> {
        String string =  txtnumero_tarjeta.getText();
          
        if (string.length() > 8) {
           event.consume();
        }
         boolean isDigit=true;
                  try {
                      int aux=Integer.parseInt(event.getCharacter());
                  } catch (Exception e) {
                      isDigit=false;
                  }
            if (!isDigit) {
                event.consume();
            }
 {
                
            }
        
    });
        if (MenuController.tipo_usuario==3) {
            Radio_efectivo.setDisable(true);
            Radio_Credito.setSelected(true);
            txtnumero_tarjeta.setDisable(false);
            btnBuscar_cliente.setVisible(false);
            setCliente();
        }
        Fecha_Defauld();
        setcount();
    }  
    
    void setCliente(){
        Id_Cliente=FXMLDocumentController.Id_Cliente;
        try {
        Connection connection = (Connection) DriverManager.getConnection(c.getString_connection(), c.getUsername(), c.getPassword());
        Statement stm = (Statement) connection.createStatement();
        String query = "Select concat(Primer_nombre,' ',Segundo_nombre,' ',Primer_apellido,' ',Segundo_apellido) as nombre,Imagen  from Cliente where Id_cliente="+Id_Cliente;
       
        ResultSet rs = stm.executeQuery(query);
        while (rs.next()) {
        imagen_cliente=new Image(new File(dir+rs.getString("Imagen")).toURI().toString());
           nombre_cliente=rs.getString("nombre");
        }   
        Cliente_Seleccionada();
        } catch (Exception e) {
            System.out.println("Error Fecha por defecto");
        }
    
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
    
    void setcount(){
         try {
             Connection connection = (Connection) DriverManager.getConnection(c.getString_connection(), c.getUsername(), c.getPassword());
             Statement stm = (Statement) connection.createStatement();
             String query = " select count(*) as conteo from Reserva where Id_cliente="+Id_Cliente+" and Estado='Espera';";
             ResultSet rs = stm.executeQuery(query);
             rs.next();
             Conteo=rs.getInt("conteo");
             id_count.setText(""+Conteo);
         } catch (SQLException ex) {
             Logger.getLogger(ReservaController.class.getName()).log(Level.SEVERE, null, ex);
         }
    
    
    }
     int getReservaID(){
         int id=0;
         try {
             Connection connection = (Connection) DriverManager.getConnection(c.getString_connection(), c.getUsername(), c.getPassword());
             Statement stm = (Statement) connection.createStatement();
             String query = " select Id_reserva from Reserva where Id_cliente="+Id_Cliente+" and Estado='Espera' and Id_habitacion="+Id_habitacion+" and Fecha_inicio='"+Fecha_Inicial+"' and Fecha_final='"+Fecha_Final+"';";
             ResultSet rs = stm.executeQuery(query);
             rs.next();
             id=rs.getInt("Id_reserva");
         } catch (SQLException ex) {
             System.out.println("error al extraer id reserva");
         }
    
    return id;
    }
     
     String typeUser(){
      if (MenuController.tipo_usuario == 1) {
         return "Administrador"  ;
        }else if (MenuController.tipo_usuario == 2) {
          return "Secretario";
        }else if (MenuController.tipo_usuario == 3) {
            return "Visitante";
        }
     return null;
     }
}
