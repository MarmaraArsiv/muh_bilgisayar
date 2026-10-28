#include <stdio.h>

#define A 'A'

int main(){

    int a=10, b=3;
	float f, g;
	f=a/b;
	g=a/(float)b;

    printf("a is %d, b is %d, f is %f, g is %f\n", a,b,f,g);


    return 1;
}
