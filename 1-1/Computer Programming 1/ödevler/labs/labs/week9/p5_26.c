/* Exercise 5.26 Solution */
#include <stdio.h>

int perfect( int );

int main()
{ 
   int j;

   printf( "For the integers from 1 to 1000:\n" );

   for ( j = 2; j <= 1000; j++ )
      if ( perfect( j ) )
         printf( "%d is perfect\n", j );

   return 0;
}

int perfect( int value )
{ 
   int factorSum = 1, i;

   for ( i = 2; i <= value / 2; i++ )
      if ( value % i == 0 )
         factorSum += i;

   if ( factorSum == value )
      return 1;
   else
      return 0;
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
