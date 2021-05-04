package br.edu.ifsc.Proj3.usuarioDAO;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.List;

public interface InterfaceUsuarioDAO<Usuario> {

	public Usuario encontrarUsuario(String numero);
	
	public boolean adicionarUsuario(Usuario usuario);
	
	public List<Usuario> pegarTodos() throws UnknownHostException, IOException;
	
	public List<Usuario> pegarContatos() throws UnknownHostException, IOException;
	
	public Usuario alterarUsuario(String tipoAleteracao,String alteracao);
	
	public void alterarRecado(String recado);
	
	public Usuario apagarUsuario(String numero);
}
