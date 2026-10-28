#include <stdio.h>

#define SIZE 10

typedef struct {
   int x, y;
} nokta_t, noktalar_t[SIZE];

int main(void)
{ 
	int x;
	nokta_t n;
	noktalar_t N;
	n.x = 5;
	N[4].x = 8;
	
	printf("n.x is %d\n", n.x);
	
	for(x=0; x<SIZE; x++)
		printf("N[%d].x is %d\n", x, N[x]);

} 

