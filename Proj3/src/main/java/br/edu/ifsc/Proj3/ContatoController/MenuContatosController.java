package br.edu.ifsc.Proj3.ContatoController;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXListView;

import br.edu.ifsc.Proj3.App;
import br.edu.ifsc.Proj3.UsuarioController.LoginController;
import br.edu.ifsc.Proj3.model.Usuario;
import br.edu.ifsc.Proj3.usuarioDAO.UsuarioDAO;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class MenuContatosController implements Initializable{
	List<Usuario> contatos;

    @FXML
    private ImageView iconVoltar;

    @FXML
    private Label btAddContato;

    @FXML
    private ImageView iconAddContato;

    @FXML
    private JFXListView<Usuario> listContatos;

    @FXML
    void AdicionarContato(MouseEvent event) throws IOException {
    	Stage stage = new Stage();
		FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("AddContato.fxml"));
		Parent root = (Parent) fxmlLoader.load();
		stage.setScene(new Scene(root));
		stage.show();
		Stage stage2 = (Stage) listContatos.getScene().getWindow();
		stage2.close();
    }

    @FXML
    void ContatoSelecionado(MouseEvent event) throws IOException {
    	
    	selecionarContato();
    	
    	Stage stage = new Stage();
		FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("PerfilContato.fxml"));
		Parent root = (Parent) fxmlLoader.load();
		stage.setScene(new Scene(root));
		stage.show();
		Stage stage2 = (Stage) listContatos.getScene().getWindow();
		stage2.close();
    }

    @FXML
    void btVoltar(MouseEvent event) throws IOException {
    	Stage stage = new Stage();
		FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("Menu.fxml"));
		Parent root = (Parent) fxmlLoader.load();
		stage.setScene(new Scene(root));
		stage.show();
		Stage stage2 = (Stage) listContatos.getScene().getWindow();
		stage2.close();
    }

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		preencherListaContatos();
		
	}
	
	 public void preencherListaContatos(){
			UsuarioDAO contatosDAO = new UsuarioDAO();

			try {
				listContatos.setItems((ObservableList<Usuario>) contatosDAO.pegarContatos());

			} catch (Exception e) {
				e.printStackTrace();
			}
	 }
	 
	 void selecionarContato() {
		
		 UsuarioDAO contatos = new UsuarioDAO();
		 
			try {				
				LoginController.usuario.setContatos(contatos.pegarContatos());
							
			} catch (Exception e) {
				e.printStackTrace();
			}
		
			for (Usuario contato : LoginController.usuario.getContatos()) {
				
				if (contato.getNome().contentEquals(listContatos.getSelectionModel().getSelectedItem().getNome())) {
					PerfilContatoController.contato.setNome(contato.getNome());
					PerfilContatoController.contato.setNumero(contato.getNumero());
					PerfilContatoController.contato.setRecado(verificaRecado(contato.getRecado()));
				}
			}
		}
	 
	 String verificaRecado(String recado) {
		 if(recado.contentEquals("null")) {
			 return "Ele ainda não colocou nenhum recado";
		 }
		 
		 return recado;
	 }
}
