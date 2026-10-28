#include <stdio.h>

void f()
{  
   int a=10;
   a++;
   printf("in f(): a=%d\n",a);
}

int main()
{  
   int a=5;
   f();
   printf("After first call to f(): a=%d\n",a);
   f();
   printf("After second call to f(): a=%d\n",a);
}
