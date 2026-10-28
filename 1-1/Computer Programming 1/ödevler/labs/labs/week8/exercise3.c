#include <stdio.h>

int main()
{
	
	int s; // seconds
	int h; // hours
	int m; // minutes
	int t; // total time

	// read total time passed from the user
	printf("Enter total time passed: ");
	scanf("%d", &t);

	// Get the number of minutes.
	m = t / 60;
	
	// Get the remaining seconds.
	s = t % 60;
	
	// Get the number of hours.
	h = m / 60;
	m = m % 60;

	// Display the results.
	printf("\n%d seconds is equivalent to:\n", t);
	printf(" %d hours: \n %d minutes:\n %d seconds: \n", h, m, s);
	
	//display in h:m:s format
	printf("\nIn hours : minutes : seconds\n");
	
	if (h < 10)
		printf("0%d:",h);
	else
		printf("%d:",h);
		
	if (m < 10)
		printf("0%d:",m);
	else
		printf("%d:",m);

	if (s < 10)
		printf("0%d:\n",s);
	else
		printf("%d:\n",s);

	return 0;
}
