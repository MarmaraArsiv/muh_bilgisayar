#include <stdio.h>

int main(void)
{
	int x, y, m, n, i, j;

	// prompt user for input
	printf("Enter arrival time: ");
	scanf("%d%d", &x, &y); // read values for x and y

	printf("Enter leave time: ");
	scanf("%d%d", &m, &n); // read values for x and y

	i=m-x;
	j=n-y;
	printf("%d %d %d %d %d %d\n", x, y, m, n, i, j);
	//j=n-y;
	if (j<0){
        j+=60;
        i--;
	}

	printf("Working hours: %d hours %d minutes", i,j);

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
