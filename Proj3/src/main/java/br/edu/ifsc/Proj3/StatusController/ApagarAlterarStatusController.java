package br.edu.ifsc.Proj3.StatusController;

import java.io.IOException;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.List;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;

import br.edu.ifsc.Proj3.App;
import br.edu.ifsc.Proj3.StatusDAO.StatusDAO;
import br.edu.ifsc.Proj3.UsuarioController.LoginController;
import br.edu.ifsc.Proj3.model.Status;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class ApagarAlterarStatusController implements Initializable {

    @FXML
    private ImageView iconVoltar;

    @FXML
    private JFXComboBox<String> cbStatus;

    @FXML
    private JFXButton btBuscarStatus;

    @FXML
    private TextArea txtLembrete;

    @FXML
    private JFXButton btSalvarAlteracoes;

    @FXML
    private JFXButton btApagarStatus;

    @FXML
    void btApagarStatusClicado(ActionEvent event) {
    	
    	if(cbStatus.getValue() != null) {
    		new StatusDAO().removerStatus(cbStatus.getValue());
    		
    		Alert errorALert = new Alert(AlertType.INFORMATION);
    		errorALert.setContentText("Status removido");
    		errorALert.showAndWait();
    		
    	}
    	else {
    		Alert errorALert = new Alert(AlertType.ERROR);
    		errorALert.setContentText("É preciso escolher um status para remove-lo");
    		errorALert.showAndWait();
    	}
    	
    }

    @FXML
    void btBuscarStatusClicado(ActionEvent event) {
 
    }

    @FXML
    void btSalvarAteracoesClicado(ActionEvent event) {
    	if(txtLembrete.getText().contentEquals("")) {
      		Alert errorALert = new Alert(AlertType.ERROR);
    		errorALert.setContentText("É preciso pôr um novo status para altera-lo");
    		errorALert.showAndWait();
    	}
    	else {
    		new StatusDAO().alterarStatus(txtLembrete.getText());
    		
    		Alert errorALert = new Alert(AlertType.INFORMATION);
    		errorALert.setContentText("Status alterado com sucesso");
    		errorALert.showAndWait();
    	}
    }

    @FXML
    void btVoltar(MouseEvent event) throws IOException {
		Stage stage = new Stage();
		FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("AddStatus.fxml"));
		Parent root = (Parent) fxmlLoader.load();
		stage.setScene(new Scene(root));
		stage.show();
		Stage stage2 = (Stage) btApagarStatus.getScene().getWindow();
		stage2.close();
    }

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		try {
			preencherComboBox();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	public void preencherComboBox() throws UnknownHostException, IOException {
		List<Status> listStatus = new StatusDAO(). pegarStatusUsuario(LoginController.usuario.getNumero());

		for (int i = 0; i < listStatus.size(); i++) {
			cbStatus.getItems().add(listStatus.get(i).getTxtStatus());
		}
	}

}
