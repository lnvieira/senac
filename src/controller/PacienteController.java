package controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import model.Paciente;
import view.AbstractView;
import dao.PacienteDao;

public class PacienteController extends AbstractController<Paciente> {

	private static PacienteController singleton = null;
	private Map<String,AbstractView<Paciente>> views = new HashMap<String, AbstractView<Paciente>>();
	private PacienteDao pacienteDao;
		
	public static PacienteController getInstance()
	{
		if (singleton == null)
			singleton = new PacienteController();
		
		return singleton;
	}	
		
	private PacienteController() {
		this.views = new HashMap<String, AbstractView<Paciente>>();
		acoes.put(0, "pacienteView");
		acoes.put(1, "pacienteListView");
		acoes.put(2, "pacienteFormView");
		acoes.put(3, "pacienteAlterarView");
		acoes.put(4, "pacienteExcluirView");
		acoes.put(5, "mainView");
		pacienteDao = new PacienteDao();
	}
	
	@Override
	public PacienteController registrarView(String acao,AbstractView<Paciente> view) {
		this.views.put(acao, view);
		return this;
	}
	
	@Override
	public void acaoEscolhida(String acao) {
		if (acao == null) {
			redirecionaParaMenu();
		}
		else if (acao.equals("pacienteFormView")) {
			views.get(acao).setListModelo(null);
			views.get(acao).setModelo(null);
			renderizaView(acao);
		}
		else if (acao.equals("pacienteListView")) {
			views.get(acao).setListModelo(listaPacientes());
			renderizaView(acao);
		}
		else if (acao.equals("salvar")) {
			if (salvarPaciente(views.get("pacienteFormView").getModelo()));
			renderizaView("pacienteView");
			
		}
		else if (acao.equals("alterar")) {
			views.get("pacienteFormView").setModelo(carregaPacientePorId(views.get("pacienteAlterarView").getModelo().getId()));
			renderizaView("pacienteFormView");
			
		}
		else if (acao.equals("excluir")) {
			if (excluirPaciente(carregaPacientePorId(views.get("pacienteExcluirView").getModelo().getId())));
			renderizaView("pacienteView");
			
			
		}
		else if (acao.equals("mainView")) {
			MainController.getInstance().acaoEscolhida(acao);
		}
		else {
			//Redireciona para a view baseada na a��o
			renderizaView(acao);
		}
	}
	
	public void renderizaView(String acao) {
		try {
			views.get(acao).exibeTela();
		}
		catch(Exception e) {
			redirecionaParaMenu();
		}
	}

	private void redirecionaParaMenu() {
		
		System.out.println("A��o n�o permitida !!! Aguarde que estamos redirecionando voc� para o menu principal");
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
		
		this.acaoEscolhida("pacienteView");
	}
	
	private List<Paciente> listaPacientes(){
		return pacienteDao.listaPacientes();
	}
	
	public boolean salvarPaciente(Paciente paciente) {
		if (pacienteDao.save(paciente)) 
			return true;
		return false;
		
	}

	public Paciente carregaPacientePorId(Long id) {
		return pacienteDao.carregaPacientePorId(id);
		
	}
	
	public boolean excluirPaciente(Paciente paciente) {
		if (pacienteDao.excluir(paciente)) 
			return true;
		return false;
		
	}
	
}
