package br.edu.ifsc.Proj3.StatusController;

import java.io.IOException;

import com.jfoenix.controls.JFXButton;

import br.edu.ifsc.Proj3.App;
import br.edu.ifsc.Proj3.StatusDAO.StatusDAO;
import br.edu.ifsc.Proj3.UsuarioController.LoginController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class AddStatusController {
	private static String txtStatusUsuario;

    @FXML
    private TextArea txtStatus;

    @FXML
    private ImageView iconAddImagem;

    @FXML
    private Label btAddImagem;

    @FXML
    private JFXButton btEnviarStatus;

    @FXML
    private JFXButton btVisualizarStatus;

    @FXML
    void addStatus(ActionEvent event) {
    	if(!(txtStatus.getText().contentEquals(""))) {
    		new StatusDAO().adicionarStatus(txtStatus.getText(), LoginController.usuario.getNome());
    		
    		Alert errorALert = new Alert(AlertType.INFORMATION);
    		errorALert.setContentText("Status enviado");
    		errorALert.showAndWait();
    		
    		txtStatusUsuario = txtStatus.getText();
    	}
    	else {
    		Alert errorALert = new Alert(AlertType.ERROR);
    		errorALert.setContentText("É preciso colocar algum texto");
    		errorALert.showAndWait();
    	}
    }

    @FXML
    void adicionarImagem(MouseEvent event) {
		Alert errorALert = new Alert(AlertType.ERROR);
		errorALert.setContentText("Recurso ainda não disponível");
		errorALert.showAndWait();
    }

    @FXML
    void visualizarStatus(ActionEvent event) throws IOException {
    	Stage stage = new Stage();
		FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("MostrarStatus.fxml"));
		Parent root = (Parent) fxmlLoader.load();
		stage.setScene(new Scene(root));
		stage.show();
		Stage stage2 = (Stage) btAddImagem.getScene().getWindow();
		stage2.close();
    }
    
    @FXML
    void btVoltar(MouseEvent event) throws IOException {
		Stage stage = new Stage();
		FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("Status.fxml"));
		Parent root = (Parent) fxmlLoader.load();
		stage.setScene(new Scene(root));
		stage.show();
		Stage stage2 = (Stage) btAddImagem.getScene().getWindow();
		stage2.close();
    }

    @FXML
    void apagarStatus(ActionEvent event) throws IOException {
		Stage stage = new Stage();
		FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("ApagarStatus.fxml"));
		Parent root = (Parent) fxmlLoader.load();
		stage.setScene(new Scene(root));
		stage.show();
		Stage stage2 = (Stage) btAddImagem.getScene().getWindow();
		stage2.close();
    }

	public static String getTxtStatusUsuario() {
		return txtStatusUsuario;
	}
}
