#include <stdio.h>

long factorial(int n);

int main()
{
	int n;
	printf("Enter an integer: ");
	scanf("%d", &n);
	printf("%d! = %ld\n", n, factorial(n));
	return 0;
}

long factorial(int n)
{
	if (n>0)
		return n*factorial(n-1);
	else if (n==0)
		return 1;
	return 0;
}
