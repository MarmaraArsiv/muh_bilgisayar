#include <stdio.h>

void read_array(int ar[], int size)
{  int i;
   for (i=0; i<size; i++)
      scanf("%d", &ar[i]);
}

void print_array(int ar[], int size)
{  int i;
   printf("Sorted Array:\n");
   for (i=0; i<size; i++)
      printf("%d\t", ar[i]);
   printf("\n");
}

void swap(int *a, int *b)
{  int temp;
   temp = *a;
   *a = *b;
   *b = temp;
}
void bubble_sort(int ar[], int size)
{  int i, j;
   for (i = 0; i < size-1; i++)
      for (j = 0; j < size-1-i; j++)
         if (ar[j] > ar[j+1])
            swap(&ar[j],&ar[j+1]);
}

int main()
{  int ar[10];
   read_array(ar,10);
   bubble_sort(ar,10);
   print_array(ar,10);
   return 0;
}
