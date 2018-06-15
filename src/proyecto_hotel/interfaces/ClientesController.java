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
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
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
    int id;
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
                        fecha_inscripcion, 
                        fecha_nacimiento, 
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
                    
                Nombres = new Label("Nombres: "+item.getPrimerNombre()+" "+item.getSegundoNombre()),
                Apellidos = new Label("Apellidos: "+item.getPrimerApellido()+" "+item.getSegundoApellido()),    
                Identificacion = new Label("Identificacion: "+item.getIdentificacion()),
                Tipo = new Label("Tipo: "+item.getTipo()), 
                Pais = new Label("Pais: "+item.getPais()),
                NumeroReserva = new Label("# Reserva: "+item.getNumero_reserva()), 
                NumeroEstancia = new Label("# Estancia: "+item.getNumero_estancia()), 
                Inscripcion = new Label("Fecha de Inscripcion: "+item.getFecha_inscripcion()), 
                Nacimiento = new Label("Fecha de Nacimiento: "+item.getFecha_nacimiento()),      
                Telefono = new Label("Telefono: "+item.getTelefono()),     
                Correo = new Label("Correo: "+item.getCorreo())        
                        
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
        
        data.add(new Clientes(imagen,id,primerNombre,segundoNombre,primerApellido,segundoApellido,
        
        identificacion,tipo,pais,numero_reserva,numero_estancia,fecha_inscripcion,fecha_nacimiento,telefono,correo));

        Connection connection = (Connection) DriverManager.getConnection(c.getString_connection(), c.getUsername(), c.getPassword());
        Statement stm = (Statement) connection.createStatement();
        String query = null;
        
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
        Crear_Lista("select * from Cliente;");
    }

    @FXML
    void Eliminar_Registro(ActionEvent event) {

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
        
        data.add(new Clientes(imagen,id,primerNombre,segundoNombre,primerApellido,segundoApellido,
        
        identificacion,tipo,pais,numero_reserva,numero_estancia,fecha_inscripcion,fecha_nacimiento,telefono,correo));

        Connection connection = (Connection) DriverManager.getConnection(c.getString_connection(), c.getUsername(), c.getPassword());
        Statement stm = (Statement) connection.createStatement();
        String query = null;
        
        
        query = "insert into Cliente " 
                +"(Primer_nombre,Segundo_nombre,Primer_apellido,Segundo_apellido,Identificacion,Tipo_identificacion,Pais_origen,Numero_reserva,Numero_estancia,\n" 
                +" Fecha_inscripcion,Fecha_nacimiento,Telefono,Correo,Imagen) values " 
                +"('"+primerNombre+"','"+segundoNombre+"','"+primerApellido+"','"+segundoApellido+"','"+identificacion+"','"+tipo+"','"+pais+"',"
                + ""+numero_reserva+","+numero_estancia+",'"+fecha_inscripcion+"','"+fecha_nacimiento+"','"+telefono+"','"+correo+"','"+nombre_img+"');";
  
        stm.executeUpdate(query);
        
        Crear_Lista("select * from Cliente;");
        
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
    
    void Nuevo(){
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


    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
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
        
        Crear_Lista("select * from Cliente;");
        
        Nuevo();
    }    
    
}
