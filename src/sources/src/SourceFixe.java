/**
 * 
 */
package sources.src;

import java.util.LinkedList;

import information.Information;

/**
 * @author FEDERICO Florian - florian.federico@telecom-bretagne.eu
 * @author SANCHEZ  Bastien - bastien.sanchez@telecom-bretagne.eu
 * @author MOULY    Thibaud - thibaud.mouly@telecom-bretagne.eu
 * @author PONCELET Nathan  - nathan.poncelet@telecom-bretagne.eu
 *
 * TELECOM Bretagne - FIP 2018 - 2016-2017
 * Projet SIT213 - Atelier systèmes de transmission
 * 
 * Classe d'un composant source d'informations dont les éléments sont fixés et
 * de type Boolean
 * 
 */
public class SourceFixe extends Source<Boolean> {

	/**
	 * Constructeur de la classe Cree une information a partir des caracteres en
	 * parametre. Chaque 1 insere un Vrai, sinon on insere un Faux.
	 * 
	 * @param message
	 *            Le contenu du message correpondant à l'argument m de l'option
	 *            -mess
	 */
	public SourceFixe(String message) {
		super();
		this.informationGeneree = new Information<Boolean>();

		LinkedList<Boolean> list = SourceFixe.fromStrToBooleanList(message);
		for (Boolean b : list)
			this.informationGeneree.add(b);
	}

	/*
	 * On teste chaque caractère pour ajouter un Vrai ou un Faux dans
	 * l'information générée.
	 */
	public static LinkedList<Boolean> fromStrToBooleanList(String message) {
		LinkedList<Boolean> list = new LinkedList<Boolean>();

		for (int i = 0; i < message.length(); i++)
			list.add(message.charAt(i) == '1');

		return list;
	}

}
