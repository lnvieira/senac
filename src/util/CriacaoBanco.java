package util;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

public class CriacaoBanco {

	/**
	 * Classe para a cria��o do banco. � necess�rio que os par�metros de SchemaExport estejam setados
	 * na classe CriadorSessionFactory, como no exemplo:
	 *  SchemaExport se = new SchemaExport(cfg);
		se.create(true, true);
	 */
	public static void main(String[] args) {
		SessionFactory factory = CriadorSessionFactory.getInstance().getFactory();
		Session session = new Hibernate(factory).getSession();
		
	}

}
