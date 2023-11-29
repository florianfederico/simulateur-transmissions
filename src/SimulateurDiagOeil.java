import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import com.csvreader.CsvWriter;

import bruit.ABruit;
import bruit.BruitBlancGaussien;
import emetteurRecepteur.emetteur.impl.AEmetteur;
import emetteurRecepteur.factory.AFactoryEmetteurRecepteur;
import emetteurRecepteur.factory.FactoryRZ;
import information.Information;
import sources.src.SourceAleatoire;
import transmetteurs.TransmetteurAnalogiqueBruite;

public class SimulateurDiagOeil {

	public static void main(String[] args) {

		int nbRealisations = 100;
		for (int i = 0; i < nbRealisations; i++) {
			SimulateurDiagOeil.WriteCSV("DiagOeil_" + i + ".csv", SimulateurDiagOeil.genererBit());
		}

	}

	public static Information<Float> genererBit() {
		float snr = 20;

		AFactoryEmetteurRecepteur factory = new FactoryRZ();
		SourceAleatoire source = new SourceAleatoire(3);
		AEmetteur emetteur = factory.creerEmetteur();

		source.connecter(emetteur);
		source.emettre();

		ABruit bruit = new BruitBlancGaussien(emetteur.getInformationEmise(), snr);
		ArrayList<ABruit> bruits = new ArrayList<ABruit>();
		bruits.add(bruit);

		TransmetteurAnalogiqueBruite transmetteur = new TransmetteurAnalogiqueBruite(bruits);
		// TransmetteurAnalogiqueParfait transmetteur = new
		// TransmetteurAnalogiqueParfait();
		emetteur.connecter(transmetteur);

		source.emettre();

		return transmetteur.getInformationEmise();
	}

	public static void WriteCSV(String nomFichier, Information<Float> data) {

		try {
			FileWriter file = new FileWriter(nomFichier, true);
			// use FileWriter constructor that specifies open for appending
			CsvWriter csvOutput = new CsvWriter(file, ',');

			for (Float f : data) {
				csvOutput.write(f.toString());
				csvOutput.endRecord();
			}

			csvOutput.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
