
import java.util.ArrayList;

import bruit.ABruit;
import bruit.BruitBlancGaussien;
import bruit.BruitTrajetMultiple;
import destinations.Destination;
import destinations.DestinationBoolean;
import emetteurRecepteur.emetteur.impl.AEmetteur;
import emetteurRecepteur.factory.AFactoryEmetteurRecepteur;
import emetteurRecepteur.factory.FactoryNRZ;
import emetteurRecepteur.factory.FactoryNRZT;
import emetteurRecepteur.factory.FactoryRZ;
import emetteurRecepteur.form.src.EnumForm;
import emetteurRecepteur.recepteur.detecteur.impl.EnumDetecteur;
import emetteurRecepteur.recepteur.impl.ARecepteur;
import information.Information;
import sources.src.Source;
import sources.src.SourceAleatoire;
import sources.src.SourceFixe;
import transducteur.ATransducteur;
import transducteur.TransducteurEmission;
import transducteur.TransducteurReception;
import transmetteurs.Transmetteur;
import transmetteurs.TransmetteurAnalogiqueBruite;
import transmetteurs.TransmetteurAnalogiqueParfait;
import transmetteurs.TransmetteurLogiqueParfait;
import visualisations.Sonde;
import visualisations.SondeAnalogique;
import visualisations.SondeLogique;

/**
 * La classe Simulateur permet de construire et simuler une chaîne de
 * transmission composée d'une Source, d'un nombre variable de Transmetteur(s)
 * et d'une Destination.
 * 
 * @author cousin
 * @author prou
 * 
 */
public class Simulateur {

	public static Simulateur simulateur = null;

	private float TEB = 0f;

	/** indique si le Simulateur utilise des sondes d'affichage */
	private boolean affichage = false;
	/**
	 * indique si le Simulateur utilise un message généré de manière
	 * aléatoire
	 */
	private boolean messageAleatoire = true;
	/**
	 * indique si le Simulateur utilise un germe pour initialiser les
	 * générateurs aléatoires
	 */
	private boolean aleatoireAvecGerme = false;
	/** la valeur de la semence utilisée pour les générateurs aléatoires */
	private Integer seed = null;
	/**
	 * la longueur du message aléatoire à transmettre si un message n'est pas
	 * impose
	 */
	private int nbBitsMess = 100;
	/** la chaîne de caractères correspondant à m dans l'argument -mess m */
	private String messageString = "100";

	/** Indique si la transmission est analogique et sa forme */
	private EnumForm form = EnumForm.RZ;

	/** Indique le nombre d'echantillons par bit */
	private int ne = 30;

	/** Amplitude min */
	private float min = 0f;

	/* Amplitude max */
	private float max = 1f;

	/* Le rapport signal sur bruit en dB */
	private float snr = 0f;

	/* Definit si le systeme analogique est bruite */
	private boolean systemeAnalogBruite = false;

	/* Definit si le snr doit etre applique ou non */
	private boolean applySnr = false;

	/* Limite le nombre de trajets multiples possibles */
	private int nbTi = 0;
	private final int NB_TI_MAX = 5;

	/** le composant Source de la chaine de transmission */
	private Source<Boolean> source = null;

	/** le composant Emetteur de la chaine de transmission */
	private AEmetteur emetteur = null;
	/**
	 * le composant Transmetteur logique de la chaine de transmission
	 */
	private Transmetteur<Boolean, Boolean> transmetteurLogique = null;
	/**
	 * le composant Transmetteur analogique de la chaine de transmission
	 */
	private TransmetteurAnalogiqueParfait transmetteurAnalogiqueParfait = null;

	/**
	 * le composant Transmetteur analogique bruite de la chaine de transmission
	 */
	private TransmetteurAnalogiqueBruite transmetteurAnalogiqueBruite = null;
	private ArrayList<ABruit> bruits = new ArrayList<ABruit>();
	private EnumDetecteur detecteur = EnumDetecteur.FILTRE_ADAPTE;

	private boolean transducteur = false;
	private ATransducteur transducteurEmission = null;
	private ATransducteur transducteurReception = null;

	/** le composant Emetteur de la chaine de transmission */
	private ARecepteur recepteur = null;

	/** le composant Destination de la chaine de transmission */
	private Destination<Boolean> destination = null;

	/**
	 * Le constructeur de Simulateur construit une chaîne de transmission
	 * composée d'une Source Boolean, d'une Destination Boolean et de
	 * Transmetteur(s) [voir la méthode analyseArguments]... <br>
	 * Les différents composants de la chaîne de transmission (Source,
	 * Transmetteur(s), Destination, Sonde(s) de visualisation) sont créés et
	 * connectés.
	 * 
	 * @param args
	 *            le tableau des différents arguments.
	 * 
	 * @throws ArgumentsException
	 *             si un des arguments est incorrect
	 * 
	 */
	public Simulateur(String[] args) throws ArgumentsException {

		// analyser et récupérer les arguments
		analyseArguments(args);

		/* Instanciation de la source */
		this.creerSource();

		/* Instanciation de la destination */
		this.destination = new DestinationBoolean();

		if (this.form != null)
			this.creerSystemAnalogique();
		else
			this.creerSystemLogique();

		/*
		 * Ajout de sondes d'affichage si nécessaire
		 */
		if (this.affichage) {
			if (this.form != null)
				this.creerAffichageSystemeAnalogique();
			else
				this.creerAffichageSystemeLogique();
		}

	}

	/**
	 * La méthode analyseArguments extrait d'un tableau de chaînes de
	 * caractères les différentes options de la simulation. Elle met à jour
	 * les attributs du Simulateur.
	 * 
	 * @param args
	 *            le tableau des différents arguments. <br>
	 *            <br>
	 *            Les arguments autorisés sont : <br>
	 *            <dl>
	 *            <dt>-mess m</dt>
	 *            <dd>m (String) constitué de 7 ou plus digits à 0 | 1, le
	 *            message à transmettre</dd>
	 *            <dt>-mess m</dt>
	 *            <dd>m (int) constitué de 1 à 6 digits, le nombre de bits du
	 *            message "aléatoire" à  transmettre</dd>
	 *            <dt>-s</dt>
	 *            <dd>utilisation des sondes d'affichage</dd>
	 *            <dt>-seed v</dt>
	 *            <dd>v (int) d'initialisation pour les générateurs
	 *            aléatoires</dd>
	 *            </dl>
	 * 
	 * @throws ArgumentsException
	 *             si un des arguments est incorrect.
	 * 
	 */
	public void analyseArguments(String[] args) throws ArgumentsException {

		for (int i = 0; i < args.length; i++) {

			if (args[i].matches("-s")) {
				affichage = true;
			} else if (args[i].matches("-seed")) {
				aleatoireAvecGerme = true;
				i++;
				// traiter la valeur associee
				try {
					seed = new Integer(args[i]);
				} catch (Exception e) {
					throw new ArgumentsException("Valeur du parametre -seed  invalide :" + args[i]);
				}
			}

			else if (args[i].matches("-mess")) {
				i++;
				// traiter la valeur associee
				messageString = args[i];
				if (args[i].matches("[0,1]{7,}")) {
					messageAleatoire = false;
					nbBitsMess = args[i].length();
				} else if (args[i].matches("[0-9]{1,6}")) {
					messageAleatoire = true;
					nbBitsMess = new Integer(args[i]);
					if (nbBitsMess < 1)
						throw new ArgumentsException("Valeur du parametre -mess invalide : " + nbBitsMess);
				} else
					throw new ArgumentsException("Valeur du parametre -mess invalide : " + args[i]);
			} else if (args[i].matches("-form")) {
				i++;
				if (i >= args.length) {
					this.form = EnumForm.RZ;
				} else if (args[i].charAt(0) == '-') {
					this.form = EnumForm.RZ;
					i--;
				}

				else {
					if (args[i].equals("NRZ"))
						this.form = EnumForm.NRZ;
					else if (args[i].equals("NRZT"))
						this.form = EnumForm.NRZT;
					else if (args[i].equals("RZ"))
						this.form = EnumForm.RZ;
					else
						throw new ArgumentsException("Valeur du parametre -form invalide : " + args[i]);
				}
			} else if (args[i].matches("-nbEch")) {
				i++;
				try {
					this.ne = Integer.parseInt(args[i]);
				} catch (NumberFormatException e) {
					throw new ArgumentsException("Valeur du parametre -nbEch invalide : " + args[i]);
				}
			} else if (args[i].matches("-ampl")) {
				i++;
				try {
					this.min = Float.parseFloat(args[i]);
				} catch (NumberFormatException e) {
					throw new ArgumentsException("Valeur du parametre -ampl min invalide : " + args[i]);
				}

				i++;
				try {
					this.max = Float.parseFloat(args[i]);
				} catch (NumberFormatException e) {
					throw new ArgumentsException("Valeur du parametre -ampl max invalide : " + args[i]);
				}
				if (this.min >= max)
					throw new ArgumentsException(
							"Valeur du parametre -ampl min: " + this.min + "  >= max: " + this.max + " invalide");
			} else if (args[i].matches("-snr")) {
				i++;
				try {
					this.snr = Float.parseFloat(args[i]);
					this.applySnr = true;
					this.systemeAnalogBruite = true;
				} catch (NumberFormatException e) {
					this.applySnr = false;
					this.systemeAnalogBruite = false;
					System.out.println("Passage en transmission analogique parfaite");
					throw new ArgumentsException("Valeur du parametre -snr invalide : " + args[i]);
				}
			} else if (args[i].matches("-ti")) {
				i++;
				this.nbTi++;
				if (this.nbTi <= this.NB_TI_MAX) {
					int dt = 0;
					float ar = 0.0f;

					if (i == args.length)
						break;

					if (args[i].charAt(0) == '-') {
						this.bruits.add(new BruitTrajetMultiple());
						i--;
					} else {
						try {
							dt = Integer.parseInt(args[i]);
							i++;
							ar = Float.parseFloat(args[i]);
							this.bruits.add(new BruitTrajetMultiple(dt, ar));
							this.systemeAnalogBruite = true;
						} catch (NumberFormatException e) {
							throw new ArgumentsException(
									"Valeur du parametre -ti invalide : " + args[i - 1] + " " + args[i]);
						}
					}
				} else
					throw new ArgumentsException("Le nombre de trajet multiples maximum est depasse: " + this.nbTi);
			} else if (args[i].matches("-transducteur")) {
				this.transducteur = true;

			} else
				throw new ArgumentsException("Option invalide :" + args[i]);
		}

	}

	/**
	 * La méthode execute effectue un envoi de message par la source de la
	 * chaîne de transmission du Simulateur.
	 * 
	 * @throws Exception
	 *             si un problème survient lors de l'exécution
	 * 
	 */
	public void execute() throws Exception {

		source.emettre();

	}

	/**
	 * La méthode qui calcule le taux d'erreur binaire en comparant les bits du
	 * message émis avec ceux du message reçu.
	 * 
	 * @return La valeur du Taux dErreur Binaire.
	 */
	public float calculTauxErreurBinaire() {

		// A compléter
		Information<Boolean> infoEmise = this.source.getInformationEmise();
		Information<Boolean> infoRecue = this.destination.getInformationRecue();

		float nbElementsEmis = infoEmise.nbElements();

		int nbErreurs = 0;
		int i = 0;

		while (i < infoEmise.nbElements() && i < infoRecue.nbElements()) {
			nbErreurs += (infoEmise.iemeElement(i).equals(infoRecue.iemeElement(i)) == false) ? 1 : 0;
			i++;
		}

		while (i < infoEmise.nbElements()) {
			nbErreurs++;
			i++;
		}

		float TEB = nbErreurs / nbElementsEmis;
		if (TEB > 0.5f)
			TEB = 0.5f;
		return TEB;
	}

	/**
	 * La fonction main instancie un Simulateur à l'aide des arguments
	 * paramètres et affiche le résultat de l'exécution d'une transmission.
	 * 
	 * @param args
	 *            les différents arguments qui serviront à l'instanciation du
	 *            Simulateur.
	 */
	public static void main(String[] args) {
		simulateur = null;
		long startTime = System.nanoTime();
		try {
			simulateur = new Simulateur(args);
		} catch (Exception e) {
			System.out.println(e);
			System.exit(-1);
		}

		try {
			simulateur.execute();
			simulateur.setTEB(simulateur.calculTauxErreurBinaire());
			String s = "java  Simulateur  ";
			for (int i = 0; i < args.length; i++) {
				s += args[i] + "  ";
			}
			System.out.println(s + "  =>   TEB : " + simulateur.getTEB());
		} catch (Exception e) {
			System.out.println(e);
			e.printStackTrace();
			System.exit(-2);
		}
		long endTime = System.nanoTime();
		long duration = (endTime - startTime) / 1000000; // milliseconds.
		System.out.println("Time of execution: " + duration / 1000.0 + " seconds	");
	}

	/**
	 * Instancie la source en fonction des attributs de la classe
	 */
	private void creerSource() {
		if (this.messageAleatoire) {

			if (this.aleatoireAvecGerme)
				this.source = new SourceAleatoire(nbBitsMess, seed);
			else
				this.source = new SourceAleatoire(nbBitsMess);

		} else
			this.source = new SourceFixe(this.messageString);
	}

	/**
	 * Cree les emetteurs, trasmetteurs, recepteurs analogiques en fonction des
	 * attributs de la classe. Puis connecte les elements.
	 */
	private void creerSystemAnalogique() {
		AFactoryEmetteurRecepteur factory = null;

		if (this.form == EnumForm.NRZ)
			factory = new FactoryNRZ();
		else if (this.form == EnumForm.NRZT)
			factory = new FactoryNRZT();
		else if (this.form == EnumForm.RZ)
			factory = new FactoryRZ();

		this.emetteur = factory.creerEmetteur(ne, min, max);
		this.recepteur = factory.creerRecepteur(detecteur, ne, min, max);

		if (this.transducteur) {

			this.transducteurEmission = new TransducteurEmission();
			this.transducteurReception = new TransducteurReception();

			this.source.connecter(transducteurEmission);
			this.transducteurEmission.connecter(emetteur);
			this.recepteur.connecter(transducteurReception);
			this.transducteurReception.connecter(destination);
		} else {
			this.source.connecter(emetteur);
			this.recepteur.connecter(destination);
		}

		if (this.systemeAnalogBruite)
			this.creerSystemeAnalogiqueBruite();
		else
			this.creerSystemeAnalogiqueParfait();

	}

	private void creerSystemeAnalogiqueParfait() {
		this.transmetteurAnalogiqueParfait = new TransmetteurAnalogiqueParfait();
		this.emetteur.connecter(this.transmetteurAnalogiqueParfait);
		this.transmetteurAnalogiqueParfait.connecter(recepteur);
	}

	private void creerSystemeAnalogiqueBruite() {
		/**
		 * On fait emettre la source pour que l emetteur convertisse le signal
		 * en analogique. Cela permet ensuite de generer le bruit en fonction du
		 * signal emis.
		 */
		this.source.emettre();

		if (this.applySnr) {
			if (this.seed == null)
				this.bruits.add(new BruitBlancGaussien(this.emetteur.getInformationEmise(), this.snr));
			else
				this.bruits.add(new BruitBlancGaussien(this.emetteur.getInformationEmise(), this.snr, this.seed));
		}
		this.transmetteurAnalogiqueBruite = new TransmetteurAnalogiqueBruite(this.bruits);
		this.emetteur.connecter(this.transmetteurAnalogiqueBruite);
		this.transmetteurAnalogiqueBruite.connecter(recepteur);
	}

	/**
	 * Cree le transmetteur logique en fonction des attributs de la classe. Puis
	 * connecte les elements.
	 */
	private void creerSystemLogique() {
		this.transmetteurLogique = new TransmetteurLogiqueParfait();

		this.source.connecter(transmetteurLogique);
		this.transmetteurLogique.connecter(destination);
	}

	/**
	 * Cree les sondes necessaires a l'affichage des informations sur un systeme
	 * analogique. Puis connecte les elements du systeme precedemment cree aux
	 * sondes.
	 */
	private void creerAffichageSystemeAnalogique() {
		Sonde<Boolean> sondeSource = new SondeLogique("Sonde source", 50);
		Sonde<Float> sondeEmetteur = new SondeAnalogique("Sonde emetteur");
		Sonde<Float> sondeTransmetteur = new SondeAnalogique("Sonde transmetteur");
		Sonde<Boolean> sondeRecepteur = new SondeLogique("Sonde recepteur", 50);

		this.source.connecter(sondeSource);
		this.emetteur.connecter(sondeEmetteur);
		this.recepteur.connecter(sondeRecepteur);
		if (this.transmetteurAnalogiqueBruite == null)
			this.transmetteurAnalogiqueParfait.connecter(sondeTransmetteur);
		else
			this.transmetteurAnalogiqueBruite.connecter(sondeTransmetteur);

		if (this.transducteur) {
			Sonde<Boolean> sondeTransEmission = new SondeLogique("Sonde transducteur emission", 50);
			Sonde<Boolean> sondeTransReception = new SondeLogique("Sonde transducteur reception", 50);

			this.transducteurEmission.connecter(sondeTransEmission);
			this.transducteurReception.connecter(sondeTransReception);
		}
	}

	/**
	 * Cree les sondes necessaires a l'affichage des informations sur un systeme
	 * logique. Puis connecte les elements du systeme precedemment cree aux
	 * sondes.
	 */
	private void creerAffichageSystemeLogique() {
		Sonde<Boolean> sondeEntree = new SondeLogique("Sonde entrée", 50);
		Sonde<Boolean> sondeSortie = new SondeLogique("Sonde sortie", 50);

		this.source.connecter(sondeEntree);
		this.transmetteurLogique.connecter(sondeSortie);
	}

	public float getTEB() {
		return TEB;
	}

	public void setTEB(float tEB) {
		TEB = tEB;
	}
}
