package br.edu.ifsc.Proj3.StatusController;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import br.edu.ifsc.Proj3.App;
import br.edu.ifsc.Proj3.UsuarioController.LoginController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class VisualizarStatusUsuario implements Initializable{

    @FXML
    private TextArea txtStatus;

    @FXML
    private Label txtNome;
    
    @FXML
    void btVoltar(MouseEvent event) throws IOException {
		Stage stage = new Stage();
		FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("AddStatus.fxml"));
		Parent root = (Parent) fxmlLoader.load();
		stage.setScene(new Scene(root));
		stage.show();
		Stage stage2 = (Stage) txtNome.getScene().getWindow();
		stage2.close();
    }

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		txtStatus.setText(AddStatusController.getTxtStatusUsuario());
		txtNome.setText(LoginController.usuario.getNome());
		
	}

}
