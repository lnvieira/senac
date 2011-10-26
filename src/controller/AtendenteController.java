package controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import model.Atendente;
import model.Medico;
import view.AbstractView;
import dao.AtendenteDao;

public class AtendenteController extends AbstractController<Atendente> {

	private static AtendenteController singleton = null;
	private Map<String,AbstractView<Atendente>> views = new HashMap<String, AbstractView<Atendente>>();
	private AtendenteDao atendenteDao;
	
	/**
	 * M�todo respons�vel pela garantia de que s� existe 1 �nico objeto controller dentro do sistema
	 * Uso do padr�o singleton para garantir. 	
	 * @return {@link AtendenteController}
	 */
	public static AtendenteController getInstance()
	{
		if (singleton == null)
			singleton = new AtendenteController();
		
		return singleton;
	}	
	/**
	 * Construtor privado que s� pode ser acessado via getInstance
	 */
	private AtendenteController() {
		this.views = new HashMap<String, AbstractView<Atendente>>();
		acoes.put(0, "atendenteView");
		acoes.put(1, "atendenteListView");
		acoes.put(2, "atendenteFormView");
		acoes.put(3, "atendenteAlterarView");
		acoes.put(4, "atendenteExcluirView");
		acoes.put(5, "mainView");
		atendenteDao = new AtendenteDao();
	}
	
	/**
	 * Implementa��o do m�todo registrarView
	 */
	@Override
	public AtendenteController registrarView(String acao,AbstractView<Atendente> view) {
		this.views.put(acao, view);
		return this;
	}
	
	/**
	 * Implementa��o do m�todo acaoEscolhida
	 */
	@Override
	public void acaoEscolhida(String acao) {
		if (acao == null) {
			redirecionaParaMenu();
		}
		else if (acao.equals("atendenteFormView")) {
			views.get(acao).setListModelo(null);
			views.get(acao).setModelo(null);
			renderizaView(acao);
		}
		else if (acao.equals("atendenteListView")) {
			views.get(acao).setListModelo(listaAtendentes());
			renderizaView(acao);
		}
		else if (acao.equals("salvar")) {
			if (salvarAtendente(views.get("atendenteFormView").getModelo()));
			renderizaView("atendenteView");
			
		}
		else if (acao.equals("alterar")) {
			views.get("atendenteFormView").setModelo(carregaAtendentePorId(views.get("atendenteAlterarView").getModelo().getId()));
			renderizaView("atendenteFormView");
			
		}
		else if (acao.equals("excluir")) {
			if (excluirAtendente(carregaAtendentePorId(views.get("atendenteExcluirView").getModelo().getId())));
			renderizaView("atendenteView");
			
			
		}
		else if (acao.equals("mainView")) {
			MainController.getInstance().acaoEscolhida(acao);
		}
		else {
			//Redireciona para a view baseada na a��o
			renderizaView(acao);
		}
	}
	
	/**
	 * Implementa��o do m�todo renderizaView
	 */
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
		
		this.acaoEscolhida("atendenteView");
	}
	/**
	 * M�todo que vai ao Dao e lista todos os m�dicos
	 * @return {@link List}
	 */
	private List<Atendente> listaAtendentes(){
		return atendenteDao.listaAtendentes();
	}
	
	/**
	 * 
	 * @param medico
	 * @return {@link Boolean}
	 */
	public boolean salvarAtendente(Atendente atendente) {
		if (atendenteDao.save(atendente)) 
			return true;
		return false;
		
	}
	/**
	 * M�todo que carrega um M�dico
	 * @param id
	 * @return {@link Medico}
	 */
	public Atendente carregaAtendentePorId(Long id) {
		return atendenteDao.carregaAtendentePorId(id);
		
	}
	/**
	 * M�todo que exclui um m�dico
	 * @param medico
	 * @return boolean
	 */
	public boolean excluirAtendente(Atendente atendente) {
		if (atendenteDao.excluir(atendente)) 
			return true;
		return false;
		
	}
	
}
