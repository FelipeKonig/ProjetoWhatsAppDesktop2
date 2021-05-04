package br.edu.ifsc.Proj3server.StatusDAO;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;

import br.edu.ifsc.Proj3server.database.Conn;
import br.edu.ifsc.Proj3server.model.Status;
import br.edu.ifsc.Proj3server.model.Usuario;
import br.edu.ifsc.Proj3server.usuarioDAO.UsuarioDAO;

public class StatusDAO implements InterfaceStatusDAO{

	@Override
	public List<Status> pegarStatus() throws UnknownHostException, IOException {
		List<Status> statusList = new ArrayList<Status>();
		
		for(int i = 0; i < UsuarioDAO.usuarioLogado.getContatos().size(); i++){
			
			for (int j = 0; j < UsuarioDAO.usuarioLogado.getContatos().get(i).getStatus().size(); j++) {
				
				statusList.add(UsuarioDAO.usuarioLogado.getContatos().get(i).getStatus().get(j));
			}
		}
		
		return statusList;
	}
	
	public static void pegarStatus(ObjectOutputStream out, String[] recebido) throws UnknownHostException, IOException {
		String msg = "";
		List<Status> statusLista = new StatusDAO().pegarStatus();
		if (statusLista == null) {
			out.writeUTF("404");
		}
		else
			for (Status status : statusLista)
				msg = msg.concat(status.getTxtStatus() + ";" + status.getNomeUsuario() + ";");
		
		System.out.println("Lista de status enviada");

		out.writeUTF(msg);
		
	}
	
	@Override
	public void adicionarStatus(String mensagem, String nomeUsuario) {
		Status status = new Status(mensagem, nomeUsuario);
		
		EntityManager entityMng = Conn.getEntityManager();
		List<Usuario> usuarios = entityMng.createQuery("select user from Usuario as user", Usuario.class).getResultList();
		
		entityMng.getTransaction().begin();
		
		entityMng.persist(status);
		
		entityMng.getTransaction().commit();
		
		entityMng.getTransaction().begin();
		for (Usuario usuario : usuarios) {
			if(usuario.getNumero().contentEquals(UsuarioDAO.usuarioLogado.getNumero())) {
								
				usuario.getStatus().add(status);
				UsuarioDAO.usuarioLogado = usuario;
			}
		}
		entityMng.getTransaction().commit();
		
		
		
		entityMng.close();
	}

	public static void adicionarStatus(ObjectOutputStream out, String[] recebido) throws IOException {
		new StatusDAO().adicionarStatus(recebido[2], recebido[3]);
		
		String msg = "Status enviado";
		
		out.writeUTF(msg);
	}

	@Override
	public void removerStatus(String mensagem) {
		EntityManager entityMng = Conn.getEntityManager();
		List<Usuario> usuarios = entityMng.createQuery("select user from Usuario as user", Usuario.class).getResultList();
		
		System.out.println("MENSAGEM STATUS: " + mensagem);
		entityMng.getTransaction().begin();
		for (Usuario usuario : usuarios) {
			if(usuario.getNumero().contentEquals(UsuarioDAO.usuarioLogado.getNumero())) {
								
				for (int i = 0; i < usuario.getStatus().size(); i++) {
					
					System.out.println(usuario.getStatus().get(i).getTxtStatus());
					
					if(usuario.getStatus().get(i).getTxtStatus().contentEquals(mensagem)) {
						usuario.getStatus().remove(i);
						
						UsuarioDAO.usuarioLogado = usuario;
					}
				}
			}
		}
		entityMng.getTransaction().commit();
		
		
		entityMng.close();
		
	}
	
	public static void removerStatus(ObjectOutputStream out, String[] recebido) throws IOException {
		new StatusDAO().removerStatus(recebido[2]);
		
		String msg = "Status removido";
		
		out.writeUTF(msg);
	}

	@Override
	public List<Status> pegarStatusUsuario(String numero) throws UnknownHostException, IOException {
		List<Status> statusList = new ArrayList<Status>();
		
		for(int i = 0; i < UsuarioDAO.usuarioLogado.getStatus().size(); i++){

			statusList.add(UsuarioDAO.usuarioLogado.getStatus().get(i));

		}
		
		return statusList;
	}
	
	public static void pegarStatusUsuario(ObjectOutputStream out, String[] recebido) throws UnknownHostException, IOException {
		
		String msg = "";
		List<Status> statusLista = new StatusDAO().pegarStatusUsuario(recebido[2]);
		if (statusLista == null) {
			out.writeUTF("404");
		}
		else
			for (Status status : statusLista)
				msg = msg.concat(status.getTxtStatus() + ";" + status.getNomeUsuario() + ";");
		
		System.out.println("Lista de status enviada");

		out.writeUTF(msg);
		
	}

	@Override
	public void alterarStatus(String mensagem) {
		EntityManager entityMng = Conn.getEntityManager();
		List<Usuario> usuarios = entityMng.createQuery("select user from Usuario as user", Usuario.class).getResultList();
		
		entityMng.getTransaction().begin();
		
		for (int i = 0; i < usuarios.size(); i++) {
			if(usuarios.get(i).getNumero().contentEquals(UsuarioDAO.usuarioLogado.getNumero())) {
			
				for (int j = 0; j < usuarios.get(i).getStatus().size(); j++) {
					
					if(usuarios.get(i).getStatus().get(j).getTxtStatus().contentEquals(mensagem)) {
						usuarios.get(i).getStatus().get(j).setTxtStatus(mensagem);
					}
					UsuarioDAO.usuarioLogado = usuarios.get(i);
				}
			}
			
			entityMng.getTransaction().commit();
			
			
			entityMng.close();
		}
		
	}

	public static void alterarStatus(ObjectOutputStream out, String[] recebido) throws IOException {
		String msg = "foi";
		new StatusDAO().alterarStatus(recebido[2]);
		
			out.writeUTF(msg);
			
		
	}



}
