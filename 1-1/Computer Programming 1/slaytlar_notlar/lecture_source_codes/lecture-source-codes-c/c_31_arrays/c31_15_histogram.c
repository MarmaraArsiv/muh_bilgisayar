// Fig. 6.8: fig06_08.c
// Displaying a histogram.
#include <stdio.h>
#define SIZE 5

// function main begins program execution
int main(void)
{
   // use initializer list to initialize array n
   int i, j, n[SIZE] = {19, 3, 15, 7, 11};

   printf("%s\t%s\t%s\n", "Element", "Value", "Histogram");

   // for each element of array n, output a bar of the histogram
   for (i = 0; i < SIZE; ++i) {
      printf("%d\t%d\t", i, n[i]) ;

      for (j = 1; j <= n[i]; ++j) { // print one bar
         printf("%c", '*');
	   }

      printf("\n"); // end a histogram bar with a newline
   }
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
