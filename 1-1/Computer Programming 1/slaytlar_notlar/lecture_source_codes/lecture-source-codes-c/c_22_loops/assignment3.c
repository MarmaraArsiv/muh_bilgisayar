#include <stdio.h>

int main(void)
{
	char x;
	int y, m, n, i, j;

	printf("Enter a character: ");
	scanf("%c", &x); // read values for x and y
    printf("Character is %c", x);

	while(x!='q'){
        printf("ASCII code is:%d New character:%c\n", x, x+3);
        // prompt user for input
        printf("Enter a character: ");
        scanf("%c", &x); // read values for x and y
        printf("Character is %c", x);
        if(x=='\n')
            continue;

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
