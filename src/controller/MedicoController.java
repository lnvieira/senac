package controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import model.Medico;
import view.AbstractView;
import view.medico.MedicoImportarView;
import dao.MedicoDao;
import util.UtilsArquivo;

public class MedicoController extends AbstractController<Medico> {

	private static MedicoController singleton = null;
	private Map<String,AbstractView<Medico>> views = new HashMap<String, AbstractView<Medico>>();
	private MedicoDao medicoDao;
	
	/**
	 * M�todo respons�vel pela garantia de que s� existe 1 �nico objeto controller dentro do sistema
	 * Uso do padr�o singleton para garantir. 	
	 * @return {@link MedicoController}
	 */
	public static MedicoController getInstance()
	{
		if (singleton == null)
			singleton = new MedicoController();
		
		return singleton;
	}	
	/**
	 * Construtor privado que s� pode ser acessado via getInstance
	 */
	private MedicoController() {
		this.views = new HashMap<String, AbstractView<Medico>>();
		acoes.put(0, "medicoView");
		acoes.put(1, "medicoListView");
		acoes.put(2, "medicoFormView");
		acoes.put(3, "medicoAlterarView");
		acoes.put(4, "medicoExcluirView");
		acoes.put(5, "medicoImportarView");
		acoes.put(6, "mainView");
		medicoDao = new MedicoDao();
	}
	
	/**
	 * Implementa��o do m�todo registrarView
	 */
	@Override
	public MedicoController registrarView(String acao,AbstractView<Medico> view) {
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
		else if (acao.equals("medicoFormView")) {
			views.get(acao).setListModelo(null);
			views.get(acao).setModelo(null);
			renderizaView(acao);
		}
		else if (acao.equals("medicoListView")) {
			views.get(acao).setListModelo(listaMedicos());
			renderizaView(acao);
		}
		else if (acao.equals("salvar")) {
			if (salvarMedico(views.get("medicoFormView").getModelo()));
			renderizaView("medicoView");
			
		}
		else if (acao.equals("alterar")) {
			views.get("medicoFormView").setModelo(carregaMedicoPorId(views.get("medicoAlterarView").getModelo().getId()));
			renderizaView("medicoFormView");
			
		}
		else if (acao.equals("excluir")) {
			if (excluirMedico(carregaMedicoPorId(views.get("medicoExcluirView").getModelo().getId())));
			renderizaView("medicoView");
			
			
		}
		else if (acao.equals("importar")){
			String s = ((MedicoImportarView) views.get("medicoImportarView")).getCaminhoArquivo();
			Importar(s);
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
		
		this.acaoEscolhida("medicoView");
	}
	/**
	 * M�todo que vai ao Dao e lista todos os m�dicos
	 * @return {@link List}
	 */
	private List<Medico> listaMedicos(){
		return medicoDao.listaMedicos();
	}
	
	/**
	 * 
	 * @param medico
	 * @return {@link Boolean}
	 */
	public boolean salvarMedico(Medico medico) {
		if (medicoDao.save(medico)) 
			return true;
		return false;
		
	}
	/**
	 * M�todo que carrega um M�dico
	 * @param id
	 * @return {@link Medico}
	 */
	public Medico carregaMedicoPorId(Long id) {
		return medicoDao.carregaMedicoPorId(id);
		
	}
	/**
	 * M�todo que exclui um m�dico
	 * @param medico
	 * @return boolean
	 */
	public boolean excluirMedico(Medico medico) {
		if (medicoDao.excluir(medico)) 
			return true;
		return false;
		
	}
	
	private void Importar(String caminhoArquivo){
		try {
			String s = UtilsArquivo.carregar(caminhoArquivo);
			String[] linha = s.split("\n");
			for (int i=0; i< linha.length; i++) {
				String[] col = linha[i].split(";");
				Medico medico = new Medico();
				medico.setCrm(col[0]);
				medico.setEspecialidade(col[1]);
				medico.setNome(col[2]);
				salvarMedico(medico);
			}
		}
		catch (Exception e) {
			e.printStackTrace();
			redirecionaParaMenu();
		}
	}	
	
}
