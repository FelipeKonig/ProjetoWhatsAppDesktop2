package br.edu.ifsc.Proj3.ChamadaController;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class LigacaoController implements Initializable{
	
	private boolean mutarConversa = false,silenciarConversa = false;

    @FXML
    private Label txtNomeContato;

    @FXML
    private ImageView iconDesligar;

    @FXML
    void Desligar(MouseEvent event) throws IOException {
		Stage stage2 = (Stage) txtNomeContato.getScene().getWindow();
		stage2.close();
    }

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		txtNomeContato.setText(ChamadaController.getLigacao().getContato());		
	}
	

    @FXML
    void SilenciarLigacao(MouseEvent event) {
    	if(silenciarConversa == false){
			
    		Alert errorALert = new Alert(AlertType.INFORMATION);
			errorALert.setContentText("Sua conversa foi silenciada");
			errorALert.showAndWait();
			
			silenciarConversa = true;
    	}
    	else {
    		
    		Alert errorALert = new Alert(AlertType.INFORMATION);
			errorALert.setContentText("Sua conversa deixou de ser silenciada");
			errorALert.showAndWait();
    		
			silenciarConversa = false;
    	}
    }

    @FXML
    void mutarLigacao(MouseEvent event) {
    	
    	if(mutarConversa == false){
			
    		Alert errorALert = new Alert(AlertType.INFORMATION);
			errorALert.setContentText("Sua conversa foi mutada");
			errorALert.showAndWait();
			
			mutarConversa = true;
    	}
    	else {
    		
    		Alert errorALert = new Alert(AlertType.INFORMATION);
			errorALert.setContentText("Sua conversa deixou de ser mutada");
			errorALert.showAndWait();
    		
			mutarConversa = false;
    	}

    }

}
