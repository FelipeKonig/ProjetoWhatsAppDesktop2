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

public class CadastroController {
	Usuario usuarioCadastro;
	
    @FXML
    private TextField txtNome;

    @FXML
    private TextField txtPais;

    @FXML
    private JFXButton btCadastrar;

    @FXML
    private JFXButton btVoltar;

    @FXML
    private TextField txtNumero;

    @FXML
    private JFXButton btAddImagem;

    @FXML
    void clique(ActionEvent e) throws IOException {
    	Button btClicado = (Button) e.getSource();
		if (btClicado == btVoltar) {
			Stage stage = new Stage();
			FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("Login.fxml"));
			Parent root = (Parent) fxmlLoader.load();
			stage.setScene(new Scene(root));
			stage.show();
			Stage stage2 = (Stage) btCadastrar.getScene().getWindow();
			stage2.close();
		}
		else if(btClicado == btCadastrar) {
			boolean verificaCadastro;
			
			if(!(txtNome.getText().contentEquals("")) && !(txtNumero.getText().contentEquals("")) && !(txtPais.getText().contentEquals(""))) {
				usuarioCadastro = new Usuario(txtNome.getText(), txtNumero.getText(), txtPais.getText());
				
				verificaCadastro = new UsuarioDAO().adicionarUsuario(usuarioCadastro);
				
				if (verificaCadastro == true) {
					System.out.println("Cadastro realizado");
					Alert alert = new Alert(AlertType.INFORMATION);
					alert.setContentText("Cadastro realizado com sucesso");
					alert.showAndWait();
				}
				else {
					System.out.println("Cadastro não realizado");
					Alert errorALert = new Alert(AlertType.ERROR);
					errorALert.setContentText("Número já cadastrado");
					errorALert.showAndWait();
				}
			}
			else {
				System.out.println("Cadastro não realizado");
				Alert errorALert = new Alert(AlertType.ERROR);
				errorALert.setContentText("É necessário preencher todos os campos");
				errorALert.showAndWait();
			}

		}
    }

}
