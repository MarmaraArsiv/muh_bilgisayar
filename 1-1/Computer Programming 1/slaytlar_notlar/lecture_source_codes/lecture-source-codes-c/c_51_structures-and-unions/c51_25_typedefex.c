#include <stdio.h>

typedef int tamsayi, int_arr[10];

int main(void)
{ 
	tamsayi i, j, x, arr[50];
	int_arr a;
	i=10;   j=35;   arr[3]=17;
	a[2]=15;
		
	printf("i is %d, j is %d\n", i, j);
	
	for(x=0; x<50; x++)
		printf("arr[%d] is %d\n", x, arr[x]);
		
	for(x=0; x<10; x++)
		printf("a[%d] is %d\n", x, a[x]);
} 

