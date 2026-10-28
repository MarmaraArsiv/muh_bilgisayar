#include <stdio.h>

#define ABC '*'

int main(){
    char ch;
    ch=ABC;
    printf("Output is %c\n", ch);

    printf("Output is %d\n", ch);

    int a;
    a=ch;
    printf("ASCII Code of %c is %d\n", ch, a);

    int b=0;
    printf("ASCII Code of %d is %c", b, b);

    return 0;
}
