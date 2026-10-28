#!/bin/bash
#takes directory as input
input=$PWD/$1
#checks all files in directory and removes it if it doesnt meet the requiments
for file in "$input"/*
do
if [[ $file != *.c ]]
then
if [[ $file != *.h ]]
then
if [[ "${file##*/}" != "makefile" ]]
then
if [[ "${file##*/}" != "Makefile" ]]
then
rm $file
fi
fi
fi
fi
done