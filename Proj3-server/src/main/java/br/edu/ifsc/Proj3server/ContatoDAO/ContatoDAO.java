package br.edu.ifsc.Proj3server.ContatoDAO;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.List;

import javax.persistence.EntityManager;

import br.edu.ifsc.Proj3server.database.Conn;
import br.edu.ifsc.Proj3server.model.Usuario;
import br.edu.ifsc.Proj3server.usuarioDAO.UsuarioDAO;

public class ContatoDAO implements InterfaceContatoDAO{

	@Override
	public Usuario adicionarContato(String numero, String tipoNumero,String mensagem, String ficheiro, String ligacao ) {
		Usuario contato = null;
		Usuario usuarioLogin = null;
		
		EntityManager entityMng = Conn.getEntityManager();
		List<Usuario> users = entityMng.createQuery("select user from Usuario as user", Usuario.class).getResultList();
		
		boolean bMensagem, bFicheiro, bLigacao;
		
		bMensagem = verificaBoolean("mensagem", mensagem);
		bFicheiro = verificaBoolean("ficheiro", ficheiro);
		bLigacao = verificaBoolean("ligacao", ligacao);
		
		for (Usuario contatos : users) {
			if(contatos.getNumero().contentEquals(numero)) {
				contato = contatos;
			}
			
			if(contatos.getNumero().contentEquals(UsuarioDAO.usuarioLogado.getNumero())) {
				usuarioLogin = contatos;
				
				UsuarioDAO.usuarioLogado.setContatos(usuarioLogin.getContatos());
			}
		}
		
		for (int i = 0; i < usuarioLogin.getContatos().size(); i++) {
			if(usuarioLogin.getContatos().get(i).getNumero().contentEquals(numero)) {
				
				entityMng.close();
				System.out.println("O contato já está adicionado na lista de contatos");
				return null;	
			
				
			}
		}

		
		System.out.println("Numero do usuario: " + UsuarioDAO.usuarioLogado.getNumero());
		
		entityMng.getTransaction().begin();
		
		for (Usuario usuario : users) {
			usuario.getNumero();
			
			System.out.println("numero: " + usuario.getNumero());
			
			if (usuario.getNumero().contentEquals(UsuarioDAO.usuarioLogado.getNumero())) {
				

				
				usuario.getContatos().add(contato);
				entityMng.persist(usuario);
				
				contato.setTipoNumero(tipoNumero);
				contato.setMensagem(bMensagem);
				contato.setFicheiro(bFicheiro);
				contato.setLigacao(bLigacao);
				
				entityMng.getTransaction().commit();
				
				UsuarioDAO.usuarioLogado.setContatos(usuario.getContatos());
				
				return contato;
			}
		}
		
		entityMng.close();
		
		return null;
	}

	public static void adicionarContato(ObjectOutputStream out, String[] recebido) throws IOException {
		Usuario usuario = new ContatoDAO().adicionarContato(recebido[2],recebido[3],recebido[4],recebido[5], recebido[6]);
		
		if (usuario == null) {
			
			out.writeUTF("404");
		} else {
			out.writeUTF(usuario.getNome() + ";" + usuario.getNumero() + ";" + usuario.getPais());
		}
	}
	
	public boolean verificaBoolean(String opcao, String bool) {
		
		if(opcao.contentEquals("mensagem")) {
			if(bool.contentEquals("true")) {
				return true;
			}
		}
		
		if(opcao.contentEquals("ficheiro")) {
			if(bool.contentEquals("true")) {
				return true;
			}
		}
		
		if(opcao.contentEquals("ligacao")) {
			if(bool.contentEquals("true")) {
				return true;
			}
		}
		
		return false;
	}

	@Override
	public Usuario encontrarContato(String numero) {
		Usuario contato = null;
		
		EntityManager entityMng = Conn.getEntityManager();
		List<Usuario> users = entityMng.createQuery("select user from Usuario as user", Usuario.class).getResultList();
		
		
		for (Usuario u : users) {
			u.getNumero();
			
			if (u.getNumero().contentEquals(numero)) {
				contato = u;
				
			}
		}

		entityMng.close();
		
		if(contato != null) {
			System.out.println(contato.getNome());
		}
		
		
		return contato;	
		
	}

	public static void encontrarContato(ObjectOutputStream out, String[] recebido) throws IOException {
		Usuario usuario = new ContatoDAO().encontrarContato(recebido[2]);
		
		if (usuario == null) {
			out.writeUTF("404");
		} else {
			out.writeUTF(usuario.getNome() + ";" + usuario.getNumero() + ";" + usuario.getPais());
		}
		
	}

	@Override
	public Usuario apagarContato(String numeroContato) {
		Usuario contato = null;
		Usuario usuario = null;
		
		EntityManager entityMng = Conn.getEntityManager();
		List<Usuario> users = entityMng.createQuery("select user from Usuario as user", Usuario.class).getResultList();
		
		
		for (Usuario u : users) {
			u.getNumero();
			
			if (u.getNumero().contentEquals(UsuarioDAO.usuarioLogado.getNumero())) {
				usuario = u;
				
			}
		}
		
		entityMng.getTransaction().begin();
		
		for (int i = 0; i < usuario.getContatos().size(); i++) {
			if(usuario.getContatos().get(i).getNumero().contentEquals(numeroContato)) {
				contato = usuario.getContatos().get(i);
				
				usuario.getContatos().remove(i);
				
				UsuarioDAO.usuarioLogado.setContatos(usuario.getContatos());
				
				entityMng.getTransaction().commit();
			}
		}
		
		entityMng.close();
		
		return contato;
	}
	
	public static void apagarContato(ObjectOutputStream out, String[] recebido) throws IOException{
		Usuario usuario = new ContatoDAO().apagarContato(recebido[2]);
		
		if (usuario == null) {
			out.writeUTF("404");
		} else {
			
			System.out.println("Contato removido");
			out.writeUTF("Contato removido");
		}
	}

	@Override
	public Usuario alterarContato(String numero, String tipoNumero, String mensagem, String ficheiro, String ligacao) {
		Usuario contato = null;
		Usuario usuario = null;
		
		boolean bMensagem, bFicheiro, bLigacao;
		
		bMensagem = verificaBoolean("mensagem", mensagem);
		bFicheiro = verificaBoolean("ficheiro", ficheiro);
		bLigacao = verificaBoolean("ligacao", ligacao);
		
		EntityManager entityMng = Conn.getEntityManager();
		List<Usuario> usuarios = entityMng.createQuery("select user from Usuario as user", Usuario.class).getResultList();
		
		for (int i = 0; i < usuarios.size(); i++) {
			if(usuarios.get(i).getNumero().contentEquals(UsuarioDAO.usuarioLogado.getNumero())) {
				usuario = usuarios.get(i);
			}
		}
		
		entityMng.getTransaction().begin();
		
		for (int i = 0; i < usuario.getContatos().size(); i++) {
			if(usuario.getContatos().get(i).getNumero().contentEquals(numero)) {
				contato = usuario.getContatos().get(i);
				
				contato.setTipoNumero(tipoNumero);
				contato.setMensagem(bMensagem);
				contato.setFicheiro(bFicheiro);
				contato.setLigacao(bLigacao);
				
			}
		}
		
		entityMng.getTransaction().commit();
		entityMng.close();
		
		return contato;
	}
	
	public static void alterarContato(ObjectOutputStream out, String[] recebido) throws IOException{
		Usuario usuario = new ContatoDAO().alterarContato(recebido[2],recebido[3],recebido[4],recebido[5],recebido[6]);
		
		if (usuario == null) {
			out.writeUTF("404");
		} else {
			
			System.out.println("Contato alterado");
			out.writeUTF("Contato alterado");
		}
	}

}
