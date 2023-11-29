package test;
import org.junit.runner.JUnitCore;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import emetteurRecepteur.emetteur.test.EmetteurNRZTTest;
import emetteurRecepteur.emetteur.test.EmetteurNRZTest;
import emetteurRecepteur.emetteur.test.EmetteurRZTest;
import emetteurRecepteur.form.test.NRZTTest;
import emetteurRecepteur.form.test.NRZTest;
import emetteurRecepteur.form.test.RZTest;
import emetteurRecepteur.recepteur.test.RecepteurNRZTTest;
import emetteurRecepteur.recepteur.test.RecepteurNRZTest;
import emetteurRecepteur.recepteur.test.RecepteurRZTest;
import sources.test.SourceAleatoireTest;
import sources.test.SourceFixeTest;

@RunWith(Suite.class)
@SuiteClasses({	
		NRZTest.class,
		NRZTTest.class,
		RZTest.class,
		
		EmetteurNRZTest.class, 
		EmetteurNRZTTest.class,
		EmetteurRZTest.class,
		
		RecepteurNRZTest.class,
		RecepteurNRZTTest.class,
		RecepteurRZTest.class,
		
		SourceAleatoireTest.class,
		SourceFixeTest.class
		
		})
public class AllTests {

	public static void main(String[] args) {
		JUnitCore.main("test.AllTests");
	}
}
