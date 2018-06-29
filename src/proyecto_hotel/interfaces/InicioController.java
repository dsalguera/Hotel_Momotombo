
package proyecto_hotel.interfaces;

import java.io.IOException;
import java.net.URL;
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
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import proyecto_hotel.FXMLDocumentController;


public class InicioController implements Initializable {
static Stage stage;

  @FXML
    void Pagar_estancia(ActionEvent event) {

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
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
