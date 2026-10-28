#!/bin/bash
input=$PWD/$1
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