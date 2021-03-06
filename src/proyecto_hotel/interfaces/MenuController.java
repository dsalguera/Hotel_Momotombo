package proyecto_hotel.interfaces;

import com.jfoenix.controls.*;
import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import javax.swing.Action;
import javax.swing.JOptionPane;
import proyecto_hotel.FXMLDocumentController;
import proyecto_hotel.*;
import proyecto_hotel.clases.*;
import proyecto_hotel.interfaces.HabitacionesController;


/**
 * FXML Controller class
 *
 * @author David Salguera
 */
public class MenuController implements Initializable {

    @FXML
    public AnchorPane lienzo;

    @FXML
    private JFXButton btnInicio;

    @FXML
    private JFXButton btnHabitaciones;

    @FXML
    private JFXButton btnClientes;

    @FXML
    private JFXButton btnReservas;

    @FXML
    private JFXButton btnEstancias;

    @FXML
    private JFXButton btnDetalle_servicio;

    @FXML
    private JFXButton btnProductos;

    @FXML
    private JFXButton btnServicio_cuarto;

    @FXML
    private JFXButton btnBitacora;

    @FXML
    private JFXButton btnCerrar_sesion;
    
    @FXML
    private VBox menuBtns;
    
    @FXML
    private JFXButton btnMinimizar;
    
    @FXML
    private JFXButton btnPerfil;
    
    @FXML
    private JFXButton btnListaReservas;

    @FXML
    private JFXButton btnListaEstancias;

    @FXML
    private JFXButton btnMaximizar;

    @FXML
    private JFXButton btnCerrar;

    @FXML
    private ImageView img_logo;

    @FXML
    private ImageView img_user;

    @FXML
    private Label txtuser;

    @FXML
    private Label txttipo;
    
    @FXML
    private Label labHora;
    
    @FXML
    private Label labFecha;
    
    double xOffset, yOffset;
    
    
    @FXML
    void Presionar(MouseEvent event) {
        xOffset = lienzo.getScene().getWindow().getX() - event.getScreenX();
        yOffset = lienzo.getScene().getWindow().getY() - event.getScreenY();
    }

    @FXML
    void Arrastrar(MouseEvent event) {
        lienzo.getScene().getWindow().setX(event.getScreenX() + xOffset);
        lienzo.getScene().getWindow().setY(event.getScreenY() + yOffset);
    }

    @FXML
    void ListaEstancias(ActionEvent event) throws IOException {
        
        if (lienzo.getChildren().size()==2) {
        Pane listaEstancia = FXMLLoader.load(getClass().getResource("/proyecto_hotel/interfaces/ListaEstancia.fxml"));
        AjustePagina(listaEstancia);
        lienzo.getChildren().add(listaEstancia);
        }else{
        int  n= lienzo.getChildren().size()-1;
            for (int i = 2; i <=n; i++) {
                lienzo.getChildren().remove(2);
            }
        Pane listaEstancia = FXMLLoader.load(getClass().getResource("/proyecto_hotel/interfaces/ListaEstancia.fxml"));
        AjustePagina(listaEstancia);
        lienzo.getChildren().add(listaEstancia);
       
        }
        
    }

    @FXML
    void ListaReservas(ActionEvent event) throws IOException {
        
        if (lienzo.getChildren().size()==2) {
        Pane listaReserva = FXMLLoader.load(getClass().getResource("/proyecto_hotel/interfaces/ListaReserva.fxml"));
        AjustePagina(listaReserva);
        lienzo.getChildren().add(listaReserva);
        }else{
        int  n= lienzo.getChildren().size()-1;
            for (int i = 2; i <=n; i++) {
                lienzo.getChildren().remove(2);
            }
        Pane listaReserva = FXMLLoader.load(getClass().getResource("/proyecto_hotel/interfaces/ListaReserva.fxml"));
        AjustePagina(listaReserva);
        lienzo.getChildren().add(listaReserva);
       
        }
        
    }

    void Cerrar(ActionEvent event) {
        
        try {
            
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("/proyecto_hotel/FXMLDocument.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Stage stage = new Stage();
        stage.setTitle("Principal");
        stage.setScene(scene);
        stage.initStyle(StageStyle.UNDECORATED);
        stage.show();
        
        
        ((Node)(event.getSource())).getScene().getWindow().hide();
        
        } catch (IOException e) {
        }
        
    }

    
    String user;
    int type;
    public static int tipo_usuario;
    static String nombre_usuario_entra;
    
    public void setUser(String username, int type) throws SQLException{
        
        this.user = username;
        this.type = type;
        txtuser.setText(username);
        
        if (this.type == 1) {
            
            txttipo.setText("Administrador");
            menuBtns.getChildren().remove(btnPerfil);
            menuBtns.getChildren().remove(btnDetalle_servicio);
            menuBtns.getChildren().remove(btnReservas);
            menuBtns.getChildren().remove(btnEstancias);
            
        }else if (this.type == 2) {
            
            txttipo.setText("Secretario");
            menuBtns.getChildren().remove(btnPerfil);
            menuBtns.getChildren().remove(btnDetalle_servicio);
            menuBtns.getChildren().remove(btnReservas);
            menuBtns.getChildren().remove(btnEstancias);
            
        }else if (this.type == 3) {
            txttipo.setText("Visitante");
            menuBtns.getChildren().remove(btnClientes);
            menuBtns.getChildren().remove(btnDetalle_servicio);
            menuBtns.getChildren().remove(btnReservas);
            menuBtns.getChildren().remove(btnEstancias);
            menuBtns.getChildren().remove(btnServicio_cuarto);
            menuBtns.getChildren().remove(btnBitacora);
            
        }
        nombre_usuario_entra = username;
        tipo_usuario = type;
        Conexion();
        
    }
    
    
   static  Conexion c = new Conexion();
    static Connection connection ;
    
    void Conexion() throws SQLException{ 
        
        String dir;
        
        if (this.type == 3) {
            dir= "src\\proyecto_hotel\\imagenes\\clientes\\";
        }else{
            dir= "src\\proyecto_hotel\\imagenes\\usuarios\\";
        }
        
        connection = (Connection) DriverManager.getConnection(c.getString_connection(), c.getUsername(), c.getPassword());
        Statement stm = (Statement) connection.createStatement();
        
        ResultSet rs = stm.executeQuery("select * from usuario where Nombre_usuario = '"+txtuser.getText()+"';");
                
        String nombre,imagen = null,contra;
        int tipo,estado;
                
        while (rs.next()) {
                nombre = rs.getString("nombre_usuario");
                tipo = rs.getInt("tipo_cuenta");
                estado = rs.getInt("estado");
                imagen = rs.getString("imagen");
                contra = rs.getString("contrasena"); 
                img_user.setImage(new Image(new File(dir+imagen).toURI().toString()));
        }
        
        
    }

    @FXML
    void Bitacora(ActionEvent event) {
        
        if (lienzo.getChildren().size()==2) {
            try {
                Pane bitacora = FXMLLoader.load(getClass().getResource("/proyecto_hotel/interfaces/Bitacora.fxml"));
                AjustePagina(bitacora);   
                lienzo.getChildren().add(bitacora);
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(null, ""+ex.getMessage());
            }
        }else{
            try {
                int  n= lienzo.getChildren().size()-1;
                for (int i = 2; i <=n; i++) {
                    lienzo.getChildren().remove(2);
                }
                Pane bitacora = FXMLLoader.load(getClass().getResource("/proyecto_hotel/interfaces/Bitacora.fxml"));
                AjustePagina(bitacora);
                lienzo.getChildren().add(bitacora);
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(null, ""+ex.getMessage());
            }
        
        }
 
    }

    @FXML
    void Cerrar_sesion(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("¿Confirmar Acción?");
        alert.setHeaderText("¿Está seguro que desea cerrar la sesión de "+this.user+"?");
        alert.setContentText("Para regresar al Login, presione aceptar.");

        ButtonType buttonTypeCancel = new ButtonType("Cancelar", ButtonBar.ButtonData.CANCEL_CLOSE);
        ButtonType buttonTypeOk = new ButtonType("Aceptar", ButtonBar.ButtonData.OK_DONE);

        alert.getButtonTypes().setAll(buttonTypeCancel,buttonTypeOk);

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == buttonTypeOk){
            // ... user chose OK
            Cerrar(event);

        } else if(result.get() == buttonTypeCancel){
            // ... user chose CANCEL or closed the dialog
            alert.close();
        }
        
        System.out.println("user "+user);
        System.out.println("type "+type);
    }
    
    @FXML
    void Perfil(ActionEvent event) throws IOException {
        
        if (lienzo.getChildren().size()==2) {
        Pane clientes = FXMLLoader.load(getClass().getResource("/proyecto_hotel/interfaces/PerfilCliente.fxml"));
        AjustePagina(clientes);
        lienzo.getChildren().add(clientes);
        }else{
        int  n= lienzo.getChildren().size()-1;
            for (int i = 2; i <=n; i++) {
                lienzo.getChildren().remove(2);
            }
        Pane clientes = FXMLLoader.load(getClass().getResource("/proyecto_hotel/interfaces/PerfilCliente.fxml"));
        AjustePagina(clientes);
        lienzo.getChildren().add(clientes);
       
        }
        
    }

    @FXML
    void Clientes(ActionEvent event) throws IOException {
        
        
        if (lienzo.getChildren().size()==2) {
        Pane clientes = FXMLLoader.load(getClass().getResource("/proyecto_hotel/interfaces/Clientes.fxml"));
        AjustePagina(clientes);
        lienzo.getChildren().add(clientes);
        }else{
        int  n= lienzo.getChildren().size()-1;
            for (int i = 2; i <=n; i++) {
                lienzo.getChildren().remove(2);
            }
        Pane clientes = FXMLLoader.load(getClass().getResource("/proyecto_hotel/interfaces/Clientes.fxml"));
        AjustePagina(clientes);
        lienzo.getChildren().add(clientes);
       
        }
    
        
    }

    @FXML
    void Detalle_servicio(ActionEvent event) throws IOException {
        
//        if (lienzo.getChildren().size()==2) {
//        Pane detalle_servicio = FXMLLoader.load(getClass().getResource("/proyecto_hotel/interfaces/Detalle_servicio.fxml"));
//        AjustePagina(detalle_servicio);
//        lienzo.getChildren().add(detalle_servicio);
//            
//        }else{
//       int  n= lienzo.getChildren().size()-1;
//            for (int i = 2; i <=n; i++) {
//                lienzo.getChildren().remove(2);
//            }
//            
//        Pane detalle_servicio = FXMLLoader.load(getClass().getResource("/proyecto_hotel/interfaces/Detalle_servicio.fxml"));
//        AjustePagina(detalle_servicio);
//        lienzo.getChildren().add(detalle_servicio);
//        }
        
          JOptionPane.showMessageDialog(null,"En construccion");
        
    }

    @FXML
    void Estancias(ActionEvent event) throws IOException {
        
//          if (lienzo.getChildren().size()==2) {
//        Pane estancias = FXMLLoader.load(getClass().getResource("/proyecto_hotel/interfaces/Estancias.fxml"));
//        AjustePagina(estancias);
//        lienzo.getChildren().add(estancias);  
//            
//        }else{
//       int  n= lienzo.getChildren().size()-1;
//            for (int i = 2; i <=n; i++) {
//                lienzo.getChildren().remove(2);
//            }
//        Pane estancias = FXMLLoader.load(getClass().getResource("/proyecto_hotel/interfaces/Estancias.fxml"));
//        AjustePagina(estancias);
//        lienzo.getChildren().add(estancias); 
//        }
       
   JOptionPane.showMessageDialog(null,"En construccion");        
    }

    @FXML
    void Inicio(ActionEvent event) throws IOException {
          if (lienzo.getChildren().size()==2) {
        Pane inicio = FXMLLoader.load(getClass().getResource("/proyecto_hotel/interfaces/Inicio.fxml"));
        AjustePagina(inicio);
        lienzo.getChildren().add(inicio); 
            
        }else{
         int  n= lienzo.getChildren().size()-1;
            for (int i = 2; i <=n; i++) {
                lienzo.getChildren().remove(2);
            }
        Pane inicio = FXMLLoader.load(getClass().getResource("/proyecto_hotel/interfaces/Inicio.fxml"));
        AjustePagina(inicio);
        lienzo.getChildren().add(inicio);
        }
        
     
        
    }

    @FXML
    void Reservas(ActionEvent event) throws IOException {
          if (lienzo.getChildren().size()==2) { 
        Pane reservas = FXMLLoader.load(getClass().getResource("/proyecto_hotel/interfaces/Reserva.fxml"));
        AjustePagina(reservas);
        lienzo.getChildren().add(reservas);
        }else{
         int  n= lienzo.getChildren().size()-1;
            for (int i = 2; i <=n; i++) {
                lienzo.getChildren().remove(2);
            }
        Pane reservas = FXMLLoader.load(getClass().getResource("/proyecto_hotel/interfaces/Reserva.fxml"));
        AjustePagina(reservas);
        lienzo.getChildren().add(reservas);
        }
        
        
       
        
    }

    @FXML
    void Servicio_cuarto(ActionEvent event) throws IOException {
          if (lienzo.getChildren().size()==2) {
        Pane servicio_cuarto = FXMLLoader.load(getClass().getResource("/proyecto_hotel/interfaces/Servicio_Cuarto.fxml"));
        AjustePagina(servicio_cuarto);
        lienzo.getChildren().add(servicio_cuarto); 
            
        }else{
        int  n= lienzo.getChildren().size()-1;
            for (int i = 2; i <=n; i++) {
                lienzo.getChildren().remove(2);
            }
        Pane servicio_cuarto = FXMLLoader.load(getClass().getResource("/proyecto_hotel/interfaces/Servicio_Cuarto.fxml"));
        AjustePagina(servicio_cuarto);
        lienzo.getChildren().add(servicio_cuarto);
        }
        
    }
     
    
    void AjustePagina(Node n){
        AnchorPane.setLeftAnchor(n, 205.00);
        AnchorPane.setTopAnchor(n, 60.00);
        AnchorPane.setRightAnchor(n, 0.00);
        AnchorPane.setBottomAnchor(n, 0.00);
    }

    
    @FXML
    void Habitaciones(ActionEvent event) throws IOException, SQLException {
        
          if (lienzo.getChildren().size()==2) {
        Pane Habitaciones = FXMLLoader.load(getClass().getResource("/proyecto_hotel/interfaces/Habitaciones.fxml"));
        AjustePagina(Habitaciones);
        lienzo.getChildren().add(Habitaciones);
            
        }else{
     int  n= lienzo.getChildren().size()-1;
            for (int i = 2; i <=n; i++) {
                lienzo.getChildren().remove(2);
            }
        Pane Habitaciones = FXMLLoader.load(getClass().getResource("/proyecto_hotel/interfaces/Habitaciones.fxml"));
        AjustePagina(Habitaciones);
        lienzo.getChildren().add(Habitaciones);
        }
        
  
        
    }
    
    @FXML
    void Productos(ActionEvent event) throws IOException {
         if (lienzo.getChildren().size()==2) {
        Pane Productos = FXMLLoader.load(getClass().getResource("/proyecto_hotel/interfaces/Productos.fxml"));
        AjustePagina(Productos);
        lienzo.getChildren().add(Productos);     
        }else{
        int  n= lienzo.getChildren().size()-1;
            for (int i = 2; i <=n; i++) {
                lienzo.getChildren().remove(2);
            }
        Pane Productos = FXMLLoader.load(getClass().getResource("/proyecto_hotel/interfaces/Productos.fxml"));
        AjustePagina(Productos);
        lienzo.getChildren().add(Productos); 
        }
        
    }
    
    String s;
    Format formatter;
    Date date = new Date();
      int cont=0,aux;
    void Fecha_Hora(){
        
        formatter = new SimpleDateFormat("EEEE, dd MMMM yyyy");
        s = formatter.format(date); 
        labFecha.setText(s+" |");
        
        Timeline clock = new Timeline(new KeyFrame(Duration.ZERO, e -> {            
        Calendar cal = Calendar.getInstance();
        int segundos = cal.get(Calendar.SECOND);
        int minutos = cal.get(Calendar.MINUTE);
        int hora = cal.get(Calendar.HOUR);
        //System.out.println(hour + ":" + (minute) + ":" + second);
        if (segundos<10) {
            labHora.setText(" "+(hora)+":"+(minutos)+":0"+(segundos)+" |");    
        }else{
            labHora.setText(" "+(hora)+":"+(minutos)+":"+(segundos)+" ");
        }
        
        }),
             new KeyFrame(Duration.seconds(1))
        );
        clock.setCycleCount(Animation.INDEFINITE);
        clock.play();
    
        
    }
    
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        String d = "src\\proyecto_hotel\\imagenes\\";
        img_logo.setImage(new Image(new File(d+"logo.png").toURI().toString()));
        Fecha_Hora();
         Audit_habitacion();
        Cargar_Inicio();
    }    

    void Cargar_Inicio(){
                if (lienzo.getChildren().size()==2) {
            try {
                Pane inicio = FXMLLoader.load(getClass().getResource("/proyecto_hotel/interfaces/Inicio.fxml"));
                AjustePagina(inicio); 
                lienzo.getChildren().add(inicio);
            } catch (IOException ex) {
                Logger.getLogger(MenuController.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        }else{
            try {
                int  n= lienzo.getChildren().size()-1;
                for (int i = 2; i <=n; i++) {
                    lienzo.getChildren().remove(2);
                }
                Pane inicio = FXMLLoader.load(getClass().getResource("/proyecto_hotel/interfaces/Inicio.fxml"));
                AjustePagina(inicio);
                lienzo.getChildren().add(inicio);
            } catch (IOException ex) {
                Logger.getLogger(MenuController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }}
 public static void Audit_habitacion(){
   
         try {
           connection = (Connection) DriverManager.getConnection(c.getString_connection(), c.getUsername(), c.getPassword());
            Statement stm = (Statement) connection.createStatement();
            String query = "  call    Audit_Habitacione();";
            stm.executeQuery(query);
         } catch (SQLException ex) {
             System.out.println("error al extraer id reserva");
         }
    
   
    }   
}
