#include <stdio.h>
#include <string.h>

int main(int argc, const char * argv[])
{
    char st[]="HeLlo";
    int j;

    printf("Original: %s\n\n",st);
    /* Convert lowercase to uppercase */
    for (j=0; j<strlen(st); j++)
        if (('a'<=st[j]) && (st[j]<='z'))
            st[j] += 'A'-'a';

    printf("Uppercase: %s",st);
    puts("");
    return 0;
}
