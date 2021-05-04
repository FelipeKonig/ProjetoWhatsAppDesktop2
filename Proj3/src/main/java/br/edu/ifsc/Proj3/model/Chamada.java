package br.edu.ifsc.Proj3.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Chamada {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String tipoLigacao;
	private String contato;
	private String lembrete;

	public Chamada() {
		super();
	}

	public Chamada(String tipoLigacao, String contato) {
		super();
		this.contato = contato;
		this.tipoLigacao = tipoLigacao;
	}

	public Chamada(int id, String tipoLigacao, String contato) {
		super();
		this.id = id;
		this.tipoLigacao = tipoLigacao;
		this.contato = contato;
	}

	public Chamada(String tipoLigacao, String contato, String lembrete) {
		super();
		this.tipoLigacao = tipoLigacao;
		this.contato = contato;
		this.lembrete = lembrete;
	}

	public String getContato() {
		return contato;
	}

	public String getTipoLigacao() {
		return tipoLigacao;
	}

	public String getLembrete() {
		return lembrete;
	}

	public void setLembrete(String lembrete) {
		this.lembrete = lembrete;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Chamada other = (Chamada) obj;
		if (id != other.id)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Número: " + contato + " Tipo da chamada: " + tipoLigacao;
	}
}
