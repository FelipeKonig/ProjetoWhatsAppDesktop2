package br.edu.ifsc.Proj3.ContatoDAO;

import java.io.IOException;
import java.net.UnknownHostException;

import br.edu.ifsc.Proj3.model.Usuario;

public interface InterfaceContatoDAO {
	
	public Usuario encontrarContato(String numero);

	public int adicionarContato(String numero, String tipoNumero, String mensagem, String ficheiro, String ligacao) throws UnknownHostException, IOException;

	public int apagarContato(String numeroContato);
	
	public int alterarContato(String numeroContato, String tipoNumero, String mensagem, String ficheiro, String ligacao);
}
