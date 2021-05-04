package br.edu.ifsc.Proj3.StatusDAO;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.List;

import br.edu.ifsc.Proj3.model.Status;

public interface InterfaceStatusDAO {
	
	public List<Status> pegarStatus() throws UnknownHostException, IOException;
	
	public void adicionarStatus(String mensagem, String nomeUsuario);
	
	public void removerStatus(String mensagem);
	
	public void alterarStatus(String mensagem);
	
	public List<Status> pegarStatusUsuario(String numeroUsuario) throws UnknownHostException, IOException;

}
