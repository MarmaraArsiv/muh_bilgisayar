#include <stdio.h>

int printHello (int i) {
	printf("Hello World for the %d. time\n", i);
}

int main () {
	
	int i = 1;
	for(; i <= 5; i++)
		printHello(i);
}