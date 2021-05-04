package br.edu.ifsc.Proj3.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity(name = "Status")
public class Status {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String txtStatus, nomeUsuario;

	public Status() {
		super();
	}

	public Status(String txtStatus,String nomeUsuario) {
		super();
		this.txtStatus = txtStatus;
		this.nomeUsuario = nomeUsuario;
	}
	
	public Status(String txtStatus) {
		super();
		this.txtStatus = txtStatus;
	}

	public String getNomeUsuario() {
		return nomeUsuario;
	}

	public String getTxtStatus() {
		return txtStatus;
	}

	public void setTxtStatus(String txtStatus) {
		this.txtStatus = txtStatus;
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
		Status other = (Status) obj;
		if (id != other.id)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return nomeUsuario;
	}
	
}
