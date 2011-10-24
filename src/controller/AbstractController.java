package controller;

import java.util.HashMap;
import java.util.Map;

import model.IModel;
import view.AbstractView;

/**
 * 
 * @author Lucas
 *
 * @param <T>
 * 
 * Classe respons�vel pela abstra��o dos controllers. Uma classe para ser um controller deve extender dessa classe
 * Usamos o tipo Paremtrizado T para defini��o do tipo do controller que se refere. No caso todos devers�o
 * ser de algum tipo que implemente IModel.
 */
public abstract class AbstractController<T extends IModel> {
	/**
	 * Atributo que cont�m todas as a��es poss�veis de serem executadas no controller
	 */
	protected Map<Integer,String> acoes = new HashMap<Integer, String>();
	
	/**
	 * M�todo que deve ser sobreescrito nos controllers espec�ficos
	 * Deve ser passado uma string contendo a acao e esta acao deve estar mapeada
	 * no mapa do atributo acoes
	 * @param acao
	 */
	public abstract void acaoEscolhida(String acao);
	
	/**
	 * M�todo que deve ser sobreescrito nos controllers espec�ficos.
	 * Nele conter� a l�gica de renderiza��o da view.
	 * @param acao
	 */
	public abstract void renderizaView(String acao);
	
	/**
	 * M�todo que deve ser sobreescrito nos controllers espec�ficos.
	 * Este m�todo � respons�vel por registrar todas as views a que os controllers
	 * ter�o acesso. Ele recebe a a��o e uma inst�ncia da classe de view.
	 * @param acao
	 * @param view
	 * @return
	 */
	public abstract AbstractController<T>  registrarView(String acao, AbstractView<T> view);
	
	/**
	 * M�todo que retorna a a��o baseada no n�mero escolhido no menu 
	 * @param opcao
	 * @return
	 */
	public String decideAcao(Integer opcao) {
		return acoes.get(opcao);
	}
	
}
