#include <stdio.h>

int main()
{
    int num, digit;
    int a, b, result=1, i;

    printf("Enter a:");
    scanf("%d", &a);
    printf("Enter b:");
    scanf("%d", &b);

    /*for (i=0; i<b; i++){
        result *= a;
        printf("Result:%d\n",result);
    }*/

    for (i=0; i<b; i++)
        result *= a;

    printf("Result:%d",result);


    return 1;
}
