/* Exercise 5.28 Solution */
#include <stdio.h>

int gcd( int, int );

int main()
{ 
   int j, a, b;

   for ( j = 1; j <= 5; j++ )
   { 
      printf( "Enter two integers: " );
      scanf( "%d%d", &a, &b );
      printf( "The greatest common divisor "
             "of %d and %d is %d\n\n", a, b, gcd( a, b ) );
   }
   return 0;
}

int gcd( int x, int y )
{ 
   int i, greatest = 1;

   for ( i = 2; i <= ( ( x < y ) ? x : y ); i++ )
      if ( x % i == 0 && y % i == 0 )
         greatest = i;

   return greatest;
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
