#include <stdio.h>

int main(){
    
    short sx =  -12345;
    unsigned ux = sx; 	  /* ux = 53191 */
    unsigned uy = (unsigned)(unsigned short) sx; /* uy = 53191 */

    printf(" sx=%hi (0x%x)\n ux=%hu (0x%.8x)\n uy=%d 						(0x%.8x)\n\n",sx,sx,ux,ux,uy,uy);
    
return 0;
}