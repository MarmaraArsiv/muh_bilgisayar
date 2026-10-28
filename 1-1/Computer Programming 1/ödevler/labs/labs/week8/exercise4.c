#include <stdio.h>

int main(){
	
	// Variables
	int term;
	double amount;
	double gross_amount;
	double interest;
	double interest_rate;
	double monthly_pay;
	
	// processing phase
	// get first mortgage amount
	printf("Enter mortgage amount in dollars: " );
	scanf( "%lf", &amount );
	
	printf( "\n Enter Mortgage term (in years): " );
	scanf( "%d", &term );

	printf( "\n Enter Interest rate: " );
	scanf( "%lf", &interest_rate );

	//Calculating Payable Interest
	interest = amount * interest_rate/100.0 * term;

	//Calculating the tax
	gross_amount = amount + interest;

	//Calculating Monthly Payable Amount
	monthly_pay = gross_amount / (term * 12);

	// Display the Monthly Payable Interest
	printf("\n The Monthly Payable Interest is: $%.2f\n" , monthly_pay );

} // end main
