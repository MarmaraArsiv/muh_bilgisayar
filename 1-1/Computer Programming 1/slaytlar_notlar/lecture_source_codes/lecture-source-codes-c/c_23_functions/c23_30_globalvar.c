#include <stdio.h>

void f(void);

int b;

void f()
{
   b++;
   printf("in f(): b=%d\n",b);
}

int main()
{  
   f();
   printf("After first call to f(): b=%d\n",b);
   f();
   printf("After second call to f(): b=%d\n",b);
}


