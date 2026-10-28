// Fig. 10.13: fig10_13.c
// Using the bitwise shift operators
#include <stdio.h>

void displayBits(unsigned int value); // prototype

int main(void)
{
   unsigned int number1 = 960; // initialize number1

   // demonstrate bitwise left shift
   puts("\nThe result of left shifting");
   printf("   ");
   displayBits(number1);
   puts("8 bit positions using the left shift operator << is");
   displayBits(number1 << 8);

   // demonstrate bitwise right shift
   puts("\nThe result of right shifting");
   displayBits(number1);
   puts("8 bit positions using the right shift operator >> is");
   printf("  ");
   displayBits(number1 >> 8);
}

// display bits of an unsigned int value
void displayBits(unsigned int value)
{
   // define displayMask and left shift 31 bits
   unsigned int c, displayMask = 1 << 31;
   char x;

   printf("%u = ", value);

   // loop through bits
   for (c = 1; c <= 32; ++c) {

      x=value & displayMask ? '1':'0';
      printf("%c",x);

      value <<= 1; // shift value left by 1

      if (c % 8 == 0) { // output space after 8 bits
         printf(" ");
      }
   }

   printf("\n");
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
