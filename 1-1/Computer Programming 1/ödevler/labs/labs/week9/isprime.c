#include <stdio.h>

int isPrime(int, int); // function prototype

int main()
{
	long number; // input

	printf("\nEnter a number: ");
	scanf("%ld", &number);

	// calculate and display whether the number is a prime number
	if(isPrime(number,2))
		printf("\n %ld is a prime number\n", number);
	else
		printf("\n %ld is not a prime number\n", number);
} // end main

// isPrime checks whether a number is a prime number
int isPrime(int n, int i)
{
	if (i==n) return 1;
	if (n%i == 0) return 0;
	return isPrime (n, i+1);
}
