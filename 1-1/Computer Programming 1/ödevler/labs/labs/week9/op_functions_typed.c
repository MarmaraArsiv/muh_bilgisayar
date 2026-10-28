
#include <stdio.h>

int add (int a, int b) {
  return a + b;
}

int mul (int a, int b) {
  return a * b;
}

int sub (int a, int b) {
  return a - b;
}

int div (int a, int b) {
  return a / b;
}


int main()
{
   int a, b;
   
   printf("Please enter two integer numbers: ");
   scanf("%d%d", &a, &b);
   
   printf("%d + %d = %d \n", a, b, add(a,b));
   printf("%d - %d = %d \n", a, b, sub(a,b));
   printf("%d * %d = %d \n", a, b, mul(a,b));
   printf("%d / %d = %d \n", a, b, div(a,b));
   
   return 0;
}
