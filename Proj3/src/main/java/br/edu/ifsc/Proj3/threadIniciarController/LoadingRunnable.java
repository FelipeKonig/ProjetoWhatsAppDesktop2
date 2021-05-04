package br.edu.ifsc.Proj3.threadIniciarController;

import java.io.IOException;

import com.jfoenix.controls.JFXSpinner;

import br.edu.ifsc.Proj3.usuarioDAO.UsuarioDAO;
import javafx.application.Platform;

public class LoadingRunnable implements Runnable {
	private ThreadIniciarController loadingController;
	private JFXSpinner spinner;

	public LoadingRunnable(JFXSpinner spinner, ThreadIniciarController loadingController) {
		this.spinner = spinner;
		this.loadingController = loadingController;
	}

	@Override
	public void run() {
		updateProgress(0);
		while (true) {
			try {
				new UsuarioDAO().pegarTodos();
				Platform.runLater(() -> {
					try {
						loadingController.closeWindow();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				});
				return;
			} catch (Exception e) {				
				if(spinner.getProgress() > 0.5)
					updateProgress(0);
				else
					updateProgress(spinner.getProgress() + 0.15);
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e1) {
				}
			}
		}
	}

	private void updateProgress(double value) {
		Platform.runLater(() -> {
			spinner.setProgress(value);
		});
	}

}
