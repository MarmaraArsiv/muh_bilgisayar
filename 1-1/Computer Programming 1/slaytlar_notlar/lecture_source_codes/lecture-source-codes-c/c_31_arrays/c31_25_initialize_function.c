#include <stdio.h>

void read_array(int ar[10]){
    int i;
    printf("Enter 10 numbers:\n");
    for (i=0; i<10; i++)
        scanf("%d", &ar[i]);
}
int main(){
    int a[10], i;
    read_array(a);
    printf("\nValues in main:\n");
    for (i=0; i<10; i++)
        printf("%d ",a[i]);

    return 0;
}

