#include <stdio.h>

#define A 'A'

int main(){

    int a, b, c, d;

    a=10;
    c=10,
    b=++a;
    d=c++;

    printf("a is %d, b is %d, c is %d, d is %d\n", a,b,c,d);

    return 1;
}
