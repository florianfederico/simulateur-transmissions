# Simulateur de système de transmissions
> Projet étudiant de développement Java

TELECOM Bretagne - Année 2016-2017

## Contexte
Dans le monde des télécommunications tel que nous le connaissons, des **signaux** sont émis
dans un environnement guidé (Fibre optique) ou libre (Faisceau hertzien). Ces signaux subissent
une atténuation et sont noyés dans divers types de **bruits**, ajoutés durant le trajet. Le signal en
réception peut ainsi être méconnaissable, et malgré les différents traitements mathématiques
existant à l’heure actuelle, il se peut qu’une partie ne soit plus exploitable. C’est ainsi que nos
récepteurs réalisent des erreurs de jugement, qui sont à l’origine de l’augmentation du **taux d’erreur
binaire (TEB)**.

L'objectif est de simuler le **taux d'erreur binaire** en fonction des **signaux** et des **bruits** appliqués avec un projet de 4 étudiants sur 1 mois. 

## Déroulement

Pour cela, les éléments modélisés sont : 
* Source : signal à émettre
* Codeur : ajoute de l'information redondante / passage en signal analogique
* Transmetteur : canal de transmission pouvant ajouter du bruit
* Décodeur : passage en signal numérique
* Destination : destinataire du signal d'origine

<p align="center">
  <img src="readme/01-modele.png" alt="" width="500" /> <br/>
  <i>Modèle général</i>
</p>


Le projet s'est déroulé en plusieurs étapes d'ajout de fonctionnalités.

### Etape 1 : signal numérique et transmetteur parfait
Réalisation d'un simple transmetteur parfait (aucun bruit) avec un signal numérique :
<p align="center">
  <img src="readme/etape1.png" alt="" width="500" /> <br/>
</p>

### Etape 2 : signaux analogiques 
Ajout d'un codeur/décodeur pour transmettre 3 types de signaux analogiques : RZ, NRZ, NRZT

### Etape 3 : transmetteur bruité
Ajout d'un bruit blanc gaussien via le transmetteur : le taux d'erreur binaire n'est plus à zéro

### Etape 4 : bruits complexes
Ajout de bruits réalistes : trajets multiples, dispersion chromatique...

### Etape 5 : codeur/décodeur pour atténuer le bruit
Ajout d'un codeur/décodeur visant à atténuer les effets du bruits et diminuer le taux d'erreur binaire

## Fonctionnalités

<p align="left">
  <img src="readme/01-jardin.png" alt="" width="400"/>
</p>

## Outils utilisés
Programmation
* Suivi de version : Git / BitBucket
* Tests unitaires : JUnit
* Designs pattern : Factory...


## Contributeurs

* Florian Federico : intégrateur
* Bastien : chef de projet
* Nathan : coordinateur
* Thibaud : responsable des livrables


## Configuration

1. Compilation des sources : compile.sh
2. Génération de la Javadoc : generateJavadoc.sh
3. Execution du programme : Simulateur.jar avec les paramètres voulus
4. Execution des tests: test.sh

## Licence

Ce projet est sous licence GPL v3.





