package univ_scheduler.main;

import univ_scheduler.view.LoginView;

import javax.swing.SwingUtilities;
/**
 * Classe principale de l'application UNIV-SCHEDULER.
  * Lance l'interface de connexion.
 */

public class Main {
	
	/**
     * Lance l'interface de connexion de l'application
     *  @param args arguments de la ligne de commande
     */

	public static void main(String[] args) {
		 SwingUtilities.invokeLater(new Runnable() {
	            public void run() {
	            	
	                new LoginView().setVisible(true);
	            }
	        });

	}

}
