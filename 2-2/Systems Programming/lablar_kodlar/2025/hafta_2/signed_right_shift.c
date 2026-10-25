#include <stdio.h>

int main(){
    
    int x=-16; /* 1111 1111 1111 0000 in binary, FFF0 in hex */
    int y = x>>4;
    /* x>>4 = 11..11 in binary, FFFF in hex */
    printf("%d>>4 = %d/(2^4) = %d (0x%.8X>>4 = 0x%.8X)\n", x,x,y,x,y);
    
    return 0;
}