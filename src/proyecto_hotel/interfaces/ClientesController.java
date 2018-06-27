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
import java.text.Format;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeFormatter;
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
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Callback;
import proyecto_hotel.Conexion;
import proyecto_hotel.clases.Clientes;
import proyecto_hotel.clases.Habitaciones;
import proyecto_hotel.clases.Productos;

/**
 * FXML Controller class
 *
 * @author David Salguera
 */
public class ClientesController implements Initializable {

    
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
    private ListView<Clientes> lista_clientes;

    @FXML
    private JFXToggleButton check_estado;

    @FXML
    private JFXTextField txtreserva;

    @FXML
    private JFXTextField txtestancia;
    
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
    private HBox boxContenedor;
    
    @FXML
    private VBox boxCambios;
    
    @FXML
    private JFXTextField txttelefono;

    @FXML
    private JFXTextField txtcorreo;

    @FXML
    private JFXButton btnNuevo;
    
    @FXML
    private JFXButton btnEditar;

    @FXML
    private JFXButton btnGuardar;

    @FXML
    private JFXButton btnEliminar;
    
    // Variables universales
    int id = 0;
    Image imagen;
    

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
        String query = "select * from Cliente where "+filtro+" like '%"+(busq)+"%'";
        
        ResultSet rs = stm.executeQuery(query);
        
        while (rs.next()) {
                
                String imagen = rs.getString("imagen");
                String primerNombre = rs.getString("Primer_nombre");
                String segundoNombre = rs.getString("Segundo_nombre");
                String primerApellido = rs.getString("Primer_apellido");
                String segundoApellido = rs.getString("Segundo_apellido");
                String identificacion = rs.getString("Identificacion");
                String tipo = rs.getString("Tipo_identificacion");
                String pais = rs.getString("Pais_origen");
                int numero_reserva = rs.getInt("Numero_reserva");
                int numero_estancia = rs.getInt("Numero_estancia");
                float gpa = rs.getFloat("GPA");
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
                        fecha_nacimiento, 
                        fecha_inscripcion, 
                        telefono, 
                        correo);
                
                data.add(clientes);
                
                
        }
        Crear_Lista(query);
     
    }

    @FXML
    void click(MouseEvent event) {
        
        Editar();
        
        id = lista_clientes.getSelectionModel().getSelectedItem().getId();
        String primerNombre = lista_clientes.getSelectionModel().getSelectedItem().getPrimerNombre();
        String segundoNombre = lista_clientes.getSelectionModel().getSelectedItem().getSegundoNombre();
        String primerApellido = lista_clientes.getSelectionModel().getSelectedItem().getPrimerApellido();
        String segundoApellido = lista_clientes.getSelectionModel().getSelectedItem().getSegundoApellido();
        
        String identificacion = lista_clientes.getSelectionModel().getSelectedItem().getIdentificacion();
        String tipo = lista_clientes.getSelectionModel().getSelectedItem().getTipo();
        String pais = lista_clientes.getSelectionModel().getSelectedItem().getPais();
        int numero_reserva = lista_clientes.getSelectionModel().getSelectedItem().getNumero_reserva();
        int numero_estancia = lista_clientes.getSelectionModel().getSelectedItem().getNumero_estancia();
        
        String fecha_inscripcion = lista_clientes.getSelectionModel().getSelectedItem().getFecha_inscripcion();
        String fecha_nacimiento = lista_clientes.getSelectionModel().getSelectedItem().getFecha_nacimiento();
        String telefono = lista_clientes.getSelectionModel().getSelectedItem().getTelefono();
        String correo = lista_clientes.getSelectionModel().getSelectedItem().getCorreo();
        
        Image imagen = lista_clientes.getSelectionModel().getSelectedItem().getImagen();
        
        pNombre = lista_clientes.getSelectionModel().getSelectedItem().getPrimerNombre();
        pApellido = lista_clientes.getSelectionModel().getSelectedItem().getPrimerApellido();
        
        txtp_nombre.setText(primerNombre);
        txts_nombre.setText(segundoNombre);
        txtp_apellido.setText(primerApellido);
        txts_apellido.setText(segundoApellido);
        
        txtidentificacion.setText(identificacion);
        txtpais.setText(pais);
        combo_tipo.setValue(tipo);
        txtreserva.setText(""+numero_reserva);
        txtestancia.setText(""+numero_estancia);
        
        
        int[] nacimiento = Arrays.stream(fecha_nacimiento.trim().split("-"))
                      .mapToInt(Integer::parseInt)
                      .toArray();
        
        int[] inscripcion = Arrays.stream(fecha_inscripcion.trim().split("-"))
                      .mapToInt(Integer::parseInt)
                      .toArray();
                
        
        fechanac.setValue(LocalDate.of(nacimiento[0],nacimiento[1],nacimiento[2]));
        this.fecha_inscripcion.setValue(LocalDate.of(inscripcion[0],inscripcion[1],inscripcion[2]));
        
        txtcorreo.setText(correo);
        screen_img.setImage(imagen);
        txttelefono.setText(telefono);
        
    }
    
    Conexion c = new Conexion();
    Connection connection ;
    ObservableList<Clientes> data = FXCollections.observableArrayList();
    String dir = "src\\proyecto_hotel\\imagenes\\clientes\\";
    
    void Conexion(String query) throws SQLException{ 
        
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
        
    }
    
    void Crear_Lista(String query){
        
        lista_clientes.getItems().clear();
        data.clear();
        
        try {
            Conexion(query);
        } catch (SQLException ex) {
            Logger.getLogger(HabitacionesController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        lista_clientes.getItems().addAll(data);
        
        lista_clientes.setCellFactory(new Callback<ListView<Clientes>, ListCell<Clientes>>() {

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
    
    String pNombre, pApellido;
    
    @FXML
    void Editar_Registro(ActionEvent event) throws SQLException {
        
        String primerNombre = txtp_nombre.getText().toString();
        String segundoNombre = txts_nombre.getText().toString();
        String primerApellido = txtp_apellido.getText().toString();
        String segundoApellido = txts_apellido.getText().toString();
        
        String identificacion = txtidentificacion.getText().toString();
        String tipo = combo_tipo.getSelectionModel().getSelectedItem().toString();
        String pais = txtpais.getText().toString();
        int numero_reserva = Integer.parseInt(txtreserva.getText());
        int numero_estancia = Integer.parseInt(txtestancia.getText());
        
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
            data.add(new Clientes(imagen,id,primerNombre,segundoNombre,primerApellido,segundoApellido,
            identificacion,tipo,pais,numero_reserva,numero_estancia,fecha_inscripcion,fecha_nacimiento,telefono,correo));
            
            if (click == 1) {
            
                    query = "Update Cliente set Primer_nombre = '"+primerNombre+"',"
                           +"Segundo_nombre = '"+segundoNombre+"',"
                           +"Primer_apellido = '"+primerApellido+"',"
                           +"Segundo_apellido = '"+segundoApellido+"',"
                           +"Identificacion = '"+identificacion+"',"
                           +"Tipo_identificacion = '"+tipo+"',"
                           +"Pais_origen = '"+pais+"',"
                           +"Numero_reserva = '"+numero_reserva+"',"
                           +"Numero_estancia = '"+numero_estancia+"',"
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
                           +"Numero_reserva = '"+numero_reserva+"',"
                           +"Numero_estancia = '"+numero_estancia+"',"
                           +"Fecha_inscripcion = '"+fecha_inscripcion+"',"
                           +"Fecha_nacimiento = '"+fecha_nacimiento+"',"
                           +"Telefono = '"+telefono+"',"
                           +"Correo = '"+correo+"' "
                           +"where Id_cliente = "+id+";";

            }
        
            click = 0;
            stm.executeUpdate(query);
            Crear_Lista("select * from Cliente where Eliminado = 0;");
            
            Dialogo("Se ha editado el registro.", "Exito al Editar!",
            "Operación Realizada", Alert.AlertType.CONFIRMATION);
            
            
        } else if(result.get() == buttonTypeCancel){
            // ... user chose CANCEL or closed the dialog
            alert.close();
        }
        
    }
    
    

    @FXML
    void Eliminar_Registro(ActionEvent event) throws SQLException {
        
        String primerNombre = txtp_nombre.getText().toString();
        String segundoNombre = txts_nombre.getText().toString();
        String primerApellido = txtp_apellido.getText().toString();
        String segundoApellido = txts_apellido.getText().toString();
        

        Connection connection = (Connection) DriverManager.getConnection(c.getString_connection(), c.getUsername(), c.getPassword());
        Statement stm = (Statement) connection.createStatement();
        String query = null;
        
        Alert alert = new Alert(AlertType.WARNING);
        alert.setTitle("¿Confirmar Acción?");
        alert.setHeaderText("¿Está seguro que desea eliminar el cliente "+primerNombre+" "+primerApellido+" de la base?");
        alert.setContentText("Si lo elimina, no podrá acceder luego a este registro.");
        
        ButtonType buttonTypeCancel = new ButtonType("Cancelar", ButtonData.CANCEL_CLOSE);
        ButtonType buttonTypeOk = new ButtonType("Aceptar", ButtonData.OK_DONE);
        
        alert.getButtonTypes().setAll(buttonTypeCancel,buttonTypeOk);

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == buttonTypeOk){
            // ... user chose OK
            query = "Update Cliente set Eliminado = "+1+" "
                   +"where Id_cliente = "+id+" and Primer_nombre = '"+primerNombre+"';";
            click = 0;
            stm.executeUpdate(query);
            Crear_Lista("select * from Cliente where Eliminado = 0;");
            
            
            Dialogo("Se ha eliminado el registro.", "Exito al Borrar!",
            "Operación Realizada", Alert.AlertType.CONFIRMATION);
            
            
        } else if(result.get() == buttonTypeCancel){
            // ... user chose CANCEL or closed the dialog
            alert.close();
        }
        
        
    }
    
    @FXML
    void Nuevo_Registro(ActionEvent event) {
        
        
        txtp_nombre.setText("");
        txts_nombre.setText("");
        txtp_apellido.setText("");
        txts_apellido.setText("");
        
        txtidentificacion.setText("");
        txtpais.setText("");
        txtreserva.setText("");
        txtestancia.setText("");
        fechanac.setValue(null);
        fecha_inscripcion.setValue(null);
        txttelefono.setText("");
        txtcorreo.setText("");
        
        Nuevo();
    }

    @FXML
    void Guardar_Registro(ActionEvent event) throws SQLException {
        
        if (Valida() == true) {
            
            String primerNombre = txtp_nombre.getText();
            String segundoNombre = txts_nombre.getText();
            String primerApellido = txtp_apellido.getText();
            String segundoApellido = txts_apellido.getText();

            String identificacion = txtidentificacion.getText();
            String tipo = combo_tipo.getSelectionModel().getSelectedItem();
            String pais = txtpais.getText();


            int numero_reserva = 0, numero_estancia = 0;

            if (txtreserva.getText().equals("") && txtestancia.getText().equals("")) {
                numero_reserva = 0;
                numero_estancia = 0;
            }else{
                numero_reserva = Integer.parseInt(txtreserva.getText());
                numero_estancia = Integer.parseInt(txtestancia.getText());
            }

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


            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("¿Confirmar Acción?");
            alert.setHeaderText("¿Está seguro que desea guardar el cliente "+primerNombre+" "+primerApellido+" a la base?");
            alert.setContentText("Se guardará la información con los datos introcucidos en los campos.\nPara Guardar, presione aceptar.");

            ButtonType buttonTypeCancel = new ButtonType("Cancelar", ButtonBar.ButtonData.CANCEL_CLOSE);
            ButtonType buttonTypeOk = new ButtonType("Aceptar", ButtonBar.ButtonData.OK_DONE);

            alert.getButtonTypes().setAll(buttonTypeCancel,buttonTypeOk);

            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == buttonTypeOk){
                // ... user chose OK
                data.add(new Clientes(imagen,id,primerNombre,segundoNombre,primerApellido,segundoApellido,
                identificacion,tipo,pais,numero_reserva,numero_estancia,fecha_inscripcion,fecha_nacimiento,telefono,correo));

                query = "insert into Cliente " 
                    +"(Primer_nombre,Segundo_nombre,Primer_apellido,Segundo_apellido,Identificacion,Tipo_identificacion,Pais_origen,Numero_reserva,Numero_estancia,\n" 
                    +" Fecha_inscripcion,Fecha_nacimiento,Telefono,Correo,Imagen,Eliminado) values " 
                    +"('"+primerNombre+"','"+segundoNombre+"','"+primerApellido+"','"+segundoApellido+"','"+identificacion+"','"+tipo+"','"+pais+"',"
                    + ""+numero_reserva+","+numero_estancia+",'"+fecha_inscripcion+"','"+fecha_nacimiento+"','"+telefono+"','"+correo+"','"+nombre_img+"',0);";

                stm.executeUpdate(query);

                Crear_Lista("select * from Cliente where Eliminado = 0;");
                
                Dialogo("Se ha guardado el registro.", "Exito al Guardar!",
                    "Operación Realizada", Alert.AlertType.CONFIRMATION);

            } else if(result.get() == buttonTypeCancel){
                // ... user chose CANCEL or closed the dialog
                alert.close();
            }
            
        }
        
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
    
    String d = "src\\proyecto_hotel\\imagenes\\login\\";
    
    void Nuevo(){
        screen_img.setImage(new Image(new File(d+"no_image.jpg").toURI().toString()));
        btnCambiarImagen.setText("Seleccionar Imagen");
        screen_img.setImage(null);
        Fecha_hoy();
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
    
    int Tipo_usuario_copia;
    public void Botones(int type){
        
        if (type == 2) {
            panel_edicion.getChildren().remove(btnEditar);
            panel_edicion.getChildren().remove(btnEliminar);
        }else if (type == 3) {
            boxContenedor.getChildren().remove(boxCambios);
        }
        
    }
    
    boolean Valida(){
        if (txtp_nombre.getText().equals("") || txtp_apellido.getText().equals("")
                || txts_nombre.getText().equals("") || txts_apellido.getText().equals("")
                || txtidentificacion.getText().equals("") || txtpais.getText().equals("")
                || txttelefono.getText().equals("") || txtcorreo.getText().equals("")
                || combo_tipo.getSelectionModel().isEmpty()
                || fechanac.getValue() == null) {
            
            Dialogo("Al parecer hay algunos campos que necesitan ser rellenados.", "¡Necesita rellenar todos los campos!",
                    "Error", Alert.AlertType.ERROR);
            
        }else{
            
            if (screen_img.getImage().equals(null)) {
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


    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        Tipo_usuario_copia = MenuController.tipo_usuario;
        Botones(Tipo_usuario_copia);
        
        boxCambios.getChildren().remove(box_estancia_reserva);
        
        ObservableList busquedas = FXCollections.observableArrayList();
        ObservableList tipoID = FXCollections.observableArrayList();
        busquedas.add("Primer_nombre");
        busquedas.add("Primer_apellido");
        busquedas.add("Identificacion");
        
        tipoID.add("Cedula");
        tipoID.add("Nacionalidad");
        
        combo_buscar.getItems().addAll(busquedas);
        combo_tipo.getItems().addAll(tipoID);
        
        txtreserva.setEditable(false);
        txtestancia.setEditable(false);
        fecha_inscripcion.setEditable(false);
        fecha_inscripcion.setDisable(true);
        
        Crear_Lista("select * from Cliente where Eliminado = 0;");
        
        Nuevo();
    }    
    
}
