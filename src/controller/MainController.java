package controller;

import java.util.HashMap;
import java.util.Map;

import view.AbstractView;

@SuppressWarnings("rawtypes")
public class MainController extends AbstractController{

	private static MainController singleton = null;
	private Map<String,AbstractView> views = new HashMap<String, AbstractView>();
	/**
	 * M�todo respons�vel pela garantia de que s� existe 1 �nico objeto controller dentro do sistema
	 * Uso do padr�o singleton para garantir. 	
	 * @return {@link MainController}
	 */	
	public static MainController getInstance()
	{
		if (singleton == null)
			singleton = new MainController();
		
		return singleton;
	}	
	/**
	 * Construtor privado que s� pode ser acessado via getInstance
	 */	
	@SuppressWarnings("unchecked")
	private MainController() {
		//Mapeamento dos numeros do menu com as a��es registradas
		this.acoes.put(0,"mainView");
		this.acoes.put(1,"medicoView");
		this.acoes.put(2,"pacienteView");
		this.acoes.put(4,"consultaView");
		this.acoes.put(5, "relatorioView");
		this.acoes.put(6,"sair");
	}
	/**
	 * Implementa��o do m�todo registrarView
	 */
	@Override
	public MainController registrarView(String acao, AbstractView view) {
		this.views.put(acao, view);
		return this;
	}
	
	/**
	 * Implementa��o do m�todo acaoEscolhida
	 */
	@Override
	public void acaoEscolhida(String acao) {
		if (acao.equalsIgnoreCase("sair")) {
			System.out.println("Se��o finalizada!");
			System.exit(0);
		}
		else {
			renderizaView(acao);
		}
	}
	/**
	 * Implementa��o do m�todo renderizaView
	 */
	@Override
	public void renderizaView(String acao) {
		try {
			views.get(acao).exibeTela();
		}
		catch(Exception e) {
			redirecionaParaMenu();
		}
	}
	/**
	 * M�todo que redireciona para o menu principal
	 */
	private void redirecionaParaMenu() {
		
		System.out.println("A��o n�o permitida !!! Aguarde que estamos redirecionando voc� para o menu principal");
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
		
		this.acaoEscolhida("mainView");
	}

	
}
