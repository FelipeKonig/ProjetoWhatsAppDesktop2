package br.edu.ifsc.Proj3.UsuarioController;

import java.io.IOException;

import com.jfoenix.controls.JFXButton;

import br.edu.ifsc.Proj3.App;
import br.edu.ifsc.Proj3.model.Usuario;
import br.edu.ifsc.Proj3.usuarioDAO.UsuarioDAO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

public class LoginController {
	public static Usuario usuario = new Usuario();

	@FXML
	private TextField txtNumero;

	@FXML
	private JFXButton btAvancar;

	@FXML
	private JFXButton btCadastrar;

	@FXML
	void clique(ActionEvent e) throws IOException {
		Button btClicado = (Button) e.getSource();
		if (btClicado == btCadastrar) {

			Stage stage = new Stage();
			FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("Cadastro.fxml"));
			Parent root = (Parent) fxmlLoader.load();
			stage.setScene(new Scene(root));
			stage.show();
			Stage stage2 = (Stage) btCadastrar.getScene().getWindow();
			stage2.close();
		} else {

			if (!(txtNumero.getText().contentEquals(""))) {
				usuario = new UsuarioDAO().encontrarUsuario(txtNumero.getText());

				if (usuario == null) {
					System.out.println("Não achou");
					Alert errorALert = new Alert(AlertType.ERROR);
					errorALert.setContentText("Número não cadastrado");
					errorALert.showAndWait();
				}

				else {
					Stage stage = new Stage();
					FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("Menu.fxml"));
					Parent root = (Parent) fxmlLoader.load();
					stage.setScene(new Scene(root));
					stage.show();
					Stage stage2 = (Stage) btCadastrar.getScene().getWindow();
					stage2.close();
				}
			} else {
				System.out.println("Número não inserido");
				Alert errorALert = new Alert(AlertType.ERROR);
				errorALert.setContentText("Coloque um número");
				errorALert.showAndWait();
			}

		}
	}

}
