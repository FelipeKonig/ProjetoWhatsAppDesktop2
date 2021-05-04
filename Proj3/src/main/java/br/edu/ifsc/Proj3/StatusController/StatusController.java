package br.edu.ifsc.Proj3.StatusController;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import br.edu.ifsc.Proj3.model.Status;
import br.edu.ifsc.Proj3.App;
import br.edu.ifsc.Proj3.StatusDAO.StatusDAO;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class StatusController implements Initializable{
	List<Status> status;

    @FXML
    private ImageView iconAddStatus;

    @FXML
    private ListView<Status> listStatus;

    @FXML
    private ImageView iconVoltar;
    
    @FXML
    void btVoltar(MouseEvent event) throws IOException {
    	Stage stage = new Stage();
		FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("Menu.fxml"));
		Parent root = (Parent) fxmlLoader.load();
		stage.setScene(new Scene(root));
		stage.show();
		Stage stage2 = (Stage) listStatus.getScene().getWindow();
		stage2.close();
    }
    
    @FXML
    void StatusSelecionado(MouseEvent event) {
    	
    }

    @FXML
    void addStatus(MouseEvent event) throws IOException {
		Stage stage = new Stage();
		FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("AddStatus.fxml"));
		Parent root = (Parent) fxmlLoader.load();
		stage.setScene(new Scene(root));
		stage.show();
		Stage stage2 = (Stage) iconVoltar.getScene().getWindow();
		stage2.close();
    }

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		preencherLista();
		
	}
	
	void preencherLista(){
		StatusDAO statusDAO = new StatusDAO();
		List<Status> statusContatos;

		try {
			statusContatos = statusDAO.pegarStatus();
			List<Status> lista = statusContatos;
			
			for (int i = 0; i < lista.size(); i++) {
			
				for (int j = 0; j < lista.size(); j++) {
					
					if(lista.get(j).getNomeUsuario() == lista.get(i).getNomeUsuario()) {
						lista.remove(i);
					}
				}
			}
			
			listStatus.setItems((ObservableList<Status>) lista);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
