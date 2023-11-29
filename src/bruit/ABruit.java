package bruit;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedHashMap;

import com.csvreader.CsvWriter;

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
public abstract class ABruit {

	/**
	 * Renvoie le bruit a ajouter au signal non bruit
	 * 
	 * @param info
	 *            Le signal non bruite
	 * @return Le signal modifie
	 */
	public abstract Information<Float> generer(Information<Float> info);

	/**
	 * Calcule la puissance moyenne du signa
	 * 
	 * @param signal
	 *            Le signal
	 * @return La puissance moyenne du signal
	 */
	protected float calculerPuissanceMoyenne(Information<Float> signal) {
		float pMoy = 0f;

		for (Float f : signal)
			pMoy += Math.pow(f, 2);

		if (signal.nbElements() != 0)
			pMoy /= signal.nbElements();
		else
			pMoy = 0f;
		
		return pMoy;
	}

	/**
	 * Affiche l'histogramme du bruit
	 * 
	 * @return TODO
	 */
	public LinkedHashMap<Float, Integer> getHistogramme() {

		LinkedHashMap<Float, Integer> histogramme = new LinkedHashMap<Float, Integer>();
		ArrayList<Float> list = new ArrayList<Float>();

		final int NB_VALEURS = (int) Math.pow(10, 6);
		final float PREC_DECIMALE = 100f; // 2 chiffres apres la virgule
		long startTime = System.nanoTime();

		/**
		 * Generation de NB_VALEURS valeurs
		 */
		Information<Float> info = new Information<Float>();
		info.add(0f);
		for (int i = 0; i < NB_VALEURS; i++) {
			list.add(((int) (this.generer(info).iemeElement(0) * PREC_DECIMALE)) / PREC_DECIMALE);
		}

		/**
		 * Trie des valeurs generee
		 */
		list.sort(new Comparator<Float>() {
			@Override
			public int compare(Float o1, Float o2) {
				return o1.compareTo(o2);
			}
		});

		/**
		 * Comptage des occurrences
		 */
		int nbOccurrences = 0;
		Float valeurOccurrence = list.get(0);
		for (Float f : list) {
			if (f.equals(valeurOccurrence))
				nbOccurrences++;
			else {
				histogramme.put(valeurOccurrence, nbOccurrences);
				nbOccurrences = 1;
				valeurOccurrence = f;
			}

		}

		long endTime = System.nanoTime();
		long duration = (endTime - startTime) / 1000000; // milliseconds.
		System.out.println("Temps d execution de l histogramme: " + duration / 1000.0 + " secondes	");

		return histogramme;
	}

	public static void WriteCSV(String nomFichier, LinkedHashMap<Float, Integer> data) {

		try {
			FileWriter file = new FileWriter(nomFichier, true);
			// use FileWriter constructor that specifies open for appending
			CsvWriter csvOutput = new CsvWriter(file, ',');

			for (Float key : data.keySet()) {
				csvOutput.write(key.toString());
				csvOutput.write(data.get(key).toString());
				csvOutput.endRecord();
			}

			csvOutput.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
