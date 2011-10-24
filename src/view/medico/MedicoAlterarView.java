package view.medico;

import java.util.Scanner;

import model.Medico;
import view.AbstractView;
import controller.MedicoController;

public class MedicoAlterarView extends AbstractView<Medico> {

	private MedicoController medicoController;
	private Scanner sc;
	
	public MedicoAlterarView () {
		sc = new Scanner(System.in);
		this.medicoController = MedicoController.getInstance();
	}
	/**
	 * Implementa��o do m�todo desenhaTela
	 */
	@Override
	protected void desenhaTela() {
		System.out.println("Digite o id do m�dico que voc� deseja alterar: ");
		Long id = sc.nextLong();
		Medico m = new Medico();
		m.setId(id);
		this.setModelo(m);
		medicoController.acaoEscolhida("alterar");
	}

}
