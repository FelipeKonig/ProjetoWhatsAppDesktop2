package br.edu.ifsc.Proj3.UsuarioController;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXListView;

import br.edu.ifsc.Proj3.App;
import br.edu.ifsc.Proj3.model.Usuario;
import br.edu.ifsc.Proj3.usuarioDAO.UsuarioDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class MenuController implements Initializable {

	private ObservableList<String> menuUsuario = FXCollections.observableArrayList("Perfil", "Configurações",
			"Sair da conta");
	private ObservableList<String> menuContato = FXCollections.observableArrayList("Dados do contato");

    @FXML
    private ImageView iconLigacao;

    @FXML
    private ImageView iconStatus;

    @FXML
    private JFXComboBox<String> cbMenuUsuario;

    @FXML
    private ImageView iconLupa1;

    @FXML
    private JFXComboBox<String> cbMenuContato;

    @FXML
    private Label lblContato;

    @FXML
    private TextField txtBuscarContato;

    @FXML
    private ImageView iconLupa2;

    @FXML
    private JFXListView<Usuario> listContatos;

    @FXML
    private ImageView btAddContato;

    @FXML
    private ImageView iconEmotion;

    @FXML
    private TextArea txtMensagem;

    @FXML
    private JFXButton btEnviar;

    @FXML
    private TextArea msg1;

    @FXML
    private TextArea msg2;

    @FXML
    private TextArea msg3;

    @FXML
    private TextArea msg4;
    
    public void preencherListaContatos(){
		UsuarioDAO contatos = new UsuarioDAO();

		try {
			listContatos.setItems((ObservableList<Usuario>) contatos.pegarContatos());
			
			
		} catch (Exception e) {
			System.out.println(listContatos);
			e.printStackTrace();
		}
    }
    
    public void addComboBox() {
		cbMenuUsuario.setItems(menuUsuario);
		cbMenuContato.setItems(menuContato);
    }

    @FXML
    void btChamadas(MouseEvent event) throws IOException {
		Stage stage = new Stage();
		FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("Chamadas.fxml"));
		Parent root = (Parent) fxmlLoader.load();
		stage.setScene(new Scene(root));
		stage.show();
		Stage stage2 = (Stage) cbMenuUsuario.getScene().getWindow();
		stage2.close();
    }

    @FXML
    void buscarContato(MouseEvent event) {

    }

    @FXML
    void contatoSelecionado(MouseEvent event) {

    }

    @FXML
    void enviarMsg(ActionEvent event) {

    }

    @FXML
    void opMenuContato(ActionEvent event) {

    }

    @FXML
    void opMenuUsuario(ActionEvent event) throws IOException {
    	System.out.println(cbMenuUsuario.getValue());
		if (cbMenuUsuario.getValue().contentEquals("Perfil")) {
			Stage stage = new Stage();
			FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("Perfil.fxml"));
			Parent root = (Parent) fxmlLoader.load();
			stage.setScene(new Scene(root));
			stage.show();
			Stage stage2 = (Stage) cbMenuUsuario.getScene().getWindow();
			stage2.close();
		}
		
		if (cbMenuUsuario.getValue().contentEquals("Sair da conta")) {
			Stage stage = new Stage();
			FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("Login.fxml"));
			Parent root = (Parent) fxmlLoader.load();
			stage.setScene(new Scene(root));
			stage.show();
			Stage stage2 = (Stage) cbMenuUsuario.getScene().getWindow();
			stage2.close();
		}
		
		if(cbMenuUsuario.getValue().contentEquals("Configurações")) {
			Stage stage = new Stage();
			FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("Configuracoes.fxml"));
			Parent root = (Parent) fxmlLoader.load();
			stage.setScene(new Scene(root));
			stage.show();
			Stage stage2 = (Stage) cbMenuUsuario.getScene().getWindow();
			stage2.close();
		}
    }

    @FXML
    void verContatos(MouseEvent event) throws IOException {
		Stage stage = new Stage();
		FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("MenuContatos.fxml"));
		Parent root = (Parent) fxmlLoader.load();
		stage.setScene(new Scene(root));
		stage.show();
		Stage stage2 = (Stage) cbMenuUsuario.getScene().getWindow();
		stage2.close();

    }

    @FXML
    void verStatus(MouseEvent event) throws IOException {
		Stage stage = new Stage();
		FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("Status.fxml"));
		Parent root = (Parent) fxmlLoader.load();
		stage.setScene(new Scene(root));
		stage.show();
		Stage stage2 = (Stage) cbMenuUsuario.getScene().getWindow();
		stage2.close();

    }

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		cbMenuUsuario.setItems(menuUsuario);
		cbMenuContato.setItems(menuContato);
		preencherListaContatos();
	}

}
