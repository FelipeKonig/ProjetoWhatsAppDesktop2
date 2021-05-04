package br.edu.ifsc.Proj3server.ChamadaDAO;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.List;

import br.edu.ifsc.Proj3server.model.Chamada;

public interface InterfaceChamadaDAO {

	public Chamada adicionarChamada(String numeroContato, String tipoChamada, String numeroUsuario);

	public List<Chamada> pegarChamadas(String numero) throws UnknownHostException, IOException;
	
	public Chamada removerChamada(String numeroContato, String tipoChamada);
	
	public Chamada buscarChamada(String numeroContato, String tipoChamada);
	
	public Chamada alterarLembrete(String numeroContato, String tipoChamada, String lembrete);

}
