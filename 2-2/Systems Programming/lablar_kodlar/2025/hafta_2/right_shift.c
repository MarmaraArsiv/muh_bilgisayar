#include <stdio.h>

int main(){
    
    unsigned x=128; /* 0000 0000 1000 0000 in binary, 0080 in hex */
    int y = x>>4;
    /* x>>4 = 00..0001000 in binary, 0008 in hex */
    printf("%d>>4 = %d/(2^4) = %d (0x%.8X>>4 = 0x%.8X)\n", x,x,y,x,y);
    
    return 0;
}