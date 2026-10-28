#include <stdio.h>
#define SIZE 10

void set_array(int ar[SIZE]){
    int i;
    for (i=0; i<SIZE; i++)
        ar[i]=2+2*i;
}
int main(){
    int a[SIZE], i;
    int total=0;
    set_array(a);
    printf("Index\tValue\n");
    for (i=0; i<SIZE; i++){
        printf("%d\t%d\n",i,a[i]);
        total+=a[i];
    }
    printf("Total=%d\n",total);

    return 0;
}

