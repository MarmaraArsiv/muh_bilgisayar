#!/bin/bash
file=$1
# While file still have a line
while read line; do
# Print dots until reach the given number
 for ((i=0 ; i < $line ; i++)); do
	echo -n "."
 done
 echo " "
done < $file
