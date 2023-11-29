#!/bin/bash

## Execution du script JUnit
echo '############### Execution des tests JUnit ###############'
java -jar SimulateurTest.jar

########### Etape 1
echo "############### ETAPE 1 #################"
read -p 'Test Simulateur: emission de 100 bits aleatoires'
java -jar Simulateur.jar -s 
echo ""

read -p 'Test Simulateur: emission de 5 bits aleatoires'
java -jar Simulateur.jar -s -mess 5 
echo ""

read -p 'Test Simulateur: emission de 5 bits aleatoires avec seed=2'
java -jar Simulateur.jar -s -mess 5 -seed 2
echo ""

read -p 'Test Simulateur: emission de 5 bits aleatoires avec seed=2'
java -jar Simulateur.jar -s -mess 5 -seed 2
echo ""

read -p 'Test Simulateur: emission du message 01110001'
java -jar Simulateur.jar -s -mess 01110001
echo ""



########### Etape 2
echo "############### ETAPE 2 #################"

read -p 'Test Simulateur: emission du message 01110001 en NRZ ampl -5 10 nbEch 15'
java -jar Simulateur.jar -s -mess 01110001 -form NRZ -ampl -5 10 -nbEch 15
echo ""

read -p 'Test Simulateur: emission du message 01110001 en RZ ampl -5 10 nbEch 15'
java -jar Simulateur.jar -s -mess 01110001 -form RZ -ampl -5 10 -nbEch 15
echo ""

read -p 'Test Simulateur: emission du message 01110001 en NRZT ampl -5 10 nbEch 15'
java -jar Simulateur.jar -s -mess 01110001 -form NRZT -ampl -5 10 -nbEch 15
echo ""

read -p 'Test Simulateur: emission du message 01110001 en RZ par defaut'
java -jar Simulateur.jar -s -mess 01110001 -form
echo ""

read -p 'Test Simulateur: emission du message 01110001 en RZ par defaut avec -ampl -10 30'
java -jar Simulateur.jar -s -mess 01110001 -form -ampl -10 30
echo ""



########### Etape 3
echo "############### ETAPE 3 #################"

read -p 'Test Simulateur: emission du message 01110001 en NRZ -snr 0'
java -jar Simulateur.jar -s -mess 01110001 -form NRZ -snr 0
echo ""

read -p 'Test Simulateur: emission du message 01110001 en NRZ -snr -10'
java -jar Simulateur.jar -s -mess 01110001 -form NRZ -snr -10
echo ""

read -p 'Test Simulateur: emission du message 01110001 en NRZ -snr 10'
java -jar Simulateur.jar -s -mess 01110001 -form NRZ -snr 10
echo ""


########### Etape 4
echo "############### ETAPE 4 #################"

read -p 'Test Simulateur: emission du message 01110001 en NRZ -ti 60 1'
java -jar Simulateur.jar -s -mess 01110001 -form NRZ -ti 60 1
echo ""


########### Etape 5
echo "############### ETAPE 5 #################"

read -p 'Test Simulateur: emission du message -mess 10000 -form NRZ -snr -20 -seed 10'
java -jar Simulateur.jar -mess 10000 -form NRZ -snr -10 -seed 10
echo ""

read -p 'Test Simulateur: emission du message '
java -jar Simulateur.jar -mess 10000 -form NRZ -snr -10 -seed 10 -transducteur
echo ""








