package sources.test;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import sources.src.SourceAleatoire;

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
public class SourceAleatoireTest {

	@Test
	public void testSourceAleatoireInt() {
		int longueurMessage = 0;
		SourceAleatoire source = new SourceAleatoire(longueurMessage);
		assertTrue("Message de longueur " + longueurMessage,
				source.getInformationGeneree().nbElements() == longueurMessage);

		longueurMessage = 10;
		source = new SourceAleatoire(longueurMessage);
		assertTrue("Message de longueur " + longueurMessage,
				source.getInformationGeneree().nbElements() == longueurMessage);

		longueurMessage = -10;
		source = new SourceAleatoire(longueurMessage);
		assertTrue("Message de longueur " + longueurMessage, source.getInformationGeneree().nbElements() == 0);

		longueurMessage = 100;
		source = new SourceAleatoire(longueurMessage);
		assertTrue("Message de longueur " + longueurMessage,
				source.getInformationGeneree().nbElements() == longueurMessage);

	}

	@Test
	public void testSourceAleatoireIntInt() {
		int longueurMessage = 0;
		int seed = 0;
		SourceAleatoire source = new SourceAleatoire(longueurMessage, seed);
		assertTrue("Message de longueur " + longueurMessage + " avec seed " + seed,
				source.getInformationGeneree().nbElements() == longueurMessage);

		longueurMessage = 10;
		seed = 1;
		source = new SourceAleatoire(longueurMessage, seed);
		assertTrue("Message de longueur " + longueurMessage + " avec seed " + seed,
				source.getInformationGeneree().nbElements() == longueurMessage);

		longueurMessage = -10;
		seed = 1;
		source = new SourceAleatoire(longueurMessage, seed);
		assertTrue("Message de longueur " + longueurMessage + " avec seed " + seed,
				source.getInformationGeneree().nbElements() == 0);

		longueurMessage = 100;
		seed = 5;
		source = new SourceAleatoire(longueurMessage, seed);
		assertTrue("Message de longueur " + longueurMessage + " avec seed " + seed,
				source.getInformationGeneree().nbElements() == longueurMessage);

	}

}
