#include <stdio.h>

typedef unsigned char *ucpointer;

void show_bytes(ucpointer start, size_t len){
  size_t i;
  for (i=0; i<len; i++)
    printf("%p\t0x%.2x\n",start+i, start[i]);
}

int main(){    
    int a = 12345;
    printf("int a = 12345;\n");
    show_bytes((ucpointer) &a, sizeof(int));
    
    return 0;
}