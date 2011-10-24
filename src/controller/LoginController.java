package controller;

import java.util.HashMap;
import java.util.Map;

import model.Usuario;
import view.AbstractView;
import dao.LoginDao;

public class LoginController extends AbstractController<Usuario>{
	
	private Map<String,AbstractView<Usuario>> views = new HashMap<String, AbstractView<Usuario>>();
	private LoginDao loginDao;
	
	// Singleton
	private static LoginController singleton = null;
	/**
	 * M�todo respons�vel pela garantia de que s� existe 1 �nico objeto controller dentro do sistema
	 * Uso do padr�o singleton para garantir. 	
	 * @return {@link LoginController}
	 */
	public static LoginController getInstance()
	{
		if (singleton == null)
			singleton = new LoginController();
		
		return singleton;
	}	
	/**
	 * Construtor privado que s� pode ser acessado via getInstance
	 */	
	private LoginController(){		
		//Mapeamento dos numeros do menu com as a��es registradas
		this.acoes.put(0,"loginView");
		loginDao = new LoginDao();
	}
	/**
	 * Implementa��o do m�todo registrarView
	 */
	@Override
	public AbstractController<Usuario> registrarView(String acao,AbstractView<Usuario> view) {
		this.views.put(acao, view);
		return this;
	}
	/**
	 * Implementa��o do m�todo acaoEscolhida
	 */
	@Override
	public void acaoEscolhida(String acao) {
		if (acao.equals("login")) {
			if (login(views.get("loginView").getModelo())) {
				MainController.getInstance().acaoEscolhida("mainView");
			}
			else {
				System.out.println("Usu�rio Inv�lido");
				renderizaView("loginView");
			}
		}
		else {
			renderizaView("loginView");
		}
	}
	/**
	 * Implementa��o do m�todo renderizaView
	 */
	@Override
	public void renderizaView(String acao) {
		views.get(acao).exibeTela();
	}
	/**
	 * 
	 * @param u
	 * @return {@link Boolean}
	 */
	public boolean login(Usuario u) {
		if (loginDao.logar(u)!= null)
			return true;
		return false;
	}

	
	
}
