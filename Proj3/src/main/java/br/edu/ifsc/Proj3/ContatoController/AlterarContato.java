package br.edu.ifsc.Proj3.ContatoController;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXToggleButton;

import br.edu.ifsc.Proj3.App;
import br.edu.ifsc.Proj3.ContatoDAO.ContatoDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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

public class AlterarContato implements Initializable{
	
	public ObservableList<String> listTipos = FXCollections.observableArrayList("Cel", "Trabalho", "Residencial",
			"Principal", "Fax Comercial");

    @FXML
    private ImageView iconVoltar;

    @FXML
    private TextField txtNumero;

    @FXML
    private JFXComboBox<String> cbtipoNumero;

    @FXML
    private TextArea txtComentrario;

    @FXML
    private JFXToggleButton btLigacao;

    @FXML
    private JFXToggleButton btMensagem;

    @FXML
    private JFXToggleButton btChamadaVIdeo;

    @FXML
    private JFXToggleButton btAudio;

    @FXML
    private JFXToggleButton btFicheiro;

    @FXML
    private JFXToggleButton btNotificacao;

    @FXML
    private JFXButton btConfirmar;

    @FXML
    private JFXButton btApagar;

    @FXML
    void apagarContato(ActionEvent event) {
    
    	if(!(txtNumero.getText().contentEquals(""))) {
    		int verifica = new ContatoDAO().apagarContato(txtNumero.getText());
    		
    		if(verifica == 1) {
    			System.out.println("Contato removido");
    			Alert errorALert = new Alert(AlertType.INFORMATION);
    			errorALert.setContentText("Contato removido com sucesso");
    			errorALert.showAndWait();
    		}
    		else {
    			System.out.println("Contato não encontrado");
    			Alert errorALert = new Alert(AlertType.ERROR);
    			errorALert.setContentText("Você não possui esse contato");
    			errorALert.showAndWait();
    		}
    	}
    	else {
			System.out.println("Contato não buscado");
			Alert errorALert = new Alert(AlertType.ERROR);
			errorALert.setContentText("É preciso haver um número para apagar o contato");
			errorALert.showAndWait();
    	}
    	
    }

    @FXML
    void btVoltar(MouseEvent event) throws IOException {
    	Stage stage = new Stage();
		FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("MenuContatos.fxml"));
		Parent root = (Parent) fxmlLoader.load();
		stage.setScene(new Scene(root));
		stage.show();
		Stage stage2 = (Stage) btApagar.getScene().getWindow();
		stage2.close();
    }

    @FXML
    void confirmarContato(ActionEvent event) {
    	String mensagem, ficheiro, ligacao;
    	
    		mensagem = verificarBoolean("mensagem");
    		ficheiro = verificarBoolean("ficheiro");
    		ligacao = verificarBoolean("ligacao");
    	
    	if(!(txtNumero.getText().contentEquals(""))) {
    		int verifica = new ContatoDAO().alterarContato(txtNumero.getText(), cbtipoNumero.getValue(), mensagem, ficheiro, ligacao);
    		
    		if(verifica == 1) {
    			System.out.println("Contato alterado");
    			Alert errorALert = new Alert(AlertType.INFORMATION);
    			errorALert.setContentText("Contato alterado com sucesso");
    			errorALert.showAndWait();
    		}
    		else {
    			System.out.println("Contato não encontrado");
    			Alert errorALert = new Alert(AlertType.ERROR);
    			errorALert.setContentText("Você não possui esse contato");
    			errorALert.showAndWait();
    		}
    	}
    	else {
			System.out.println("Contato não buscado");
			Alert errorALert = new Alert(AlertType.ERROR);
			errorALert.setContentText("É preciso haver um número para alterar o contato");
			errorALert.showAndWait();
    	}
    }
    
	public String verificarBoolean(String opcao) {
		if(opcao == "mensagem") {
			if(btMensagem.isSelected()) {
				return "true";
			}
		}
		else if(opcao == "ficheiro") {
			if(btFicheiro.isSelected()) {
				return "true";
			}
		}
		else if(opcao == "ligacao") {
			if(btLigacao.isSelected()) {
				return "true";
			}
		}
		
		return "false";
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		cbtipoNumero.setItems(listTipos);
		
	}

}
