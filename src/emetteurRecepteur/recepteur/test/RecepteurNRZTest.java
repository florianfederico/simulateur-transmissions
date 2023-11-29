/**
 * 
 */
package emetteurRecepteur.recepteur.test;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import org.junit.Test;

import bruit.ABruit;
import bruit.BruitBlancGaussien;
import emetteurRecepteur.emetteur.impl.AEmetteur;
import emetteurRecepteur.emetteur.impl.EmetteurNRZ;
import emetteurRecepteur.factory.AFactoryEmetteurRecepteur;
import emetteurRecepteur.factory.FactoryNRZ;
import emetteurRecepteur.recepteur.detecteur.impl.EnumDetecteur;
import emetteurRecepteur.recepteur.impl.ARecepteur;
import sources.src.Source;
import sources.src.SourceAleatoire;
import transmetteurs.TransmetteurAnalogiqueBruite;
import transmetteurs.TransmetteurAnalogiqueParfait;

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
public class RecepteurNRZTest {

	private AFactoryEmetteurRecepteur factory = new FactoryNRZ();

	/**
	 * Test method for
	 * {@link emetteurRecepteur.recepteur.impl.RecepteurNRZ#RecepteurNRZ()}.
	 */
	@Test
	public void testRecepteurNRZ() {
		ARecepteur recepteur = factory.creerRecepteur(EnumDetecteur.EQUAL);
		assertTrue(recepteur.getNe() == 30);
		assertTrue(recepteur.getaMin() == 0f);
		assertTrue(recepteur.getaMax() == 1f);
	}

	/**
	 * Test method for
	 * {@link emetteurRecepteur.recepteur.impl.RecepteurNRZ#CAN()}.
	 */
	@Test
	public void testCAN() {

		EnumDetecteur detecteur = EnumDetecteur.EQUAL;

		assertTrue(this.testTransmissionParfaite(detecteur, 30, 5, 100) == true);
		assertTrue(this.testTransmissionParfaite(detecteur, 30, 0, 10) == true);
		assertTrue(this.testTransmissionParfaite(detecteur, 30, -100, 0) == true);
		assertTrue(this.testTransmissionParfaite(detecteur, 30, -100, 100) == true);
		assertTrue(this.testTransmissionParfaite(detecteur, 30, -100, -10) == true);

		detecteur = EnumDetecteur.FILTRE_ADAPTE;
		assertTrue(this.testTransmissionParfaite(detecteur, 30, 5, 100) == true);
		assertTrue(this.testTransmissionParfaite(detecteur, 30, 0, 10) == true);
		assertTrue(this.testTransmissionParfaite(detecteur, 30, -100, 0) == true);
		assertTrue(this.testTransmissionParfaite(detecteur, 30, -100, 100) == true);
		assertTrue(this.testTransmissionParfaite(detecteur, 30, -100, -10) == true);
		assertTrue(this.testTransmissionParfaite(detecteur, 30, -100, 200) == true);

		assertTrue(this.testTransmissionBruitee(detecteur, 30, 5, 100) == true);
		assertTrue(this.testTransmissionBruitee(detecteur, 30, 0, 10) == true);
		assertTrue(this.testTransmissionBruitee(detecteur, 30, -100, 0) == true);
		assertTrue(this.testTransmissionBruitee(detecteur, 30, -100, 100) == true);
		assertTrue(this.testTransmissionBruitee(detecteur, 30, -100, -10) == true);
		assertTrue(this.testTransmissionBruitee(detecteur, 30, -100, 200) == true);

	}

	private boolean testTransmissionParfaite(EnumDetecteur detecteurType, int ne, float aMin, float aMax) {
		boolean ok = true;

		Source<Boolean> source = new SourceAleatoire(100);
		AEmetteur emetteur = new EmetteurNRZ(ne, aMin, aMax);
		TransmetteurAnalogiqueParfait transmetteurParfait = new TransmetteurAnalogiqueParfait();
		ARecepteur recepteur = factory.creerRecepteur(detecteurType, ne, aMin, aMax);

		source.connecter(emetteur);
		emetteur.connecter(transmetteurParfait);
		transmetteurParfait.connecter(recepteur);
		source.emettre();

		if (recepteur.getInformationEmise().equals(source.getInformationEmise()) == false)
			ok = false;

		return ok;
	}

	private boolean testTransmissionBruitee(EnumDetecteur detecteurType, int ne, float aMin, float aMax) {
		boolean ok = true;

		Source<Boolean> source = new SourceAleatoire(100);
		AEmetteur emetteur = new EmetteurNRZ(ne, aMin, aMax);
		float SNR = 0f;

		source.connecter(emetteur);
		source.emettre();

		ABruit bruitGaussien = new BruitBlancGaussien(emetteur.getInformationEmise(), SNR);
		ArrayList<ABruit> bruits = new ArrayList<ABruit>();
		bruits.add(bruitGaussien);
		TransmetteurAnalogiqueBruite transmetteurBruite = new TransmetteurAnalogiqueBruite(bruits);
		ARecepteur recepteur = factory.creerRecepteur(detecteurType, ne, aMin, aMax);

		emetteur.connecter(transmetteurBruite);
		transmetteurBruite.connecter(recepteur);
		source.emettre();

		if (recepteur.getInformationEmise().equals(source.getInformationEmise()) == false)
			ok = false;

		return ok;

	}

}
