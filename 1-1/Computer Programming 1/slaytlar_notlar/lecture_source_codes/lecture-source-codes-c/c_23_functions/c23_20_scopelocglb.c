#include <stdio.h>

int count;

int func()
{
   int i, j;
   printf("Enter two numbers:");
   scanf("%d %d", &i, &j);
   count += i+j;
   return 0;
}

int main()
{
   int i;

   func();
   for (i=0; i<count; i++)
      printf("%d",i);
   return 0;
}
