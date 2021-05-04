package br.edu.ifsc.Proj3.StatusDAO;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import br.edu.ifsc.Proj3.model.Status;
import javafx.collections.FXCollections;

public class StatusDAO implements InterfaceStatusDAO {
	
	private static List<Status> statusLista;
	private String ipServer = "localhost";
	private int portServer = 1024;

	@Override
	public List<Status> pegarStatus() throws UnknownHostException, IOException {
		statusLista = FXCollections.observableArrayList();
		Socket server = new Socket(ipServer, portServer);

		ObjectOutputStream out = new ObjectOutputStream(server.getOutputStream());
		out.writeUTF("usuario;pegarStatus");
		out.flush();

		ObjectInputStream in = new ObjectInputStream(server.getInputStream());
		String msg = in.readUTF();

		if (!msg.contains("404") && msg.length() > 0) {
			String[] splitResult = msg.split(";");
			
			int indiceContato = 0;
			while (indiceContato < splitResult.length) {
				Status status = new Status(splitResult[indiceContato], splitResult[indiceContato+1]);
				statusLista.add(status);
				indiceContato += 2;
			}
		}
		in.close();
		out.close();
		server.close();
		
		for (int i = 0; i < statusLista.size(); i++) {
			System.out.println(statusLista.get(i));
		}

		return statusLista;
	}

	@Override
	public void adicionarStatus(String mensagem, String nomeUsuario) {
		try {
			Socket server = new Socket(ipServer, portServer);
			
			ObjectOutputStream out = new ObjectOutputStream(server.getOutputStream());
			out.writeUTF("usuario;adicionarStatus;" + mensagem + ";" + nomeUsuario  + ";");
			out.flush();
			
			ObjectInputStream in = new ObjectInputStream(server.getInputStream());
			String msg = in.readUTF();
			
			if (!msg.contains("404")) {
				out.close();
				server.close();
				
			}
			else {
				out.close();
				server.close();
			}
			
		} catch (Exception e) {
			System.out.println("Erro: " + e.getMessage());
		}	
		
	}

	@Override
	public void removerStatus(String mensagem) {
		try {
			Socket server = new Socket(ipServer, portServer);
			
			ObjectOutputStream out = new ObjectOutputStream(server.getOutputStream());
			out.writeUTF("usuario;removerStatus;" + mensagem + ";");
			out.flush();
			
			ObjectInputStream in = new ObjectInputStream(server.getInputStream());
			String msg = in.readUTF();
			
			if (!msg.contains("404")) {
				out.close();
				server.close();
				
			}
			else {
				out.close();
				server.close();
			}
			
		} catch (Exception e) {
			System.out.println("Erro: " + e.getMessage());
		}	
		
	}

	@Override
	public List<Status> pegarStatusUsuario(String numeroUsuario) throws UnknownHostException, IOException {
		List<Status> statusLista = new ArrayList<Status>();
		Socket server = new Socket(ipServer, portServer);

		ObjectOutputStream out = new ObjectOutputStream(server.getOutputStream());
		out.writeUTF("usuario;pegarStatusUsuario" + ";" + numeroUsuario);
		out.flush();

		ObjectInputStream in = new ObjectInputStream(server.getInputStream());
		String msg = in.readUTF();

		if (!msg.contains("404") && msg.length() > 0) {
			String[] splitResult = msg.split(";");
			
			int indiceContato = 0;
			while (indiceContato < splitResult.length) {
				Status status = new Status(splitResult[indiceContato]);
				statusLista.add(status);
				indiceContato += 2;
			}
		}
		in.close();
		out.close();
		server.close();
		
		for (int i = 0; i < statusLista.size(); i++) {
			System.out.println(statusLista.get(i));
		}

		return statusLista;
	}

	@Override
	public void alterarStatus(String mensagem) {
		try {
			Socket server = new Socket(ipServer, portServer);
			
			ObjectOutputStream out = new ObjectOutputStream(server.getOutputStream());
			out.writeUTF("usuario;alterarStatus;" + mensagem + ";");
			out.flush();
			
			ObjectInputStream in = new ObjectInputStream(server.getInputStream());
			String msg = in.readUTF();
			
			if (!msg.contains("404")) {
				out.close();
				server.close();
				
			}
			else {
				out.close();
				server.close();
			}
			
		} catch (Exception e) {
			System.out.println("Erro: " + e.getMessage());
		}	
		
	}

}
