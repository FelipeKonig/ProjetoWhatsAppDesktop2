package br.edu.ifsc.Proj3.ChamadaController;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;

import br.edu.ifsc.Proj3.App;
import br.edu.ifsc.Proj3.ChamadaDAO.chamadaDAO;
import br.edu.ifsc.Proj3.UsuarioController.LoginController;
import br.edu.ifsc.Proj3.model.Chamada;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class AlterarLembreteController implements Initializable{
	Chamada chamada;

    @FXML
    private ImageView iconVoltar;

    @FXML
    private JFXComboBox<String> cbNumeroChamada;

    @FXML
    private JFXComboBox<String> cbTipoChamada;

    @FXML
    private JFXButton btBuscarChamada;

    @FXML
    private JFXButton btSalvarAlteracoes;
    
    @FXML
    private TextArea txtLembrete;

    @FXML
    void btBuscarChamadaClicado(ActionEvent event) {
    	
    	chamada = new chamadaDAO().buscarChamada(cbNumeroChamada.getValue(), cbTipoChamada.getValue());
    	
    	if(chamada == null) {
			Alert errorALert = new Alert(AlertType.ERROR);
			errorALert.setContentText("O tipo de ligação informado está incorreto");
			errorALert.showAndWait();
    	}
    	else {
    		if(!(chamada.getLembrete().contentEquals("null"))) {
    			txtLembrete.setText(chamada.getLembrete());
    		}
    	}
    }

    @FXML
    void btSalvarAteracoesClicado(ActionEvent event) {
    	
    	chamada = new chamadaDAO().buscarChamada(cbNumeroChamada.getValue(), cbTipoChamada.getValue());
    	
    	if(!(txtLembrete.getText().contentEquals(""))) {
    		new chamadaDAO().alterarLembrete(chamada.getContato(), chamada.getTipoLigacao(), txtLembrete.getText());	
    		
    		Alert errorALert = new Alert(AlertType.INFORMATION);
			errorALert.setContentText("Lembrete alterado");
			errorALert.showAndWait();
    	
    	}
    	else {
			Alert errorALert = new Alert(AlertType.ERROR);
			errorALert.setContentText("É preciso haver um lembrete para altera-lo");
			errorALert.showAndWait();
    	}
    
    
    }

    @FXML
    void btVoltar(MouseEvent event) throws IOException {
		Stage stage = new Stage();
		FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("Chamadas.fxml"));
		Parent root = (Parent) fxmlLoader.load();
		stage.setScene(new Scene(root));
		stage.show();
		Stage stage2 = (Stage) btBuscarChamada.getScene().getWindow();
		stage2.close();
    }

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		try {
			adicionarComboBox();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	void adicionarComboBox() throws IOException {
		List<Chamada> chamadas = new chamadaDAO().pegarChamadas(LoginController.usuario.getNumero());
		
		for (int i = 0; i < chamadas.size(); i++) {
			
			cbNumeroChamada.getItems().add(chamadas.get(i).getContato());
			cbTipoChamada.getItems().add(chamadas.get(i).getTipoLigacao());
		}
	}

}
