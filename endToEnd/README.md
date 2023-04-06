# Tests End-to-End tests

Ce dossier contient un environnement de tests end to end primitif pour le projet.
Pour lancer les tests, il suffit de faire `./endToEnd.sh`.

## Fonctionnement

Les tests sont lancés dans un environnement docker, qui est défini dans le fichier `docker-compose.yml`.
Le fichier `expected.txt` contient le nom des scripts à exécuter, ainsi que les résultats attendus sous forme de
Regex.\
\
Le fichier `testRegex.sh` permet de tester les expressions régulières utilisées dans les tests.\
\
Le fichier `beforeAll.txt` est lancé avant le premier test et permet de verifier que des instances n'existent pas dans
la base de données, en spécifiant d'abord la table, puis une liste d'attributs.\
Ce fichier permet également de lancer des commandes dans la cli, pour peupler la base de données, dans la
section `Init`. \
\
Le fichier `afterAll.txt` est lancé après le dernier test et permet de lancer des commandes dans la cli. Il peut
notamment être utilisé pour supprimer les données créées dans la base de données pendant les tests.
\
\
Vous pouvez verifier les actions de `beforeAll.txt` et `afterAll.txt` en consultant les fichiers de logs, respectivement
`beforeAll.log` et `afterAll.log`.

## Troubleshooting

### TL:DR

+ Verifier que les fichiers `beforeAll.txt` et `afterAll.txt` existent
+ Verifier les tests dans le fichier `expected.txt`
+ Verifier que docker-compose est en train de tourner et fonctionne normalement

### Les tests ne passent toujours pas

Verifier dans un premier temps que le nom des scripts dans le fichier `expected.txt` est correct et correspond à un
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

La premiere ligne execute le script `buySomething.txt` et vérifie que le dernier résultat de ce script correspond à
l'expression régulière `CliPurchase\{id=.* , storeName='abcdaire', internalAccount=false\}`

La deuxième ligne execute le script `parking.txt` et vérifie que le dernier résultat de ce script correspond
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

Ce fichier va d'abord verifier qu'il n'existe pas d'instance de `admin` dans la base de données avec les
attributs `name=b` et `mail=a@a`.
Il va ensuite verifier qu'il n'existe pas d'instance de `customer` dans la base de données avec les
attributs `name=a`, `mail=a@a` et `balance=100`. \
\
Le mot clé `Init` permet de lancer des commandes dans la cli. Il va donc lancer la commande `script buySomething.txt`,
puis la commande `logout`.

### AfterAll.txt

```txt
register-customer catalogCustomer catalog@customer pwd 5251896983
login-customer catalog@customer pwd
refill 50
available-catalog
explore-catalog test
logout
```

[//]: # (TODO modifier afterAll apres l'ajout des commandes delete)
Ce fichier fonctionne simplement comme un script standard dans la cli. Dans ce cas-là, il va executer chacune de ces
commandes dans la cli.