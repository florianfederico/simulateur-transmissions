#!/bin/bash

javadoc -quiet -d ./doc -sourcepath ./src/ -cp ./javacsv2.1/javacsv.jar -subpackages destinations emetteurRecepteur.emetteur.impl emetteurRecepteur.factory emetteurRecepteur.form.src emetteurRecepteur.recepteur.impl emetteurRecepteur.recepteur.detecteur.impl information sources.src transmetteurs bruit transducteur

