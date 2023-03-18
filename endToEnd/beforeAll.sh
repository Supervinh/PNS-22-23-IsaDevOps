function is_in_db() {
  var=$(echo "" | socat -t5 EXEC:"docker compose exec postgres psql -d tcf-db -U postgresuser -c \\\"$1\\\"",pty STDIO | tail -n 2 | head -n 1 | grep -o '[0-9]\+') #get the number of lines
  if [ "$var" -gt "0" ]; then
    echo "Failed on $1"
    exit 1
  fi
}

function construct_query_find() {
  table=$1
  declare -a attributes=("${!2}")
  declare -a values=("${!3}")
  index=$4
  query="SELECT ${attributes[0]} FROM $table WHERE"
  for i in "${!values[@]}"; do
    query="$query ${attributes[$i]}=\'${values[$i]}\'"
    if [ "$i" -lt "$((index - 1))" ]; then
      query="$query AND"
    fi
  done
  echo "$query"
}

function beforeAll() {
  if [ ! -f "beforeAll.txt" ]; then
    echo "beforeAll not found, no check performed"
    touch beforeAll.txt
  else
    index=0
      init_phase=false
      while IFS="" read -r ligne || [ -n "$ligne" ]; do
        if [ "$init_phase" = true ]; then
          echo "$ligne" | socat -t5 EXEC:"docker attach cli",pty STDIO >> beforeAll.log #Send command to the cli
        elif [ "$ligne" = "Init" ]; then
          init_phase=true #Checking phase is over, we can start to initialize
          if [ "$index" -gt 0 ]; then
            is_in_db "$(construct_query_find "$table" attributes[@] values[@] "$index")" #check if the row exist in db, if yes, fail
          fi
        elif [[ "$ligne" =~ -.* ]]; then # "$ligne" = -attribute:value
          tmp=$(echo "$ligne" | cut -d'-' -f2)
          attributes[index]=$(echo "$tmp" | cut -d':' -f1 | tr -d ' ')
          values[index]=$(echo "$tmp" | cut -d':' -f2 | tr -d ' ')
          index=$((index + 1))
        else # "$ligne" = table name
          if [ "$index" -gt 0 ]; then
            is_in_db "$(construct_query_find "$table" attributes[@] values[@] "$index")" #check if the row exist in db, if yes, fail
          fi #Reset values to check the next line
          index=0
          table="$ligne"
          values=()
          attributes=()
        fi
      done <"beforeAll.txt"
      printf "\n---------------------------------------" >> beforeAll.log
  fi
}