package view;

import java.util.List;

import model.IModel;
/**
 * 
 * @author Lucas
 *
 * @param <T>
 * 
 * Classe respons�vel pela abstra��o das views. Uma classe para ser uma view deve extender dessa classe
 * Usamos o tipo Paremtrizado T para defini��o do tipo de view que se refere. No caso todas as views devers�o
 * ser de algum tipo que implemente IModel.
 */
public abstract class AbstractView<T extends IModel>  {
	
	/**
	 * Lista do Modelo parametrizado como T. Usado em telas que precisamos exibir mais de 1 elemento
	 */
	private List<T> listModelo;
	
	
	/**
	 * Modelo parametrizado como T. Usado em telas que precisamos exibir mais de 1 elemento
	 */
	private T modelo;
	
	/**
	 * M�todo que monta o cabe�alho e chama o desenha tela polim�rficamente de cada uma das classes filhas
	 */
	public void exibeTela() {
		desenhaCabecalho();
		desenhaTela();
	}
	
	/**
	 * M�todo que deve ser sobreescrito (por ser abstrato) em cada view espec�fica 
	 */
	protected abstract void desenhaTela();
	
	/**
	 * M�todo para pular algumas linhas e dar a impress�o de que no limpamos a tela no Console
	 */
	private void limpaTela() {
		for(int i=0; i<25; i++) {
			System.out.println();
		}
	}
	
	/**
	 * M�todo que desenha o cabe�alho
	 */
	private void desenhaCabecalho() {
		limpaTela();
		System.out.println("-----------------------------------------------");
		System.out.println("SISTEMA DE CONSULT�RIO M�DICO");
		System.out.println("-----------------------------------------------");
	}
	/**
	 * Metodo de acesso a lista de modelo
	 * @return {@link List}
	 */
	public List<T> getListModelo() {
		return listModelo;
	}

	/**
	 * Metodo de acesso a lista de modelo
	 * @param listModelo
	 */
	public void setListModelo(List<T> listModelo) {
		this.listModelo = listModelo;
	}
	/**
	 * Metodo de acesso ao modelo
	 * @return {@link IModel}
	 */
	public T getModelo() {
		return modelo;
	}
	
	/**
	 * Metodo de acesso ao modelo
	 * @param modelo
	 */
	public void setModelo(T modelo) {
		this.modelo = modelo;
	}
	
}
