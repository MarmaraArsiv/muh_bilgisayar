/* Exercise 5.39 Solution */
#include <stdio.h>

unsigned int gcd( unsigned int, unsigned int );

int main()
{ 
   unsigned int x, y, gcDiv;

   printf( "Enter two integers: " );
   scanf( "%u%u", &x, &y );

   gcDiv = gcd( x, y );

   printf( "Greatest common divisor of %u and %u is %u\n",
          x, y, gcDiv );

   return 0;
}

unsigned int gcd( unsigned int xMatch, unsigned int yMatch )
{ 
   if ( yMatch == 0 )
      return xMatch;
   else
      return gcd( yMatch, xMatch % yMatch );

}



 /*************************************************************************
 * (C) Copyright 2000 by Deitel & Associates, Inc. and Prentice Hall.     *
 * All Rights Reserved.                                                   *
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
