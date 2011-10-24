package view.medico;

import java.util.Scanner;

import model.Medico;
import view.AbstractView;
import controller.MedicoController;

public class MedicoFormView extends AbstractView<Medico> {
	
	private MedicoController medicoController;
	private String mensagemNome;
	private String mensagemCrm;
	private String mensagemEspecialidade;
	private String mensagemLogin;
	private String mensagemSenha;
	private Scanner sc;
	
	public MedicoFormView () {
		sc = new Scanner(System.in);
		this.medicoController = MedicoController.getInstance();
	}
	/**
	 * Implementa��o do m�todo desenhaTela
	 */
	@Override
	protected void desenhaTela() {
		
		defineLabels();
		
		Medico m = defineModelo();
		
		interfaceUsuario(m);
		
		medicoController.acaoEscolhida("salvar");
		
	}
	/**
	 * M�todo que escreve na tela, recupera dados digitados e popula modelo (neste caso de consulta)
	 * @param c
	 */
	private void interfaceUsuario(Medico m) {
		System.out.println(mensagemNome);
		String nome = sc.nextLine();
		sc.nextLine();
		
		System.out.println(mensagemCrm);
		String crm = sc.next();
		
		System.out.println(mensagemEspecialidade);
		String especialidade = sc.nextLine();
		
		sc.nextLine();
		
		System.out.println(mensagemLogin);
		String login = sc.next();
		System.out.println(mensagemSenha);
		String senha = sc.next();
			
		m.setCrm(crm);
		m.setEspecialidade(especialidade);
		m.setLogin(login);
		m.setNome(nome);
		m.setSenha(senha);
		
		this.setModelo(m);
	}
	/**
	 * M�todo usado apenas para definir as labels da tela
	 */
	private void defineLabels() {
		mensagemNome = "Digite o nome ";
		mensagemCrm = "Digite o crm: ";
		mensagemEspecialidade = "Digite o especialidade: ";
		mensagemLogin = "Digite o login: ";
		mensagemSenha = "Digite o senha: ";
		
		if (this.getModelo() != null) { 
			mensagemNome+="("+this.getModelo().getNome()+")"; 
			mensagemCrm+="("+this.getModelo().getCrm()+")";
			mensagemEspecialidade+="("+this.getModelo().getEspecialidade()+")";
			mensagemLogin+="("+this.getModelo().getLogin()+")";
			mensagemSenha+="("+this.getModelo().getSenha()+")";
		}
	}
	/**
	 * M�todo que define se vai criar um novo modelo ou usar o que j� foi setado no controller
	 * @return {@link Medico}
	 */
	private Medico defineModelo() {
		Medico m = null;
		if (this.getModelo() == null) 
			m = new Medico();
		else {
			m = this.getModelo();
		}
		return m;
	}
	
	

}
