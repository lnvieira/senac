package controller;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import model.IModel;
import model.Relatorios;
import net.sf.jasperreports.engine.JRException;
import report.Relatorio;
import view.AbstractView;
import dao.ConsultaDao;
import dao.PacienteDao;

public class RelatorioController extends AbstractController<Relatorios> {

	private static RelatorioController singleton = null;
	private Map<String,AbstractView<Relatorios>> views = new HashMap<String, AbstractView<Relatorios>>();
	@SuppressWarnings("rawtypes")
	private Map<String,Relatorio> relatorios = new HashMap<String, Relatorio>();
	private PacienteDao pacienteDao; 
	private ConsultaDao consultaDao;
	/**
	 * M�todo respons�vel pela garantia de que s� existe 1 �nico objeto controller dentro do sistema
	 * Uso do padr�o singleton para garantir. 	
	 * @return {@link RelatorioController}
	 */		
	public static RelatorioController getInstance()
	{
		if (singleton == null)
			singleton = new RelatorioController();
		
		return singleton;
	}	
	/**
	 * Construtor privado que s� pode ser acessado via getInstance
	 */	
	private RelatorioController() {
		this.views = new HashMap<String, AbstractView<Relatorios>>();
		acoes.put(0, "relatorioView");
		acoes.put(1, "relatorioPaciente");
		acoes.put(2, "relatorioHistoricoPaciente");
		acoes.put(3, "consultaAgendadaView");
		acoes.put(4, "receituarioView");
		acoes.put(5, "atestadoView");
		acoes.put(6, "mainView");
		
		this.pacienteDao = new PacienteDao();
		this.consultaDao = new ConsultaDao();
	}
	/**
	 * Implementa��o do m�todo registrarView
	 */
	@Override
	public RelatorioController registrarView(String acao,AbstractView<Relatorios> view) {
		this.views.put(acao, view);
		return this;
	}
	
	@SuppressWarnings("rawtypes")
	public RelatorioController registrarRelatorio(String acao,Relatorio relatorio) {
		this.relatorios.put(acao, relatorio);
		return this;
	}
	/**
	 * Implementa��o do m�todo acaoEscolhida
	 */
	@SuppressWarnings({ "unchecked", "null" })
	@Override
	public void acaoEscolhida(String acao) {
		
		if (acao == null) {
			redirecionaParaMenu();
		}
		
		else if (acao.equals("consultaAgendadaView")) {
			renderizaView("consultaAgendadaView");
			
		}
		else if (acao.equals("receituarioView")) {
			renderizaView("receituarioView");
			
		}
		else if (acao.equals("atestadoView")) {
			renderizaView("atestadoView");
			
		}
		else if (acao.equals("relatorioPaciente")) {

			try {
				relatorios.get(acao).gerar("ListarPacientes.jrxml","Lista de Pacientes", "Lista_Pacientes.pdf", null, pacienteDao.listaPacientes());
			} catch (JRException e) {
				redirecionaParaMenu();
			} catch (SQLException e) {
				redirecionaParaMenu();
			} catch (ClassNotFoundException e) {
				redirecionaParaMenu();
			}finally{
				this.acaoEscolhida("relatorioView");
			}
		}
		else if (acao.equals("relatorioHistoricoPaciente")) {
			
			try {
				relatorios.get(acao).gerar("ListarHistoricoPacientes.jrxml","Lista Historico de Pacientes", "Lista_Historico_Pacientes.pdf", null, consultaDao.listaConsultas());
			} catch (JRException e) {
				redirecionaParaMenu();
			} catch (SQLException e) {
				redirecionaParaMenu();
			} catch (ClassNotFoundException e) {
				redirecionaParaMenu();
			}finally{
				this.acaoEscolhida("relatorioView");
			}
		}
		else if (acao.equals("relatorioConsultaAgendada")){
			try {
				relatorios.get(acao).gerar("ConsultasAgendadas.jrxml","Consultas Agendadas", "ConsultasAgendadas.pdf", null, consultaDao.listaConsultas(views.get("consultaAgendadaView").getModelo().getDataIni(), views.get("consultaAgendadaView").getModelo().getDataFim()));
			} catch (Exception e) {
				redirecionaParaMenu();
			}
			finally{
				this.acaoEscolhida("relatorioView");
			}
			
		}
		else if (acao.equals("relatorioReceituario")){
			try {
				List<IModel> listConsulta = new ArrayList<IModel>();
				listConsulta.add(consultaDao.carregaConsultaPorId(views.get("receituarioView").getModelo().getIdConsulta()));
				relatorios.get(acao).gerar("Receituario.jrxml","Receituario", "Receituario.pdf", null, listConsulta);
			} catch (Exception e) {
				redirecionaParaMenu();
			}
			finally{
				this.acaoEscolhida("relatorioView");
			}
			
		}
		else if (acao.equals("relatorioAtestado")){
			try {
				List<IModel> listConsulta = new ArrayList<IModel>();
				listConsulta.add(consultaDao.carregaConsultaPorId(views.get("atestadoView").getModelo().getIdConsulta()));
				
				relatorios.get(acao).gerar("Atestado.jrxml","Atestado", "Atestado.pdf", views.get("atestadoView").getModelo().getAtestado(), listConsulta);
			} catch (Exception e) {
				redirecionaParaMenu();
			}finally{
				this.acaoEscolhida("relatorioView");
			}			
			
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
		
		this.acaoEscolhida("relatorioView");
	}
		
}
