// Fig. 6.4: fig06_04.c
// Initializing the elements of an array with an initializer list.
#include <stdio.h>

// function main begins program execution
int main(void)
{
   // use initializer list to initialize array n
   int x;


   int i, n[5] = {32, 27, 64, 18, 95};

   printf("%s\t%s\n", "Element", "Value");

   // output contents of array in tabular format
   for (i = 0; i < 5; ++i) {
      printf("%d\t%d\n", i, n[i]);
   }
   printf("\nValues:\n");
   printf("%d\t%d\t%d\t%d\t%d\n",*n, *(n+1),*(n+2),*(n+3),*(n+4));

   printf("\nAddresses:\n");
   printf("%p\t%p\t%p\t%p\t%p\n",n, n+1, n+2, n+3, n+4);
   printf("%p\t%p\t%p\t%p\t%p\n",&n[0], &n[1], &n[2], &n[3], &n[4]);
}


/**************************************************************************
 * (C) Copyright 1992-2015 by Deitel & Associates, Inc. and               *
 * Pearson Education, Inc. All Rights Reserved.                           *
 *                                                                        *
 * DISCLAIMER: The authors and publisher of this book have used their     *
 * best efforts in preparing the book. These efforts include the          *
 * development, research, and testing of the theories and programs        *
 * to determine their effectiveness. The authors and publisher make       *
 * no warranty of any kind, expressed or implied, with regard to these    *
 * programs or to the documentation contained in these books. The authors *
 * and publisher shall not be liable in any event for incidental or       *
 * consequential damages in connection with, or arising out of, the       *
 * furnishing, performance, or use of these programs.                     *
 *************************************************************************/
