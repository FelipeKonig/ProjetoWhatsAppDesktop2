package br.edu.ifsc.Proj3.UsuarioController;

import java.io.IOException;

import javax.transaction.Transactional.TxType;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXToggleButton;

import br.edu.ifsc.Proj3.App;
import br.edu.ifsc.Proj3.model.Usuario;
import br.edu.ifsc.Proj3.usuarioDAO.UsuarioDAO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class ConfiguracoesController {

    @FXML
    private ImageView iconVoltar;

    @FXML
    private Label btConversa;

    @FXML
    private ImageView iconChat;

    @FXML
    private Label btDados;

    @FXML
    private ImageView iconDados;

    @FXML
    private ImageView iconPhone;

    @FXML
    private Label btChamada;

    @FXML
    private ImageView iconConfigGeral;

    @FXML
    private JFXButton btSolicitarDados;

    @FXML
    private JFXButton btApagarConta;

    @FXML
    private JFXButton btAlterarNumero;

    @FXML
    private JFXToggleButton btNotificSegunranca;

    @FXML
    private ImageView iconAjuda;

    @FXML
    private Label btAjuda;

    @FXML
    void btAjudaClicado(MouseEvent event) {

    }

    @FXML
    void btAlterarNumeroClicado(ActionEvent event) {

    }

    @FXML
    void btApagarContaClicado(ActionEvent event) throws IOException {
    	
    	Usuario usuarioRemovido = new UsuarioDAO().apagarUsuario(LoginController.usuario.getNumero());
    
    	if(usuarioRemovido != null) {
			System.out.println("Usuario removido");
			Alert errorALert = new Alert(AlertType.INFORMATION);
			errorALert.setContentText("Usuario removido com sucesso");
			errorALert.showAndWait();
			
			Stage stage = new Stage();
			FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("Login.fxml"));
			Parent root = (Parent) fxmlLoader.load();
			stage.setScene(new Scene(root));
			stage.show();
			Stage stage2 = (Stage) btAjuda.getScene().getWindow();
			stage2.close();
    	}
    	else {
			System.out.println("Usuario não removido");
			Alert errorALert = new Alert(AlertType.ERROR);
			errorALert.setContentText("Usuario não removido");
			errorALert.showAndWait();
    	}
    }

    @FXML
    void btChamadaClicado(MouseEvent event) {

    }

    @FXML
    void btConversaClicado(MouseEvent event) {

    }

    @FXML
    void btDadosClicado(MouseEvent event) {

    }

    @FXML
    void btNotificSegurancaClicado(ActionEvent event) {

    }

    @FXML
    void btSolicitarDadosClicado(ActionEvent event) {

    }

    @FXML
    void btVoltar(MouseEvent event) throws IOException {
		Stage stage = new Stage();
		FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("Menu.fxml"));
		Parent root = (Parent) fxmlLoader.load();
		stage.setScene(new Scene(root));
		stage.show();
		Stage stage2 = (Stage) btAjuda.getScene().getWindow();
		stage2.close();
    }

}
