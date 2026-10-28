#include <stdio.h>

void f(int c)
{
   c++;
   printf("in f(): c=%d\n",c);
}

int main()
{  
   int c=5;
   f(c);
   printf("After f(): a=%d\n",c);
}
