package br.edu.ifsc.Proj3.usuarioDAO;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.List;


import br.edu.ifsc.Proj3.model.Usuario;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class UsuarioDAO implements InterfaceUsuarioDAO<Usuario> {
	
	private static ObservableList<Usuario> usuarios;

	private static List<Usuario> contatos;
	private String ipServer = "localhost";
	private int portServer = 1024;
	
	@Override
	public Usuario encontrarUsuario(String numero) {
		
		Usuario usuario = null;
		
		try {
			Socket server = new Socket(ipServer, portServer);

			ObjectOutputStream out = new ObjectOutputStream(server.getOutputStream());
			out.writeUTF("usuario;encontrarUsuario;" + numero);
			out.flush();

			ObjectInputStream in = new ObjectInputStream(server.getInputStream());
			String msg = in.readUTF();

			if (!msg.contains("404")) {
				String[] splitResult = msg.split(";");
				usuario = new Usuario(splitResult[0], splitResult[1]);
			}

			in.close();
			out.close();
			server.close();
		} catch (Exception e) {
			System.out.println("Erro: " + e.getMessage());
		}
		if(usuario != null) {
			System.out.println("nome:" + usuario.getNome() + " numero:" + usuario.getNumero()); 
		}
		
		return usuario;
	}

	@Override
	public boolean adicionarUsuario(Usuario usuarioCadastro) {
		try {
			Socket server = new Socket(ipServer, portServer);
			
			ObjectOutputStream out = new ObjectOutputStream(server.getOutputStream());
			out.writeUTF("usuario;cadastrar;" + usuarioCadastro.getNome() + ";" + usuarioCadastro.getNumero() + ";" + usuarioCadastro.getPais());
			out.flush();
			
			ObjectInputStream in = new ObjectInputStream(server.getInputStream());
			String msg = in.readUTF();
			
			if (!msg.contains("404")) {
				out.close();
				server.close();
				
				return true;
				
			}
			else {
				out.close();
				server.close();
				return false;
			}
			
		} catch (Exception e) {
			System.out.println("Erro: " + e.getMessage());
			
			return false;
		}	
		
	}

	@Override
	public List<Usuario> pegarTodos() throws UnknownHostException, IOException {
		usuarios = FXCollections.observableArrayList();
		Socket server = new Socket(ipServer, portServer);

		ObjectOutputStream out = new ObjectOutputStream(server.getOutputStream());
		out.writeUTF("usuario;pegarTodos");
		out.flush();

		ObjectInputStream in = new ObjectInputStream(server.getInputStream());
		String msg = in.readUTF();

		if (!msg.contains("404") && msg.length() > 0) {
			String[] splitResult = msg.split(";");
			int userIndex = 0;
			while (userIndex < splitResult.length) {
				Usuario user = new Usuario(splitResult[userIndex]);
				usuarios.add(user);
				userIndex += 1;
			}
		}
		in.close();
		out.close();
		server.close();

		return usuarios;
	}

	@Override
	public List<Usuario> pegarContatos() throws UnknownHostException, IOException {
		contatos = FXCollections.observableArrayList();
		Socket server = new Socket(ipServer, portServer);

		ObjectOutputStream out = new ObjectOutputStream(server.getOutputStream());
		out.writeUTF("usuario;pegarContatos");
		out.flush();

		ObjectInputStream in = new ObjectInputStream(server.getInputStream());
		String msg = in.readUTF();

		if (!msg.contains("404") && msg.length() > 0) {
			String[] splitResult = msg.split(";");
			
			int indiceContato = 0;
			while (indiceContato < splitResult.length) {
				Usuario contato = new Usuario(splitResult[indiceContato], splitResult[indiceContato+1], splitResult[indiceContato+2],splitResult[indiceContato+3]);
				contatos.add(contato);
				indiceContato += 4;
			}
		}
		in.close();
		out.close();
		server.close();
		
		for (int i = 0; i < contatos.size(); i++) {
			System.out.println(contatos.get(i));
		}

		return contatos;
	}

	@Override
	public Usuario alterarUsuario(String tipoAlteracao, String alteracao) {
		Usuario usuario = null;
		
		if(tipoAlteracao == "nome") {
			alterarNomeUsuario(alteracao);
		}
		
		return usuario;
	}
	
	public void alterarNomeUsuario(String nomeNovo) {
	
		try {
			Socket server = new Socket(ipServer, portServer);

			ObjectOutputStream out = new ObjectOutputStream(server.getOutputStream());
			out.writeUTF("usuario;mudarNome;" + nomeNovo);
			out.flush();
			
			ObjectInputStream in = new ObjectInputStream(server.getInputStream());
			String msg = in.readUTF();
			
			
			in.close();
			out.close();
			server.close();
		} catch (Exception e) {
			System.out.println("Erro: " + e.getMessage());
		}
	}

	@Override
	public void alterarRecado(String recado) {
		try {
			Socket server = new Socket(ipServer, portServer);

			ObjectOutputStream out = new ObjectOutputStream(server.getOutputStream());
			out.writeUTF("usuario;mudarRecado;" + recado);
			out.flush();
			
			ObjectInputStream in = new ObjectInputStream(server.getInputStream());
			String msg = in.readUTF();
			
			
			in.close();
			out.close();
			server.close();
		} catch (Exception e) {
			System.out.println("Erro: " + e.getMessage());
		}
		
	}

	@Override
	public Usuario apagarUsuario(String numero) {
		Usuario usuario = null;
		
		try {
			Socket server = new Socket(ipServer, portServer);

			ObjectOutputStream out = new ObjectOutputStream(server.getOutputStream());
			out.writeUTF("usuario;apagarUsuario;" + numero);
			out.flush();

			ObjectInputStream in = new ObjectInputStream(server.getInputStream());
			String msg = in.readUTF();

			if (!msg.contains("404")) {
				String[] splitResult = msg.split(";");
				usuario = new Usuario(splitResult[0]);
			}

			in.close();
			out.close();
			server.close();
		} catch (Exception e) {
			System.out.println("Erro: " + e.getMessage());
		}
		
		return usuario;
	}

}
