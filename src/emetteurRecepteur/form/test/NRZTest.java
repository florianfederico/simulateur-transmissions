/**
 * 
 */
package emetteurRecepteur.form.test;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import emetteurRecepteur.form.src.NRZ;
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
public class NRZTest {

	/**
	 * Test method for
	 * {@link emetteurRecepteur.form.src.NRZ#verifierNbEchantillons(int)}.
	 */
	@Test
	public void testVerifierNbEchantillons() {
		/**
		 * Les signaux NRZ n'ont pas d'exigences sur le nombre d'echantillons.
		 * La fonction ne doit donc pas modifier le nombre d'echantillon
		 * 
		 */
		NRZ nrz = new NRZ(12, 0, 1);
		assertTrue(nrz.getNe() == 12);
		
		nrz = new NRZ(13, 0, 1);
		assertTrue(nrz.getNe() == 13);
		
		nrz = new NRZ(15, 0, 1);
		assertTrue(nrz.getNe() == 15);
	}

	/**
	 * Test method for
	 * {@link emetteurRecepteur.form.src.NRZ#getSymboleParfait(boolean)}.
	 */
	@Test
	public void testGenererSymboleAnalogiqueParfait() {
		NRZ nrz = new NRZ(30, -10, 10);

		assertTrue(this.testSymbole(true, nrz) == true);
		assertTrue(this.testSymbole(false, nrz) == true);
	}

	/**
	 * Teste si le symbole generé est correct
	 * 
	 * @param etatSymbole
	 *            L'etat du symbole: Vrai si etat haut, faux si etat bas
	 * @param nrz
	 *            Le NRZ qui genere le symbole
	 * @return Vrai si le symbole est correct, faux sinon
	 */
	private boolean testSymbole(boolean etatSymbole, NRZ nrz) {
		Information<Float> info = nrz.getSymboleParfait(etatSymbole);
		boolean ok = true;
		float expected = (etatSymbole) ? nrz.getaMax() : nrz.getaMin();

		for (int i = 0; i < info.nbElements(); i++) {
			if (info.iemeElement(i) != expected)
				ok = false;
		}
		return ok;
	}

}
