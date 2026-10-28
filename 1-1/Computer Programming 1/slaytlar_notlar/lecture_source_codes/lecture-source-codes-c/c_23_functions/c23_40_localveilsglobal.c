#include <stdio.h>

int d=10;

void f()
{
   d++;
   printf("in f(): d=%d\n",d);
}

int main()
{  
   d=30;
   f();
   printf("After first call to f(): d=%d\n",d);
   f();
   printf("After second call to f(): d=%d\n",d);
}
