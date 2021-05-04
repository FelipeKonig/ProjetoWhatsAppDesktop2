package br.edu.ifsc.Proj3server.ChamadaDAO;



import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;

import br.edu.ifsc.Proj3server.database.Conn;
import br.edu.ifsc.Proj3server.model.Chamada;
import br.edu.ifsc.Proj3server.model.Usuario;
import br.edu.ifsc.Proj3server.usuarioDAO.UsuarioDAO;

public class chamadaDAO implements InterfaceChamadaDAO {
	
	@Override
	public Chamada adicionarChamada(String nomeContato, String tipoChamada, String numeroUsuario) {
		Chamada chamadaUsuario = null;
		
		EntityManager em = Conn.getEntityManager();
		List<Usuario> usuarios = em.createQuery("select user from Usuario as user", Usuario.class).getResultList();

		
		for (Usuario usuarioLogado : usuarios) {
			if (usuarioLogado.getNumero().contentEquals(UsuarioDAO.usuarioLogado.getNumero())) {
				System.out.println(usuarioLogado.getNome());
				
				em.getTransaction().begin();
				Chamada chamada = new Chamada(nomeContato,tipoChamada);
				em.persist(chamada);
				em.getTransaction().commit();
				
				chamadaUsuario = chamada;
				
				System.out.println("Chamada salvada: " + chamada.getContato() + "," + chamada.getTipoLigacao());
				
				em.getTransaction().begin();
				usuarioLogado.getChamadas().add(chamada);
				em.getTransaction().commit();
				
				UsuarioDAO.usuarioLogado = usuarioLogado;
			}
		}
		em.close();
		return chamadaUsuario;
	}

	public static void adicionarChamada(ObjectOutputStream out, String[] recebido) throws IOException {
		Chamada chamada = new chamadaDAO().adicionarChamada(recebido[2], recebido[3], recebido[4]);
		
		if (chamada == null) {
			out.writeUTF("404");
		} else {
			System.out.println("Chamada adicionada");
			out.writeUTF("chamada adicionada");
		}
		
	}

	@Override
	public List<Chamada> pegarChamadas(String numero) throws UnknownHostException, IOException {
		Usuario usuario = null;
		
		EntityManager em = Conn.getEntityManager();
		List<Usuario> usuarios = em.createQuery("select user from Usuario as user", Usuario.class).getResultList();
		
		for(Usuario buscarUsuario : usuarios) {
			if(buscarUsuario.getNumero().contentEquals(numero)) {
				usuario = buscarUsuario;
			}
		}
		List<Chamada> chamadas = new ArrayList<Chamada>();
		
		for(int i = 0; i < usuario.getChamadas().size(); i++) {
			
			chamadas.add(usuario.getChamadas().get(i));
			
			System.out.println(usuario.getChamadas().get(i));
		}
		
		em.close();
		
		return chamadas;
	}
	
	public static void pegarChamadas(ObjectOutputStream out, String[] recebido) throws IOException {
		String msg = "";
		List<Chamada> chamadas = new chamadaDAO().pegarChamadas(recebido[2]);
		if (chamadas == null) {
			out.writeUTF("404");
		}
		else
			for (Chamada chamada : chamadas)
				msg = msg.concat(chamada.getContato() + ";" + chamada.getTipoLigacao() + ";");

		out.writeUTF(msg);
		
	}

	@Override
	public Chamada removerChamada(String nomeContato, String tipoChamada) {
		
		Chamada chamada = new Chamada(nomeContato, tipoChamada);
		
		EntityManager em = Conn.getEntityManager();
		List<Usuario> usuarios = em.createQuery("select user from Usuario as user", Usuario.class).getResultList();
		
		em.getTransaction().begin();
		
		for(Usuario buscarUsuario : usuarios) {
			if(buscarUsuario.getNumero().contentEquals(UsuarioDAO.usuarioLogado.getNumero())) {

				for (int i = 0; i < buscarUsuario.getChamadas().size(); i++) {
					
//					System.out.println("NUMERO:" + chamada.getContato());
//					System.out.println("NUMERO ENCONTRADO: " + buscarUsuario.getChamadas().get(i).getContato() + " ");
//					
//					System.out.println("TIPO: " + chamada.getTipoLigacao());
//					System.out.println("TIPO ENCONTRADO: " + buscarUsuario.getChamadas().get(i).getTipoLigacao());
					
					if(buscarUsuario.getChamadas().get(i).getContato().contentEquals(chamada.getContato())) {
						if(buscarUsuario.getChamadas().get(i).getTipoLigacao().contentEquals(chamada.getTipoLigacao())) {
							
							em.remove(buscarUsuario.getChamadas().remove(i));
							
							UsuarioDAO.usuarioLogado = buscarUsuario;
							
							chamada = null;
						}
					}
				}
			}
		}
		em.getTransaction().commit();
		
		em.close();
		
		return chamada;
	}

	public static void apagarChamadas(ObjectOutputStream out, String[] recebido) throws IOException {
		String msg = "";
		
		Chamada chamada = new chamadaDAO().removerChamada(recebido[2], recebido[3]);
		if (chamada == null) {
			out.writeUTF("404");
		}
		else {
			msg = msg.concat( "Chamada não removida");
		}
				

		out.writeUTF(msg);
		
	}

	@Override
	public Chamada buscarChamada(String numeroContato, String tipoChamada) {
		Chamada chamada = null;
		Usuario usuario = null;
		
		EntityManager em = Conn.getEntityManager();
		List<Usuario> usuarios = em.createQuery("select user from Usuario as user", Usuario.class).getResultList();
		
		for(Usuario buscarUsuario : usuarios) {
			if(buscarUsuario.getNumero().contentEquals(UsuarioDAO.usuarioLogado.getNumero())) {
				usuario = buscarUsuario;
			}
		}
		
		for(int i = 0; i < usuario.getChamadas().size(); i++) {
			
			if(usuario.getChamadas().get(i).getContato().contentEquals(tipoChamada)) {
				if(usuario.getChamadas().get(i).getTipoLigacao().contentEquals(numeroContato)) {
					
					chamada = usuario.getChamadas().get(i);
					System.out.println("Chamada encontrada");
				}
			}
		}
		
		
		
		em.close();
		
		return chamada;
	}
	
	public static void buscarChamada(ObjectOutputStream out, String[] recebido) throws IOException {
		
		Chamada chamada = new chamadaDAO().buscarChamada(recebido[2], recebido[3]);
		if (chamada == null) {
			out.writeUTF("404");
		}
		else {
			out.writeUTF(chamada.getContato() + ";" + chamada.getTipoLigacao() + ";" + chamada.getLembrete() + ";");
		}
	}
	
	@Override
	public Chamada alterarLembrete(String numeroContato, String tipoChamada, String lembrete) {
		Chamada chamadaAlterada = null;
		Usuario usuario = null;
		
		EntityManager em = Conn.getEntityManager();
		List<Usuario> usuarios = em.createQuery("select user from Usuario as user", Usuario.class).getResultList();
		
		for(Usuario buscarUsuario : usuarios) {
			if(buscarUsuario.getNumero().contentEquals(UsuarioDAO.usuarioLogado.getNumero())) {
				usuario = buscarUsuario;
			}
		}
		
		em.getTransaction().begin();
		
		for (int i = 0; i < usuario.getChamadas().size(); i++) {
			if(usuario.getChamadas().get(i).getContato().contentEquals(tipoChamada)) {
				if(usuario.getChamadas().get(i).getTipoLigacao().contentEquals(numeroContato)) {
					
					usuario.getChamadas().get(i).setLembrete(lembrete);
					
					chamadaAlterada = usuario.getChamadas().get(i);
					
					UsuarioDAO.usuarioLogado = usuario;
					
					System.out.println(chamadaAlterada.getContato());
					System.out.println(chamadaAlterada.getTipoLigacao());
					System.out.println("Lembrete alterado");
				}
			}
		}
		
		em.getTransaction().commit();
		em.close();
		
		return chamadaAlterada;
	}

	public static void alterarChamada(ObjectOutputStream out, String[] recebido) throws IOException {
		Chamada chamada = new chamadaDAO().alterarLembrete(recebido[2], recebido[3],recebido[4]);
		if (chamada == null) {
			out.writeUTF("404");
		}
		else {
			out.writeUTF(chamada.getContato() + ";" + chamada.getTipoLigacao() + ";" + chamada.getLembrete() + ";");
		}
		
	}



}
