package br.edu.ifsc.Proj3.ContatoController;

import java.io.IOException;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXToggleButton;

import br.edu.ifsc.Proj3.App;
import br.edu.ifsc.Proj3.ContatoDAO.ContatoDAO;
import br.edu.ifsc.Proj3.UsuarioController.LoginController;
import br.edu.ifsc.Proj3.model.Usuario;
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

public class AddContatoController implements Initializable{
	
	public ObservableList<String> listTipos = FXCollections.observableArrayList("Cel", "Trabalho", "Residencial",
			"Principal", "Fax Comercial");

    @FXML
    private ImageView iconVoltar;

    @FXML
    private TextField txtNumero;

    @FXML
    private JFXComboBox<String> cbTiposNumero;

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
    void btVoltar(MouseEvent event) throws IOException {
    	Stage stage = new Stage();
		FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("MenuContatos.fxml"));
		Parent root = (Parent) fxmlLoader.load();
		stage.setScene(new Scene(root));
		stage.show();
		Stage stage2 = (Stage) txtNumero.getScene().getWindow();
		stage2.close();
    }

    @FXML
    void confirmarContato(ActionEvent event) throws UnknownHostException, IOException{
    	String mensagem, ficheiro, ligacao;
    	
    	if(!(txtNumero.getText().contentEquals(""))) {
    	
    		Usuario encontrarContato = new ContatoDAO().encontrarContato(txtNumero.getText());
    		
    		if(!(encontrarContato == null)) {
        		mensagem = verificarBoolean("mensagem");
        		ficheiro = verificarBoolean("ficheiro");
        		ligacao = verificarBoolean("ligacao");
        		
        		if(!(encontrarContato.getNumero().contentEquals(LoginController.usuario.getNumero()))) {
        			int verifica = new ContatoDAO().adicionarContato(txtNumero.getText(), cbTiposNumero.getValue(), mensagem, ficheiro, ligacao);
        			
        			if(verifica == 1) {
              			System.out.println("Contato adicionado");
            			Alert errorALert = new Alert(AlertType.INFORMATION);
            			errorALert.setContentText("Contato adicionado com sucesso");
            			errorALert.showAndWait();
        			}
        			else {
            			System.out.println("Contato inválido");
            			Alert errorALert = new Alert(AlertType.ERROR);
            			errorALert.setContentText("Você já possui esse contato");
            			errorALert.showAndWait();
        			}

        		}
        		else {
        			System.out.println("Número inválido");
        			Alert errorALert = new Alert(AlertType.ERROR);
        			errorALert.setContentText("Você não pode adicionar a si mesmo");
        			errorALert.showAndWait();
        		}
        		
        		
    		}
    		else {
    			System.out.println("Número inválido");
    			Alert errorALert = new Alert(AlertType.ERROR);
    			errorALert.setContentText("Este número não é cadastrado");
    			errorALert.showAndWait();
    		}

    	}
    	else {
			System.out.println("Usuário não cadastrado");
			Alert errorALert = new Alert(AlertType.ERROR);
			errorALert.setContentText("É preciso haver um número para cadastrar um usuário");
			errorALert.showAndWait();
    	}
    }

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		cbTiposNumero.setItems(listTipos);
		
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

}
