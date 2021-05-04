
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTimeout;

import java.util.List;

import javax.persistence.EntityManager;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assumptions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import static java.time.Duration.ofMillis;
import static java.time.Duration.ofSeconds;

import br.edu.ifsc.Proj3server.ChamadaDAO.chamadaDAO;
import br.edu.ifsc.Proj3server.ContatoDAO.ContatoDAO;
import br.edu.ifsc.Proj3server.database.Conn;
import br.edu.ifsc.Proj3server.model.Usuario;
import br.edu.ifsc.Proj3server.usuarioDAO.UsuarioDAO;


class ProjetoTeste {
	
	static Usuario usuarioInicial = null;

	@BeforeAll
	public static void construtor() {
			
		Usuario usuario1 = new Usuario("Felipe", "000");
		Usuario usuario2 = new Usuario("Ana", "123");
		Usuario usuario3 = new Usuario("Jonas", "1234");
		Usuario usuario4 = new Usuario("Eduardo", "12345");
		Usuario usuario5 = new Usuario("Cristiano", "123456");	

		EntityManager em = Conn.getEntityManager();
		em.getTransaction().begin();
		em.persist(usuario1);
		em.persist(usuario2);
		em.persist(usuario3);
		em.persist(usuario4);
		em.persist(usuario5);

		em.getTransaction().commit();
		
		List<Usuario> users = em.createQuery("select user from Usuario as user", Usuario.class).getResultList();

		System.out.println("Usuários:");
		for (Usuario u : users)
			System.out.println(u.getNome());

		em.close();
		
		EntityManager em2 = Conn.getEntityManager();

		Usuario usuarioTeste1 = new Usuario("usuarioTeste","numeroTeste","PaisTeste");
		
		if(new UsuarioDAO().encontrarUsuario("numeroTeste") == null)
			new UsuarioDAO().adicionarUsuario(usuarioTeste1.getNome(), usuarioTeste1.getNumero(), usuarioTeste1.getPais());
			
		em2.close();
		
		usuarioInicial = new UsuarioDAO().encontrarUsuario("numeroTeste");
		
	}

	@BeforeEach
	public void verifica_cadastro() {
		
		assertNotEquals(null, new UsuarioDAO().encontrarUsuario("numeroTeste"));
		
	}
	
	@Test
	public void verifica_addUsuario() {
		
		if( new ContatoDAO().adicionarContato("000", "cel", "true", "true", "true") != null)
			System.out.println("contato adicionado");
		
		assertThrows(NullPointerException.class, () -> { 
			new ContatoDAO().adicionarContato("0000", "cel", "true", "true", "true");
			});

	}
	
	@Test
	public void verifica_pegarTodosUsuarios() {
	    assertTimeout(ofSeconds(1) , () -> { new UsuarioDAO().pegarTodos1();
	    });
	}
	
	@Test
	public void verifica_apagarContato() {
		Usuario contato = null;
		Usuario usuario = UsuarioDAO.usuarioLogado;
		
		for (int i=0; i < usuario.getContatos().size(); i++) {
			if(usuario.getContatos().get(i).getNumero().contentEquals("000"))
				contato = usuario.getContatos().get(i);
		}
		
		assertEquals(contato, new ContatoDAO().apagarContato("000"));
	}
	
	@Test
	public void verificar_chamadas() {
		
		assertAll("Chamada",
			() -> assertNotEquals(null, new chamadaDAO().adicionarChamada("Felipe", "ligacao", "000")),
			() -> assertNotEquals(null, new chamadaDAO().alterarLembrete("Felipe", "ligacao", "lembrete alterado")),
			() -> assertNull(null, new chamadaDAO().removerChamada("Felipe", "ligacao")));
	}
	
	@Test
	public void verificar_tipos_chamada() {
		
		Assumptions.assumeTrue(new ContatoDAO().verificaBoolean("mensagem", "true"));
		Assumptions.assumeFalse(new ContatoDAO().verificaBoolean("ficheiro", "false"));
		Assumptions.assumeFalse(new ContatoDAO().verificaBoolean("ligaçao", "true"));
	}
	
	@Test
	public void verifica_alterar_nomeUsuario() {

		assertNotEquals(null, new UsuarioDAO().alterarNomeUsuario("Eu"));
	}
	
	@AfterEach
	public void recuperar_alteracoes_usuario() {
		
		Usuario usuario = UsuarioDAO.usuarioLogado;
		
		System.out.println(usuarioInicial.getNome());
		System.out.println(usuario.getNome());
		
		assertNotSame(usuarioInicial, usuario);
		
	}
	
	@AfterAll
	public static void verificar_banco_fechado() {
		Conn.closeConn();
	}

}
