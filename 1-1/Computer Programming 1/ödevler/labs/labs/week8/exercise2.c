#include<stdio.h>

int main(){ 
	float b,e; 
	
	printf("MILEAGE REIMBURSEMENT CALCULATOR\n");
	printf("Enter beginning odometer reading=>"); 
	scanf("%f",&b); 
	printf("\nEnter ending odometer reading=>"); 
	scanf("%f",&e); 
	
	printf("\nYou traveled %.1f miles. At $0.35 per mile, your reimbursement is %.2f.",e-b,(e-b)*0.35); 
	return 0;
	
}
	
	
