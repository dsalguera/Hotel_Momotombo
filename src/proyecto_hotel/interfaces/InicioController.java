
package proyecto_hotel.interfaces;

import com.jfoenix.controls.JFXButton;
import com.mysql.jdbc.Connection;
import java.io.IOException;
import java.net.URL;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;
import proyecto_hotel.Conexion;
import proyecto_hotel.FXMLDocumentController;


public class InicioController implements Initializable {
static Stage stage;

  @FXML
    void Pagar_estancia(ActionEvent event) {
         try {
            
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("/proyecto_hotel/interfaces/Pagar_Estancia.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage = new Stage();
        stage.setTitle("Contratar Estancia");
        stage.initOwner(FXMLDocumentController.stage);
        stage.initModality(Modality.WINDOW_MODAL);
        stage.setScene(scene);
        stage.show();
        
        
        } catch (IOException e) {
        } 
    }

    @FXML
    void Contratar_estancia(ActionEvent event) {
      try {
            
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("/proyecto_hotel/interfaces/Contrato_estancia.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage = new Stage();
        stage.setTitle("Contratar Estancia");
        stage.initOwner(FXMLDocumentController.stage);
        stage.initModality(Modality.WINDOW_MODAL);
        stage.setScene(scene);
        stage.show();
        
        
        } catch (IOException e) {
        } 
    }

      @FXML
    void Contratar_Reserva(ActionEvent event) {
       
        try {
            
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("/proyecto_hotel/interfaces/Reserva.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage = new Stage();
        stage.setTitle("Contratar Reserva");
        stage.initOwner(FXMLDocumentController.stage);
        stage.initModality(Modality.WINDOW_MODAL);
        stage.setScene(scene);
        stage.show();
        
        
        } catch (IOException e) {
        } 
    }
    
        @FXML
    void Facturar_estancia(ActionEvent event) {
 try {
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("/proyecto_hotel/interfaces/Facturas_estancia.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage = new Stage();
        stage.setTitle("Factura de Estancia");
        stage.initOwner(FXMLDocumentController.stage);
        stage.initModality(Modality.WINDOW_MODAL);
        stage.setScene(scene);
        stage.show();
        
        } catch (IOException e) {
            System.out.println("eror al abrir");
        } 
    }
    
        @FXML
    void Certificado_Reserva(ActionEvent event) {
   try {
        FXMLLoader fxmlLoader = new FXMLLoader();
        System.out.println("1.");
        fxmlLoader.setLocation(getClass().getResource("/proyecto_hotel/interfaces/Certificado_Rerserva.fxml"));
        System.out.println("2.");
        Scene scene = new Scene(fxmlLoader.load());
        System.out.println("3.");
        stage = new Stage();
        stage.setTitle("Certificado de Reserva");
         
        stage.initOwner(FXMLDocumentController.stage);
        stage.initModality(Modality.WINDOW_MODAL);
        stage.setScene(scene);
        stage.show();
        
        } catch (IOException e) {
            System.out.println("eror al abrir");
        } 
    }
      void AjustePagina(Node n){
        AnchorPane.setLeftAnchor(n, 205.00);
        AnchorPane.setTopAnchor(n, 60.00);
        AnchorPane.setRightAnchor(n, 0.00);
        AnchorPane.setBottomAnchor(n, 0.00);
    }
      
      
          @FXML
    private HBox h1;
    @FXML
    void Grafico(ActionEvent event) {
    Grafico();
    }
    Connection connection ;
    void Grafico(){
              try {
    Conexion c=new Conexion();
    connection = (Connection) DriverManager.getConnection(c.getString_connection(), c.getUsername(), c.getPassword());
    JasperReport reporte = (JasperReport) JRLoader.loadObject("src//Reportes//new.jasper"); 
    JasperPrint j = JasperFillManager.fillReport(reporte,null, connection); 
    JasperViewer jv=new JasperViewer(j,false);
    jv.setTitle("Factura del Hospedaje");
    jv.setVisible(true);
        } catch (SQLException e) {
            System.out.println(""+e);
        } catch (JRException ex) {
          System.out.println(""+ex);
    }

    
    }
        @FXML
    private HBox h3;

        @FXML
    private HBox h2;
    @FXML
    private JFXButton CE;

    @FXML
    private JFXButton IA;
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        if (MenuController.tipo_usuario==3) {
            h1.getChildren().remove(CE);
            h3.getChildren().remove(IA);
        }   
    }
}
