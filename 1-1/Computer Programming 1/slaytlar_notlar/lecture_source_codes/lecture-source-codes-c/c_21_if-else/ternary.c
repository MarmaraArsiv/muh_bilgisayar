#include <stdio.h>

int main(){
    int a=1, b=2, c=3, n=10, m=2, k;
    a = (b>c) ? b : c;
    k = (n!=0) ? n/m : 0;

    printf("a:%d\nk:%d",a, k);

    return 1;
}
