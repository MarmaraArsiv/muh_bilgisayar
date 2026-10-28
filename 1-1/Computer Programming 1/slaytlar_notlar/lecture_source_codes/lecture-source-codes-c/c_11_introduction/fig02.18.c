/* FIGURE 2.18 */
#include <stdio.h>

int
main(void)
{
    int first, second;
	double temp, ans;

    printf("Enter two integers> ");
    scanf("%d%d", &first, &second); 
	temp = second / first;
    ans = first / temp;
    printf("The result is %.3f\n", ans);

    return (0);
}


