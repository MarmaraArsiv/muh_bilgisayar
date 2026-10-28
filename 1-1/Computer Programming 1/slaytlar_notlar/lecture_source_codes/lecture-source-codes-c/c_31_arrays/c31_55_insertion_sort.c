#include <stdio.h>
void read_array(int ar[], int size)
{  int i;
   for (i=0; i<size; i++)
      scanf("%d", &ar[i]);
}

void print_array(int ar[], int size)
{  int i;
   for (i=0; i<size; i++)
      printf("%d\t", ar[i]);
   printf("\n");
}

void insertion_sort(int ar[], int size)
{
   int value, i, j;
   for (i=1; i<size; i++)
   {
      value = ar[i];
      j = i-1;
      while ((j>=0) && (ar[j]>value))
      {
         ar[j+1] = ar[j];
         j--;
      }
      ar[j+1] = value;
   }
}

int main()
{  int ar[10];
   read_array(ar,10);
   insertion_sort(ar,10);
   print_array(ar,10);
   return 0;
}
