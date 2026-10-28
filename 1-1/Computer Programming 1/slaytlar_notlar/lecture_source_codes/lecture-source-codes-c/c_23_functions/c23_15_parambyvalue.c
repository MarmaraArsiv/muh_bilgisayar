#include <stdio.h>

void f(int a)
{
   a+=5;
   printf("in function f(): a=%d\n", a);
}

int main()
{  
   int a=10;
   printf("in main(), before calling f(): a=%d\n",a);
   f(a);
   printf("in main(), after calling f(): a=%d\n",a);
}
