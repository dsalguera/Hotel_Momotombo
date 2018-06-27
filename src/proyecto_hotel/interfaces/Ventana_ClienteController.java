
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
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
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
import proyecto_hotel.clases.Clientes;


public class Ventana_ClienteController implements Initializable {

    @FXML
    private AnchorPane anchorpane;
    
    @FXML
    private ComboBox<String> combo_buscar;

    @FXML
    private JFXTextField txtbuscar;

    @FXML
    private JFXButton btnBuscar;

    @FXML
    private ListView<Clientes> lista_habitaciones;

    @FXML
    private JFXButton btnAceptar;

    @FXML
    private JFXButton btnCancelar;
   
    public static String HabitacionInfoCopia;
    
    @FXML
    void Aceptar(ActionEvent event) throws SQLException, IOException {
        if (lista_habitaciones.selectionModelProperty().getValue().getSelectedIndex()!=-1) {
             ReservaController.Id_Cliente=lista_habitaciones.selectionModelProperty().getValue().getSelectedItem().getId();
         ReservaController.nombre_cliente=lista_habitaciones.selectionModelProperty().getValue().getSelectedItem().getPrimerNombre()+" "+
                 lista_habitaciones.selectionModelProperty().getValue().getSelectedItem().getSegundoNombre()+" "+
                 lista_habitaciones.selectionModelProperty().getValue().getSelectedItem().getPrimerApellido()+" "+
                 lista_habitaciones.selectionModelProperty().getValue().getSelectedItem().getSegundoApellido();
         ReservaController.imagen_cliente=lista_habitaciones.selectionModelProperty().getValue().getSelectedItem().getImagen();
         ((Node)(event.getSource())).getScene().getWindow().hide();
        }
 
    }

    @FXML
    void Cancelar(ActionEvent event) {
    ((Node)(event.getSource())).getScene().getWindow().hide(); 
    }
    
    Conexion c = new Conexion();
    Connection connection ;
    ObservableList<Clientes> data = FXCollections.observableArrayList();
    String dir = "src\\proyecto_hotel\\imagenes\\clientes\\";
    
    static String nombre_habitacion_copia;
    
   
       @FXML
    void Accion_buscar(ActionEvent event) throws SQLException {
    consulta("");
    }


    
    @FXML
    void consulta(String complemento) throws SQLException {
    
        if (combo_buscar.getSelectionModel().getSelectedIndex()!=-1 && txtbuscar.getText()!=null ) {
        String busq = txtbuscar.getText()+complemento;
        String filtro = combo_buscar.getSelectionModel().getSelectedItem();
            System.out.println(""+filtro);
        String query = "call getHabitaciones_disponibles_buscar('"+ReservaController.Fecha_Inicial+"','"+ReservaController.Fecha_Final+"','"+filtro+"','"+busq+"');";
            System.out.println(""+query);
        Crear_Lista(query);
        }else{
         Crear_Lista("call getHabitaciones_disponibles('"+ReservaController.Fecha_Inicial+"','"+ReservaController.Fecha_Final+"');");
        }
     
    }

    // Variables universales
    String habitacion, tipo, telefono, descripcion;
    int estado,id;
    Image imagen;
    double tarifa; 
    
    @FXML
    void click(MouseEvent event) throws SQLException {

    }
    
      void Conexion(String query) { 
        
        try {
            connection = (Connection) DriverManager.getConnection(c.getString_connection(), c.getUsername(), c.getPassword());
            Statement stm = (Statement) connection.createStatement();
            
            ResultSet rs = stm.executeQuery(query);
            
            while (rs.next()) {
                
                String imagen = rs.getString("imagen");
                int id = rs.getInt("Id_cliente");
                String primerNombre = rs.getString("Primer_nombre");
                String segundoNombre = rs.getString("Segundo_nombre");
                String primerApellido = rs.getString("Primer_apellido");
                String segundoApellido = rs.getString("Segundo_apellido");
                String identificacion = rs.getString("Identificacion");
                String tipo = rs.getString("Tipo_identificacion");
                String pais = rs.getString("Pais_origen");
                int numero_reserva = rs.getInt("Numero_reserva");
                int numero_estancia = rs.getInt("Numero_estancia");
                String fecha_inscripcion = rs.getString("Fecha_inscripcion");
                String fecha_nacimiento = rs.getString("Fecha_nacimiento");
                String telefono = rs.getString("Telefono");
                String correo = rs.getString("Correo");
             
                Clientes clientes = new Clientes(
                        new Image(new File(dir+imagen).toURI().toString()),
                        id,
                        primerNombre,
                        segundoNombre,
                        primerApellido,
                        segundoApellido, 
                        identificacion,
                        tipo,
                        pais,
                        numero_reserva,
                        numero_estancia,
                        fecha_inscripcion,
                        fecha_nacimiento,
                        telefono, 
                        correo);
                
                data.add(clientes);
                
                
            }
        } catch (SQLException ex) {
            Logger.getLogger(Ventana_ClienteController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    void Crear_Lista(String query){
        
        lista_habitaciones.getItems().clear();
        data.clear();
        
        Conexion(query);
        
        lista_habitaciones.getItems().addAll(data);
        
        lista_habitaciones.setCellFactory(new Callback<ListView<Clientes>, ListCell<Clientes>>() {

        @Override
        public ListCell<Clientes> call(ListView<Clientes> arg0) {
        return new ListCell<Clientes>() {

            @Override
            protected void updateItem(Clientes item, boolean bln) {
            super.updateItem(item, bln);
            if (item != null) {
            
            Label Nombres,Apellidos,Identificacion,Tipo,Pais,NumeroReserva,NumeroEstancia,
                    GPA,Inscripcion,Nacimiento,Telefono,Correo;
            ImageView imagen;
            
            VBox vBox = new VBox(
                    
                Nombres = new Label("Nombre Completo : "+item.getPrimerNombre()+" "+item.getSegundoNombre()+" "+item.getPrimerApellido()+" "+item.getSegundoApellido()),
                Identificacion = new Label("Identificacion: "+item.getIdentificacion()+" Tipo: "+item.getTipo()),
                Pais = new Label("Pais: "+item.getPais()),
                Inscripcion = new Label("Fecha de Inscripcion: "+item.getFecha_inscripcion()), 
                Nacimiento = new Label("Fecha de Nacimiento: "+item.getFecha_nacimiento()),      
                Telefono = new Label("Telefono: "+item.getTelefono()),     
                Correo = new Label("Correo: "+item.getCorreo()),
                NumeroReserva = new Label("Número de Reserva: "+item.getNumero_reserva()),
                NumeroEstancia = new Label("Número de Estancia: "+item.getNumero_estancia())
                        
                );
                             
                    HBox hBox = new HBox(
                            
                    imagen = new ImageView(item.getImagen()), vBox);
                    imagen.setFitHeight(166);
                    imagen.setFitWidth(250);
                    
                    hBox.setSpacing(10);
                    vBox.setSpacing(5);
                    hBox.setAlignment(Pos.CENTER_LEFT);
                    setGraphic(hBox);
                            
                        }
                    }

                };
            }
        
            

        });
        
    }
  
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        ObservableList busquedas = FXCollections.observableArrayList();
        busquedas.add("Nombre");
        busquedas.add("Tipo");
        busquedas.add("Tarifa <=");
        
          txtbuscar.setOnKeyTyped(event -> {     
        String string =  txtbuscar.getText();
        
        if (string.length() > 50) {
           event.consume();
           return;
        }      });
        combo_buscar.getItems().addAll(busquedas);
        
        Crear_Lista("Select * from Cliente where Eliminado=0 ;");
    }    
    
}
