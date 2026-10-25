#include <stdio.h>

int main(){
    short int x = -15;
    unsigned short ux = (unsigned short) x; //explicit casting
    printf("x = %d (0x%hX), ux = %hu (0x%X)\n", x, x, ux, ux);

    unsigned short u = 65535u; //Umax
    short tu = u; 	//implicit casting
    printf("u = %hu (0x%X), tu = %d (0x%hX)\n", u,u,tu,tu);    
    
    return 0;
}