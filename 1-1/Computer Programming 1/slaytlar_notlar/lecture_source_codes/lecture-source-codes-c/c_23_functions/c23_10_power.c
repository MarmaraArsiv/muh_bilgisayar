#include <stdio.h>

float power(float, int );

int main()
{
   float P_x, x;

   printf("Enter your number:");
   scanf("%f", &x);
   P_x = 8 * power(x,5) +
         5 * power(x,4) +
         6 * power(x,3) +
         3 * power(x,2) +
         4 * x + 2;
   printf("Result=%f\n", P_x);
   return 0;
}

/* Calculate a^b */
float power(float a, int b)
{
   float result=1;

   for (; b>0; b--)
      result *= a;
   return result;
}
