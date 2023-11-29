/**
 * 
 */
package emetteurRecepteur.form.test;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import emetteurRecepteur.form.src.RZ;
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
public class RZTest {

	/**
	 * Test method for
	 * {@link emetteurRecepteur.form.src.RZ#verifierNbEchantillons(int)}.
	 */
	@Test
	public void testVerifierNbEchantillons() {
		
		/**
		 * Les signaux RZ exigent un nombre d'echantillons divisible par 3 et
		 * impair.
		 */
		RZ rz = new RZ(3, 0, 1);
		assertTrue(rz.getNe() == 3);
		
		rz = new RZ(5, 0, 1);
		assertTrue(rz.getNe() == 9);
		
		rz = new RZ(2, 0, 1);
		assertTrue(rz.getNe() == 3);
		
		rz = new RZ(12,0,1);
		assertTrue(rz.getNe() == 15);
	}

	/**
	 * Test method for
	 * {@link emetteurRecepteur.form.src.RZ#getSymboleParfait(boolean)}.
	 */
	@Test
	public void testGenererSymboleAnalogiqueParfait() {
		RZ rz = new RZ(33, -1, 1);

		/** Test avec un bit 1 */
		assertTrue(this.testSymbole(true, rz) == true);

		/** Test avec un bit 0 */
		assertTrue(this.testSymbole(false, rz) == true);

	}

	/**
	 * Teste si le symbole generé est correct
	 * 
	 * @param etatSymbole
	 *            L'etat du symbole: Vrai si etat haut, faux si etat bas
	 * @param rz
	 *            Le RZ qui genere le symbole
	 * @return Vrai si le symbole est correct, faux sinon
	 */
	private boolean testSymbole(boolean etatSymbole, RZ rz) {
		Information<Float> info = rz.getSymboleParfait(etatSymbole);
		float pas = (rz.getaMax() - rz.getaMin()) / ((rz.getNe() / 6) + 1);
		float aCourant = rz.getaMin();
		boolean ok = true;

		if (etatSymbole) {
			/**
			 * On verifie que le 1/3 du symbole correspond bien a l'amplitude
			 * min
			 */
			int i = 0;
			while (i < info.nbElements() / 3) {
				if (info.iemeElement(i) != rz.getaMin())
					ok = false;
				i++;
			}
			/**
			 * On verifie que le 1/6 suivant du symbole correspond bien a une
			 * montee de amplitude min vers max (inclus)
			 */
			while (i < (3 * info.nbElements() / 6)) {
				aCourant += pas;
				if (info.iemeElement(i) != aCourant) {
					ok = false;
					break;
				}
				i++;
			}

			/**
			 * On verifie la valeur maximale
			 */
			if (info.iemeElement(i) != rz.getaMax())
				ok = false;
			i++;
			aCourant = rz.getaMax();

			/**
			 * On verifie que le 1/6 suivant du symbole correspond bien a une
			 * descente de amplitude max vers min
			 */
			while (i < (4 * info.nbElements() / 6)) {
				aCourant -= pas;
				if (info.iemeElement(i) != aCourant) {
					ok = false;
					break;
				}
				i++;
			}

			/**
			 * On verifie que le reste est a aMin
			 */
			while (i < info.nbElements()) {
				if (info.iemeElement(i) != rz.getaMin()) {
					ok = false;
					break;
				}
				i++;
			}

		} else {
			for (int i = 0; i < info.nbElements(); i++) {
				if (info.iemeElement(i) != rz.getaMin()) {
					ok = false;
					break;
				}
			}
		}

		return ok;
	}

}
