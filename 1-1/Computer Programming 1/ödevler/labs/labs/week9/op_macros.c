
#include <stdio.h>

#define op(a, b, opname, op) \
  int opname(int a, int b) { \
    return a op b;	     \
  }

op(a, b, add, +);
op(a, b, mul, *);
op(a, b, sub, -);
op(a, b, div, /);


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
