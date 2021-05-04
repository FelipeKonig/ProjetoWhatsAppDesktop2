package br.edu.ifsc.Proj3server.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;


@Entity
public class Usuario {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String nome, numero, pais, recado, tipoNumero, comentario;
	private boolean mensagem, ligacao, ficheiro;
	
	
	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private List<Usuario> contatos = new ArrayList<>();
	
	@ManyToMany(cascade = CascadeType.ALL)
	private List<Chamada> chamadas = new ArrayList<>();
	
	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private List<Status> status = new ArrayList<>();

	public Usuario() {
		super();
	}
	
	public Usuario(String nome, String numero, String tipoNumero, boolean mensagem, boolean ligacao, boolean ficheiro) {
		super();
		this.nome = nome;
		this.numero = numero;
		this.tipoNumero = tipoNumero;
		this.mensagem = mensagem;
		this.ligacao = ligacao;
		this.ficheiro = ficheiro;
		this.chamadas = chamadas;
	}



	public Usuario(String numero, String tipoNumero, boolean mensagem, boolean ficheiro, boolean ligacao) {
		super();
		this.numero = numero;
		this.tipoNumero = tipoNumero;
		this.ligacao = ligacao;
		this.mensagem = mensagem;
		this.ficheiro = ficheiro;
	}

	public Usuario(String nome, String numero, String pais) {
		super();
		this.nome = nome;
		this.numero = numero;
		this.pais = pais;
	}

	public Usuario(String nome, String numero, String recado, String comentario) {
		super();
		this.nome = nome;
		this.numero = numero;
		this.recado = recado;
		this.comentario = comentario;
	}

	public Usuario(String nome, String numero) {
		super();
		this.nome = nome;
		this.numero = numero;
	}

	public Usuario(String nome) {
		super();
		this.nome = nome;
	}

	public String getNumero() {
		return numero;
	}

	public void setPais(String pais) {
		this.pais = pais;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public int getId() {
		return id;
	}

	public String getPais() {
		return pais;
	}

	public String getRecado() {
		return recado;
	}

	public String getTipoNumero() {
		return tipoNumero;
	}

	public void setTipoNumero(String tipoNumero) {
		this.tipoNumero = tipoNumero;
	}

	public String getComentario() {
		return comentario;
	}

	public void setComentario(String comentario) {
		this.comentario = comentario;
	}

	public boolean isMensagem() {
		return mensagem;
	}

	public void setMensagem(boolean mensagem) {
		this.mensagem = mensagem;
	}

	public boolean isLigacao() {
		return ligacao;
	}

	public void setLigacao(boolean ligacao) {
		this.ligacao = ligacao;
	}

	public boolean isFicheiro() {
		return ficheiro;
	}

	public void setFicheiro(boolean ficheiro) {
		this.ficheiro = ficheiro;
	}

	public void setRecado(String recado) {
		this.recado = recado;
	}

	public List<Usuario> getContatos() {
		return contatos;
	}

	public void setContatos(List<Usuario> contatos) {
		this.contatos = contatos;
	}

	public List<Chamada> getChamadas() {
		return chamadas;
	}

	public List<Status> getStatus() {
		return status;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		result = prime * result + ((numero == null) ? 0 : numero.hashCode());
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
		Usuario other = (Usuario) obj;
		if (id != other.id)
			return false;
		if (numero == null) {
			if (other.numero != null)
				return false;
		} else if (!numero.equals(other.numero))
			return false;
		return true;
	}

}
