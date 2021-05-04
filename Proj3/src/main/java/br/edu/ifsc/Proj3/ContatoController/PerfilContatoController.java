package br.edu.ifsc.Proj3.ContatoController;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;

import br.edu.ifsc.Proj3.App;
import br.edu.ifsc.Proj3.model.Usuario;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class PerfilContatoController implements Initializable {
	static Usuario contato = new Usuario() ; 

    @FXML
    private ImageView iconVoltar;

    @FXML
    private ImageView iconUsuario;

    @FXML
    private Label txtNome;

    @FXML
    private Text txtRecado;

    @FXML
    private Text txtNumero;

    @FXML
    private JFXButton btAlterarContato;

    @FXML
    private JFXButton btApagarContato;

    @FXML
    void alterarContato(ActionEvent event) throws IOException {
    	Stage stage = new Stage();
		FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("AlterarContato.fxml"));
		Parent root = (Parent) fxmlLoader.load();
		stage.setScene(new Scene(root));
		stage.show();
		Stage stage2 = (Stage) txtNome.getScene().getWindow();
		stage2.close();
    }

    @FXML
    void btVoltar(MouseEvent event) throws IOException {
    	Stage stage = new Stage();
		FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("MenuContatos.fxml"));
		Parent root = (Parent) fxmlLoader.load();
		stage.setScene(new Scene(root));
		stage.show();
		Stage stage2 = (Stage) txtNome.getScene().getWindow();
		stage2.close();
    }

    @FXML
    void visualizarContato(ActionEvent event) {

    }

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		txtNome.setText(contato.getNome());
		txtNumero.setText(contato.getNumero());
		txtRecado.setText(contato.getRecado());
		
	}

}
