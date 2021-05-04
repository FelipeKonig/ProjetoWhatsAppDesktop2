package br.edu.ifsc.Proj3.ChamadaDAO;

import java.io.IOException;
import java.util.List;

import br.edu.ifsc.Proj3.model.Chamada;

public interface InterfaceChamadaDAO {
	
	public void adicionarChamada(String nomeContato, String tipoChamada, String numeroUsuario);
	
	public List<Chamada> pegarChamadas(String numero) throws IOException;
	
	public void removerChamada(String nomeContato, String tipoChamada);
	
	public void alterarLembrete(String nomeContato, String tipoChamada, String lembrete);
	
	public Chamada buscarChamada(String nomeContato, String tipoChamada);
}
