# Comment installer et lancer les outils DevOps

Ce document permet de se connecter à la VM et de relancer les différents composants de la chaine DevOps.
Il permet également de relever les différents points important lors de la configuration et de l'installation des outils.

## Table des matières

+ [Relancer la chaine DevOps](#Relancer-la-chaine-DevOps)
+ [Installation](#Installation)
    + [Jenkins & SonarQube](#Jenkins-et-SonarQube)
    + [Webhook](#Webhook)
    + [Artifactory](#Artifactory)
    + [Screen](#Screen)
+ [Troubleshooting](#Troubleshooting)
    + [Ports](#Ports)
    + [Screen](#Screen)
    + [Docker Compose](#Docker-Compose)
    + [Divers](#Divers)
+ [Liens utiles](#Liens-utiles)

## Relancer la chaine DevOps

Se connecter à la machine virtuelle :

* vpn : (ids unice) e.g username@etu.polytech:mdpENT
* ssh : sftp://teamb@vmpx02.polytech.unice.fr/

Une fois dans la vm, une commande :

```shell
sudo ./launch-all.sh
```

Ce script lance le docker compose dans un ecran uniquement accesible par root. Il faut donc utiliser sudo pour le voir
et s'y rattacher

Si smee tourne toujours, et qu'il ne faut seulement changer l'un des conteneurs :

```shell
./quick-start.sh
```

## Installation

Tous les fichiers de configuration nécessaires sont dans ce dossier. Il faut commencer par copier le dossier DevOps dans
la machine virtuelle, s'ils ne sont pas présents. Une fois toutes les installations faites correctement, en se plaçant a
la racine :

```shell
chmod +x quick-start.sh
chmod +x lauch-all.sh
sudo ./launch-all.sh
```

### Jenkins et SonarQube

En se plaçant dans le dossier Jenkins :

+ Pour configurer correctement
  Jenkins, [c'est ici](https://www.cloudbees.com/blog/how-to-install-and-run-jenkins-with-docker-compose)
+ Il faut utiliser JGit dans la config globale pour éviter les problèmes avec ca-certificates
+ Pour générer la clé ssh :

```shell
ssh-keygen -t rsa -f jenkins_agent
```

+ Penser à changer le volume et la clé ssh dans le [docker-compose.yml](jenkins/docker-compose.yml)
+ Le nom de l'agent doit être identique au nom du conteneur dans le docker compose :
  + Le dockerfile dans le dossier [jenkins](jenkins/Dockerfile) permet de construire une image
    qui se base sur un ssh-agent avec le jdk 17 et ajoute maven, docker et docker-compose.
+ Pour construire l'agent :

```shell
  chmod +x build.sh
 ./build.sh
 ```

### Webhook

En se plaçant dans le dossier jenkins de la vm :

```shell
sudo apt install npm  
sudo npm install --global smee-client
```

### Artifactory

En se plaçant dans le dossier artifactory de la vm :

+ Télécharger
  et [extraire l'archive](https://releases.jfrog.io/artifactory/bintray-artifactory/org/artifactory/oss/docker/jfrog-artifactory-oss/[RELEASE]/jfrog-artifactory-oss-[RELEASE]-compose.tar.gz)
+ Lancer la config : (Base de données : Derby)

```shell
chmod +x config.sh
sudo ./config.sh
```

+ Changer le port dans le [.env](artifactory-oss-7.49.8/.env) à 8002
+ Modifier le docker-compose.yaml avec le [modèle](artifactory-oss-7.49.8/docker-compose.yaml) dans le dossier
  artifactory
  + Map le port 8081 vers 8003
  + Ajoute artifactory au réseau jenkins_default

## TroubleShooting

### Ports

Format : PortExposé:PortInterne

+ Jenkins : 8000:8080
+ SonarQube : 8001:9000
+ Artifactory-UI : 8002:8002
+ Artifactory-config : 8003:8081

### Screen

+ Créer un screen :          screen -S <name>
+ Se rattacher :             screen -r <name>
+ Lister les screen actifs : screen -ls
+ Détacher un screen :       Ctrl+A d pour détacher
+ Tuer un screen :           Ctrl+A k pour tuer

### Docker Compose

Tous les conteneurs i.e Jenkins, JenkinsAgent, SonarQube, Artifactory sont sur le même réseau.
Pour y accéder, l'adresse depuis Jenkins e.g dans un JenkinsFile est : < nomDuContainer >:< PortInterne >

### Divers

L'image de l'agent embarque maven, docker et docker compose. Le socket docker est partagé avec l'agent Jenkins (Mauvaise
Pratique à changer).
Cela permet d'utiliser mvn et docker directement dans un JenkinsFile

Droits pour y accéder depuis l'agent Jenkins : chmod 666 /var/run/docker.sock  
Pour remettre à l'état d'origine : chmod 660

## Liens utiles

Jenkins & Agent : https://www.cloudbees.com/blog/how-to-install-and-run-jenkins-with-docker-compose
Install
Artifactory : https://www.jfrog.com/confluence/display/JFROG/Installing+Artifactory#InstallingArtifactory-DockerComposeInstallation  
Source Artifactory : https://jfrog.com/community/download-artifactory-oss/  
Config Maven Artifactory : https://www.jfrog.com/confluence/display/JFROG/Maven+Repository  
Maven3.8.7 workaround for https : https://gist.github.com/vegaasen/1d545aafeda867fcb48ae3f6cd8fd7c7  
Set Up smee : https://www.jenkins.io/blog/2019/01/07/webhook-firewalls/  
Install Docker : https://docs.docker.com/engine/install/ubuntu/