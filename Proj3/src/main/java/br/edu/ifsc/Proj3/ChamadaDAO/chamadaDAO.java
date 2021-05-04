package br.edu.ifsc.Proj3.ChamadaDAO;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.List;

import br.edu.ifsc.Proj3.model.Chamada;
import javafx.collections.FXCollections;

public class chamadaDAO implements InterfaceChamadaDAO {
	
	private static List<Chamada> chamadas;
	
	private String ipServer = "localhost";
	int portServer = 1024;
	
	@Override
	public void adicionarChamada(String nomeContato, String tipoChamada, String numeroUsuario) {
		
		try {
			Socket server = new Socket(ipServer, portServer);

			ObjectOutputStream out = new ObjectOutputStream(server.getOutputStream());
			out.writeUTF("usuario;adicionarChamada;" + nomeContato + ";" + tipoChamada + ";" + numeroUsuario + ";");
			out.flush();

			ObjectInputStream in = new ObjectInputStream(server.getInputStream());
			String msg = in.readUTF();

			if (!msg.contains("404")) {
				System.out.println("Chamada para o contato:" + nomeContato + " tipo da ligação:" + tipoChamada); 
				
			}

			in.close();
			out.close();
			server.close();
		} catch (Exception e) {
			System.out.println("Erro: " + e.getMessage());
		}
		
	}

	@Override
	public List<Chamada> pegarChamadas(String numero) throws IOException {
		chamadas = FXCollections.observableArrayList();
		Socket server = new Socket(ipServer, portServer);

		ObjectOutputStream out = new ObjectOutputStream(server.getOutputStream());
		out.writeUTF("usuario;pegarChamadas" + ";" + numero + ";");
		out.flush();

		ObjectInputStream in = new ObjectInputStream(server.getInputStream());
		String msg = in.readUTF();

		if (!msg.contains("404") && msg.length() > 0) {
			String[] splitResult = msg.split(";");
			
			int indiceChamada = 0;
			while (indiceChamada < splitResult.length) {
				Chamada chamada = new Chamada(splitResult[indiceChamada], splitResult[indiceChamada+1]);
				chamadas.add(chamada);
				indiceChamada += 2;
			}
		}
		in.close();
		out.close();
		server.close();

		return chamadas;
	}

	@Override
	public void removerChamada(String nomeContato, String tipoChamada) {
				
		try {
			Socket server = new Socket(ipServer, portServer);
			
			ObjectOutputStream out = new ObjectOutputStream(server.getOutputStream());
			out.writeUTF("usuario;apagarChamada;" + nomeContato + ";" + tipoChamada + ";");
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
	public void alterarLembrete(String nomeContato, String tipoChamada, String lembrete) {
		
		try {
			Socket server = new Socket(ipServer, portServer);
			
			ObjectOutputStream out = new ObjectOutputStream(server.getOutputStream());
			out.writeUTF("usuario;alterarLembrete;" + nomeContato + ";" + tipoChamada + ";" + lembrete + ";");
			out.flush();
			
			ObjectInputStream in = new ObjectInputStream(server.getInputStream());
			String msg = in.readUTF();
			
			if (!(msg.contains("404"))) {
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
	public Chamada buscarChamada(String nomeContato, String tipoChamada) {
		Chamada chamada = null;
		
		try {
			Socket server = new Socket(ipServer, portServer);
			
			ObjectOutputStream out = new ObjectOutputStream(server.getOutputStream());
			out.writeUTF("usuario;buscarChamada;" + nomeContato + ";" + tipoChamada + ";");
			out.flush();
			
			ObjectInputStream in = new ObjectInputStream(server.getInputStream());
			String msg = in.readUTF();
			
			if (!(msg.contains("404"))) {
				String[] splitResult = msg.split(";");
				
				Chamada chamadaEncontrada = new Chamada(splitResult[0], splitResult[1], splitResult[2]);
				chamada = chamadaEncontrada;
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
		if(chamada != null) {
			System.out.println("Chamada encontrada: " + chamada.getContato());
		}
		return chamada;
	}

	
}
