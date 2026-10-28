/* FIGURE 2.19  A Program That Produces Incorrect Results Due to & Omission */
#include <stdio.h>

int
main(void)
{
    int first, second, sum;

    printf("Enter two integers> ");
    scanf("%d%d", first, second); /* ERROR!! should be &first, &second */
    sum = first + second;
    printf("%d + %d = %d\n", first, second, sum);

    return (0);
}

/*
Enter two integers> 14   3
5971289 + 5971297 = 11942586
*/
