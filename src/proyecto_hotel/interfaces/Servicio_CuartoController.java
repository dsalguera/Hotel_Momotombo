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
import java.io.IOException;
import java.net.URL;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Callback;
import javafx.util.Duration;
import javax.swing.JOptionPane;
import proyecto_hotel.Conexion;
import proyecto_hotel.clases.Productos;
import proyecto_hotel.clases.Servicio;
import proyecto_hotel.clases.Servicio_cuarto;
import static proyecto_hotel.interfaces.ReservaController.Id_Cliente;
import static proyecto_hotel.interfaces.ReservaController.imagen_cliente;
import static proyecto_hotel.interfaces.ReservaController.nombre_cliente;

/**
 * FXML Controller class
 *
 * @author David Salguera
 */
public class Servicio_CuartoController implements Initializable {

    @FXML
    private HBox boxContenedor;

    @FXML
    private ComboBox<?> combo_buscar;

    @FXML
    private JFXTextField txtbuscar;

    @FXML
    private JFXButton btnBuscar;

    @FXML
    private VBox boxCambios;

    @FXML
    private JFXTextField txtidestancia;

    @FXML
    private JFXButton btnBuscarEstancia;

    @FXML
    private ImageView screen_img;

    @FXML
    private JFXButton btnBuscarProducto;

    @FXML
    private JFXTextField txtprecio;

    @FXML
    private JFXTextField txtcantidad;

    @FXML
    private JFXTextField txtsubtotal;

    @FXML
    private HBox panel_edicion;

    @FXML
    private JFXButton btnNuevo;

    @FXML
    private JFXButton btnGuardar;
    
    @FXML
    private ListView<Servicio_cuarto> lista_productos;

    @FXML
    private ListView<Servicio> lista_servicios;

    @FXML
    private ListView<Servicio_cuarto> lista_agrega;

    
    @FXML
    void Buscar_estancia(ActionEvent event) {
        
        stage_buscar.close();
        if (!stage_buscar.isShowing()) {
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("/proyecto_hotel/interfaces/Ventana_Estancia.fxml"));            
        stage_buscar = new Stage();   
        Scene scene = null;       
            try { 
                
                scene = new Scene(fxmlLoader.load(),Color.TRANSPARENT);
            } catch (IOException ex) {
                Logger.getLogger(ReservaController.class.getName()).log(Level.SEVERE, null, ex);
            }
            stage_buscar.setTitle("Buscar Estancia");
            stage_buscar.setScene(scene);
            id_estanciaC=-1;
            stage_buscar.show();
            stage_buscar.setOnHidden(new EventHandler<WindowEvent> (){
            @Override
                public void handle(WindowEvent event) {
                    if (id_estanciaC!=-1) {
                        try {
                            Estancia_Seleccionada();
                        } catch (Exception ex) {
                            System.out.println(""+ex);
                            Logger.getLogger(ReservaController.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                }   
            });
        
        }
    }
    
    void Estancia_Seleccionada(){
        
        txtidestancia.setText(""+id_estanciaC);

    }
    
    void Nuevo(){
        screen_img.setImage(null);
        txtidestancia.setText("");
        txtprecio.setText("");
        txtcantidad.setText("");
        txtsubtotal.setText("");
    }

    @FXML
    void Guardar_Registro(ActionEvent event) {
        
        try {
            Connection connection = (Connection) DriverManager.getConnection(c.getString_connection(), c.getUsername(), c.getPassword());
            Statement stm = (Statement) connection.createStatement();
            
            DateFormat hourdateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            fecha_hora = hourdateFormat.format(date);
            
            String query = "insert into servicio_al_cuarto (id_servicio_cuarto,id_estancia,fecha_hora,Costo_total) " +
                            "values ("+id_servicio_cuarto+","+id_estancia+",'"+fecha_hora+"',0);";
            
            stm.executeUpdate(query);
            
            for (Servicio_cuarto servicio : data){
                
                     query = "call Inserta_Servicio("+id_servicio_cuarto+","
                        + ""+servicio.getId_producto()+","
                        + ""+servicio.getPrecio()+","+servicio.getCantidad()+","
                        +servicio.getSubtotal()+");";
                
                try {
                    stm.executeUpdate(query);
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(null,""+ex.getMessage());
                }
            }

            Crear_Lista_Servicio("select * from servicio_al_cuarto;");
            Dialogo("Se ha guardado el registro.", "Exito al Guardar!",
            "Operación Realizada", Alert.AlertType.CONFIRMATION,1); 
            
            lista_agrega.getItems().clear();
            data.clear();
            Nuevo();
            
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null,""+ex.getMessage());
        }
        
    }

    @FXML
    void Nuevo_Registro(ActionEvent event) throws SQLException {
        ValidaC();
    }

    static int id_producto;
    static int id_estanciaC;
    static String nombre_producto;
    static double precio_producto;
    static int cantidad;
    static String tipo_producto;
    static Image imagen_producto;
    static int estadoP;
    Stage stage_buscar=new Stage();

    @FXML
    void Buscar_producto(ActionEvent event) {
        
        stage_buscar.close();
        if (!stage_buscar.isShowing()) {
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("/proyecto_hotel/interfaces/Ventana_Productos.fxml"));            
        stage_buscar = new Stage();   
        Scene scene = null;       
            try { 
                
                scene = new Scene(fxmlLoader.load(),Color.TRANSPARENT);
            } catch (IOException ex) {
                Logger.getLogger(ReservaController.class.getName()).log(Level.SEVERE, null, ex);
            }
            stage_buscar.setTitle("Buscar Producto");
            stage_buscar.setScene(scene);
            id_producto=-1;
            stage_buscar.show();
            stage_buscar.setOnHidden(new EventHandler<WindowEvent> (){
            @Override
                public void handle(WindowEvent event) {
                    if (id_producto!=-1) {
                        try {
                            Producto_Seleccionado();
                        } catch (Exception ex) {
                            System.out.println(""+ex);
                            Logger.getLogger(ReservaController.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                }   
            });
        
        }
    }
    
    boolean Valida(){
        if (txtidestancia.getText().equals("") || txtcantidad.getText().equals("")) {
            
            Dialogo("Al parecer hay algunos campos que necesitan ser rellenados.", "¡Necesita rellenar todos los campos!",
                    "Error", Alert.AlertType.ERROR,1);
            
        }else{
            
            if (screen_img.getImage() == null) {
                Dialogo("Al parecer necesita agregar una imagen.", "¡No imagen!",
                    "Error", Alert.AlertType.ERROR,1);
            }else{
                
                return true;
                
            }
        }
        
        return false;
    }
    
    String fecha_hora = null;
    Format formatter;
    Date date = new Date();
    
    Conexion c = new Conexion();
    Connection connection ;
    int id_servicio_cuarto = 0;
    int id_estancia = 0;
    
    void Confirmar_Producto() throws SQLException{
        
            connection = (Connection) DriverManager.getConnection(c.getString_connection(), c.getUsername(), c.getPassword());
            Statement stm = (Statement) connection.createStatement();
        
            ResultSet rs = stm.executeQuery("select count(*)+1 as Conteo from Servicio_al_cuarto;");
        
            while (rs.next()) {
                id_servicio_cuarto = rs.getInt("Conteo");
            }
        
            id_estancia = Integer.parseInt(txtidestancia.getText());
            Image imagen = imagen_producto;
            String nombre = nombre_producto;
            double precio = precio_producto;
            String tipo = tipo_producto;
            int estado = estadoP;
            String fecha = fecha_hora;
            double sub_total = Double.parseDouble(txtsubtotal.getText());

            Servicio_cuarto sevicio = new Servicio_cuarto(
                            id_servicio_cuarto, id_estancia, 
                            fecha, sub_total, imagen_producto, id_producto, 
                            nombre, precio, cantid, sub_total
            );

            data.add(sevicio);
            
        
    }
    
    void Crear_Add_Prod() throws SQLException{
        
        lista_agrega.getItems().clear();
        dataServicios.clear();
        
//        try {
//            ConexionServiciosC(query);
//        } catch (SQLException ex) {
//            
//        }

        Confirmar_Producto();
        
        lista_agrega.getItems().addAll(data);
        
        lista_agrega.setCellFactory(new Callback<ListView<Servicio_cuarto>, ListCell<Servicio_cuarto>>() {

        @Override
        public ListCell<Servicio_cuarto> call(ListView<Servicio_cuarto> arg0) {
        return new ListCell<Servicio_cuarto>() {

            @Override
            protected void updateItem(Servicio_cuarto item, boolean bln) {
            super.updateItem(item, bln);
            if (item != null) {
            
            Label id_servicio_cuarto,id_producto,nombre,precio,cantidad,subtotal;
            ImageView imagen;
            
            VBox vBox = new VBox(
                    
                id_servicio_cuarto = new Label("Id Servicio Cuarto: "+item.getId_servicio_al_cuarto()), 
                    
                id_producto = new Label("Id Producto: "+item.getId_producto()), 
                nombre = new Label("Nombre: "+item.getNombre_producto()),
                precio = new Label("Precio: "+item.getPrecio()),
                cantidad = new Label("Cantidad: "+item.getCantidad()),
                subtotal = new Label("Subtotal: "+item.getSubtotal())
                );
             
                    id_servicio_cuarto.getStyleClass().add("espacio");
                    id_producto.getStyleClass().add("espacio");
                    nombre.getStyleClass().add("espacio");
                    precio.getStyleClass().add("espacio");
                    cantidad.getStyleClass().add("espacio");
                    subtotal.getStyleClass().add("espacio");
                    
                    HBox hBox = new HBox(
                            
                   imagen = new ImageView(item.getImagenProducto()), 
                            vBox);
                    imagen.setFitHeight(166/1.5);
                    imagen.setFitWidth(250/1.5);
                    
                    hBox.setSpacing(10);
                    vBox.setSpacing(5);
                    setGraphic(hBox);
                            
                        }
                    }

                };
            }
        
            

        });
        
    } 
    
    void Producto_Seleccionado(){
        
        screen_img.setImage(imagen_producto);
        txtprecio.setText(""+precio_producto);

    }

    @FXML
    void buscar(KeyEvent event) throws SQLException {
        consulta();
    }
    
    @FXML
    void consulta() throws SQLException {
    
        String busq = txtbuscar.getText();
        String filtro = (String) combo_buscar.getSelectionModel().getSelectedItem();
        Crear_Lista_Servicio("select * from servicio_al_cuarto where "+filtro+" like '%"+(busq)+"%';");
     
    }
    
    int id = 0;

    @FXML
    void click(MouseEvent event) {
        
        
        
    }
    
    int cantid;
    
    void ValidaC() throws SQLException{
        
        if (Valida() == true) {
            
            cantid = Integer.parseInt(txtcantidad.getText());
            
            if (cantid>cantidad) {
                Dialogo("La cantidad introducida rebasa las unidades del almacen.\nUnidades: "+cantidad+"\nIntroducido: "+cantid+"", "No hay suficientes unidades!",
                    "Error", Alert.AlertType.ERROR,1);
            }else{
                txtsubtotal.setText(""+cantid * Double.parseDouble(txtprecio.getText()));
                Crear_Add_Prod();
            }   
            
        }
              
    }
    
    void Dialogo(String mensaje, String cabecera, String titulo, Alert.AlertType e, int SoloOk){
        
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
                //Accion_Dialogo();

            } else if(result.get() == buttonTypeCancel){
                // ... user chose CANCEL or closed the dialog
                alert.close();
            }
        }
        
    }
       
    @FXML
    void clickDentro(MouseEvent event) {
        
        id = lista_servicios.getSelectionModel().getSelectedItem().getId_servicio_cuarto();
        Crear_Lista_Prod("select * from Servicio_al_cuarto s inner join detalle_servicio_al_cuarto d " +
        "on s.Id_servicio_cuarto = d.Id_servicio_cuarto inner join Producto p on " +
        "p.Id_producto = d.Id_producto where s.id_servicio_cuarto = "+id+";");
        
    }
    
    ObservableList<Servicio> dataServ = FXCollections.observableArrayList();
    ObservableList<Servicio_cuarto> dataServicios = FXCollections.observableArrayList();
    ObservableList<Servicio_cuarto> data = FXCollections.observableArrayList();
    String dir = "src\\proyecto_hotel\\imagenes\\productos\\";

    
    void ConexionServicios(String query) throws SQLException{ 
        
        connection = (Connection) DriverManager.getConnection(c.getString_connection(), c.getUsername(), c.getPassword());
        Statement stm = (Statement) connection.createStatement();
        
        ResultSet rs = stm.executeQuery(query);
        
         while (rs.next()) {
                
                int id_servicio_cuarto = rs.getInt("Id_servicio_cuarto");
                int id_estancia = rs.getInt("id_estancia");
                String fecha_hota = rs.getString("fecha_hora");
                double costo_total = rs.getDouble("Costo_total");
                
                
                Servicio serv = new Servicio(id_servicio_cuarto, id_estancia, fecha_hota, costo_total);
        
                dataServ.add(serv);
                
        }
        
    }
    
    void ConexionServiciosC(String query) throws SQLException{ 
        
        connection = (Connection) DriverManager.getConnection(c.getString_connection(), c.getUsername(), c.getPassword());
        Statement stm = (Statement) connection.createStatement();
        
        ResultSet rs = stm.executeQuery(query);
        
         while (rs.next()) {
                
                int id_detalle_servicio_al_cuarto = rs.getInt("id_detalle_servicio_al_cuarto");
                int id_servicio_cuarto = rs.getInt("Id_servicio_cuarto");
                int id_estancia = rs.getInt("id_estancia");
                String fecha_hota = rs.getString("fecha_hora");
                double costo_total = rs.getDouble("Costo_total");
                String imagen = rs.getString("p.Imagen");
                
                Image imagenProducto = new Image(new File(dir+imagen).toURI().toString());
                
                int id_producto = rs.getInt("id_producto");
                String nombre = rs.getString("p.nombre");
                
                double precio = rs.getDouble("precio");
                int cantidad = rs.getInt("cantidad");
                double sub_total = rs.getDouble("Sub_total");
                
                Servicio_cuarto sevicio = new Servicio_cuarto
        
                (id_servicio_cuarto, id_estancia, 
                        fecha_hota, sub_total, imagenProducto, id_producto, 
                        nombre, precio, cantidad, sub_total);
                        
                
                dataServicios.add(sevicio);
                
        }
        
    }
    
    
    void Crear_Lista_Servicio(String query){
        
        lista_servicios.getItems().clear();
        dataServ.clear();
        
        try {
            ConexionServicios(query);
        } catch (SQLException ex) {
            
        }
        
        lista_servicios.getItems().addAll(dataServ);
        
        lista_servicios.setCellFactory(new Callback<ListView<Servicio>, ListCell<Servicio>>() {

        @Override
        public ListCell<Servicio> call(ListView<Servicio> arg0) {
        return new ListCell<Servicio>() {

            @Override
            protected void updateItem(Servicio item, boolean bln) {
            super.updateItem(item, bln);
            if (item != null) {
            
            Label id_servicio_cuarto,id_estancia,fecha_hora,
                    costo_total,id_detalle_servicio_al_cuarto,
                    id_producto,precio,cantidad,sub_total;
            
            VBox vBox = new VBox(
                    
                id_servicio_cuarto = new Label("Id Servicio Cuarto: "+item.getId_servicio_cuarto()), 
                    
                id_estancia = new Label("Estancia: "+item.getId_estancia()), 
                fecha_hora = new Label("Fecha Hora: "+item.getFecha_hota()),
                costo_total = new Label("Costo Total: "+item.getCosto_total())                
                );
             
//                    id_servicio_cuarto.getStyleClass().add("espacio");
//                    id_estancia.getStyleClass().add("espacio");
//                    fecha_hora.getStyleClass().add("espacio");
//                    costo_total.getStyleClass().add("espacio");
//                    id_detalle_servicio_al_cuarto.getStyleClass().add("espacio");
                    
                    
                    HBox hBox = new HBox(
                            
//                    imagen = new ImageView(item.getImagen()), 
                            vBox);
//                    imagen.setFitHeight(166/1.5);
//                    imagen.setFitWidth(250/1.5);
//                    
                    hBox.setSpacing(10);
                    vBox.setSpacing(5);
                    setGraphic(hBox);
                            
                        }
                    }

                };
            }
        
            

        });
        
    }
    
    void Crear_Lista_Prod(String query){
        
        lista_productos.getItems().clear();
        dataServicios.clear();
        
        try {
            ConexionServiciosC(query);
        } catch (SQLException ex) {
            
        }
        
        lista_productos.getItems().addAll(dataServicios);
        
        lista_productos.setCellFactory(new Callback<ListView<Servicio_cuarto>, ListCell<Servicio_cuarto>>() {

        @Override
        public ListCell<Servicio_cuarto> call(ListView<Servicio_cuarto> arg0) {
        return new ListCell<Servicio_cuarto>() {

            @Override
            protected void updateItem(Servicio_cuarto item, boolean bln) {
            super.updateItem(item, bln);
            if (item != null) {
            
            Label id_servicio_cuarto,id_producto,nombre,precio,cantidad,subtotal;
            ImageView imagen;
            
            VBox vBox = new VBox(
                    
                id_servicio_cuarto = new Label("Id Servicio Cuarto: "+item.getId_servicio_al_cuarto()), 
                    
                id_producto = new Label("Id Producto: "+item.getId_producto()), 
                nombre = new Label("Nombre: "+item.getNombre_producto()),
                precio = new Label("Precio: "+item.getPrecio()),
                cantidad = new Label("Cantidad: "+item.getCantidad()),
                subtotal = new Label("Subtotal: "+item.getSubtotal())
                );
             
//                    id_servicio_cuarto.getStyleClass().add("espacio");
//                    id_estancia.getStyleClass().add("espacio");
//                    fecha_hora.getStyleClass().add("espacio");
//                    costo_total.getStyleClass().add("espacio");
//                    id_detalle_servicio_al_cuarto.getStyleClass().add("espacio");
                    
                    HBox hBox = new HBox(
                            
                   imagen = new ImageView(item.getImagenProducto()), 
                            vBox);
                    
                    imagen.setFitHeight(166/1.5);
                    imagen.setFitWidth(250/1.5);
                    
                    hBox.setSpacing(10);
                    vBox.setSpacing(5);
                    setGraphic(hBox);
                            
                        }
                    }

                };
            }
        
            

        });
        
    }
    
    int tipo_usuario;
    String nombre_usuario;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        txtprecio.setDisable(true);
        txtsubtotal.setDisable(true);
        Crear_Lista_Servicio("select * from servicio_al_cuarto;");
        
        ObservableList busquedas = FXCollections.observableArrayList();
        busquedas.add("Id_Servicio_Cuarto");
        busquedas.add("Id_Estancia");
        busquedas.add("Fecha_Hora");
        busquedas.add("Costo_Total");
        
        combo_buscar.getItems().addAll(busquedas);
        
        txtcantidad.setOnKeyTyped(event -> {
            String string =  txtcantidad.getText();
          
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
        });
        
 
        
    }    
    
}
