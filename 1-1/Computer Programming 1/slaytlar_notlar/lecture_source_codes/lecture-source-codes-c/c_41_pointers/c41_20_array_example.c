// Fig. 7.20: fig07_20.cpp
// Using indexing and pointer notations with arrays.
#include <stdio.h>
#define ARRAY_SIZE 4

int main(void)
{
   int b[] = {10, 20, 30, 40}; // create and initialize array b
   int *bPtr = b; // create bPtr and point it to array b
   int i, offset;
   // output array b using array index notation
   puts("Array b printed with:\nArray index notation");

   // loop through array b
   for (i = 0; i < ARRAY_SIZE; ++i) {
      printf("b[%u] = %d\n", i, b[i]);
   }

   // output array b using array name and pointer/offset notation
   puts("\nPointer/offset notation where\n"
         "the pointer is the array name");

   // loop through array b
   for (offset = 0; offset < ARRAY_SIZE; ++offset) {
      printf("*(b + %u) = %d\n", offset, *(b + offset));
   }

   // output array b using bPtr and array index notation
   puts("\nPointer index notation");

   // loop through array b
   for (i = 0; i < ARRAY_SIZE; ++i) {
      printf("bPtr[%u] = %d\n", i, bPtr[i]);
   }

   // output array b using bPtr and pointer/offset notation
   puts("\nPointer/offset notation");

   // loop through array b
   for (offset = 0; offset < ARRAY_SIZE; ++offset) {
      printf("*(bPtr + %u) = %d\n", offset, *(bPtr + offset));
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

