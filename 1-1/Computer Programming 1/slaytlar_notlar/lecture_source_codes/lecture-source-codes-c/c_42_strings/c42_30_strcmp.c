#include <stdio.h>
#include <string.h>

int main(int argc, const char * argv[])
{
    /* Create a place to store our results */
    int result;

    /* Create two arrays to hold our data */
    char example1[50];
    char example2[50];

    /* Copy two strings into our data arrays */
    strcpy(example1, "C programming");
    strcpy(example2, "C programming is fun");

    /* Compare the two strings provided */
    result = strcmp(example1, example2);

    /* If the two strings are the same say so */
    if (result == 0) printf("Strings are the same\n");

 
    if (result < 0) printf("First string \"%s\" is less than the second \"%s\".\n", example1, example2);

    return 0;
}
