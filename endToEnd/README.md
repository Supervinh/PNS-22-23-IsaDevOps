# Tests End-to-End tests

Ce dossier contient un environnement de tests end to end pour le projet.
Pour lancer les tests, il suffit de faire `./endToEnd.sh`.

## Utilisation

Les tests sont lancés dans un environnement docker, qui est défini dans le fichier `docker-compose.yml`.
Le [fichier `expected.txt`](expected.txt) contient le nom des scripts à exécuter, ainsi que les résultats attendus sous
forme de
Regex.

Le [fichier `testRegex.sh`](testRegex.sh) permet de tester les expressions régulières utilisées dans les tests.

Le [fichier `beforeAll.txt`](beforeAll.txt) est lancé avant le premier test et permet de vérifier que des instances
n'existent pas dans
la base de données, en spécifiant d'abord la table, puis une liste d'attributs.\
Ce fichier permet également de lancer des commandes dans la cli, pour peupler la base de données, dans la
section `Init`.

Le [fichier `afterAll.txt`](afterAll.txt) est lancé après le dernier test et permet de lancer des commandes dans la cli.
Il peut
notamment être utilisé pour supprimer les données créées dans la base de données pendant les tests. Cette étape sera
exécutée même si certains tests échouent.

Vous pouvez vérifier les actions de [`beforeAll.txt`](beforeAll.txt) et [`afterAll.txt`](afterAll.txt)  en consultant
les fichiers de logs, respectivement
[`beforeAll.log`](beforeAll.log) et [`afterAll.log`](afterAll.log).

Il existe un mode `démo`, qui peut être activé avec `--demo` ou `-d`. Ce mode permet de lancer les tests, en attendant
une entrée de l'utilisateur, entre chaque test,
pour permettre un temps d'explication entre deux scénarios. Ce mode cache également les logs de lancement du projet, et
affiche simplement un message au début et à la fin du lancement, pour informer l'utilisateur.

## Troubleshooting

### TL:DR

+ Vérifier que les fichiers `beforeAll.txt` et `afterAll.txt` existent
+ Vérifier les tests dans le fichier `expected.txt`
+ Vérifier que docker-compose est en train de tourner et fonctionne normalement

### Les tests ne passent toujours pas

Vérifier dans un premier temps que le nom des scripts dans le fichier `expected.txt` est correct et correspond à un
script existant dans le dossier cli/scripts
Le fichier `./testRegex.sh` permet de tester les expressions régulières utilisées dans les tests, en cas de doutes sur
la syntaxe. Les accolades et caractères spéciaux doivent généralement être échappées avec un backslash. Le
site [Regexr](https://regexr.com/) peut vous aider à écrire le bon regex.

## Examples

### Expected.txt

```txt
buySomething : CliPurchase\{id=.* , storeName='abcdaire', internalAccount=false\}
parking : SomeTest\{id=.*, matriculation=[0-9]+, internalAccount=false\}
```

La première ligne exécute le script `buySomething.txt` et vérifie que le dernier résultat de ce script correspond à
l'expression régulière `CliPurchase\{id=.* , storeName='abcdaire', internalAccount=false\}`

La deuxième ligne exécute le script `parking.txt` et vérifie que le dernier résultat de ce script correspond
à `SomeTest\{id=.*, matriculation=[0-9]+, internalAccount=false\}`.
Par exemple, `SomeTest{id=1, matriculation=123456, internalAccount=false}`
et `SomeTest{id=46, matriculation=8465, internalAccount=false}` sont des résultats valides.

### BeforeAll.txt

```txt
admin
    -name: b
    -mail: a@a
customer
    -name: a
    -mail: a@a
    -balance: 100
Init
script buySomething.txt
logout
```

Ce fichier va d'abord vérifier qu'il n'existe pas d'instance de `admin` dans la base de données avec les
attributs `name=b` et `mail=a@a`.
Il va ensuite vérifier qu'il n'existe pas d'instance de `customer` dans la base de données avec les
attributs `name=a`, `mail=a@a` et `balance=100`. \
\
Le mot clé `Init` permet de lancer des commandes dans la cli. Il va donc lancer la commande `script buySomething.txt`,
puis la commande `logout`.

### AfterAll.txt

```txt
logout
login-customer refill@customer pwd
delete-customer
```

Ce fichier fonctionne simplement comme un script standard dans la cli et exécute chacune des commandes fournies dans la
CLI. Ici, il va donc se connecter en tant que `refill@customer` et supprimer son compte.

## Fonctionnement

Ce système fonctionne grâce à la commande `socat` qui permet de rediriger l'entrée standard d'un programme vers la CLI.
Le système commence par lancer les conteneurs contenus dans le docker compose, puis exécute le before All. Toutes les
commandes `socat` ont un délai de 5 secondes, pour attendre que la CLI ait reçu toutes les informations attendues. Ce
système n'est pas parfait, l'idéal serait de pouvoir détecter lorsque la CLI a fini de traiter une commande.

Le before All va alors vérifier que les objets indiqués n'existent pas en base de données, en utilisant la
commande `socat`
pour exécuter des `SELECT` sur la base de données au travers du conteneur `postgres`, puis en vérifiant que le résultat
de la requête ne contient pas de lignes.

Ensuite, le before All va exécuter les commandes après le mot clé `Init` dans la CLI, qui permet de peupler la base de
données.

Le système va ensuite exécuter les tests, en lançant chaque script indiqué dans le fichier `expected.txt`, puis en
comparant le résultat avec celui attendu, en utilisant des expressions régulières.

À la fin, peu importe le résultat des tests, le fichier `afterAll.txt` lance ses commandes dans la CLI, puis les
conteneurs sont arrêtés.


