/**
 * 
 */
package sources.test;

import static org.junit.Assert.assertTrue;

import java.util.LinkedList;

import org.junit.Test;

import information.Information;
import sources.src.SourceFixe;

/**
 * @author FEDERICO Florian - florian.federico@telecom-bretagne.eu
 * @author SANCHEZ  Bastien - bastien.sanchez@telecom-bretagne.eu
 * @author MOULY    Thibaud - thibaud.mouly@telecom-bretagne.eu
 * @author PONCELET Nathan  - nathan.poncelet@telecom-bretagne.eu
 *
 * TELECOM Bretagne - FIP 2018 - 2016-2017
 * Projet SIT213 - Atelier systèmes de transmission
 * 
 */
public class SourceFixeTest {

	/**
	 * Test method for {@link sources.src.SourceFixe#SourceFixe(java.lang.String)}.
	 */
	@Test
	public void testSourceFixe() {
		/**
		 * Tests cas nominaux
		 */
		String message = "0100111001";
		boolean ok = this.testSourceFixeIteration(message);
		assertTrue("Generation du message "+message, ok == true);
		
		// La limitation minimum de 7 caracteres est gérée en amont par le Simulateur
		message = "11001";
		ok = this.testSourceFixeIteration(message);
		assertTrue("Generation du message "+message, ok == true);
		
		message = "11001101010111011001100111000110110";
		ok = this.testSourceFixeIteration(message);
		assertTrue("Generation du message "+message, ok == true);
		
		/**
		 * Tests de robustesse avec des caracteres autres que '1' ou '0'
		 */
		
		message = "22422112421241";
		ok = this.testSourceFixeIteration(message);
		assertTrue("Generation du message "+message, ok == true);
		
		message = "dsfsfdfsfdsfdszefzfsd";
		ok = this.testSourceFixeIteration(message);
		assertTrue("Generation du message "+message, ok == true);
		
	}
	
	/**
	 * Permet de factoriser le traitement d'un test sur le constructeur de SourceFixe
	 * @param message 	Le message a généré
	 * @return 	Vrai si le message est bien généré, faux sinon
	 */
	private boolean testSourceFixeIteration(String message) {
		SourceFixe source = new SourceFixe(message);
		LinkedList<Boolean> expected = SourceFixe.fromStrToBooleanList(message);
		Information<Boolean> actual = source.getInformationGeneree();
		
		boolean ok = true;
		for(int i=0; i<expected.size(); i++) {
			if(expected.get(i).equals(actual.iemeElement(i)) == false) {
				ok = false;
				break;
			}
		}
		return ok;
	}
}
