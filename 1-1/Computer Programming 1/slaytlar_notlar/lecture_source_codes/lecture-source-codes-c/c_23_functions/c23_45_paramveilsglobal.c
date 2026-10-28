#include <stdio.h>

void f(int e);

int e=10;

void f(int e)
{
   e++;
   printf("in f(): e=%d\n",e);
}

int main()
{  
   int g=30;
   f(g);
   printf("After first call to f(): g=%d\n",g);
}


