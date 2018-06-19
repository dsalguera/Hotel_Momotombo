
package proyecto_hotel.interfaces;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;
import java.io.File;
import java.net.URL;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.util.Callback;
import javax.swing.JOptionPane;
import org.controlsfx.control.textfield.AutoCompletionBinding;
import org.controlsfx.control.textfield.AutoCompletionBinding.AutoCompletionEvent;
import org.controlsfx.control.textfield.TextFields;
import proyecto_hotel.Conexion;
import proyecto_hotel.clases.*;

public class BitacoraController implements Initializable {
    Conexion c=new Conexion();
    Connection connection ;
    List<String> posiblesWords;
    
    int id=-1;
    AutoCompletionBinding<String> TextfieldAutocomplete;
    String dir = "src\\proyecto_hotel\\imagenes\\clientes\\";
    String Defaultimage="user-default.png";
    String AvisoImagen="aviso.png";
    String ProtocoloImagen="protocolo.png";
    ObservableList<Acciones> data = FXCollections.observableArrayList();
    int Id_cliente;
        @FXML
    public Label label; 
        @FXML
    private ImageView Foto_Cliente;
    @FXML
    private Label panel_nombre_cliente;

    @FXML
    private Label panel_identificacion;
    @FXML
    public TextField txtbuscar_cliente;
        @FXML
    private Button btnVer_Historial;
            @FXML
    private ListView<Acciones> lista_acciones;
       @FXML
    private TextField txt_titulo;

    @FXML
    private TextArea txt_descripcion;

    @FXML
    private Button btn_agregar;
    
    @FXML
    private TextField txt_fecha;
    @FXML
    private RadioButton Radio_Aviso;

    @FXML
    private ToggleGroup Grupo_Radio;
    
    @FXML
    public AnchorPane panel_pincipal;

  
    @FXML
    private RadioButton Radio_Protocolar;

    @FXML
    private RadioButton Radio_Todo;
    @FXML
   public  HBox Hbox_principal;
    int tamaño_buscador=490;

    @FXML
    void Accion_Agregar(ActionEvent event) {
//Agregar Comentario
if (lista_acciones.getItems().size()!=0) {
        if (!txt_titulo.getText().equals("") && !txt_descripcion.getText().equals("")) {
        if (JOptionPane.showConfirmDialog(null, "Esta seguro que quiere agregar este comentario al cliente.", "Confirmar Aviso", JOptionPane.YES_NO_OPTION)==JOptionPane.YES_OPTION) {
            try {
          connection = (Connection) DriverManager.getConnection(c.getString_connection(), c.getUsername(), c.getPassword());
          Statement stm = (Statement) connection.createStatement();
          String query = " insert into Acciones(Nombre,Descripcion,Fecha_hora,Tipo,Id_cliente)  values('"+txt_titulo.getText()+"','"+txt_descripcion.getText()+"', now() ,'Aviso',"+id+");";
          stm.executeUpdate(query);
          connection.close();
          txt_descripcion.setText("");
          txt_titulo.setText("");
          Ver_historial(id);
                System.out.println("comentario");
            } catch (SQLException ex) {
                System.out.println("Error en agregar comentario");  
            }
            
        }
        }else{
        JOptionPane.showMessageDialog(null,"Complete los campos");
        }   
}else{
 JOptionPane.showMessageDialog(null,"Seleccione el historial del cliente");
}
}
    
    @FXML
    void Solo_Aviso(ActionEvent event) throws SQLException {
          if (id!=-1) {
             Ver_historial_compuesto(id,txt_fecha.getText(),"Aviso");
        }
    }

    @FXML
    void Solo_Protocolar(ActionEvent event) throws SQLException {
        if (id!=-1) {
             Ver_historial_compuesto(id,txt_fecha.getText(),"Protocolar");
        }
    }

    @FXML
    void Todo(ActionEvent event) throws SQLException {
        if (id!=-1) {
           
            Ver_historial_compuesto(id,txt_fecha.getText(),"Todo");
        }
    }
        @FXML
    void Accioin_Filtrar_Fecha(ActionEvent event) throws SQLException {
            if (id!=-1) {
  connection = (Connection) DriverManager.getConnection(c.getString_connection(), c.getUsername(), c.getPassword());
 Statement stm = (Statement) connection.createStatement();
 String query="SELECT Cast( '"+txt_fecha.getText()+"' as date) as Fecha_convertida";
 ResultSet rs = stm.executeQuery(query);
boolean Error_fecha=true;

while(rs.next()){
if (rs.getString("Fecha_convertida")!=null) {
 Error_fecha=false;
}
         }
               

            if (Error_fecha) {
               JOptionPane.showMessageDialog(null,"Fecha Invalida");
               txt_fecha.setText("");
            }else{
              
            if (Radio_Todo.isSelected()) {
           
            Ver_historial_compuesto(id,txt_fecha.getText(),"Todo");
            }else if(Radio_Protocolar.isSelected()){
            Ver_historial_compuesto(id,txt_fecha.getText(),"protocolar");
            }else if(Radio_Aviso.isSelected()){
              Ver_historial_compuesto(id,txt_fecha.getText(),"aviso");
            }
                
                
            }
            }else{
            JOptionPane.showMessageDialog(null, "Seleccione el historial del cliente primero.");
            }
            connection.close();
    }
    
    @FXML
    void Ver_Historial(ActionEvent event) throws SQLException {
     id=-1;
        if (!txtbuscar_cliente.getText().trim().equals("")) {
    String txt=txtbuscar_cliente.getText();
    connection = (Connection) DriverManager.getConnection(c.getString_connection(), c.getUsername(), c.getPassword());
    Statement stm = (Statement) connection.createStatement();
    String query = "SELECT Id_cliente,Primer_nombre,Segundo_nombre,Primer_apellido,Segundo_apellido,Tipo_identificacion,Identificacion FROM cliente";
    ResultSet rs = stm.executeQuery(query);
    posiblesWords=new ArrayList<String>();
    
    //Llena una lista nueva
    String cadena;
    while (rs.next()) {
    cadena=rs.getString("Primer_nombre")+" "+rs.getString("Segundo_nombre")+" "+rs.getString("Primer_apellido")+" "+rs.getString("Segundo_apellido")
    +"    "+rs.getString("Tipo_identificacion")+" : "+rs.getString("Identificacion");
    posiblesWords.add(cadena); 
    
    }        
    if (posiblesWords.contains(txt)) {
        //Si existe Encuentra el ID
    rs = stm.executeQuery(query);
    while (rs.next()) {
    cadena=rs.getString("Primer_nombre")+" "+rs.getString("Segundo_nombre")+" "+rs.getString("Primer_apellido")+" "+rs.getString("Segundo_apellido")
    +"    "+rs.getString("Tipo_identificacion")+" : "+rs.getString("Identificacion");
        if (cadena.equals(txt)) {
            // Todo
        Id_cliente=rs.getInt("Id_cliente");
        id=Id_cliente;
        Ver_historial(Id_cliente);
        System.out.println(""+Id_cliente);
         
         break;
        }
    }
    }else{
        //Si no existe en la lista renueva la busqueda.
    TextfieldAutocomplete=TextFields.bindAutoCompletion(txtbuscar_cliente, posiblesWords);       
    TextfieldAutocomplete.setPrefWidth(tamaño_buscador);
    TextfieldAutocomplete.setOnAutoCompleted(new EventHandler<AutoCompletionEvent<String>>() {
            @Override
            public void handle(AutoCompletionEvent<String> event) {
            Evento_AutoCompletion_cliente();
            }
        });
     id=-1;
   JOptionPane.showMessageDialog(null, "Asegurese de elegir el cliente y no modificarlo para poder ver su historial. \nTambien es posible que el cliente haya sido eliminado o modificado.", "Cliente Invalido", JOptionPane.CANCEL_OPTION);       
    }
            
   }else{ 
    id=-1;        
    JOptionPane.showMessageDialog(null, "Asegurese de elegir el cliente \npara poder ver su historial.", "Cliente Invalido", JOptionPane.CANCEL_OPTION);   
    }
  connection.close();
    }
        @FXML
    void Evento_Ttecla_en_txtbuscar_cliente(KeyEvent event) {
            if (!posiblesWords.contains(txtbuscar_cliente.getText())) {
             Foto_Cliente.setImage(new Image(new File(dir+Defaultimage).toURI().toString()));
             panel_nombre_cliente.setText("");
             panel_identificacion.setText("");
            }
        
    }
    
        @FXML
    void Ocultar_historial(ActionEvent event) {
     lista_acciones.getItems().clear();
      id=-1;
        Radio_Aviso.setDisable(true);
        Radio_Protocolar.setDisable(true);
        Radio_Todo.setDisable(true);
    }

    
    void  Evento_AutoCompletion_cliente() {
        try {
    connection = (Connection) DriverManager.getConnection(c.getString_connection(), c.getUsername(), c.getPassword());
    Statement stm = (Statement) connection.createStatement();
    String query = "SELECT Id_cliente,Primer_nombre,Segundo_nombre,Primer_apellido,Segundo_apellido,Tipo_identificacion,Identificacion,Imagen FROM cliente";
    ResultSet rs = stm.executeQuery(query);
    String cadena,a1,a2;
    while (rs.next()) {
        
    cadena=rs.getString("Primer_nombre")+" "+rs.getString("Segundo_nombre")+" "+rs.getString("Primer_apellido")+" "+rs.getString("Segundo_apellido")
    +"    "+rs.getString("Tipo_identificacion")+" : "+rs.getString("Identificacion");
    if (cadena.equals(txtbuscar_cliente.getText())) {
        a1=rs.getString("Primer_nombre")+" "+rs.getString("Segundo_nombre")+" "+rs.getString("Primer_apellido")+" "+rs.getString("Segundo_apellido");
        a2=rs.getString("Tipo_identificacion")+" : "+rs.getString("Identificacion");
        String str=dir+rs.getString("Imagen");
        System.out.println("Listo "+str);
        Foto_Cliente.setImage(new Image(new File(str).toURI().toString()));
        panel_nombre_cliente.setText(a1);
        panel_identificacion.setText(a2);
        break;
    }
    
    }
   
    connection.close();
        } catch (Exception e) {
            System.out.println("Error al cargar imagenes");
        }
    
    }
        @FXML
    void key_pressed_fecha(KeyEvent event) {
            
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
      Hbox_principal.prefHeightProperty().addListener(new ChangeListener(){
          @Override
          public void changed(ObservableValue observable, Object oldValue, Object newValue) {
              System.out.println("todo bien");
          }
      
      });
      
        try {
         
           
           Cargar_texfield_cliente();
          Radio_Aviso.setDisable(true);
          Radio_Protocolar.setDisable(true);
          Radio_Todo.setDisable(true);
       
               txt_fecha.setOnKeyTyped(new EventHandler<KeyEvent>() {
                @Override
                public void handle(KeyEvent event) {
                  
                    
                     if (event.getCharacter().charAt(0)=='-' || Character.isDigit(event.getCharacter().charAt(0))) {
                        
                     }else {
                     event.consume();
                     }
                       String string =  txt_fecha.getText();
        if (string.length() > 9) {
           event.consume();
        }
                } } );
          
            txt_titulo.setOnKeyTyped(event -> {
        String string =  txt_titulo.getText();

        if (string.length() > 30) {
           event.consume();
        }
    });
         
            txt_descripcion.setOnKeyTyped(event -> {
        String string =  txt_descripcion.getText();

        if (string.length() > 200) {
           event.consume();
        }
    });
            
      
            
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Existe un error en la conexion");
        }
      
    }    
    
    
    void Cargar_texfield_cliente() throws SQLException{
    connection = (Connection) DriverManager.getConnection(c.getString_connection(), c.getUsername(), c.getPassword());
    Statement stm = (Statement) connection.createStatement();
    String query = "SELECT Id_cliente,Primer_nombre,Segundo_nombre,Primer_apellido,Segundo_apellido,Tipo_identificacion,Identificacion FROM cliente";
    ResultSet rs = stm.executeQuery(query);
    posiblesWords=new ArrayList<String>();
    String cadena;
    while (rs.next()) {
    cadena=rs.getString("Primer_nombre")+" "+rs.getString("Segundo_nombre")+" "+rs.getString("Primer_apellido")+" "+rs.getString("Segundo_apellido")
    +"    "+rs.getString("Tipo_identificacion")+" : "+rs.getString("Identificacion");
    posiblesWords.add(cadena); 
    }
    
    Foto_Cliente.setImage(new Image(new File(dir+Defaultimage).toURI().toString()));
    panel_nombre_cliente.setText("");
    panel_identificacion.setText("");
    TextfieldAutocomplete=TextFields.bindAutoCompletion(txtbuscar_cliente, posiblesWords);       
    TextfieldAutocomplete.setPrefWidth(tamaño_buscador);
    TextfieldAutocomplete.setOnAutoCompleted(new EventHandler<AutoCompletionEvent<String>>() {
            @Override
            public void handle(AutoCompletionEvent<String> event) {
              
                
                    Evento_AutoCompletion_cliente();
               
            }
        });
   
    connection.close();
    }
   
    
    void Ver_historial(int Id_cliente_elegido) throws SQLException {
    
      connection = (Connection) DriverManager.getConnection(c.getString_connection(), c.getUsername(), c.getPassword());
       Statement stm = (Statement) connection.createStatement();
       String query = "select * from Acciones where Id_cliente="+Id_cliente_elegido;
       System.out.println(""+query);
        ResultSet rs = stm.executeQuery(query);
        data.clear();
        while (rs.next()) {
        int id=rs.getInt("Id_accion");
        String nombre=rs.getString("Nombre");
        String Descripcion=rs.getString("Descripcion");
        String Fecha_Hora=rs.getString("Fecha_hora");
        String Tipo=rs.getString("Tipo");
        String Id_cliente=rs.getString("Id_cliente");
         Acciones accion;
         
             if (Tipo.equals("Aviso")) {
                 accion= new Acciones(
                         id,nombre,Descripcion,Fecha_Hora,Tipo,Id_cliente,
                         new Image(new File(dir+AvisoImagen).toURI().toString()) );
                
             }else{
                
               accion= new Acciones(
                         id,nombre,Descripcion,Fecha_Hora,Tipo,Id_cliente,
                         new Image(new File(dir+ProtocoloImagen).toURI().toString()) ); 
              
             }
      
                
        data.add(accion);
            
        }
         
   lista_acciones.getItems().clear();        
   lista_acciones.getItems().addAll(data);
     Radio_Aviso.setDisable(false);
     Radio_Protocolar.setDisable(false);
     Radio_Todo.setDisable(false);
    lista_acciones.setCellFactory(new Callback<ListView<Acciones>, ListCell<Acciones>>() {

        @Override
        public ListCell<Acciones> call(ListView<Acciones> arg0) {
        return new ListCell<Acciones>() {

            @Override
            protected void updateItem(Acciones item, boolean bln) {
            super.updateItem(item, bln);
            if (item != null) {
            
            Label Id_accion,Titulo,Descripcion,Fecha_hora,Tipo,Id_cliente;
            ImageView imagen;
            
            VBox vBox = new VBox(
                    
                Id_accion = new Label("Identificador de Accion: "+item.getId_accion()),
                Tipo = new Label("Tipo: "+item.getTipo()), 
                Titulo = new Label("Titulo : "+item.getNombre()),    
                Descripcion = new Label("Descripcion: \n "+item.getDescripcion()),
                Fecha_hora = new Label("Fecha y Hora : "+item.getFecha_hora()),
                Id_cliente = new Label("Identificador Cliente : "+item.getId_cliente())         
                );
                                               
                    HBox hBox = new HBox(
                            
                    imagen = new ImageView(item.getfoto()), vBox);
                    imagen.setFitHeight(120);
                    imagen.setFitWidth(120);
                    
                    hBox.setSpacing(10);
                    vBox.setSpacing(5);
                    hBox.setAlignment(Pos.CENTER_LEFT);
                    setGraphic(hBox);
                            
                        }
                    }

                };
            }
        
            

        });
     connection.close();
    }
   
     void Ver_historial_compuesto(int Id_cliente_elegido,String fecha,String tipo) throws SQLException {
    
      connection = (Connection) DriverManager.getConnection(c.getString_connection(), c.getUsername(), c.getPassword());
       Statement stm = (Statement) connection.createStatement();
       data.clear();
        String query="SELECT Cast( '"+fecha+"' as date) as Fecha_convertida";
        ResultSet rs = stm.executeQuery(query);
        boolean Error_fecha=true;
       
         while(rs.next()){
            
             if (rs.getString("Fecha_convertida")!=null) {
                 Error_fecha=false;
             }
         }
        
         if (!Error_fecha) {
          if (tipo.equalsIgnoreCase("Todo")) {
            query = "select * from Acciones where Id_cliente="+Id_cliente_elegido+" and Cast(Fecha_hora as date) >= '"+fecha+"'";
         }else if(tipo.equalsIgnoreCase("Aviso")){
            query = "select * from Acciones where Id_cliente="+Id_cliente_elegido+" and Cast(Fecha_hora as date) >= '"+fecha+"' and lower(Tipo) like 'aviso'";
       
         
         }else if(tipo.equalsIgnoreCase("Protocolar")){
                query = "select * from Acciones where Id_cliente="+Id_cliente_elegido+" and Cast(Fecha_hora as date) >= '"+fecha+"' and lower(Tipo) like 'protocolar'";
         
         }
         }else{
          if (tipo.equalsIgnoreCase("Todo")) {
            query = "select * from Acciones where Id_cliente="+Id_cliente_elegido;
         }else if(tipo.equalsIgnoreCase("Aviso")){
            query = "select * from Acciones where Id_cliente="+Id_cliente_elegido+"  and lower(Tipo) like 'aviso'";
       
         
         }else if(tipo.equalsIgnoreCase("Protocolar")){
                query = "select * from Acciones where Id_cliente="+Id_cliente_elegido+"  and lower(Tipo) like 'protocolar'";
         
         }
         }
         System.out.println(query);
         rs = stm.executeQuery(query);
      
        while (rs.next()) {
        int id=rs.getInt("Id_accion");
        String nombre=rs.getString("Nombre");
        String Descripcion=rs.getString("Descripcion");
        String Fecha_Hora=rs.getString("Fecha_hora");
        String Tipo=rs.getString("Tipo");
        String Id_cliente=rs.getString("Id_cliente");
        System.out.println(""+Tipo);
         Acciones accion;
             if (Tipo.equalsIgnoreCase("Aviso")) {
                 accion= new Acciones(
                         id,nombre,Descripcion,Fecha_Hora,Tipo,Id_cliente,
                         new Image(new File(dir+AvisoImagen).toURI().toString()) ); 
             }else{
               accion= new Acciones(
                         id,nombre,Descripcion,Fecha_Hora,Tipo,Id_cliente,
                         new Image(new File(dir+ProtocoloImagen).toURI().toString()) ); 
             }
      
                
        data.add(accion);
            
        }
         
   lista_acciones.getItems().clear();        
   lista_acciones.getItems().addAll(data);
   lista_acciones.setCellFactory(new Callback<ListView<Acciones>, ListCell<Acciones>>() {

        @Override
        public ListCell<Acciones> call(ListView<Acciones> arg0) {
        return new ListCell<Acciones>() {

            @Override
            protected void updateItem(Acciones item, boolean bln) {
            super.updateItem(item, bln);
            if (item != null) {
            
            Label Id_accion,Titulo,Descripcion,Fecha_hora,Tipo,Id_cliente;
            ImageView imagen;
            
            VBox vBox = new VBox(
                    
                   
                Id_accion = new Label("Identificador de Accion: "+item.getId_accion()),
                Tipo = new Label("Tipo: "+item.getTipo()), 
                Titulo = new Label("Titulo : "+item.getNombre()),    
                Descripcion = new Label("Descripcion: \n "+item.getDescripcion()),
                Fecha_hora = new Label("Fecha y Hora : "+item.getFecha_hora()),
                Id_cliente = new Label("Identificador Cliente : "+item.getId_cliente())         
                );
                                               
                    HBox hBox = new HBox(
                            
                    imagen = new ImageView(item.getfoto()), vBox);
                    imagen.setFitHeight(120);
                    imagen.setFitWidth(120);
                    
                    hBox.setSpacing(10);
                    vBox.setSpacing(5);
                    hBox.setAlignment(Pos.CENTER_LEFT);
                    setGraphic(hBox);
                            
                        }
                    }

                };
            }
        
            

        });
     connection.close();
    }
    
}
