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
* Pour tout les DTO & Cli, utilisez un constructeur vide, créez les setters correspondants et créez au besoin une
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

### Liens Utiles

+ Jenkins : http://vmpx02.polytech.unice.fr:8000
+ SonarQube : http://vmpx02.polytech.unice.fr:8001
+ Artifactory : http://vmpx02.polytech.unice.fr:8002
+ Kanban : https://github.com/orgs/pns-isa-devops/projects/10/

## Scenarios

*(Liste non exhaustive, à completer ou modifier dès que nécessaire)*

    L’administrateur enregistre un commerçant dans le système (CLI→AdminController→ StoreOwnerRegistry → StoreHandler → StoreRepository)(Création du magasin réussi dans StoreRepository → StoreHandler → StoreOwnerRegistry → StoreOwnerRepository pour creer le compte commercant)
    L’inscription d’un client (CLI → CustomerController → CustomerRegistry → CustomerRepository)
    Le magasin enregistre un achat d’un client qui utilise sa carte fidélité ( CLI →  TransactionController → TransactionHandler → PurchaseRegistry →  PurchaseRepository)
    Le magasin alimente le catalogue fidélité (CLI→ StoreController → CatalogRegistry →CatalogRepository  )
    Le client consulte le catalogue (CLI → CatalogController →CatalogRegistry →CatalogRepository)
    Le client utilise ses points pour acheter un avantage fidélité (CLI → PayOffController →  PayOffHandler →  PayOffPurchaseRegistry →  PayOffPurchaseRepository)
    
    Le magasin enregistre un achat du client qui paye avec son compte fidélité(CLI →  TransactionController →   PurchaseRegistry →  PurchaseRepository )
    Le client fait un paiement à la bank et recharge son compte (CLI →  CustomerController →  PaiementHandler →  BankProxy →  Bank)(retour positif de Bank →  BankProxy →  PaiementHandler →  CustomerRegistry qui modifie le solde du client)
    Le client claim une récompense (CLI → PayOffController →  PayOffHandler →  PayOffPurchaseRegistry →  PayOffPurchaseRepository)
    Le client claim la place de parking gratuite (CLI → PayOffController →  PayOffHandler →  ParkingHandler →  ParkingProxy →  ISawWhere ParkedLastSummer)(retour positif de ISawWhereYouParkedLastSummer → ParkingProxy →  ParkingHandler → PayOffHandler -> PayOffPurchaseRegistry →PayOffPurchaseRepository pour l’enregistrement de la transaction )
    Gestion de la notification de fin de stationnement/ 5 min avant la fin ( ISawWhereYouParkedLastSummer → ParkingProxy →  ParkingHandler →NotificationHandler)
    Le client crée un compte (CLI → CustomerController → CustomerRegistry →CustomerRepository)
    Le client consulte le catalogue (CLI → CatalogController →CatalogRegistry →CatalogRepository)
    Le client saisit sa plaque Immatriculation (CLI →  CustomerController → CustomerRegistry →CustomerRepository)
    Le client consulte un horaire (CLI → CustomerController →StoreHandler → StoreRepository)
    Le client ajoute un magasin à sa liste de magasins favoris (CLI → CustomerController → CustomerRegistry →CustomerRepository)
    Le magasin change ses horaires (CLI → StoreController →StoreHandler →StoreRepository) (Retour positif de StoreRepository →StoreHandler →NotificationHandler pour envoyer une notification aux clients associé au magasin)
    Récupérer les habitudes de consommation (Achats moyen par magasin pour un client / Coût des récompenses (CLI →AdminController  → DataGatherer → )
    Relancer les consommateurs (Perte Statut VFP), Sondage, Emails événementiels (CLI →  )
    Alimenter le catalogue cadeau (CLI →AdminController→ CatalogRegistry →CatalogRepository )
    Enregistrer un commerçant (CLI→AdminController→ StoreOwnerRegistry→StoreOwnerRepository)
    Connaître le volume de ventes / coûts associés  (CLI →StoreOwnerController → DataGatherer → )
    Comparer aux autres (Données anonymisé+agrégés seulement)  (CLI →StoreOwnerController → DataGatherer →  )
    Alimenter le catalogue cadeau  (CLI →StoreOwnerController -> CatalogRegistry →CatalogRepository  )
    Magasin se connecte
    Admin se connecte
    Le client se connecte
