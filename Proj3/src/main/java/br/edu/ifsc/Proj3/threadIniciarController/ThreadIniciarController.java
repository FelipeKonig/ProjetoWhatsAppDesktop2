package br.edu.ifsc.Proj3.threadIniciarController;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXSpinner;

import br.edu.ifsc.Proj3.App;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class ThreadIniciarController implements Initializable{

    @FXML
    private JFXSpinner spinner;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		Thread loadingThread = new Thread(new LoadingRunnable(spinner, this));
		loadingThread.start();
	}

	public void closeWindow() throws IOException {		
		Scene scene = spinner.getScene();
		Stage stage = (Stage) scene.getWindow();
		stage.close();			

		FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("Login.fxml"));
		Parent parent = fxmlLoader.load();
		Scene newScene = new Scene(parent);
		Stage newStage = new Stage();
		newStage.setScene(newScene);
		newStage.show();
	}

}
