#include <stdio.h>

int main()
{
    int sum=0, n, count=0;
    float avg;

    printf("Enter number, negative number will terminate:");
    scanf("%d", &n);
    while (n>=0){
        sum += n;
        count++;
        printf("Enter number, negative number will terminate:");
        scanf("%d", &n);
    }
    avg = (count)?(float)sum/count:0;

    printf("Average:%f",avg);

    return 1;
}
