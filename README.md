TeamB-2023 - Carte Multi Fidélité
===

## Introduction

Ce projet a pour but de mettre en place un système de carte multi-fidélité dans le cadre du cours d'ISA/DevOps avancé.
Ce projet inclut une chaine DevOps incluant Jenkins pour automatiser les tests, ainsi que Sonarqube et Jenkins.
Les détails sur le fonctionnement sont dans le dossier [devops](devops).

Projet développé par :

+ [Bevan Tom](https://github.com/TomBevanIUT)
+ [Bourdeau Quentin](https://github.com/QuentinBourdeau)
+ [Correia Ambre](https://github.com/AmbreCorreia)
+ [Faucher Vinh](https://github.com/Supervinh)
+ [Jeannes Théo](https://github.com/JeannesTheo)

## Git Branching

### Main

* Version stable uniquement, ne lance que les tests end-2-ends

### Dev

* Versions instables, lance des tests d'intégration avec Cucumber

### Feature

* Branches pour développer une fonctionnalité spécifique, doit comporter des tests unitaires pour valider le
  fonctionnement métier

## Bonnes Pratiques à respecter

* Toujours faire une PR, à valider par au moins deux personnes (non incluant l'auteur) sur cinq avant de merge
* Pas de merge si les tests ne passent pas ou ne sont pas fait
* Pensez à utiliser [SonarQube](http://vmpx02.polytech.unice.fr:8001/) pour vous assurer de la qualité de votre code
* Pour tous les DTO & Cli, utilisez un constructeur vide, créez les setters correspondants et créez au besoin une
  fonction init() dans l'objet avec les arguments nécessaires pour redefiner les attributs souhaités
* Pour les codes d'erreur, referez-vous
  à [REST API Design](https://drive.google.com/file/d/1Vv8m1Sub5WFFe2O1NEZPyP88C0muBpUY/view)
* Liez vos commits à des issues au maximum, normalement les seuls commits non liés sont ceux d'un merge
* Créez des issues si nécessaires et placer les dans les milestones associés (à créer au besoin)
* Commentez le code produit, l'intérêt étant d'expliquer pourquoi vous faites ça, et comment si la logique est
  particulièrement complexe
* Évitez au maximum de-sur complexifier les méthodes, préférez créer des fonctions intermédiaires ou des helpers
* ChatGPT et Copilot sont à utiliser avec parcimonie, et en les relisant

### A savoir

* Sur les finder, pas d'exceptions. En cas d'informations de connexions erronées, ou si le compte n'existe pas, il faut
  renvoyer Optional.empty
* Chaque ville a un commerce pour pouvoir délivrer les recompenses liées à la commune (e.g Reduction Bus)
* Chaque commerce a un owner, un owner peut avoir plusieurs commerces
* Une seule personne est connectée à la fois dans la CLI
* La connexion n'est pas automatique après l'inscription

## Scénarios

+ Connection Customer
+ Connection StoreOwner
+ Connection Admin
+ Log out

+ StoreOwner ajoute une payoff
+ StoreOwner récupère son volume de ventes, et les couts associés
+ StoreOwner ajoute un magasin
+ StoreOwner modifie les horaires d'un magasin
+ StoreOwner modifie une payoff
+ StoreOwner supprime une payoff
+ StoreOwner ajoute un achat d'un client (Update VFP)
+ StoreOwner ajoute un achat d'un client qui paye avec son solde
+ StoreOwner supprime un magasin
+ StoreOwner supprime son compte
+ StoreOwner modifie son compte

+ Le client claim une payoff
+ Le client claim Parking
+ Le client recharge son compte
+ Le client consulte le catalogue
+ Le client recherche dans le catalogue
+ Le client crée un compte
+ Le client modifie ses informations (Immatriculation, carte bancaire)
+ Le client ajoute un magasin à ses favoris
+ Le client retire un magasin de ses favoris
+ Le client consulte les horaires de ses magasins favoris
+ Le client répond à un sondage
+ Le client supprime son compte

+ Admin ajoute un storeOwner
+ Admin enregistre un admin
+ Admin lance un sondage
+ Admin supprime son compte
