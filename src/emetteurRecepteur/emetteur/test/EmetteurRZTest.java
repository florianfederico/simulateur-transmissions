/**
 * 
 */
package emetteurRecepteur.emetteur.test;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import emetteurRecepteur.emetteur.impl.AEmetteur;
import emetteurRecepteur.emetteur.impl.EmetteurRZ;
import information.Information;

/**
 * @author FEDERICO Florian - florian.federico@telecom-bretagne.eu
 * @author SANCHEZ Bastien - bastien.sanchez@telecom-bretagne.eu
 * @author MOULY Thibaud - thibaud.mouly@telecom-bretagne.eu
 * @author PONCELET Nathan - nathan.poncelet@telecom-bretagne.eu
 *
 *         TELECOM Bretagne - FIP 2018 - 2016-2017 Projet SIT213 - Atelier
 *         systèmes de transmission
 * 
 */
public class EmetteurRZTest {

	/**
	 * Test method for
	 * {@link emetteurRecepteur.emetteur.impl.EmetteurRZ#EmetteurRZ()}.
	 */
	@Test
	public void testEmetteurRZ() {
		AEmetteur emetteur = new EmetteurRZ();

		/**
		 * Les signaux RZ exigent un nombre d'echantillons divisible par 3 et
		 * impair.
		 * 
		 * @see emetteurRecepteur.form.src.RZ#verifierNbEchantillons(int)
		 */
		assertTrue(emetteur.getNe() == 33); // La valeur 30 par defaut dans
											// AEmetteur n'est pas acceptée
		assertTrue(emetteur.getaMin() == 0f);
		assertTrue(emetteur.getaMax() == 1f);
	}

	/**
	 * Test method for
	 * {@link emetteurRecepteur.emetteur.impl.AEmetteur#recevoir(information.Information)}.
	 */
	@Test
	public void testRecevoirInformationOfBoolean() {
		AEmetteur emetteur = new EmetteurRZ();
		Information<Boolean> info = new Information<Boolean>();
		info.add(true);
		emetteur.recevoir(info);
		/**
		 * recevoir doit affecter informationRecue et appeler emettre qui
		 * affecte informatonEmise
		 */
		assertTrue(emetteur.getInformationRecue() != null);
		assertTrue(emetteur.getInformationEmise() != null);
	}

}
