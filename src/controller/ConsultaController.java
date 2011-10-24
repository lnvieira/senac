package controller;

import java.util.HashMap;
import java.util.Map;

import model.Consulta;
import model.Receita;
import model.Status;
import view.AbstractView;
import dao.ConsultaDao;

public class ConsultaController extends AbstractController<Consulta> {

	private static ConsultaController singleton = null;
	private Map<String,AbstractView<Consulta>> views = new HashMap<String, AbstractView<Consulta>>();
	private ConsultaDao consultaDao;
	/**
	 * M�todo respons�vel pela garantia de que s� existe 1 �nico objeto controller dentro do sistema
	 * Uso do padr�o singleton para garantir. 	
	 * @return
	 */	
	public static ConsultaController getInstance()
	{
		if (singleton == null)
			singleton = new ConsultaController();
		
		return singleton;
	}	
	/**
	 * Construtor privado que s� pode ser acessado via getInstance
	 */	
	private ConsultaController() {
		this.views = new HashMap<String, AbstractView<Consulta>>();
		acoes.put(0, "consultaView");
		acoes.put(1, "agendarConsultaView");
		acoes.put(2, "cancelarConsultaView");
		acoes.put(3, "atualizarConsultaView");
		acoes.put(4, "mainView");
		consultaDao  = new ConsultaDao();
	}
	/**
	 * Implementa��o do m�todo registrarView
	 */
	@Override
	public ConsultaController registrarView(String acao,AbstractView<Consulta> view) {
		this.views.put(acao, view);
		return this;
	}
	/**
	 * Implementa��o do m�todo acaoEscolhida
	 */
	@Override
	public void acaoEscolhida(String acao) {
		if (acao == null) {
			redirecionaParaMenu("A��o n�o permitida !!! Aguarde que estamos redirecionando voc� para o menu principal");
		}
		else if (acao.equals("agendarConsultaView")) {
			views.get(acao).setListModelo(null);
			views.get(acao).setModelo(null);
			renderizaView(acao);
		}
		else if (acao.equals("cancelarConsultaView")) {
			views.get(acao).setListModelo(null);
			views.get(acao).setModelo(null);
			renderizaView(acao);
		}
		else if (acao.equals("atualizarConsultaView")) {
			views.get(acao).setListModelo(null);
			views.get(acao).setModelo(null);
			renderizaView(acao);
		}
		else if (acao.equals("salvar")) {
			if (salvarConsulta(views.get("agendarConsultaView").getModelo()))
				renderizaView("consultaView");
			else
				redirecionaParaMenu("Houve um problema para salvar a consulta");
		}
		else if (acao.equals("cancelar")) {
			
			Consulta c = preparaModeloParaCancelamento();
			if (salvarConsulta(c))
				renderizaView("consultaView");
			else
				redirecionaParaMenu("Houve um problema para salvar a consulta");
		}
		else if (acao.equals("atualizar")) {
			
			Consulta c = preparaModeloParaAtualizacao();
			if (atualizarConsulta(c))
				renderizaView("consultaView");
			else
				redirecionaParaMenu("Houve um problema para salvar a consulta");
		}
		
		else if (acao.equals("mainView")) {
			MainController.getInstance().acaoEscolhida(acao);
		}
	}

	/**
	 * M�todo que atualiza uma consulta
	 * @param c
	 * @return {@link Boolean}
	 */
	private boolean atualizarConsulta(Consulta c) {
		if (consultaDao.update(c)) 
			return true;
		return false;
	}

	/**
	 * M�todo que prepara o objeto consulta para a persist�ncia
	 * @return {@link Consulta}
	 */
	private Consulta preparaModeloParaAtualizacao() {
		Receita receita = views.get("atualizarConsultaView").getModelo().getReceita();
		Integer duracao = views.get("atualizarConsultaView").getModelo().getDuracao();
		Status statusAlterado = views.get("atualizarConsultaView").getModelo().getStatus();
		
		Consulta c = refresh(views.get("atualizarConsultaView").getModelo());
		c.setDuracao(duracao);
		c.setReceita(receita);
		c.setStatus(statusAlterado);
		return c;
	}

	/**
	 * M�todo que prepara o objeto consulta para cancelamento
	 * @return {@link Consulta}
	 */
	private Consulta preparaModeloParaCancelamento() {
		Status statusAlterado = views.get("cancelarConsultaView").getModelo().getStatus();
		Consulta c = refresh(views.get("cancelarConsultaView").getModelo());
		c.setStatus(statusAlterado);
		return c;
	}
	
	/**
	 * m�todo que salva uma consulta
	 * @param consulta
	 * @return
	 */
	private boolean salvarConsulta(Consulta consulta) {
		if (consultaDao.save(consulta)) 
			return true;
		return false;
	}
	/**
	 * Implementa��o do m�todo renderizaView
	 */
	public void renderizaView(String acao) {
		try {
			views.get(acao).exibeTela();
		}
		catch(Exception e) {
			redirecionaParaMenu("A��o n�o permitida !!! Aguarde que estamos redirecionando voc� para o menu principal");
		}
	}
	/**
	 * M�todo que redireciona para o menu principal 
	 * 	@param message
 	 */
	private void redirecionaParaMenu(String message) {
		
		System.out.println(message);
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
		
		this.acaoEscolhida("consultaView");
	}
	/**
	 * M�todo que carrega uma consulta dado o seu Id
	 * @param id
	 * @return {@link Consulta}
	 */
	public Consulta carregaConsultaPorId(Long id) {
		return consultaDao.carregaConsultaPorId(id);
		
	}
	/**
	 * M�todo que atualiza o objeto consulta
	 * @param c
	 * @return {@link Consulta}
	 */
	public Consulta refresh(Consulta c) {
		return consultaDao.refresh(c);
		
	}
	
}
