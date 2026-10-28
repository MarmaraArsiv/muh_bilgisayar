/* FIGURE 2.16        */

#include <stdio.h> /* printf, scanf definitions        */
#define KMS_PER_MILE 1.609 /* conversion constant      */

int
main(void)
{
     double kms  /* distance in miles                 */

     /* Get and echo the distance in miles. */
     scanf("%lf", &miles);
     printf("The distance in miles is %.2f.\n", miles);

     /* Convert the distance to kilometers. */
     kms = KMS_PER_MILE * miles;

     /* Display the distance in kilometers. */
     printf("That equals %.2f kilometers.\n", kms);

     return (0);
}


