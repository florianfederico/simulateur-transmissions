package sources.src;

import java.util.Random;

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
 * Classe d'un composant source d'informations dont les éléments sont aléatoires
 * et de type Boolean
 * 
 */
public class SourceAleatoire extends Source<Boolean> {

	/**
	 * Constructeur de la classe
	 * 
	 * @param longueurMessage
	 *            Définit la longueur du message aléatoire à générer
	 */
	public SourceAleatoire(final int longueurMessage) {
		super();
		this.informationGeneree = new Information<Boolean>();

		Random generator = new Random();
		for (int i = 0; i < longueurMessage; i++) {

			if (generator.nextBoolean())
				this.informationGeneree.add(true);
			else
				this.informationGeneree.add(false);
		}
	}

	/**
	 * Constructeur de la classe avec une graine pour influer sur le message
	 * généré
	 * 
	 * @param longueurMessage
	 *            Définit la longueur du message aléatoire à générer
	 * @param seed
	 *            Définit le message aléatoire généré
	 */
	public SourceAleatoire(final int longueurMessage, final int seed) {
		super();
		this.informationGeneree = new Information<Boolean>();

		Random generator = new Random(seed);
		for (int i = 0; i < longueurMessage; i++) {

			if (generator.nextBoolean())
				this.informationGeneree.add(true);
			else
				this.informationGeneree.add(false);
		}
	}

}
