#!/usr/bin/env bash
#git grep -l --all-match -e "for" --or -e "while"

q="language:javascript&extension:EXTENSION:js"
i=0

for var in "$@"
do
   if [ "$i" -eq "0" ]
   then
      q+=$var
   else
      q+="+"$var
   fi
   i+=1
done

google-chrome https://github.com/search?q=$q
