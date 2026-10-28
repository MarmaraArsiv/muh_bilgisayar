#!/bin/bash
# Initalize number by a default value
number="0"
if [ $# -gt "0" ]; then
  number="$1"
fi
# If number is negative make it positive
if [ $number -lt "0" ]; then
	number=$(( $number * -1 ))
fi
result="0"
# Digit swapping algorithm
swap_digits () {
	n="$1"
	lastDigit=$(( $n % 10 ))
	swapped=$(( $lastDigit * 10 + (($n-$lastDigit)/10)))
}

# While it has more than one digit
while [ "$number" -gt "9" ]; 
do
	# Get last two digits
	lastDigits=$(( $number % 100 ))
	swapped=0
	# Swap digits
	swap_digits $lastDigits
	# Sum with previous result
	result=$(( $result +  $swapped))
	# Remove last digit
	number=$(( $number / 10 ))
done
echo "$result"