#include <stdio.h>

int main(void)
{
	unsigned int x, i, j;

	// prompt user for input
	printf("Enter lenght: ");
	scanf("%u", &x); // read value for x

	for (i = 1; i <= x; i++) { // count from 1 to x

		for (j = i; j <= x; j++) { // count from i to x
			printf("*");
		}

		printf("\n");
	}
}

