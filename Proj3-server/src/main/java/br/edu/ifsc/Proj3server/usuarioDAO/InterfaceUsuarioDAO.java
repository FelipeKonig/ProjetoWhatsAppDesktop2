package br.edu.ifsc.Proj3server.usuarioDAO;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.List;

public interface InterfaceUsuarioDAO<Usuario> {

	public Usuario encontrarUsuario(String numero);
	
	public Usuario adicionarUsuario(String nome, String numero, String pais);
	
	public List<Usuario> pegarTodos() throws UnknownHostException, IOException;
	
	public List<Usuario> pegarContatos() throws UnknownHostException, IOException;
	
	public Usuario alterarUsuario(String tipoAleteracao,String alteracao);
	
	public String alterarRecado(String recado);
	
	public Usuario apagarUsuario(String numero);

	List<br.edu.ifsc.Proj3server.model.Usuario> pegarTodos1();
}
