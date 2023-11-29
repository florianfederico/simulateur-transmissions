package bruit;

import java.util.Random;

import emetteurRecepteur.emetteur.impl.AEmetteur;
import emetteurRecepteur.factory.AFactoryEmetteurRecepteur;
import emetteurRecepteur.factory.FactoryNRZ;
import information.Information;
import sources.src.SourceAleatoire;

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
public class BruitBlancGaussien extends ABruit {

	private Random rand;

	private float moyenne;

	private float sigma;

	/**
	 * Cree un bruit blanc gaussien
	 * 
	 * @param signal
	 *            Le signal auquel le bruit va etre applique
	 * @param snr
	 *            Le rapport signal sur bruit souhaite en dB
	 */
	public BruitBlancGaussien(Information<Float> signal, float snr) {
		super();
		this.moyenne = 0;
		this.sigma = this.calculerSigma(signal, snr);
		this.rand = new Random();
	}

	/**
	 * Cree un bruit blanc gaussien
	 * 
	 * @param signal
	 *            Le signal auquel le bruit va etre applique
	 * @param snr
	 *            Le rapport signal sur bruit souhaite en dB
	 * @param seed
	 *            La seed pour regenerer le meme bruit
	 */
	public BruitBlancGaussien(Information<Float> signal, float snr, int seed) {
		super();
		this.moyenne = 0;
		this.sigma = this.calculerSigma(signal, snr);
		this.rand = new Random(seed);
	}

	/**
	 * Determine sigma pour adapte le bruit en fonction du signal et du SNR
	 * 
	 * @param signal
	 *            Le signal
	 * @param snr
	 *            Le rapport signal sur bruit
	 * @return Le sigma
	 */
	private float calculerSigma(Information<Float> signal, float snr) {
		float sigma = 0f;

		float pMoySignal = this.calculerPuissanceMoyenne(signal);
		float snrLin = (float) Math.pow(10, snr / 10);
		float pMoyBruit = pMoySignal / snrLin;
		sigma = (float) Math.sqrt(pMoyBruit);

		return sigma;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see transmetteurs.bruit.impl.ABruit#generer(information.Information)
	 */
	@Override
	public Information<Float> generer(Information<Float> info) {
		Information<Float> infoSortie = new Information<Float>();

		float bruit = 0f;
		for (int i = 0; i < info.nbElements(); i++) {
			bruit = (float) (this.rand.nextGaussian()) * this.sigma + this.moyenne;
			infoSortie.add(bruit);
		}
		return infoSortie;
	}

	public float getMoyenne() {
		return moyenne;
	}

	public float getSigma() {
		return sigma;
	}

	public static void main(String[] args) {
		AFactoryEmetteurRecepteur factory = new FactoryNRZ();
		SourceAleatoire source = new SourceAleatoire(10000);
		AEmetteur e = factory.creerEmetteur(30, 0, 2);
		float snr = 3;

		source.connecter(e);
		source.emettre();

		BruitBlancGaussien bruit = new BruitBlancGaussien(e.getInformationEmise(), snr);
		System.out.println("Sigma = " + bruit.sigma);

		ABruit.WriteCSV("test.csv", bruit.getHistogramme());
	}

}
