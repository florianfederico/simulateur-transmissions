/**
 * 
 */
package emetteurRecepteur.emetteur.test;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import emetteurRecepteur.emetteur.impl.AEmetteur;
import emetteurRecepteur.emetteur.impl.EmetteurNRZT;
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
public class EmetteurNRZTTest {

	/**
	 * Test method for
	 * {@link emetteurRecepteur.emetteur.impl.EmetteurNRZT#EmetteurNRZT()}.
	 */
	@Test
	public void testEmetteurNRZT() {
		AEmetteur emetteur = new EmetteurNRZT();
		assertTrue(emetteur.getNe() == 30);
		assertTrue(emetteur.getaMin() == 0f);
		assertTrue(emetteur.getaMax() == 1f);
	}

	/**
	 * Test method for
	 * {@link emetteurRecepteur.emetteur.impl.AEmetteur#recevoir(information.Information)}.
	 */
	@Test
	public void testRecevoirInformationOfBoolean() {
		AEmetteur emetteur = new EmetteurNRZT();
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
