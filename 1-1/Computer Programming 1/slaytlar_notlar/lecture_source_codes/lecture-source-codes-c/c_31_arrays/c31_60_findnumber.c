#include <stdio.h>
#include <stdlib.h>
#define SIZE 10
int main(){
    int haystack[SIZE] = {1, 3, 2, 4, 7, 6, 9, 5, 8, 0};
    int needle;
    int i;
    puts("Original array:");
	for (i=0; i<SIZE; i++) {
		printf("%d  ",haystack[i]);
	}
    printf("\n\nEnter a number (0-9) to see its position:");
    scanf("%d",&needle);
    for (i = 0; i < SIZE; i++){
        if (needle != haystack[i]){
            printf("Finding at position %d: %d\n", i, haystack[i]);
            continue;
        }
        printf("Number %d found at position %d\n", needle,i);
        break;
    }
    return 0;
}
