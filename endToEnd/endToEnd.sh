#!/bin/bash
source beforeAll.sh

function afterAll() {
  if [ ! -f "afterAll.txt" ]; then
    echo "afterAll not found, no clean up performed"
    touch afterAll.txt
  else
    while IFS="" read -r ligne || [ -n "$ligne" ]; do
      echo "$ligne" | socat -t5 EXEC:"docker attach cli",pty STDIO >>afterAll.log #Send command to the cli
    done <"afterAll.txt"
    printf "\n---------------------------------------" >>afterAll.log
  fi
}

function test_script() {
  file=$(echo "$1" | cut -d " " -f 1)
  # shellcheck disable=SC2086
  expected=$(echo $1 | cut -d " " -f3-)
  # Exécute la commande pour tester le fichier
  echo "script ${file}.txt" | socat -t5 EXEC:"docker attach cli",pty STDIO >res.txt
  # Récupère la dernière ligne du fichier de résultat
  actual=$(tail -n 2 res.txt | head -n 1)
  # Vérifie si le résultat obtenu correspond au résultat attendu
  if [[ "$actual" =~ $expected ]]; then
    echo "$file succeeded"
    return 0
  else
    echo "--> Expected: $expected"
    echo "--> Actual: $actual"
    echo "$file failed"
    return 1
  fi
}

demo=false #if true, pause between each test
for arg in "$@"; do
  if [ "$arg" = "-d" ] || [ "$arg" = "--demo" ]; then
    demo=true
  fi
done

cd ..
if [ "$demo" = true ]; then
  clear
  echo "Building the project..."
  ./build-all.sh -d &>/dev/null
  echo "Project built, we can now test"
else
  ./build-all.sh -d
fi
cd endToEnd || exit
sleep 10

beforeAll #Do the beforeAll check

# Récupère le nom du fichier d'entrée
fichier="expected.txt"
# Vérifie si le fichier existe
if [ ! -f "$fichier" ]; then
  echo "$fichier not found"
  exit 1
fi
res=true
# Parcourt le fichier ligne par ligne
while IFS="" read -r ligne || [ -n "$ligne" ]; do
  #while read ligne; do
  # Récupère le nom du fichier à tester et le résultat attendu

  if ! test_script "$ligne"; then
    res=false
  fi
  if [ "$demo" = true ]; then
    read -r </dev/tty
  fi
done <"$fichier"

if [ "$res" = false ]; then
  echo "Some tests are failing"
  exit 2
fi
echo "Everything was tested successfully"

afterAll
exit 0
