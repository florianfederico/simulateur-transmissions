/**
 * 
 */
package emetteurRecepteur.form.test;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import emetteurRecepteur.form.src.NRZT;
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
public class NRZTTest {

	/**
	 * Test method for
	 * {@link emetteurRecepteur.form.src.NRZT#verifierNbEchantillons(int)}.
	 */
	@Test
	public void testVerifierNbEchantillons() {
		/**
		 * Les signaux RZ exigent un nombre d'echantillons divisible par 3
		 */
		NRZT nrzt = new NRZT(5, 0, 1);
		assertTrue(nrzt.getNe() == 6);

		nrzt = new NRZT(7, 0, 1);
		assertTrue(nrzt.getNe() == 9);

		nrzt = new NRZT(10, 0, 1);
		assertTrue(nrzt.getNe() == 12);
	}

	/**
	 * Test method for
	 * {@link emetteurRecepteur.form.src.NRZT#getSymboleParfait(boolean)}.
	 */
	@Test
	public void testGenererSymboleAnalogiqueParfait() {
		NRZT nrzt = new NRZT(30, -1, 1);

		/** Test avec un bit 1 */
		assertTrue(this.testSymbole(true, nrzt) == true);

		/** Test avec un bit 0 */
		assertTrue(this.testSymbole(false, nrzt) == true);
	}

	/**
	 * Teste si le symbole generé est correct
	 * 
	 * @param etatSymbole
	 *            L'etat du symbole: Vrai si etat haut, faux si etat bas
	 * @param nrzt
	 *            Le NRZT qui genere le symbole
	 * @return Vrai si le symbole est correct, faux sinon
	 */
	private boolean testSymbole(boolean etatSymbole, NRZT nrzt) {
		Information<Float> info = nrzt.getSymboleParfait(etatSymbole);

		float pas = (nrzt.getaMax() - nrzt.getaMin()) / (nrzt.getNe() / 3);
		float aCourant = nrzt.getaMin();
		boolean ok = true;

		System.out.println(info.nbElements());
		if (info.nbElements() != nrzt.getNe())
			return false;
		
		if (etatSymbole) {
			/**
			 * On verifie que le 1/3 du symbole correspond bien a une montee de
			 * amplitude min (incluse) vers max (non incluse)
			 */
			int i = 0;
			while (i < info.nbElements() / 3) {
				System.out.print(info.iemeElement(i)+" ");
				if (info.iemeElement(i) != aCourant) {
					ok = false;
					break;
				}
				aCourant += pas;
				i++;
			}
			/**
			 * On verifie que le 1/3 suivant du symbole correspond bien a
			 * l'amplitude max
			 */
			while (i < 2 * info.nbElements() / 3) {
				if (info.iemeElement(i) != nrzt.getaMax()) {
					ok = false;
					break;
				}
				i++;
			}
			/**
			 * On verifie que le 1/3 suivant du symbole correspond bien a une
			 * descente de l'amplitude max (non incluse) vers min (incluse)
			 */
			aCourant = nrzt.getaMax();
			while (i < (3 * info.nbElements() / 3 - 1)) {
				aCourant -= pas;
				if (info.iemeElement(i) != aCourant) {
					ok = false;
					break;
				}
				i++;
			}
			if (info.iemeElement(i) != nrzt.getaMin())
				ok = false;
		} else {
			for (int i = 0; i < info.nbElements(); i++) {
				if (info.iemeElement(i) != nrzt.getaMin()) {
					ok = false;
					break;
				}
			}
		}

		return ok;
	}

}
