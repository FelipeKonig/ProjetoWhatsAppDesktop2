package br.edu.ifsc.Proj3server.ContatoDAO;

import br.edu.ifsc.Proj3server.model.Usuario;

public interface InterfaceContatoDAO {

	public Usuario encontrarContato(String numero);
	
	public Usuario adicionarContato(String numero, String tipoNumero, String mensagem, String ficheiro, String ligacao);

	public Usuario apagarContato(String numeroContato);
	
	public Usuario alterarContato(String numero, String tipoNumero, String mensagem, String ficheiro, String ligacao);
}
