#include <stdio.h>

#define A 'A'

int main(){
    int a=8, b=3;
    float x=a;

    //printf("a/b is %f\n", a/b);

    printf("a/b is %d, x/b is %f\n", a/b, x/b);

    printf("a/b is %f, x/b is %f\n", (float)a/b, x/b);


    return 1;
}
