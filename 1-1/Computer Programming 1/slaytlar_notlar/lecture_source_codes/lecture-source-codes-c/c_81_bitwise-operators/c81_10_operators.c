// Fig. 10.9: fig10_09.c
// Using the bitwise AND, bitwise inclusive OR, bitwise
// exclusive OR and bitwise complement operators
#include <stdio.h>

void displayBits(unsigned int value); // prototype

int main(void)
{
   // demonstrate bitwise AND (&)
   unsigned int number1 = 65535;
   unsigned int mask = 1;
   int number3;
   puts("The result of combining the following");
   displayBits(number1);
   printf("    ");
   displayBits(mask);
   puts("using the bitwise AND operator & is");
   printf("    ");
   displayBits(number1 & mask);

   // demonstrate bitwise inclusive OR (|)
   number1 = 15;
   unsigned int setBits = 241;
   puts("\nThe result of combining the following");
   printf(" ");
   displayBits(number1);
   displayBits(setBits);
   puts("using the bitwise inclusive OR operator | is");
   displayBits(number1 | setBits);

   // demonstrate bitwise exclusive OR (^)
   number1 = 139;
   unsigned int number2 = 199;
   puts("\nThe result of combining the following");
   displayBits(number1);
   displayBits(number2);
   puts("using the bitwise exclusive OR operator ^ is");
   printf(" ");
   displayBits(number1 ^ number2);

   // demonstrate bitwise complement (~)
   number1 = 21845;
   puts("\nThe one's complement of");
   printf("     ");
   displayBits(number1);
   puts("is");
   displayBits(~number1);
   puts("\nThe two's complement of");
   printf("     ");
   displayBits(number1);
   puts("is");
   displayBits(~number1+1);
   number3=~number1+1;
   printf("The signed value of the number is=%d\n",number3);
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
