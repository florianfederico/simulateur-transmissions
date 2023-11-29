package emetteurRecepteur.form.src;

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
 *         Definit les methodes et attributs communs aux emetteurs et
 *         recepteurs. Les classes filles de AForm definissent les types des
 *         emetteurs recepteurs.
 * 
 */
public abstract class AForm {

	/**
	 * Le nombre d'echantillons a generer par bit
	 */
	private final int ne;

	/**
	 * L'amplitude minimum
	 */
	private final float aMin;

	/**
	 * L'amplitude maximum
	 */
	private final float aMax;

	public AForm() {
		this.ne = this.corrigerNbEchantillons(30);
		this.aMin = 0f;
		this.aMax = 1f;

	}

	/**
	 * @param ne
	 *            Le nombre d'echantillons par bit
	 * @param aMin
	 *            L'amplitude minimale
	 * @param aMax
	 *            L'amplitude maximale
	 */
	public AForm(int ne, float aMin, float aMax) {
		super();

		try {
			this.verifierParamConstructeur(ne, aMin, aMax);
			this.verifierNbEchantillons(ne);
		} catch (FormConstructeurException e) {
			System.out.println(e.getMessage());
			ne = this.corrigerNbEchantillons(30);
			aMin = 0f;
			aMax = 1f;
		} catch (NbEchantillonException e) {
			boolean drap = false;
			if (ne < 3)
				drap = true;

			ne = this.corrigerNbEchantillons(ne);
			if (drap)
				System.out.println("Le nombre d echantillons a ete augmente a " + ne);
		}

		this.ne = ne;
		this.aMin = aMin;
		this.aMax = aMax;

	}

	/**
	 * Convertisseur Numerique Analogique
	 * 
	 * @param infoNumerique
	 *            L'information numerique
	 * @return L'information analogique
	 */
	public abstract Information<Float> CNA(Information<Boolean> infoNumerique);

	/**
	 * Renvoie le symbole parfait
	 * 
	 * @param bit
	 *            L'etat du symbole
	 * @return Le symbole
	 */
	public abstract Information<Float> getSymboleParfait(boolean bit);

	/**
	 * Calcule le nombre d'echantillons par bits.
	 * 
	 * @param ne
	 *            Le nombre d'echantillons
	 * @return Le nombre d'echantillons corrigé
	 */
	protected abstract int corrigerNbEchantillons(int ne);

	/**
	 * Calcule le pas a ajouter pour aller de aMin a aMax
	 * 
	 * @return Le pas
	 */
	protected abstract float calculerPas();

	/**
	 * /**
	 * 
	 * @param ne
	 *            Le nombre d'echantillons par bit
	 * @param aMin
	 *            L'amplitude minimale
	 * @param aMax
	 *            L'amplitude maximale
	 *
	 * @throws FormConstructeurException
	 */
	private void verifierParamConstructeur(int ne, float aMin, float aMax) throws FormConstructeurException {
		if (ne <= 0)
			throw new FormConstructeurException(
					"Emetteur constructeur Exception: ne <= 0, valeurs par defaut appliquees");
		else if (aMax <= aMin)
			throw new FormConstructeurException(
					"Emetteur constructeur Exception: aMax <= aMin, valeurs par defaut appliquees");

	}

	/**
	 * Controle le nombre d'echantillons par bits.
	 * 
	 * @param ne
	 *            Le nombre d'echantillons
	 */
	private void verifierNbEchantillons(int ne) throws NbEchantillonException {
		if (ne != this.corrigerNbEchantillons(ne))
			throw new NbEchantillonException();
	}

	public int getNe() {
		return ne;
	}

	public float getaMin() {
		return aMin;
	}

	public float getaMax() {
		return aMax;
	}

}
