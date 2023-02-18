# Devops

ssh-keygen -t rsa -f jenkins_agent
changer volume / clé ssh
agent name should be identical to docker compose name (container name)

dossier jenkins --> docker-compose
ssh-keygen -t rsa -f jenkins_agent
changer volume local / clé ssh

sudo apt install npm
sudo npm install --global smee-client

Webhooks : dans le rep Jenkins
screen -S smee
smee --url https://smee.io/uTN3kiLqSU3wkZp --path /github-webhook/ --port 8000
Ctrl+A d

Pour rattacher/arreter :
screen -r smee

Jenkins :
Utiliser JGit dans la config globale
-Dorg.jenkinsci.plugins.gitclient.GitClient.untrustedSSL=true

Pour lancer Maven :
-Installer Maven Integration Plugin
-installer maven dans les globals tools
-specifier dans le jenkinsfile le nom de mvn

https://www.cloudbees.com/blog/how-to-install-and-run-jenkins-with-docker-compose
http://vmpx02.polytech.unice.fr:8000/ (s'adresser a moi pour avoir des ids :p )

sudo apt install npm
sudo npm install --global smee-client

Webhooks : dans le rep Jenkins
screen -S smee
smee --url https://smee.io/uTN3kiLqSU3wkZp --path /github-webhook/ --port 8000
Ctrl+A d

Pour rattacher/arreter :
screen -r smee

Jenkins :
Utiliser JGit dans la config globale

# ISA

    Sur les finder, pas d'exception -> si mauvais credentials ou compte non existant, Optional.empty
    PayOff -> Un commerce par ville
    Chaque commerce a un owner, un owner peut avoir plusieurs commerces

# Scenarios

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