#include<stdio.h>

int main()
{
   int number;
   int three=3;
   int five=5;
   int fiftten=15;

    printf("enter the number :");
    scanf("%d",&number);

   printf("%d\n",number);


   if (number%three==0)
    {
    printf(" The number is divisible by 3 ");

    }
    if (number%five==0)
            {
        printf( "The number is divisible by 5");


            }
            if (number%fiftten==0)
                {
        printf(" The number is divisible by 15");

                }
return 0;
}
