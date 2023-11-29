/**
 * 
 */
package bruit;

import information.Information;

/**
 * @author Zettajump
 *
 */
public class BruitTrajetMultiple extends ABruit {

	/**
	 * Decalage temporel
	 */
	private int dt;

	/**
	 * Amplitude relative
	 */
	private float ar;

	public BruitTrajetMultiple() {
		this.dt = 0;
		this.ar = 0.0f;
	}

	public BruitTrajetMultiple(int dt, float ar) {
		super();
		this.dt = dt;
		this.ar = ar;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see transmetteurs.bruit.impl.ABruit#generer(information.Information)
	 */
	@Override
	public Information<Float> generer(Information<Float> info) {
		Information<Float> res = new Information<Float>();
		/**
		 * Decalage temporel s(t) = e(t-dt)
		 */
		for (int i = 0; i < this.dt; i++)
			res.add(0f);

		/**
		 * Application attenuation s(t) = e(t) + e(t)*ar
		 */
		for (int i = 0; i < info.nbElements(); i++)
			res.add(info.iemeElement(i) * this.ar);

		return res;
	}

}
