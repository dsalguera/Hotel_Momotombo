
package proyecto_hotel.interfaces;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
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
import javafx.scene.control.RadioButton;
import javafx.scene.control.Separator;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.util.Callback;
import javax.swing.JOptionPane;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;
import proyecto_hotel.FXMLDocumentController;
import proyecto_hotel.Conexion;
import proyecto_hotel.clases.Manuel_Estancia;
import proyecto_hotel.clases.Manuel_Reserva;

public class Facturas_estanciaController implements Initializable {
  
    @FXML
    private AnchorPane anchorpane;
    
    @FXML
    private ComboBox<String> combo_buscar;

    @FXML
    private JFXTextField txtbuscar;

    @FXML
    private JFXButton btnBuscar;

    @FXML
    private ListView<Manuel_Estancia> lista_habitaciones;

    @FXML
    private JFXButton btnAceptar;

    @FXML
    private JFXButton btnCancelar;
   
    public static String HabitacionInfoCopia;
    int id_cliente=-1;
    
    @FXML
    void Aceptar(ActionEvent event) throws SQLException, IOException {
        boolean errorsql=false;
        
        if (lista_habitaciones.getItems().size()==0) {
            JOptionPane.showMessageDialog(null, "No hay reservas en la lista.");
            return ;
        }
        if (lista_habitaciones.getSelectionModel().getSelectedIndex()==-1) {
          JOptionPane.showMessageDialog(null, "No ha selecionado la reserva.");
            return ;
        }
       
        if (lista_habitaciones.getSelectionModel().getSelectedIndex()!=-1) {
            try {
    Conexion c=new Conexion();
    Connection connection ;
    connection = (Connection) DriverManager.getConnection(c.getString_connection(), c.getUsername(), c.getPassword());
    JasperReport reporte = (JasperReport) JRLoader.loadObject("src//Reportes//Factura_Hospedaje.jasper"); 
    Map parametro=new HashMap();
    parametro.put("Id_estancia",lista_habitaciones.getSelectionModel().getSelectedItem().getId_estancia());
        if (lista_habitaciones.getSelectionModel().getSelectedItem().getId_reserva()==0) {
            parametro.put("deduccion", 0.0);
            System.out.println("2");
        }else{
            System.out.println("3");
        parametro.put("deduccion", 0.1);
        }
    JasperPrint j = JasperFillManager.fillReport(reporte,parametro, connection); 
    JasperViewer jv=new JasperViewer(j,false);
    jv.setTitle("Factura del Hospedaje");
    jv.setVisible(true);
        } catch (Exception e) {
            System.out.println(""+e);
        }

 
        }else{
        JOptionPane.showMessageDialog(null,"Seleccione una reserva");
        }
    }

    @FXML
    void Cancelar(ActionEvent event) {
    ((Node)(event.getSource())).getScene().getWindow().hide(); 
    }
    
    Conexion c = new Conexion();
    Connection connection ;
    ObservableList<Manuel_Estancia> data = FXCollections.observableArrayList();
    String dirh = "src\\proyecto_hotel\\imagenes\\habitaciones\\";
    String dirc = "src\\proyecto_hotel\\imagenes\\clientes\\";
    static String nombre_habitacion_copia;
  
       @FXML
    void Accion_buscar(ActionEvent event) throws SQLException {
            if (MenuController.tipo_usuario!=3) {
                       String buscar=txtbuscar.getText();
                if (!buscar.trim().equals("")) {
                    
                    Crear_Lista("call get_Estancia_consulta_buscar_fact('"+buscar+"',"+combo_buscar.getSelectionModel().getSelectedIndex()+");");
                }else{
                  Crear_Lista("call get_Estancia_consulta_fact() ;");
                }
                }else{
                
                       String buscar=txtbuscar.getText();
                if (!buscar.trim().equals("")) {
                    
                    Crear_Lista("call get_Estancia_consulta_buscar_id_fact('"+buscar+"',"+combo_buscar.getSelectionModel().getSelectedIndex()+","+id_cliente+");");
                }else{
                  Crear_Lista("call get_Estancia_consulta_id_fact("+id_cliente+") ;");
                }
                }
    }


    
    @FXML
    void consulta(String complemento) throws SQLException {
    
 
     
    }

    // Variables universales
    String habitacion, tipo, telefono, descripcion;
    int estado,id;
    Image imagen;
    Image imagen1;
    double tarifa; 
    
    @FXML
    void click(MouseEvent event) throws SQLException {
    
        
    }
    
   void Conexion(String query) { 
        
        try {
            connection = (Connection) DriverManager.getConnection(c.getString_connection(), c.getUsername(), c.getPassword());
            Statement stm = (Statement) connection.createStatement();
            
            System.out.println(query);
            ResultSet rs = stm.executeQuery(query);
            
            while (rs.next()) {
                 int Id_reserva=rs.getInt("Id_reserva");
                 int Id_cliente=rs.getInt("Id_cliente");
                 int Id_habitacion=rs.getInt("Id_habitacion");
                 int Id_estancia=rs.getInt("Id_estancia");
                 String Fecha_inicio=rs.getString("Fecha_inicio");
                 String Fecha_final=rs.getString("Fecha_final");
                 String Descripcion=rs.getString("Descripcion");
                 String Estado=rs.getString("Estado");
                 double Costo_total=rs.getDouble("Costo_total");
                 String Nombre_cliente=rs.getString("nombre_cliente");
                 String Nombre_habitacion=rs.getString("nombre_habitacion");
                 
                 Image i1=new Image(new File(dirc+rs.getString("imgcliente")).toURI().toString());
                 Image i2=new Image(new File(dirh+rs.getString("imghabitacion")).toURI().toString());
                Manuel_Estancia habitacion = new Manuel_Estancia(Id_estancia,Id_reserva, Id_cliente,Id_habitacion, Fecha_inicio, Fecha_final, Estado,Descripcion, Costo_total,Nombre_cliente,Nombre_habitacion,i1,i2);
                if (Estado.toUpperCase().trim().equals("Cancelado".toUpperCase())) {
                    data.add(habitacion);
                }
            }
        } catch (SQLException ex) {
            System.out.println("Invalida busqueda");
        }
        
    }
    
      void Crear_Lista(String query){
        
        lista_habitaciones.getItems().clear();
        data.clear();
          System.out.println(query);
        Conexion(query);
        lista_habitaciones.getItems().addAll(data);
        
        lista_habitaciones.setCellFactory(new Callback<ListView<Manuel_Estancia>, ListCell<Manuel_Estancia>>() {
      
        @Override
        public ListCell<Manuel_Estancia> call(ListView<Manuel_Estancia> arg0) {
        return new ListCell<Manuel_Estancia>() {

            @Override
            protected void updateItem(Manuel_Estancia item, boolean bln) {
            super.updateItem(item, bln);
            if (item != null) {
            Label Id_reserva,Id_Estancia,Descripcion, Id_cliente, Fecha_inicio, Fecha_final, Estado, Costo_total, Id_habitacion, Fecha_reserva,Nombre_cliente,Nombre_habitacion;
           ImageView imagen1,imagen2;
            VBox vBox = new VBox(
                    
                Id_Estancia = new Label("Id Estancia : "+item.getId_estancia()), 
                Id_cliente = new Label("Id Cliente : "+item.getId_cliente()), 
                Nombre_cliente = new Label("Nombre :  "+item.getNombre_cliente()),
                Id_habitacion = new Label("Id Habitacion: "+item.getId_habitacion()),
               Nombre_habitacion = new Label("Nombre : "+item.getNombre_habitacion()),
               Fecha_inicio = new Label("Fecha Inicio : "+item.getFecha_inicio()), 
               Fecha_final= new Label("Fecha Final : "+item.getFecha_final()), 
               Descripcion = new Label("Descripcion :  "+item.getDescripcion()),
               Costo_total = new Label("Costo Total : "+item.getCosto_total()),
               Estado = new Label("Estado : "+item.getEstado()),
               Id_reserva=new Label("Reserva : "+item.getId_reserva())
                    
                    //,Estado = new Label(""+estado)    
                );
               Id_Estancia.getStyleClass().add("espacio");
               Id_reserva.getStyleClass().add("espacio");
                Id_cliente.getStyleClass().add("espacio");
                Nombre_cliente.getStyleClass().add("espacio");
                Id_habitacion.getStyleClass().add("espacio");
               Nombre_habitacion.getStyleClass().add("espacio");
               Fecha_inicio.getStyleClass().add("espacio");
               Fecha_final.getStyleClass().add("espacio");
               Descripcion.getStyleClass().add("espacio");
               Costo_total.getStyleClass().add("espacio");
              
                    if (item.getEstado().equalsIgnoreCase("Activo")) {
                        Estado.getStyleClass().add("round-green");
                    }else if (item.getEstado().equalsIgnoreCase("Cancelado")){
                        Estado.getStyleClass().add("round-yellow");
                    }else{
                     Estado.getStyleClass().add("round-red");
                    }
                  
                    VBox vimg=new VBox(imagen1 = new ImageView(item.getImgcliente()),imagen2 = new ImageView(item.getImghabitacion()));
                    imagen1.setFitHeight(100);
                    imagen1.setFitWidth(200);
                    imagen2.setFitHeight(100);
                    imagen2.setFitWidth(200);
                    HBox hBox = new HBox(vimg, vBox);
                   Separator s=new Separator();
                    VBox vfinal=new VBox(hBox,s);
                    vfinal.setSpacing(2);
                    hBox.setSpacing(5);
                    vBox.setSpacing(2);
                    vimg.setSpacing(5);
                    setGraphic(vfinal);
                            
                        }
                    }

                };
            }
      
            

        });
    }
    
  
    @Override
    public void initialize(URL url, ResourceBundle rb) {
         MenuController.Audit_habitacion(); 
        ObservableList busquedas = FXCollections.observableArrayList();
        if (MenuController.tipo_usuario!=3) {
        busquedas.add("Nombre Cliente");
        }else{
        id_cliente=FXMLDocumentController.Id_Cliente;
        }
        busquedas.add("Nombre Habitacion");
        txtbuscar.setOnKeyPressed(event->{
        System.out.println(""+event.getCode());
         if (event.getCode().equals(KeyCode.BACK_SPACE) && combo_buscar.getSelectionModel().getSelectedIndex()==2) {
             txtbuscar.selectAll();
         }
        
        });
          txtbuscar.setOnKeyTyped(event -> {     
        String string =  txtbuscar.getText();
             
        
        if (string.length() > 50) {
           event.consume();
           return;
        }else{
           
            if (combo_buscar.getSelectionModel().getSelectedIndex()!=-1) {
                if (MenuController.tipo_usuario!=3) {
                       String buscar=txtbuscar.getText()+event.getCharacter();
                if (!buscar.trim().equals("")) {
                    
                    Crear_Lista("call get_Estancia_consulta_buscar_fact('"+buscar+"',"+combo_buscar.getSelectionModel().getSelectedIndex()+");");
                }else{
                  Crear_Lista("call get_Estancia_consulta_fact() ;");
                }
                }else{
                
                       String buscar=txtbuscar.getText()+event.getCharacter();
                if (!buscar.trim().equals("")) {
                    
                    Crear_Lista("call get_Estancia_consulta_buscar_id_fact('"+buscar+"',"+combo_buscar.getSelectionModel().getSelectedIndex()+","+id_cliente+");");
                }else{
                  Crear_Lista("call get_Estancia_consulta_id_fact("+id_cliente+") ;");
                }
                }
            }
  
        
        }    
          
          
          });
          
        combo_buscar.getItems().addAll(busquedas);
        if (MenuController.tipo_usuario==3) {
             Crear_Lista("call get_Estancia_consulta_id_fact("+id_cliente+") ;");
        }else{
        Crear_Lista(" call get_Estancia_consulta_fact() ;");
        }
        
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
