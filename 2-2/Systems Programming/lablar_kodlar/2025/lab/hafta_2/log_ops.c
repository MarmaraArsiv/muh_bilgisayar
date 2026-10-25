#include <stdio.h>

int main(){
    
    int a = 12;
    printf("%d && 0 = %d\n",a,a&&0);
    printf("%d && 1 = %d\n\n",a,a&&1);

    printf("%d || 0 = %d\n",a,a||0);
    printf("%d || 1 = %d\n\n",a,a||1);
    
    printf("%d == 0 = %d\n",a,a==0);
    printf("%d == 1 = %d\n\n",a,a==1);

    printf("!%d = %d\n",a,!a);
    
    return 0;
}