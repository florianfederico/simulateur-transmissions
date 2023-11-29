package visualisations;

import javax.swing.JFrame;

public class Vue extends JFrame {

	private static final long serialVersionUID = 1917L;

	protected static int xPosition = 0;
	protected static int yPosition = 0;
	private static int yDecalage = 200;

	public static int getXPosition() {
		xPosition += 0;
		return xPosition - 0;
	}

	public static int getYPosition() {
		yPosition += yDecalage;
		return yPosition - yDecalage;
	}

	public Vue(String nom) {
		super(nom);
	}

}
