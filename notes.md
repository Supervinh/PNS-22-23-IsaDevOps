# ISA

### Git Branching :

#### Main

* Versions stables uniquement, ne lance que des tests end-2-ends

#### Dev

* Versions instables, lance des TI (Cucumber)

#### Feature

* Branches pour développer une feature spécifique, doit comporter des tests unitaires pour valider le fonctionnement
  métier

#### Bonnes Pratiques à respecter

* Toujours faire une PR, à valider par au moins deux personnes sur 5 avant de merge
* Pas de merge si les tests ne passent pas ou ne sont pas fait
* Pensez à utiliser [SonarQube]() pour vous assurer de la qualité de votre code
* Pour tout les DTO & Cli, utilisez un constructeur vide, créez les setters correspondants et créez au besoin une
  fonction init() dans l'objet avec les arguments nécessaires pour redefiner les attributs souhaités
* Pour les codes d'erreur, referez-vous
  à [REST API Design](https://drive.google.com/file/d/1Vv8m1Sub5WFFe2O1NEZPyP88C0muBpUY/view)
* Liez vos commits à des issues au maximum, normalement les seuls commits non liés sont ceux d'un merge
* Créez des issues si nécessaires et placer les dans les milestones associés (à creer au besoin)
* Commentez le code produit, l'intérêt étant d'expliquer pourquoi vous faites ça, et comment si la logique est
  particulierement complexe
* Évitez au maximum de sur complexifier les méthodes, préférez créer des fonctions intermediares ou des helpers
* ChatGPT et Copilot sont à utiliser avec parcimonie, et en les relisant

#### A savoir

* Sur les finder, pas d'exceptions → si mauvais credentials ou compte non existant, Optional.empty
* Chaque ville a un commerce pour pouvoir délivrer les recompenses liées a la commune (e.g Reduction Bus)
* Chaque commerce a un owner, un owner peut avoir plusieurs commerces

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

# Devops

### Jenkins :

Utiliser JGit dans la config globale  
ssh-keygen -t rsa -f jenkins_agent  
changer volume / clé ssh  
agent name should be identical to docker compose name (
container name)

### Webhooks : dans le repository Jenkins

sudo apt install npm sudo npm install --global smee-client screen -S smee smee --url https://smee.io/uTN3kiLqSU3wkZp
--path /github-webhook/ --port 8000

#### Screen :

screen -r smee  
screen -ls  
Ctrl+A d pour détacher  
Ctrl+A k pour tuer

Pour lancer Maven :
-Installer Maven Integration Plugin → installer maven dans global tools -> specifier dans le Jenkins file le nom de mvn

https://www.cloudbees.com/blog/how-to-install-and-run-jenkins-with-docker-compose   
http://vmpx02.polytech.unice.fr:8000/ (s'adresser à [JeannesTheo](https://github.com/JeannesTheo) pour avoir des ids)
