#include <stdio.h>

void read_array(int ar[10]){
    int i;
    printf("\nAdress in function:\n");
    for(i=0;i<10;i++)
        printf("%p  ",&ar[i]);
    printf("\n");
    for (i=0; i<10; i++)
        scanf("%d", &ar[i]);
}
int main(){
    int a[10], i;
    printf("Adress in main:\n");
    for(i=0;i<10;i++)
        printf("%p  ",&a[i]);
    printf("\n");
    read_array(a);
    printf("\nAdress in main:\n");
    for(i=0;i<10;i++)
        printf("%p  ",&a[i]);
    printf("\n");
    printf("\nValues in main:\n");
    for (i=0; i<10; i++)
        printf("%d ",a[i]);

    return 0;
}

