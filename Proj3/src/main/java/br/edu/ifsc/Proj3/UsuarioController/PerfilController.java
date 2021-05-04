package br.edu.ifsc.Proj3.UsuarioController;

import java.io.IOException;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;

import br.edu.ifsc.Proj3.App;
import br.edu.ifsc.Proj3.usuarioDAO.UsuarioDAO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class PerfilController implements Initializable {

    @FXML
    private ImageView iconVoltar;

    @FXML
    private TextArea txtRecado;

    @FXML
    private JFXButton btConfirmarRecado;

    @FXML
    private TextField txtNome;

    @FXML
    private JFXButton btConfirmarNome;

    @FXML
    private TextField txtNumero;

    @FXML
    void addRecado(ActionEvent event) {
    	
    	if(!(txtRecado.getText() == null)) {
    		new UsuarioDAO().alterarRecado(txtRecado.getText());
			
    		System.out.println("alterou o recado");
			Alert errorALert = new Alert(AlertType.INFORMATION);
			errorALert.setContentText("O recado foi alterado");
			errorALert.showAndWait();
			
			LoginController.usuario.setRecado(txtRecado.getText());
    		
    	}
    	else {
    		
			System.out.println("Não alterou o recado");
			Alert errorALert = new Alert(AlertType.ERROR);
			errorALert.setContentText("É preciso colocar um recado para altera-lo");
			errorALert.showAndWait();
    	}
    }

    @FXML
    void alterarNome(ActionEvent event) throws UnknownHostException, IOException {
    	
    	if(!txtNome.getText().contentEquals("")) {
    		new UsuarioDAO().alterarUsuario("nome", txtNome.getText());
			
    		System.out.println("alterou o nome");
			Alert errorALert = new Alert(AlertType.INFORMATION);
			errorALert.setContentText("O nome foi alterado");
			errorALert.showAndWait();
			
			LoginController.usuario.setNome(txtNome.getText());
    		
    	}
    	else {
    		
			System.out.println("Não alterou o nome");
			Alert errorALert = new Alert(AlertType.ERROR);
			errorALert.setContentText("É preciso colocar um nome para altera-lo");
			errorALert.showAndWait();
    	}
    	
    }

    @FXML
    void btVoltar(MouseEvent event) throws IOException {
		Stage stage = new Stage();
		FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("Menu.fxml"));
		Parent root = (Parent) fxmlLoader.load();
		stage.setScene(new Scene(root));
		stage.show();
		Stage stage2 = (Stage) btConfirmarNome.getScene().getWindow();
		stage2.close();
    }

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		txtNumero.setText(LoginController.usuario.getNumero());
		txtNome.setText(LoginController.usuario.getNome());
		txtRecado.setText(LoginController.usuario.getRecado());
		
	}

}
