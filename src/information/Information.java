package information;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * 
 * @author prou
 */
public class Information<T> implements Iterable<T> {

	private ArrayList<T> content;

	/**
	 * pour construire une information vide
	 */
	public Information() {
		this.content = new ArrayList<T>();
	}

	/**
	 * pour construire à partir d'un tableau de T une information
	 * 
	 * @param content
	 *            le tableau d'éléments pour initialiser l'information
	 *            construite
	 */
	public Information(T[] content) {
		this.content = new ArrayList<T>();
		for (int i = 0; i < content.length; i++) {
			this.content.add(content[i]);
		}
	}

	/**
	 * pour connaître le nombre d'éléments d'une information
	 * 
	 * @return le nombre d'éléments de l'information
	 */
	public int nbElements() {
		return this.content.size();
	}

	/**
	 * pour renvoyer un élément d'une information
	 * 
	 * @param i
	 *            L'indice de l'element
	 * @return le ieme élément de l'information
	 */
	public T iemeElement(int i) {
		return this.content.get(i);
	}

	/**
	 * pour modifier le ième élément d'une information
	 *
	 * @param i
	 *            L'indice de l'element
	 * @param v
	 *            L'element modifie
	 */
	public void setIemeElement(int i, T v) {
		this.content.set(i, v);
	}

	/**
	 * pour ajouter un élément à la fin de l'information
	 * 
	 * @param valeur
	 *            l'élément à rajouter
	 */
	public void add(T valeur) {
		this.content.add(valeur);
	}

	/**
	 * Pour ajouter un tableau d'elemeent a la fin de l'information
	 * 
	 * @param tab
	 *            Le tableau d'elements a ajouter
	 */
	public void add(Information<T> tab) {
		for (int i = 0; i < tab.nbElements(); i++)
			this.content.add(tab.iemeElement(i));
	}

	/**
	 * pour comparer l'information courante avec une autre information
	 * 
	 * @param o
	 *            l'information avec laquelle se comparer
	 * @return "true" si les 2 informations contiennent les mêmes éléments
	 *         aux mêmes places; "false" dans les autres cas
	 */
	@SuppressWarnings("unchecked")
	public boolean equals(Object o) {
		if (!(o instanceof Information))
			return false;
		Information<T> information = (Information<T>) o;
		if (this.nbElements() != information.nbElements())
			return false;
		for (int i = 0; i < this.nbElements(); i++) {
			if (!this.iemeElement(i).equals(information.iemeElement(i)))
				return false;
		}
		return true;
	}

	/**
	 * pour afficher une information
	 */
	public String toString() {
		String s = "";
		for (int i = 0; i < this.nbElements(); i++) {
			s += " " + this.iemeElement(i);
		}
		return s;
	}

	/**
	 * pour utilisation du "for each"
	 */
	public Iterator<T> iterator() {
		return content.iterator();
	}
	
	public static Information<Float> sum(final Information<Float> info1, final Information<Float> info2) {
		Information<Float> res = new Information<Float>();
		
		int i=0;
		for(i=0; i < info1.nbElements() && i < info2.nbElements(); i++) {
			res.add(info1.iemeElement(i) + info2.iemeElement(i));
		}
		while(i < info1.nbElements()) {
			res.add(info1.iemeElement(i));
			i++;
		}
		while(i < info2.nbElements()) {
			res.add(info2.iemeElement(i));
			i++;
		}
		
		return res;
	}
}
