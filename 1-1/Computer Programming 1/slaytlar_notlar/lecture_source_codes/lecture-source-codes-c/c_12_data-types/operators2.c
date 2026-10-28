#include <stdio.h>

#define A 'A'

int main(){
    int a=1, b=0, x=1, c=1;

    x= a == b && (c=2);

    printf("a is %d, b is %d c is %d x is %d\n", a, b, c, x);

    x= a == b || (c=2);

    printf("a is %d, b is %d c is %d x is %d\n", a, b, c, x);
    return 1;
}
