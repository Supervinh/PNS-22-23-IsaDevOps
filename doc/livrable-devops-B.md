# Chaîne DevOps : Groupe B

## Table des matières

* [Outils en place](#outils-en-place)
    * [Agent Jenkins](#agent-jenkins)
    * [Définitions des Pipelines](#définitions-des-pipelines)
        * [Git Branching Strategy](#git-branching-strategy)
        * [Pipeline : Feature & Dev](#pipeline--feature--dev)
        * [Pipeline : Pull Request from Feature to Dev & Pull Request from Dev to Main](#pipeline--pull-request-from-feature-to-dev--pull-request-from-dev-to-main)
        * [Pipeline : Main](#pipeline--main)
    * [Tests End-to-End](#tests-end-to-end)
* [Axes d'améliorations](#axe-daméliorations)
* [Plan de reprise d'activité](#plan-de-reprise-dactivité)
* [Identifiants et Accès](#identifiants-et-accès)
    * [Smee](#smee)
    * [Jenkins](#jenkins)
    * [SonarQube](#sonarqube)
    * [Artifactory](#artifactory)
    * [Docker Registry](#docker-registry)

## Outils en place

Notre chaîne DevOps est composée de plusieurs outils qui permettent d'automatiser les tests tout au long du projet.
Nous utilisons notamment Jenkins pour lancer des pipelines, ainsi que Smee.io pour informer Jenkins des commits sur le
répertoire GitHub. Nous intégrons également Artifactory, pour stocker différents artefacts, et SonarQube pour suivre la
qualité du code produit.

### Agent Jenkins

Nous avons mis en place un agent Jenkins qui intègre tous les outils nécessaires au déroulement des pipelines. Pour
cela, nous avons créé une [image Docker](../devops/jenkins/Dockerfile) qui
contient `maven`, `docker`, `docker-compose`, `curl` et `socat`. Nous avons ensuite créé un conteneur Docker à
partir de l'agent SSH fourni par Jenkins, et nous avons ajouté maven, docker et docker compose, socat ainsi que curl.
Les avantages de cette solution sont multiples. Pour commencer, puisque notre agent est un conteneur Docker, il est
possible de lancer les pipelines sans
faire d'installations supplémentaires. Cela nous permet de ne pas dépendre des plugins Jenkins. Un autre avantage réside
dans la reproductibilité de l'environnement.
Il nous suffit de reconstruire l'image Docker pour avoir un environnement identique sur une autre machine par exemple,
ce qui peut faciliter les procédures de
réinstallation. Le dernier avantage est la présence d'un cache. En effet, nous pouvons utiliser le dossier  `.m2` de
l'agent pour stocker les dépendances, et les images docker temporaires.

### Définitions des Pipelines

Nous n'utilisons qu'un seul [Jenkinsfile](../Jenkinsfile) pour lancer tous les pipelines, ce qui permet de faire évoluer
le comportement de chaque pipeline facilement. De plus, cela évite de dupliquer les étapes communes à plusieurs
pipelines. Pour comprendre la logique de
chaque pipeline, il est essentiel de comprendre notre stratégie de branches.

#### Git Branching Strategy

Nos branches sont organisées de la manière suivante :

+ `main` : branche principale, contenant le code de production
+ `dev/*` : contient le code d'une version en cours de développement, intègre plusieurs nouvelles fonctionnalités
+ `feature/*` : contenant le code d'une seule fonctionnalité en cours de développement \

Le nouveau code n'est donc normalement commit que sur les branches `feature/*`, et ensuite fusionné sur une
branche `dev/*`, puis sur la branche `main`, grâce à des pulls requests. Cela permet de tester différemment selon la
branche sur laquelle un commit est effectué.

Pour reconnaître la branche sur laquelle le commit est effectué, nous utilisons un Jenkinsfile scripted, ce qui nous
permet d'utiliser des expressions régulières, pour identifier la branche grâce aux variables d'environnement
CHANGE_BRANCH et BRANCH_NAME. La première action d'un Jenkinsfile est donc de déterminer la branche sur laquelle le
commit a été effectué, puis de récupérer le code de cette branche, grâce à un `checkout`.

#### Pipeline : Feature & Dev

Une branche `feature/*` sert à développer une seule fonctionnalité. Nous avons donc choisi de ne faire que des tests
unitaires de façon automatique sur cette branche.

Une branche `dev/*` sert à intégrer plusieurs fonctionnalités, qui ont déjà été testées unitairement. Nous avons donc
choisi de ne faire que des tests d'intégrations, puisque les fonctionnalités sont correctes individuellement, mais
peuvent poser un problème lorsqu'elles sont mises bout à bout.

Que ce soit une branche `feature/*` ou `dev/*`, si les tests échouent, le pipeline s'arrête. Sinon un artefact est créé
et stocké dans Artifactory, puis le code est envoyé sur SonarQube. Nous utilisons en mode consultatif seulement, afin de
pouvoir s'assurer de la qualité de notre code, d'identifier des problèmes éventuels, que ce soit au niveau de la
répartition des responsabilités, de la duplication de code, ou de potentiels bugs que nous n'aurions pas détectés.

#### Pipeline : Pull Request from Feature to Dev & Pull Request from Dev to Main

Une fois que le code d'une branche `feature/*` a été testé unitairement, et qu'il est prêt à être intégré dans une
branche `dev/*`, nous créons une pull request. Cette pull request déclenche un pipeline qui va lancer les tests
unitaires, puis les tests d'intégration. Cela permet de vérifier que les fonctionnalités sont correctes
individuellement, et qu'elles fonctionnent également lorsqu'elles sont intégrées avec les fonctionnalités sur une
branche `dev/*`. En cas de succès, la pull request peut donc être validée sans craindre de déstabiliser la
branche `dev/*`.

Pour une pull request d'une branche `dev/*` vers la branche `main`, nous lançons les tests d'intégrations, pour nous
assurer que les fonctionnalités fonctionnent correctement ensemble, puis les tests End-to-End, pour vérifier le système
au plus proche d'une utilisation réelle. Pour cela, nous utilisons notre système de tests End-to-End,
[détaillé ici](#tests-end-to-end). Lancer les tests unitaires n'est pas nécessaire, puisque les fonctionnalités ont
déjà été testées individuellement avant d'être intégrées dans la branche `dev/*`.

Dans les deux cas, si les tests échouent, le pipeline s'arrête. Sinon une notification discord est envoyée afin de
prévenir les gens qu'une nouvelle pull request requiert une validation.

#### Pipeline : Main

La branche `main` contient le code de production. Lorsqu'une pull request est fusionnée sur cette branche, le code a
déjà passé tous les tests, et est donc prêt à être déployé. Nous avons ainsi choisi de ne pas lancer de tests, mais de
déployer directement le code. Pour cela, nous récupérons sur artifactory la dernière version des artefacts, pour le
serveur et le client, puis nous construisons les images docker correspondantes. Nous déployons ensuite les images sur
Docker Hub en leur donnant la version de l'artefact récupéré sur Artifactory. Cela permet de versionner nos images, pour
garder disponible les versions précédentes, et de pouvoir facilement revenir en arrière en cas de problème. Nous
déployons également sur artifactory un fichier zip, généré par le [script `generateZip.sh`](../deploy.sh), qui contient
un [docker-compose.yml](../deploy/docker-compose.yaml) permettant de lancer simplement le projet à partir des images
docker, ainsi que le dossier [scripts](../cli/scripts) contenant les scripts de tests End-to-End, pour fournir des
exemples à un utilisateur.

### Tests End-to-End

Nous avons développé un système de tests End-to-End qui démarre les différentes images docker de notre projet, et qui
permet de comparer les résultats de plusieurs scripts avec les résultats attendus. Cela nous permet de lancer des tests
End-to-End directement dans nos pipelines Jenkins, puisque notre agent intègre docker compose, mais aussi la
commande `socat`, essentielle au fonctionnement de nos tests End-to-End. Pour plus de détails sur notre système de tests
End-to-End, une documentation est disponible dans le dossier [endToEnd](../endToEnd/README.md).

## Plan de reprise d'activité

Notre plan de reprise d'activité est disponible [ici](../devops/README.md), et contient notamment la marche à suivre
pour reconstruire la chaîne DevOps depuis zéro.

## Axe d'améliorations

Une premiere faiblesse de notre projet se situe au niveau de la gestion des identifiants. D'une part, le
fichier [settings.xml](../settings.xml) contient les identifiants de connexion à Artifactory en clair. D'autre part, le
Jenkinsfile utilise plusieurs identifiants qui sont interpolés dans des chaines de caracteres. Il serait interessant
de trouver une solution pour utiliser les identifiants sans les interpoler.

Un point d'amélioration réside dans la compilation de ressources inutiles. En effet, les services externes sont
re-compilés à chaque fois que le projet est lancé, alors qu'ils ne changent pas. Stocker les services externes dans des
artefacts Artifactory permettrait de ne pas recompiler les services externes à chaque fois.

Un dernier point d'amélioration concerne le changement de version, dans
le [docker-compose d'exemple](../deploy/docker-compose.yaml) et les deux pom.xml,
celui du [server](../backend/pom.xml) et celui de la [cli](../cli/pom.xml). Il serait intéressant de trouver une
solution pour automatiser ce changement de version, ou de ne pas avoir à modifier la valeur à trois endroits différents.

## Identifiants et Accès

### Smee

+ https://smee.io/uTN3kiLqSU3wkZp

### Jenkins

Accessible uniquement avec le VPN ou depuis le réseau de l'Université.

+ Lien : http://vmpx02.polytech.unice.fr:8000/
+ Login : admin
+ Password : yZvRLf3AG8BdS79w

### SonarQube

Accessible uniquement avec le VPN ou depuis le réseau de l'Université.

+ Lien : http://vmpx02.polytech.unice.fr:8001/
+ Login : admin
+ Password : yZvRLf3AG8BdS79w

### Artifactory

Accessible uniquement avec le VPN ou depuis le réseau de l'Université.

+ Lien : http://vmpx02.polytech.unice.fr:8002/
+ Login : admin
+ Password : @yZvRLf3AG8BdS79w

### Docker Registry

+ CLI : https://hub.docker.com/repository/docker/jeannestheo/mfc-spring-cli/general
+ Server : https://hub.docker.com/repository/docker/jeannestheo/mfc-spring-server/general
+ Bank : https://hub.docker.com/repository/docker/jeannestheo/mfc-bank-service/general
+ Parking : https://hub.docker.com/repository/docker/jeannestheo/mfc-parking-service/general


