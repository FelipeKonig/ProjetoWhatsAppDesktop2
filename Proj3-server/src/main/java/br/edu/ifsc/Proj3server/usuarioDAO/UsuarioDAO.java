package br.edu.ifsc.Proj3server.usuarioDAO;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;

import br.edu.ifsc.Proj3server.database.Conn;
import br.edu.ifsc.Proj3server.model.Usuario;


public class UsuarioDAO implements InterfaceUsuarioDAO<Usuario> {

	public static Usuario usuarioLogado;
	
	@Override
	public Usuario encontrarUsuario(String numero) {
		usuarioLogado = null;
		EntityManager entityMng = Conn.getEntityManager();
		List<Usuario> users = entityMng.createQuery("select user from Usuario as user", Usuario.class).getResultList();
		
		
		for (Usuario u : users) {
			u.getNumero();
			
			if (u.getNumero().contentEquals(numero)) {
				usuarioLogado = u;
				
			}
		}

		entityMng.close();		
		
		return usuarioLogado;
	}
	
	public static void encontrarUsuario(ObjectOutputStream out, String[] recebido) throws IOException {
		Usuario usuario = new UsuarioDAO().encontrarUsuario(recebido[2]);
		if (usuario == null) {
			out.writeUTF("404");
		} else {
			out.writeUTF(usuario.getNome() + ";" + usuario.getNumero());
		}
	}

	@Override
	public Usuario adicionarUsuario(String nome, String numero, String pais) {
		
		int i=0;
		
		System.out.println("numero" + numero);
		
		EntityManager entityMng = Conn.getEntityManager();
		List<Usuario> users = entityMng.createQuery("select user from Usuario as user", Usuario.class).getResultList();
		
		for (Usuario u : users) {
			u.getNumero();
			
			if (u.getNumero().contentEquals(numero)) {
				i++;		
			}
		}
		
		if(i==0) {
			Usuario usuarioCadastro = new Usuario(nome, numero, pais);
			
    		entityMng.getTransaction().begin();    		
			entityMng.persist(usuarioCadastro);
    		entityMng.getTransaction().commit();
    		
    		entityMng.close();
    		
    		return usuarioCadastro;
		}
		else {
		
		entityMng.close();
		
		Usuario nenhum = null;
		return nenhum;
		
		}
		
		
	}
	
	public static void cadastrarUsuario(ObjectOutputStream out, String[] recebido) throws IOException {
		Usuario usuario = new UsuarioDAO().adicionarUsuario(recebido[2],recebido[3],recebido[4]);
		if (usuario.getId() == 0 && usuario.getNome() == null) {
			out.writeUTF("404");
		} else {
			out.writeUTF(usuario.getNome() + ";" + usuario.getNumero() + ";" + usuario.getPais());
		}
	}

	@Override
	public List<Usuario> pegarTodos1() {
		List<Usuario> users = new ArrayList<Usuario>();
		EntityManager entityMng = Conn.getEntityManager();
		
		System.out.println("Usuarios: ");
		
		users = entityMng.createQuery("select user from Usuario as user", Usuario.class).getResultList();
		entityMng.close();
		System.out.println(users);
		return users;
	}
	
	public static void pegarTodos(ObjectOutputStream out) throws IOException {
		String msg = "";
		List<Usuario> users = new UsuarioDAO().pegarTodos1();
		if (users == null)
			out.writeUTF("404");
		else
			for (Usuario user : users)
				msg = msg.concat(user.getNome() + ";");
		out.writeUTF(msg);
		
	}

	@Override
	public List<Usuario> pegarContatos() throws UnknownHostException, IOException {
		List<Usuario> contatos = new ArrayList<Usuario>();
		
		for(Usuario contato : usuarioLogado.getContatos()) {
		
			contatos.add(contato);
		}
		
		return contatos;
	}
	
	public static void pegarContatos(ObjectOutputStream out, String[] recebido) throws IOException {
		String msg = "";
		List<Usuario> contatos = new UsuarioDAO().pegarContatos();
		if (contatos == null) {
			out.writeUTF("404");
		}
		else
			for (Usuario contato : contatos)
				msg = msg.concat(contato.getNome() + ";" + contato.getNumero() + ";" + contato.getRecado() + ";" + contato.getComentario() + ";");

		out.writeUTF(msg);
	}

	@Override
	public Usuario alterarUsuario(String tipoAleteracao,String alteracao) {
		Usuario usuario = null;
		
		if(tipoAleteracao.contentEquals("mudarNome")) {
			usuario = alterarNomeUsuario(alteracao);
		}
		return usuario;
	}

	public static void alterarUsuario(ObjectOutputStream out, String[] recebido) throws UnknownHostException, IOException {
		Usuario usuario = new UsuarioDAO().alterarUsuario(recebido[1], recebido[2]);
		
		if (usuario == null) {
			out.writeUTF("404");
		} else {
			out.writeUTF(usuario.getNome() + ";");
		}
		
	}
	
	public Usuario alterarNomeUsuario(String nome) {
		Usuario usuario = usuarioLogado;
		
		System.out.println("Usuario: " + usuario.getNome() + " numero: " + usuario.getNumero());

		EntityManager entityMng = Conn.getEntityManager();
		List<Usuario> usuarios = entityMng.createQuery("select user from Usuario as user", Usuario.class).getResultList();
		
		entityMng.getTransaction().begin();
		
		int verifica = 0;
		for(Usuario buscarUsuario : usuarios ) {
			if (buscarUsuario.getNumero().contentEquals(usuario.getNumero())) {
				
				
				buscarUsuario.setNome(nome);		
				
				System.out.println("O nome foi alterado para " + nome);
				entityMng.getTransaction().commit();
				
				verifica = 1;
			}
		}
		
		entityMng.close();
		
		if(verifica == 0) {
			usuario = null;
			
			return usuario;
		}
		
		return usuario;
	}

	@Override
	public String alterarRecado(String recado) {
		
		Usuario usuario = usuarioLogado;
		
		System.out.println("Usuario: " + usuario.getNome() + " numero: " + usuario.getNumero());

		EntityManager entityMng = Conn.getEntityManager();
		List<Usuario> usuarios = entityMng.createQuery("select user from Usuario as user", Usuario.class).getResultList();
		
		entityMng.getTransaction().begin();
	
		for(Usuario buscarUsuario : usuarios ) {
			if (buscarUsuario.getNumero().contentEquals(usuario.getNumero())) {
				
				
				buscarUsuario.setRecado(recado);	
				
				System.out.println("O recado foi alterado para: " + recado);
				entityMng.getTransaction().commit();
			}
		}
		
		entityMng.close();
		
		return recado;
		
	}

	public static void alterarRecado(ObjectOutputStream out, String[] recebido) throws IOException {
		
		String recado = new UsuarioDAO().alterarRecado(recebido[2]);
		
		if (recado == null) {
			out.writeUTF("404");
		} else {
			out.writeUTF(recado + ";");
		}
		
	}

	@Override
	public Usuario apagarUsuario(String numero) {
		Usuario usuario = usuarioLogado;
		
		EntityManager entityMng = Conn.getEntityManager();
		List<Usuario> usuarios = entityMng.createQuery("select user from Usuario as user", Usuario.class).getResultList();
	
		for(int i = 0; i < usuarios.size(); i++) {
			Usuario usuariosCadastrados = usuarios.get(i);
			
			for (int j = 0; j < usuariosCadastrados.getContatos().size(); j++) {
				if(usuariosCadastrados.getContatos().get(j).getNumero().contentEquals(numero)) {
					
					entityMng.getTransaction().begin();
					usuariosCadastrados.getContatos().remove(j);
					entityMng.getTransaction().commit();
					
				}
			}
		}
		
		
		for (Usuario usuarioEncontrado : usuarios) {
			if (usuarioEncontrado.getNumero().contentEquals(numero)) {
				if(usuarioEncontrado.getContatos() != null)
					usuarioEncontrado.getContatos().clear();
				
				entityMng.getTransaction().begin();
				entityMng.remove(usuarioEncontrado);
				entityMng.getTransaction().commit();
				
				entityMng.close();
				
				usuario = null;
			}
		}
	
		
		
		return usuario;
	}
	
	public static void apagarUsuario(ObjectOutputStream out, String[] recebido) throws IOException {
		
		Usuario usuario = new UsuarioDAO().apagarUsuario(recebido[2]);
		
		if (usuario != null) {
			out.writeUTF("404");
		} else {
			out.writeUTF(usuario + ";");
		}
		
	}

	@Override
	public List<Usuario> pegarTodos() throws UnknownHostException, IOException {
		// TODO Auto-generated method stub
		return null;
	}



}
