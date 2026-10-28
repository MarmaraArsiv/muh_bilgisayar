#!/bin/bash
#These part creates directorys
mkdir -p smallest
mkdir -p largest
#Sorts all files by size and selects first one and adds to 'largest' 
mv `ls --ignore="*.sh" -Sp | grep -v / | head -1` ./largest/
#Sorts all files again by size but reverse and selects first one and adds to 'smallest' 
mv `ls --ignore="*.sh" -Srp | grep -v / | head -1` ./smallest/