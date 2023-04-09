# Plan de Reprise d'Activité - Groupe B

Ce document permet de se connecter à la VM et de relancer les différents composants de la chaîne DevOps.
Il permet également de relever les différents points importants lors de la configuration et de l'installation des
outils.

## Table des matières

* [Problèmes et Solutions](#problèmes-et-solutions)
  * [Git n'est pas disponible](#git-nest-pas-disponible)
  * [Docker Hub n'est plus utilisable](#docker-hub-nest-plus-utilisable)
  * [Smee n'est plus disponible](#smee-nest-plus-disponible)
  * [La VM ne redémarre plus](#la-vm-ne-redémarre-plus)
  * [La VM redémarre et tous les volumes sont perdus](#la-vm-redémarre-et-tous-les-volumes-sont-perdus)
  * [Les versions utilisées dans l'agent Jenkins ne sont plus disponibles](#les-versions-utilisées-dans-lagent-jenkins-ne-sont-plus-disponibles)
  * [La VM redémarre](#la-vm-redémarre)
  * [Artifactory, Jenkins ou SonarQube ne sont plus disponibles](#artifactory-jenkins-ou-sonarqube-ne-sont-plus-disponibles)
* [Relancer la chaîne DevOps](#relancer-la-chaîne-devops)
* [Installation depuis zéro](#installation-depuis-zéro)
  * [Jenkins et SonarQube](#jenkins-et-sonarqube)
  * [Webhook](#webhook)
  * [Artifactory](#artifactory)
* [TroubleShooting](#troubleshooting)
  * [Ports](#ports)
  * [Screen](#screen)
  * [Docker Compose](#docker-compose)
  * [Divers](#divers)
* [Liens utiles](#liens-utiles)

## Problèmes et Solutions

Ci-dessous, les problèmes les plus courants et leurs solutions sont listés, par ordre de gravité avec un temps estimé
pour résoudre chaque problème.

### Git n'est pas disponible

Temps estimé : 35 minutes

Si Git n'est plus disponible, il est nécessaire d'utiliser un autre outil de versioning et de collaboration, tel que
SourceForge. Il faut alors configurer les machines des utilisateurs pour push le code non plus sur GitHub,
mais sur le nouvel outil, et reconfigurer Smee pour transmettre les informations d'un webhook de l'outil choisi vers
Jenkins.

### Docker Hub n'est plus utilisable

Temps estimé : 30 minutes

Si Docker Hub n'est plus accessible, nous ne pouvons plus héberger les images du projet sur Docker Hub. Il faut alors
les héberger sur un autre site, comme GitBucket. Pour cela, la partie 'Deploy on docker'
du [Jenkinsfile](../Jenkinsfile) doit être modifiée.

Les images Jenkins, Jenkins-agent, et Artifactory sont également hébergées sur Docker Hub. Il faut alors les récupérer
par un autre moyen. Pour les images Jenkins et Jenkins Agent, il est possible de les trouver sur le site
de [Jenkins](https://www.jenkins.io/download/). Le site
d'[Artifactory](https://jfrog.com/community/download-artifactory-oss/) permet aussi de récupérer les images en cas de
problème.

### Smee n'est plus disponible

Temps estimé : 10 minutes

Si Smee n'est pas disponible, il faut héberger une instance Smee.io nous-même. La marche à suivre peut être trouvée sur
le [github de Smee](https://github.com/probot/smee.io). Il faut remplacer le lien par le nouveau webhook dans
le [fichier `launch-all.sh`](launch-all.sh), et reconfigurer le webhook
sur [GitHub](https://www.jenkins.io/blog/2019/01/07/webhook-firewalls/).

### La VM ne redémarre plus

Temps estimé : 10 à 45 minutes

Attention, s'il est nécessaire de réinstaller toute la chaîne, le temps de téléchargement des images doit être pris en
compte, et peut fluctuer selon le débit internet.
Si la VM n'est pas disponible, la chaîne DevOps peut être lancer sur une autre machine, ou en local pour des besoins de
démonstrations. Pour cela, il faut les suivre les instructions d'installation dans la
partie [Installation depuis Zéro](#installation-depuis-zéro).

### La VM redémarre et tous les volumes sont perdus

Temps estimé : 20 minutes

Si les volumes sont inutilisables après un redémarrage, il faudra recréer les identifiants dans Jenkins comme indiqué
dans la partie [Jenkins & SonarQube](#jenkins-et-sonarqube). Il faudra également recréer les identifiants des
utilisateurs pour Jenkins, Artifactory et SonarQube. Les artefacts stockés dans Artifactory seront perdus, il peut alors
être intéressant de sauvegarder les versions majeures des artefacts ailleurs, sur BitBucket par exemple.

### Les versions utilisées dans l'agent Jenkins ne sont plus disponibles

Temps estimé : 15 minutes

L'agent utilise des versions spécifiques de maven (3.8.8), docker (23.0.1) et docker-compose (2.16.0). Il est possible
que ces versions ne soient plus disponibles sur les dépôts. Il faut alors sauvegarder les versions utilisées en local,
pour les récupérer quand elles ne seront plus disponibles, ou être capable de changer les versions. Les
versions peuvent être changées dans le [Dockerfile](jenkins/Dockerfile) de l'agent Jenkins.

### La VM redémarre

Temps estimé : 3 minutes

En cas de redémarrage, si les volumes sont intacts, il suffit
de [relancer la chaîne DevOps](#relancer-la-chaîne-devops).

### Artifactory, Jenkins ou SonarQube ne sont plus disponibles

Temps estimé : 5 minutes

En cas d'indisponibilité de ces services, il faut d'abord vérifier la connexion au VPN. Si la connexion au VPN est
Active, il faut vérifier que la VM est toujours connectée. Le cas échéant, il faut vérifier que les conteneurs tournent.
Sinon, `screen -r docker` permet de s'y attacher,
et `docker compose -f ./artifactory-oss-7.49.8/docker-compose.yaml -f ./jenkins/docker-compose.yml logs` permet de
récupérer les logs pour trouver une éventuelle erreur. Pour relancer les conteneurs, les instructions sont
données [plus bas](#relancer-la-chaîne-devops).

## Relancer la chaîne DevOps

Se connecter à la machine virtuelle :

* vpn : (ids unice) e.g username@etu.polytech:mdpENT
* ssh : sftp://teamb@vmpx02.polytech.unice.fr/

Si Smee.io tourne toujours, et qu'il ne faut seulement que recharger l'un des conteneurs :

```shell
./quick-start.sh
```

Sinon, une commande pour relancer à la fois Smee.io et les conteneurs Artifactory, SonarQube et Jenkins :

```shell
sudo ./launch-all.sh
```

Ce script lance le docker compose dans un écran uniquement accessible par root. Il faut donc utiliser sudo pour le voir
et s'y rattacher

## Installation depuis zéro

Cette partie indique comment installer la chaîne DevOps, que ce soit sur une nouvelle machine, sur la VM si celle-ci est
réinitialisée, ou encore en local au besoin.

Tous les fichiers de configuration nécessaires sont dans ce dossier. Il faut commencer par copier le dossier DevOps dans
la machine virtuelle, s'ils ne sont pas présents. Une fois toutes les installations faites correctement, en se plaçant à
la racine :

```shell
chmod +x quick-start.sh
chmod +x lauch-all.sh
sudo ./launch-all.sh
```

Les images nécessaires et leurs versions sont ci-dessous :

| Image                                               | Version         |
|-----------------------------------------------------|-----------------|
| jenkins/jenkins                                     | 2.387.2-lts     |
| jenkins/ssh-agent                                   | jdk17           |
| releases-docker.jfrog.io<br/>/jfrog/artifactory-oss | 7.49.8          |
| sonarqube                                           | 8.9.1-community | 

### Jenkins et SonarQube

En se plaçant dans le dossier Jenkins :

```shell
cd jenkins
```

+ Pour configurer correctement
  Jenkins, [c'est ici](https://www.cloudbees.com/blog/how-to-install-and-run-jenkins-with-docker-compose)
+ Il est possible d'utiliser JGit pour éviter les problèmes avec `ca-certificates` si Jenkins ne parvient
  pas à se connecter avec Git.
+ Pour générer la clé ssh :

```shell
ssh-keygen -t rsa -f jenkins_agent
```

+ Penser à changer le volume et la clé ssh dans le [docker-compose.yml](jenkins/docker-compose.yml)
+ Le nom de l'agent doit être identique au nom du conteneur dans le docker compose :
  + Le dockerfile dans le dossier [jenkins](jenkins/Dockerfile) permet de construire une image
    qui se base sur un ssh-agent avec le jdk 17 et ajoute maven, docker, docker-compose, curl et socat.
+ Pour construire l'agent :

```shell
  chmod +x build.sh
 ./build.sh
 ```

Pour que les jobs Jenkins puissent s'exécuter correctement, ils ont besoin de plusieurs identifiants :

+ TeamB : Clé SSH privée générée ci-dessus. Le nom d'utilisateur est `jenkins`
+ Sonar : Texte secret qui correspond à un token SonarQube valide
+ Docker : Texte secret qui correspond à un token DockerHub valide pour le compte JeannesTheo
+ DiscordHook : Texte secret qui correspond à un webhook discord
+ Artifactory : Texte secret qui correspond à des identifiants artifactory, sous la forme `username:password`
+ GlobalGitIds : Nom d'utilisateur et mot de passe. Le nom d'utilisateur est celui d'un compte github, et le mot de
  passe
  est un token github associé au nom d'utilisateur, avec au moins les permissions liées à la catégorie Repo.

### Webhook

Les commandes suivantes permettent d'installer smee-client. Smee client est démarré par `./launch-all.sh`, il n'est pas
nécessaire de le lancer à l'installation.

```shell
sudo apt install npm  
sudo npm install --global smee-client
```

### Artifactory

En se plaçant dans le dossier artifactory de la vm :

+ Télécharger
  et [extraire l'archive](https://releases.jfrog.io/artifactory/bintray-artifactory/org/artifactory/oss/docker/jfrog-artifactory-oss/[RELEASE]/jfrog-artifactory-oss-[RELEASE]-compose.tar.gz)
+ Lancer le fichier de configuration. Il faut sélectionner la base de données Derby.

```shell
chmod +x config.sh
sudo ./config.sh
```

+ Changer le port dans le [.env](artifactory-oss-7.49.8/.env) à 8002
+ Modifier le docker-compose.yaml avec le [modèle](artifactory-oss-7.49.8/docker-compose.yaml) dans le dossier
  artifactory
  + Map le port 8081 vers 8003
  + Ajouter artifactory au réseau jenkins_default

Une fois artifactory installé, il faut modifier le [fichier `settings.xml`](../settings.xml) pour qu'il pointe vers
les bons repositories. Ceux-ci sont indiqué dans le fichier généré par Artifactory, par l'option `Set up` dans
Application -> Artifactory -> Artifacts. La partie `Deploy` du menu Set-Up doit remplacer la
partie `<distributionManagement>` ajoutée dans les pom.xml de la [CLI](../cli/pom.xml) et
du [serveur](../backend/pom.xml).

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

L'image de l'agent embarque entre autres maven, docker et docker compose. Le socket docker est partagé avec l'agent
Jenkins. C'est une mauvaise pratique, mais nous n'avons pas trouvé de solutions. Cela devrait être changé dès que
possible. Cela permet d'utiliser mvn et docker directement dans le JenkinsFile.\
\
Droits pour y accéder depuis l'agent Jenkins : `sudo chmod 666 /var/run/docker.sock`  \
Pour remettre à l'état d'origine : `sudo chmod 660 /var/run/docker.sock`

## Liens utiles

Jenkins & Agent : https://www.cloudbees.com/blog/how-to-install-and-run-jenkins-with-docker-compose \
Install
Artifactory : https://www.jfrog.com/confluence/display/JFROG/Installing+Artifactory#InstallingArtifactory-DockerComposeInstallation \
Source Artifactory : https://jfrog.com/community/download-artifactory-oss/  
Config Maven Artifactory : https://www.jfrog.com/confluence/display/JFROG/Maven+Repository  
Maven3.8.7 workaround for https : https://gist.github.com/vegaasen/1d545aafeda867fcb48ae3f6cd8fd7c7  
Set Up smee : https://www.jenkins.io/blog/2019/01/07/webhook-firewalls/  
Install Docker : https://docs.docker.com/engine/install/ubuntu/

