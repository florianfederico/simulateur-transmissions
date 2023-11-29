#! /bin/bash

rm -rf ./bin/*
mkdir ./bin

javac -d ./bin/ -sourcepath ./src/ ./src/information/*
javac -d ./bin/ -sourcepath ./src/ ./src/destinations/*
javac -d ./bin/ -sourcepath ./src/ ./src/sources/src/*
javac -d ./bin/ -sourcepath ./src/ ./src/visualisations/*

javac -d ./bin/ -sourcepath ./src/ ./src/transmetteurs/Transmetteur.java

javac -d ./bin/ -sourcepath ./src/ ./src/emetteurRecepteur/form/src/*
javac -d ./bin/ -sourcepath ./src/ ./src/emetteurRecepteur/emetteur/impl/*

javac -d ./bin/ -sourcepath ./src/ ./src/emetteurRecepteur/recepteur/detecteur/impl/*
javac -d ./bin/ -sourcepath ./src/ ./src/emetteurRecepteur/recepteur/impl/*

javac -d ./bin/ -sourcepath ./src/ ./src/emetteurRecepteur/factory/*

javac -d ./bin/ -sourcepath ./src/ -cp ./javacsv2.1/javacsv.jar ./src/bruit/*
javac -d ./bin/ -sourcepath ./src/ -cp ./javacsv2.1/javacsv.jar ./src/transmetteurs/*
javac -d ./bin/ -sourcepath ./src/ -cp ./javacsv2.1/javacsv.jar ./src/*.java


echo "Manifest-Version: 1.0
Class-Path: .
Main-Class: Simulateur" > ./bin/manifest.mf

rm -f Simulateur.jar
jar cfm Simulateur.jar ./bin/manifest.mf -C ./bin/ .
chmod u+x Simulateur.jar

