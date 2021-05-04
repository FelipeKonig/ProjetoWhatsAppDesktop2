package br.edu.ifsc.Proj3.ChamadaController;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;

import br.edu.ifsc.Proj3.App;
import br.edu.ifsc.Proj3.ChamadaDAO.chamadaDAO;
import br.edu.ifsc.Proj3.ContatoDAO.ContatoDAO;
import br.edu.ifsc.Proj3.UsuarioController.LoginController;
import br.edu.ifsc.Proj3.model.Usuario;
import br.edu.ifsc.Proj3.model.Chamada;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class ChamadaController implements Initializable {
	boolean verificaContato = false;
	private static Chamada  ligacao;

    @FXML
    private ImageView iconVoltar;

    @FXML
    private ListView<Chamada> listChamadas;

    @FXML
    private TextField txtContato;

    @FXML
    private ImageView iconBuscar;

    @FXML
    private ImageView iconTelefone;

    @FXML
    private ImageView iconVideo;

    @FXML
    private Text txtTipoLigacao;

    @FXML
    private JFXButton btChamada;

    @FXML
    private Label lblTipoLigacao;
    
    @FXML
    private JFXButton btAlterarLembrete;


    @FXML
    void ApagarChamada(MouseEvent event) throws IOException {
    	
    	if(listChamadas.getSelectionModel().getSelectedItem() != null) {
    		
    		String numero = listChamadas.getSelectionModel().getSelectedItem().getContato();
    		String tipoLigacao = listChamadas.getSelectionModel().getSelectedItem().getTipoLigacao();
    		
    		new chamadaDAO().removerChamada(numero, tipoLigacao);
    		
    		preencherChamadas();
    		
    	}
    }
    
    @FXML
    void AlterarLembrete(ActionEvent event) throws IOException {
		Stage stage = new Stage();
		FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("AlterarLembrete.fxml"));
		Parent root = (Parent) fxmlLoader.load();
		stage.setScene(new Scene(root));
		stage.show();
		Stage stage2 = (Stage) listChamadas.getScene().getWindow();
		stage2.close();
    }

    @FXML
    void Chamada(MouseEvent event) {
		lblTipoLigacao.setText("Ligação");
		lblTipoLigacao.setVisible(true);
    }

    @FXML
    void FazerChamada(ActionEvent event) throws IOException {
    	
		if (verificaContato == true && lblTipoLigacao.isVisible()) {
			
			new chamadaDAO().adicionarChamada(txtContato.getText(), lblTipoLigacao.getText(), LoginController.usuario.getNumero());
			
			Chamada chamada = new Chamada(lblTipoLigacao.getText(), txtContato.getText());
			
			ligacao = chamada;
			
			preencherChamadas();
			verificaContato = false;
			
			Stage stage = new Stage();
			FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("Ligacao.fxml"));
			Parent root = (Parent) fxmlLoader.load();
			stage.setScene(new Scene(root));
			stage.show();
			
		} else if(verificaContato == false){
			Alert errorALert = new Alert(AlertType.ERROR);
			errorALert.setContentText("Selecione um contato");
			errorALert.showAndWait();
				
		}
		else {
			Alert errorALert = new Alert(AlertType.ERROR);
			errorALert.setContentText("Informe o tipo de ligação");
			errorALert.showAndWait();
		}
    }

    @FXML
    void LigarChamadaVideo(MouseEvent event) {
		lblTipoLigacao.setText("Chamada de video");
		lblTipoLigacao.setVisible(true);
    }

    @FXML
    void btVoltar(MouseEvent event) throws IOException {
		Stage stage = new Stage();
		FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("Menu.fxml"));
		Parent root = (Parent) fxmlLoader.load();
		stage.setScene(new Scene(root));
		stage.show();
		Stage stage2 = (Stage) listChamadas.getScene().getWindow();
		stage2.close();
    }

    @FXML
    void buscarContato(MouseEvent event) {
    	Usuario contato = new ContatoDAO().encontrarContato(txtContato.getText());
    	
    	if(contato != null) {
    		verificaContato = true;
    	}
    	else {
			Alert errorALert = new Alert(AlertType.ERROR);
			errorALert.setContentText("Informe um contato válido");
			errorALert.showAndWait();
		}
    }

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		try {
			preencherChamadas();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	public void preencherChamadas() throws IOException {
		
		List<Chamada> listChamadasUsuario = new chamadaDAO().pegarChamadas(LoginController.usuario.getNumero());

		try {
			listChamadas.setItems((ObservableList<Chamada>) listChamadasUsuario);

		} catch (Exception e) {
			e.printStackTrace();
		}
    }

	public static Chamada getLigacao() {
		return ligacao;
	}
	
	

}
