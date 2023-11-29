import java.io.FileWriter;

import java.io.IOException;
import java.util.LinkedHashMap;

import com.csvreader.CsvWriter;

/**
 * @author FEDERICO Florian - florian.federico@telecom-bretagne.eu
 * @author SANCHEZ  Bastien - bastien.sanchez@telecom-bretagne.eu
 * @author MOULY    Thibaud - thibaud.mouly@telecom-bretagne.eu
 * @author PONCELET Nathan  - nathan.poncelet@telecom-bretagne.eu
 *
 * TELECOM Bretagne - FIP 2018 - 2016-2017
 * Projet SIT213 - Atelier systèmes de transmission
 * 
 * Rempli un fichier CSV avec le TEB en fonction du SNR
 */
public class SimulateurTEB {

	public static void main(String[] args) {
		/**
		 * Contient le TEB en fonction du SNR
		 */
		LinkedHashMap<Float, Float> map = new LinkedHashMap<Float, Float>();
		Integer nbEch = 15;
		Integer mess = 100000;
		
		Float snrMin = -20f;
		Float snrMax = 10f;
		Float snrPas = 0.1f;
		
		Integer seed = 10;
		
		
		/**
		 * Cas NRZ
		 */
		map = new LinkedHashMap<Float, Float>();
		for(Float snr = snrMin; snr <= snrMax; snr+=snrPas) {
			String[] param = {"-mess", mess.toString(), "-seed", seed.toString(), "-transducteur", "-form", "NRZ", "-nbEch", nbEch.toString(), "-snr", snr.toString() };
			Simulateur.main(param);
			map.put(snr, Simulateur.simulateur.getTEB());
		}
		SimulateurTEB.WriteCSV("TEB_SNR_NRZ.csv", map);
		
		/**
		 * Cas NRZT
		 */
		map = new LinkedHashMap<Float, Float>();
		for(Float snr = snrMin; snr <= snrMax; snr+=snrPas) {
			String[] param = {"-mess", mess.toString(), "-seed", seed.toString(), "-transducteur", "-form", "NRZT", "-nbEch", nbEch.toString(), "-snr", snr.toString() };
			Simulateur.main(param);
			map.put(snr, Simulateur.simulateur.getTEB());
		}
		SimulateurTEB.WriteCSV("TEB_SNR_NRZT.csv", map);
		
		/**
		 * Cas RZ
		 */
		map = new LinkedHashMap<Float, Float>();
		for(Float snr = snrMin; snr <= snrMax; snr+=snrPas) {
			String[] param = {"-mess", mess.toString(), "-seed", seed.toString(), "-transducteur", "-form", "RZ", "-nbEch", nbEch.toString(), "-snr", snr.toString()};
			Simulateur.main(param);
			map.put(snr, Simulateur.simulateur.getTEB());
		}
		SimulateurTEB.WriteCSV("TEB_SNR_RZ.csv", map);
	}

	public static void WriteCSV(String nomFichier, LinkedHashMap<Float, Float> data) {

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
