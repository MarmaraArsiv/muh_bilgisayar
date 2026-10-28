#include <stdlib.h>
#include <stdio.h>
#include <unistd.h>

static void show_pointer(void *p, char *descr) {
    printf("%s\t\t%p\n", descr, p);
}

char big_array[1L<<24];    /*  16 MB */
char huge_array[1L<<31];   /*   2 GB */

int global = 0;

int main ()
{
    void *phuge1, *psmall2, *phuge3, *psmall4;
    int local = 0;
    phuge1 = malloc(1L << 45);
    psmall2 = malloc(1L << 8);
    phuge3 = malloc(1L << 40);
    psmall4 = malloc(1L << 8);

    printf("%ld \n",1L << 8 );

    show_pointer((void *) &global, "global");
    show_pointer((void *) big_array, "big ar");
    show_pointer((void *) huge_array, "huge ar");
    printf("\n");

    show_pointer((void *) &local, "local");
    printf("\n");

    show_pointer((void *) phuge1, "phuge1");
    show_pointer((void *) phuge3, "phuge3");
    show_pointer((void *) psmall2, "psmall2");
    show_pointer((void *) psmall4, "psmall4");
    printf("\n");

    show_pointer((void *) exit, "exit");
    show_pointer((void *) malloc, "malloc");
    return 0;
}