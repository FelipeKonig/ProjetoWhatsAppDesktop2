package br.edu.ifsc.Proj3.ContatoDAO;


import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

import br.edu.ifsc.Proj3.model.Usuario;

public class ContatoDAO implements InterfaceContatoDAO{
	
	private String ipServer = "localhost";
	private int portServer = 1024;

	@Override
	public int adicionarContato(String numero, String tipoNumero, String mensagem, String ficheiro, String ligacao) throws UnknownHostException, IOException {
			
			int verifica = enviarContatoServidor(numero, tipoNumero, mensagem, ficheiro, ligacao);
			
			return verifica;
		}
	
	public int enviarContatoServidor(String numero, String tipoNumero, String mensagem, String ficheiro, String ligacao) {
		try {
			Socket server = new Socket(ipServer, portServer);
			
			ObjectOutputStream out = new ObjectOutputStream(server.getOutputStream());
			out.writeUTF("usuario;adicionarContato;" + numero + ";" + tipoNumero + ";" + mensagem + ";" + ficheiro + ";" + ligacao + ";");
			out.flush();
			
			ObjectInputStream in = new ObjectInputStream(server.getInputStream());
			String msg = in.readUTF();
			
			if (!msg.contains("404")) {
				out.close();
				server.close();
				
				return 1;
				
			}
			else {
				out.close();
				server.close();
			}
			
		} catch (Exception e) {
			System.out.println("Erro: " + e.getMessage());
		}	
		
		return 0;
	}

	@Override
	public Usuario encontrarContato(String numero) {
		Usuario contato = null;
		
		try {
			Socket server = new Socket(ipServer, portServer);

			ObjectOutputStream out = new ObjectOutputStream(server.getOutputStream());
			out.writeUTF("usuario;encontrarContato;" + numero);
			out.flush();

			ObjectInputStream in = new ObjectInputStream(server.getInputStream());
			String msg = in.readUTF();

			if (!msg.contains("404")) {
				String[] splitResult = msg.split(";");
				contato = new Usuario(splitResult[0], splitResult[1]);
			}

			in.close();
			out.close();
			server.close();
		} catch (Exception e) {
			System.out.println("Erro: " + e.getMessage());
		}
		if(contato != null) {
			System.out.println("nome do contato:" + contato.getNome() + " numero do contato:" + contato.getNumero()); 
		}
		
		return contato;
	}

	@Override
	public int apagarContato(String numeroContato) {
		int resposta = 0;
		
		try {
			Socket server = new Socket(ipServer, portServer);
			
			ObjectOutputStream out = new ObjectOutputStream(server.getOutputStream());
			out.writeUTF("usuario;apagarContato;" + numeroContato + ";");
			out.flush();
			
			ObjectInputStream in = new ObjectInputStream(server.getInputStream());
			String msg = in.readUTF();
			
			if (!msg.contains("404")) {
				out.close();
				server.close();
				
				return resposta = 1;
			}
			else {
				out.close();
				server.close();
			}
			
		} catch (Exception e) {
			System.out.println("Erro: " + e.getMessage());
		}	
		
		return resposta;
	}

	@Override
	public int alterarContato(String numeroContato, String tipoNumero, String mensagem, String ficheiro, String ligacao) {
		int resposta = 0;
		
		try {
			Socket server = new Socket(ipServer, portServer);
			
			ObjectOutputStream out = new ObjectOutputStream(server.getOutputStream());
			out.writeUTF("usuario;alterarContato;" + numeroContato + ";" + tipoNumero + ";" + mensagem + ";" + ficheiro + ";" + ligacao);
			out.flush();
			
			ObjectInputStream in = new ObjectInputStream(server.getInputStream());
			String msg = in.readUTF();
			
			if (!msg.contains("404")) {
				out.close();
				server.close();
				
				return resposta = 1;
			}
			else {
				out.close();
				server.close();
			}
			
		} catch (Exception e) {
			System.out.println("Erro: " + e.getMessage());
		}	
		
		return resposta;
	}

}
