package model;

import java.io.Serializable;

import javax.persistence.Entity;

@Entity
public class Medico extends Usuario implements IModel,Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String crm;
	private String nome;
	private String especialidade;
	
	public String getCrm() {
		return crm;
	}
	public void setCrm(String crm) {
		this.crm = crm;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getEspecialidade() {
		return especialidade;
	}
	public void setEspecialidade(String especialidade) {
		this.especialidade = especialidade;
	}
	
	
}
