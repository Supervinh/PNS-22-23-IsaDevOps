#!/bin/bash
./build-all-detach.sh
sleep 60
# Récupère le nom du fichier d'entrée
fichier="expected.txt"
# Vérifie si le fichier existe
if [ ! -f "$fichier" ]; then
  echo "$fichier not found"
  exit 1
fi
res=true
# Parcourt le fichier ligne par ligne
while IFS="" read -r ligne || [ -n "$ligne" ]
do
#while read ligne; do
  # Récupère le nom du fichier à tester et le résultat attendu
  file=$(echo "$ligne" | cut -d " " -f 1)
  # shellcheck disable=SC2086
  expected=$(echo $ligne | cut -d " " -f3-)
  # Exécute la commande pour tester le fichier
  echo "script ${file}.txt" | socat -t10 EXEC:"docker attach cli",pty STDIO > res.txt
  # Récupère la dernière ligne du fichier de résultat
  echo "---------------------------------------------------------------"
  cat res.txt
  echo "---------------------------------------------------------------"
  actual=$(tail -n 2 res.txt | head -n 1)
  # Vérifie si le résultat obtenu correspond au résultat attendu
  if [[ "$actual" =~ $expected ]]; then
    echo "$file succeeded"
  else
    echo "$file failed"
        echo "--> Expected |$expected|"
        echo "--> Actual |$actual|"
        res=false
  fi
done < "$fichier"
#rm res.txt
if [ "$res" = false ]
then
  echo "Some tests are failing"
  exit 2
fi
echo "Everything was tested successfully"
exit 0