#include <stdio.h>

int main(){
    int num, digit;
    printf("Enter number to be printed in reverse order:");
    scanf("%d",&num);
    while (num){
        digit=num%10;
        num /= 10;
        printf("%d", digit);
    }

    return 1;
}
