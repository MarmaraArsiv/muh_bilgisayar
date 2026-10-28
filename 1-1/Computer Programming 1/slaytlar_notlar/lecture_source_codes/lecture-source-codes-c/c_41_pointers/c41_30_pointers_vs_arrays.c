#include <stdio.h>

void foo(char arr_arg[], char* ptr_arg)
{
    char a = arr_arg[4];
    char b = ptr_arg[4];
    printf("Foo a=%c  b=%c\n",a,b);
}

int main()
{
    char array_place[12] = "don't panic";
    char* ptr_place = "don't panic";
    int i;
    
    
    char a = array_place[7];
    char b = ptr_place[7];

    printf("Main a=%c  b=%c\n",a,b);

    foo(array_place, ptr_place);

    puts("\nArray Traversal");
    
    /* array traversal */
    for (i = 0; i < sizeof(array_place); ++i)
        printf("%c ", array_place[i]);

    printf("\n");

    /* pointer traversal */
    puts("\nPointer Traversal");
    for (; *ptr_place; ++ptr_place)
        printf("%c ", *ptr_place);

    return 0;
}
