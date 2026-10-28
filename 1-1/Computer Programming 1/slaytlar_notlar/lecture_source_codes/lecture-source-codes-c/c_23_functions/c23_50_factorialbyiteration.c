#include <stdio.h>

long factorial(int n);

int main()
{  
   int a;
   printf("Enter a number to get the factorial> ");
   scanf("%d",&a);
   printf("\n%d! = %ld", a, factorial(a));
}

long factorial(int n)
{  
   int i; 
   long res = 1;
   if (n == 0)
      return 1;
   for (i = 1; i <= n; i++)
      res *= i;
   return res;
}

