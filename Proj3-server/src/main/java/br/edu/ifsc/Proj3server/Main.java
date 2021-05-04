package br.edu.ifsc.Proj3server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Enumeration;
import java.util.List;

import javax.persistence.EntityManager;

import br.edu.ifsc.Proj3server.ChamadaDAO.chamadaDAO;
import br.edu.ifsc.Proj3server.ContatoDAO.ContatoDAO;
import br.edu.ifsc.Proj3server.StatusDAO.StatusDAO;
import br.edu.ifsc.Proj3server.database.Conn;
import br.edu.ifsc.Proj3server.exceptions.CommException;
import br.edu.ifsc.Proj3server.exceptions.NetDeviceException;
import br.edu.ifsc.Proj3server.exceptions.PortException;
import br.edu.ifsc.Proj3server.model.Chamada;
import br.edu.ifsc.Proj3server.model.Status;
import br.edu.ifsc.Proj3server.model.Usuario;
import br.edu.ifsc.Proj3server.usuarioDAO.UsuarioDAO;

public class Main{

	public static void main(String[] args) {
		
//		Chamada chamada1 = new Chamada("1234", "Ligação");
//		
//		Usuario usuario1 = new Usuario("Felipe", "000");
//		Usuario usuario2 = new Usuario("Ana", "123");
//		Usuario usuario3 = new Usuario("Jonas", "1234");
//		Usuario usuario4 = new Usuario("Eduardo", "12345");
//		Usuario usuario5 = new Usuario("Cristiano", "123456");
//		
//		usuario1.getContatos().add(usuario2);
//		usuario1.getContatos().add(usuario3);
//		usuario1.getContatos().add(usuario4);
//		usuario1.getContatos().add(usuario5);
//		
//		usuario1.getChamadas().add(chamada1);
//		
//		Status status1 = new Status("Oi, eu sou Ana", "Ana");
//		Status status2 = new Status("É isso", "Ana");
//		Status status3 = new Status("Oi, eu sou Eduardo", "Eduardo");
//		Status status4 = new Status("É isso também", "Eduardo");
//		
//		usuario2.getStatus().add(status1);
//		usuario2.getStatus().add(status2);
//		usuario4.getStatus().add(status3);
//		usuario4.getStatus().add(status4);
//		
//
//		EntityManager em = Conn.getEntityManager();
//		em.getTransaction().begin();
//		em.persist(usuario1);
//		em.persist(usuario2);
//		em.persist(usuario3);
//		em.persist(usuario4);
//		em.persist(usuario5);
//
//		em.getTransaction().commit();
//		
//		List<Usuario> users = em.createQuery("select user from Usuario as user", Usuario.class).getResultList();
//
//		System.out.println("Usuários:");
//		for (Usuario u : users)
//			System.out.println(u.getNome());
//
//		em.close();
	
		ServerSocket server = null;
		try {
			Conn.getEntityManager().close();
			printServerInfo();
			server = openSocket();
			System.out.println("O servidor está aberto na porta " + server.getLocalPort());
			while (true) {
				listen(server);
			}
		} catch (PortException ex) {
			System.err.println("Nenhuma porta disponível no servidor.");
		} catch (NetDeviceException ex) {
			System.err.println("A placa de rede está com algum problema.");
		} catch (CommException ex) {
			System.err.println("Ocorreu algum problema em uma comunicação com um cliente.");
		} finally {
			if (server != null) {
				try {
					server.close();
				} catch (IOException e) {
				}
			}
//			Conn.closeConn();
		}
	}
	
	private static void listen(ServerSocket server) throws CommException {
		try {
			Socket client = server.accept();
			process(client);
			client.close();
		} catch (IOException e) {
			throw new CommException();
		}
	}
	
	private static void process(Socket client) throws IOException {
		System.out.println("Cliente conectado: " + client.getInetAddress().getHostAddress());
	
		ObjectInputStream in = new ObjectInputStream(client.getInputStream());
		String msg = in.readUTF();
		System.out.println("Cliente enviou: " + msg);
		
		ObjectOutputStream out = new ObjectOutputStream(client.getOutputStream());

		String recebido[] = msg.split(";");
		if (recebido[0].contentEquals("usuario")) {
			if (recebido[1].contentEquals("encontrarUsuario")) 
				UsuarioDAO.encontrarUsuario(out, recebido);			
			
			else if (recebido[1].contentEquals("cadastrar")) 
				UsuarioDAO.cadastrarUsuario(out, recebido);	
			
			else if (recebido[1].contentEquals("pegarContatos")) 
				UsuarioDAO.pegarContatos(out, recebido);
			
			else if(recebido[1].contentEquals("mudarNome"))
				UsuarioDAO.alterarUsuario(out, recebido);
			
			else if(recebido[1].contentEquals("mudarRecado"))
				UsuarioDAO.alterarRecado(out, recebido);
			
			else if(recebido[1].contentEquals("pegarTodos"))
				UsuarioDAO.pegarTodos(out);
			
			else if(recebido[1].contentEquals("apagarUsuario"))
				UsuarioDAO.apagarUsuario(out, recebido);
			
			else if(recebido[1].contentEquals("adicionarContato"))
				ContatoDAO.adicionarContato(out, recebido);
			
			else if(recebido[1].contentEquals("encontrarContato"))
				ContatoDAO.encontrarContato(out, recebido);
			
			else if(recebido[1].contentEquals("apagarContato")) 
				ContatoDAO.apagarContato(out, recebido);
			
			else if(recebido[1].contentEquals("alterarContato"))
				ContatoDAO.alterarContato(out, recebido);
			
			else if(recebido[1].contentEquals("adicionarChamada"))
				chamadaDAO.adicionarChamada(out, recebido);
			
			else if(recebido[1].contentEquals("pegarChamadas"))
				chamadaDAO.pegarChamadas(out, recebido);
			
			else if(recebido[1].contentEquals("apagarChamada"))
				chamadaDAO.apagarChamadas(out, recebido);
			
			else if(recebido[1].contentEquals("buscarChamada"))
				chamadaDAO.buscarChamada(out, recebido);
			
			else if(recebido[1].contentEquals("alterarLembrete"))
				chamadaDAO.alterarChamada(out, recebido);
			
			else if(recebido[1].contentEquals("pegarStatus"))
				StatusDAO.pegarStatus(out, recebido);
			
			else if(recebido[1].contentEquals("adicionarStatus"))
				StatusDAO.adicionarStatus(out, recebido);
			
			else if(recebido[1].contentEquals("removerStatus"))
				StatusDAO.removerStatus(out, recebido);
			
			else if(recebido[1].contentEquals("pegarStatusUsuario"))
				StatusDAO.pegarStatusUsuario(out, recebido);
			
			else if(recebido[1].contentEquals("alterarStatus"))
				StatusDAO.alterarStatus(out, recebido);
			
			

		}		
		
		out.flush();
		out.close();
		in.close();

	}
	
	private static ServerSocket openSocket() throws PortException {
		int port = 1024;
		while (port <= 65535) {
			try {
				return new ServerSocket(port);
			} catch (IOException ex) {
				port++;
			}
		}
		throw new PortException();
	}

	private static void printServerInfo() throws NetDeviceException {
		try {
			System.out.println("-----------------------------------");
			System.out.println("Informações do servidor:");
			String hostname = InetAddress.getLocalHost().getHostName();
			System.out.println("Hostname: " + hostname);
			Enumeration<NetworkInterface> myNetInterfaces = NetworkInterface.getNetworkInterfaces();
			while (myNetInterfaces.hasMoreElements()) {
				NetworkInterface netInterface = myNetInterfaces.nextElement();
				System.out.println("Interface: " + netInterface.getName());
				Enumeration<InetAddress> interfaceAddrs = netInterface.getInetAddresses();
				while (interfaceAddrs.hasMoreElements()) {
					InetAddress addr = interfaceAddrs.nextElement();
					System.out.println("  " + addr.getHostAddress());
				}
			}
			System.out.println("-----------------------------------");
		} catch (SocketException e1) {
			throw new NetDeviceException();
		} catch (UnknownHostException e) {
			throw new NetDeviceException();
		}
	}
}