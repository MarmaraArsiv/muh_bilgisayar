#!/bin/bash
# Get input file name argument
filename=$1
# Get search term argument
search=$2
# Get replace term argument
replace=$3
# Find number of occurrences
occurrences=`grep -o $search $filename | wc -l`
if [[ $search != "" && $replace != "" ]]; then
# Replace all matches
sed -i "s/$search/$replace/g" $filename
fi
echo "All $occurrences occurrences of \"$search\" in \"$filename\" has changed with \"$replace\""