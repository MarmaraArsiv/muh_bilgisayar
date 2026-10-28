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
   for (i = 0; i < size; i++)
      for (j = i + 1; j < size; j++)
         if (ar[i] > ar[j])
            swap(&ar[i],&ar[j]);
}

int binary_search(int A[], int number, int N)
{  int low = 0, high = N - 1, mid;

   while (low <= high)
   {  mid = (low + high) / 2;
      if (A[mid] == number)
         return mid;
      if (A[mid] < number)
         low = mid + 1;
      else
         high = mid - 1;
   }
   return -1;
}

int main()
{  int ar[10];
   int location, search_number;
   read_array(ar,10);
   search_number=ar[5];
   printf("Before sorting %d is in 5th location\n\n",search_number);
   bubble_sort(ar,10);
   print_array(ar,10);
   location=binary_search(ar, search_number, 10);
   printf("\nAfter sorting %d is in %dth location\n",search_number,location);
   return 0;
}
